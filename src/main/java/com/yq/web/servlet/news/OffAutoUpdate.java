package com.yq.web.servlet.news;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * servlet文件
 * 关闭自动更新
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 18:12
 **/

@WebServlet("/offautoupdate")
public class OffAutoUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 根据传入的参数isStopSleep值为 true or false 将NewsWebClient的自动更新关闭<p>
     * 原理 参数一定要传递true or false，没有进行判断<p>
     * 根据参数，获取ServletContext对象，并设置域对象中的关闭更新<p>
     * context.setAttribute("isStopSleep",isStopSleep);
     * NewsWebClient代码块获取域对象中的isStopSleep值，完成自动更新的关闭
     *
     * @author chuchen
     * @date 2021/3/27 18:15
     * @param request request
     * @param response response
     * @return void
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取参数
        //获取是否开启自动更新
        boolean isStopSleep = Boolean.parseBoolean(request.getParameter("isStopSleep"));

        //获取顶级域对象
        ServletContext context = request.getServletContext();
        //设置自动更新时间
        context.setAttribute("isStopSleep",isStopSleep);

        //启动模拟浏览器，
        String json = packJson("success", response.getStatus(),isStopSleep);
        response.getWriter().write(json);

    }

    /**
     * 封装响应体内容
     * 返回json数据
     * @author chuchen
     * @date 2021/3/27 18:21
     * @param isSuccess isSuccess 值为success or fail
     * @param status status 响应状态码
     * @param isStopSleep isStopSleep 用户输入的值
     * @return String 转化后的json数据
     */
    private String packJson(String isSuccess,int status,boolean isStopSleep) throws JsonProcessingException {
        Map<String,Object> map_one = new HashMap<>();
        Map<String,Object> map_two = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        map_two.put("message",isSuccess);
        map_two.put("isStopSleep",isStopSleep);

        map_one.put("code",status);
        map_one.put("data",map_two);

        String json = mapper.writeValueAsString(map_one);
        return json;
    }

}
