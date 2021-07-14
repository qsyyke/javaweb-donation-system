package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Hero;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/describeupdate")
public class DescribeUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String identify = request.getParameter("identify");
        String describe = request.getParameter("describe");

        if (identify == null || "".equals(identify)) {
            String json = packJson(response.getStatus(), "参数不能为空", identify, 0);
            response.getWriter().write(json);
            return;
        }

        if (describe == null || "".equals(describe)) {
            String json = packJson(response.getStatus(), "描述不能为空", identify, 0);
            response.getWriter().write(json);
            return;
        }

        HeroService service = new HeroImplService();
        Hero hero = new Hero();
        hero.setDescribe(describe);
        hero.setUniqueIdentify(identify);
        int updateNum = service.updateDescribeSer(hero);

        if (updateNum != 1) {
            String json = packJson(response.getStatus(), "修改失败", identify, updateNum);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "修改成功", identify, updateNum);
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
