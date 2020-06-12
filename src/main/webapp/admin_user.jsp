<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/26
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>
<jsp:useBean id="locator" type="dao.ResourceLocator" scope="request"/>
<jsp:useBean id="path" type="dao.PathDao" scope="request"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>用户 - 健康码管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <%@include file="scripts.jspf"%>
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
                        ${locator.type == 0?"老师":"学生"}
                    </a>
                    <span>/</span>
                </div>
                <div class="navigator-second">
                    <a href="${pageContext.request.contextPath}/admin/college">架构</a>
                    <a class="${locator.type == 0?"selected":null}" href="${pageContext.request.contextPath}/admin/user?type=teacher">老师</a>
                    <a class="${locator.type != 0?"selected":null}" href="${pageContext.request.contextPath}/admin/user?type=student">学生</a>
                </div>
            </div>
            <div class="data-control">
                <div id="msg" class="msg-info">插入成功</div>
                <div class="data-control-box">
                    <button id="button-add">添加</button>
                    <button id="button-delete">删除</button>

                    <span>将显示2020/4/7 -<label><input type="date" value=""></label>的打卡记录。</span>
                </div>
                <div id="div-data-inserted" class="data-inserted">
                    <label><input id="input-name" type="text" placeholder="姓名"></label>
                    <label><input id="input-number" type="text" placeholder="工号/学号"></label>
                    <label><input id="input-idcard" type="text" placeholder="身份证号"></label>
                    <label><input id="input-field" type="text" placeholder="所在学院/班级" ></label>
                    <button id="button-submit">提交</button>
                    <button id="button-cancel">撤销</button>
                </div>
            </div>
            <table class="table table-user">
                <thead >
                <tr id="table-user-header-row">
                    <td>&nbsp;&nbsp;</td>
                    <td>姓名</td>
                    <td>学号</td>
                    <td>身份证</td>
                    <td>路径</td>
                    <td>健康码</td>
                    <td>打卡情况</td>
                </tr>
                </thead>
<%--                <tr>--%>
<%--                    <td><label><input type="checkbox"></label></td>--%>
<%--                    <td>胡皓睿</td>--%>
<%--                    <td>201800000000</td>--%>
<%--                    <td>3300000000*******0</td>--%>
<%--                    <td>计算机科学与技术学院</td>--%>
<%--                    <td><span class="red-true">7</span></td>--%>
<%--                    <td>--%>
<%--                        <span class="green-true">&nbsp;&nbsp</span>&nbsp;--%>
<%--                        <span class="green-true">&nbsp;&nbsp;</span>&nbsp;--%>
<%--                        <span class="yellow-true">&nbsp;&nbsp;</span>&nbsp;--%>
<%--                        <span class="no-true">&nbsp;&nbsp;</span>&nbsp;--%>
<%--                        <span class="no-true">&nbsp;&nbsp;</span>&nbsp;--%>
<%--                        <span class="no-true">&nbsp;&nbsp;</span>&nbsp;--%>
<%--                        <span class="no-true">&nbsp;&nbsp;</span>&nbsp;--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                <tr>--%>
<%--                    <td><label><input type="checkbox"></label></td>--%>
<%--                    <td>胡皓睿</td>--%>
<%--                    <td>201800000000</td>--%>
<%--                    <td>3300000000*******0</td>--%>
<%--                    <td>计算机科学与技术学院</td>--%>
<%--                    <td><span class="no-true">未申领</span></td>--%>
<%--                    <td>--%>

<%--                    </td>--%>
<%--                </tr>--%>
                <tbody id="data-tbody">

                </tbody>
            </table>
        </div>
    </div>

<%--    <div>type:${locator.type}</div>--%>
<%--    <div>pageIndex:${locator.pageIndex}</div>--%>
<%--    <div>scope:${locator.scope}</div>--%>
<%--    <div>tag:${locator.tag}</div>--%>
    <script>
        locator = new Locator(${locator.type},${locator.pageIndex},'${locator.scope}', ${locator.tag});

        locator.college = '${path.college}';
        locator.collegeId = ${path.collegeId};
        locator.profession = '${path.profession}';
        locator.professionId = ${path.professionId};
        locator.xclass = '${path.xclass}';
        locator.xclassId = ${path.xclassId};
    </script>
    <script src="${pageContext.request.contextPath}/js/admin_user.js"></script>
</body>
</html>
