package com.yq.img;


import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/01 20:52
 **/

public class ImageTest {

    @Test
    public void test() throws Exception {
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
        String sql = "insert into image (img_name,img_src,img_date,identify) values(?,?,?,?)";
        for (int i = 0; i <40; i++) {
            String format = new SimpleDateFormat().format(new Date());
            String s = new Random().nextInt(1000 * 1000) + "";
            template.update(sql,"图片: "+(i+1),"https://vipblogs.cn",format,s);
        }

    }


}
