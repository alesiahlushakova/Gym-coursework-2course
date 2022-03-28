<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 11.07.2019
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<fmt:bundle basename="page_content">
    <fmt:message key="exercise.create_title" var="title"/>
    <fmt:message key="exercise.select_level" var="level"/>
    <fmt:message key="exercise.beginner" var="beginner"/>
    <fmt:message key="exercise.expert" var="expert"/>
    <fmt:message key="exercise.pro" var="pro"/>
    <fmt:message key="exercise.description" var="description"/>
    <fmt:message key="exercise.create_exercise" var="create"/>
    <fmt:message key="exercise.name" var="name"/>
    <fmt:message key="title.exercise_name" var="title_name"/>
    <fmt:message key="title.exercise_level" var="title_level"/>
    <fmt:message key="title.exercise_description" var="title_description"/>
</fmt:bundle>

<html>
<head>

    <title>${pageScope.title}</title>
</head>
<body class="page">
<tag:userMenu/>
<p class="error">${requestScope.message}</p>
<div class="create_exercise">
    <form id="createExercise" name="createExercise" method="POST"
          action="${pageContext.request.contextPath}/ControllerServlet">
        <input type="hidden" name="command" value="trainer_create_exercise"/>
        <p><label>${pageScope.name} <input id="name_id" class="create_exercise_name" title="${pageScope.title_name}"
                                           type="text" name="name" value="" onkeyup="checkName()"/></label></p>
        <p><label>${pageScope.level} <select name="level" title="${pageScope.title_level}">
            <option value="BEGINNER">${pageScope.beginner}</option>
            <option value="EXPERT">${pageScope.expert}</option>
            <option value="PRO">${pageScope.pro}</option>
        </select></label></p>
        <p>${pageScope.description}</p>
        <p><textarea title="${pageScope.title_description}" id="description_id" name="description"
                     onkeyup="checkDescription()"></textarea></p>
        <button id="create_exercise_id" class="create_exercise_confirm" id="submit" type="submit"
                disabled>${pageScope.create} <i class="fa fa-plus" aria-hidden="true"></i></button>
    </form>
</div>
<script>
    <jsp:directive.include file="/js/exerciseValidation.js"/>
</script>
</body>
</html>

