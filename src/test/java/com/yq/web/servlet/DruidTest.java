package com.yq.web.servlet;

import com.yq.service.MessageService;
import com.yq.service.impl.MessageServiceImpl;
import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DruidTest {

    @Test
    public void get(){
        try {
            Connection con = DruidUtil.getConnection();
            JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
            String sql = "select * from user";
            List<Map<String, Object>> maps = template.queryForList(sql);
            System.out.println(maps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void get1(){
        //创建service对象
        MessageService message = new MessageServiceImpl();
        //调用getMessage获取结果集
        List<Map<String, Object>> maps = message.getMessage();

        //listAll保存所有的数组
        List<List> listAll = new ArrayList<>();
        //集合中记录的条数
        int size = maps.size();
        //i是一个整数
        int i = size / 10;
        if (i == 0) {
            List<Map> list = new ArrayList<>();
            //记录小于10[[[],[],[]],[]]
            for (Map<String, Object> map : maps) {
                list.add(map);
            }
            listAll.add(list);
        }else {
            for (int i1 = 0; i1 <= i; i1++) {
                List list = new ArrayList();
                //将新创建的集合放入所有的集合中
                listAll.add(list);
            }
            for (int i1 = 0; i1 < i; i1++) {
                for (int i2 = 0; i2 < 9; i2++) {
                    //获取总数组中的元素
                    Map<String, Object> map = maps.get((i1 * 10) + i2);
                    listAll.get(i1).add(map);
                }
            }
        }
        System.out.println(listAll);


    }
}
