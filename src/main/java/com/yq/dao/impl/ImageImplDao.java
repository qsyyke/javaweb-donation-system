package com.yq.dao.impl;


import com.yq.dao.ImageDao;
import com.yq.domain.Image;
import com.yq.util.impl.DruidUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/01 20:26
 **/

public class ImageImplDao implements ImageDao {
    private JdbcTemplate template = new JdbcTemplate(DruidUtil.getDs());

    @Override
    public List<Map<String, Object>> selectPageDao (int startRow,int pageSize) {
        String sql = "select * from image limit ?,?";
        String sqlAll = "select * from image";
        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> listAll = template.queryForList(sqlAll);
        List<Map<String, Object>> list = template.queryForList(sql, startRow, pageSize);

        
        map.put("totalCount",listAll.size());
        list.add(map);
        return list;
    }

    @Override
    public int updateImgDao (Image img) {
        String sql = "update image set ";
        boolean flag = false;
        if (img.getImg_name() != null && !("".equals(img.getImg_name()))) {
            sql = sql + "img_name='"+img.getImg_name()+"'";
            flag = true;
        }

        if (flag) {
            sql = sql + " ,img_src='"+img.getImg_src()+"' where identify=?";
        }else {
            sql =sql+"img_src='"+img.getImg_src()+"' where identify=?";
        }
        int update = template.update(sql, img.getIdentify());

        return update;
    }

    @Override
    public int deleteImgDao (Image img) {
        String sql = "delete from image where identify=?";
        int update = template.update(sql, img.getIdentify());
        return update;
    }

    @Override
    public int insertImgDao (Image img) {
        String sql = "insert into image (img_name,img_src,img_date,identify) values(?,?,?,?)";

        return template.update(sql,img.getImg_name(),img.getImg_src(),img.getImg_date(),img.getIdentify());
    }
}
