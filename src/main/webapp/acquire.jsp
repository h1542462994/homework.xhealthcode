<%--
  Created by IntelliJ IDEA.
  User: t1542
  Date: 2020/5/26
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" type="dao.UserDao" scope="request"/>

<html>
<head>
    <title>申领健康码 - 健康码管理系统</title>
</head>
<body>
    <br>
    <form action="${pageContext.request.contextPath}/acquire" method="post">
        <div id="basic-info" class="basic-info-card">
            <label for="input-name" class="form-label">姓名</label>
            <input id="input-name" name="name" class="form-input" type="text" placeholder="${user.name}" readonly><br>
            <label for="input-number" class="form-label">工号</label>
            <input id="input-number" name="number" class="form-input" type="text" placeholder="${user.number}" readonly><br>
            <label for="input-idCard" class="form-label">身份证号</label>
            <input id="input-idCard" name="idCard" class="form-input" type="text" placeholder="${user.idCard}" readonly><br>
            <label for="input-phone" class="form-label">手机号</label>
            <input id="input-phone" name="phone" class="form-input" type="text" placeholder="" ><br>
            <span class="form-error">${viewModel.errors['phone']}</span>
        </div><br>

        <div id="question-info" class="question-info-card">
            <label for="input-isArrivedInfectedArea" class="form-label">本人近期（14天内）是否去过湖北省或重点疫区？</label>
            <input id="input-isArrivedInfectedArea" name="isArrivedInfectedArea" type="radio" value="y" />
            是
            <input name="isArrivedInfectedArea" type="radio" value="n" checked="checked">
            否
            <br>

            <label for="input-isBeenAbroad" class="form-label">本人近期（14天内）是否去过国外？</label>
            <input id="input-isBeenAbroad" name="isBeenAbroad" type="radio" value="y" />
            是
            <input name="isBeenAbroad" type="radio" value="n" checked="checked">
            否
            <br>

            <label for="input-isContactedPatient" class="form-label">本人近期（14天内）是否接触过新冠确诊病人或疑似病人？</label>
            <input id="input-isContactedPatient" name="isContactedPatient" type="radio" value="y" />
            是
            <input name="isContactedPatient" type="radio" value="n" checked="checked">
            否
            <br>

            <label for="input-isDefiniteDiagnosis" class="form-label">本人是否被卫生部门确认为新冠肺炎确诊病例或疑似病例？</label>
            <input id="input-isDefiniteDiagnosis" name="isDefiniteDiagnosis" type="radio" value="y" />
            是
            <input name="isDefiniteDiagnosis" type="radio" value="n" checked="checked">
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
        <input type="submit" name="submit" value="提交"/>
    </form>
</body>
</html>
