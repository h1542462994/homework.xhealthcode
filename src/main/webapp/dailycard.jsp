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

</head>
<body>
<b><h1 align="center"style="color:#0000ff;">每日打卡</h1></b>
<br></br>
    <form action="${pageContext.request.contextPath}/dailycard" method="post">

        <div id="question-info" class="question-info-card">
            <label for="input-isArrivedInfectedArea" class="form-label">本人近期（14天内）是否去过湖北省或重点疫区？</label>
            <input id="input-isArrivedInfectedArea" name="isArrivedInfectedArea" type="radio" value="y" checked="checked"/>
            是
            <input name="isArrivedInfectedArea" type="radio" value="n">
            否
            <br>

            <label for="input-isBeenAbroad" class="form-label">本人近期（14天内）是否去过国外？</label>
            <input id="input-isBeenAbroad" name="isBeenAbroad" type="radio" value="y" checked="checked"/>
            是
            <input name="isBeenAbroad" type="radio" value="n">
            否
            <br>

            <label for="input-isContactedPatient" class="form-label">本人近期（14天内）是否接触过新冠确诊病人或疑似病人？</label>
            <input id="input-isContactedPatient" name="isContactedPatient" type="radio" value="y" checked="checked"/>
            是
            <input name="isContactedPatient" type="radio" value="n">
            否
            <br>

            <label for="input-isDefiniteDiagnosis" class="form-label">本人是否被卫生部门确认为新冠肺炎确诊病例或疑似病例？</label>
            <input id="input-isDefiniteDiagnosis" name="isDefiniteDiagnosis" type="radio" value="y" checked="checked"/>
            是
            <input name="isDefiniteDiagnosis" type="radio" value="n">
            否
            <br>

            <label for="input-health" class="form-label">当前健康的异常状况？(无勾选即为无异常)</label>
            <input id="input-health" name="illness" type="checkbox" value="1">
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
            <br>
        </div>

        <br/>
        <input type="submit"name="submit"value="提交"/>
    </form>
</body>
</html>
