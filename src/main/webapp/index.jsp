
<%@ page import="java.time.Instant" %>
<%@ page import="java.sql.Date" %>
<%@ page import="enums.RoleType" %><%--
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <%@include file="scripts.jspf"%>
  </head>
  <body>
    <div class="container">

      <div class="cards">
        <% if (user.isAdmin()) { %>
        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/admin/college">
              学院管理
            </a></div>
            <div class="card-content">
              <p>进行学院、专业、班级的管理</p>
            </div>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/admin/user">
              人员管理
            </a></div>
            <div class="card-content">
              <p>老师：<mark>99</mark></p>
              <p>学生：<mark>128</mark></p>
            </div>
          </div>
        </div>
        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/admin/card">
              打卡管理
            </a></div>
            <div class="card-content">
              <p>申领：<mark>99/123</mark></p>
              <p>打卡：<mark>88/99</mark></p>
            </div>
          </div>
        </div>
        <% } %>
        <% if(user.getRole() != RoleType.SYSTEM) {%>
        <% if(!user.isAcquired()){ %>
        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/acquire">
              申领健康码
            </a></div>
            <div class="card-content">
              请申领健康码
            </div>
          </div>
        </div>

        <% } else { %>
        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/dailycard">
              每日打卡
            </a></div>
            <div class="card-content">

            </div>
          </div>

        </div>
        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/healthcode">
              我的健康码
            </a></div>
            <div class="card-content">
              <% if(user.isAcquired()){ %>
              <% if(user.getDate().equals(Date.from(Instant.now()))) { %>
              <p>健康码${user.result}</p>
              <% } else {%>
              <p>今日未打卡</p>
              <% }%>
              <% } else {%>
              <p>你还没有申领健康码</p>
              <% }%>
              <% }%>
            </div>
          </div>
        </div>
        <% }%>



        <div class="card">
          <div class="card-body">
            <div class="card-title"><a href="${pageContext.request.contextPath}/user">用户信息</a></div>
            <div class="card-content">
              <p>工号 ${user.number}</p>
              <p>姓名 ${user.name}</p>
              <p>身份 ${user.typeDisplay}</p>
              <% if(user.isAdmin()){ %>
              <p>${user.roleDisplay}</p>
              <% } %>

            </div>
          </div>
        </div>
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
