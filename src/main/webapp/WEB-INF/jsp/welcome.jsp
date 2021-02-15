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
            <h2>${pageContext.request.userPrincipal.name} | <a href="${pageContext.request.contextPath}/reserved_tickets">Bilete</a> | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
        </div>

        <div class="container" style="margin-top: 150px; max-width: 600px;">
                    <form:form action="searchCinema" method="POST" modelAttribute="searchCinemaObj">
                        <label for="sel1" style="color: white">Select Cinema (select one):</label>
                        <select name="selectList" class="form-control" id="sel1">
                            <c:forEach var="cinema" items="${listCinemas}">
                                <option ${cinema.name == selected_cinema ? "selected":""}>${cinema.name}</option>
                            </c:forEach>
                        </select>
                        <button name="search_movie" class="btn btn-lg btn-primary btn-block" type="submit">Search</button>
                    </form:form>
                </div>
                <div class="container" style="max-width: 800px;">
                    <ul>
                        <c:forEach var="b" items="${listBroadcasts}">
                            <li style="position:relative; font-weight: bold;">
                                <div>
                                    <div style="color: orange"><c:out value="${b.movie.name}"/></div>
                                    <div style="color: white">
                                        <c:if test="${b.time != null &&  b.time != ''}">Starting at: <c:out value="${b.time}"/></c:if>
                                        <c:if test="${b.movie.duration != null && b.movie.duration != ''}">, Duration(mins): <c:out value="${b.movie.duration}"/></c:if>
                                        <form:form method="GET" action="/reservation/${b.id}"><input name="make_reservation" class="btn btn-lg btn-primary btn-block" style="width:auto; height:auto; padding-top:3px; padding-bottom:3px; padding-right:10px; padding-left:10px; position:absolute; right:5px; bottom: 5px;" type="submit" value="+"></form:form>
                                        <form:form method="GET" action="/review/${b.movie.id}"><input name="go_to_review" class="btn btn-lg btn-primary btn-block" style="width:auto; height:auto; padding-top:3px; padding-bottom:3px; padding-right:10px; padding-left:10px; position:absolute; right:50px; bottom: 5px;" type="submit" value="Review"></form:form>
                                    </div>
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