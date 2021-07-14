package com.yq.service;

import java.util.List;
import java.util.Map;

public interface HeroTypeService {
    /**
     * 插入栏目名称和编号
     * @author chuchen
     * @date 2021/4/29 12:34
     * @param pType 栏目名称
     * @param pNo  编号
     * @return int
     */
    int insertProfessionTypeSer(String pType,int pNo);

    /**
     *
     * @author chuchen
     * @date 2021/4/29 13:06
     * @param pType 栏目名称
     * @return int
     */
    int updateProfessionTypeSer(String pType,int pNo);

    /**
     * 根据栏目名称删除栏目
     * @author chuchen
     * @date 2021/4/29 13:07
     * @param pType 栏目名称
     * @return int
     */
    int deleteProfessionTypeSer(String pType);

    /**
     * 返回所有的栏目信息，没有进行分页
     * @author chuchen
     * @date 2021/4/29 13:08
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> selectAllProfessionTypeSer();
    List<Map<String,Object>> selectAllProfessionTypeSer(String sql);





}
