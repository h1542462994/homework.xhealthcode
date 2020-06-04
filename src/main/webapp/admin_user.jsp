<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/26
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>
<jsp:useBean id="type" type="java.lang.String" scope="request"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>用户 - 健康码管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@include file="header.jspf"%>
    <div class="container">
        <%--        <div class="header header-fixed">--%>
        <%--            标题栏--%>
        <%--        </div>--%>
        <div class="section data-section">
            <div class="navigator">
                <div class="navigator-first">
                    <a href="${pageContext.request.contextPath}/admin/user?type=teacher">
                        ${type == "teacher"?"老师":"学生"}
                    </a>
                    <span>/</span>
                </div>
                <div class="navigator-second">
                    <a href="${pageContext.request.contextPath}/admin/college">架构</a>
                    <a class="${type == "teacher"?"selected":null}" href="${pageContext.request.contextPath}/admin/user?type=teacher">老师</a>
                    <a class="${type != "teacher"?"selected":null}" href="${pageContext.request.contextPath}/admin/user?type=student">学生</a>
                </div>
            </div>
            <div class="data-control">
                <div id="msg" class="msg-info">插入成功</div>
                <div class="data-control-box">
                    <button id="button-add">添加</button>
                    <button id="button-delete">删除</button>
                </div>
                <div id="div-data-inserted" class="data-inserted">
                    <label><input id="input-name" type="text" placeholder="请输入学院的名称"></label>
                    <button id="button-submit">提交</button>
                    <button id="button-cancel">撤销</button>
                </div>
            </div>
            <table class="table table-user-school">
                <thead>
                <tr>
                    <td>&nbsp;&nbsp;</td>
                    <td>姓名</td>
                    <td>所在学院</td>
                    <td>健康码</td>
                    <td>近期打卡情况</td>
                </tr>
                </thead>
                <%--                    <tr class="table-selected">--%>
                <%--                        <td><label><input type="checkbox"></label></td>--%>
                <%--                        <td><label><input type="text" value="计算机科学与计算机学院"></label></td>--%>
                <%--                        <td>98 / No Initialized</td>--%>
                <%--                        <td>47 / No Initialized</td>--%>
                <%--                    </tr>--%>
                <%--                    <tr>--%>
                <%--                        <td><label><input type="checkbox"></label></td>--%>
                <%--                        <td><label><input type="text" value="信息工程学院"></label></td>--%>
                <%--                        <td>45 / No Initialized</td>--%>
                <%--                        <td>20 / No Initialized</td>--%>
                <%--                    </tr>--%>
                <tbody id="data-tbody">
                    <tr>
                        <td><label><input type="checkbox"></label></td>
                        <td>胡皓睿</td>
                        <td>计算机科学与技术学院</td>
                        <td><span class="red-true">7</span></td>
                        <td>
                            <span class="green-true">&nbsp;&nbsp;</span>
                            <span class="green-true">&nbsp;&nbsp;</span>
                            <span class="yellow-true">&nbsp;&nbsp;</span>
                            <span class="no-true">&nbsp;&nbsp;</span>
                            <span class="no-true">&nbsp;&nbsp;</span>
                            <span class="no-true">&nbsp;&nbsp;</span>
                            <span class="no-true">&nbsp;&nbsp;</span>
                        </td>
                    </tr>
                    <tr>
                        <td><label><input type="checkbox"></label></td>
                        <td>胡皓睿</td>
                        <td>计算机科学与技术学院</td>
                        <td><span class="no-true">未申领</span></td>
                        <td>

                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div>${type}</div>
</body>
</html>
