package com.yq.web.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.DomainProperties;
import com.yq.service.UserService;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 获取用户名参数 从数据库中查询是否存在相同用户名 根据用户名进行获取
 *
 * 功能只能用于判断用户是否存在
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/10 15:06
 **/

@WebServlet("/queryuser")
public class QueryUser extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String username = request.getParameter("username");

        if (username == null ||"".equals(username)) {
            String json = packJson(response.getStatus(), "参数不合法", "", 0);
            response.getWriter().write(json);
            return;
        }

        //true表示有相同 false表示没有相同
        boolean isExists = false;
        UserService service = new UserServiceImpl();
        List<Map<String, Object>> list = service.userServiceAll();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String db_username = (String)map.get("username");

            if (db_username.equals(username)) {
                //如果该用户名和数据库中有相同的
                isExists = true;
                break;
            }
            System.out.println(db_username);
        }

        if (isExists == true) {
            //有相同
            String json = packJson(response.getStatus(), "用户名已存在", username, 1);
            response.getWriter().write(json);
            return;
        }

        //如果能够运行到这里 说明用户名不存在
        String json = packJson(response.getStatus(), "用户名未存在", username, 0);
        response.getWriter().write(json);

        
    }

    private String packJson(int code,String message,String username,int isExists) throws JsonProcessingException {
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

        mapThree.put("isExists",isExists);


        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
