package com.yq.service.impl;


import com.yq.dao.HeroDao;
import com.yq.dao.impl.HeroImplDao;
import com.yq.domain.Hero;
import com.yq.service.HeroService;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:25
 **/

public class HeroImplService implements HeroService {
    private HeroDao heroDao = new HeroImplDao();
    @Override
    public List<Map<String, Object>> selectByPageSer (int startRow, int pageSize, String username) {
        String sql = "select hero.heroname name,hero.identify,birthday,isshow,photosrc,`h_like`,ptype,`h_describe` ";
        for (int i = 0; i < 7; i++) {
            sql = sql+" ,honor"+(i+1);
        }

        //select hero.heroname name,birthdat,isshow,photosrc,like,ptype,describe  ,honor1 ,honor2 ,honor3 ,honor4 ,honor5 ,honor6 ,honor7
        sql = sql+" from hero,herohonor where hero.identify=herohonor.identify  ";
        if (!("".equals(username))) {
            String sqlAll = sql + "and hero.heroname like'%"+username+"%'";
            sql = sql+" and hero.heroname like '%"+username+"%' limit ?,?";
            System.out.println(sql);
            System.out.println(sqlAll);
            return heroDao.selectByPageDao(startRow,pageSize,sql,sqlAll);
        }

        //能运行到这里离说明用户名为“”
        String sqlAll = sql;
        sql = sql+" limit ?,?";
        System.out.println(sql);
        System.out.println(sqlAll);
        return heroDao.selectByPageDao(startRow,pageSize,sql,sqlAll);
    }

    @Override
    public List<Map<String, Object>> selectBySqlSer (String sql) {
        return heroDao.selectBySqlDao(sql);
    }

    @Override
    public int deleteSer (String identify) {
        return heroDao.deleteDao(identify);
    }

    @Override
    public int updateSer (Hero hero) {
        return heroDao.updateDao(hero);
    }

    @Override
    public int updatePhotoSrcSer (Hero hero) {
        return heroDao.updatePhotoSrcDao(hero);
    }

    @Override
    public int updateDescribeSer (Hero hero) {
        return heroDao.updateDescribeDao(hero);
    }

    @Override
    public int insertSer (Hero hero) {
        return heroDao.insertDao(hero);
    }

    @Override
    public int insertPhotoSrcSer (Hero hero) {
        return heroDao.insertPhotoSrcDao(hero);
    }

    @Override
    public int insertLikeSer (Hero hero) {
        return heroDao.insertLikeDao(hero);
    }

    @Override
    public int updateLikeSer (Hero hero) {
        return heroDao.updateLikeDao(hero);
    }
}
