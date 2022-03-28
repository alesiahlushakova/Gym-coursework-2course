<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 08.07.2019
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<fmt:bundle basename="page_content">
    <fmt:message key="main.title" var="title"/>
    <fmt:message key="page.content.info" var="info"/>
    <fmt:message key="page.content.news" var="news"/>
    <fmt:message key="page.content.promotion" var="promotion"/>
</fmt:bundle>

<html>
<head>

    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="wrapper_content">
    <div class="wrapper_content-item  clearfix">

        <p>
            ${pageScope.info}
        </p>
    </div>
    <div class="wrapper_content-item clearfix">

        <p>${pageScope.news}
        </p>
    </div>
    <div class="wrapper_content-item  clearfix">

        <p>
            ${pageScope.promotion}
        </p>
    </div>
</div>
<footer class="footer">
    <p>
        <span><i class="fa fa-mobile" aria-hidden="true"></i> +375 (33)<strong>6340558</strong></span>

    </p>
</footer>
</body>
</html>
