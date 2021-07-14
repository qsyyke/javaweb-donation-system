package com.yq.client;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.yq.api.AliApi;
import com.yq.util.Broadcast;
import com.yq.util.impl.BroadcastImpl;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.net.URL;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/07 17:55
 **/

public class ClientTest {

    @Test
    public void test() throws Exception {
        AliApi aliApi = new AliApi();
        String apiJson = aliApi.getJson();
        System.out.println(apiJson);
    }


    @Test
    public void test1() throws Exception {
        URL url = new URL("https://www.baidu.com/sugrec?p=3&json=1&prod=pc&from=pc_web&sugsid=33800,33822,31253,34004,33675,33607,26350&wd=%E5%B8%B8%E7%94%A8&req=2&csor=5&pwd=%E5%B8%B8%E7%94%A8&cb=jQuery110203314006852831428_1621129582252&_=1621129582258");
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        HtmlPage page = client.getPage(url);
        Thread.sleep(1000*8);
        System.out.println(page.asText());
    }
}
