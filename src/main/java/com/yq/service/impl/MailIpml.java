package com.yq.service.impl;


import com.yq.util.impl.MailUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.Random;

/**
 *@decript:此类用于发送验证码 使用try catch进行判断是否发送成功
 * 没有抛出异常 则代表发送成功 反之
 */
public class MailIpml {
    //此类同于发送验证码
    protected String yzm = "";
    protected Session session;
    protected Message message;
    protected Transport transport;
    protected String mail;//用户QQ邮箱
    //是否发送成功 1为成功 0为失败
    private int mail_oK = 1;
    public MailIpml(String mail) {
        this.mail = mail;
        try {
            session = MailUtil.getSession();
            message = MailUtil.getMessage();
            transport = MailUtil.getTransport();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sendMail();
    }
    public void sendMail() {
        //初始化验证码
        int[] randoms = {0,1,2,3,4,5,6,7,8,9};
        for (int i = 0; i < 4; i++) {
            int random = new Random().nextInt(10);
            yzm = yzm+randoms[random];
        }
        //标题
        try {
            message.setSubject("捐款验证码");
            //设置内容
            message.setContent("<h1 style=\"text-align: center\">你的验证码为<span style=\"color: red\">"+yzm+"</span></h1>","text/html;charset=utf-8");
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(mail));
            for (Address allRecipient : message.getAllRecipients()) {
                System.out.println("接受者: "+allRecipient);
            }
            //异常在此处进行判断
            transport.sendMessage(message,message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
            //如果执行到此处 代表发送失败
            mail_oK = 0;
        }
    }

    public String getYzm () {
        return yzm;
    }

    public void setMail (String mail) {
        this.mail = mail;
    }

    public int isMail_oK () {
        return mail_oK;
    }

    public void setMail_oK (int mail_oK) {
        this.mail_oK = mail_oK;
    }
}

