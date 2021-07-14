package com.yq.png;




import com.aspose.html.Url;
import com.gargoylesoftware.htmlunit.html.HtmlTemplate;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/04 20:48
 **/

public class PngTest {
    public static void main (String[] args) {

    }
    @Test
    public void test1() {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        String uri = new File("D:\\Code\\yq\\yq-project\\src\\main\\webapp\\forget.html").toURI().toString();
        System.out.println(uri);
        imageGenerator.loadUrl(uri);
        imageGenerator.saveAsImage("hello-world.png");
        imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
    }
    @Test
    public void test2() {
        for (int i = 0; i < 4; i++) {
            if (i == 2) {
                continue;
            }
            System.out.println(i);

        }
    }

    @Test
    public void test3() {
        String separator = File.separator;
        System.out.println(separator);
    }
}
