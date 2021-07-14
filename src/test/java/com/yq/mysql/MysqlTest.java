package com.yq.mysql;

import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MysqlTest {

    @Test
    public void test() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into outtradeno(createDate,outTradeNo,tradeMoney,tradeCount,tradeNo,tradeStatus)" +
                " values(?,?,?,?,?,?)";
        for (int i = 0; i < 40; i++) {

            int i1 = new Random().nextInt(12);
            int i2 = new Random().nextInt(30);

            String i1s = "";
            String i2s = "";

            if (i1<10) {
                i1s = "0"+i1;
            }else {
                i1s = i1+"";
            }

            if (i2<10) {
                i2s = "0"+i2;
            }else {
                i2s = i2+"";
            }

            String year = "2021-"+i1s+"-"+i2s+" ";


            int h = new Random().nextInt(24);
            String hs = "";
            if (h<10) {
                hs = "0"+h;
            }else {
                hs = h+"";
            }

            int m = new Random().nextInt(59);
            String ms = "";
            if (m<10) {
                ms = "0"+m;
            }else {
                ms = m+"";
            }

            int mm = new Random().nextInt(59);
            String mms = "";
            if (h<10) {
                mms = "0"+h;
            }else {
                mms = mm+"";
            }
            String time = year+hs+":"+ms+":"+mms;
            System.out.println(time);
            template.update(sql,time,(i+1),new Random().nextInt(1000*10),i,i,"success");
        }
    }

    @Test
    public void test2() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into outtradeinfo (outTradeNo,username) values(?,?)";
        for (int i = 0; i < 40; i++) {
            template.update(sql,(i+1),(i+1));
        }
    }

    @Test
    public void test3() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into heromessage (cname,cdate,ccontent,identify,uid) values(?,?,?,?,?)";
        for (int i = 0; i < 40; i++) {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            template.update(sql,"用户"+(i+1),format,"只要努力 只要坚持 无论疫情还是人生的困难 都会冬去春来 万物复苏 迎春 遇见更好的自己！",(i+1),(i+1));
        }
    }


    @Test
    public void test1() throws Exception {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(format);
    }


    @Test
    public void test4() throws Exception {
        String adminStr = null;
        int admin = 0;
        try {
            admin = Integer.parseInt(adminStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}


