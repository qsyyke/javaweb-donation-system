package com.yq.web.servlet.alipayservlet.Operation;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.OutTradeInfoService;
import com.yq.service.UserService;
import com.yq.service.impl.OutTradeInfoServiceImpl;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修改用户的订单和用户名
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/28 19:07
 **/

@WebServlet("/tradeupdate")
public class TradeUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //调用service层判断新用户名是否在数据库中存在
        UserService userService = new UserServiceImpl();
        OutTradeInfoService infoService = new OutTradeInfoServiceImpl();

        //获取用户旧的用户名
        String oldUsername = request.getParameter("oldUsername");

        //获取用户新的用户名
        String newUsername = request.getParameter("newUsername");

        //获取邮箱号
        String newEmail = request.getParameter("newEmail");

        //获取捐赠地址
        String donateAddress = request.getParameter("donateAddress");

        //获取订单号
        String outTradeNo = request.getParameter("outTradeNo");
        if (outTradeNo == null ||"".equals(outTradeNo)) {
            String json = packJson(response.getStatus(), "请传入订单", "", "", "", 0, 1);
            response.getWriter().write(json);
            return;
        }


        if (donateAddress == null || "".equals(donateAddress)) {
            //捐赠地址为空或者null
            List<Map<String, Object>> mapList = infoService.selectOutTradeNoInfo(outTradeNo);
            if (mapList.size() == 0 || mapList.size() >1) {
                //订单不存在或者存在多个
                String json = packJson(response.getStatus(), "订单错误", "", "", "", 0, 1);
                response.getWriter().write(json);
                return;
            }

            //订单值存在一个
            try {
                donateAddress = (String)mapList.get(0).get("donateaddress");
            } catch (Exception e) {
                e.printStackTrace();
                donateAddress = "全国";
            }
        }

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
        int updateAddress = infoService.updateAddressSer(outTradeNo, donateAddress);
        if (infoNum == 0 || updateAddress == 0) {
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

