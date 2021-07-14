package com.yq.dao;

import com.yq.domain.Hero;

import java.util.List;
import java.util.Map;

public interface HeroHonorDao {
    List<Map<String,Object>> selectAllDao(String identify);
    List<Map<String,Object>> selectAllDao();

    int deleteDao(String identify);

    int updateDao(Hero hero);

    int insertDao(Hero hero);

}
