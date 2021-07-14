package com.yq.service.impl;


import com.yq.dao.HeroDao;
import com.yq.dao.HeroHonorDao;
import com.yq.dao.impl.HeroHonorImplDao;
import com.yq.dao.impl.HeroImplDao;
import com.yq.domain.Hero;
import com.yq.service.HeroHonorSer;
import com.yq.service.HeroService;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:25
 **/

public class HeroHonorImplService implements HeroHonorSer {
    private HeroHonorDao honorDao = new HeroHonorImplDao();

    @Override
    public List<Map<String, Object>> selectAllSer () {
        return honorDao.selectAllDao();
    }

    @Override
    public List<Map<String, Object>> selectAllSer (String heroName) {
        return honorDao.selectAllDao(heroName);
    }

    @Override
    public int deleteSer (String identify) {
        return honorDao.deleteDao(identify);
    }

    @Override
    public int updateSer (Hero hero) {
        return honorDao.updateDao(hero);
    }

    @Override
    public int insertSer (Hero hero) {
        return honorDao.insertDao(hero);
    }
}
