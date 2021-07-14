package com.yq.web.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.DomainProperties;
import com.yq.domain.User;
import com.yq.service.UserService;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@WebServlet("/iu")
public class InsertUser extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Properties pro = DomainProperties.getPro();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String isAdmin = request.getParameter("isAdmin");

        if (username == null || email == null || "".equals(username) || "".equals(email)) {
            String json = packJson(response.getStatus(), "参数错误", 0);
            response.getWriter().write(json);
            return;
        }

        //根据邮箱判断数据库中是否存在
        UserService userService = new UserServiceImpl();
        List<Map<String, Object>> list = userService.usernameByEmailSer(email);

        if (list.size() != 0) {
            String json = packJson(response.getStatus(), "用户已存在", 0);
            response.getWriter().write(json);
            return;
        }
        //能运行到这里，说明参数正确
        int admin = 0;
        try {
            admin = Integer.parseInt(isAdmin);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            admin = 0;
        }


        if (password == null || "".equals(password)) {
            password = "fskdjhkxjhsdfhuksudhfjkbdfhsjdfh";
        }

        User user  = new User(username,email,address,password,admin);
        int insertUserInfoSer = userService.insertUserInfoSer(user);
        if (insertUserInfoSer == 0) {
            String json = packJson(response.getStatus(), "用户插入失败", 0);
            response.getWriter().write(json);
            return;
        }

        //将用户名插入到cookie中
        String domain = (String) pro.get("domain");
        int maxAge = Integer.parseInt((String) pro.get("maxAge"));
        String path = (String) pro.get("path");

        Cookie cookie = new Cookie("username",username);
        cookie.setPath(path);
        cookie.setDomain(domain);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);

        String json = packJson(response.getStatus(), "用户插入成功", 1);
        response.getWriter().write(json);
    }

    private String packJson(int code,String message,int isSuccess) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("isExists",isSuccess);


        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
