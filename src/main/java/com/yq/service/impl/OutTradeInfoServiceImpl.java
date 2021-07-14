package com.yq.service.impl;


import com.yq.dao.OutTradeNoInfoDao;
import com.yq.dao.impl.OutTradeInfoDaoImpl;
import com.yq.domain.OutTradeInfo;
import com.yq.domain.OutTradeNo;
import com.yq.service.OutTradeInfoService;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/04 09:36
 **/

public class OutTradeInfoServiceImpl implements OutTradeInfoService {
    private OutTradeNoInfoDao infoDao = new OutTradeInfoDaoImpl();

    @Override
    public int insertOutTradeInfoService (OutTradeInfo userOutTradeInfo) {
        //用户地址
        String userAddress = "";

        //捐款地址
        String donateAddress = "";


        //订单用户名
        String username = userOutTradeInfo.getUsername();

        //订单邮箱
        String email = userOutTradeInfo.getEmail();

        //获取商户订单号
        String outTradeNo = userOutTradeInfo.getOutTradeNo();

        //获取期望捐款金额用于
        String wishGoods = userOutTradeInfo.getWishGoods();

        //用户省份
        String userProvince = userOutTradeInfo.getDonateProvince();

        if (userOutTradeInfo.getUserCity() == null || userOutTradeInfo.getUserArea() == null ||
                userOutTradeInfo.getUserCity() == "" || userOutTradeInfo.getUserArea() == "") {
            userAddress = userOutTradeInfo.getUserProvince();
        }else {
            userAddress = userOutTradeInfo.getUserProvince()+userOutTradeInfo.getUserCity()+userOutTradeInfo.getUserArea();
        }

        //获取捐赠到省份
        String donateProvince = userOutTradeInfo.getDonateProvince();

        if (userOutTradeInfo.getDonateCity()== null || userOutTradeInfo.getDonateArea() == null ||
                userOutTradeInfo.getDonateCity()== "" || userOutTradeInfo.getDonateArea() == "") {
            donateAddress = userOutTradeInfo.getDonateProvince();
        }else {
            donateAddress = userOutTradeInfo.getDonateProvince()+userOutTradeInfo.getDonateCity()+userOutTradeInfo.getDonateArea();
        }

        int index = infoDao.insertOutTradeNoInfoDao(username, email, wishGoods, userProvince, donateProvince, outTradeNo, userAddress, donateAddress);
        return index;
    }

    @Override
    public List<Map<String,Object>> selectOutTradeInfoAllService (String outTradeNo) {
        List<Map<String, Object>> mapList = infoDao.selectOutTradeNoInfoAllDao(outTradeNo);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeGoodsService () {
        List<Map<String, Object>> mapList = infoDao.selectOutTradeGoodsInfoDao();
        return mapList;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeNoInfo (String outTradeNo) {
        return infoDao.selectOutTradeNoInfoDao(outTradeNo);
    }

    @Override
    public List<Map<String,Object>> selectOutTradeInfoAllService () {
        return infoDao.selectOutTradeNoInfoAllDao();
    }

    @Override
    public int deleteOutTradeInfoService (String outTrade) {
        return 0;
    }

    @Override
    public int updateOutTradeInfoService (String outTrade) {
        return 0;
    }

    @Override
    public int updateUserSer (String newUsername, String oldUsername,String email) {
        return infoDao.updateUserDao(newUsername,oldUsername,email);
    }

    @Override
    public int updateAddressSer (String outTradeNo, String address) {
        return infoDao.updateAddressDao(outTradeNo,address);
    }

    /*@Override
    public int updateUserSer (String newUsername, String oldUsername, String email, String donateAddress,String outTradeNo) {
        return infoDao.updateUserDao(newUsername,oldUsername,email,donateAddress,outTradeNo);
    }*/

    @Override
    public int updateUserSer (String user) {
        return 0;
    }
}
