package com.yq.web.servlet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取验证码
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/3 14:23
 **/

@WebServlet("/getauthcode")
public class GetAuthCode extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取参数
        Map<String, String[]> parameterMap = request.getParameterMap();

        //获取输入验证码
        String email = parameterMap.get("email")[0];

        //获取发送的验证码
        HttpSession session = request.getSession();
        String send_yzm = (String)session.getAttribute("yzm&" + email);;

        //获取是否发送成功过
        int isSuccessSend = 0;
        try {
            isSuccessSend = (Integer)session.getAttribute("isSuccessSend&" + email);
        } catch (NullPointerException e) {
            e.printStackTrace();
            isSuccessSend = 0;
        }

        if (isSuccessSend == 0) {
            //验证码没有发送成功
            String json = packJson(response.getStatus(),"验证码发送失败",email,"",0,0);
            response.getWriter().write(json);
            return;
        }

        //能运行到这里，说明验证码发送成功过
        String json = packJson(response.getStatus(), "验证码发送成功", email, send_yzm, 1, 1);

        //发送json
        response.getWriter().write(json);

    }
    private String packJson(int code,String message,String email,String verCode,int isVerCode,int isSend) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("uname",email);
        mapThree.put("verCode",verCode);

        //验证码是否正确
        mapThree.put("isVerCode",isVerCode);

        //是否发送过验证码
        mapThree.put("isSend",isSend);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
