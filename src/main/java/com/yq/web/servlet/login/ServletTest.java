package com.yq.web.servlet.login;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logintest1")
public class ServletTest extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        response.sendRedirect("login.html");
//        request.getRequestDispatcher("/message.html").forward(request,response);
        System.out.println("请求了");

        /*response.getWriter().write("你好");
        response.getWriter().write("你sdf好sdsdfsdfsdf");

        if (request.getParameter("c").equals("c")) {
            request.getRequestDispatcher("/message.html").forward(request,response);
            return;
        }else if (request.getParameter("c").equals("cc")){
            response.sendRedirect("login.html");
            return;
        }
        System.out.println("end");*/
    }
}
