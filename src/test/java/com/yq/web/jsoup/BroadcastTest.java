package com.yq.web.jsoup;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.SgmlPage;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.yq.dao.impl.NewsDaoImpl;
import com.yq.util.Broadcast;
import com.yq.util.impl.BroadcastImpl;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */
public class BroadcastTest {
    @Test
    public void test1() throws Exception {
        Broadcast broadcast = new BroadcastImpl();
        HtmlPage htmlPage = broadcast.getHtmlPage();
//        HtmlElement time_lists = htmlPage.getHtmlElementById("time_lists");
        List<HtmlDivision> list = htmlPage.getByXPath("//div[@class='more_news']");
        HtmlElement html = list.get(0);
        HtmlPage page = null;
        for (int i = 0; i < 3; i++) {
            page = html.click();
        }
        DomElement time_lists1 = page.getElementById("time_lists");
        Iterable<DomElement> childElements = time_lists1.getChildElements();
        Iterator<DomElement> iterator = childElements.iterator();
        while (iterator.hasNext()) {
            DomElement next = iterator.next();
            List<HtmlDivision> byXPath = next.getByXPath("//div[@class='time']");
            HtmlDivision htmlDivision = byXPath.get(0);
            String time_text = htmlDivision.asText();
            String url = next.getBaseURI();
            List<HtmlDivision> title = next.getByXPath("//div[@class='tit']");
            HtmlDivision htmlDivision1 = title.get(0);
            String s = htmlDivision1.asText();
            System.out.println("时间: "+time_text);
            System.out.println("url "+byXPath);
            System.out.println("title: "+s);
            System.out.println("=============");


        }


    }

    @Test
    public  void test2() {
        /*List<Map<String, Object>> maps = new NewsDaoImpl().queryNewsAll("456", "sdf");
        System.out.println(maps.size());*/
    }

    @Test
    public void test3() {
        String f1 =  "sd";
        int f = 45;
        System.out.println((Integer)Integer.parseInt(f1) instanceof Number);

    }
}
