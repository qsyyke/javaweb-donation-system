package com.yq.service;

import com.yq.domain.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Map<String, Object>> getMessage();
    List<Map<String, Object>> messageAllPageSer(String username,String startTime,String endTime,int startRow, int pageSize);
    int insertMessageService(String message,String date,String province,String uname);

    List<Map<String, Object>> randomFromSystemMessageService();

    List<Map<String, Object>> messageAllInfoSer();

    int updateMessageInfoSer(Message message);

    int deleteMessageInfoSer(Message message);




}
