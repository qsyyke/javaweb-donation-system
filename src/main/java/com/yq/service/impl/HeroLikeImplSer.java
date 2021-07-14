package com.yq.service.impl;


import com.yq.dao.HeroLikeDao;
import com.yq.dao.impl.HeroLikeImplDao;
import com.yq.domain.HeroLike;
import com.yq.service.HeroLikeService;
import com.yq.service.HeroService;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/06 17:36
 **/

public class HeroLikeImplSer implements HeroLikeService {
    private HeroLikeDao dao = new HeroLikeImplDao();
    @Override
    public List<Map<String, Object>> likeAllSer (HeroLike like) {
        return dao.likeAllDao(like);
    }

    @Override
    public List<Map<String, Object>> likeBySqlSer (String sql) {
        return dao.likeBySqlDao(sql);
    }

    @Override
    public int insertLikeService (HeroLike like) {
        return dao.insertLikeDao(like);
    }

    @Override
    public int updateLikeSer (HeroLike like) {
        return dao.updateLikeDao(like);
    }

    @Override
    public int deleteLikeSer (HeroLike like) {
        return dao.deleteLikeDao(like);
    }
}
