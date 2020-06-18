<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/26
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>
<html>
<head>
    <title>打卡 - 健康码管理系统</title>
    <%@include file="scripts.jspf"%>
</head>
<body>
    <%@include file="header.jspf"%>
    <div class="container">
        <div class="section">
            <label><input id="input-download-template" type="button" value="下载模板"></label>
            <div>
                <label><input id="input-file" type="file" value="导入文件"></label>
                <label><input id="input-submit" type="button" value="导入"></label>
            </div>
            <div class="info">
            </div>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/admin_import.js"></script>
    <script>
        adminimport = new AdminImport();
    </script>
</body>
</html>
