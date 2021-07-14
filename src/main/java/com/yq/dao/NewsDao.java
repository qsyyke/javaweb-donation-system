package com.yq.dao;

import java.util.List;
import java.util.Map;

/**
 * 向数据库中插入获取查询，更改等新闻数据
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 17:44
 **/

public interface NewsDao {
    /**
     * 通过传入的参数List<Map<String,String>>，使用循环，依次向数据库中插入数据
     *
     * @author chuchen
     * @date 2021/3/27 17:45
     * @param news_list news_list  使用BroadcastService类获取到的List集合
     * @return int 返回插入的数据条数
     */
    int insertNews(List<Map<String,String>> news_list);

    /**
     * 向数据库查询新闻表中所有的数据
     * @author chuchen
     * @date 2021/3/27 17:47
     * @return List<Map<String,Object>> 所有数据，封装成多个map集合，使用List集合进行存放
     */
    List<Map<String, Object>> queryNewsAll(String firstDate ,String lastDate);
    List<Map<String, Object>> queryNewsBySql(String sql);

    List<Map<String, Object>> queryNewsByPage(int startRows ,int pageSize,String title);

    /**
     * 对新闻表中的数据进行更新
     * @author chuchen
     * @date 2021/3/27 17:48
     * @return int 更新的条数
     */
    int updateNews();
    int deleteNews(String title);

    //删除
}
