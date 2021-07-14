package com.yq.web.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.UserService;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据邮箱判断用户是否存在 如果存在 返回用户名
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/10 12:03
 **/

@WebServlet("/userexists")
public class UserExists extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String email = request.getParameter("email");

        if (email == null || "".equals(email)) {
            String json = packJson(response.getStatus(), "参数错误", "", new ArrayList<>(), 0);
            response.getWriter().write(json);
            return;
        }

        UserService userService = new UserServiceImpl();
        List<Map<String, Object>> list = userService.usernameByEmailSer(email);

        if (list.size() == 0) {
            String json = packJson(response.getStatus(), "success", email, list, 0);
            response.getWriter().write(json);
            return;
        }

        //如果能够运行到这里，说明查询到有数据
        String json = packJson(response.getStatus(), "success", email, list, 1);
        response.getWriter().write(json);

    }

    private String packJson(int code,String message,String email,List<Map<String, Object>> list,int exists) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("email",email);
        mapThree.put("userList",list);

        mapThree.put("exists",exists);


        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
