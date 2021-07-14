package com.yq.dao;

import com.yq.domain.User;

import java.util.List;

public interface LoginDao {
    /**
     *@decript:  此类用于向数据库中数据的操作
     */

    //用户登录验证 返回一个user对象
    List<User> getLoginDao(String username, String password);

    //查询邮箱是否存在
    int selectMailDao(String email);

    //向数据库中插入数据 返回的是影响的行数
    int insertDao(User user);

    int updateUserDao(String email,String password);
}
