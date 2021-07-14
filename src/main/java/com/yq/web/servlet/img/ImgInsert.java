package com.yq.web.servlet.img;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Image;
import com.yq.service.ImageService;
import com.yq.service.impl.ImageImplSer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@WebServlet("/imginsert")
public class ImgInsert extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //图片名称，如果为null，那么会自动变为空
        String imgName = request.getParameter("imgName");

        //不能为空 null
        String imgSrc = request.getParameter("imgSrc");

        if (imgName == null) {
            imgName = "";
        }

        if (imgSrc == null || "".equals(imgSrc)) {
            String json = packJson(response.getStatus(), "图片地址不能为空", imgName, 0);
            response.getWriter().write(json);
            return;
        }

        int nextInt = new Random().nextInt(1000 * 1000);

        String time = new SimpleDateFormat().format(new Date());

        Image img = new Image(imgName,imgSrc,time,nextInt+"");

        ImageService service = new ImageImplSer();
        int insertNum = service.insertImgSer(img);

        String message = "";

        if (insertNum != 1) {
            message = "添加图片失败";
        }else {
            message = "添加图片成功";
        }

        String json = packJson(response.getStatus(), message, imgName, insertNum);
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, String imgName,int insert) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("update",insert);
        mapThree.put("imgName",imgName);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
