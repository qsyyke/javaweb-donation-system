package com.yq.web.servlet.index;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.OutTradeInfoService;
import com.yq.service.impl.OutTradeInfoServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此文件用于查询各类用户期望用于的物资数
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/5 19:45
 **/

@WebServlet("/goods")
public class DonateGoods extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        OutTradeInfoService infoService = new OutTradeInfoServiceImpl();
        List<Map<String, Object>> mapList = infoService.selectOutTradeGoodsService();

        Map<String,Object> mapOne = new HashMap<>();
        Map<String,Object> mapTwo = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        mapTwo.put("goodsList",mapList);

        mapOne.put("data",mapTwo);
        mapOne.put("code",response.getStatus());

        String json = mapper.writeValueAsString(mapOne);

        response.getWriter().write(json);
    }
}
