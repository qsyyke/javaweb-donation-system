package com.yq.web.jdbc;


import com.yq.dao.NewsDao;
import com.yq.dao.impl.NewsDaoImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/03/28 11:27
 **/

public class InsertTest {
    @Test
    public void test1() {
        NewsDao newsDao = new NewsDaoImpl();
        List<Map<String, String>> news_list = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
//{date=2021.03.28 10:44:01, href=https://www.163.com/dy/article/G65VRDB20514R9P4.html, title=美国总统拜登签署2021年新冠疫情破产纾困延长法案}
        map.put("date","2021.03.28 10:44:11");
        map.put("href","https://www.163.com/dy/article/G65VRDB20514R9P4.html");
        map.put("title","美国总统拜登签署2021年新冠疫情破产纾困延长法案");

        news_list.add(map);
        int i = newsDao.insertNews(news_list);
        System.out.println(i);
    }
}
