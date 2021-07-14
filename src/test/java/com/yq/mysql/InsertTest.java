package com.yq.mysql;


import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Random;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/05 20:22
 **/

public class InsertTest {
    JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    @Test
    public void test1() {
        String sql1 = "insert into outtradeinfo(username,email,wishGoods,userProvince," +
                "donateProvince,outTradeNo,useraddress,donateaddress) values(?,?,?,?,?,?,?,?)";

        String sql2 = "insert into outtradeno(outTradeNo, tradeNo, createDate, tradeStatus, tradeMoney, tradeCount, subject) values(?,?,?,?,?,?,?)";
        String[] goods = {"现金","防护服","医用口罩","护目镜","隔离衣","喷雾器","红外线体温仪"};

        for (int i = 0; i < 400; i++) {
            Random random = new Random();
            template.update(sql1,i,i,goods[random.nextInt(goods.length)],i,i,i,1,1);

            template.update(sql2,i,i,i,i,random.nextInt(9000),1,1);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
