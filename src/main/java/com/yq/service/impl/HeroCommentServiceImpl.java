package com.yq.service.impl;

import com.yq.dao.HeroCommentDao;
import com.yq.dao.MessageDao;
import com.yq.dao.impl.HeroCommentDaoImpl;
import com.yq.dao.impl.MessageDaoImpl;
import com.yq.domain.HeroComment;
import com.yq.domain.Message;
import com.yq.service.HeroCommentService;
import com.yq.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HeroCommentServiceImpl implements HeroCommentService {
    private HeroCommentDao commentDao = new HeroCommentDaoImpl();

    @Override
    public List<Map<String, Object>> commentAllPageSer (String cName, String startTime, String endTime, int startRow, int pageSize) {
        String sqlAll = "select hero.identify,hero.heroname,uid,h_like,cdate,ccontent,cname from hero,heromessage " +
                "where heromessage.identify=hero.identify ";

        String sql = sqlAll;

         if (!("".equals(cName)) && !("".equals(endTime))) {
             //评论者和时间都不为空
             sqlAll = sqlAll+" and cname like '%"+cName+"%' and cdate BETWEEN '"+startTime+"' AND '"+endTime+"'";
             sql = sqlAll+" limit ?,?";
             return commentDao.commentAllPageDao(startRow,pageSize,sql,sqlAll);
         }

        if ("".equals(cName) && "".equals(endTime)) {
            //评论者和时间都为空
            sql = sqlAll+" limit ?,?";
            return commentDao.commentAllPageDao(startRow,pageSize,sql,sqlAll);
        }

        if ("".equals(cName)) {
            //评论者为空
            sqlAll = sqlAll + " and cdate BETWEEN '"+startTime+"' AND '"+endTime+"'";
            sql = sqlAll+" limit ?,?";
            return commentDao.commentAllPageDao(startRow,pageSize,sql,sqlAll);
        }

        if ("".equals(startTime)) {
            //评论者为空
            sqlAll = sqlAll + " and cname like '%"+cName+"%'";
            sql = sqlAll+" limit ?,?";
            return commentDao.commentAllPageDao(startRow,pageSize,sql,sqlAll);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Map<String, Object>> commentAllSer (HeroComment comment) {
        return commentDao.commentAllDao(comment);
    }

    @Override
    public List<Map<String, Object>> commentBySqlSer (String sql) {
        return commentDao.commentBySqlDao(sql);
    }

    @Override
    public int insertCommentService (HeroComment comment) {
        return commentDao.insertCommentDao(comment);
    }

    @Override
    public int updateCommentSer (HeroComment comment) {
        return commentDao.updateCommentDao(comment);
    }

    @Override
    public int deleteCommentSer (HeroComment comment) {
        return commentDao.deleteCommentDao(comment);
    }

    @Override
    public int updateLikeSer (HeroComment comment) {
        return commentDao.updateLikeDao(comment);
    }
}
