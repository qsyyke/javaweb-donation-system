package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 删除人物信息，通过名字进行删除
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/30 23:05
 **/

@WebServlet("/herodelete")
public class HeroDelete extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //唯一标识
        String identify = request.getParameter("identify");

        if (identify == null || "".equals(identify)) {
            String json = packJson(response.getStatus(), "参数不能为空", identify, 0);
            response.getWriter().write(json);
            return;
        }

        HeroService heroService = new HeroImplService();

        int deleteHeroNum = heroService.deleteSer(identify);

        if (deleteHeroNum != 1) {
            String json = packJson(response.getStatus(), "删除失败", identify, deleteHeroNum);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "删除成功", identify, deleteHeroNum);
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, String identify,int delete) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("update",delete);
        mapThree.put("identify",identify);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
