<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create reservation</title>
    <link href="<c:url value="http://localhost:8080/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="http://localhost:8080/css/common.css" />" rel="stylesheet">
</head>
<body style="background-image: url('<c:url value="http://localhost:8080/img/cinema_welcome.jpg" />'); height: 1000px; background-position: center; background-repeat: no-repeat; background-size: cover; margin: 0;">
<div class="container">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <div style="position: absolute; right: 15px;">
            <h2>${pageContext.request.userPrincipal.name} | <a href="/welcome">Program</a> | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
        </div>

        <c:if test="${nr_seats_available == 0}">
            <h2 style="color: white">${movie_title}</h2>
            <span style="color: white">Cast: </span><c:forEach var="actor" items="${listActors}">
                <span style="color:orange">- ${actor.name}</span>
            </c:forEach>
            <span style="color: red">There are no seats available.</span>
        </c:if>
        <div class="container" style="margin-top: 150px; max-width: 600px;">
            <c:if test="${nr_seats_available > 0}">
                <form:form action="/create_reservation/${broadcast_id }" method="POST" modelAttribute="reservationObj" cssClass="form-signin">
                    <span style="display: none;">${broadcast_id}</span>
                    <h2 style="color: white">${movie_title}</h2>
                    <span style="color: white">Cast: </span><c:forEach var="actor" items="${listActors}">
                        <span style="color:orange">- ${actor.name}</span>
                    </c:forEach>
                    <span style="color: white">Available seats: </span><span style="color: green;">${nr_seats_available}</span>
                    <table class="form-group">
                        <tr>
                            <td><label for="sel1"><span style="color: white">Please select a seat </span><span style="color: red">* </span> </label></td>
                            <td>
                                <select name="availableSeatsList" class="form-control" id="sel1">
                                    <c:forEach var="seat" items="${listAvailableSeats}">
                                        <option>${seat.seat_number}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                    <span style="color: white">Ticket price: ${ticket_price} lei</span>
                    <button name="createReservation" class="btn btn-lg btn-primary btn-block" type="submit">Reserve</button>
                </form:form>
            </c:if>
        </div>
    </c:if>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<c:url value="http://localhost:8080/js/bootstrap.min.js" />"></script>
</body>
</html>