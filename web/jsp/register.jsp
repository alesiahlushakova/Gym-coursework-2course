<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 08.07.2019
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="register.title" var="title"/>
    <fmt:message key="register.login" var="login"/>
    <fmt:message key="register.password" var="password"/>
    <fmt:message key="register.repeat_password" var="repeat_password"/>
    <fmt:message key="register.first_name" var="first_name"/>
    <fmt:message key="register.last_name" var="last_name"/>
    <fmt:message key="register.telephone" var="telephone"/>
    <fmt:message key="register.register_submit" var="register_submit"/>
    <fmt:message key="title.login" var="title_login"/>
    <fmt:message key="title.password" var="title_password"/>
    <fmt:message key="title.confirm_password" var="title_confirm_password"/>
    <fmt:message key="title.first_name" var="title_first_name"/>
    <fmt:message key="title.last_name" var="title_last_name"/>
    <fmt:message key="title.telephone" var="title_telephone"/>
</fmt:bundle>

<html>
<head>

    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<span class="error">${requestScope.message}</span>
<div class="reg_form">
    <form id="reg" name="RegisterForm" method="POST" action="${pageContext.request.contextPath}/ControllerServlet">
        <input type="hidden" name="command" value="common_register"/>
        <p><span>${pageScope.login}</span>
            <input id="login" title="${pageScope.title_login}" type="text" name="login" value="" onkeyup="checkLogin();"/>
        </p>
        <p><span>${pageScope.password}</span>
            <input id="password" title="${pageScope.title_password}" type="password" name="password" value=""
                   onkeyup="checkPassword();"/>
        </p>
        <p><span>${pageScope.repeat_password}</span>
            <input id="confirm_password" title="${pageScope.title_confirm_password}" type="password" value=""
                   onkeyup="checkPassword();"/>
        </p>
        <p><span>${pageScope.first_name}</span>
            <input id="first_name" title="${pageScope.title_first_name}" type="text" name="first_name" value=""
                   onkeyup="checkName();"/>
        </p>
        <p><span>${pageScope.last_name}</span>
            <input id="last_name" title="${pageScope.title_last_name}" type="text" name="last_name" value=""
                   onkeyup="checkName();"/>
        </p>
        <p><span>${pageScope.telephone}</span>
            <input id="telephone" title="${pageScope.title_telephone}" type="text" name="telephone" value=""
                   />
        </p>
        <input class="reg_submit" id="submit" type="submit" value="${pageScope.register_submit}" disabled/>
    </form>
</div>
<script>
    <jsp:directive.include file="/js/registerValidation.js"/>
</script>
</body>
</html>
