package com.yq.web.servlet.alipayservlet.donate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.OutTradeNoService;
import com.yq.service.impl.OutTradeServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ajax请求，返回所有用户的捐款金额，省份，时间
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/4 18:07
 **/


@WebServlet("/dlist")
public class DonateLists extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        OutTradeNoService service = new OutTradeServiceImpl();

        //调用service层
        List<Map<String, Object>> mapList = service.selectOutTradeNoAllService();

        //获取返回结果集
        String json = packJson(response.getStatus(), "success", mapList);

        //返回
        response.getWriter().write(json);

    }

    private String packJson(int code,String message,List<Map<String, Object>> mapList) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        //验证码是否正确

        //是否发送过验证码
        mapThree.put("donateList",mapList);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
