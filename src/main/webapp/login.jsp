<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/23
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <h1>登录界面</h1>
    <div id="msg" style="color:red;">异常信息</div>
    <form action="" method="post">
        <div>
            <label for="number">工号</label>
            <input id="number" name="number" type="text" placeholder="请输入工号">
        </div>
        <div id="input-name" style="display: none;">
            <label for="name">姓名</label>
            <input id="name" name="name" type="text" placeholder="请输入姓名">
        </div>
        <div>
            <label for="passport">通行证</label>
            <input id="passport" name="passport" type="password" placeholder="请输入通行证">
        </div>
        <div>
            登录方式
            <label><input name="is_admin" type="radio" value="true">管理员</label>
            <label><input name="is_admin" type="radio" checked value="false">普通用户</label>
        </div>
        <div>
            <input type="submit">
        </div>
    </form>
    <script type="text/javascript" src="js/login.js"></script>
</body>
</html>
