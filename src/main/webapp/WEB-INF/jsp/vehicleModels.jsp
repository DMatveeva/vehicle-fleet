<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<body>

<section>
    <h3><spring:message code="models.label"/></h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="model.brand"/></th>
            <th><spring:message code="model.name"/></th>
            <th><spring:message code="model.vehicleType"/></th>
            <th><spring:message code="model.numSeats"/></th>
            <th><spring:message code="model.engineType"/></th>
            <th><spring:message code="model.loadCapacity"/></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.models}" var="model">
            <jsp:useBean id="model" type="ru.dmatveeva.model.vehicle.VehicleModel"/>
            <tr>
                <td>${model.brand}</td>
                <td>${model.name}</td>
                <td>${model.vehicleType}</td>
                <td>${model.numSeats}</td>
                <td>${model.engineType}</td>
                <td>${model.loadCapacity}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>