package com.yq.web.servlet.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * 随机从image/tximg中返回一张图片
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/4 19:27
 **/

@WebServlet("/avatar")
public class RandomAvatar extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpg;charset=utf-8");
        request.setCharacterEncoding("utf-8");

        //获取根路径
        String realPath = request.getServletContext().getRealPath(File.separator);

        File file = new File(realPath+File.separator+"image"+File.separator+"tximg");
        File[] files = file.listFiles();

        Random random = new Random();

        //获取一个随机整数
        int index = random.nextInt(files.length - 1);

        InputStream is = new FileInputStream(files[index]);
        ServletOutputStream os = response.getOutputStream();

        byte[] bytes = new byte[1020*1024];
        int read = 0;
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes,0,read);
        }
        os.close();
    }
}
