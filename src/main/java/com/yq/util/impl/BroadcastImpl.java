package com.yq.util.impl;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yq.util.Broadcast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 此类是Broadcast的实现类
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 16:47
 **/

public class BroadcastImpl implements Broadcast {
     /** 目标网址 */
    private String url;

    /*//等待时间，用于JavaScript的数据渲染
    private int waitTime;*/

     /** 等待JavaScript加载时间 */
    private int waitForBackgroundJavaScript;


     /** 加载配置文件 */
    private static Properties pro;
     /** 模拟浏览器对象 */
    private WebClient client;
     /** HTMLPage对象 */
    private HtmlPage page;

     /** 是否启动css，设置为false，表示不使用css */
    private boolean cssEnabled;
     /** 是否使用JavaScript文件，对于动态添加的HTML，此值必须为true */
    private boolean javaScriptEnabled;

    /**
     * 构造方法，用于设置此类中定义的参数，通过配置文件进行加载，并获取设置
     * @author chuchen
     * @date 2021/3/27 16:48
     * @return null
     */
    public BroadcastImpl () {
        setParameter();
        initClient();
    }

    /**
     * 通过配置文件，获取所需的参数，并将其赋值给定义的变量
     * @author chuchen
     * @date 2021/3/27 16:49
     * @return void
     */
    private void setParameter() {
        url =  pro.getProperty("url");
        cssEnabled = Boolean.parseBoolean(pro.getProperty("cssEnabled"));
        javaScriptEnabled = Boolean.parseBoolean(pro.getProperty("javaScriptEnabled"));
        waitForBackgroundJavaScript = Integer.parseInt(pro.getProperty("waitForBackgroundJavaScript"));
    }

    /**
     * 静态代码块，用于加载配置文件
     * @author chuchen
     * @date 2021/3/27 16:51
     * @param null null
     * @return null
     */
    static {
        InputStream stream = Thread.currentThread().getContextClassLoader().
                getResourceAsStream("broadcast.properties");
        pro = new Properties();
        try {
            pro.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initClient () {
        client = new WebClient();
        //禁用css
        client.getOptions().setCssEnabled(cssEnabled);
        client.getOptions().setJavaScriptEnabled(javaScriptEnabled);
        //当js发生错误时，是否抛出异常
        client.getOptions().setThrowExceptionOnScriptError(false);

        //很重要，设置支持AJAX
        client.setAjaxController(new NicelyResynchronizingAjaxController());

        //等待JavaScript加载时间
        client.waitForBackgroundJavaScript(waitForBackgroundJavaScript);

        try {
            page = client.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *此方法用于返回解析到的文件的document
     * @author chuchen
     * @date 2021/3/26 19:54
     */
    @Override
    public Document getDocument () {
        Document document = Jsoup.parse(page.asXml());
        return document;
    }

    /**
     * 此方法返回获取到的所有文本
     * @author chuchen
     * @date 2021/3/26 19:55
     * @return java.lang.String
     */
    public String getText() {
        return page.asText();
    }

    /**
     * 此方法返回HtmlPage对象
     * @author chuchen
     * @date 2021/3/26 20:18
     * @return com.gargoylesoftware.htmlunit.html.HtmlPage
     */
    @Override
    public HtmlPage getHtmlPage () {
        return page;
    }
}
