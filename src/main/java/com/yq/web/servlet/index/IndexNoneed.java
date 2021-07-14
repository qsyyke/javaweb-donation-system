package com.yq.web.servlet.index;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/index.html")
public class IndexNoneed extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        String s = "\n" + "\t\t\t\t\t\tquu..__\n" + "\t\t\t\t\t\t $$$b  `---.__\n" + "\t\t\t\t\t\t  \"$$b        `--.                          ___.---uuudP\n" + "\t\t\t\t\t\t   `$$b           `.__.------.__     __.---'      $$$$\"              .\n" + "\t\t\t\t\t\t\t \"$b          -'            `-.-'            $$$\"              .'|\n" + "\t\t\t\t\t\t\t   \".                                       d$\"             _.'  |\n" + "\t\t\t\t\t\t\t\t `.   /                              ...\"             .'     |\n" + "\t\t\t\t\t\t\t\t   `./                           ..::-'            _.'       |\n" + "\t\t\t\t\t\t\t\t\t/                         .:::-'            .-'         .'\n" + "\t\t\t\t\t\t\t\t   :                          ::''\\          _.'            |\n" + "\t\t\t\t\t\t\t\t  .' .-.             .-.           `.      .'               |\n" + "\t\t\t\t\t\t\t\t  : /'$$|           .@\"$\\           `.   .'              _.-'\n" + "\t\t\t\t\t\t\t\t .'|$u$$|          |$$,$$|           |  <            _.-'\n" + "\t\t\t\t\t\t\t\t | `:$$:'          :$$$$$:           `.  `.       .-'\n" + "\t\t\t\t\t\t\t\t :                  `\"--'             |    `-.     \\\n" + "\t\t\t\t\t\t\t\t:##.       ==             .###.       `.      `.    `\\\n" + "\t\t\t\t\t\t\t\t|##:                      :###:        |        >     >\n" + "\t\t\t\t\t\t\t\t|#'     `..'`..'          `###'        x:      /     /\n" + "\t\t\t\t\t\t\t\t \\                                   xXX|     /    ./\n" + "\t\t\t\t\t\t\t\t  \\                                xXXX'|    /   ./\n" + "\t\t\t\t\t\t\t\t  /`-.                                  `.  /   /\n" + "\t\t\t\t\t\t\t\t :    `-  ...........,                   | /  .'\n" + "\t\t\t\t\t\t\t\t |         ``:::::::'       .            |<    `.\n" + "\t\t\t\t\t\t\t\t |             ```          |           x| \\ `.:``.\n" + "\t\t\t\t\t\t\t\t |                         .'    /'   xXX|  `:`M`M':.\n" + "\t\t\t\t\t\t\t\t |    |                    ;    /:' xXXX'|  -'MMMMM:'\n" + "\t\t\t\t\t\t\t\t `.  .'                   :    /:'       |-'MMMM.-'\n" + "\t\t\t\t\t\t\t\t  |  |                   .'   /'        .'MMM.-'\n" + "\t\t\t\t\t\t\t\t  `'`'        qsyyke     :  ,'          |MMM<\n" + "\t\t\t\t\t\t\t\t\t|                     `'            |tbap\\\n" + "\t\t\t\t\t\t\t\t\t \\                                  :MM.-'\n" + "\t\t\t\t\t\t\t\t\t  \\                 |              .''\n" + "\t\t\t\t\t\t\t\t\t   \\.               `.            /\n" + "\t\t\t\t\t\t\t\t\t\t/     .:::::::.. :           /\n" + "\t\t\t\t\t\t\t\t\t   |     .:::::::::::`.         /\n" + "\t\t\t\t\t\t\t\t\t   |   .:::------------\\       /\n" + "\t\t\t\t\t\t\t\t\t  /   .''               >::'  /\n" + "\t\t\t\t\t\t\t\t\t  `',:                 :    .'\n" + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t   `:.:'\n" + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t   ";
        response.getWriter().write(s);
    }
}
