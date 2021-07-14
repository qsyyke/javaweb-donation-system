package com.yq.web.servlet.message.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.HeroComment;
import com.yq.service.HeroCommentService;
import com.yq.service.impl.HeroCommentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/commentdelete")
public class CommentDelete extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String identify = request.getParameter("identify");

        //用户唯一id
        String uidStr = request.getParameter("uid");

        if (identify == null || "".equals(identify) || uidStr == null || "".equals(uidStr)) {
            String json = packJson(response.getStatus(), "唯一标识为空", "", 0);
            response.getWriter().write(json);
            return;
        }

        int uid = 0;
        try {
            uid = Integer.parseInt(uidStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(), "用户唯一标识不正确", "", 0);
            response.getWriter().write(json);
            return;
        }


        HeroCommentService service = new HeroCommentServiceImpl();

        HeroComment comment = new HeroComment();
        comment.setUid(uid);
        comment.setIdentify(identify);

        int update = service.deleteCommentSer(comment);

        String msg = "success";

        if (update == 0) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, "", update);
        response.getWriter().write(json);

    }

    private String packJson(int code, String message, String username,int update) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("update",update);
        mapThree.put("username",username);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
