package com.yq.web.servlet.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.OutTradeInfo;
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
 * 用于返回查询所有捐款人数的个数
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/5 14:06
 **/


@WebServlet("/donatecount")
public class DonateCount extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        OutTradeInfoService infoService = new OutTradeInfoServiceImpl();

        //调用service层
        List<Map<String, Object>> mapList = infoService.selectOutTradeInfoAllService();

        if (mapList.size() == 0) {
            String json = packJson(response.getStatus(), "fail", mapList,0);
            response.getWriter().write(json);
            return;
        }

        //计算所有的捐款金额
        double totalMoney = 0;
        for (int i = 0; i < mapList.size(); i++) {
             totalMoney = totalMoney + (Double)mapList.get(i).get("tradeMoney");
        }

        //里面存在数据
        response.getWriter().write(packJson(response.getStatus(),"success",mapList,totalMoney));

        //返回数据

    }

    private String packJson(int code,String message,List<Map<String, Object>> mapList,double totalMoney) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();


        //是否发送过验证码
        mapThree.put("donateList",mapList);

        mapTwo.put("totalMoney",totalMoney);
        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
