package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Hero;
import com.yq.domain.HeroComment;
import com.yq.service.HeroCommentService;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroCommentServiceImpl;
import com.yq.service.impl.HeroImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/likeupdate")
public class HeroLikeupdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String identify = request.getParameter("identify");

        //点赞数量
        String likeNum = request.getParameter("likeNum");

        //是否点赞，1表示点赞，0表示没有 如果为空，则没有点赞

        if (identify == null || "".equals(identify)) {
            String json = packJson(response.getStatus(), "fail", identify, 0);
            response.getWriter().write(json);
            return;
        }

        int like = 0;
        try {
            like = Integer.parseInt(likeNum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(), "点赞参数不合法", identify, 0);
            response.getWriter().write(json);
            return;
        }

        HeroService service = new HeroImplService();

        String sql = "select h_like from hero where identify='"+identify+"'";
        List<Map<String, Object>> maps = service.selectBySqlSer(sql);

        if (maps.size() == 0) {
            String json = packJson(response.getStatus(), "更新失败", identify, 0);
            response.getWriter().write(json);
            return;
        }

        //用户的UID 如果存在cookie，便从cookie中提取
        int uid = 0;
        Cookie[] cookies = request.getCookies();
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

        HeroComment comment = new HeroComment();
        comment.setIdentify(identify);
        comment.setUid(uid);
        comment.setIsLike(isLike);

        Hero hero = new Hero();
        hero.setUniqueIdentify(identify);
        hero.setLike(totalLike);

        HeroCommentService  commentService = new HeroCommentServiceImpl();

        //因为如果用户没有评论，没有捐款，就不会有UID，则不会对此作出判断，如果更新点赞状态失败
        int updateLike = commentService.updateLikeSer(comment);

        int update = service.updateLikeSer(hero);

        String msg = "success";

        if (update != 1) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, identify, update);
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, String identify,int update) throws JsonProcessingException {
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
        mapThree.put("identify",identify);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
