package com.yq.dao;

import com.yq.domain.Image;

import java.util.List;
import java.util.Map;

public interface ImageDao {
    List<Map<String,Object>> selectPageDao(int startRow,int pageSize);
    int updateImgDao(Image img);
    int deleteImgDao(Image img);
    int insertImgDao(Image img);
}
