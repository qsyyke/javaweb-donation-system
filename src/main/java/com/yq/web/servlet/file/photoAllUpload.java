package com.yq.web.servlet.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Image;
import com.yq.service.ImageService;
import com.yq.service.impl.ImageImplSer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 这是一个图片上传接口
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/29 9:23
 **/


@WebServlet("/imgupload")
public class photoAllUpload extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String img_name = "";
        String fileName = "";
        String fileValue = "";
        byte[] bytes = new byte[1024*1024];
        File file = null;
        InputStream is = null;
        OutputStream os = null;

        String identify = new Random().nextInt(1000*1000)+"";

        //1.创建磁盘文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2.创建核心解析类
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //3.利用核心解析类request，解析后得到多个部分，返回一个list集合
        List<FileItem> fileItems = null;
        try {
            fileItems = fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        for (FileItem fileItem : fileItems) {
            if (fileItem.isFormField()) {
                //普通项
                String fieldName = fileItem.getFieldName();
                if ("img_name".equals(fieldName)) {
                    InputStream isStream = fileItem.getInputStream();
                    byte[] b = new byte[isStream.available()];
                    isStream.read(b, 0, b.length);
                    fileValue = new String(b,"utf-8");
                    img_name = new String(b,"utf-8");

                }
            }else {
                //是文件项 获取文件上传项的名称
                String name = fileItem.getName();
                int i = name.lastIndexOf(File.separator);
                if (i != -1) {
                    //是低版本浏览器
                    name = name.substring(i+1);
                }

                String contentType = fileItem.getContentType();

                if (!contentType.contains("image")) {
                    //不是一个图片
                    String json = packJson(response.getStatus(), "请传入图片", "", name);
                    response.getWriter().write(json);
                    return;
                }
                fileName = name;
                is = fileItem.getInputStream();
            }
        }

        if (fileValue != null && !("".equals(fileValue))) {
            int indexOf = fileName.lastIndexOf(".");
            fileName = fileValue + fileName.substring(indexOf,fileName.length());
        }

        String realPath = request.getServletContext().getRealPath(File.separator);
        String imgSaveSrc = realPath+File.separator+"image"+File.separator+"upload"+File.separator +fileName;
        String imgSrc = "http://api.vipblogs.cn:8080/image/upload/"+fileName;
        file = new File(imgSaveSrc);
        os = new FileOutputStream(file,true);
        if (is == null ) {
            String json = packJson(response.getStatus(), "保存失败", imgSrc, fileName);
            response.getWriter().write(json);
            return;
        }

        int read = 0;
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes,0,read);
        }
        os.close();
        is.close();

        String time = new SimpleDateFormat().format(new Date());
        System.out.println(time);
        //插入数据库中
        ImageService service = new ImageImplSer();
        Image image = new Image(img_name,imgSrc,time,identify);
        int updateNum = service.insertImgSer(image);

        if (updateNum != 1) {
            //插入失败
            String json = packJson(response.getStatus(), "insert fail", imgSaveSrc, fileName);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "success", imgSrc, fileName);
        response.getWriter().write(json);


    }

    private String packJson(int code,String message,String src,String fileName) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("imgSrc",src);
        mapThree.put("imgName",fileName);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
