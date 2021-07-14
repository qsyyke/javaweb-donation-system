package com.yq.web.jsoup;


import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ${description}
 * @author ${USER} 程钦义
 * @date ${YEAR}/${MONTH}/${DAY} ${HOUR}:${MINUTE}
 **/
/**
 * 此类用于爬取一个文件
 *
 * @author 青衫烟雨客 程钦义
 * @date 2021/03/27 16:25
 **/

/**
 * todo
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 16:41
 **/



public class test4 {

    private int a;
    private String f;
    boolean i = true;
    /**
     * 此方法用于返回用户名
     * @author chuchen
     * @date 2021/3/27 16:31 时间
     * @param user user 用户名
     * @param id id  用用户id
     * @return void 返回空
     */
    public void get(String user,int id) {

    }
    /**
     * 此方法不做介绍
     * @author chuchen 作者
     * @date 2021/3/27 16:32
     * @return String 返回值为返回用户名
     */
    private String get1() {
        return f;
    }
    /**
     * 返回一个集合
     * @author chuchen
     * @date 2021/3/27 16:32
     * @return Map<String,String> 用户集合
     */
    protected Map<String,String> getsd() {
        return new HashMap<>();
    }

    @Test
    public void test() {
        int month = new Date().getMonth();
        System.out.println(month);
    }
}
