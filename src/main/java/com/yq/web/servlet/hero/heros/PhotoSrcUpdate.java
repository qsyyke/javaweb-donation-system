package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Hero;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/photosrcupdate")
public class PhotoSrcUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        String identify = request.getParameter("identify");
        String photoSrc = request.getParameter("photoSrc");

        if (identify == null || "".equals(identify)) {
            String json = packJson(response.getStatus(), "参数不能为空", identify, 0);
            response.getWriter().write(json);
            return;
        }

        if (photoSrc == null || "".equals(photoSrc)) {
            String json = packJson(response.getStatus(), "图片地址不能为空", identify, 0);
            response.getWriter().write(json);
            return;
        }

        HeroService service = new HeroImplService();
        Hero hero = new Hero();
        hero.setPhotoSrc(photoSrc);
        hero.setUniqueIdentify(identify);
        int updateNum = service.updatePhotoSrcSer(hero);

        if (updateNum != 1) {
            String json = packJson(response.getStatus(), "修改失败", identify, updateNum);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "修改成功", identify, updateNum);
        response.getWriter().write(json);


    }

    private String packJson(int code, String message, String identify,int delete) throws JsonProcessingException {
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
        mapThree.put("identify",identify);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
