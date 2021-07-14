package com.yq.web.servlet.news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.dao.NewsDao;
import com.yq.dao.impl.NewsDaoImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/deletenews")
public class DeleteNews extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String title = request.getParameter("title");

        if (title == null || "".equals(title)) {
            String json = packJson(response.getStatus(), "title is empty", 0);
            response.getWriter().write(json);
            return;
        }

        NewsDao dao = new NewsDaoImpl();
        int update = dao.deleteNews(title);

        String msg = "success";

        if (update == 0) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, update);
        response.getWriter().write(json);

    }

    private String packJson(int code, String message,int update) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("update",update);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
