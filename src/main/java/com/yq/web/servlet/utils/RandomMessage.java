package com.yq.web.servlet.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.MessageService;
import com.yq.service.impl.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

/**
 * 从SystemMessage数据库中随机返回一条留言message
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/6 15:43
 **/

@WebServlet("/rm")
public class RandomMessage extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        //调用service层

        MessageService ms = new MessageServiceImpl();

        //封装数据
        List<Map<String, Object>> mapList = ms.randomFromSystemMessageService();

        if (mapList.size() == 0) {
            //没有数据
            response.getWriter().write("请重新刷新...");
            return;
        }

        //有数据

        Random random = new Random();
        Map<String, Object> map = mapList.get(random.nextInt(mapList.size() - 1));

        String message = (String)map.get("message");

        //返回
        response.getWriter().write(message);
    }


}
