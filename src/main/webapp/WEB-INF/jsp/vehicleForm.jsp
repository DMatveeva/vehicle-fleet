<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<body>

<section>
  <jsp:useBean id="vehicle" type="ru.dmatveeva.model.Vehicle" scope="request"/>
  <h3><spring:message code="${vehicle.isNew() ? 'vehicle.add' : 'vehicle.edit'}"/></h3>
  <hr>
  <form method="post" action="update_or_create">
    <input type="hidden" name="id" value="${vehicle.id}">
    <dl>
      <dt><spring:message code="vehicle.vin"/>:</dt>
      <dd><input type="text" value="${vehicle.vin}" size=40 name="vin" required></dd>
    </dl>
    <dl>
      <dt><spring:message code="vehicle.model"/>:</dt>
      <dd>
        <label>
          <select name="vehicle.model">
              <c:forEach items="${models}" var="model">
              <option value="${model.id}">${model.name}</option>
            </c:forEach>
          </select>
        </label>
    </dl>
    <dl>
      <dt><spring:message code="vehicle.color"/>:</dt>
      <dd><input type="text" value="${vehicle.color}" size=40 name="color" required></dd>
    </dl>
    <dl>
      <dt><spring:message code="vehicle.costUsd"/>:</dt>
      <dd><input type="number" value="${vehicle.costUsd}" size=40 name="costUsd" required></dd>
    </dl>
    <dl>
      <dt><spring:message code="vehicle.mileage"/>:</dt>
      <dd><input type="number" value="${vehicle.mileage}" size=40 name="mileage" required></dd>
    </dl>
    <dl>
      <dt><spring:message code="vehicle.productionYear"/>:</dt>
      <dd><input type="number" value="${vehicle.productionYear}"  name="productionYear" required></dd>
    </dl>
    <button type="submit"><spring:message code="common.save"/></button>
    <button onclick="window.history.back()" type="button"><spring:message code="common.cancel"/></button>
  </form>
</section>
</body>
</html>
