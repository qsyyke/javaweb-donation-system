package com.yq.web.servlet.cookie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/verifylogin")
public class VerifyLogin extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            //没有cookie 默认就是访问前台页面
            String json = packJson(response.getStatus(), "success", "0", "0", "http://yq.vipblogs.cn");
            response.getWriter().write(json);
            return;
        }

        //cookie不为空
        boolean isTaken = false;
        boolean isAdmin = false;
        boolean isUsername = false;

        String takenStr = "";
        String adminStr = "";
        String usernameStr = "";
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            //获取admin，taken username
            if ("admin".equals(cookieName)) {
                isAdmin = true;
                adminStr = cookie.getValue();
            }
            if ("username".equals(cookieName)) {
                isUsername = true;
                usernameStr = cookie.getValue();
            }
            if ("taken".equals(cookieName)) {
                isTaken = true;
                takenStr = cookie.getValue();
            }
        }

        //获取session中的值
        HttpSession session = request.getSession();
        String sessionTaken = (String)session.getAttribute("taken");
        String sessionUsername = (String)session.getAttribute("username");

        if (!isAdmin) {
            //没有admin信息 直接跳转到前台首页
            String json = packJson(response.getStatus(), "success", "0", "0", "http://yq.vipblogs.cn");
            response.getWriter().write(json);
            return;
        }

        if ("0".equals(adminStr)) {
            //前端访问
            String json = packJson(response.getStatus(), "success", "0", "0", "");
            response.getWriter().write(json);
            return;
        }

        //后面的都是处理操作后台

        //验证cookie中的taken和session中的值
        if (takenStr.equals(sessionTaken) && usernameStr.equals(sessionUsername)) {
            //验证通过
            String json = packJson(response.getStatus(), "success", "1", "1", "");
            response.getWriter().write(json);
            return;
        }

        //不正确 返回登录
        //验证通过
        String json = packJson(response.getStatus(), "success", "1", "0", "http://ht.vipblogs.cn/login.html");
        response.getWriter().write(json);

    }


    /**
     * 通过cookie验证，是操作后台还是前台，通过cookie进行
     * @author chuchen
     * @date 2021/5/4 15:09
     * @param code code
     * @param message message
     * @param isAdmin verify 1表示操作后台，0表示操作前台
     * @param isTaken rightTaken 1表示taken值正确 0表示错误
     * @param url url  taken错误，返回的url
     * @return String
     */
    private String packJson(int code, String message,String isAdmin,String isTaken,String url) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("isAdmin",isAdmin);
        mapThree.put("url",url);
        mapThree.put("isTaken",isTaken);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
