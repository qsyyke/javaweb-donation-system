package com.yq.dao;

import java.util.List;
import java.util.Map;

public interface TemporaryTradeNoDao {

    int insertTemporaryTradeNoDao(String createDate,String outTradeNo,String buyerCount,String tradeNo);

    int updateTemporaryTradeNoDao(String outTradeNo);

    int deleteTemporaryTradeNoDao(String outTradeNo);

    List<Map<String,Object>> selectTemporaryTradeNoDao();
}
