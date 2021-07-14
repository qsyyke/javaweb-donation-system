package com.yq.web.servlet.hero.heros;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.domain.Hero;
import com.yq.service.HeroHonorSer;
import com.yq.service.HeroService;
import com.yq.service.impl.HeroHonorImplService;
import com.yq.service.impl.HeroImplService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/heroinsert")
public class HeroInsert extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //名字 可以重复，但是人物唯一标识不能重复
        String heroName = request.getParameter("heroName");

        //是否展示 可以为空，如果为空，表示展示
        String isShowStr = request.getParameter("isShow");

        //英雄所属类型 不能为空，传入的是一个数字
        String pTypeStr = request.getParameter("pType");

        //英雄描述 可以为null或者空，为空不会添加
        String describe = request.getParameter("describe");

        //英雄唯一标识 最好传入一个时间戳，传入数字，不能为空
        String identify = new Random().nextInt(1000*1000)+"";

        //获取所获荣耀 可以为空 传入的参数需要以,进行隔开，使用,进行数组提取
        String honors = request.getParameter("honors");

        boolean isShow = true;

        if (!("1".equals(isShowStr))) {
            //不展示
            isShow = true;
        }

        //将所获荣耀使用,号进行分割成数组
        List<String> honorList = new ArrayList<>();
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

        if (heroName == null || "".equals(heroName)) {
            //姓名为空或者null
            String json = packJson(response.getStatus(), "heroName is empty", heroName, identify, 0);
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

        HeroService heroService = new HeroImplService();
        String selectHeroIdentifySql = "select * from hero where identify='"+identify+"'";

        //验证唯一标识是否唯一
        List<Map<String, Object>> heroIdentifyList = heroService.selectBySqlSer(selectHeroIdentifySql);
        if (heroIdentifyList.size() > 0) {
            String json = packJson(response.getStatus(), pTypeStr + " 此唯一标识已经存在", heroName, identify, 0);
            response.getWriter().write(json);
            return;
        }

        //正确，进行插入
        Hero hero = new Hero();
        hero.setName(heroName);
        hero.setDescribe(describe);
        hero.setShow(isShow);
        hero.setProfessionType(pType);
        hero.setHonor(honorList);
        hero.setUniqueIdentify(identify);

        int insertHeroNum = heroService.insertSer(hero);
        HeroHonorSer heroHonorSer = new HeroHonorImplService();
        int insertHeroHonorNum = heroHonorSer.insertSer(hero);

        if (insertHeroNum != 1 && insertHeroHonorNum != 1) {
            String json = packJson(response.getStatus(), "插入失败", heroName, identify, insertHeroNum);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "插入成功", heroName, identify, insertHeroNum);
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
