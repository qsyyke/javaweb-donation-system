package com.yq.web.servlet.alipayservlet.Operation;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.yq.alipay.trade.config.Configs;
import com.yq.alipay.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.yq.alipay.trade.model.result.AlipayF2FQueryResult;
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

@WebServlet("/QueryUser")
public class Query extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log log = LogFactory.getLog("trade_query");


        if(request.getParameter("outTradeNo")!=null){
            // 一定要在创建AlipayTradeService之前设置参数
            Configs.init("zfbinfo.properties");

            AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

            // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
            String outTradeNo = request.getParameter("outTradeNo");
            AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                    .setOutTradeNo(outTradeNo);
            AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("查询返回该订单支付成功: )");

                    AlipayTradeQueryResponse resp = result.getResponse();

                    log.info(resp.getTradeStatus());
                    log.info(resp.getFundBillList());
                    break;

                case FAILED:
                    log.error("查询返回该订单支付失败!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，订单支付状态未知!!!");
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
