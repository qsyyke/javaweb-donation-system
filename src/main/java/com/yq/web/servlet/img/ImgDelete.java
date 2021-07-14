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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/imgdelete")
public class ImgDelete extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String identify = request.getParameter("identify");

        if (identify == null || "".equals(identify)) {
            String json = packJson(response.getStatus(), "图片唯一标识不能为空", "", 0);
            response.getWriter().write(json);
            return;
        }

        Image img = new Image();
        img.setIdentify(identify);

        ImageService service = new ImageImplSer();
        int insertNum = service.deleteImgSer(img);

        String message = "";

        if (insertNum != 1) {
            message = "fail";
        }else {
            message = "success";
        }

        String json = packJson(response.getStatus(), message, "", insertNum);
        response.getWriter().write(json);
    }

    private String packJson(int code, String message, String imgName,int delete) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("update",delete);
        mapThree.put("imgName",imgName);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
