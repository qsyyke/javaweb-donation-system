package com.yq.web.servlet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.impl.MailIpml;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

/**
 * 验证码发送成功和失败，都会进行session的存储，不需要进行更换，因为每一次请求，对于同一个邮箱来说，都会重新进行设置
 * <p>MailIpml mail_yzm = new MailIpml(email);mail_oK = mail_yzm.isMail_oK();
 * 用于发送验证码，和获取是否发送成功 0代表发送失败 1代表发送失败</p>
 * <p>发送失败存储session格式 session.setAttribute("isSuccessSend&"+email,0);</p>
 *
 * 发送成功，除了存储此值外，还会存储验证码
 *  <p>成功session格式 session.setAttribute("isSuccessSend&"+email,1);
 *  session.setAttribute("yzm&"+email,yzm);
 *  </p>
 *
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/30 21:08
 **/

@WebServlet("/sendmail")
public class SendMail extends HttpServlet {

    //邮箱
    private String email;

    //用户名
    private String uname;

    //邮件发送状态码
    private int mail_oK;
    private int status;

    private String message;


    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 此方法用于发送验证码
     * 用户点击发送验证码，会请求这个类 此类调用验证码对象完成发送，并且将此次的验证码保存至session中
     * 不需要设置session保存至的存活时间 发送一个请求，就会把原来的那个值替换掉
     * 保存至session的格式为yzm+邮箱号
     * @param request
     * @param response
     * @return void
     */

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取是否是捐款发送的请求
        String donate = request.getParameter("donate");

        //获取邮箱
        email = request.getParameter("email");

        //此对象发送验证码
        MailIpml mail_yzm = new MailIpml(email);

        //只有验证码发送成功 才能进行获取
        mail_oK = mail_yzm.isMail_oK();

        //设置响应的消息
        if (mail_oK == 1) {
            message = "success";
        }else {
            message = "fail";
        }

        //获取响应状态码
         status = response.getStatus();

        HttpSession session = request.getSession();

        if (mail_oK != 1) {
            //封装json
            String json = packJSON(status, "fail", mail_oK, uname, email,"");

            //登录判断是否发送过验证码 1代表发送过 0 没有发送过
            session.setAttribute("isSuccessSend&"+email,0);

            //发送失败
            response.getWriter().write(json);

            return;
        }

        //能执行到此处说明 验证码发送成功
        //获取验证码
        String yzm = mail_yzm.getYzm();

        System.out.println("session: "+session);

        //保存至session
        session.setAttribute("yzm&"+email,yzm);

        if (donate == null) {
            //不是捐款页面发送的请求
            //登录判断是否发送过验证码 1代表发送过 0 没有发送过
            session.setAttribute("isSuccessSend&"+email,1);
            String json = packJSON(status, "success", mail_oK, uname, email,"");

            //发送成功
            response.getWriter().write(json);
            return;
        }

        //能够运行到这里，说明是捐款页面发送的请求
        String json = packJSON(status, "success", mail_oK, uname, email,yzm);
        response.getWriter().write(json);
    }
    private String packJSON(int code,String message,int OK,String uname,String email,String verCode) throws JsonProcessingException {
        //此对象用于封装json
        Map<String,Object> mapOne = new HashMap<>();
        //用于封装信息
        Map<String,Object> map_next = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        //封装json
        mapOne.put("code",code);
        map_next.put("message",message);
        map_next.put("OK",OK);
        map_next.put("uname",uname);
        map_next.put("email",email);
        map_next.put("verCode",verCode);
        mapOne.put("data",map_next);
        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
