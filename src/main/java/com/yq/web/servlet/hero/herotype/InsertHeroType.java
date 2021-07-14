package com.yq.web.servlet.hero.herotype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.impl.HeroTypeImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 插入类型的文件
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/29 16:30
 **/

@WebServlet("/insertherotype")
public class InsertHeroType extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //类型名字
        String pTypeName = request.getParameter("pType");

        //编号
        String pNoStr = request.getParameter("pNo");

        if (pNoStr == null || pTypeName == null || "".equals(pNoStr) || "".equals(pTypeName)) {
            String json = packJson(response.getStatus(),"参数不能为空", pTypeName, pNoStr, 0);
            response.getWriter().write(json);
            return;
        }

        int pNo = 0;
        try {
            pNo = Integer.parseInt(pNoStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(),"编号格式错误", pTypeName, pNoStr, 0);
            response.getWriter().write(json);
            return;
        }

        HeroTypeImplService service = new HeroTypeImplService();
        List<Map<String, Object>> maps = service.selectAllProfessionTypeSer();

        for (Map<String, Object> map : maps) {
            String pTypeDB = (String) map.get("ptype");
            Integer pNoDB = (Integer) map.get("pno");
            if (pTypeDB.equals(pTypeName) || pNoDB.equals(pNo)) {
                String json = packJson(response.getStatus(), "此编号或者职业已经存在", pTypeName, pNoStr, 0);
                response.getWriter().write(json);
                return;
            }
        }


        int insert = service.insertProfessionTypeSer(pTypeName, pNo);
        if (insert != 1) {
            String json = packJson(response.getStatus(),"insert fail", pTypeName, pNoStr, insert);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(),"insert success", pTypeName, pNoStr, insert);
        response.getWriter().write(json);
        return;
    }
    private String packJson(int code, String message, String pTypeName,String pTypeNo,int insert) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("pTypeName",pTypeName);
        mapThree.put("pTypeNo",pTypeNo);
        mapThree.put("update",insert);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
