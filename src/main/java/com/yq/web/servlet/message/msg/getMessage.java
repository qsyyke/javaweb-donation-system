package com.yq.web.servlet.message.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.MessageService;
import com.yq.service.impl.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/message")
public class getMessage extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        //创建service对象
        MessageService message = new MessageServiceImpl();
        //调用getMessage获取结果集
        List<Map<String, Object>> maps = message.getMessage();

        //listAll保存所有的数组
        //List<List> listAll = new ArrayList<>();
        //集合中记录的条数
        /*int size = maps.size();
        //i是一个整数
        int i = size / 10;
        if (i == 0) {
            List<Map> list = new ArrayList<>();
            //记录小于10[[[],[],[]],[]]
            for (Map<String, Object> map : maps) {
                list.add(map);
            }
            listAll.add(list);
        }else {
            for (int i1 = 0; i1 <= i; i1++) {
                List list = new ArrayList();
                //将新创建的集合放入所有的集合中
                listAll.add(list);
            }
            for (int i1 = 0; i1 < i; i1++) {
                for (int i2 = 0; i2 < 10; i2++) {
                    //获取总数组中的元素
                    Map<String, Object> map = maps.get((i1 * 10) + i2);
                    listAll.get(i1).add(map);
                }
            }
        }*/
        //listAll是一个三维集合
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(maps);
        response.getWriter().write(json);


    }
}
