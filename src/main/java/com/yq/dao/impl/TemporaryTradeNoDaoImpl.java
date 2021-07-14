package com.yq.dao.impl;


import com.yq.dao.TemporaryTradeNoDao;
import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/05 08:55
 **/

public class TemporaryTradeNoDaoImpl implements TemporaryTradeNoDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public int insertTemporaryTradeNoDao (String createDate, String outTradeNo, String buyerCount, String tradeNo) {
        String sql = "insert into temporarytradeno (gmt_create,buyer_pay_amount,trade_no,out_trade_no) values(?,?,?,?)";
        int update = 0;
        try {
            update = template.update(sql, createDate, buyerCount, outTradeNo, tradeNo);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return update;
    }

    @Override
    public int updateTemporaryTradeNoDao (String outTradeNo) {
        return 0;
    }

    @Override
    public int deleteTemporaryTradeNoDao (String outTradeNo) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectTemporaryTradeNoDao () {
        return null;
    }
}
