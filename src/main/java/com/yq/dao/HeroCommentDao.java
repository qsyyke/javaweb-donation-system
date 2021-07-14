package com.yq.dao;

import com.yq.domain.HeroComment;

import java.util.List;
import java.util.Map;

public interface HeroCommentDao {
    List<Map<String, Object>> commentAllPageDao(int startRow, int pageSize,String sql,String sqlAll);

    List<Map<String, Object>> commentAllDao(HeroComment comment);
    List<Map<String, Object>> commentBySqlDao(String sql);

    int insertCommentDao(HeroComment comment);

    int updateCommentDao(HeroComment comment);

    int deleteCommentDao(HeroComment comment);

    int updateLikeDao(HeroComment comment);
}
