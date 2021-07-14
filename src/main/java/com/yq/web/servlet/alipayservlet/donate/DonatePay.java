package com.yq.web.servlet.alipayservlet.donate;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.yq.alipay.trade.config.Configs;
import com.yq.alipay.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.yq.alipay.trade.model.result.AlipayF2FPrecreateResult;
import com.yq.alipay.trade.service.AlipayTradeService;
import com.yq.alipay.trade.service.impl.AlipayTradeServiceImpl;
import com.yq.alipay.trade.utils.ZxingUtils;
import com.yq.domain.DomainProperties;
import com.yq.domain.OutTradeInfo;
import com.yq.domain.TemporaryTradeNo;
import com.yq.domain.User;
import com.yq.service.MessageService;
import com.yq.service.OutTradeNoService;
import com.yq.service.TemporaryTradeNoService;
import com.yq.service.UserService;
import com.yq.service.impl.MessageServiceImpl;
import com.yq.service.impl.OutTradeServiceImpl;
import com.yq.service.impl.TemporaryTradeNoServiceImpl;
import com.yq.service.impl.UserServiceImpl;
import com.yq.util.impl.OutTradeNoRandom;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 如果下单成功，那么就会将参数信息保存至域对象中，这里不能保存至session中，因为异步通知不是同一个session对象<p></p>
 * 用户点击付款之后的请求处理<p></p>
 * ServletContext servletContext = request.getServletContext();
 * servletContext.setAttribute(outTradeNo,parameterMap);
 * @author 青衫烟雨客 程钦义
 * @date 2021/4/3 17:24
 **/

@WebServlet("/donatepay")
public class DonatePay extends HttpServlet {
     /** service层对象 */
    private OutTradeNoService tradeNoService = new OutTradeServiceImpl();

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Properties pro = DomainProperties.getPro();

        String domain = (String) pro.get("domain");
        int maxAge = Integer.parseInt((String) pro.get("maxAge"));
        String cookiePath = (String) pro.get("path");

        String zfbProStr = "zfbinfo.properties";
        InputStream re = Thread.currentThread().getContextClassLoader().getResourceAsStream(zfbProStr);
        Properties zfbPro = new Properties();
        zfbPro.load(re);

        String zfbNotifyUrl = (String) zfbPro.get("notifyUrl");
        if (zfbNotifyUrl == null) {
            zfbNotifyUrl = "";
        }


        String separator = File.separator;
        //路径
        String path = request.getSession().getServletContext().getRealPath(File.separator)+separator+"qrimg";

        System.out.println("path: "+path);

        //订单号
        String outTradeNo = getOutTradeNo();

        //获取订单集合
        OutTradeInfo tradeInfo = new OutTradeInfo();

        //获取用户名
        String username = request.getParameter("username");
        //将用户名添加到cookie中
        String encode = URLEncoder.encode(username, StandardCharsets.UTF_8.name());
        Cookie cookieUsername = new Cookie("username",encode);
        Cookie cookieAdmin = new Cookie("admin","0");

        cookieUsername.setDomain(domain);
        cookieAdmin.setDomain(domain);

        cookieAdmin.setMaxAge(maxAge);
        cookieUsername.setMaxAge(maxAge);

        cookieAdmin.setPath(cookiePath);
        cookieUsername.setPath(cookiePath);

        response.addCookie(cookieAdmin);
        response.addCookie(cookieUsername);

        //获取用户省份
        String userProvince = request.getParameter("userProvince");

        //获取捐赠地
        String donateProvince = request.getParameter("donateProvince");

        if ("".equals(userProvince) || userProvince == null) {
            //用户省份为空 默认是云南
            userProvince = "云南";
        }

        if ("".equals(donateProvince) || donateProvince == null) {
            //捐赠到的省份为空 默认是全国
            donateProvince = "全国";
        }

        String email = request.getParameter("email");
        String wishgoods = request.getParameter("wishgoods");
        String userCity = request.getParameter("userCity");
        String userArea = request.getParameter("userArea");
        String donateCity = request.getParameter("donateCity");
        String donateArea = request.getParameter("donateArea");

        if (userCity == null) {
            userCity = "";
        }

        if (userArea == null) {
            userArea = "";
        }

        tradeInfo.setUsername(username);
        tradeInfo.setOutTradeNo(outTradeNo);
        tradeInfo.setEmail(email);
        tradeInfo.setWishGoods(wishgoods);
        tradeInfo.setUserProvince(userProvince);
        tradeInfo.setUserCity(userCity);
        tradeInfo.setUserArea(userArea);
        tradeInfo.setDonateProvince(donateProvince);
        tradeInfo.setDonateCity(donateCity);
        tradeInfo.setDonateArea(donateArea);

        List<Map<String, Object>> list = selectUserInfo(email, username);
        if (list.size() == 0) {
            //数据库中没有该用户信息
            //创建一个用户对象
            User user = new User(username,email,userProvince+userCity+userArea,userCity,userArea,userProvince,0);
            UserService userService = new UserServiceImpl();
            int insertUserInfoNum = userService.insertUserInfoSer(user);

            if (insertUserInfoNum == 0) {
                log("用户插入失败");
            }
        }

        //获取留言信息
        String message = request.getParameter("message");

        ServletContext servletContext = request.getServletContext();

        //标题
        String subject = "疫情捐款";

        // 支付超时，定义为120分钟
        String timeoutExpress = "200m";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = request.getParameter("totalAmount");

        Log log = LogFactory.getLog("trade_precreate");

        //获取参数"outTradeNo 商户订单号
        // 一定要在创建AlipayTradeService之前设置参数   加载配置文件
        Configs.init("zfbinfo.properties");

        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        AlipayTradePrecreateRequestBuilder builder =new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject)
                .setTotalAmount(totalAmount)
                .setOutTradeNo(outTradeNo)
                .setSellerId(sellerId)
                .setStoreId(storeId)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(zfbNotifyUrl);
                //.setNotifyUrl("http://api.vipblogs.cn/notify");
                //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);

        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                //插入留言 留言不为空或者是null时，才会进行插入
                if (!"null".equals(message) && !("".equals(message)) && message!=null) {

                    //调用service
                    MessageService messageService = new MessageServiceImpl();
                    String date = new SimpleDateFormat().format(new Date());
                    int i = messageService.insertMessageService(message, date, userProvince, username);
                }

                AlipayTradePrecreateResponse res = result.getResponse();

                String qrCode = res.getQrCode();
                long time = new Date().getTime();
                ZxingUtils.getQRCodeImge(qrCode,256,path+File.separator+time+".png");

                //存储订单信息
                servletContext.setAttribute(outTradeNo,tradeInfo);

                //存储时间 另一个servlet通过时间获取订单号
                servletContext.setAttribute("time@"+time,outTradeNo);

                //临时插入对象
                TemporaryTradeNo temporaryTradeNo = new TemporaryTradeNo();
                temporaryTradeNo.setOut_trade_no(outTradeNo);
                TemporaryTradeNoService tradeNoService = new TemporaryTradeNoServiceImpl();

                int temporaryNumber = tradeNoService.insertTemporaryTradeNo(temporaryTradeNo);

                if (temporaryNumber == 0) {
                    log.error("插入临时订单数据失败");
                }

                System.out.println("订单号: "+outTradeNo);
                //请求转发
                request.setAttribute("qrpath","http://api.vipblogs.cn:8080/qrimg/"+time+".png");
                //request.setAttribute("qrpath","http://localhost/qrimg/"+time+".png");
                String qrpath = (String)request.getAttribute("qrpath");

                System.out.println("付款path: "+qrpath);

                request.getRequestDispatcher("alipay.jsp").forward(request,response);
                return;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                response.getWriter().write("<script>alert(\"很抱歉，下单失败，请重试\")</script>");
                return;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                response.getWriter().write("<script>alert(\"很抱歉，下单失败，请重试\")</script>");
                return;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                response.getWriter().write("<script>alert(\"很抱歉，下单失败，请重试\")</script>");
                return;
        }
    }

    /**
     * 用于生成一个订单号，在方法中会使用生成的这个订单号到数据库中进行查找，看是否有相同<p></p>
     * 如果有相同，则重新生成一个
     * @author chuchen
     * @date 2021/4/4 7:19
     * @return String
     */
    private String getOutTradeNo() {

        String outTradeNo = new OutTradeNoRandom().getOutTradeNo();

        //调用service层进行查询，是否数据库中存在
        int selectNumber = selectTradeNo(outTradeNo);

        //存在，则重新生成一个
        while (selectNumber != 0) {
            //存在
            outTradeNo = new OutTradeNoRandom().getOutTradeNo();
            selectNumber = selectTradeNo(outTradeNo);
        }

        //返回
        return outTradeNo;
    }

    /**
     * 操作service层
     * return tradeNoService.selectOutTradeNoService(outTradeNo);
     * @author chuchen
     * @date 2021/4/4 7:42
     * @param outTradeNo outTradeNo
     * @return int
     */
    private int selectTradeNo(String outTradeNo) {
        int selectNumber = tradeNoService.selectOutTradeNoService(outTradeNo);
        if (selectNumber == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 根据用户名和邮箱查询数据库中是否存在该用户名
     * @author chuchen
     * @date 2021/4/10 18:34
     * @param email email
     * @param username username
     * @return List<Map<String,Object>>
     */
    private List<Map<String, Object>> selectUserInfo(String email,String username) {
        UserService service = new UserServiceImpl();
        List<Map<String, Object>> list = service.usernameByEmailSer(email, username);
        return list;
    }
}
