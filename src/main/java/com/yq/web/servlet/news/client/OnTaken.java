package com.yq.web.servlet.news.client;
/**
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/taken")
public class OnTaken extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //这是第一次进行的，运行这个就可以开启自动更新
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Map<String,Object> map = new HashMap<>();
        ServletContext context = request.getServletContext();

        //获取taken值
        String taken = request.getParameter("taken");
        if (!("vipblogs1202".equals(taken))) {
            map.put("message","开启失败");
            map.put("isRight",false);
            String json = new ObjectMapper().writeValueAsString(map);
            response.getWriter().write(json);
            return;
        }

        context.setAttribute("isOpenUpdate",true);
        context.setAttribute("autoUpdateTime",7200000);
        context.setAttribute("isStopSleep",false);
        map.put("message","开启成功");
        String json = new ObjectMapper().writeValueAsString(map);
        response.getWriter().write(json);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run () {
                NewsWebClient newsWebClient = new NewsWebClient(request.getServletContext(),request);
                boolean newsUpdate = newsWebClient.isNewsUpdate();
            }
        });

        thread.run();
    }
}
