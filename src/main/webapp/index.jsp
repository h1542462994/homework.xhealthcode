<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/20
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserInfo" scope="request"/>
<html>
  <head>
    <title>主页</title>
  </head>
  <body>
    <%@include file="header.jspf"%>
    <div class="container">
      <div class="container-card">
        <% if (user.isAdmin()) { %>
        <a href="${pageContext.request.contextPath}/admin">
          学院管理
        </a>
        <a href="${pageContext.request.contextPath}/login">
          人员管理
        </a>
        <a href="${pageContext.request.contextPath}/login">
          打卡管理
        </a>
        <% }%>
        <a href="${pageContext.request.contextPath}/login">
          申领健康码
        </a>
        <a href="${pageContext.request.contextPath}/login">
          每日打卡
        </a>
        <a href="${pageContext.request.contextPath}/login">
          我的健康码
        </a>
      </div>
    </div>
    <div>${user.role}</div>
    <div>${user.admin}</div>
    <div>${user.name}</div>
    <div>${user.number}</div>
  </body>
</html>
