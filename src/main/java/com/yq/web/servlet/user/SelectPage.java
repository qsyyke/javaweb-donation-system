package com.yq.web.servlet.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yq.domain.PageProperties;
import com.yq.service.UserService;
import com.yq.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 分页查询处理
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/24 20:33
 **/

@WebServlet("/page")
public class SelectPage extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        Properties pro = PageProperties.getPro();

        int pageSize = Integer.parseInt((String) pro.get("pageSize"));

        String pageNumStr = request.getParameter("pageNum");

        //省份和用户名
        String province = request.getParameter("province");
        String username = request.getParameter("username");

        String isAdminSAtr = request.getParameter("isadmin");

        if (isAdminSAtr == null || "".equals(isAdminSAtr)) {
            isAdminSAtr = "0";
        }

        if (province == null) {
            province = "";
        }
        if (username == null) {
            username = "";
        }

        int pageNum = 0;

        if (pageNumStr == null || "".equals(pageNumStr)) {
            //默认从0开始
            pageNum = 1;
        }else {
            try {
                pageNum = Integer.parseInt(pageNumStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                pageNum = 1;
            }
        }

        int isAdmin = 0;
        try {
            isAdmin = Integer.parseInt(isAdminSAtr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            isAdmin = 0;
        }

        Page<Map<String,String>> objects = PageHelper.startPage(pageNum, pageSize);
        String s = objects.toString();
        String[] split = s.split(",");

        //开始页数
        int startRow = Integer.parseInt(split[3].split("=")[1]);

        UserService userService = new UserServiceImpl();
        List<Map<String, Object>> list = userService.userPageSer(startRow, pageSize,province,username,isAdmin);
        List<Map<String, Object>> listAll = userService.userServiceAll();
        System.out.println(list);
        System.out.println(list.get(list.size()-1));
        int totalCount = 0;

        for (Map<String, Object> map : list) {
            if (map.get("totalCount") != null) {
                totalCount = (Integer) map.get("totalCount");
            }
        }

        //总记录条数
        //int totalSize = listAll.size();

        //总页面
        int totalPage = 0;
        if (totalCount % pageSize  == 0) {
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize +1;
        }

        String json = packJson(response.getStatus(), "success", list,
                totalPage,list.size(),totalCount,pageSize,pageNum);
        response.getWriter().write(json);

    }

    /**
     *
     * @author chuchen
     * @date 2021/4/24 22:15
     * @param code code
     * @param message message
     * @param list list
     * @param totalPage totalPage 总页数
     * @param recordCount recordCount 当前页面记录数
     * @param totalCount totalCount 总记录数
     * @param pageSize pageSize  每页大小
     * @return pageNo pageNo 当前页码
     */
    private String packJson(int code,String message,List<Map<String, Object>> list,
                            int totalPage,int recordCount,int totalCount,
                            int pageSize,int pageNo) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("userList",list);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);
        mapTwo.put("totalPage",totalPage);
        mapTwo.put("recordCount",recordCount);
        mapTwo.put("totalCount",totalCount);
        mapTwo.put("pageSize",pageSize);
        mapTwo.put("pageNo",pageNo);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }
}
