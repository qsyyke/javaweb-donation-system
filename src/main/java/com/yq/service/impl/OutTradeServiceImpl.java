package com.yq.service.impl;


import com.yq.dao.OutTradeNoDao;
import com.yq.dao.impl.OutTradeDaoImpl;
import com.yq.domain.OutTradeInfo;
import com.yq.domain.OutTradeNo;
import com.yq.service.OutTradeNoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/03 18:58
 **/

public class OutTradeServiceImpl implements OutTradeNoService {
    OutTradeNoDao tradeNoDao = new OutTradeDaoImpl();

    @Override
    public int insertOutTradeNoService (OutTradeNo userOutTradeNo) {

        //商户订单号
        String outTradeNo = userOutTradeNo.getOutTradeNo();

        //支付宝订单号
        String tradeNo = userOutTradeNo.getTradeNo();

        //创建时间
        String createDate = userOutTradeNo.getCreateDate();

        //交易状态
        String tradeStatus = userOutTradeNo.getTradeStatus();

        //订单金额
        double tradeMoney = userOutTradeNo.getTradeMoney();

        //用户订单账户
        String tradeCount = userOutTradeNo.getTradeCount();

        //订单标题
        String subject = userOutTradeNo.getSubject();

        int insertNumber = tradeNoDao.insertOutTradeNoDao(outTradeNo, tradeNo, createDate, tradeStatus, tradeMoney, tradeCount, subject);

        return insertNumber;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeNoAllService () {
        List<Map<String, Object>> maps = tradeNoDao.selectOutTradeNoAllDao();
        return maps;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeAllService (
            int refund, String username, String startTime,String endTime,int startPage,int pageSize) {

        String sql = "SELECT createDate date, username buyer,tradeNo zfbCount,`outtradeinfo`.outTradeNo tradeNo," +
                "tradeMoney money,isRefund refund, tradeCount count,wishGoods good,email,donateaddress address " +
                "FROM `outtradeinfo`,outtradeno  WHERE outtradeno.outTradeNo = `outtradeinfo`.outTradeNo and isRefund = '"+refund +"' ";

        if (!("".equals(username)) && !("".equals(startTime))) {
            //时间还有用户名都不为空
            String sqlAll = sql+ "and username like '%"+
                    username+"%' and createDate between '"+startTime+"' and '"+endTime+"'";
            sql = sql+ "and username like '%"+
                    username+"%' and createDate between '"+startTime+"' and '"+endTime +"' limit ?,?";
            List<Map<String, Object>> mapList = tradeNoDao.selectOutTradeAllDao(sql, sqlAll,startPage, pageSize);

            return mapList;
        }

        if ("".equals(username) && "".equals(startTime)) {
            //用户名和时间都为空
            String sqlAll = sql;
            sql = sql+" limit ?,?";

            List<Map<String, Object>> mapList = tradeNoDao.selectOutTradeAllDao(sql,sqlAll, startPage, pageSize);
            return mapList;
        }

        if ("".equals(username)) {
            //用户名为空
            //时间还有用户名都不为空
            String sqlAll = sql+" and createDate between '"+startTime+"'  and '"+endTime+"'";
            sql = sql+" and createDate between '"+startTime+"' and '"+endTime +"' limit ?,?";

            List<Map<String, Object>> mapList = tradeNoDao.selectOutTradeAllDao(sql,sqlAll, startPage, pageSize);
            return mapList;
        }

        if ("".equals(startTime)) {
            //时间为空
            String sqlAll = sql+" and username ='"+username+"' ";
            sql = sql+" and username ='"+username+"'  limit ?,?";

            List<Map<String, Object>> mapList = tradeNoDao.selectOutTradeAllDao(sql,sqlAll, startPage, pageSize);
            return mapList;
        }
        return new ArrayList<>();
    }

    @Override
    public String selectTradeNoStrService (String outTradeNo) {
        String tradeNoStr = tradeNoDao.selectOutTradeNoTradeNoDao(outTradeNo);
        return tradeNoStr;
    }

    @Override
    public int selectOutTradeNoService (String outTradeNo) {
        int selectNumber = tradeNoDao.selectOutTradeNoDao(outTradeNo);
        return selectNumber;
    }

    @Override
    public int deleteOutTradeNoService (String outTrade) {
        return 0;
    }

    @Override
    public int updateOutTradeNoService (String outTrade) {
        return tradeNoDao.updateOutTradeNoDao(outTrade);
    }
}
