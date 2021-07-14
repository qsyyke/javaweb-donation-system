package com.yq.mysql;


import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/25 23:39
 **/

public class Test2 {
    // and username like '%?%'"
    @Test
    public void test() throws Exception {
        String sql = "select * from user where 1=1 and address like  ? or username='admin'";
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
        List<Map<String, Object>> list = template.queryForList(sql, "%云%");
        System.out.println(list);

    }
}
