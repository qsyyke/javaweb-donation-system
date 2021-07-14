package com.yq.dao;

import com.yq.domain.OutTradeNo;

import java.util.List;
import java.util.Map;

public interface OutTradeNoInfoDao {
    /**
     * 插入订单号
     * @author chuchen
     * @date 2021/4/3 18:22
     * @param outTradeNo outTradeNo  订单号
     * @return int 插入的条数
     */
    int insertOutTradeNoInfoDao(String username,String email,
                            String wishGoods,String userProvince,String donateProvince,String outTradeNo,String userAddress,String donateAddress);


    /**
     * 根据订单号查询订单，返回的是整个订单对象
     * @author chuchen
     * @date 2021/4/3 18:43
     * @param outTradeNo outTradeNo
     * @return List<Map<OutTradeNo,String>>
     */
    List<Map<String,Object>> selectOutTradeNoInfoAllDao(String outTradeNo);

    List<Map<String,Object>> selectOutTradeNoInfoAllDao();

    List<Map<String,Object>> selectOutTradeGoodsInfoDao();



    /**
     * 用户付款时，根据随机生成的订单号进行查询，返回查询到的条数
     *
     * @author chuchen
     * @date 2021/4/3 18:50
     * @param outTradeNo outTradeNo
     * @return int 订单在数据库中的条数，只能是0或者1
     */
    List<Map<String,Object>> selectOutTradeNoInfoDao(String outTradeNo);

    /**
     * 根据订单号删除订单，订单号只会出现一个
     * @author chuchen
     * @date 2021/4/3 18:52
     * @param outTrade outTrade
     * @return int
     */
    int deleteOutTradeNoInfoDao(String outTrade);

    /**
     * 根据订单号修改订单
     * @author chuchen
     * @date 2021/4/3 18:54
     * @param outTrade outTrade
     * @return int
     */
    int updateOutTradeNoInfoDao(String outTrade);



    int updateUserDao(String newUsername,String oldUsername,String email);
    int updateAddressDao(String outTradeNo,String address);

    int updateUserDao(String user);
}
