package com.yq.dao;

import com.yq.domain.HeroLike;

import java.util.List;
import java.util.Map;

public interface HeroLikeDao {

    List<Map<String, Object>> likeAllDao(HeroLike like);
    List<Map<String, Object>> likeBySqlDao(String sql);

    int insertLikeDao(HeroLike like);

    int updateLikeDao(HeroLike like);

    int deleteLikeDao(HeroLike like);
}
