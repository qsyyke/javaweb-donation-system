package com.yq.service;

import com.yq.domain.User;

import java.util.List;

public interface LoginService {
    //验证用户名 密码  返回用户对象
    List<User> getLogin(User user);

    //查询邮箱 返回邮件
    int selectMailService(String email);

    //用户注册 返回影响的行数 0代表插入失败
    int getInsert(User user);

    /**
     * 根据用户的邮箱，更新密码
     * @author chuchen
     * @date 2021/3/30 22:34
 * @param email email
     * @return int
     */
    int updateUserService(String email,String password);
}
