package com.yq.service;

import com.yq.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 返回所有的用户信息
     * @author chuchen
     * @date 2021/4/9 19:19
     * @return List<Map<String,Object>>
     */
    /**
     * 返回所有的用户信息
     * @author chuchen
     * @date 2021/4/9 19:19
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> userServiceAll();
    List<Map<String,Object>> userPageSer(int startRow,int pageSize,String province,String username,int isAdmin);


    List<Map<String,Object>> usernameByEmailSer(String email);
    List<Map<String,Object>> usernameByEmailSer(String email,String username);

    List<Map<String, Object>> usernameByUserProvinceSer(String province,String username);

    int updateUsernameSer(String username,String email);
    int updateOldUsernameSer (String newUsername, String oldUsername,String email);
    int updateUserSer (String newUsername, String oldUsername,String email,String passWord);


    int deleteUsernameSer(String username,String email);
    int deleteUsernameSer(String email);

    int insertUserInfoSer(User user);



}
