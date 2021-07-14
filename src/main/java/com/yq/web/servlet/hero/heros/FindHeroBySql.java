package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/findherobysql")
public class FindHeroBySql extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String identify = request.getParameter("identify");

        if (identify == null || "".equals(identify)) {
            String json = packJson(response.getStatus(), "success", new ArrayList<>());
            response.getWriter().write(json);
            return;
        }
        String sql = "SELECT * FROM `herohonor`,hero where hero.identify = herohonor.identify and hero.identify='"+identify+"'";
        HeroService service = new HeroImplService();
        List<Map<String, Object>> maps = service.selectBySqlSer(sql);

        String json = packJson(response.getStatus(), "success", maps);
        response.getWriter().write(json);
        return;
    }

    private String packJson(int code, String message, List<Map<String, Object>> list) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("userList",list);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
