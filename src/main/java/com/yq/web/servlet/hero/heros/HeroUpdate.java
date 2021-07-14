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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更新英雄的记录 更具人物的唯一标识进行修改 除了唯一标识还有id之外的字段都是可以进行修改的
 * @author 青衫烟雨客 程钦义
 * @date 2021/5/1 7:08
 **/

@WebServlet("/heroupdate")
public class HeroUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //名字必须
        String heroName = request.getParameter("heroName");

        //人物唯一标识，不能为null或者空
        String identify = request.getParameter("identify");

        //人物头像地址
        String photoSrc = request.getParameter("photoSrc");

        //获取描述
        String describe = request.getParameter("describe");

        //人物所属类型 数字
        String pTypeStr = request.getParameter("pType");

        //是否展示 传入的是一个数字
        String isShowStr = request.getParameter("isShow");

        //所获得的荣耀 通过,号进行数组的切分
        String honors = request.getParameter("honors");

        boolean isShow = true;

        if ("0".equals(isShowStr)) {
            //不展示
            isShow = false;
        }

        if (photoSrc == null || "".equals(photoSrc)) {
            photoSrc = "";
        }

        if (heroName == null || "".equals(heroName)) {
            //姓名为空或者null
            String json = packJson(response.getStatus(), "heroName is empty", heroName, identify, 0);
            response.getWriter().write(json);
            return;
        }

        if (identify == null || "".equals(identify)) {
            //姓名为空或者null
            String json = packJson(response.getStatus(), "唯一标识不能为空", heroName, identify, 0);
            response.getWriter().write(json);
            return;
        }

        //属性编号
        int pType = 0;
        try {
            pType = Integer.parseInt(pTypeStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(), "所属类型编号有误或者传入参数不合法", heroName, identify, 0);
            response.getWriter().write(json);
            return;
        }

        //正确，进行插入
        Hero hero = new Hero();
        hero.setName(heroName);
        hero.setShow(isShow);
        hero.setProfessionType(pType);
        hero.setDescribe(describe);
        hero.setUniqueIdentify(identify);

        if (photoSrc != null && !("".equals(photoSrc))) {
            hero.setPhotoSrc(photoSrc);
        }

        List<String> honorList = new ArrayList<>();
        if (honors != null && !("".equals(honors))) {
            //所获得荣耀不能为空的时候，才进行荣耀的更新 将所获荣耀使用,号进行分割成数组
            String[] splitHonors = new String[0];
            try {
                splitHonors = honors.split(",");
            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean flag = true;
            for (int i = 0; i < 7; i++) {
                String honorStr = "";
                if (flag && honors != null) {
                    try {
                        honorStr = splitHonors[i];
                    } catch (Exception e) {
                        e.printStackTrace();
                        flag = false;
                    }
                }
                honorList.add(honorStr);
            }
        }

        hero.setHonor(honorList);

        HeroService heroService = new HeroImplService();

        int updateHeroNum = heroService.updateSer(hero);

        if (updateHeroNum != 1 ) {
            String json = packJson(response.getStatus(), "更新失败", heroName, identify, updateHeroNum);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "更新成功", heroName, identify, updateHeroNum);
        response.getWriter().write(json);

    }

    private String packJson(int code, String message, String heroName,String identify,int insert) throws JsonProcessingException {
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
        mapThree.put("identify",identify);
        mapThree.put("heroName",heroName);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
