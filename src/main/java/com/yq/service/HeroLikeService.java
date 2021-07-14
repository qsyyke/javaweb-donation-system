package com.yq.service;

import com.yq.domain.HeroComment;
import com.yq.domain.HeroLike;

import java.util.List;
import java.util.Map;

public interface HeroLikeService {

    List<Map<String, Object>> likeAllSer(HeroLike like);
    List<Map<String, Object>> likeBySqlSer(String sql);

    int insertLikeService(HeroLike like);

    int updateLikeSer(HeroLike like);

    int deleteLikeSer(HeroLike like);
}
