package com.yq.web.servlet.hero.herotype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.HeroTypeService;
import com.yq.service.impl.HeroTypeImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回抗疫英雄栏目的模块，返回栏目名称，编号，每个栏目下面的个数，还有每个栏目下面有哪些人物
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/28 23:13
 **/

@WebServlet("/herotypes")
public class HeroTypeAll extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        HeroTypeService service = new HeroTypeImplService();
        List<Map<String, Object>> mapList = service.selectAllProfessionTypeSer();

        String json = packJson(response.getStatus(), "success", mapList);
        response.getWriter().write(json);
    }

    private String packJson(int code,String message,List<Map<String, Object>> list) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("list",list);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
