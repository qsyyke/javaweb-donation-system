package com.yq.web.servlet.hero.like;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.domain.DomainProperties;
import com.yq.domain.HeroLike;
import com.yq.domain.PageProperties;
import com.yq.service.HeroCommentService;
import com.yq.service.HeroLikeService;
import com.yq.service.impl.HeroCommentServiceImpl;
import com.yq.service.impl.HeroLikeImplSer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/likeall")
public class HeroLikeAll extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        
        //根据cookie获取UID，如果没哟UID的话，便添加cookie
        Cookie[] cookies = request.getCookies();
        
        int uid = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                
                if (cookieName != null && "uid".equals(cookieName)) {
                    try {
                        uid = Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        if (cookies == null) {
            Properties pro = DomainProperties.getPro();

            String domain = (String) pro.get("domain");
            int maxAge = Integer.parseInt((String) pro.get("maxAge"));
            String path = (String) pro.get("path");

            Cookie cookie = new Cookie("uid",new Random().nextInt(1000*1000)+"");
            cookie.setPath(path);
            cookie.setDomain(domain);
            cookie.setMaxAge(maxAge);

            response.addCookie(cookie);

            //因为用户的UID为空，所以没任何的点赞信息，直接退出
            String json = packJson(response.getStatus(), "fail", new ArrayList<>());
            response.getWriter().write(json);
            return;
        }

        //能运行到这里，说明，存在UID
        HeroLike like = new HeroLike();
        like.setUid(uid);

        HeroLikeService service = new HeroLikeImplSer();
        List<Map<String, Object>> maps = service.likeAllSer(like);

        String json = packJson(response.getStatus(), "success", maps);
        response.getWriter().write(json);



    }

    private String packJson(int code, String message, List<Map<String, Object>> list) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("userList",list);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);
        
        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
