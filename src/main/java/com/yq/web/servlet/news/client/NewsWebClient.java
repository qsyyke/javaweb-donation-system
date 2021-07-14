package com.yq.web.servlet.news.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yq.dao.NewsDao;
import com.yq.dao.impl.NewsDaoImpl;
import com.yq.service.impl.BroadcastService;
import com.yq.util.Broadcast;
import com.yq.util.impl.BroadcastImpl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类同于用户开启自动更新，每个参数秒使用模拟浏览器发送一个请求
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */
public class NewsWebClient {
     /** 自动爬取间隔时间 */
    private int autoUpdateTime;

     /** 是否停止自动爬取 */
    private boolean isStopSleep;

     /** 是否开启自动爬取 */
    private boolean isOpenUpdate;

     /** 域对象 */
    private ServletContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;

     /** 存储json中的第一级信息 */
    private Map<String,Object> map_one = new HashMap<>();

    /** 存储json中的第二级信息 */
    private Map<String,Object> map_two = new HashMap<>();

     /** json转化对象 */
    private ObjectMapper mapper = new ObjectMapper();


     /** 爬取新闻网页对象 */
    private Broadcast broadcast;

     /** service层调用dao层操作数据 */
    private BroadcastService broadcastService;

     /** dao层对象 */
    private NewsDao newsDao = new NewsDaoImpl();

     /** 测试使用的url链接 */
    private String url = "http://localhost/Hello";

     /** WebClient对象 */
    private com.gargoylesoftware.htmlunit.WebClient client = new com.gargoylesoftware.htmlunit.WebClient(BrowserVersion.CHROME);

    public NewsWebClient () {
    }

    /**
     * 使用参数对象，初始化变量<p>
     * 此构造中会调用initClient()方法，使用WebClient对象只是在做测试的时候使用<p>
     * 对于真正爬取的时候，都不会使用，使用BroadcastService broadcastServic中的方法进行爬取
     * @author chuchen
     * @date 2021/3/27 19:01
     * @param context context
     * @param request request
     * @return null
     */
    public NewsWebClient (ServletContext context,HttpServletRequest request) {
        this.context = context;
        this.request = request;
        initClient();
    }

    /**
     * 测试时，初始化WebClient对象对象，包括是否启用css，JavaScript等
     * @author chuchen
     * @date 2021/3/27 19:05
     * @return void
     */
    public void initClient() {
        isOpenUpdate = (Boolean)context.getAttribute("isOpenUpdate");

        //自动更新时间
        autoUpdateTime = (Integer)context.getAttribute("autoUpdateTime");

        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);

        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);

        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.waitForBackgroundJavaScript(300);
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public HttpServletRequest getRequest () {
        return request;
    }

    public void setRequest (HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse () {
        return response;
    }

    public void setResponse (HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 核心方法，使用while循环启动<p>
     * while (isOpenUpdate)判断，如果自动爬取值为true，则会继续，
     * 调用broadcastService.getItems()的方法获取目标网页的新闻(url在配置文件中)<p>
     * 每一次循环，都会通过域对象获取间隔时间值，是否开启自动爬取，是否结束自动爬取
     * 每次循环都会重新更新域对象
     *
     * @author chuchen
     * @date 2021/3/27 19:06
     * @return boolean
     */
    public boolean isNewsUpdate() {
        //开启自动更新
        while (isOpenUpdate) {
            broadcast = new BroadcastImpl();
            broadcastService = new BroadcastService(broadcast);
            List<Map<String, String>> items = broadcastService.getItems();

            System.out.println("===============================================");
            System.out.println("===============================================");
            System.out.println("===========正在进行插入数据===========");
            //insertNews插入的数据
            int insertNews = newsDao.insertNews(items);
            System.out.println("----------------当前间隔时间"+autoUpdateTime+"----------------");
            System.out.println(items);
            System.out.println("===========插入"+insertNews+"条数数据成功========");
            System.out.println("===============================================");
            System.out.println("===============================================");


            //更新是否开启自动更新
            context = request.getServletContext();
            autoUpdateTime = (Integer)context.getAttribute("autoUpdateTime");
            isOpenUpdate = (Boolean)context.getAttribute("isOpenUpdate");
            isStopSleep = (Boolean) context.getAttribute("isStopSleep");
            if (isStopSleep) {
                System.out.println("===============================================");
                System.out.println("===============================================");
                System.out.println("===============================================");
                System.out.println("===============程序正在推出自动更新=============");
                System.out.println("===============================================");
                System.out.println("===============================================");
                System.out.println("===============================================");;
                //停止自动更新
                isOpenUpdate = false;
                if (response != null) {
                    //设置过response
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run () {
                            response.setContentType("text/json;charset=utf-8");
                            map_one.put("code",response.getStatus());
                            map_two.put("message","success");
                            map_two.put("updateMessage","正在关闭自动更新...");
                            map_one.put("data",map_two);
                            try {
                                String json = mapper.writeValueAsString(map_one);
                                response.getWriter().write(json);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.run();
                }
//                System.out.println("正在推出自动更新");
            }
        }

        return isOpenUpdate;
    }
}
