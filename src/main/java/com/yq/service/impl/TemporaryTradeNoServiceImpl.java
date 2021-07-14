package com.yq.service.impl;


import com.yq.dao.TemporaryTradeNoDao;
import com.yq.dao.impl.TemporaryTradeNoDaoImpl;
import com.yq.domain.TemporaryTradeNo;
import com.yq.service.TemporaryTradeNoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/05 08:51
 **/

public class TemporaryTradeNoServiceImpl implements TemporaryTradeNoService {
    private TemporaryTradeNoDao tradeNoDao = new TemporaryTradeNoDaoImpl();

    @Override
    public int insertTemporaryTradeNo (TemporaryTradeNo temporaryTradeNo) {
        String time = new SimpleDateFormat().format(new Date());

        int insertTemporaryNumber = tradeNoDao.insertTemporaryTradeNoDao(time, temporaryTradeNo.getOut_trade_no(), "", "");
        return insertTemporaryNumber;
    }

    @Override
    public int updateTemporaryTradeNo (TemporaryTradeNo temporaryTradeNo) {
        return 0;
    }

    @Override
    public int deleteTemporaryTradeNo (TemporaryTradeNo temporaryTradeNo) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> selectTemporaryTradeNo () {
        return null;
    }
}
