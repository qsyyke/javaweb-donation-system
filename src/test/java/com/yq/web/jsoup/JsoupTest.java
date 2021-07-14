package com.yq.web.jsoup;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import java.net.URL;

/**
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */
public class JsoupTest {
    @Test
    public void test1() throws Exception {
        String keyWord = "Jsoup";
        URL url = new URL("https://www.baidu.com/sugrec?p=3&json=1&prod=pc&from=pc_web&sugsid=33800,33822,31253,34004,33675,33607,26350&req=2&csor=5&cb=jQuery110203314006852831428_1621129582252&_=1621129582258&wd="+keyWord);

        com.gargoylesoftware.htmlunit.WebClient client = new com.gargoylesoftware.htmlunit.WebClient(BrowserVersion.CHROME);

        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);

        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);

        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.waitForBackgroundJavaScript(300);

        TextPage page = client.getPage(url);
        System.out.println(page.getContent());
    }
    @Test
    public void test2() {

    }
}
