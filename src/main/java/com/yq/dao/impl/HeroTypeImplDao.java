package com.yq.dao.impl;


import com.yq.dao.HeroTypeDao;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:31
 **/

public class HeroTypeImplDao implements HeroTypeDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public int insertProfessionTypeDao (String pType, int pNo) {
        String sql = "insert into professiontype(ptype,pno) values(?,?)";
        int update = template.update(sql, pType, pNo);
        return update;
    }

    @Override
    public int updateProfessionTypeDao (String pType,int pNo) {
        String sql = "update professiontype set ptype = ? where pno = ?";
        return template.update(sql,pType,pNo);
    }

    @Override
    public int deleteProfessionTypeDao (String pType) {
        String sql = "delete from professiontype where ptype=?";
        int update = template.update(sql, pType);
        return update;
    }

    @Override
    public List<Map<String, Object>> selectAllProfessionTypeDao () {
        String sql = "select * from professiontype";
        List<Map<String, Object>> maps = template.queryForList(sql);
        return maps;
    }

    @Override
    public List<Map<String, Object>> selectAllProfessionTypeDao (String sql) {
        List<Map<String, Object>> maps = template.queryForList(sql);
        return maps;
    }
}
