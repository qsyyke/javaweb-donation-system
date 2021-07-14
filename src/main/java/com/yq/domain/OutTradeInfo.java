package com.yq.domain;


/**
 *
 * 用户捐款信息类，包括捐款金额期望用于，省份等等
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/04 09:12
 **/

public class OutTradeInfo {

     /** 商户订单号 */
    private String outTradeNo;

     /** 用户名 */
     private String username;

     /** 邮箱 */
     private String email;

     /** 获取期望捐款金额用于 */
    private String wishGoods;

     /** 获取用户省份 */
    private String userProvince;

     /** 市 */
    private String userCity;

     /** 区 */
    private String userArea;

     /** 获取捐赠到省份 */
    private String donateProvince;

     /** 市 */
    private String donateCity;

     /** 区 */
    private String donateArea;

    public String getOutTradeNo () {
        return outTradeNo;
    }

    public void setOutTradeNo (String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getWishGoods () {
        return wishGoods;
    }

    public void setWishGoods (String wishGoods) {
        this.wishGoods = wishGoods;
    }

    public String getUserProvince () {
        return userProvince;
    }

    public void setUserProvince (String userProvince) {
        this.userProvince = userProvince;
    }

    public String getUserCity () {
        return userCity;
    }

    public void setUserCity (String userCity) {
        this.userCity = userCity;
    }

    public String getUserArea () {
        return userArea;
    }

    public void setUserArea (String userArea) {
        this.userArea = userArea;
    }

    public String getDonateProvince () {
        return donateProvince;
    }

    public void setDonateProvince (String donateProvince) {
        this.donateProvince = donateProvince;
    }

    public String getDonateCity () {
        return donateCity;
    }

    public void setDonateCity (String donateCity) {
        this.donateCity = donateCity;
    }

    public String getDonateArea () {
        return donateArea;
    }

    public void setDonateArea (String donateArea) {
        this.donateArea = donateArea;
    }

    @Override
    public String toString () {
        return "OutTradeInfo{" + "outTradeNo='" + outTradeNo + '\'' + ", username='" + username + '\'' + ", email='" + email + '\'' + ", wishGoods='" + wishGoods + '\'' + ", userProvince='" + userProvince + '\'' + ", userCity='" + userCity + '\'' + ", userArea='" + userArea + '\'' + ", donateProvince='" + donateProvince + '\'' + ", donateCity='" + donateCity + '\'' + ", donateArea='" + donateArea + '\'' + '}';
    }
}
