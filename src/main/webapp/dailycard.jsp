<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/26
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.time.Instant" %>
<%@ page import="java.sql.Date" %>
<%@ page import="enums.RoleType" %>
<%@ page import="enums.TypeType" %>
<%@ page import="enums.Result" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>

<html>
<head>
    <title>每日打卡 - 健康码管理系统</title>
    <link href="css/main.css" rel="stylesheet" type="text/css" >
    <link href="css/form.css" rel="stylesheet" type="text/css" >
    <%@include file="scripts.jspf"%>

</head>
<body>
<div class="header-con">
    <div class="header-van">
        <div class="welcome fl">每日打卡 - 健康码管理系统</div>
        <div class="fr">
        </div>
    </div>
</div>



<div class="container-van">
    <div class="row-van">
        <div class="column-van">

            <form action="${pageContext.request.contextPath}/dailycard" method="post">
                <br><label for="input-isArrivedInfectedArea" class="form-label">本人近期（14天内）是否去过湖北省或重点疫区？</label>
                <br><input id="input-isArrivedInfectedArea" name="isArrivedInfectedArea" type="radio" value="y" />
                是
                <input name="isArrivedInfectedArea" type="radio" value="n" checked="checked">
                否
                <br><div class="line"></div><br>


                <br><label for="input-isBeenAbroad" class="form-label">本人近期（14天内）是否去过国外？</label>
                <br><input id="input-isBeenAbroad" name="isBeenAbroad" type="radio" value="y" />
                是
                <input name="isBeenAbroad" type="radio" value="n" checked="checked">
                否
                <br><div class="line"></div><br>


                <br><label for="input-isContactedPatient" class="form-label">本人近期（14天内）是否接触过新冠确诊病人或疑似病人？</label>
                <br><input id="input-isContactedPatient" name="isContactedPatient" type="radio" value="y" />
                是
                <input name="isContactedPatient" type="radio" value="n" checked="checked">
                否
                <br><div class="line"></div><br>


                <br><label for="input-isDefiniteDiagnosis" class="form-label">本人是否被卫生部门确认为新冠肺炎确诊病例或疑似病例？</label>
                <br><input id="input-isDefiniteDiagnosis" name="isDefiniteDiagnosis" type="radio" value="y" />
                是
                <input name="isDefiniteDiagnosis" type="radio" value="n" checked="checked">
                否
                <br><div class="line"></div><br>


                <br><label for="input-health" class="form-label">当前健康的异常状况？(无勾选即为无异常)</label>
                <br><input id="input-health" name="illness" type="checkbox" value="1">
                发烧
                <input name="illness" type="checkbox" value="2">
                乏力
                <input name="illness" type="checkbox" value="3">
                干咳
                <input name="illness" type="checkbox" value="4">
                鼻塞
                <input name="illness" type="checkbox" value="5">
                流涕
                <input name="illness" type="checkbox" value="6">
                咽痛
                <input name="illness" type="checkbox" value="7">
                腹泻

                <br><div class="line"></div><br>
                <input type="submit" name="submit" value="提交"/>
            </form>
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
