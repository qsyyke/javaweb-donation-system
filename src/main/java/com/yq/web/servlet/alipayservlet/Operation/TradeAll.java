package com.yq.web.servlet.alipayservlet.Operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.domain.PageProperties;
import com.yq.service.OutTradeNoService;
import com.yq.service.UserService;
import com.yq.service.impl.OutTradeServiceImpl;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 根据参数是否退款返回订单数据
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/28 11:46
 **/


@WebServlet("/tall")
public class TradeAll extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Properties pro = PageProperties.getPro();

        int pageSize = Integer.parseInt((String) pro.get("pageSize"));

        //是否退款
        String isRefundStr = request.getParameter("isRefund");
        String username = request.getParameter("username");

        //当前页码
        String pageNumStr = request.getParameter("pageNum");

        //订单开始时间
        String startTime = request.getParameter("startTime");
        //订单结束时间
        String endTime = request.getParameter("endTime");

        if (username == null) {
            username = "";
        }
        if (startTime == null && endTime == null) {
            startTime = "";
            endTime = "";
        }

        int isRefund = 0;
        try {
            isRefund = Integer.parseInt(isRefundStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isRefund = 0;
        }
        OutTradeNoService tradeNoService = new OutTradeServiceImpl();

        int pageNum = 0;

        if (pageNumStr == null || "".equals(pageNumStr)) {
            //默认从0开始
            pageNum = 1;
        }else {
            try {
                pageNum = Integer.parseInt(pageNumStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pageNum = 1;
            }
        }

        Page<Map<String,String>> objects = PageHelper.startPage(pageNum, pageSize);
        String s = objects.toString();
        String[] split = s.split(",");

        //开始页数
        int startRow = Integer.parseInt(split[3].split("=")[1]);

        //查询结果
        List<Map<String, Object>> list = tradeNoService.selectOutTradeAllService(isRefund, username, startTime, endTime, startRow, pageSize);
        int totalCount = 0;

        for (Map<String, Object> map : list) {
            if (map.get("totalCount") != null) {
                totalCount = (Integer) map.get("totalCount");
            }
        }

        //总记录条数
        //int totalSize = listAll.size();

        //总页面
        int totalPage = 0;
        if (totalCount % pageSize  == 0) {
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize +1;
        }
        String json = packJson(response.getStatus(), "success", list, totalPage, list.size(), totalCount, pageSize, pageNum);
        response.getWriter().write(json);

    }

    private String packJson(int code, String message, List<Map<String, Object>> list,
                            int totalPage, int recordCount, int totalCount,
                            int pageSize, int pageNo) throws JsonProcessingException {
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
        mapTwo.put("totalPage",totalPage);
        mapTwo.put("recordCount",recordCount);
        mapTwo.put("totalCount",totalCount);
        mapTwo.put("pageSize",pageSize);
        mapTwo.put("pageNo",pageNo);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
