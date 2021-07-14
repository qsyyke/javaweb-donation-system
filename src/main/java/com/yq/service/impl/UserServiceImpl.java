package com.yq.service.impl;


import com.yq.dao.UserDao;
import com.yq.dao.impl.UserDaoImpl;
import com.yq.domain.User;
import com.yq.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/09 19:22
 **/

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public List<Map<String, Object>> userServiceAll () {
        return dao.userAllDao();
    }

    @Override
    public List<Map<String, Object>> userPageSer (int startRow, int pageSize,String province,String username,int isAdmin) {
        if (" ".equals(province) && "".equals(username)) {
            //用户没有进行模糊查询
            String sql = "select * from user where isadmin='"+isAdmin+"' limit ?,?";
            String sqlAll = "select * from user where isadmin='"+isAdmin+"'";
            return dao.userPageDao(startRow, pageSize, sql,sqlAll);
        }

        //如果省份为空 用户名不为空
        if (" ".equals(province)) {
            String sql = "select * from user where isadmin='"+isAdmin+"' and username ='"+username+"' limit ?,?";
            String sqlAll = "select * from user where isadmin='"+isAdmin+"' and username ='"+username+"'";
            return dao.userPageDao(startRow, pageSize, sql,sqlAll);
        }

        //如果用户名为空 省份不为空
        if ("".equals(username)) {
            String sql = "select * from user where isadmin='"+isAdmin+"' and address like '%"+province+"%' limit ?,?";
            String sqlAll = "select * from user where isadmin='"+isAdmin+"' and address like '%"+province+"%'";
            return dao.userPageDao(startRow, pageSize, sql,sqlAll);
        }

        if (!(" ".equals(province)) && !("".equals(username))) {
            String sql = "select * from user where isadmin='"+isAdmin+"' and address like '%"+province+"%' " +
                    "or username = '"+username+"' limit ?,?";

            String sqlAll = "select * from user where isadmin='"+isAdmin+"' and address like '%"+province+"%' " +
                    "or username ='"+username+"'";
            return dao.userPageDao(startRow, pageSize, sql,sqlAll);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Map<String, Object>> usernameByEmailSer (String email) {
        return dao.usernameByEmailDao(email);
    }

    @Override
    public List<Map<String, Object>> usernameByEmailSer (String email, String username) {
        return dao.usernameByEmailDao(email,username);
    }

    @Override
    public List<Map<String, Object>> usernameByUserProvinceSer (String province, String username) {
        return dao.usernameByUserProvinceDao(province,username);
    }

    @Override
    public int updateUsernameSer (String username, String email) {
        return 0;
    }

    @Override
    public int updateOldUsernameSer (String newUsername, String oldUsername,String newEmail) {
        return dao.updateOldUsernameDao(newUsername,oldUsername,newEmail);
    }

    @Override
    public int updateUserSer (String newUsername, String oldUsername, String email, String passWord) {
        return dao.updateUserDao(newUsername,oldUsername,email,passWord);
    }

    @Override
    public int deleteUsernameSer (String username, String email) {
        return 0;
    }

    @Override
    public int deleteUsernameSer (String email) {
        return dao.deleteUsernameDao(email);
    }

    @Override
    public int insertUserInfoSer (User user) {
        return dao.insertUserInfoDao(user.getUsername(),user.getEmail(),user.getProvince(),
                user.getCity(),user.getArea(),user.getAddress(),user.getIsAdmin(),user.getPassword());
    }
}
