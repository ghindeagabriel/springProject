<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Welcome</title>
    <link href="<c:url value="../css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="../css/train_list.css" />" rel="stylesheet">
</head>
<body style="background-image: url('<c:url value="../img/cinema_welcome.jpg" />'); background-repeat: no-repeat; background-size: cover; margin: 0;">
<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <div style="position: absolute; right: 15px;">
            <h2>${pageContext.request.userPrincipal.name} | <a href="${pageContext.request.contextPath}/welcome">Program</a> | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
        </div>

        <span style="display: none;">${movie_id}</span>
        <div class="container" style="margin-top: 150px; max-width: 800px;">
            <center><form:form method="GET" action="/review_creation/${movie_id}"><input name="make_review" class="btn btn-lg btn-primary btn-block" style="width:auto; height:auto; padding-top:3px; padding-bottom:3px; padding-right:10px; padding-left:10px; right:5px; bottom: 5px;" type="submit" value="Add review"></form:form></center>
            <ul>
                <c:forEach var="r" items="${listReviews}">
                    <li style="position:relative; font-weight: bold;">
                        <div>
                            <div style="color: orange"><c:out value="${r.user.username}"/></div>
                            <div style="color: white"><c:out value="${r.review}"/></div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<c:url value="../js/bootstrap.min.js" />"></script>
</body>
</html>