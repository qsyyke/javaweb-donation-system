package com.yq.web.servlet.alipayservlet.donate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.OutTradeInfoService;
import com.yq.service.OutTradeNoService;
import com.yq.service.impl.OutTradeInfoServiceImpl;
import com.yq.service.impl.OutTradeServiceImpl;
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

/**
 * 这个一个个人捐款的展示请求，如果用户是刚付完款，那么就会到这个页面，通过<p></p>
 * servletContext.setAttribute("time@"+time,outTradeNo);获取到订单编号<p></p>
 * 这里会进行判断，如果是通过时间time获取的，那么就使用"time@"+time获取订单号，从数据库中进行查找<p></p>
 * 如果是使用订单编号进行查找的，那么首先会使用这个订单编号从数据库中判断是否存在，如果存在的话，那么就获取捐款信息<p></p>
 *
 * 如果这个订单存在，那么就会返回json信息，前端通过这个json进行用户信息的添加
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/6 13:57
 **/


@WebServlet("/pershow")
public class PerShow extends HttpServlet {

     /** service层 */
     private OutTradeInfoService infoService = new OutTradeInfoServiceImpl();

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        System.out.println("请求了");

        //判断是通过时间time还是通过订单编号outTradeNo进行获取的

        //获取时间
        String time = request.getParameter("time");

        //获取订单
        String selectOutTradeNo = request.getParameter("outTradeNo");

        //获取邮箱
        String email = request.getParameter("email");

        //时间 获取订单 调用方法查询数据库信息
        if (time != null) {
            //获取servletContext
            String outTradeNo = (String)request.getServletContext().getAttribute("time@" + time);

            List<Map<String, Object>> mapList = selectAllBaseTradeNo(outTradeNo);

            String message = "fail";
            if (mapList.size() != 0) {
                //存在该条记录
                message = "success";
            }
            String json = packJson(response.getStatus(), message, mapList);
            response.getWriter().write(json);
            return;
        }

        //能运行到这里，说明用户是根据订单进行查询或者是参数不是time或者是outTradeNo
        if (selectOutTradeNo != null) {
            List<Map<String, Object>> mapList = selectAllBaseTradeNo(selectOutTradeNo);
            String message = "fail";
            if (mapList.size() != 0) {
                //存在该条记录
                message = "success";
            }
            String json = packJson(response.getStatus(), message, mapList);
            response.getWriter().write(json);
            return;
        }

        //如果能够运行到这里，说明用户可能使用邮箱进行查询
        if (email != null) {
            String sql = "SELECT email email,username name,donateProvince province,outtradeno.outTradeno tradeNo," +
                    "createDate date,wishGoods goods,tradeMoney money,tradeCount count ,count(DISTINCT outtradeno.outtradeno) c " +
                    "from outtradeno,outtradeinfo " +
                    "WHERE outtradeno.outtradeno = outtradeinfo.outtradeno " +
                    "and email= ? GROUP BY outtradeinfo.outtradeno";
            JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

            List<Map<String, Object>> lists = template.queryForList(sql, email);
            String message = "fail";
            if (lists.size() != 0) {
                //存在该条记录
                message = "success";
            }
            String json = packJson(response.getStatus(), message, lists);
            response.getWriter().write(json);
            return;
        }

        //如果还能运行到这里，说明用户不是通过time或者是商户订单进行查询
        String json = packJson(response.getStatus(), "订单未存在", new ArrayList<>());
        response.getWriter().write(json);

    }

    private List<Map<String,Object>> selectAllBaseTradeNo(String outTradeNo) {
        List<Map<String, Object>> mapList = infoService.selectOutTradeInfoAllService(outTradeNo);
        return mapList;
    }

    private String packJson(int code,String message,List<Map<String, Object>> mapList) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();


        //是否发送过验证码

        mapTwo.put("tradeList",mapList);

        mapTwo.put("message",message);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
