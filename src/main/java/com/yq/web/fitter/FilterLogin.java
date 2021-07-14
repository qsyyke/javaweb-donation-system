/*
package com.yq.web.fitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * 过滤所有除了addCookie的请求，判断是操作前台还是操作后台，根据cookie的值
 * @author 青衫烟雨客 程钦义
 * @date 2021/5/4 14:36
 **//*


@WebFilter(filterName = "FilterLogin",value = "/*")
public class FilterLogin implements Filter {
    public void init (FilterConfig config) throws ServletException {
    }

    public void destroy () {
    }

    @Override
    public void doFilter (ServletRequest rs, ServletResponse ps, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) rs;
        HttpServletResponse response = (HttpServletResponse) ps;

        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL);

        chain.doFilter(request,response);

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            //cookie中为空，则跳转到首页
            String json = packJson(response.getStatus(), "success", "0", "http://yq.vipblogs.cn");
            response.getWriter().write(json);
            return;
        }else {
            String json = packJson(response.getStatus(), "success", "0", "http://yq.vipblogs.cn");
            response.getWriter().write(json);
            return;
        }


    }

    private String packJson(int code, String message,String right,String url) throws JsonProcessingException {
        //map用于返回json
        //第一级
        Map<String,Object> mapOne = new HashMap<>();
        //第二级
        Map<String,Object> mapTwo = new HashMap<>();
        //第三级别
        Map<String,Object> mapThree = new HashMap<>();
        //json
        ObjectMapper mapper = new ObjectMapper();

        mapThree.put("isRight",right);
        mapThree.put("url",url);

        mapTwo.put("message",message);
        mapTwo.put("entity",mapThree);

        mapOne.put("code",code);
        mapOne.put("data",mapTwo);

        String json = mapper.writeValueAsString(mapOne);
        return json;
    }

}
*/
