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
<body style="background-image: url('<c:url value="../img/cinema_welcome.jpg" />'); height: 1000px; background-position: center; background-repeat: no-repeat; background-size: cover; margin: 0;">
<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <div style="position: absolute; right: 15px;">
            <h2>${pageContext.request.userPrincipal.name} | <a href="/welcome">Program</a> | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
        </div>

        <div style="margin-top: 150px;" class="container">
            <ul style="max-width: 850px;">
                <c:forEach var="booking" items="${listBooking}">
                    <li style="position:relative; color: white"><b><c:out value="${booking.broadcast.movie.name}"/></b><b> | <c:out value="${booking.broadcast.cinema.name}"/></b> | <b><c:out value="${booking.broadcast.time}"/></b> | Seat: <b><c:out value="${booking.seat}"/></b> <form:form method="POST" action="/delete_reservation/${booking.id}"> <input name="deleteReservation" class="btn btn-lg btn-primary btn-block" style="background: red; width:auto; height:auto; padding-top:3px; padding-bottom:3px; padding-right:10px; padding-left:10px; position:absolute; right:5px; bottom: 5px;" type="submit" value="x"></form:form></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<c:url value="../js/bootstrap.min.js" />"></script>
</body>
</html>