package com.yq.web.servlet;

import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Temp {
    @Test
    public void get() {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

        String sql = "insert into message (uname,province,date,message) values(?,?,?,?)";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-mm");
        for (int i = 0; i < 30; i++) {
            Date date1 = new Date();
            String format = dateFormat.format(date1);
            template.update(sql,"chuchen"+i,"云南"+i,format,"hello this is a test content");
        }
    }
}
