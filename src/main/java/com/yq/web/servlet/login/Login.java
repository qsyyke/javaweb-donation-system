package com.yq.web.servlet.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.yq.dao.impl.LoginDaoImpl;
import com.yq.domain.User;
import com.yq.service.LoginService;
import com.yq.service.impl.LoginServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于验证用户登录的信息，用户请求的信息包括<p>
 * 验证码，邮箱，用户名，密码，选择。验证码，选择和邮箱是必须的，<p></p>
 * 通过用户的select值，可以判断用户的操作，login代表登录，forget代表找回密码，register代表注册操作<p></p>
 *
 * 全部使用json的形式，此文件用于发送登录的json，如果用户存在，则返回登录成功，如果失败，则返回失败，将此信息存入至json中
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/30 21:04
 **/

@WebServlet("/clogin")
public class Login extends HttpServlet {
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
        //判断验证码是否正确

        //userInformation用户请求体集合
        Map<String, String[]> userInformation = request.getParameterMap();

        //获取用户输入的验证码
        String verCode = userInformation.get("verCode")[0];

        //获取邮箱
        String email = userInformation.get("email")[0];

        //获取密码
        String pwd = userInformation.get("password")[0];

        //获取用户名
        String username = userInformation.get("username")[0];



        HttpSession session = request.getSession();
        System.out.println("session: "+session);

        //获取发送的验证码
        String send_yzm = (String)session.getAttribute("yzm&" + email);;

        //获取是否发送过值
        int isSuccessSend = 0;
        try {
            isSuccessSend = (Integer)session.getAttribute("isSuccessSend&" + email);
        } catch (NullPointerException e) {
            e.printStackTrace();
            isSuccessSend = 0;
        }

        if (isSuccessSend == 0) {
            //验证码没有发送成功
            String json = packJson(response.getStatus(),"验证码发送失败","","",0,0);
            response.getWriter().write(json);
            return;
        }

        //能够运行到这里，说明，验证码发送成功
        //判断验证码是否相同
        if (!verCode.equals(send_yzm) || send_yzm == null) {
            System.out.println("验证码不正确");
            //验证码不对  重定向到登录界面 并将用户提交的信息以json返回 只返回用户名 密码
            //重定向
            String json = packJson(response.getStatus(), "验证码错误", "", "", 0, 1);
            response.getWriter().write(json);
            return;
        }

        //能够运行到这里，说明验证码正确

        //创建对象
        User userLogin = new User();

        //封装请求参数
        try {
            BeanUtils.populate(userLogin,userInformation);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(userLogin);

        //调用service层
        LoginService loginService = new LoginServiceImpl();

        //获取结果集
        List<User> logins = loginService.getLogin(userLogin);
        if (logins.size() >1 || logins.size() == 0) {
            int selectMailNumber = loginService.selectMailService(email);
            if (selectMailNumber == 1) {
                //邮箱存在
                String json = packJson(response.getStatus(), "密码错误",email,pwd,1,1);
                response.getWriter().write(json);
                return;
            }else {
                //邮箱不存在
                String json = packJson(response.getStatus(), "邮箱不存在",email,pwd,1,1);
                response.getWriter().write(json);
                return;
            }
        }

        User user_get = logins.get(0);

        /*//比较
        if (!(user_get.getEmail().equals(userLogin.getEmail())) &&
                    !(user_get.getPassword().equals(userLogin.getPassword()))) {
            //邮箱或者密码不正确
            String json = packJson(response.getStatus(), "邮箱或密码错误", email, pwd, 1,1);
            response.getWriter().write(json);
            return;
        }*/

        /*if (!(user_get.getEmail().equals(userLogin.getEmail()))) {
            //用户名不正确
            String json = packJson(response.getStatus(), "登录失败", uname, pwd, verCode);
            response.getWriter().write(json);
            return;
        }*/
        //用户名，密码判断处理

        //验证成功
        String json = packJson(response.getStatus(), "登录成功", email, pwd, 1,1);
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
    private String packJson(int code,String message,String email,String password,int isVerCode,int isSend) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("uname",email);
        mapThree.put("pwd",password);

        //验证码是否正确
        mapThree.put("verCode",isVerCode);

        //是否发送过验证码
        mapThree.put("isSend",isSend);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
