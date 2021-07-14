package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取用户的cookie，根据cookie中的UID，从数据库中查询用户点赞情况
 * @author 青衫烟雨客 程钦义
 * @date 2021/5/6 17:13
 **/


@WebServlet("/herocommentallbyuid")
public class HeroCommentAllCountBySqlUid extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Cookie[] cookies = request.getCookies();

        int uid = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();

                if (cookieName != null && "uid".equals(cookieName)) {
                    uid = Integer.parseInt(cookie.getValue());
                }
            }
        }

        String sql = "SELECT * from heromessage where uid ="+uid;
        HeroService service = new HeroImplService();
        List<Map<String, Object>> maps = service.selectBySqlSer(sql);

        String msg = "success";
        if (maps.size() == 0) {
            msg = "fail";
        }

        String json = packJson(response.getStatus(), msg, maps, maps.size());
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, List<Map<String, Object>> list,int totalCount) throws JsonProcessingException {
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
        mapTwo.put("totalCount",totalCount);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
