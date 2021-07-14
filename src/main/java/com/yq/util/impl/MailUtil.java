package com.yq.util.impl;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class MailUtil {
    //此类是邮件发送的工具类，提供返回message对象，transport对象、
    //transport对象已经连接到服务器
    private static Session session;//session对象
    private static Properties pro;//配置文件对象
    protected static Transport transport;
    protected static boolean debug;//debug模式
    protected static Message message;//message对象
    protected static String username;
    protected static String password;
    protected static String auth;//是否开启登录验证
    protected static String host;//服务器

    protected MailUtil(){};//私有化构造方法

    //静态代码块用于初始化properties类的参数
    static {
        InputStream stream = MailUtil.class.getClassLoader().getResourceAsStream("mail.properties");
        pro = new Properties();
        try {
            pro.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置邮件所需的配置信息 通过配置文件获取，然后在将值设置

        //初始化用户名和密码
        username = pro.getProperty("username");
        password = pro.getProperty("password");
        host = pro.getProperty("host");
        debug = Boolean.parseBoolean(pro.getProperty("debug"));
        pro.setProperty("mail.host", host);//设置服务器
        pro.setProperty("mail.transport.protocol", pro.getProperty("protocol"));//设置邮箱协议
        auth = pro.getProperty("auth");
        pro.setProperty("mail.smtp.auth", auth);//是否开启用户名密码验证

        //初始化session
        session = Session.getDefaultInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication () {
                //发件人邮件用户名，密码
                return new PasswordAuthentication(username, password);
            }
        });
        //qq邮箱的话 进行ssl验证
        if (pro.getProperty("host").contains("qq")) {
            //是QQ服务器
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                pro.put("mail.smtp.ssl.enable","true");
                pro.put("mail.smtp.socketFactory",sf);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public static Session getSession () throws MessagingException {
        setTransport(session);
        return session;
    }

    public static void setSession () {
        MailUtil.session = session;
    }

    public static Transport getTransport () {
        return transport;
    }

    public static void setTransport (Session session) throws MessagingException {
        //初始化transport对象
        session.setDebug(debug);
        transport = session.getTransport();
        transport.connect(host,username,password);
        setMessage(session);
    }
    
    public static Message getMessage () {
        return message;
    }

    public static void setMessage (Session session) {
        //初始化message对象
        message = new MimeMessage(session);
        //设置发送人，因为发件人邮箱和用户邮箱相同
        try {
            message.setFrom(new InternetAddress(username));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
