package com.yq.bund;


import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/05 16:55
 **/

public class Test1 {

    @Test
    public void test() throws Exception {
        Properties pro = new Properties();
        InputStream rs = Thread.currentThread().getContextClassLoader().getResourceAsStream("domain.properties");
        pro.load(rs);
        String domain = (String)pro.get("domain");
        System.out.println(domain);

    }
}
