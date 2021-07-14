package com.yq.service;

import com.yq.domain.HeroComment;
import com.yq.domain.Message;

import java.util.List;
import java.util.Map;

public interface HeroCommentService {

    List<Map<String, Object>> commentAllPageSer(String username,String startTime,String endTime,int startRow, int pageSize);

    List<Map<String, Object>> commentAllSer(HeroComment comment);
    List<Map<String, Object>> commentBySqlSer(String sql);

    int insertCommentService(HeroComment comment);

    int updateCommentSer(HeroComment comment);

    int deleteCommentSer(HeroComment comment);

    int updateLikeSer(HeroComment comment);




}
