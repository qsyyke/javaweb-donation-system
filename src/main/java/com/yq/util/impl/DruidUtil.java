package com.yq.util.impl;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author 程钦义 vipblogs.cn
 * @Version 1.0
 */
//这是一个数据库连接池工具类
public class DruidUtil {
    private static DataSource ds;
    private static String properties = "druid.properties";
    //设置构造方法为私有，工具类不应该有构造方法
    private DruidUtil() {}
    static {
        try {
            //加载配置文件
            //配置文件的名称默认就是druid.properties
            //可以调用setProperties进行设置
            Properties pro = new Properties();
            pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(properties));
            //初始化连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("静态代码出错");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //获取连接Connection对象
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void setProperties (String properties) {
        DruidUtil.properties = properties;
    }
    /*
     *释放资源
     * 因为在使用Connection对象的时候，我们都会进行DML语言或者是DQL语言的操作
     * 那么都会使用到Statement对象
     * 如果进行的是DQL语言，那么还需要ResultSet结果集对象，在关闭资源的时候，这两个对象都应该对其进行关闭
     */
    //DML语言的关闭
    public static void close(Statement stmt,Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();//进行的其实的归还操作
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    //DQL语言的关闭
    public static void close(ResultSet res, Statement stmt, Connection conn) {
        if (res != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();//进行的其实的归还操作
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static DataSource getDs () {
        return ds;
    }
}
