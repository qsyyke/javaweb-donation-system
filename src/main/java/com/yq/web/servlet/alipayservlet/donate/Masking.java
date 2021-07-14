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
import java.util.Map;

/**
 * 这是用户扫描完成支付之后，使用ajax进行请求，查看用户的付款状态<p></p>
 * 如果用户已经付款，则在二维码上添加一个蒙版，并且进行跳转<p></p>
 * 通过servletContext进行判断，付款的时候，就已经将时间保存至servletContext对象中，通过ajax的时间值，就可以拿到这个servletContext
 * servletContext.setAttribute("time@"+time,outTradeNo);outTradeNo是商户订单编号
 *
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/4 13:00
 **/


@WebServlet("/mask")
public class Masking extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取时间
        String time = request.getParameter("time");

        //获取servletContext
        String outTradeNo = (String)request.getServletContext().getAttribute("time@" + time);

        //根据商户订单号从数据库中进行查找
        OutTradeNoService tradeNoService = new OutTradeServiceImpl();
        int selectNumber = tradeNoService.selectOutTradeNoService(outTradeNo);

        if (selectNumber == 0) {
            //数据库中没有
            String json = packJson(response.getStatus(), "fail", 0, selectNumber);
            response.getWriter().write(json);
            return;
        }

        //数据库中存在该订单编号
        String json = packJson(response.getStatus(), "success", 1, selectNumber);
        response.getWriter().write(json);

    }

    private String packJson(int code,String message,int isExists,int selectNumber) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();


        //是否发送过验证码
        mapTwo.put("selectNumber",selectNumber);
        mapTwo.put("isExists",isExists);

        mapTwo.put("message",message);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
