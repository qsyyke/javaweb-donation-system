package com.yq.domain;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载cookie的域名配置，返回一个properties对象
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/05 17:07
 **/

public class PageProperties {
     /** 配置文件 */
    private static String fileName = "page.properties";

     /** properties对象 */
     private static Properties pro;

     static {
         InputStream re = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
         pro = new Properties();
         try {
             pro.load(re);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public String getFileName () {
        return fileName;
    }

    public void setFileName (String fileName) {
        this.fileName = fileName;
    }

    public static Properties getPro () {
        return pro;
    }

    public void setPro (Properties pro) {
        this.pro = pro;
    }
}
