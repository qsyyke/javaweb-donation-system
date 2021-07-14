package com.yq.util.impl;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCode")
public class CheckCode extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post");
        //图片验证码
        //设置禁止缓存
        response.setHeader("Cache-Control","no-store");
        //创建一个图片对象
        int width = 100;
        int height = 50;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //美化图片
        //获取画笔对象
        Graphics g = image.getGraphics();
        //设置颜色
        g.setColor(Color.pink);
        //画方框
        g.fillRect(0,0,width-1,height-1);

        //验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789";
        //随机数对象
        Random random = new Random();
        //定义一个String 用于存储验证码
        String check_code = "";
        for (int i = 1; i <= 4; i++) {
            g.setColor(new Color(0x2AB8B8));
            g.setFont(new Font(null,0,24));
            //获取随机数字
            int nextInt = random.nextInt(str.length());
            char c = str.charAt(nextInt);
            //将c添加到字符串中
            check_code = check_code+c;
            g.drawString(c+"",(i*20-10),35);
        }
        //创建一个session对象，并将当前验证码存入session中
        HttpSession session = request.getSession();
        session.setAttribute("CHECKCODE_SERVER",check_code);
        //画点
        for (int i = 0; i < 450; i++) {
            g.setColor(Color.BLACK);
            int width1 = random.nextInt(100);
            int height1 = random.nextInt(50);
            g.drawOval(width1,height1,1,1);
        }

        //将图片输出到页面
        ImageIO.write(image,"png",response.getOutputStream());
    }
}
