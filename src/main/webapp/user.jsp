
<%@ page import="java.time.Instant" %>
<%@ page import="java.sql.Date" %>
<%@ page import="enums.RoleType" %>
<%@ page import="enums.TypeType" %>
<%@ page import="enums.Result" %>
<%@ page import="java.util.concurrent.locks.ReentrantLock" %><%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/20
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>
<html>
<head>
    <title>用户信息 - 健康码管理系统</title>
    <link href="css/main.css" rel="stylesheet" type="text/css" >
    <link href="css/form.css" rel="stylesheet" type="text/css" >
    <%@include file="scripts.jspf"%>
</head>
<body>
<div class="header-con">
    <div class="header-van">
        <div class="welcome fl">用户信息 - 健康码管理系统</div>
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
            <form action="${pageContext.request.contextPath}/acquire" method="post">
                <%%>
                <%%>
                <%%>
                <%%>
<%--            <%--内容有待添加--%>
                <div id="basic-info" class="basic-info-card">
                    <label for="input-id" class="form-label">UserId</label>
                    <input id="input-id" name="name" class="form-input" type="text" placeholder="${user.id}" readonly><br>

                    <label for="input-name" class="form-label">姓名</label>
                    <input id="input-name" name="name" class="form-input" type="text" placeholder="${user.name}" readonly><br>

                    <label for="input-number" class="form-label">学号/工号</label>
                    <input id="input-number" name="number" class="form-input" type="text" placeholder="${user.number}" readonly><br>

                    <label for="input-idCard" class="form-label">身份证号</label>
                    <input id="input-idCard" name="idCard" class="form-input" type="text" placeholder="${user.idCard}" readonly><br>

                </div>
            </form>
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
