package com.yq.service;

import com.yq.domain.OutTradeInfo;
import com.yq.domain.OutTradeNo;

import java.util.List;
import java.util.Map;

public interface OutTradeInfoService {
    int insertOutTradeInfoService( OutTradeInfo userOutTradeInfo);

    /**
     * 查询所有的
     * @author chuchen
     * @date 2021/4/5 14:11
     * @return List<Map<OutTradeNo,String>>
     */
    List<Map<String,Object>> selectOutTradeInfoAllService();

    /**
     * 根据订单号查询所有的数据
     * @author chuchen
     * @date 2021/4/5 14:12
     * @param outTradeNo outTradeNo
     * @return List<Map<OutTradeNo,String>>
     */
    List<Map<String,Object>>selectOutTradeInfoAllService(String outTradeNo);

    List<Map<String,Object>> selectOutTradeGoodsService();
    List<Map<String, Object>> selectOutTradeNoInfo(String outTradeNo);

    /**
     * 根据订单号删除订单，订单号只会出现一个
     * @author chuchen
     * @date 2021/4/3 18:52
     * @param outTrade outTrade
     * @return int
     */
    int deleteOutTradeInfoService(String outTrade);

    /**
     * 根据订单号修改订单
     * @author chuchen
     * @date 2021/4/3 18:54
     * @param outTrade outTrade
     * @return int
     */
    int updateOutTradeInfoService(String outTrade);

    /**
     * 修改订单中的用户名，根据用户的旧的用户名进行修改
     * @author chuchen
     * @date 2021/4/11 8:13
     * @param newUsername newUsername 新的用户名
     * @param oldUsername oldUsername  旧的用户名
     * @return int
     */
    int updateUserSer(String newUsername,String oldUsername,String email);
    //int updateUserSer(String newUsername,String oldUsername,String email,String donateAddress,String outTradeNo);

    int updateAddressSer(String outTradeNo,String address);
    int updateUserSer(String user);
}
