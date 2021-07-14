package com.yq.web.servlet.cookie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.DomainProperties;
import com.yq.util.impl.OutTradeNoRandom;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@WebServlet("/addcookie")
public class AddCookie extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Properties pro = DomainProperties.getPro();

        String domain = (String) pro.get("domain");
        int maxAge = Integer.parseInt((String) pro.get("maxAge"));
        String path = (String) pro.get("path");

        //admin 为1 表示访问后台数据，为0表示访问前台数据
        String adminStr = request.getParameter("admin");

        int admin = 0;

        Cookie cookieAdmin = new Cookie("admin","0");


        cookieAdmin.setDomain(domain);
        cookieAdmin.setPath(path);
        cookieAdmin.setMaxAge(maxAge);

        try {
            admin = Integer.parseInt(adminStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            response.addCookie(cookieAdmin);
            //表示操作前台数据
            String json = packJson(response.getStatus(), "success", "");
            response.getWriter().write(json);
            return;
        }

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            //没有cookie 添加cookie
            if (admin == 1) {
                cookieAdmin.setValue("1");
            }

            String json = packJson(response.getStatus(), "success", "");
            response.getWriter().write(json);

            response.addCookie(cookieAdmin);
            return;
        }

        //cookie不为空，获取admin taken  flag true表示存在taken，admin
        boolean flagAdmin = false;

        String cookieGetAdmin = "";
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if ("admin".equals(cookieName)) {
                flagAdmin = true;
                cookieGetAdmin = cookie.getValue();
            }
        }

        //存在cookie
        //判断是操作前台还是操作后台 能运行到这里，说明没有添加到cookie
        if (!flagAdmin) {
            //存在cookie，但是cookie中没有admin
            cookieAdmin.setValue(adminStr);
            response.addCookie(cookieAdmin);

            String json = packJson(response.getStatus(), "success",adminStr);
            response.getWriter().write(json);
            return;
        }
    }

    private String packJson(int code, String message,String admin) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("admin",admin);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
