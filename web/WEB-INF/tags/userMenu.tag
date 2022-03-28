<%@tag pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>



<fmt:bundle basename="page_content">
    <fmt:message key="menu.title" var="title"/>
    <fmt:message key="menu.hello" var="hello"/>
    <fmt:message key="menu.hello_guest" var="hello_guest"/>
    <fmt:message key="menu.login" var="login"/>
    <fmt:message key="menu.register" var="register"/>
    <fmt:message key="menu.logout" var="logout"/>
    <fmt:message key="menu.create_training_program" var="create_training_program"/>
    <fmt:message key="menu.create_exercise" var="create_exercise"/>
    <fmt:message key="menu.show_personal_clients" var="show_personal_clients"/>
    <fmt:message key="menu.show_clients" var="show_clients"/>
    <fmt:message key="menu.find_client" var="find_client"/>
    <fmt:message key="menu.orders_history" var="orders_history"/>
    <fmt:message key="menu.my_training_program" var="my_training_program"/>
    <fmt:message key="menu.make_order" var="make_order"/>
    <fmt:message key="menu.change" var="change"/>
    <fmt:message key="menu.language" var="language"/>
</fmt:bundle>

<header class="header">
    <h1 class="top">${pageScope.title}</h1>
    <div class="change_level">
        <ul>
            <li>${pageScope.language} </li>
            <li>
                <a href="${pageContext.request.contextPath}/ControllerServlet?command=common_change_language&locale=ru">RU</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/ControllerServlet?command=common_change_language&locale=by">BY</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/ControllerServlet?command=common_change_language&locale=en">EN</a>
            </li>
        </ul>
    </div>
    <div class="hello_message">
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <span class="hello_text">${pageScope.hello_guest}</span>
                <a class="logout_a"
                   href="${pageContext.request.contextPath}/jsp/login.jsp">${pageScope.login}</a>
                <a class="register_login_a"
                   href="${pageContext.request.contextPath}/jsp/register.jsp">${pageScope.register}</a>
            </c:when>
            <c:otherwise>
                <span class="hello_text">${pageScope.hello} ${sessionScope.user.firstName} ${sessionScope.user.lastName}</span>
                <a class="register_login_a"
                   href="${pageContext.request.contextPath}/ControllerServlet?command=common_logout">${pageScope.logout}</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>

