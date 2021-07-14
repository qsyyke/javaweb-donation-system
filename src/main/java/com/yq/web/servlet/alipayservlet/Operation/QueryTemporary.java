package com.yq.web.servlet.alipayservlet.Operation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * 用于查询临时订单，用于用户未付款，但是已经下单的情况
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/10 8:48
 **/

@WebServlet("/querytem")
public class QueryTemporary extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");

        //获取订单号
        String outTradeNo = request.getParameter("outTradeNo");
    }
}
