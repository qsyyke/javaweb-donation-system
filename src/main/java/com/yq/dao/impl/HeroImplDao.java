package com.yq.dao.impl;

import com.yq.dao.HeroDao;
import com.yq.domain.Hero;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/04/29 13:31
 **/

public class HeroImplDao implements HeroDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());
    @Override
    public List<Map<String, Object>> selectByPageDao (int startRow, int pageSize, String sql, String sqlAll) {
        List<Map<String, Object>> list = template.queryForList(sql, startRow, pageSize);
        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount",listAll.size());
        list.add(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> selectBySqlDao (String sql) {
        return template.queryForList(sql);
    }

    @Override
    public int deleteDao (String identify) {
        String heroSql = "delete from hero where identify =?";
        String honorSql = "delete from herohonor where identify =?";
        Connection con = null;
        PreparedStatement heroPs = null;
        PreparedStatement honorPs = null;
        int honorNum = 0;

        try {
            con = DruidUtil.getConnection();
            con.setAutoCommit(false);
            heroPs = con.prepareStatement(heroSql);
            honorPs = con.prepareStatement(honorSql);

            heroPs.setString(1, identify);
            honorPs.setString(1, identify);

            int heroNum = heroPs.executeUpdate();

            if (heroNum != 1) {
                con.rollback();
                con.commit();
                return 0;
            }

            honorNum = honorPs.executeUpdate();

            if (heroNum != 1 && honorNum != 1) {
                con.rollback();
                con.commit();
            }

            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        } finally {
            if (heroPs != null) {
                try {
                    heroPs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (honorPs != null) {
                try {
                    honorPs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return honorNum;

        }
    }

    @Override
    public int updateDao (Hero hero) {
        //String heroSql = "UPDATE hero set heroname =?,isshow=?,ptype=? ";
        String heroSql = "UPDATE hero set heroname =?,isshow=?,ptype=?  ";

        String honorSql = "update herohonor set honor1=?,honor2=?,honor3=?,honor4=?,honor5=?,honor6=?,honor7=? " +
                "where `identify`='"+hero.getUniqueIdentify()+"';";

        String isShow = "1";
        if (!hero.isShow()) {
            isShow = "0";
        }

        if (hero.getPhotoSrc() != null || !("".equals(hero.getPhotoSrc()))) {
            //没有描述内容
            heroSql = heroSql + ",photosrc='"+hero.getPhotoSrc()+"' ";
        }

        if (hero.getDescribe() == null || "".equals(hero.getDescribe())) {
            //没有描述内容
            //heroSql = heroSql + " where identify ='"+hero.getUniqueIdentify()+"';";
            heroSql = heroSql + " where identify = ? ";
        } else {
            //有描述内容
            //heroSql = " h_describe='" + hero.getDescribe() + "'  where identify ='"+hero.getUniqueIdentify()+"';";
            heroSql = heroSql+ " ,h_describe='" + hero.getDescribe() + "'  where identify = ?;";
        }

        PreparedStatement psHero = null;
        PreparedStatement psHonor = null;
        Connection con = null;
        List<String> list = null;
        int update = 1;
        try {
            con = DruidUtil.getConnection();
            con.setAutoCommit(false);
            psHero = con.prepareStatement(heroSql);
            psHonor = con.prepareStatement(honorSql);

            psHero.setString(1, hero.getName());
            psHero.setString(2, isShow);
            psHero.setInt(3, hero.getProfessionType());

            String uniqueIdentify = hero.getUniqueIdentify();
            System.out.println(uniqueIdentify);
            psHero.setString(4, uniqueIdentify);

            psHonor.setString(1, hero.getUniqueIdentify());
            list = hero.getHonor();

            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    psHonor.setString(i + 1, list.get(i));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            int heroNum = psHero.executeUpdate();
            int honorNum = 1;
            if (list.size() > 0) {
                honorNum = psHonor.executeUpdate();
            }

            if (heroNum != 1 && honorNum != 1) {
                update = 0;
                con.rollback();
            }
            con.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (psHero != null) {
                try {
                    psHero.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psHonor != null) {
                try {
                    psHonor.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return update;
    }

    @Override
    public int updatePhotoSrcDao (Hero hero) {
        String sql = "update hero set photosrc=? where identify = ?";
        int update = template.update(sql, hero.getPhotoSrc(),hero.getUniqueIdentify());
        return update;
    }

    @Override
    public int updateDescribeDao (Hero hero) {
        String sql = "update hero set `h_describe` =? where identify = ?";
        int update = template.update(sql, hero.getDescribe(),hero.getUniqueIdentify());
        return update;
    }

    @Override
    public int insertDao (Hero hero) {
        String isShow = "1";
        if (!hero.isShow()) {
            isShow = "0";
        }

        String sql = "insert into hero (heroname,isshow,ptype";
        if (hero.getDescribe() == null || "".equals(hero.getDescribe())) {
            //没有描述
            sql = sql+",identify) values(?,?,?,?)";
        }else {
            sql = sql+",h_describe,identify) values(?,?,?,'"+hero.getDescribe()+"',?)";
        }
        return template.update(sql,hero.getName(),isShow,hero.getProfessionType(),hero.getUniqueIdentify());
    }

    @Override
    public int insertPhotoSrcDao (Hero hero) {
        String sql = "update hero set photosrc=? where identify = ?";
        int update = template.update(sql, hero.getPhotoSrc(),hero.getUniqueIdentify());
        return update;
    }

    @Override
    public int insertLikeDao (Hero hero) {
        //String sql = "insert"
        return 0;
    }

    @Override
    public int updateLikeDao (Hero hero) {
        String sql = "update hero set h_like=? where identify=?";
        return template.update(sql,hero.getLike(),hero.getUniqueIdentify());
    }

}
