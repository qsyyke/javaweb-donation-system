package com.yq.web.servlet.alipayservlet.Operation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.alipay.trade.config.Configs;
import com.yq.alipay.trade.model.TradeStatus;
import com.yq.alipay.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.yq.alipay.trade.model.result.AlipayF2FQueryResult;
import com.yq.alipay.trade.service.AlipayTradeService;
import com.yq.alipay.trade.service.impl.AlipayTradeServiceImpl;
import com.yq.domain.OutTradeInfo;
import com.yq.domain.OutTradeNo;
import com.yq.service.OutTradeInfoService;
import com.yq.service.OutTradeNoService;
import com.yq.service.impl.OutTradeInfoServiceImpl;
import com.yq.service.impl.OutTradeServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户扫码支付成功之后，支护宝异步通知页面
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/4 7:47
 **/


@WebServlet("/notify")
public class Notify extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Configs.init("zfbinfo.properties");

        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        Map<String, String[]> parameterMap = request.getParameterMap();
        String out_trade_no = parameterMap.get("out_trade_no")[0];

        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(out_trade_no);
        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        TradeStatus tradeStatus = result.getTradeStatus();
        String name = tradeStatus.name();

        if (!name.equals("SUCCESS")) {
            //用户没有支付完成
            String json = packJson(response.getStatus(), "订单未支付", 0, 0);
            response.getWriter().write(json);
            return;
        }

        //订单对象
        OutTradeNo userOutTradeNo = new OutTradeNo();

        //service对象
        OutTradeNoService tradeNoService = new OutTradeServiceImpl();
        OutTradeInfoService infoService = new OutTradeInfoServiceImpl();

        //这个map对象用来获取商户订单号
        //没有参数
        if (parameterMap.size() == 0) {
            String json = packJson(response.getStatus(), "没有参数", 0, 0);
            response.getWriter().write(json);
            return;
        }

        List<Map<String, Object>> list = infoService.selectOutTradeNoInfo(out_trade_no);
        if (list.size() == 1) {
            //数据库中存在数据
            System.out.println("数据库中已经存在数据");
            return;
        }

        //获取参数对象
        ServletContext servletContext = request.getServletContext();

        //用户捐赠信息对象
        OutTradeInfo userOutTradeInfo = (OutTradeInfo)servletContext.getAttribute(out_trade_no);

        userOutTradeNo.setOutTradeNo(request.getParameter("out_trade_no"));
        userOutTradeNo.setSubject(request.getParameter("subject"));
        userOutTradeNo.setTradeNo(request.getParameter("trade_no"));
        userOutTradeNo.setTradeCount(request.getParameter("buyer_logon_id"));
        userOutTradeNo.setCreateDate(request.getParameter("gmt_create"));
        userOutTradeNo.setTradeMoney(Double.parseDouble(request.getParameter("receipt_amount")));
        userOutTradeNo.setTradeStatus(request.getParameter("trade_status"));

        userOutTradeInfo.setOutTradeNo(userOutTradeNo.getOutTradeNo());

        //没有订单号
        if (userOutTradeNo.getOutTradeNo() == null) {
            String json = packJson(response.getStatus(), "参数不合法", 0, 0);
            response.getWriter().write(json);
            return;
        }

        int insertNumber = tradeNoService.insertOutTradeNoService(userOutTradeNo);

        int insertInfoNumber = infoService.insertOutTradeInfoService(userOutTradeInfo);

        if (insertNumber == 0) {
            //插入失败
            String json = packJson(response.getStatus(), "订单插入失败", insertNumber, userOutTradeNo.getTradeMoney());
            response.getWriter().write(json);
            return;
        }

        if (insertInfoNumber == 0) {
            //插入失败
            String json = packJson(response.getStatus(), "订单捐款信息插入失败", insertInfoNumber, userOutTradeNo.getTradeMoney());
            response.getWriter().write(json);
            return;
        }

        //插入成功
        String json = packJson(response.getStatus(), "insert success", insertNumber, userOutTradeNo.getTradeMoney());
        response.getWriter().write(json);
    }

    /**
     *
     * @author chuchen
     * @date 2021/4/4 8:00
     * @param code code
     * @param message message
     * @param insertNumber insertNumber 插入数据库的条数
     * @param payAmount payAmount 买家实付金额
     * @return String
     */
    private String packJson(int code,String message,int insertNumber,double payAmount) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();


        //买家实付金额
        mapThree.put("insertNumber",insertNumber);

        //插入数据库条数
        mapThree.put("payAmount",payAmount);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
