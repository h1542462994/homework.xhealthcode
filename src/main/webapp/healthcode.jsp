<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>
<html>
<head>
    <title>健康码 - 健康码管理系统</title>
    <link href="css/main.css" rel="stylesheet" type="text/css" >
    <link href="css/form.css" rel="stylesheet" type="text/css" >

    <%@include file="scripts.jspf"%>

</head>
<body>
<div class="header-con">
    <div class="header-van">
        <div class="welcome fl">健康码 - 健康码管理系统</div>
        <div class="fr">
            <div class="login-btn fl">
                <a href="${pageContext.request.contextPath}/logout">登出</a>
            </div>
        </div>
    </div>
</div>

<div class="container-van">
    <div class="row-van">
        <div class="column-van">

            <img src="${pageContext.request.contextPath}/image/QRCodeOut/${user.number}.png">

        </div>
    </div>
</div>

<div class="footer">
    <div class="foot-link">
        <a href="#">LINK1</a>
        <span>|</span>
        <a href="#">LINK2</a>
    </div>
    <p>CopyRight © 2020 浙江工业大学-蒋贤伟 All Rights Reserved</p>
</div>
</body>
</html>
