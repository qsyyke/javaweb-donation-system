package com.yq.web.jdbc;

import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */
public class JdbcTest {
    JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Test
    public void test1() {
        String sql = "select * from news";
        List<Map<String, Object>> mapList = template.queryForList(sql);
        System.out.println(mapList);
    }
}
