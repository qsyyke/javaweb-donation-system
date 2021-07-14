package com.yq.web.servlet.news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/autonewsstatus")
public class AutoUpdateNewsStatus extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        ServletContext context = request.getServletContext();
        Boolean status = (Boolean)context.getAttribute("isStopSleep");
        String json = packJson("success", status);
        response.getWriter().write(json);

    }

    private String packJson(String message,boolean status) throws JsonProcessingException {
        Map<String,Object> map_one = new HashMap<>();
        Map<String,Object> map_two = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        map_two.put("message",message);
        map_two.put("status",status);
        map_one.put("data",map_two);

        String json = mapper.writeValueAsString(map_one);
        return json;
    }
}
