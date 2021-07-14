<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="shortcut icon" href="./favicon.ico" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>支付-幸得有你 山河无恙</title>
    <link rel="stylesheet" href="http://api.vipblogs.cn:8080//css/alipay.css">
    <link rel="stylesheet" href="http://api.vipblogs.cn:8080//css/head.css">
</head>
<body>
    <header>
        <h1>支付宝付款</h1>
        <ul>
            <li>
                <span class="top_border head_donate"><a href="http://yq.vipblogs.cn/index2.html">首页</a></span>
            </li>
            <li>
                <span class="top_border head_donate"><a href="http://yq.vipblogs.cn/message.html">留言</a></span>
            </li>
            <li>
                <span class="top_border head_donate"><a href="http://yq.vipblogs.cn/donateshow.html">统计</a></span>
            </li>
            <li>
                <span class="top_border head_donate"><a href="http://yq.vipblogs.cn/donate.html">查询</a></span>
            </li>
        </ul>
    </header>
<div class="nav">
    <div class="header">
        <div class="logo"><img src="http://api.vipblogs.cn:8080/zfb.png" alt=""></div>
    </div>
    <section class="mainbox">
        <div class="qr">
            <img class="qrimg" src="${qrpath}" alt="">
<%--            <img class="qrimg" src="./qrimg/1.png" alt="">--%>
            <div style="display: none" class="mask qrm"></div>
            <div style="display: none" class="load decr">请稍等...</div>
        </div>
    </section>
</div>
<script src="http://api.vipblogs.cn:8080/js/jquery-3.5.1.min.js"></script>
<script src="http://api.vipblogs.cn:8080/js/alipay.js"></script>
</body>
</html>