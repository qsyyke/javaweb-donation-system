package com.yq.domain;


import java.util.Date;

/**
 * 订单号类 这是一个父类
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/03 18:23
 **/

public class OutTradeNo {
     /** 订单创建时间 */
     private String createDate;

     /** 订单号 */
     private String outTradeNo;

     /** 订单金额 */
     private double tradeMoney;

     /** 是否退款过 */
     private boolean isRefund;

     /** 用户账号 */
     private String tradeCount;

     /** 支付宝订单号 */
     private String tradeNo;

     /** 订单标题subject */
     private String subject;

     /** 交易状态trade_status */
     private String tradeStatus;

    public OutTradeNo () {
    }

    public OutTradeNo (String createDate, String tradeNo, double tradeMoney, String tradeCount) {
        this.createDate = createDate;
        this.tradeNo = tradeNo;
        this.tradeMoney = tradeMoney;
        this.tradeCount = tradeCount;
    }

    public String getCreateDate () {
        return createDate;
    }

    public void setCreateDate (String createDate) {
        this.createDate = createDate;
    }

    public String getOutTradeNo () {
        return outTradeNo;
    }

    public void setOutTradeNo (String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public double getTradeMoney () {
        return tradeMoney;
    }

    public void setTradeMoney (double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public boolean isRefund () {
        return isRefund;
    }

    public void setRefund (boolean refund) {
        isRefund = refund;
    }

    public String getTradeCount () {
        return tradeCount;
    }

    public void setTradeCount (String tradeCount) {
        this.tradeCount = tradeCount;
    }

    public String getTradeNo () {
        return tradeNo;
    }

    public void setTradeNo (String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getSubject () {
        return subject;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public String getTradeStatus () {
        return tradeStatus;
    }

    public void setTradeStatus (String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Override
    public String toString () {
        return "OutTradeNo{" + "createDate='" + createDate + '\'' + ", outTradeNo='" + outTradeNo + '\'' + ", tradeMoney=" + tradeMoney + ", isRefund=" + isRefund + ", tradeCount='" + tradeCount + '\'' + '\'' + ", tradeNo='" + tradeNo + '\'' + ", subject='" + subject + '\'' + ", tradeStatus='" + tradeStatus + '\'' + '}';
    }
}
