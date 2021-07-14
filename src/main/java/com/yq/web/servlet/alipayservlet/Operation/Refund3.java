package com.yq.web.servlet.alipayservlet.Operation;

import com.yq.alipay.trade.config.Configs;
import com.yq.alipay.trade.model.builder.AlipayTradeRefundRequestBuilder;
import com.yq.alipay.trade.model.result.AlipayF2FRefundResult;
import com.yq.alipay.trade.service.AlipayTradeService;
import com.yq.alipay.trade.service.impl.AlipayTradeServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/refundS")
public class Refund3 extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Log log = LogFactory.getLog("trade_refund");

        if(request.getParameter("outTradeNo")!=null){
            // 一定要在创建AlipayTradeService之前设置参数
            Configs.init("zfbinfo.properties");
            AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

            // (必填) 外部订单号，需要退款交易的商户外部订单号
            String outTradeNo = request.getParameter("outTradeNo");

            // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
            String refundAmount = request.getParameter("refundAmount");

            // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
            // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
            String outRequestNo = request.getParameter("outRequestNo");

            // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
            String refundReason = request.getParameter("refundReason");

            // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
            String storeId = request.getParameter("storeId");

            AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                    .setOutTradeNo(outTradeNo)
                    .setRefundAmount(refundAmount)
                    .setRefundReason(refundReason)
                    .setOutRequestNo(outRequestNo)
                    .setStoreId(storeId);

            AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("支付宝退款成功: )");
                    break;

                case FAILED:
                    log.error("支付宝退款失败!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，订单退款状态未知!!!");
                    break;

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    break;
            }
            response.getWriter().write(result.getResponse().getBody());
            return;
        }
    }
}
