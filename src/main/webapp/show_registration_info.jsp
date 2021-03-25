<%// JSTL imports %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">
<head>
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
<meta charset="utf-8">
<style>
    <%@ include file="/style.css" %>
</style>
<!-- Popper JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"
	type="text/javascript"></script>


<title>The Johns Hopkins University</title>
</head>
<body>
<header>
<%@ include file="/Header.html" %>
</header>

</head>

<body>

	<div class="container">
	<br />

		<b>${userRegistrationInfo.name}</b>
		<p>You selected to be registered as a <b>${userRegistrationInfo.status}</b>.</p>
		<p>Your email confirmation will be sent to <b>${userRegistrationInfo.email}</b>.</p>
		<p><i>${message}</i></p>
		<legend>Course Info</legend>
		<table class="table">
	    <thead>
	      <tr>
	        <th>Course Name</th>
	        <th>Cost</th>
	        <th></th>
	      </tr>
	    </thead>
		    <tbody>
		      	<c:forEach items="${userRegistrationInfo.courses}" var="item" >
					<tr>
						<td><c:out value="${item}"></c:out></td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${userRegistrationInfo.courseCost}"/></td>
						<td>
						<form action="registration" method="POST">
							<input hidden type="text" name="action" value="remove" >
							<input hidden type="text" name="course" value="${item}" >
						    <input type="submit" class="btn btn-danger" value="Remove" />
						</form>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${userRegistrationInfo.hotel && !userRegistrationInfo.permit}">
					<tr>
						<td>Hotel Accommodation</td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${hotelFee}"/></td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${userRegistrationInfo.hotel && userRegistrationInfo.permit}">
					<tr>
						<td>Hotel Accommodation</td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${hotelFee}"/></td>
						<td></td>
					</tr>
					<tr>
						<td>Parking Permit (Included)</td>
						<td>$0.00</td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${!userRegistrationInfo.hotel && userRegistrationInfo.permit}">
					<tr>
						<td>Parking Permit</td>
						<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${parkingFee}"/></td>
						<td></td>
					</tr>
				</c:if>
				<tr></tr>
				<tr class="table-active">
					<td><b>Total</b></td>
					<td>$<fmt:formatNumber type="number" minFractionDigits="2" value="${userRegistrationInfo.totalCost}"/></td>
					<td></td>
				</tr>

			</tbody>
	    </table>
        						<form action="/" method="POST">
        					    	<input type="submit" class="btn btn-primary" value="Edit Registration" />
        						</form></br>
        						<form action="registration" method="POST">
        							<input hidden type="text" name="action" value="confirm" >
        						    <input type="submit" class="btn btn-success" value="Confirm Registration" />
        						</form>

	</div>
</body>
</html>