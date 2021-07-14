package com.yq.file;


import com.aspose.html.Url;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yq.util.impl.DruidUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/05 23:08
 **/

public class PageTest {
    JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    String sql = "insert into systemMessage (message) values(?)";
    @Test
    public void test1() throws Exception {
        WebClient client = new WebClient();
        //禁用css
        client.getOptions().setCssEnabled(true);
        client.getOptions().setJavaScriptEnabled(false);
        //当js发生错误时，是否抛出异常
        client.getOptions().setThrowExceptionOnScriptError(false);

        //很重要，设置支持AJAX
        client.setAjaxController(new NicelyResynchronizingAjaxController());

        //等待JavaScript加载时间
        client.waitForBackgroundJavaScript(1000*2);

        URL url = new URL("http://www.lizhigushi.com/jingdianyulu/a21681.html");
        HtmlPage page = client.getPage(url);
        Document document = Jsoup.parse(page.asXml());
        Elements con_main = document.getElementsByClass("content");
        String html = con_main.html();
        Document parse = Jsoup.parse(html);
        Elements p = parse.getElementsByTag("p");
        for (Element element : p) {
            String text = element.text();
            String[] split = text.split(". ");

            String str = "";
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    str = str + split[i];
                }
            }
            template.update(sql,str);
            System.out.println(str);
            System.out.println("--------");
        }




        /*for (String s : list) {
            String[] split = s.split("、");
            String str = "";
            for (int i = 0; i < split.length; i++) {
                if (i != 0) {
                    str = str + split[i];
                }
            }
            template.update(sql,str);
            System.out.println(str);
            System.out.println("--------");
        }*/

    }


    @Test
    public void test2() throws Exception {
        WebClient client = new WebClient();
        //禁用css
        client.getOptions().setCssEnabled(true);
        client.getOptions().setJavaScriptEnabled(false);
        //当js发生错误时，是否抛出异常
        client.getOptions().setThrowExceptionOnScriptError(false);

        //很重要，设置支持AJAX
        client.setAjaxController(new NicelyResynchronizingAjaxController());

        //等待JavaScript加载时间
        client.waitForBackgroundJavaScript(1000*2);

        URL url = new URL("https://www.zhihu.com/question/38247404/answer/512784606");
        HtmlPage page = client.getPage(url);
        Document document = Jsoup.parse(page.asXml());
        Elements figure = document.getElementsByTag("figure");

        int count = 160;
        for (Element element : figure) {
            String html = element.html();
            Document parse = Jsoup.parse(html);
            Elements img = parse.getElementsByTag("img");
            System.out.println(img);
            String attr = img.attr("data-default-watermark-src");

            URL url1 = new URL(attr);

            HttpURLConnection conn = (HttpURLConnection)url1.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10 * 1000);

            InputStream is = conn.getInputStream();

            System.out.println("长度: "+is.available());

            byte[] bytes = new byte[1024*1024];
            int read = 0;

            File file = new File("D:\\360downloads\\wpcache\\1\\tx"+count+".jpg");
            FileOutputStream os = new FileOutputStream(file,false);

            while ((read = is.read(bytes)) != -1) {
                os.write(bytes,0,read);
            }

            os.flush();
            os.close();

            count = count+1;



        }
    }

    @Test
    public void test3() throws Exception {
        FileInputStream is = new FileInputStream("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\head.jpg");

        byte[] bytes = new byte[1024*1024];

        int read = 0;

        FileOutputStream os = new FileOutputStream("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\head12.jpg");
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes,0,read);
        }

    }
}
