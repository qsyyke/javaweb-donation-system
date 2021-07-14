package com.yq.web.servlet.news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.web.servlet.news.client.NewsWebClient;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 开启自动更新 这是一个servlet文件
 * 虚拟目录为`/onautoupdate`
 * 参数autoupdatetime 自动更新间隔时间
 * isopenupdate 是否自动更新 值为true or false 有参数合法判断
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 18:23
 **/


@WebServlet("/onautoupdate")
public class OnAutoUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 此类用于开启自动更新并且设置自动更新时间
     *根据传入的参数，间隔更新时间，还有是否自动更新，如果自动更新为false，那么将不会启动模拟爬取网页的线程<p>
     * 返回json字符串<p>
     * 如果间隔时间有误，输入字符串等等，呆滞异常抛出，则会返回json字符串<p>
     * 如果都成功(isopenupdate无论是true还是false)，都会将参数值传入
     * ServletContext域对象中<p>
     * 启动爬取网页是通过线程<p>
     * <code>OnAutoUpdateThread thread = new OnAutoUpdateThread(context,waitNewsTimeStr,request);
     *thread.run();</code>
     *
     * @author chuchen
     * @date 2021/3/27 18:24
     * @param request request
     * @param response response
     * @return void
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取自动更新时间
        //这里也不需要进行判断，因为没有传入参数，那么null转换会出现异常，也不会进行启动
        String waitNewsTime = request.getParameter("autoupdatetime");

        //获取是否开启自动更新
        boolean isopenupdate = Boolean.parseBoolean(request.getParameter("isopenupdate"));

        //判断参数是否合法
        //更新间隔时间是否合理
        boolean isWaitNewsTimeRight = true;
        int waitNewsTimeStr = 0;

        try {
            waitNewsTimeStr = Integer.parseInt(waitNewsTime);
        }catch (NumberFormatException e) {
            e.printStackTrace();
            isWaitNewsTimeRight = false;
        }

        if (!isWaitNewsTimeRight) {
            //更新时间参数传入不正确
            String json = packJson(0,"fail",response.getStatus(),false);
            response.getWriter().write(json);
            return;
        }

        //判断自动更新是否合法
        //能够运行到这里，说明参数合法
        //获取顶级域对象
        ServletContext context = request.getServletContext();

        //设置自动更新时间
        context.setAttribute("autoUpdateTime",waitNewsTimeStr);
        context.setAttribute("isOpenUpdate",isopenupdate);
        context.setAttribute("isStopSleep",false);

        if (!isopenupdate) {
            String json = packJson(waitNewsTimeStr, "fail", response.getStatus(),isopenupdate);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(waitNewsTimeStr, "success", response.getStatus(),isopenupdate);
        response.getWriter().write(json);

        //启动模拟器线程
        OnAutoUpdateThread thread = new OnAutoUpdateThread(context,request);
        thread.setName("自动爬取新闻线程");
        thread.start();
        return;
    }

    /**
     * 封装json数据
     * @author chuchen
     * @date 2021/3/27 18:42
     * @param waitNewsTime waitNewsTime 等待间隔时间 通过线程休眠做到
     * @param isSuccess isSuccess 值为success or fail
     * @param status status 响应状态码
     * @param isOpenUpdate isOpenUpdate 是否开启自动更新 值为 true or false
     * @return String 封装的json
     */
    private String packJson(int waitNewsTime,String isSuccess,int status,boolean isOpenUpdate) throws JsonProcessingException {
        Map<String,Object> map_one = new HashMap<>();
        Map<String,Object> map_two = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        map_two.put("message",isSuccess);
        map_two.put("waitNewsTime",waitNewsTime);
        map_two.put("isOpenUpdate",isOpenUpdate);

        map_one.put("code",status);
        map_one.put("data",map_two);

        String json = mapper.writeValueAsString(map_one);
        return json;
    }
}

/**
 * 线程类，用于启动爬取网页线程
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 18:44
 **/

class OnAutoUpdateThread extends Thread {
    private NewsWebClient newsWebClient;

     /** 域对象 */
    private ServletContext context;

     /** request请求对象 */
    private HttpServletRequest request;

    /**
     * 构造方法，用于初始化变量
     * @author chuchen
     * @date 2021/3/27 18:47
     * @param context context
     * @param request request
     * @return null
     */
    public OnAutoUpdateThread (ServletContext context, HttpServletRequest request) {
        this.context = context;
        this.request = request;
        newsWebClient = new NewsWebClient(request.getServletContext(), request);
    }

    /**
     * run()方法<p>
     * <code>NewsWebClient newsWebClient = new NewsWebClient(request.getServletContext(), 5000,request);</code>
     *         <p>newsWebClient.isNewsUpdate();
     * @author chuchen
     * @date 2021/3/27 18:47
     * @return void
     */
    @Override
    public void run () {
        newsWebClient.isNewsUpdate();
    }
}

