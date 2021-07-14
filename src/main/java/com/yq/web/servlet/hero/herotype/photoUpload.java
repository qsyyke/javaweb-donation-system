package com.yq.web.servlet.hero.herotype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Hero;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是一个图片上传接口
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/29 9:23
 **/


@WebServlet("/photoupload")
public class photoUpload extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取英雄名称
        String heroName = request.getParameter("heroName");
        if (heroName == null || "".equals(heroName)) {
            String json = packJson(response.getStatus(), "heroName is empty", "", "");
            response.getWriter().write(json);
            return;
        }

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
        //4. 遍历fileitems
        for (FileItem fileItem : fileItems) {
            //判断这个文件项是否是普通项还是文件上传项
            if (fileItem.isFormField()) {
                //普通项
                //接收普通项的值，不能使用request.getParameter()
                //获取名称
                String name = fileItem.getFieldName();
                //获取值  如果是中文的话，使用有参方法，参数传递编码方式
                String json = packJson(response.getStatus(), "fail", "", name);
                response.getWriter().write(json);
                return;
            }

            //是文件项
            //获取文件上传项的名称
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

            String realPath = request.getServletContext().getRealPath(File.separator);
            String imgSaveSrc = realPath+File.separator+"image"+File.separator+"hero";
            //System.out.println(new File(imgSaveSrc).exists());

            //int indexOf = name.lastIndexOf(".");

            //String newName = name.substring(0,indexOf);

            File file = new File(imgSaveSrc+File.separator+name);
            //获取文件中的数据
            InputStream is = fileItem.getInputStream();
            FileOutputStream os = new FileOutputStream(file,true);
            byte[] bytes = new byte[1024*1024];
            int read = 0;
            while ((read = is.read(bytes)) != -1) {
                os.write(bytes,0,read);
            }
            os.close();
            is.close();

            //插入数据库中
            Hero hero = new Hero();
            hero.setPhotoSrc(imgSaveSrc);
            hero.setName(heroName);
            HeroService heroService = new HeroImplService();
            int updateNum = heroService.updatePhotoSrcSer(hero);

            if (updateNum != 1) {
                //插入失败
                String json = packJson(response.getStatus(), "insert fail", imgSaveSrc, name);
                response.getWriter().write(json);
                return;
            }

            String json = packJson(response.getStatus(), "success", imgSaveSrc, name);
            response.getWriter().write(json);
        }
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
