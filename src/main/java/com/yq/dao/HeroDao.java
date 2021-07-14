package com.yq.dao;

import com.yq.domain.Hero;

import java.util.List;
import java.util.Map;

public interface HeroDao {
    List<Map<String,Object>> selectByPageDao(int startRow, int pageSize,String sql,String sqlAll);
    List<Map<String,Object>> selectBySqlDao(String sql);

    int deleteDao(String identify);

    int updateDao(Hero hero);
    int updatePhotoSrcDao(Hero hero);
    int updateDescribeDao(Hero hero);

    int insertDao(Hero hero);

    int insertPhotoSrcDao (Hero hero);
    int insertLikeDao(Hero hero);
    int updateLikeDao(Hero hero);
}
