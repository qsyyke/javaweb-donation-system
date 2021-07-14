package com.yq.web.servlet.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yq.dao.impl.LoginDaoImpl;
import com.yq.domain.DomainProperties;
import com.yq.domain.User;
import com.yq.service.LoginService;
import com.yq.service.impl.LoginServiceImpl;
import com.yq.util.impl.OutTradeNoRandom;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 这个应用是用于Vue的登录验证
 * 用于验证用户登录的信息，用户请求的信息包括<p>
 * 验证码，邮箱，用户名，密码，选择。验证码，选择和邮箱是必须的，<p></p>
 * 通过用户的select值，可以判断用户的操作，login代表登录，forget代表找回密码，register代表注册操作<p></p>
 *
 * 全部使用json的形式，此文件用于发送登录的json，如果用户存在，则返回登录成功，如果失败，则返回失败，将此信息存入至json中
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/30 21:04
 **/

@WebServlet("/login")
public class VueLogin extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     *
     * @param request
     * @param response
     * @return void
     * 用户点击登录 进行数据处理 向数据库查询数据，和用户参数值进行比较
     * 正确 请求转发到首页 并设置用户状态session进行保存
     * 失败 重定向到登录界面
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Properties pro = DomainProperties.getPro();

        String domain = (String) pro.get("domain");
        int maxAge = Integer.parseInt((String) pro.get("maxAge"));
        String path = (String) pro.get("path");

        //获取密码
        String pwd = request.getParameter("password");

        //获取用户名
        String username = request.getParameter("username");

        if (pwd == null ||  "".equals(pwd) || username == null || "".equals(username)) {
            String json = packJson(response.getStatus(), "参数错误", "", null,null,0);
            response.getWriter().write(json);
            return;
        }

        //创建对象
        User userLogin = new User();

        userLogin.setUsername(username);
        userLogin.setPassword(pwd);

        //调用service层
        LoginService loginService = new LoginServiceImpl();

        //获取结果集
        List<User> logins = loginService.getLogin(userLogin);

        Cookie cookieLogin = new Cookie("taken","");
        Cookie cookieUser = new Cookie("username","");
        Cookie cookieAdmin = new Cookie("admin","1");

        cookieLogin.setDomain(domain);
        cookieUser.setDomain(domain);
        cookieAdmin.setDomain(domain);

        cookieLogin.setMaxAge(maxAge);
        cookieUser.setMaxAge(maxAge);
        cookieAdmin.setMaxAge(maxAge);

        cookieLogin.setPath(path);
        cookieUser.setPath(path);
        cookieAdmin.setPath(path);

        response.addCookie(cookieAdmin);

        if (logins.size() == 0) {
            //用户不存在
            String json = packJson(response.getStatus(), "用户不存在","", username, pwd,0);
            response.getWriter().write(json);
            response.addCookie(cookieLogin);
            response.addCookie(cookieUser);
            return;
        }

        //验证成功
        //随机生成一个taken
        OutTradeNoRandom random = new OutTradeNoRandom();
        String outTradeNo = random.getOutTradeNo();

        cookieLogin.setValue(outTradeNo);
        cookieUser.setValue(username);

        response.addCookie(cookieLogin);
        response.addCookie(cookieUser);

        HttpSession session = request.getSession();
        session.setAttribute("taken",outTradeNo);
        session.setAttribute("username",username);

        String json = packJson(response.getStatus(), "用户存在", outTradeNo,username, pwd,1);
        response.getWriter().write(json);

    }
    /**
     * 封装json
     * boolean isVerCode验证码是否正确，正确1 否则0<p></p>
     * boolean isSend 是否发送过验证码 1 发送过 0 没有
     * @author chuchen
     * @date 2021/3/30 21:32
     * @return String
     */
    private String packJson(int code,String message,String taken,String username,String password,int isExists) throws JsonProcessingException {
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
        mapThree.put("password",password);
        mapThree.put("isExists",isExists);
        mapThree.put("taken",taken);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}

