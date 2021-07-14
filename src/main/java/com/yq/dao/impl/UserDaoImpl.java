package com.yq.dao.impl;


import com.yq.dao.UserDao;
import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/09 19:23
 **/

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public List<Map<String, Object>> userAllDao () {
        String sql = "select * from user";
        List<Map<String, Object>> list = template.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> userPageDao (int startRow, int pageSize,String sql,String sqlAll) {
        /*String sql = "SELECT * FROM `user` LIMIT ?,?";*/
        List<Map<String, Object>> list = template.queryForList(sql, startRow, pageSize);
        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",listAll.size());
        list.add(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> usernameByEmailDao (String email) {
        String sql = "select * from user where email = ?";
        List<Map<String, Object>> list = template.queryForList(sql, email);
        return list;
    }

    @Override
    public List<Map<String, Object>> usernameByEmailDao (String email, String username) {
        String sql = "select * from user where email = ? and username=?";
        List<Map<String, Object>> list = template.queryForList(sql, email,username);
        return list;
    }

    @Override
    public List<Map<String, Object>> usernameByUserProvinceDao (String province, String username) {
        String sql = "select * from user where 1=1 and address like  ? or username=?";
        List<Map<String, Object>> list = template.queryForList(sql, "%"+province+"%",username);
        return list;
    }

    @Override
    public int updateUsernameDao (String username, String email) {
        return 0;
    }

    @Override
    public int updateOldUsernameDao (String newUsername, String oldUsername,String email) {
        String sql = "UPDATE user set username=?,email=? where username=?";
        int update = template.update(sql, newUsername,email, oldUsername);
        return update;
    }

    @Override
    public int updateUserDao (String newUsername, String oldUsername, String email, String passWord) {
        String sql = "UPDATE `user` set username=?,email=?,password =?  where username=?";
        int update = template.update(sql, newUsername,email, passWord, oldUsername);
        return update;
    }

    @Override
    public int deleteUsernameDao (String username, String email) {
        return 0;
    }

    @Override
    public int deleteUsernameDao (String email) {
        String sql = "DELETE FROM user where email = ?";
        int update = template.update(sql, email);
        return update;
    }

    @Override
    public int insertUserInfoDao (String username, String email, String province, String city, String area, String address,int isAdmin,String passWord) {
        String sql = "insert into user(username,email,province,address,city,area,isadmin,password) values(?,?,?,?,?,?,?,?)";
        int update = 0;
        try {
            update = template.update(sql, username, email, province, address,city, area,isAdmin,passWord);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return update;
    }
}
