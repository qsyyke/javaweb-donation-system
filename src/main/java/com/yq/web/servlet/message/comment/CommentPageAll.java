package com.yq.web.servlet.message.comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yq.domain.PageProperties;
import com.yq.service.HeroCommentService;
import com.yq.service.impl.HeroCommentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@WebServlet("/commentpageall")
public class CommentPageAll extends HttpServlet {
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

        String username = request.getParameter("username");

        //当前页码
        String pageNumStr = request.getParameter("pageNum");

        //订单开始时间
        String startTime = request.getParameter("startTime");
        //订单结束时间
        String endTime = request.getParameter("endTime");

        if (startTime == null && endTime == null) {
            startTime = "";
            endTime = "";
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

        Page<Map<String,String>> objects = PageHelper.startPage(pageNum, pageSize);
        String s = objects.toString();
        String[] split = s.split(",");

        HeroCommentService service = new HeroCommentServiceImpl();

        //开始页数
        int startRow = Integer.parseInt(split[3].split("=")[1]);

        //查询结果
        List<Map<String, Object>> list = service.commentAllPageSer(username,startTime,endTime,startRow,pageSize);

        int totalCount = 0;
        for (Map<String, Object> map : list) {
            if (map.get("totalCount") != null) {
                totalCount = (Integer) map.get("totalCount");
            }
        }
        //总记录条数
        //总页面
        int totalPage = 0;
        if (totalCount % pageSize  == 0) {
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize +1;
        }
        String json = packJson(response.getStatus(), "success", list, totalPage, list.size()-1, totalCount, pageSize, pageNum);
        response.getWriter().write(json);

    }

    private String packJson(int code, String message, List<Map<String, Object>> list,
                            int totalPage, int recordCount, int totalCount,
                            int pageSize, int pageNo) throws JsonProcessingException {
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
