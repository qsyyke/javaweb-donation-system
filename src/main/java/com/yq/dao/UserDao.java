package com.yq.dao;

import java.util.List;
import java.util.Map;

public interface UserDao {
    List<Map<String,Object>> userAllDao();
    List<Map<String,Object>> userPageDao(int startRow,int pageSize,String sql,String sqlAll);

    List<Map<String,Object>> usernameByEmailDao(String email);
    List<Map<String,Object>> usernameByEmailDao(String email,String username);
    List<Map<String,Object>> usernameByUserProvinceDao(String province,String username);

    int updateUsernameDao(String username,String email);

    int updateOldUsernameDao(String newUsername,String oldUsername,String email);
    int updateUserDao(String newUsername,String oldUsername,String email,String passWord);

    int deleteUsernameDao(String username,String email);
    int deleteUsernameDao(String email);

    int insertUserInfoDao(String username,String email,String province,String city,String area,String address,int isAdmin,String passWord);
}
