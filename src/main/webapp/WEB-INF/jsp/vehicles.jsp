<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<body>

<section>
    <h3><spring:message code="vehicle.label"/></h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="vehicle.vin"/></th>
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
                <td>${vehicle.color}</td>
                <td>${vehicle.costUsd}</td>
                <td>${vehicle.mileage}</td>
                <td>${vehicle.productionYear}</td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>