package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/herocommentallbysql")
public class HeroCommentAllCountBySql extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String sql = "SELECT * from heromessage";
        HeroService service = new HeroImplService();
        List<Map<String, Object>> maps = service.selectBySqlSer(sql);

        if (maps.size() == 0) {
            return;
        }

        String json = packJson(response.getStatus(), "success", maps, maps.size());
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, List<Map<String, Object>> list,int totalCount) throws JsonProcessingException {
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
        mapTwo.put("totalCount",totalCount);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
