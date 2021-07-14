package com.yq.dao;

import com.yq.domain.Message;

import java.util.List;
import java.util.Map;

public interface MessageDao {
    List<Map<String, Object>> getMessage();
    List<Map<String, Object>> messageAllPageDao(int startRow, int pageSize,String sql,String sqlAll);

    int insertMessageDao (String message,String date,String province,String uname);
    List<Map<String, Object>> randomFromSystemMessageDao();

    List<Map<String, Object>> messageAllInfoDao();

    int updateMessageDao(String message,String province,String username);

    int deleteMessageDao(String username);
}
