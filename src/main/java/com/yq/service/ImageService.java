package com.yq.service;

import com.yq.domain.Image;

import java.util.List;
import java.util.Map;

public interface ImageService {
    List<Map<String,Object>> selectPageSer(int startRow,int pageSize);
    int updateImgSer(Image img);
    int deleteImgSer(Image img);
    int insertImgSer(Image img);
}
