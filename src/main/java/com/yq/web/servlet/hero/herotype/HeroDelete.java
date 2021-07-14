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
import java.util.Map;

@WebServlet("/herotypedelete")
public class HeroDelete extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //类型名字
        String pTypeName = request.getParameter("pType");
        if (pTypeName == null || "".equals(pTypeName)) {
            String json = packJson(response.getStatus(),"parameter is empty", pTypeName,0);
            response.getWriter().write(json);
            return;
        }

        //不为空或者null
        HeroTypeService service = new HeroTypeImplService();
        int delete = service.deleteProfessionTypeSer(pTypeName);

        if (delete != 1) {
            String json = packJson(response.getStatus(),"delete is fail", pTypeName,delete);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(),"success", pTypeName,delete);
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, String pTypeName,int delete) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("pTypeName",pTypeName);
        mapThree.put("delete",delete);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
