package com.yq.service;

import com.yq.domain.Hero;

import java.util.List;
import java.util.Map;

public interface HeroHonorSer {

    List<Map<String,Object>> selectAllSer();
    List<Map<String,Object>> selectAllSer(String identify);

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

    /**
     * 插入人物
     * @author chuchen
     * @date 2021/4/29 13:24
     * @param hero 人物对象
     * @return int
     */
    int insertSer(Hero hero);

}
