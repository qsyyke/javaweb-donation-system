package com.yq.web.servlet;

import com.yq.dao.MessageDao;
import com.yq.dao.impl.MessageDaoImpl;
import com.yq.util.impl.DruidUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */
public class InsertMessageTest {
    @Test
    public void test1(){
        JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
        MessageDao dao = new MessageDaoImpl();
        int i = dao.insertMessageDao("sdf", "sdf", null, "sdf");
        System.out.println(i);
    }
}
