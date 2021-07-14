package com.yq.service;

import com.yq.domain.Hero;

import java.util.List;
import java.util.Map;

public interface HeroService {

    /**
     * 返回对应页面的数据
     * @author chuchen
     * @date 2021/4/29 13:13
     * @param startRow 开始条数
     * @param pageSize 总页面
     * @param username 用户名，根据用户名进行模糊查询
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> selectByPageSer(int startRow,int pageSize,String username);


    /**
     * 根据传入sql进行查询
     * @author chuchen
     * @date 2021/4/30 21:58
     * @param sql sql sql语句
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> selectBySqlSer(String sql);

    /**
     * 根据英雄名字，进行删除该信息，包括荣誉
     * @author chuchen
     * @date 2021/4/29 13:15
     * @param identify 英雄名字
     * @return int
     */
    int deleteSer(String identify);

    /**
     *
     * @author chuchen
     * @date 2021/4/29 13:23
     * @param hero 英雄对象
     * @return int
     */
    int updateSer(Hero hero);
    int updatePhotoSrcSer(Hero hero);
    int updateDescribeSer(Hero hero);


    /**
     * 插入人物
     * @author chuchen
     * @date 2021/4/29 13:24
     * @param hero 人物对象
     * @return int
     */
    int insertSer(Hero hero);

    /**
     * 插入人物图片地址
     * @author chuchen
     * @date 2021/4/29 13:38
     * @param hero 通过此对象获取人物图片地址
     * @return int
     */
    int insertPhotoSrcSer(Hero hero);

    int insertLikeSer(Hero hero);
    int updateLikeSer(Hero hero);

}
