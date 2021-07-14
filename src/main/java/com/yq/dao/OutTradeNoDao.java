package com.yq.dao;

import java.util.List;
import java.util.Map;

public interface OutTradeNoDao {
    /**
     * 插入订单号
     * @author chuchen
     * @date 2021/4/3 18:22
     * @param outTradeNo outTradeNo  订单号
     * @return int 插入的条数
     */
    int insertOutTradeNoDao(String outTradeNo,String radeNo,String createDate,String tradeStatus,double tradeMoney,String tradeCount,String subject);


    /**
     * 根据订单号查询订单，返回的是整个订单对象
     * @author chuchen
     * @date 2021/4/3 18:43
     * @return List<Map<OutTradeNo,String>>
     */
    List<Map<String, Object>> selectOutTradeNoAllDao();

    /**
     *
     * @author chuchen
     * @date 2021/4/28 11:54
     * @return List<Map<String,Object>>
     */
    List<Map<String, Object>> selectOutTradeAllDao(String sql,String sqlAll,int startPage,int pageSize);

    /**
     * 用于退款操作，根据商户订单号查询交易金额
     * @author chuchen
     * @date 2021/4/5 9:17
     * @param outTradeNo outTradeNo
     * @return List<Map<String,Object>>
     */
    String selectOutTradeNoTradeNoDao(String outTradeNo);

    /**
     * 用户付款时，根据随机生成的订单号进行查询，返回查询到的条数
     *
     * @author chuchen
     * @date 2021/4/3 18:50
     * @param outTradeNo outTradeNo
     * @return int 订单在数据库中的条数，只能是0或者1
     */
    int selectOutTradeNoDao(String outTradeNo);

    /**
     * 根据订单号删除订单，订单号只会出现一个
     * @author chuchen
     * @date 2021/4/3 18:52
     * @param outTrade outTrade
     * @return int
     */
    int deleteOutTradeNoDao(String outTrade);

    /**
     * 根据订单号修改订单
     * @author chuchen
     * @date 2021/4/3 18:54
     * @param outTrade outTrade
     * @return int
     */
    int updateOutTradeNoDao(String outTrade);
}
