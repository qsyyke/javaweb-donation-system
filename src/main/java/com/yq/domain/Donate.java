package com.yq.domain;

public class Donate {
    /**
     *@decript: 此类用于用户捐款信息记录
     */
    //捐赠目的地
    private String donateDis;
    //物资
    private String goods;
    //祝福
    private String wish;

    public Donate () {
    }

    public Donate (String donateDis, String goods, String wish) {
        this.donateDis = donateDis;
        this.goods = goods;
        this.wish = wish;
    }

    public String getDonateDis () {
        return donateDis;
    }

    public void setDonateDis (String donateDis) {
        this.donateDis = donateDis;
    }

    public String getGoods () {
        return goods;
    }

    public void setGoods (String goods) {
        this.goods = goods;
    }

    public String getWish () {
        return wish;
    }

    public void setWish (String wish) {
        this.wish = wish;
    }

    @Override
    public String toString () {
        return "Donate{" + "donateDis='" + donateDis + '\'' + ", goods='" + goods + '\'' + ", wish='" + wish + '\'' + '}';
    }
}
