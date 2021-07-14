package com.yq.dao.impl;


import com.yq.dao.HeroLikeDao;
import com.yq.domain.HeroLike;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/06 17:38
 **/

public class HeroLikeImplDao implements HeroLikeDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public List<Map<String, Object>> likeAllDao (HeroLike like) {
        String sql = "SELECT h_like.uid uid,h_like.identify identify,islike " +
                "FROM `h_like`,hero " +
                "where h_like.identify=hero.identify " +
                "and h_like.uid="+"?";

        return template.queryForList(sql,like.getUid());
    }

    @Override
    public List<Map<String, Object>> likeBySqlDao (String sql) {
        return template.queryForList(sql);
    }

    @Override
    public int insertLikeDao (HeroLike like) {
        String sql = "insert into h_like (uid,identify,islike) values(?,?,?)";
        return template.update(sql,like.getUid(),like.getIdentify(),like.getIsLike());
    }

    @Override
    public int updateLikeDao (HeroLike like) {
        String sql = "update h_like set islike = ? where uid=? and identify=?";
        return template.update(sql,like.getIsLike(),like.getUid(),like.getIdentify());
    }

    @Override
    public int deleteLikeDao (HeroLike like) {
        String sql = "delete from h_like where uid=? and identify =?";
        return template.update(sql,like.getUid(),like.getIdentify());
    }
}
