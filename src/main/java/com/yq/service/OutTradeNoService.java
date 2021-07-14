package com.yq.service;

import com.yq.domain.OutTradeInfo;
import com.yq.domain.OutTradeNo;

import java.util.List;
import java.util.Map;

public interface OutTradeNoService {

    int insertOutTradeNoService(OutTradeNo userOutTradeNo);


    /**
     * 根据订单号查询订单，返回的是整个订单对象
     * @author chuchen
     * @date 2021/4/3 18:43
     * @return List<Map<OutTradeNo,String>>
     */
    List<Map<String, Object>> selectOutTradeNoAllService();

    List<Map<String, Object>> selectOutTradeAllService(
            int refund,String username,String startTime,String endTime,int startPage,int pageSize);

    /**
     * 用于退款操作，根据订单号查询交易金额
     * @author chuchen
     * @date 2021/4/5 9:16
     * @param outTradeNo outTradeNo
     * @return List<Map<String,Object>>
     */
    String selectTradeNoStrService(String outTradeNo);

    /**
     * 用户付款时，根据随机生成的订单号进行查询，返回查询到的条数
     *
     * @author chuchen
     * @date 2021/4/3 18:50
     * @param outTradeNo outTradeNo
     * @return int 订单在数据库中的条数，只能是0或者1
     */
    int selectOutTradeNoService(String outTradeNo);

    /**
     * 根据订单号删除订单，订单号只会出现一个
     * @author chuchen
     * @date 2021/4/3 18:52
     * @param outTrade outTrade
     * @return int
     */
    int deleteOutTradeNoService(String outTrade);

    /**
     * 根据订单号修改订单
     * @author chuchen
     * @date 2021/4/3 18:54
     * @param outTrade outTrade
     * @return int
     */
    int updateOutTradeNoService(String outTrade);

}
