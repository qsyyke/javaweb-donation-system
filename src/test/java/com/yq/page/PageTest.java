package com.yq.page;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;

import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/24 20:37
 **/

public class PageTest {

    @Test
    public void test() throws Exception {
        Page<Map<String,String>> objects = PageHelper.startPage(-1, 3);
        PageInfo<Map<String, String>> mapPageInfo = objects.toPageInfo();

        ObjectMapper mapper = new ObjectMapper();
        String s = objects.toString();
        String[] split = s.split(",");
        System.out.println(split[2]);
        System.out.println(split[3]);
        System.out.println(objects.toString());

    }
}
