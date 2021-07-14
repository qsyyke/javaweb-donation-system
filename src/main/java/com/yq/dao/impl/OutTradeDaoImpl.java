package com.yq.dao.impl;


import com.yq.dao.OutTradeNoDao;
import com.yq.domain.OutTradeNo;
import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/03 18:57
 **/

public class OutTradeDaoImpl implements OutTradeNoDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public int insertOutTradeNoDao (String outTradeNo,String tradeNo,String createDate,String tradeStatus,double tradeMoney,String tradeCount,String subject) {
        String sql = "insert into outtradeno(outTradeNo, tradeNo, createDate, tradeStatus, tradeMoney, tradeCount, subject) values(?,?,?,?,?,?,?)";
        int update = 0;
        try {
            update = template.update(sql, outTradeNo, tradeNo, createDate, tradeStatus, tradeMoney, tradeCount, subject);
        } catch (DataAccessException e) {
            e.printStackTrace();
            update = 0;
        }
        return update;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeNoAllDao () {
        String sql = "SELECT tradeMoney,createDate,userprovince,username from outtradeinfo info,outtradeno tradeno where info.outTradeNo = tradeno.outTradeNo";
        List<Map<String, Object>> mapList = template.queryForList(sql);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeAllDao (String sql,String sqlAll,int startPage,int pageSize) {
        System.out.println(sql);
        System.out.println(sqlAll);
        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        List<Map<String, Object>> mapList = template.queryForList(sql, startPage, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",listAll.size());
        mapList.add(map);
        return mapList;
    }

    @Override
    public String selectOutTradeNoTradeNoDao (String outTradeNo) {
        String sql = "select * from outtradeno where outTradeNo = ?";
        List<Map<String, Object>> mapList = null;
        String outTradeNoStr = "";

        try {
            mapList = template.queryForList(sql, outTradeNo);
            if (mapList.size() != 0) {
                outTradeNoStr = (Double)mapList.get(0).get("tradeMoney")+"";
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            //发生异常
        }

        return outTradeNoStr;
    }

    @Override
    public int selectOutTradeNoDao (String outTradeNo) {
        String sql = "select * from outtradeno where outTradeNo= ?";
        int selectNumber = 1;
        try {
            template.queryForMap(sql, outTradeNo);
        } catch (DataAccessException e) {
            //e.printStackTrace();
            selectNumber = 0;
        }

        return selectNumber;
    }

    @Override
    public int deleteOutTradeNoDao (String outTrade) {
        return 0;
    }

    @Override
    public int updateOutTradeNoDao (String outTrade) {
        //如果退款，则修改是否退款过的标志
        String sql = "UPDATE outtradeno set isRefund=1 where outtradeno=?";

        int update = template.update(sql, outTrade);
        return update;
    }
}
