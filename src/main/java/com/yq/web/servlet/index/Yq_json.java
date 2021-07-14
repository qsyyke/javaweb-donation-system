package com.yq.web.servlet.index;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.api.AliApi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/yq_json")
public class Yq_json extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        //response.setContentType("text/html;charset=utf-8");
        //获取阿里疫情json
        AliApi aliApi = new AliApi();
        String apiJson = aliApi.getJson();
        response.getWriter().write(apiJson);
    }
}
