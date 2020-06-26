
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
<jsp:useBean id="viewModel" type="dao.ViewModel" scope="request"/>
<html>
  <head>
    <title>主页 - 健康码管理系统</title>
    <link href="${pageContext.servletContext.contextPath}/css/card.css" rel="stylesheet" type="text/css" >
    <link href="${pageContext.servletContext.contextPath}/css/main.css" rel="stylesheet" type="text/css" >

    <%@include file="scripts.jspf"%>
  </head>
  <body>
    <%if(user.getType() == TypeType.ADMIN){%>
    <%@include file="header.jspf"%>
    <%}else{%>
    <div class="header-con">
      <div class="header-van">
        <div class="welcome fl">健康码 - 健康码管理系统</div>
        <div class="fr">
          <div class="login-btn fl">
            <a href="${pageContext.servletContext.contextPath}/logout">登出</a>
          </div>
        </div>
      </div>
    </div>
    <%}%>

    <%if(viewModel.getMsg() != null){ %>
    <div id="msg-box">
      <span id="msg-text">${viewModel.msg}</span>
      <div id="msg-close">关闭</div>
    </div>
    <%}%>
    <div class="container">
      <div class="cards">
        <% if (user.getType() == TypeType.ADMIN) { %>
        <div class="vanot-card">
          <div class="additional">
            <div class="user-card">
              <div class="one center">
                管理系统
              </div>
              <div class="two center">
                学院、学生、打卡
              </div>
            </div>
            <div class="more-info">
              <h1>管理系统</h1>
              <div class="coords">
                <span>______________________</span><br><br>
                <% if(user.getAdminType() == RoleType.COLLAGE){ %>
                <span><a href="${pageContext.servletContext.contextPath}/admin/college?page=profession&college=${user.fieldId}">详细信息</a></span><br>
                <% } else{%>
                <span><a href="${pageContext.servletContext.contextPath}/admin/college">详细信息</a></span><br>
                <% }%>

                <span></span><br>
              </div>
            </div>
          </div>
          <div class="general">
            <h1>管理系统</h1>
            <p></p>
            <span class="more">移动鼠标浏览功能内容</span>
          </div>
        </div>

        <% if (user.getAdminType() == RoleType.SYSTEM) { %>
        <div class="vanot-card">
          <div class="additional">
            <div class="user-card">
              <div class="one center">
                数据导入
              </div>
            </div>
            <div class="more-info">
              <h1>数据导入</h1>
              <div class="coords">
                <span>______________________</span><br><br>
                <span><a href="${pageContext.servletContext.contextPath}/admin/import">详细信息</a></span><br>
                <span></span><br>
              </div>
            </div>
          </div>
          <div class="general">
            <h1>数据导入</h1>
            <p></p>
            <span class="more">移动鼠标浏览功能内容</span>
          </div>
        </div>
        <% } %>
        <% } %>

        <div class="vanot-card">
          <div class="additional">
            <div class="user-card">
              <div class="one center">
                ${user.name}
              </div>
              <div class="two center">
                <%if(user.getType()== TypeType.STUDENT){%>
                学生
                <%}else if(user.getType() == TypeType.TEACHER){%>
                教师
                <%} else {%>
                <% if(user.getAdminType() == RoleType.SYSTEM) {%>
                系统管理员
                <% } else if(user.getAdminType() == RoleType.SCHOOL) {%>
                校级管理员
                <% } else {%>
                学院管理员
                <%}%>
                <% }%>
              </div>
              <div class="three center">
                ${user.number}
              </div>

            </div>
            <div class="more-info">
              <h1>用户信息</h1>
              <div class="coords">
                <span>______________________</span><br><br>
                <span><a href="${pageContext.servletContext.contextPath}/user">详细信息</a></span><br>
                <span></span><br>
              </div>

            </div>
          </div>

          <div class="general">
            <h1>用户信息</h1>
            <p></p>
            <span class="more">移动鼠标浏览功能内容</span>
          </div>
        </div>

        <% if(user.getType() != TypeType.ADMIN) {%>
        <div class="vanot-card green">
          <div class="additional">
            <div class="user-card">
              <div class="one center">
                <%int result = user.getResult();%>
                <%if(result == Result.No){%>
                尚未申领
                <%}else if(result == Result.GREEN){%>
                绿色
                <%}else if(result == Result.YELLOW){%>
                黄色
                <%}else{%>
                红色
                <%}%>
              </div>

              <%if(result != Result.No){%>
              <div class="two center">
                上次打卡日期
              </div>
              <div class="three center">
                ${user.date}
              </div>
              <%}else{%>
              <div class="two center">
                请申领健康码
              </div>
              <%}%>

              <svg width="110" height="110" viewBox="0 0 250 250" role="img" aria-labelledby="title desc" class="center">
              </svg>
            </div>
            <div class="more-info">
              <h1>我的健康码</h1>
              <div class="coords">
                <span>______________________</span><br><br>
                <%if(result != Result.No){%>
                <% Date date = new Date(System.currentTimeMillis());%>
                <% if(user.getDate().toString().equals(date.toString())) { %>
                <span><a href="${pageContext.servletContext.contextPath}/healthcode">
                    查看健康码
                  </a></span><br>
                <% } else {%>
                <span><a href="${pageContext.servletContext.contextPath}/dailycard">
                    每日打卡
                  </a></span><br>
                <% }%>

                <%}else{%>
                <span><a href="${pageContext.servletContext.contextPath}/acquire">
                  申领健康码
                </a></span><br>
                <%}%>

              </div>
              <div class="stats">
              </div>
            </div>
          </div>
          <div class="general">
            <h1>我的健康码</h1>
            <p>注意:<br>
              绿码可以进出校园，黄码需居家观察7天不得进入校园，红码需居家医学观察或集中隔离14天不得进入校园。</p>
            <span class="more">移动鼠标浏览功能内容</span>
          </div>
        </div>
        <% }%>
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
  <script>

  </script>
</html>
