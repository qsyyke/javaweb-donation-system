package com.yq.dao.impl;

import com.yq.dao.MessageDao;

import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDaoImpl implements MessageDao {
    JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public List<Map<String, Object>> getMessage () {
        String sql = "select message from message";
        List<Map<String, Object>> maps = template.queryForList(sql);
        return maps;
    }

    @Override
    public List<Map<String, Object>> messageAllPageDao (int startRow, int pageSize, String sql, String sqlAll) {
        List<Map<String, Object>> mapList = template.queryForList(sql, startRow, pageSize);
        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",listAll.size());
        mapList.add(map);
        return mapList;
    }

    @Override
    public int insertMessageDao (String message,String date,String province,String uname) {
        int insert_number = 0;
        if (province == null) {
            province = "武汉";
        }
        if (message == null || "".equals(message)) {
            message = "武汉加油";
        }
        String sql = "insert into message(username,province,message,date) values(?,?,?,?)";
        try {
            insert_number = template.update(sql, uname, province, message, date);
        } catch (DataAccessException e) {
            e.printStackTrace();
            insert_number = 0;
            return insert_number;
        }
        return insert_number;
    }

    @Override
    public List<Map<String, Object>> randomFromSystemMessageDao () {
        String sql = "select * from systemmessage";

        List<Map<String, Object>> mapList = template.queryForList(sql);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> messageAllInfoDao () {
        String sql = "select * from message";
        List<Map<String, Object>> list = template.queryForList(sql);
        return list;
    }

    @Override
    public int updateMessageDao (String message, String province,String username) {
        String sql = "UPDATE message set message = ? ,province=? WHERE username=?";
        int update = 0;
        try {
            update = template.update(sql, message, province, username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            update = 0;
        }

        return update;
    }

    @Override
    public int deleteMessageDao (String username) {
        String sql = "DELETE from message WHERE username = ?";
        int update = 0;
        try {
            update = template.update(sql,username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            update = 0;
        }
        return update;
    }
}
