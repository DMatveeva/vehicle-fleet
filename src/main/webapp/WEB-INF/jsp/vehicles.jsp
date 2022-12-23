<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>

<section>
    <h3><spring:message code="vehicle.label"/></h3>

    <form:form method="get" action="vehicles/all">
    </form:form>
    <hr>
    <a href="create"><spring:message code="vehicle.add"/></a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="vehicle.vin"/></th>
            <th><spring:message code="vehicle.model"/></th>
            <th><spring:message code="vehicle.color"/></th>
            <th><spring:message code="vehicle.costUsd"/></th>
            <th><spring:message code="vehicle.mileage"/></th>>
            <th><spring:message code="vehicle.productionYear"/></th>>
        </tr>
        </thead>
        <c:forEach items="${requestScope.vehicles}" var="vehicle">
            <jsp:useBean id="vehicle" type="ru.dmatveeva.model.Vehicle"/>
            <tr>
                <td><c:out value="${vehicle.vin}"/></td>
                <td>${vehicle.vehicleModel.brand} ${vehicle.vehicleModel.name}</td>
                <td>${vehicle.color}</td>
                <td>${vehicle.costUsd}</td>
                <td>${vehicle.mileage}</td>
                <td>${vehicle.productionYear}</td>
                <td><a href="update?id=${vehicle.id}"><spring:message code="common.update"/></a></td>
                <td><a href="delete?id=${vehicle.id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>