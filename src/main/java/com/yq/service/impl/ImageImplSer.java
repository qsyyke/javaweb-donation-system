package com.yq.service.impl;


import com.yq.dao.ImageDao;
import com.yq.dao.impl.ImageImplDao;
import com.yq.domain.Image;
import com.yq.service.ImageService;

import java.util.List;
import java.util.Map;

/**
 * @author 青衫烟雨客 程钦义
 * @date 2021/05/01 20:27
 **/

public class ImageImplSer implements ImageService {
    private ImageDao dao = new ImageImplDao();
    @Override
    public List<Map<String, Object>> selectPageSer (int startRow,int pageSize) {
        return dao.selectPageDao(startRow,pageSize);
    }

    @Override
    public int updateImgSer (Image img) {
        return dao.updateImgDao(img);
    }

    @Override
    public int deleteImgSer (Image img) {
        return dao.deleteImgDao(img);
    }

    @Override
    public int insertImgSer (Image img) {
        return dao.insertImgDao(img);
    }
}
