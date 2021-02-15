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
            <h2>${pageContext.request.userPrincipal.name} | <a href="${pageContext.request.contextPath}/review/${movie_id}">Review</a> | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
        </div>

        <span style="display: none;">${movie_id}</span>
        <div class="container" style="margin-top: 150px; max-width: 600px;">
            <form:form action="/create_review/${movie_id }" method="POST" modelAttribute="reviewObj" cssClass="form-signin">
                <form:input path="review" required="required" cssClass="form-control" />
                <br>
                <center><button name="createReview" class="btn btn-lg btn-primary btn-block" style="max-width: 150px" type="submit">Add review</button></center>
            </form:form>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<c:url value="../js/bootstrap.min.js" />"></script>
</body>
</html>