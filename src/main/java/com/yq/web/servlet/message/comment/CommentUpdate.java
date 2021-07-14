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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/commentupdate")
public class CommentUpdate extends HttpServlet {
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

        String username = request.getParameter("username");

        String content = request.getParameter("content");

        if (identify == null || "".equals(identify) || uidStr == null || "".equals(uidStr)) {
            String json = packJson(response.getStatus(), "唯一标识为空", username, 0);
            response.getWriter().write(json);
            return;
        }

        int uid = 0;
        try {
            uid = Integer.parseInt(uidStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(), "用户唯一标识不正确", username, 0);
            response.getWriter().write(json);
            return;
        }

        if (content == null || "".equals(content)) {
            content = "此用户还没发表评论`(〜￣△￣)〜` ";
        }

        HeroCommentService service = new HeroCommentServiceImpl();
        if (username == null || "".equals(username)) {
            String sql = "select cname,uid from heromessage where uid="+uid;
            List<Map<String, Object>> maps = service.commentBySqlSer(sql);
            if (maps.size() != 0) {
                //获取名字
                username = (String) maps.get(0).get("cname");
            }
        }

        HeroComment comment = new HeroComment();
        comment.setcContent(content);
        comment.setUid(uid);
        comment.setcName(username);
        comment.setIdentify(identify);

        int update = service.updateCommentSer(comment);

        String msg = "success";

        if (update == 0) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, username, update);
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
