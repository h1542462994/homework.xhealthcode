
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
    <title>主页 - 健康码管理系统</title>
    <link href="css/card.css" rel="stylesheet" type="text/css" >
    <link href="css/main.css" rel="stylesheet" type="text/css" >

    <%@include file="scripts.jspf"%>
  </head>
  <body>
  <div class="header-con">
    <div class="header-van">
      <div class="welcome fl">主页 - 健康码管理系统</div>
      <div class="fr">
        <div class="login-btn fl">
          <a href="${pageContext.request.contextPath}/logout">登出</a>
        </div>
      </div>
    </div>
  </div>


    <div class="container">
      <div class="cards">
        <% if (user.getType() == TypeType.ADMIN) { %>
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
            <div class="card-title"><a href="${pageContext.request.contextPath}/admin/import">
              数据导入
            </a></div>
            <div class="card-content">
              <p>申领：<mark>99/123</mark></p>
              <p>打卡：<mark>88/99</mark></p>
            </div>
          </div>
        </div>
        <% } %>
<%--        <% if(user.getAdminType() != RoleType.SYSTEM) {%>--%>
<%--        <% if(user.getResult() == Result.No){ %>--%>
<%--        <div class="card">--%>
<%--          <div class="card-body">--%>
<%--            <div class="card-title"><a href="${pageContext.request.contextPath}/acquire">--%>
<%--              申领健康码--%>
<%--            </a></div>--%>
<%--            <div class="card-content">--%>
<%--              请申领健康码--%>
<%--            </div>--%>
<%--          </div>--%>
<%--        </div>--%>

<%--        <% } else { %>--%>
<%--        <% Date date = new Date(System.currentTimeMillis());%>--%>
<%--        <div class="card">--%>
<%--          <div class="card-body">--%>
<%--            <div class="card-title">--%>
<%--              <% if(user.getDate().toString().equals(date.toString())) { %>--%>
<%--                <a href="">--%>
<%--                已完成打卡--%>
<%--              <% } else {%>--%>
<%--                <a href="${pageContext.request.contextPath}/dailycard">--%>
<%--                每日打卡--%>

<%--              <% }%>--%>


<%--            </a></div>--%>
<%--            <div class="card-content">--%>

<%--            </div>--%>
<%--          </div>--%>

<%--        </div>--%>
<%--        <div class="card">--%>
<%--          <div class="card-body">--%>
<%--            <div class="card-title"><a href="${pageContext.request.contextPath}/healthcode">--%>
<%--              我的健康码--%>
<%--            </a></div>--%>
<%--            <div class="card-content">--%>
<%--              <% if(user.getResult() != Result.No){ %>--%>
<%--              <% if(user.getDate().toString().equals(date.toString())) { %>--%>
<%--              <p>健康码</p>--%>
<%--              <p>${user.result}</p>--%>
<%--              <% } else {%>--%>
<%--              <p>今日未打卡</p>--%>
<%--              <% }%>--%>
<%--              <% } else {%>--%>
<%--              <p>你还没有申领健康码</p>--%>
<%--              <% }%>--%>
<%--              <% }%>--%>
<%--            </div>--%>
<%--          </div>--%>
<%--        </div>--%>
<%--        <% }%>--%>


<%--        <div class="card">--%>
<%--          <div class="card-body">--%>
<%--            <div class="card-title"><a href="${pageContext.request.contextPath}/user">用户信息</a></div>--%>
<%--            <div class="card-content">--%>
<%--              <p>工号 ${user.number}</p>--%>
<%--              <p>姓名 ${user.name}</p>--%>
<%--              <p>身份 ${user.type}</p>--%>
<%--              <% if(user.getType() == TypeType.ADMIN){ %>--%>
<%--              <p>${user.adminType}</p>--%>
<%--              <% } %>--%>

<%--            </div>--%>
<%--          </div>--%>
<%--        </div>--%>

<%--        <br>--%>

      </div>

      <div class="vanot-card">
        <div class="additional">
          <div class="user-card">
            <div class="one center">
              ${user.name}
            </div>
            <div class="two center">
              <%if(user.getType()==1){%>
                教师
              <%}else{%>
                学生
              <%}%>

            </div>
            <div class="three center">
              ${user.number}
            </div>

          </div>
          <div class="more-info">
            <h1>用户信息</h1>
            <div class="coords">
              <span>______________________</span><br><br>
              <span><a href="${pageContext.request.contextPath}/user">详细信息</a></span><br>
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
                  <span><a href="${pageContext.request.contextPath}/healthcode">
                    查看健康码
                  </a></span><br>
                <% } else {%>
                  <span><a href="${pageContext.request.contextPath}/dailycard">
                    每日打卡
                  </a></span><br>
                <% }%>

              <%}else{%>
                <span><a href="${pageContext.request.contextPath}/acquire">
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
