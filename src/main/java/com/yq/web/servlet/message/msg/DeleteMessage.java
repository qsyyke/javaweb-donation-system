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
 * 用于删除用户留言数据 根据用户名
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/9 20:40
 **/

@WebServlet("/dtms")
public class DeleteMessage extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String username = request.getParameter("username");

        if (username == null || "".equals(username)) {
            String json = packJson(response.getStatus(), "用户名传入失败", username, 0);
            response.getWriter().write(json);
            return;
        }

        Message ms = new Message();
        ms.setUsername(username);


        MessageService service = new MessageServiceImpl();
        int deleteNum = service.deleteMessageInfoSer(ms);

        String json = packJson(response.getStatus(), "success", username, deleteNum);
        response.getWriter().write(json);


    }

    private String packJson(int code,String message, String username,int deleteNum) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("deleteNum",deleteNum);
        mapThree.put("username",username);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
