package com.yq.service.impl;


import com.yq.dao.HeroTypeDao;
import com.yq.dao.impl.HeroTypeImplDao;
import com.yq.service.HeroService;
import com.yq.service.HeroTypeService;
import org.w3c.dom.CDATASection;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:25
 **/

public class HeroTypeImplService implements HeroTypeService {
    private HeroTypeDao dao = new HeroTypeImplDao();
    @Override
    public int insertProfessionTypeSer (String pType, int pNo) {
        return dao.insertProfessionTypeDao(pType,pNo);
    }

    @Override
    public int updateProfessionTypeSer (String pType,int pNo) {
        return dao.updateProfessionTypeDao(pType,pNo);
    }

    @Override
    public int deleteProfessionTypeSer (String pType) {
        return dao.deleteProfessionTypeDao(pType);
    }

    @Override
    public List<Map<String, Object>> selectAllProfessionTypeSer () {
        return dao.selectAllProfessionTypeDao();
    }

    @Override
    public List<Map<String, Object>> selectAllProfessionTypeSer (String sql) {
        return dao.selectAllProfessionTypeDao(sql);
    }
}
