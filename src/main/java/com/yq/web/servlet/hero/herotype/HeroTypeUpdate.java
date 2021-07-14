package com.yq.web.servlet.hero.herotype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.HeroTypeService;
import com.yq.service.impl.HeroTypeImplService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 只能更改栏目的名称
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/29 18:11
 **/

@WebServlet("/herotypeupdate")
public class HeroTypeUpdate extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //栏目名称
        String pType = request.getParameter("pType");

        //栏目编号
        String pNoStr = request.getParameter("pNo");

        if (pNoStr == null || pType == null) {
            String json = packJson(response.getStatus(), "参数不能为空", pType, 99999, 0);
            response.getWriter().write(json);
            return;
        }

        if ("".equals(pType) && "".equals(pNoStr)) {
            String json = packJson(response.getStatus(), "参数不能同时为空", pType, 999999, 0);
            response.getWriter().write(json);
            return;
        }

        int pNo = 0;
        try {
            pNo = Integer.parseInt(pNoStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            String json = packJson(response.getStatus(), "编号不合法", pType, pNo, 0);
            response.getWriter().write(json);
            return;
        }

        HeroTypeService service = new HeroTypeImplService();
        List<Map<String, Object>> maps = service.selectAllProfessionTypeSer();

        for (Map<String, Object> map : maps) {
            String pTypeDB = (String) map.get("ptype");
            if (pTypeDB.equals(pType)) {
                String json = packJson(response.getStatus(), "此职业已经存在", pType, pNo, 0);
                response.getWriter().write(json);
                return;
            }
        }

        int i = service.updateProfessionTypeSer(pType,pNo);
        if (i != 1) {
            //更新失败
            String json = packJson(response.getStatus(), "fail", pType, pNo, i);
            response.getWriter().write(json);
            return;
        }

        String json = packJson(response.getStatus(), "success", pType, pNo, i);
        response.getWriter().write(json);

    }

    private String packJson(int code, String message, String pTypeName,int pTypeNo,int update) throws JsonProcessingException {
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
        mapThree.put("update",update);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
