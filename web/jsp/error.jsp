<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 08.07.2019
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="error.title" var="title"/>
    <fmt:message key="error.request" var="request_message"/>
    <fmt:message key="error.servlet" var="servlet_message"/>
    <fmt:message key="error.status" var="status_message"/>
    <fmt:message key="error.exception" var="exception_message"/>
</fmt:bundle>

<html>
<head>

    <title>${pageScope.title}</title>
</head>
<body class="page">

<div class="error_wrapper">
    <p>${pageScope.request_message} ${pageContext.errorData.requestURI} is failed</p>
    <p>${pageScope.servlet_message} ${pageContext.errorData.servletName}</p>
    <p>${pageScope.status_message} ${pageContext.errorData.statusCode}</p>
    <p>${pageScope.exception_message} ${pageContext.errorData.throwable}</p>
</div>
</body>
</html>