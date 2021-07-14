package com.yq.file;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.fileupload.FileItem;

import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/03 21:16
 **/

public class FileTest {
    private String name;

    public String getName () {
        return name;
    }

    @Test
    public void test() throws Exception {
        Page<Map<String,String>> objects = PageHelper.startPage(1, 10);
        String s = objects.toString();
        Page<Map<String, String>> maps = objects.pageSize(345);
        System.out.println(20 % 3);


       /* System.out.println(s);
        String[] split = s.split(",");
        int startRow = Integer.parseInt(split[3].split("=")[1]);
        System.out.println(startRow);*/
    }

}
