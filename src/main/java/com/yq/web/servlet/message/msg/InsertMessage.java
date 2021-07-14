package com.yq.web.servlet.message.msg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yq.service.MessageService;
import com.yq.service.impl.MessageServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/insertmessage")
public class InsertMessage extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }


    /**
     * 此类用于用户点击发送，将输入的信息存入数据库中
     * 插入成功 返回1 因为只插入一条数据
     * 失败，返回0 因为发生异常
     * @author chuchen
     * @date 2021/3/25 17:24
     * @param request
     * @param response
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        MessageService service = new MessageServiceImpl();
        //获取插入数据
        String message = request.getParameter("message");
        String province = request.getParameter("province");
        String username = request.getParameter("username");

        //获取是管理员插入还是前端用户插入

        if (message == null || "".equals(message)) {
            List<Map<String, Object>> mapList = service.randomFromSystemMessageService();
            message = (String)mapList.get(new Random().nextInt(mapList.size() -1)).get("message");
        }

        //设置插入时间
        Date now_date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(now_date);

        //调用service层方法
        //如果用户名为空或者null 生成一个随机用户名
        if (username == null || "".equals(username)) {
            Random random_username = new Random();
            int random_number = random_username.nextInt(1000000);
            username = "匿名用户" + random_number;
        }
        int insert_number = service.insertMessageService(message, time, province, username);

        //获取返回结果集
        Map<String,Object> map_first= new HashMap<>();
        Map<String,Object> map_two= new HashMap<>();
        Map<String,Object> map_third= new HashMap<>();

        map_third.put("date",time);
        map_third.put("uname",username);
        map_third.put("province",province);
        map_third.put("insertMessage",message);
        map_third.put("updateNumber",insert_number);
        //判断插入成功与否
        if (insert_number == 0) {
            //插入失败
            response.setStatus(202);
            map_first.put("code",response.getStatus());
            map_two.put("message","fail");
            map_first.put("code",response.getStatus());
            map_two.put("entity",map_third);
            map_first.put("data",map_two);
            return;
        }

        map_first.put("code",response.getStatus());
        map_two.put("message","success");
        map_two.put("entity",map_third);
        map_first.put("data",map_two);
        //处理
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map_first);
        System.out.println(json);
        response.getWriter().write(json);


    }
}
