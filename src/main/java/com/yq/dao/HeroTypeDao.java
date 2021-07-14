package com.yq.dao;

import java.util.List;
import java.util.Map;

public interface HeroTypeDao {
    int insertProfessionTypeDao(String pType,int pNo);

    int updateProfessionTypeDao(String pType,int pNo);

    int deleteProfessionTypeDao(String pType);

    List<Map<String,Object>> selectAllProfessionTypeDao();
    List<Map<String,Object>> selectAllProfessionTypeDao(String sql);
}
