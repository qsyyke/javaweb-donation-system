package com.yq.web.servlet.hero.herotype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/selectbysql")
public class SelectBySql extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String sqlCount = "SELECT COUNT(*) count,ptype FROM `hero` GROUP BY ptype";

        String sqlTypes = "select * from professiontype";

        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
        List<Map<String, Object>> typesList = template.queryForList(sqlTypes);

        List<Map<String, Object>> maps = template.queryForList(sqlCount);
        List queryForList = new ArrayList<>();

        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            Integer pNo = (Integer)map.get("ptype");

            String sqlType = "SELECT heroname,hero.ptype,professiontype.ptype FROM `hero`,professiontype where hero.ptype="+pNo+" and professiontype.pno = hero.ptype";
            List<Map<String, Object>> list = template.queryForList(sqlType);
            Map<String, Object> pNoMap = new HashMap<>();
            pNoMap.put("typeNo",pNo);
            list.add(pNoMap);
            queryForList.add(list);
        }

        String json = packJson(response.getStatus(), "success", maps, queryForList,typesList);
        response.getWriter().write(json);


    }

    private String packJson(int code,String message,List<Map<String, Object>> list,List queryForList,List<Map<String, Object>> typesList) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("countList",list);
        mapThree.put("queryForList",queryForList);
        mapThree.put("typesList",typesList);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
