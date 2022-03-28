<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 08.07.2019
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:bundle basename="page_content">
    <fmt:message key="login.title" var="title"/>
    <fmt:message key="login.log_in" var="log_in"/>
    <fmt:message key="login.enter_login" var="enter_login"/>
    <fmt:message key="login.enter_password" var="enter_password"/>
    <fmt:message key="login.register_span" var="register_span"/>
    <fmt:message key="login.register_link" var="register_link"/>
</fmt:bundle>


<html>
<head>
    <meta charset="UTF-8">

    <title>${pageScope.title}</title>
</head>
<body class="page">
<div class="title">
    <a href="${pageContext.request.contextPath}/jsp/main.jsp"><i class="fa fa-home" aria-hidden="true"></i></a>
    <h1>${pageScope.log_in}</h1>
</div>
<p class="error">${requestScope.message}</p>
<div class="wrapper_form">
    <form name="loginForm" method="POST" action="${pageContext.request.contextPath}/ControllerServlet">
        <input type="hidden" name="command" value="common_login"/>
        <p><label>${pageScope.enter_login}<input class="log_input" type="text" name="login" value=""/></label></p>
        <p><label>${pageScope.enter_password}<input class="log_input" type="password" name="password" value=""/></label></p>
        <input class="log_button" type="submit" value="${pageScope.log_in}"/>
    </form>
</div>
<div class="reg_link">
    <span>${pageScope.register_span}</span>
    <a class="reg_button" href="${pageContext.request.contextPath}/jsp/register.jsp">${pageScope.register_link}</a>
</div>
</body>
</html>