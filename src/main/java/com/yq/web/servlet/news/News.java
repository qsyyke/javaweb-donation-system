package com.yq.web.servlet.news;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.dao.NewsDao;
import com.yq.dao.impl.NewsDaoImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * servlet文件，虚拟目录为'/nwes'
 * 返回所有的新闻记录
 * 响应类型为json
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 17:59
 **/

@WebServlet("/news")
public class News extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 返回所有的记录
     * @author chuchen
     * @date 2021/3/27 18:04
     * @param request request
     * @param response response
     * @return void
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");


        //调用service层，获取返回的结果集
        NewsDao newsDao = new NewsDaoImpl();

        //获取请求参数 日期
        Map<String, String[]> parameterMap = request.getParameterMap();

        String month = parameterMap.get("month")[0];
        String year = parameterMap.get("year")[0];
        String day = parameterMap.get("day")[0];

        //生成新的时间  2021.03.28 00:23:01
        String lastDate = year+"."+month+"."+day+" 23:59:59";

        String firstDate = year+"."+month+"."+day+" 00:00:00";

        //System.out.println(lastDate);
        //System.out.println(firstDate);

        List<Map<String, Object>> newsList = newsDao.queryNewsAll(firstDate,lastDate);

        //获取json
        String json = packJson("success", newsList.size(), newsList, response.getStatus());

        //响应
        response.getWriter().write(json);

    }


    /**
     * 用于将数据转化为json
     * @author chuchen
     * @date 2021/3/27 18:09
     * @param isSuccess isSuccess 是否成功 值为success or fail
     * @param totalCount totalCount 所有的新闻的记录数
     * @param newsList newsList 所有新闻集合
     * @param Status Status  状态码 根据response获取
     * @return String 返回json
     */
    private String packJson(String isSuccess,
            int totalCount, List<Map<String,Object>> newsList,int Status) throws JsonProcessingException {
        //json
        ObjectMapper mapper = new ObjectMapper();

        Map<String,Object> news_first = new HashMap<>();
        Map<String,Object> news_two = new HashMap<>();
        Map<String,Object> news_three = new HashMap<>();

        //放入第三个集合中
        news_three.put("newsList",newsList);

        //放入第二个集合
        news_two.put("totalCount",totalCount);
        news_two.put("message",isSuccess);
        news_two.put("list",news_three);

        //放入第一个集合中
        news_first.put("data",news_two);
        news_first.put("code",Status);

        String json = mapper.writeValueAsString(news_first);

        return json;
    }
}
