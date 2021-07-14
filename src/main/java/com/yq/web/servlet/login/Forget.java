package com.yq.web.servlet.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.LoginService;
import com.yq.service.impl.LoginServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/forget")
public class Forget extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取密码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        LoginService loginService = new LoginServiceImpl();

        String password = request.getParameter("password");

        //获取邮箱
        String email = request.getParameter("email");
        System.out.println(email);

        //获取用户输入的验证码
        String verCode = request.getParameter("vercode");

        HttpSession session = request.getSession();

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
            String json = packJson(response.getStatus(),"验证码发送失败",email,0,0);
            response.getWriter().write(json);
            return;
        }

        //能够运行到这里，说明，验证码发送成功
        //判断验证码是否相同
        if (!verCode.equals(send_yzm) || send_yzm == null) {
            System.out.println("验证码不正确");
            //验证码不对  重定向到登录界面 并将用户提交的信息以json返回 只返回用户名 密码
            //重定向
            String json = packJson(response.getStatus(),"验证码错误",email,0,1);
            response.getWriter().write(json);
            return;
        }

        //能够运行到这里，说明验证码正确
        //调用service层

        //判断是否存在此邮箱
        int selectMailNumber = loginService.selectMailService(email);
        if (selectMailNumber == 0) {
            //用户不存在
            String json = packJson(response.getStatus(), "邮箱不存在", email, 1, 1);
            response.getWriter().write(json);
            return;
        }

        int updateNumber = loginService.updateUserService(email, password);

        //获取返回数据
        String json = packJson(response.getStatus(), "修改成功", email, 1, 1);
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
    private String packJson(int code,String message,String email,int isVerCode,int isSend) throws JsonProcessingException {
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
