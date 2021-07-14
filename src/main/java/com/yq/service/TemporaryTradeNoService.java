package com.yq.service;

import com.yq.domain.TemporaryTradeNo;

import java.util.List;
import java.util.Map;

public interface TemporaryTradeNoService {

    int insertTemporaryTradeNo(TemporaryTradeNo temporaryTradeNo);

    int updateTemporaryTradeNo(TemporaryTradeNo temporaryTradeNo);

    int deleteTemporaryTradeNo(TemporaryTradeNo temporaryTradeNo);

    List<Map<String,Object>> selectTemporaryTradeNo();


}
