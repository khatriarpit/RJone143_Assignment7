<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
    <%@ include file="/style.css" %>
</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<title>Registration Form</title>
</head>
<body>
<header>

<%@ include file="/Header.html" %>
</header>
<body>

	<br />
		<b>Hello , ${userRegistrationInfo.name}</b> </br>
		<p>You are registered as a <b>${userRegistrationInfo.status}</b>.</p>
		<p>Your email confirmation has been sent to <b>${userRegistrationInfo.email}</b>.</p>

		<legend>Course Info</legend>
		<table class="table">
	    <thead>
	      <tr>
	        <th>Course Name</th>
	        <th>Cost</th>
	      </tr>
	    </thead>
		    <tbody>
		      	<c:forEach items="${userRegistrationInfo.courses}" var="item" >
					<tr>
						<td><c:out value="${item}"></c:out></td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${userRegistrationInfo.courseCost}"/></td>
					</tr>
				</c:forEach>
				<c:if test="${userRegistrationInfo.hotel && !userRegistrationInfo.permit}">
					<tr>
						<td>Hotel Accommodation</td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${hotelFee}"/></td>
					</tr>
				</c:if>
				<c:if test="${userRegistrationInfo.hotel && userRegistrationInfo.permit}">
					<tr>
						<td>Hotel Accommodation</td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${hotelFee}"/></td>
					</tr>
					<tr>
						<td>Parking Permit (Included)</td>
						<td>$0.00</td>
					</tr>
				</c:if>
				<c:if test="${!userRegistrationInfo.hotel && userRegistrationInfo.permit}">
					<tr>
						<td>Parking Permit</td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${parkingFee}"/></td>
					</tr>
				</c:if>
				<tr class="table-active">
					<td><b>Total</b></td>
					<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${userRegistrationInfo.totalCost}"/></td>
				</tr>
			</tbody>
	    </table>

</body>
</html>