package com.yq.web.servlet;

import com.yq.util.impl.MailUtil;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import java.util.Date;

public class MailTest1 {
    @Test
    public void get() {
        try {
            Session session = MailUtil.getSession();
            Message message = MailUtil.getMessage();
            message.setRecipient(Message.RecipientType.TO,new InternetAddress("2291308006@qq.com"));
            message.setSubject("验证码");
            message.setContent("你的验证码为 ","text/html;charset=utf-8");
            Transport transport = MailUtil.getTransport();
            transport.sendMessage(message,message.getAllRecipients());



        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void get1() {
        System.out.println(new Date().getTime());
    }
}
