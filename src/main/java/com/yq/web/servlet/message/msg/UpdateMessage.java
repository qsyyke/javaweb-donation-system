package com.yq.web.servlet.message.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Message;
import com.yq.service.MessageService;
import com.yq.service.impl.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 修改留言表中的数据 只能修改用户省份和留言信息 用户名不可以修改
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/9 20:23
 **/


@WebServlet("/upms")
public class UpdateMessage extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取用户省份
        String province = request.getParameter("province");

        String username = request.getParameter("username");

        //获取留言信息
        String message = request.getParameter("message");

        if (username == null || "".equals(username)) {
            String json = packJson(response.getStatus(), "用户名不能为空", username, "", "", 0);
            response.getWriter().write(json);

            return;

        }

        if (province == null) {
            province = "";
        }

        if (message == null) {
            message = "";
        }

        Message ms = new Message();
        ms.setUsername(username);
        ms.setMessage(message);
        ms.setProvince(province);

        //调用service层
        MessageService service = new MessageServiceImpl();
        int updateNum = service.updateMessageInfoSer(ms);

        if (updateNum == 0) {
            String json = packJson(response.getStatus(), "fail", username, message, province, updateNum);
            response.getWriter().write(json);
            return;
        }

        //能运行到这里 说明修改数据的条数不是0

        String json = packJson(response.getStatus(), "success", username, message, province, updateNum);
        response.getWriter().write(json);

    }

    private String packJson(int code,String message, String username,String newMessage,String newProvince,int updateNum) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("updateNum",updateNum);
        mapThree.put("province",newProvince);
        mapThree.put("ms",newMessage);
        mapThree.put("username",username);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
