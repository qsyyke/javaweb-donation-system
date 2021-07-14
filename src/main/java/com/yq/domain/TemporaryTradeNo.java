package com.yq.domain;


/**
 * 临时存储用户的订单信息，这种情况针对于，没有设置好，后台没有接收到支付宝异步通知的情况
 *
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/05 08:39
 **/

public class TemporaryTradeNo {

     /** 订单时间 */
    private String gmt_create;

     /** 用户在支付宝中实付金额 */
    private String buyer_pay_amount;

     /** 支付宝订单号 */
    private String trade_no;

     /** 商户订单号 */
    private String out_trade_no;

    public String getGmt_create () {
        return gmt_create;
    }

    public void setGmt_create (String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getBuyer_pay_amount () {
        return buyer_pay_amount;
    }

    public void setBuyer_pay_amount (String buyer_pay_amount) {
        this.buyer_pay_amount = buyer_pay_amount;
    }

    public String getTrade_no () {
        return trade_no;
    }

    public void setTrade_no (String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no () {
        return out_trade_no;
    }

    public void setOut_trade_no (String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    @Override
    public String toString () {
        return "TemporaryTradeNo{" + "gmt_create='" + gmt_create + '\'' + ", buyer_pay_amount='" + buyer_pay_amount + '\'' + ", trade_no='" + trade_no + '\'' + ", out_trade_no='" + out_trade_no + '\'' + '}';
    }
}
