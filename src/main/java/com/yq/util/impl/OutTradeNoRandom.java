package com.yq.util.impl;


import java.util.Random;

/**
 * 调用此类，随机生成一个订单号
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/03 18:59
 **/

public class OutTradeNoRandom {
    /** 订单号 */
    private String outTradeNo = "";

    /** 存放生成随机订单号序列 */
    private String[] randomStr = {"A","B","H","I","J","K","L","M",
        "N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b",
        "c","d","e","k","l","m","n","o","p","q","r",
        "s","t","u","v","w","x","y","z","1","2","3","4","5","6","7","8","9","0"};

    public OutTradeNoRandom() {

    }

    public String getOutTradeNo() {
        Random random = new Random();

        //数组长度
        int length = randomStr.length;

        //订单号的长度64个字符以内
        for (int i = 0; i < 25; i++) {
            int nextInt = random.nextInt(length - 1);
            outTradeNo = outTradeNo + randomStr[nextInt];
        }
        return outTradeNo;
    }
}
