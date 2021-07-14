package com.yq.web.servlet.alipayservlet.Operation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.alipay.trade.config.Configs;
import com.yq.alipay.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.yq.alipay.trade.model.result.AlipayF2FRefundResult;
import com.yq.alipay.trade.service.AlipayTradeService;
import com.yq.alipay.trade.service.impl.AlipayTradeServiceImpl;
import com.yq.service.OutTradeNoService;
import com.yq.service.impl.OutTradeServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;


/**
 * 用于支付宝的退款
 *
 * 参数为商户订单号outTradeNo
 * 一定要
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/4 21:30
 **/

@WebServlet("/refund")
public class Refund extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Log log = LogFactory.getLog("trade_refund");

        if(request.getParameter("outTradeNo")!=null){
            // 一定要在创建AlipayTradeService之前设置参数
            Configs.init("zfbinfo.properties");
            AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

            // (必填) 外部订单号，需要退款交易的商户外部订单号
            String outTradeNo = request.getParameter("outTradeNo");

            OutTradeNoService service = new OutTradeServiceImpl();

            //返回的是订单金额 如果没有 则返回空""
            String moneyStr = service.selectTradeNoStrService(outTradeNo);
            double tradeMoney = 0;
            if ("".equals(moneyStr)) {
                //空，没有这个订单号
                tradeMoney = 0;
            }else {
                tradeMoney = Double.parseDouble(moneyStr);
            }
            
            // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
            String refundAmount = tradeMoney+"";

            // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
            String refundReason = "感谢你的捐款";

            // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
            //String storeId = request.getParameter("storeId");
            String storeId = "3987y34sdj89389";

            AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                    .setOutTradeNo(outTradeNo)
                    .setRefundAmount(refundAmount)
                    .setRefundReason(refundReason)
                    .setStoreId(storeId);

            AlipayF2FRefundResult result = tradeService.tradeRefund(builder);

            int updateNumber = 0;
            String tradeStatus = result.getTradeStatus().name();
            if ("SUCCESS".equals(tradeStatus)) {
                //退款成功
                log.info("支付宝退款成功: )");
                updateNumber = service.updateOutTradeNoService(outTradeNo);
            }

            //支付宝返回的json body
            String body = result.getResponse().getBody();

            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> map_json = mapper.readValue(body, Map.class);

            map_json.put("isRefund",updateNumber);
            String json = mapper.writeValueAsString(map_json);
            response.getWriter().write(json);

        }
    }

}
