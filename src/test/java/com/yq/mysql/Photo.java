package com.yq.mysql;


import com.mysql.jdbc.Blob;
import com.yq.domain.Hero;
import com.yq.service.HeroHonorSer;
import com.yq.service.impl.HeroHonorImplService;
import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/28 23:20
 **/

public class Photo {

    @Test
    public void test() throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\f.jpg");
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
        String sql = "insert into hero(heroname,birthday,photo) values('chuchen','234',?)";
        byte[] bytes = new byte[1024*1024];
        OutputStream os = new FileOutputStream(new File("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\jj.jpg"),true);
        int read = 0;

        int available = is.available();
        System.out.println(available);
        byte[] b = new byte[available];
        System.out.println("b: "+b.length);
        is.read(b,0,b.length);
        String ss=new String(b);
        //os.write(b,0,b.length);
        System.out.println("ss"+ss.length());
        int update = template.update(sql, ss);
        System.out.println(update);
        String sql1 = "select photo from hero";

        List<Map<String, Object>> mapList = template.queryForList(sql1);
        Map<String, Object> map = mapList.get(0);
        Object photo = map.get("photo");

        byte[] blob = (byte[]) photo;
        System.out.println(is.available());
        System.out.println(blob.length);
       /*os.write(blob,0,blob.length);*/

    }

    @Test
    public void test1() throws Exception {
        InputStream is = new FileInputStream("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\f.jpg");
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
        String sql = "insert into hero(heroname,birthday,photo) values('chuchen','234',?)";


        int available = is.available();
        System.out.println(available);
        byte[] b = new byte[available];

        is.read(b,0,b.length);
        OutputStream os = new FileOutputStream(new File("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\jj.jpg"),true);
        //template.update(sql,b);


        //String ss=new String(b);


        //os.write(b,0,b.length);
        String sql1 = "select photo from hero";
        //OutputStream os = new FileOutputStream(new File("C:\\Users\\chuchen\\Pictures\\好看动漫图片\\jj.jpg"),true);
        List<Map<String, Object>> mapList = template.queryForList(sql1);
        Map<String, Object> map = mapList.get(0);
        Object photo = map.get("photo");

        byte[] blob = (byte[]) photo;
        System.out.println(is.available());
        System.out.println(blob.length);
        os.write(blob,0,blob.length);

    }


    @Test
    public void test2() throws Exception {
        String name = "Pictures\\好看动漫图片\\head.jpg";
        System.out.println(name.length());

        int i = name.lastIndexOf("\\");
        System.out.println(i);
        String substring = name.substring(i+1);
        System.out.println(substring);
        System.out.println("----------");

        String s = "11.dfdf.jpg";
        int indexOf = s.lastIndexOf(".");

        String substring1 = s.substring(0, indexOf);
        System.out.println(substring1);
    }


    @Test
    public void test4() throws Exception {
        String sql = "select hero.heroname name,birthdat,isshow,photosrc,like,ptype,describe ";
        for (int i = 0; i < 7; i++) {
            sql = sql+" ,honor"+(i+1);
            System.out.println(sql);
        }
    }


    @Test
    public void test5() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into professiontype (ptype) values(?)";
        for (int i = 0; i < 11; i++) {
            template.update(sql,i);
        }
    }


    @Test
    public void test6() throws Exception {
        Hero hero = new Hero();
        List<String> list = new ArrayList<>();
        for (int i = 0; i <5; i++) {
            list.add("医生"+i);
        }
        hero.setHonor(list);
        String sql = "insert into herohonor (heroname ";
        List<String> honorList = hero.getHonor();
        for (int i = 0; i < honorList.size(); i++) {
            String honorStr = honorList.get(i);
            sql = sql+" ,honor "+(i+1);
        }
        sql = sql+") values(";
        System.out.println(sql);
        /*int indexOf = sql.lastIndexOf(",");
        String substring = sql.substring(0, indexOf);
        System.out.println(substring);*/
    }


    @Test
    public void test7() throws Exception {
        String sql = "insert into herohonor (identify ";
        Hero hero = new Hero();

        List<String> list = new ArrayList<>();
        for (int i = 0; i <5; i++) {
            list.add("医生"+i);
        }
        hero.setHonor(list);

        List<String> honorList = hero.getHonor();
        for (int i = 0; i < 7; i++) {
            sql = sql+",honor"+(i+1);
        }
        sql = sql+") values(";

        sql = sql+"'"+hero.getName()+"',";

        for (int i = 0; i < honorList.size(); i++) {
            String honorStr = honorList.get(i);
            sql = sql+"'"+honorStr+"',";
        }
        int indexOf = sql.lastIndexOf(",");
        sql = sql.substring(0, indexOf);
        sql =  sql+")";

        System.out.println(sql);
    }


    @Test
    public void test8() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into hero values(?,?,?,?,?,?,?,?,?)";
        for (int i = 0; i < 15; i++) {
            template.update(sql,(i+1),"初尘"+(i+1),"",new Random().nextInt(2)+"",
                    "",23,new Random().nextInt(5)+"","这是一段描述",(i+1+""));
        }
    }

    @Test
    public void test9() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into herohonor (`identify`,honor1,honor2,honor3) values(?,?,?,?)";
        for (int i = 0; i < 15; i++) {
            template.update(sql,(i+1+""),"获奖1","获奖2","获奖3");
        }
    }


    @Test
    public void test10() throws Exception {
        String s = "sdfsddfsdfgf";
        String[] split = s.split(",");
        for (String s1 : split) {
            System.out.println(s1);
        }
    }




    @Test
    public void test12() throws Exception {
        Connection con = DruidUtil.getConnection();
        con.setAutoCommit(false);
        String sql = "update hero set heroname=? where identify=?";
        //String sql = "update hero set heroname='chuchen' where identify='13'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,"chuchen");
        ps.setString(2,"14");
        int update = ps.executeUpdate();
        con.commit();
        System.out.println(update);

    }


    @Test
    public void test13() throws Exception {
        String format = new SimpleDateFormat().format(new Date());
        System.out.println(format);
    }
}
