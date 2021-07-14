package com.yq.web.servlet.news.client;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 监听器类 用于初始化
 * @author 青衫烟雨客 程钦义
 * @date 2021/3/27 19:12
 **/


public class ContextListener implements ServletContextListener {
    //等待时间
    private int waitNewsTime;

    //是否终止休眠
    private boolean isStopSleep;

    private ServletContext context;
    private HttpServletRequest request;
    private HttpServletResponse response;
    @Override
    public void contextInitialized (ServletContextEvent servletContextEvent) {
        /*
        *         /*
        quu..__
         $$$b  `---.__
          "$$b        `--.                          ___.---uuudP
           `$$b           `.__.------.__     __.---'      $$$$"              .
             "$b          -'            `-.-'            $$$"              .'|
               ".                                       d$"             _.'  |
                 `.   /                              ..."             .'     |
                   `./                           ..::-'            _.'       |
                    /                         .:::-'            .-'         .'
                   :                          ::''\          _.'            |
                  .' .-.             .-.           `.      .'               |
                  : /'$$|           .@"$\           `.   .'              _.-'
                 .'|$u$$|          |$$,$$|           |  <            _.-'
                 | `:$$:'          :$$$$$:           `.  `.       .-'
                 :                  `"--'             |    `-.     \
                :##.       ==             .###.       `.      `.    `\
                |##:                      :###:        |        >     >
                |#'     `..'`..'          `###'        x:      /     /
                 \                                   xXX|     /    ./
                  \                                xXXX'|    /   ./
                  /`-.                                  `.  /   /
                 :    `-  ...........,                   | /  .'
                 |         ``:::::::'       .            |<    `.
                 |             ```          |           x| \ `.:``.
                 |                         .'    /'   xXX|  `:`M`M':.
                 |    |                    ;    /:' xXXX'|  -'MMMMM:'
                 `.  .'                   :    /:'       |-'MMMM.-'
                  |  |                   .'   /'        .'MMM.-'
                  `'`'                   :  ,'          |MMM<
                    |                     `'            |tbap\
                     \                                  :MM.-'
                      \                 |              .''
                       \.               `.            /
                        /     .:::::::.. :           /
                       |     .:::::::::::`.         /
                       |   .:::------------\       /
                      /   .''               >::'  /
                      `',:                 :    .'
                                           `:.:'

        */

        System.out.println(" quu..__\n" + "         $$$b  `---.__\n" + "          \"$$b        `--.                          ___.---uuudP\n" + "           `$$b           `.__.------.__     __.---'      $$$$\"              .\n" + "             \"$b          -'            `-.-'            $$$\"              .'|\n" + "               \".                                       d$\"             _.'  |\n" + "                 `.   /                              ...\"             .'     |\n" + "                   `./                           ..::-'            _.'       |\n" + "                    /                         .:::-'            .-'         .'\n" + "                   :                          ::''\\          _.'            |\n" + "                  .' .-.             .-.           `.      .'               |\n" + "                  : /'$$|           .@\"$\\           `.   .'              _.-'\n" + "                 .'|$u$$|          |$$,$$|           |  <            _.-'\n" + "                 | `:$$:'          :$$$$$:           `.  `.       .-'\n" + "                 :                  `\"--'             |    `-.     \\\n" + "                :##.       ==             .###.       `.      `.    `\\\n" + "                |##:                      :###:        |        >     >\n" + "                |#'     `..'`..'          `###'        x:      /     /\n" + "                 \\                                   xXX|     /    ./\n" + "                  \\                                xXXX'|    /   ./\n" + "                  /`-.                                  `.  /   /\n" + "                 :    `-  ...........,                   | /  .'\n" + "                 |         ``:::::::'       .            |<    `.\n" + "                 |             ```          |           x| \\ `.:``.\n" + "                 |                         .'    /'   xXX|  `:`M`M':.\n" + "                 |    |                    ;    /:' xXXX'|  -'MMMMM:'\n" + "                 `.  .'                   :    /:'       |-'MMMM.-'\n" + "                  |  |                   .'   /'        .'MMM.-'\n" + "                  `'`'                   :  ,'          |MMM<\n" + "                    |                     `'            |tbap\\\n" + "                     \\                                  :MM.-'\n" + "                      \\                 |              .''\n" + "                       \\.               `.            /\n" + "                        /     .:::::::.. :           /\n" + "                       |     .:::::::::::`.         /\n" + "                       |   .:::------------\\       /\n" + "                      /   .''               >::'  /\n" + "                      `',:                 :    .'\n" + "                                           `:.:'\n" + "         \n");

        System.out.println("服务器启动成功 正在设置....");
        context  = servletContextEvent.getServletContext();

        /*//是否开启自动更新
        boolean isOpenUpdate = true;
        isOpenUpdate =  (Boolean)context.getAttribute("isOpenUpdate");

        //设置时间
//        int updateTime = 1000*60*60*24;
        int updateTime = 5000;
        updateTime = (Integer)context.getAttribute("autoUpdateTime");

        System.out.println("默认自动更新时间为 ---> "+updateTime);
        System.out.println("是否开启自动更新 ---> "+isOpenUpdate);*/
        /*NewsWebClient newsWebClient = new NewsWebClient(context, updateTime);
        boolean newsUpdate = newsWebClient.isNewsUpdate();
        System.out.println("自动更新状态 ---> "+newsUpdate);*/

    }

    @Override
    public void contextDestroyed (ServletContextEvent servletContextEvent) {
        System.out.println("服务器被销毁");
    }
}
