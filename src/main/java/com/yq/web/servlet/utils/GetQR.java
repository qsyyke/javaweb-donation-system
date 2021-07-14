package com.yq.web.servlet.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

/**
 * 用户下单生成二维码，二维码被保存至本地文件，此servlet从本地拿到用户刚下单生成的二维码<p></p>\
 * 用户下单之后，通过时间戳进行命名，所以拿到这个文件，也是通过文件的文件获取
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/3 21:02
 **/


@WebServlet("/qr")
public class GetQR extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //获取图片名字
        String fileName = request.getParameter("time")+".png";

        //获取路径
        String path = null;

        //设置空指针变量
        boolean fileExist = true;

        try {
            path = this.getClass().getClassLoader().getResource("qrimg/" +fileName).getPath();
        } catch (Exception e) {
            e.printStackTrace();
            //如果运行到这里，说明图片文件不存在
            fileExist = false;

        }

        if (!fileExist) {
            //图片不存在

            response.getOutputStream().write("<script>alert(\"图片加载失败，请重新提交\")</script>".getBytes());
            return;
        }



        //设置响应头
        response.setContentType("image/png");
        InputStream is = new FileInputStream(path);

        byte[] bytes = new byte[1024];

        ServletOutputStream os = response.getOutputStream();

        int read = 0;
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes,0,read);
        }

    }
}
