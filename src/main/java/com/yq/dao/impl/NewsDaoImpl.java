package com.yq.dao.impl;

import com.yq.dao.NewsDao;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NewsDao的实现类，操作数据库新闻表中数据的细节
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 17:49
 **/

public class NewsDaoImpl implements NewsDao {
     /** JdbcTemplate对象，通过自己的工具类获取连接池 */
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    /**
     * 插入新闻map集合到数据库中的细节处理
     * 通过for循环将List<Map<String, String>>集合中的每个元素获取出来
     * 然后调用queryNews()方法，根据每条记录的日期和标题向数据库中进行查找
     * 因为日期和标题不会有相同，queryNews()返回记录条数，如果记录条数大于0,
     * 说明数据库中存在相同数据，则跳过
     * @author chuchen
     * @date 2021/3/27 17:50
     * @param news_list news_list 爬取的记录List集合
     * @return int 成功插入的条数
     */
    @Override
    public int insertNews (List<Map<String, String>> news_list) {
        int update_number = 0;
        //循环取出数据
        String sql = "insert into news(title,href,date) values(?,?,?)";
        for (Map<String, String> map : news_list) {
            //先验证数据库中是否有此数据
            List<Map<String, Object>> mapList = queryNews(map.get("date"), map.get("title"));
            if (mapList.size() != 0) {
                //长度不为0 说明数据库中已经存在数据
                continue;
            }
            update_number += template.update(sql, map.get("title"), map.get("href"), map.get("date"));
        }
        return update_number;
    }

    /**
     * 查询所有的新闻表中的数据
     * @author chuchen
     * @date 2021/3/27 17:55
     * @return List<Map<String,Object>> 每条记录封装的map集合List数组
     */
    @Override
    public List<Map<String, Object>> queryNewsAll (String firstDate ,String lastDate) {
        //String sql = "select * from news";
        String sql = "SELECT * FROM news WHERE date BETWEEN ? and ? ORDER BY date desc";
        List<Map<String, Object>> newsList = template.queryForList(sql,firstDate,lastDate);
        return newsList;
    }

    @Override
    public List<Map<String, Object>> queryNewsBySql (String sql) {
        return template.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> queryNewsByPage (int startRows, int pageSize,String title) {
        String sql = "select * from news";
        String sqlAll = "select * from news";

        if (!("".equals(title))) {
            //title有内容
            sql = sql+ " where title like '%"+title+"%' limit ?,?";
            sqlAll = sqlAll + " where title like '%"+title+"%'";
        }else {
            sql = sql + " limit ?,?";
        }
        List<Map<String, Object>> list = template.queryForList(sql, startRows, pageSize);
        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",listAll.size());
        list.add(map);
        return list;
    }


    /**
     * 插入数据，调用此方法判断数据库中是否存在相同的数据
     * @author chuchen
     * @date 2021/3/27 17:56
     * @param date date
     * @param title title
     * @return List<Map<String,Object>>
     */
    private List<Map<String, Object>> queryNews (String date,String title) {
        String sql = "select `date` from news where `date`= ? and title=?";
        List<Map<String, Object>> list = template.queryForList(sql, date, title);
        return list;
    }

    /**
     * 对数据库中的记录进行修改
     * @author chuchen
     * @date 2021/3/27 17:56
     * @return int 成功修改的条数
     */
    @Override
    public int updateNews () {
        return 0;
    }

    @Override
    public int deleteNews (String title) {
        String sql = "delete from news where title=?";
        return template.update(sql,title);
    }
}
