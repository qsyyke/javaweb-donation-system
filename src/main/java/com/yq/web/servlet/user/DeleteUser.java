package com.yq.web.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.UserService;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除用户的信息，根据邮箱进行删除
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/24 16:54
 **/

@WebServlet("/deluser")
public class DeleteUser extends HttpServlet {
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
            String json = packJson(response.getStatus(), "fail", 0);
            response.getWriter().write(json);
            return;
        }

        UserService userService = new UserServiceImpl();
        int i = userService.deleteUsernameSer(email);
        if (i == 0) {
            String json = packJson(response.getStatus(), "fail", 0);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "success", 1);
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
