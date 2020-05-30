<%--
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
    <title>主页 - 健康码管理系统</title>
  </head>
  <body>
    <%@include file="header.jspf"%>
    <div class="container">
      <div class="container-card">
        <% if (user.isAdmin()) { %>
        <a href="${pageContext.request.contextPath}/admin/college">
          学院管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/user">
          人员管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/card">
          打卡管理
        </a>
        <% }%>
        <a href="${pageContext.request.contextPath}/acquire">
          申领健康码
        </a>
        <a href="${pageContext.request.contextPath}/dailycard">
          每日打卡
        </a>
        <a href="${pageContext.request.contextPath}/healthcode">
          我的健康码
        </a>
      </div>
    </div>
    <div>身份:${user.typeDisplay}</div>
    <div>是否是管理员:${user.admin}</div>
    <div>管理员类别:${user.roleDisplay}</div>
    <div>姓名:${user.name}</div>
    <div>工号:${user.number}</div>
    <div>是否申领健康码:${user.acquired}</div>
    <div>健康码状态:${user.result}</div>
    <div>健康码日期:${user.date}</div>
  </body>
</html>
