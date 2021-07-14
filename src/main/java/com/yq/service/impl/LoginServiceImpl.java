package com.yq.service.impl;

import com.yq.dao.LoginDao;
import com.yq.dao.impl.LoginDaoImpl;
import com.yq.domain.User;
import com.yq.service.LoginService;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class LoginServiceImpl implements LoginService {
    private LoginDao loginDao = new LoginDaoImpl();

    @Override
    public List<User> getLogin (User user) {
        return loginDao.getLoginDao(user.getUsername(),user.getPassword());
    }

    @Override
    public int selectMailService(String email) {
        return loginDao.selectMailDao(email);
    }

    @Override
    public int getInsert (User user) {
        return loginDao.insertDao(user);
    }

    @Override
    public int updateUserService (String email,String password) {


        return loginDao.updateUserDao(email,password);
    }
}
