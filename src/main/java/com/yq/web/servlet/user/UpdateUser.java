package com.yq.web.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.OutTradeInfoService;
import com.yq.service.UserService;
import com.yq.service.impl.OutTradeInfoServiceImpl;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于修改用户名，根据用户的用户名进行修改
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/11 8:10
 **/

@WebServlet("/upuser")
public class UpdateUser extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取用户旧的用户名
        String oldUsername = request.getParameter("oldUsername");

        //获取用户新的用户名
        String newUsername = request.getParameter("newUsername");

        //获取邮箱号
        String newEmail = request.getParameter("newEmail");

        //获取密码
        String passWord = request.getParameter("passWord");

        //旧的邮箱
        String oldEmail = "";

        if (newUsername == null || "".equals(newUsername)) {
            String json = packJson(response.getStatus(), "新用户名不能为空", oldUsername, newUsername, newEmail, 0,0);
            response.getWriter().write(json);
            return;
        }

        if (oldUsername == null || "".equals(oldUsername)) {
            String json = packJson(response.getStatus(), "旧用户名不能为空", oldUsername, newUsername, newEmail, 0,0);
            response.getWriter().write(json);
            return;
        }

        //能执行到这里，说明用户传入的新的用户名没有为空

        //调用service层判断新用户名是否在数据库中存在
        UserService userService = new UserServiceImpl();
        OutTradeInfoService infoService = new OutTradeInfoServiceImpl();

        List<Map<String, Object>> userList = userService.userServiceAll();

        //用户是否存在标志点 true 表示存在
        boolean userExists = false;
        for (int i = 0; i < userList.size(); i++) {
            String username = (String)userList.get(i).get("username");
            if (username.equals(newUsername)) {
                userExists = true;
            }

            if (username.equals(oldUsername)) {
                oldEmail = (String)userList.get(i).get("email");
            }
        }

        //邮箱为空
        if (newEmail == null || "".equals(newEmail)) {
            newEmail = oldEmail;
        }

        if (userExists) {
            //用户已经存在
            String json = packJson(response.getStatus(), "用户已存在", oldUsername, newUsername, newEmail, 0, 1);
            response.getWriter().write(json);
            return;
        }

        //判断邮箱是否已经存在
        boolean emailExists = false;
        if (!newEmail.equals(oldEmail)) {
            //新邮箱和旧邮箱不相等，用户输入了邮箱号
            List<Map<String, Object>> list = userService.usernameByEmailSer(newEmail);
            if (list.size() != 0) {
                //数据库中已经存在该邮箱
                String json = packJson(response.getStatus(), "该邮箱已存在", newUsername, newUsername, newEmail, 0, 0);
                response.getWriter().write(json);
                return;
            }
        }

        //运行到这里 说明用户名在数据库中不存在

        //如果新邮箱为空，则使用旧的邮箱号进行修改
        int userNum = 0;
        if (passWord == null || "".equals(passWord)) {
            //密码为空
            userNum = userService.updateOldUsernameSer(newUsername, oldUsername, newEmail);
        }else {
            userNum = userService.updateUserSer(newUsername, oldUsername, newEmail, passWord);
        }

        if (userNum == 0) {
            //更新失败
            String json = packJson(response.getStatus(), "用户表用户名更新失败", oldUsername, newUsername, newEmail, 0, 0);
            response.getWriter().write(json);
            return;
        }

        //运行到这里，用户表更新成功
        int infoNum = infoService.updateUserSer(newUsername, oldUsername, newEmail);
        if (infoNum == 0) {
            //订单表更新失败
            String json = packJson(response.getStatus(), "订单用户名更新失败", oldUsername, newUsername, newEmail, 0, 0);

            //重新修改用户表中的数据
            if (passWord == null || "".equals(passWord)) {
                //密码为空
                int i = userService.updateOldUsernameSer(oldUsername, newUsername, oldEmail);
            }else {
                int i = userService.updateUserSer(oldUsername,newUsername, oldEmail, passWord);
            }

            response.getWriter().write(json);
            return;
        }

        //运行到这里，说明两表中的数据修改成功
        String json = packJson(response.getStatus(), "用户名修改成功", oldUsername, newUsername, newEmail, 1, 0);
        response.getWriter().write(json);
    }

    private String packJson(int code,String message,String username,String newUsername,String newEmail,int update,int userExists) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("username",username);

        mapThree.put("update",update);
        mapThree.put("newUsername",newUsername);
        mapThree.put("newEmail",newEmail);
        mapThree.put("userExists",userExists);


        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
