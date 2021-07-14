package com.yq.dao.impl;



import com.yq.dao.HeroHonorDao;
import com.yq.domain.Hero;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:31
 **/

public class HeroHonorImplDao implements HeroHonorDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    @Override
    public List<Map<String, Object>> selectAllDao (String identify) {
        String sql = "select * from herohonor where identify =?";
        return template.queryForList(sql,identify);
    }

    @Override
    public List<Map<String, Object>> selectAllDao () {
        String sql = "select * from herohonor";
        return template.queryForList(sql);
    }

    @Override
    public int deleteDao (String identify) {
        String sql = "delete from herohonor where identify=?";
        int update = template.update(sql, identify);
        return update;
    }

    @Override
    public int updateDao (Hero hero) {
        String sql = "update herohonor set ";
        List<String> honorList = hero.getHonor();
        if (honorList.size() != 0) {
            for (int i = 0; i < honorList.size(); i++) {
                String honorStr = honorList.get(i);
                sql = sql + " honor" + (i + 1) + "='" + honorStr + "', ";
            }
        }
        int indexOf = sql.lastIndexOf(",");
        sql = sql.substring(0, indexOf) +" where heroname='"+hero.getName()+"'";
        int update = template.update(sql);

        return update;
    }

    @Override
    public int insertDao (Hero hero) {
        String sql = "insert into herohonor (identify ";

        List<String> honorList = hero.getHonor();
        for (int i = 0; i < 7; i++) {
            sql = sql+",honor"+(i+1);
        }
        sql = sql+") values(";

        sql = sql+"'"+hero.getUniqueIdentify()+"',";

        for (int i = 0; i < honorList.size(); i++) {
            String honorStr = honorList.get(i);
            sql = sql+"'"+honorStr+"',";
        }
        int indexOf = sql.lastIndexOf(",");
        sql = sql.substring(0, indexOf);
        sql =  sql+")";

        System.out.println(sql);
        int update = template.update(sql);
        return update;
    }
}
