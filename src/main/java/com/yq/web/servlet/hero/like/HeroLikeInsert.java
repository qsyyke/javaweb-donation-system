package com.yq.web.servlet.hero.like;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.DomainProperties;
import com.yq.domain.Hero;
import com.yq.domain.HeroComment;
import com.yq.domain.HeroLike;
import com.yq.service.HeroCommentService;
import com.yq.service.HeroLikeService;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroCommentServiceImpl;
import com.yq.service.impl.HeroImplService;
import com.yq.service.impl.HeroLikeImplSer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 根据cookie获取UID，identity，如果没有UID，则插入一个UID，并且将这个UID添加到cookie中
 * @author 青衫烟雨客 程钦义
 * @date 2021/5/6 18:08
 **/

@WebServlet("/likeinsert")
public class HeroLikeInsert extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //点赞数量
        String likeStr = request.getParameter("like");

        String identify = request.getParameter("identify");

        if (identify == null || "".equals(identify) || likeStr == null || "".equals(likeStr)) {
            String json = packJson(response.getStatus(), "参数有误", "", 0);
            response.getWriter().write(json);
            return;
        }

        int like = 0;
        try {
            like = Integer.parseInt(likeStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        int uid = 0;
        //true表示，cookie中有username值
        boolean flagUid = false;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName != null && "uid".equals(cookieName)) {
                    uid = Integer.parseInt(cookie.getValue());
                    flagUid = true;
                }
            }
        }

        if (!flagUid) {
            Properties pro = DomainProperties.getPro();

            String domain = (String) pro.get("domain");
            int maxAge = Integer.parseInt((String) pro.get("maxAge"));
            String path = (String) pro.get("path");

            uid = getUid();

            Cookie cookieUid = new Cookie("uid",uid+"");
            cookieUid.setDomain(domain);
            cookieUid.setMaxAge(maxAge);
            cookieUid.setPath(path);
            response.addCookie(cookieUid);
        }

        //能运行到这里，说明uid已经存在或者已将添加到cookie中，并且点赞数量正确
        HeroService service = new HeroImplService();

        String sql = "select h_like from hero where identify='"+identify+"'";
        List<Map<String, Object>> maps = service.selectBySqlSer(sql);

        if (maps.size() == 0) {
            String json = packJson(response.getStatus(), "更新失败", identify, 0);
            response.getWriter().write(json);
            return;
        }

        //数据库中此人物存在的点赞数
        Integer h_like = (Integer) maps.get(0).get("h_like");

        if (h_like == null) {
            h_like = 0;
        }

        int totalLike = h_like+like;
        int isLike = 0;
        if (like > 0) {
            isLike = 1;
        }

        Hero hero = new Hero();
        hero.setLike(totalLike);
        hero.setUniqueIdentify(identify);

        int heroUpdate = service.updateLikeSer(hero);

        HeroLike heroLike = new HeroLike();
        heroLike.setUid(uid);
        heroLike.setIdentify(identify);
        heroLike.setIsLike(isLike+"");

        //先判断数据库中是否已经插入过这条数据，如果插入过，则进行更新，如果没有，则进行插入
        String selectIsInsert = "select * from h_like where uid="+uid+" and identify='"+identify+"'";

        HeroLikeService likeService = new HeroLikeImplSer();
        List<Map<String, Object>> selectIsInsertMaps = likeService.likeBySqlSer(selectIsInsert);

        if (selectIsInsertMaps.size() > 0) {
            //已经存在，则进行更新
            int updateLikeNum = likeService.updateLikeSer(heroLike);
        }else {
            int heroLikeUpdate = likeService.insertLikeService(heroLike);
        }

        String msg = "success";

        if (heroUpdate == 0) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, uid+"", heroUpdate);
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, String uid,int update) throws JsonProcessingException {
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
        mapThree.put("uid",uid);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }

    /**
     * 返回一个UID
     * @author chuchen
     * @date 2021/5/6 18:18
     * @return int
     */
    private int getUid() {
        HeroCommentService commentService = new HeroCommentServiceImpl();

        int uid = 0;
        boolean flag = false;
        while (!flag) {
            uid = new Random().nextInt(1000 * 1000);
            String sql = "select * from heromessage where uid="+uid;
            List<Map<String, Object>> maps = commentService.commentBySqlSer(sql);
            if (maps.size() ==0) {
                //此uid在数据库中不存在
                flag = true;
            }
        }
        return uid;
    }
}
