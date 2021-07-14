package com.yq.dao.impl;

import com.yq.dao.HeroCommentDao;
import com.yq.dao.MessageDao;
import com.yq.domain.HeroComment;
import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeroCommentDaoImpl implements HeroCommentDao {
    JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    @Override
    public List<Map<String, Object>> commentAllPageDao (int startRow, int pageSize,String sql,String sqlAll) {

        List<Map<String, Object>> list = template.queryForList(sql, startRow, pageSize);
        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",listAll.size());
        list.add(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> commentAllDao (HeroComment comment) {
        String sql = "select * from heromessage  where identify =?";
        return template.queryForList(sql,comment.getIdentify());
    }

    @Override
    public List<Map<String, Object>> commentBySqlDao (String sql) {
        return template.queryForList(sql);
    }

    @Override
    public int insertCommentDao (HeroComment comment) {
        String sql = "insert into heromessage (identify,cname,cdate,ccontent,uid) values(?,?,?,?,?)";
        return template.update(sql,comment.getIdentify(),comment.getcName(),comment.getcDate(),comment.getcContent(),comment.getUid());
    }

    @Override
    public int updateCommentDao (HeroComment comment) {
        String sql = "update heromessage set cname=?,ccontent=? where identify=? and uid=?";
        return template.update(sql,comment.getcName(),comment.getcContent(),comment.getIdentify(),comment.getUid());
    }

    @Override
    public int deleteCommentDao (HeroComment comment) {
        String sql = "delete from heromessage where identify=? and uid=?";
        return template.update(sql,comment.getIdentify(),comment.getUid());
    }

    @Override
    public int updateLikeDao (HeroComment comment) {
        String sql = "update heromessage set islike=? where identify = ? and uid=?";
        return template.update(sql,comment.getIsLike(),comment.getIdentify(),comment.getUid());
    }
}
