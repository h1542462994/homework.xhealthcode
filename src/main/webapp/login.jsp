<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/23
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="viewModel" type="dao.LoginViewModel" scope="request"/>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>登录界面 - 健康码管理系统</title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/login.css">
    <%@include file="scripts.jspf"%>
</head>
<body>
<div class="screen">
    <div class="center-block">
        <div class="login-title">
            <h1>欢迎登录健康码管理系统</h1>
        </div>
        <div class="login-form">

            <div id="msg-error">
                ${viewModel.msg}
            </div>
            <form action="${pageContext.servletContext.contextPath}/login" method="post">
                <div id="input-type-row" class="form-row-type">
                    <label class="selected"><input name="type" type="radio" value="0" checked>学生登录</label>
                    <label><input name="type" type="radio" value="1">教师登录</label>
                    <label><input name="type" type="radio" value="2">管理员登录</label>
                </div>
                <div id="input-number-row" class="form-row">
                    <label for="input-number" class="form-label">工号</label>
                    <input id="input-number" name="number" class="form-input" type="text" placeholder="请输入工号" autofocus>
                    <span class="form-error">${viewModel.errors['number']}</span>
                </div>
                <div id="input-name-row" class="form-row">
                    <label for="input-name" class="form-label">姓名</label>
                    <input id="input-name" name="name" class="form-input" type="text" placeholder="请输入姓名">
                    <span class="form-error">${viewModel.errors['name']}</span>
                </div>
                <div id="input-passport-row" class="form-row">
                    <label for="input-passport" class="form-label">通行证</label>
                    <input id="input-passport" class="form-input" name="passport" type="password" placeholder="请输入通行证">
                    <span class="form-error">${viewModel.errors['passport']}</span>
                </div>
                <div id="input-submit-row" class="form-submit-row">
                    <input type="submit" value="登录">
                </div>
            </form>

        </div>
    </div>

</div>

<script type="text/javascript" src="js/login.js"></script>
</body>
</html>