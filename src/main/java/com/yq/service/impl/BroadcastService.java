package com.yq.service.impl;

import com.gargoylesoftware.htmlunit.html.*;
import com.yq.dao.NewsDao;
import com.yq.dao.impl.NewsDaoImpl;
import com.yq.util.Broadcast;
import com.yq.util.impl.BroadcastImpl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * 获取数据，并且调用dao层将数据插入到数据库中
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 17:05
 **/

public class BroadcastService {
     /** 接口参数变量 */
    private Broadcast broadcast;
     /** HtmlPage对象 */
    private HtmlPage htmlPage;

     /** 新闻发布时间 */
    private String time;

     /** 最新发布的时间，通过这个筛选 */
    private String newestTime;

     /** 新闻标题点击链接 */
    private String news_a;

     /** 新闻标题 */
    private String news_title;

     /** List<Map<String,String>>集合用于保存所有的数据
      * 使用循环向里面放入一个map集合数据，每一个都是一个map集合
      * 此map集合中，都有title,href,time更新时间键
      * */
    List<Map<String,String>> news_list = new ArrayList<>();

    /**
     * 此构造方法用于初始化Broadcast对象和HtmlPage对象，使用于，不需要自动提供HtmlPage的情况
     * @author chuchen
     * @date 2021/3/27 17:10
     * @param broadcast broadcast
     * @param htmlPage htmlPage  自己提供的HtmlPage对象
     * @return null
     */
    public BroadcastService (Broadcast broadcast, HtmlPage htmlPage) {
        this.broadcast = broadcast;
        this.htmlPage = htmlPage;
    }


    /**
     * 构造，初始化Broadcast对象
     * @author chuchen
     * @date 2021/3/27 17:12
     * @param broadcast broadcast
     * @return null
     */
    public BroadcastService (Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public Broadcast getBroadcast () {
        return broadcast;
    }

    public void setBroadcast (Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    /**
     * 用于获取爬取到的页面的HtmlPage对象
     * @author chuchen
     * @date 2021/3/27 17:13
     * @return HtmlPage HtmlPage对象
     */
    public HtmlPage getHtmlPage () {
        return htmlPage;
    }

    public void setHtmlPage (HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    /**
     * 此方法用于获取新闻时间，标题，并将其存入map集合中
     * 通过xpath选择器获取其中的节点
     * 使用click()方法进行点击，获取到所有能看到的每个新闻li
     * 并且使用Jsoup将HTMLPage对象转化成document对象，通过循环方式
     * 获取每一个li，并将每一个li中的数据，发布时间，标题，链接放入map集合中
     * 最终放入List<Map<String,String>>对象中
     * @author chuchen
     * @date 2021/3/26 22:21
     * @return java.util.Map<java.lang.String,java.lang.String> 每个li的map集合的List集合
     */
    public List<Map<String,String>> getItems() {
        htmlPage = broadcast.getHtmlPage();

        //获取点击
        List<HtmlDivision> list = htmlPage.getByXPath("//div[@class='more_news']");
        HtmlElement button = list.get(0);
        //pageAll对象代表所有的节点
        HtmlPage pageAll = null;
        try {
            for (int i = 0; i < 3; i++) {
                pageAll = button.click();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        //通过id获取ul节点
        DomElement ul = pageAll.getElementById("time_lists");
        Document document = Jsoup.parse(ul.asXml());

        Elements lis = document.getElementsByTag("li");

        for (Element element : lis) {
            //时间
            String time = element.getElementsByClass("time").text();

            //获取标题
            String title = element.getElementsByClass("tit").text();

            //获取连接
            String href = element.getElementsByClass("con").attr("data-url");

            Map<String,String> map = new HashMap<>();
            map.put("date",time);
            map.put("title",title);
            map.put("href",href);
            news_list.add(map);
        }
        return news_list;
    }

    /*public static void main (String[] args) {

        //开启一个线程

//        while (true) {
        new NewsThread().run();

//        }

    }*/
}

