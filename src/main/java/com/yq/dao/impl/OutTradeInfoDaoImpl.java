package com.yq.dao.impl;


import com.yq.dao.OutTradeNoDao;
import com.yq.dao.OutTradeNoInfoDao;
import com.yq.domain.OutTradeNo;
import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/03 18:57
 **/

public class OutTradeInfoDaoImpl implements OutTradeNoInfoDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    @Override
    public int insertOutTradeNoInfoDao (String username,String email,
                                    String wishGoods,String userProvince,String donateProvince,
                                    String outTradeNo,String userAddress,String donateAddress) {
        String sql = "insert into outtradeinfo(username,email,wishGoods,userProvince," +
                "donateProvince,outTradeNo,useraddress,donateaddress) values(?,?,?,?,?,?,?,?)";
        int update = 0;
        try {
            update = template.update(sql, username,email,wishGoods,userProvince,donateProvince,outTradeNo,userAddress,donateAddress);
        } catch (DataAccessException e) {
            e.printStackTrace();
            update = 0;
        }
        return update;
    }

    @Override
    public List<Map<String,Object>> selectOutTradeNoInfoAllDao (String outTradeNo) {
        String sql = "SELECT email email,username name,donateProvince province,outtradeno.outTradeno tradeNo,createDate date,wishGoods goods,tradeMoney money,tradeCount count ,count(DISTINCT outtradeno.outtradeno) c from outtradeno,outtradeinfo WHERE outtradeno.outtradeno = outtradeinfo.outtradeno and outtradeno.outtradeno = ? GROUP BY outtradeinfo.outtradeno";
        List<Map<String, Object>> mapList = template.queryForList(sql, outTradeNo);
        return mapList;
    }

    @Override
    public List<Map<String,Object>> selectOutTradeNoInfoAllDao () {
        String sql = "SELECT info.wishGoods,trade.tradeMoney,info.useraddress,info.donateProvince ,count(DISTINCT username) from outtradeinfo info,outtradeno trade where trade.outtradeno = info.outtradeno GROUP BY trade.outtradeno";
        List<Map<String, Object>> mapList = template.queryForList(sql);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> selectOutTradeGoodsInfoDao () {
        String sql = "SELECT DISTINCT outtradeNo FROM outtradeinfo WHERE wishGoods =?";

        List<Map<String, Object>> list = new ArrayList<>();


        //定义一个物品数组
        String[] goods = {"现金","防护服","医用口罩","护目镜","隔离衣","喷雾器","红外线体温仪"};
        for (int i = 0; i < goods.length; i++) {
            Map<String, Object> mapGoodSize = new HashMap<>();
            List<Map<String, Object>> mapList = template.queryForList(sql, goods[i]);

            if (mapList.size() == 0) {
                mapGoodSize.put(goods[i], 0);
                list.add(mapGoodSize);
                continue;
            }
            mapGoodSize.put(goods[i], mapList.size());
            list.add(mapGoodSize);
        }
        return list;
    }


    @Override
    public List<Map<String,Object>> selectOutTradeNoInfoDao (String outTradeNo) {
        String sql = "select * from outtradeinfo where outtradeno = ?";
        List<Map<String, Object>> list = template.queryForList(sql, outTradeNo);
        return list;
    }

    @Override
    public int deleteOutTradeNoInfoDao (String outTrade) {
        return 0;
    }

    @Override
    public int updateOutTradeNoInfoDao (String outTrade) {
        return 0;
    }

    @Override
    public int updateUserDao (String newUsername, String oldUsername,String email) {
        String sql = "UPDATE outtradeinfo set username=?,email=? where username=?";
        int update = template.update(sql, newUsername,email, oldUsername);
        return update;
    }

    @Override
    public int updateAddressDao (String outTradeNo, String address) {
        String sql = "update outtradeinfo set donateaddress=? where outTradeNo = ?";
        int update = template.update(sql, address, outTradeNo);
        return update;
    }

    /*@Override
    public int updateUserDao (String newUsername, String oldUsername, String email, String donateAddress,String outTradeNo) {
        String sql = "UPDATE outtradeinfo set username=?,email=? ,donateaddress =?  where username=? and outTradeNo = ?";
        int update = template.update(sql, newUsername,email,donateAddress, oldUsername,outTradeNo);
        return update;
    }*/

    @Override
    public int updateUserDao (String user) {
        return 0;
    }
}
