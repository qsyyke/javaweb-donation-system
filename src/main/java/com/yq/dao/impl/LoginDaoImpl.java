package com.yq.dao.impl;

import com.yq.dao.LoginDao;
import com.yq.domain.User;
import com.yq.util.impl.DruidUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class LoginDaoImpl implements LoginDao {
    /**  */
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    @Override
    public List<User> getLoginDao (String username, String password) {
        String sql = "select * from user where username=? and password=?";
        List<User> userList = template.query(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        return userList;
    }

    @Override
    public int selectMailDao (String email) {
        String sql = "select * from user where email=?";

        //设置标志变量
        int isFind = 0;
        Map<String, Object> emailMap = null;
        try {
            emailMap = template.queryForMap(sql, email);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return isFind;
        }
        //能够运行到这里，说明没有问题
        isFind = 1;
        return isFind;
    }

    @Override
    public int insertDao (User user) {
        String sql = "insert into user (username,password,email) values(?,?,?)";

        return template.update(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }

    @Override
    public int updateUserDao (String email, String password) {
        String sql = "UPDATE user set password=? WHERE email=?";
        int update = template.update(sql, password, email);
        return update;
    }
}
