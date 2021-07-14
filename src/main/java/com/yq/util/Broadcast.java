package com.yq.util;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.nodes.Document;

/**
 * 此接口用于向目标网址爬取数据
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 16:41
 **/


public interface Broadcast {
    /**
     * 初始化模拟浏览器的参数
     * @author chuchen
     * @date 2021/3/27 16:43
     * @return void
     */
    void initClient();
    /**
     * 获取爬取到的网页的所有元素节点
     * @author chuchen
     * @date 2021/3/27 16:44
     * @return Document 返回获取到的元素节点
     */
    Document getDocument();
    /**
     * 获取爬取的网页的所有文本内容
     * @author chuchen
     * @date 2021/3/27 16:45
     * @return String 此页面的所有文本内容分
     */
    String getText();
    /**
     * 获取HtmlPage对象，用于还需要使用其对象进行发送请求，点击的情况
     * @author chuchen
     * @date 2021/3/27 16:46
     * @return HtmlPage 返回此对象
     */
    HtmlPage getHtmlPage();
}
