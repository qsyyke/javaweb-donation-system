package com.yq.web.servlet.message.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.DomainProperties;
import com.yq.domain.HeroComment;
import com.yq.service.HeroCommentService;
import com.yq.service.impl.HeroCommentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/commentinsert")
public class CommentInsert extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Properties pro = DomainProperties.getPro();

        String domain = (String) pro.get("domain");
        int maxAge = Integer.parseInt((String) pro.get("maxAge"));
        String path = (String) pro.get("path");

        String identify = request.getParameter("identify");

        //用户唯一id int类型
        String uidStr = request.getParameter("uid");

        //内容
        String content = request.getParameter("content");

        String cookieUsername = "";
        String cookieUidStr = "";
        int uid = 0;
        //用户名
        //String username = "";

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        if (identify == null || "".equals(identify) || uidStr == null || "".equals(uidStr)) {
            String json = packJson(response.getStatus(), "唯一标识为空", "", 0,time);
            response.getWriter().write(json);
            return;
        }

        try {
            uid = Integer.parseInt(uidStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(), "用户唯一标识不正确", "", 0,time);
            response.getWriter().write(json);
            return;
        }

        //true表示，cookie中有username值
        boolean flag = false;
        boolean flagUid = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName != null && "username".equals(cookieName)) {
                    String value = cookie.getValue();
                    String decode = URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
                    cookieUsername = decode;
                    flag = true;
                }
                if (cookieName != null && "uid".equals(cookieName)) {
                    uidStr = cookie.getValue();
                    flagUid = true;
                }
            }
        }

        if (flagUid) {
            try {
                uid = Integer.parseInt(uidStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (!flag) {
            //没有cookie
             cookieUsername = "用户 "+new Random().nextInt(1000*100);

            String encode = URLEncoder.encode(cookieUsername, StandardCharsets.UTF_8.toString());
            Cookie cookieUser = new Cookie("username",encode);
            Cookie cookieUid = new Cookie("uid",uid+"");

            cookieUser.setDomain(domain);
            cookieUid.setDomain(domain);

            cookieUser.setMaxAge(maxAge);
            cookieUid.setMaxAge(maxAge);

            cookieUid.setPath(path);
            cookieUser.setPath(path);

            response.addCookie(cookieUser);
            response.addCookie(cookieUid);
        }

        if (content == null || "".equals(content)) {
            content = "此用户还没发表评论`(〜￣△￣)〜` ";
        }

        HeroCommentService service = new HeroCommentServiceImpl();

        //查询用户是否存在
        String sql = "select uid,cname from heromessage where uid="+uid;
        List<Map<String, Object>> maps = service.commentBySqlSer(sql);

        HeroComment comment = null;
        if (maps.size() == 0) {
            //此用户为新用户
            comment = new HeroComment(identify,cookieUsername,time,content,uid);;
        }else {
            //老用户
            String queryName =  (String) maps.get(0).get("cname");
            comment = new HeroComment(identify,queryName,time,content,uid);;
        }

        int update = service.insertCommentService(comment);

        String msg = "success";

        if (update == 0) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, cookieUsername, update,time);
        response.getWriter().write(json);


    }

    private String packJson(int code, String message, String username,int update,String time) throws JsonProcessingException {
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
        mapThree.put("cdate",time);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
