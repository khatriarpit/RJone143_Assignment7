<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
    <%@ include file="/style.css" %>
</style>
<link rel="stylesheet" href="${contextPath}/resources/css/style.css" type="text/css">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<title>Registration Form</title>
</head>
<body>
<header>
<c:import url="/Header.html"/>
</header>

<form  name ="myForm" action = "registration"  method="post">
<fieldset>
<legend> Contact Information</legend>
<input type="hidden" name="action" value="cart"/>
<label>Name:</label>
<input type="text" name="name" value="${userRegistrationInfo.name}" autofocus required> <br>
<label>E-mail</label>
<input type="email" name="email" title="JhuId@jh.edu" value="${userRegistrationInfo.email}" required>
</fieldset>
<fieldset >
<legend>Select Your Course(s)</legend>
<select   id="courses" name= "courses" multiple size="3" title="Use Ctr or Command to select Multiples" multiple required>
<option id="a" value= "A1 - J2EE Design Patterns">A1 - J2EE Design Patterns</option>
<option id="a" value= "A2 - Enterprise Service Bus">A2 - Enterprise Service Bus</option>
<option id= "a" value="A3 - Service Oriented Architecture">A3 - Service Oriented Architecture</option>
<option id="a" value="A4 - Web Service">A4 - Web Service</option>
<option id= "a" value= "A5 - Web Services Security">A5 - Web Services Security</option>
<option id="a" value="A6 - Secure Messaging">A6 - Secure Messaging</option>
</select>
</fieldset>
<fieldset id="status" title="Current Status">
<legend>Employment Status</legend>
<input type="radio" name="status" value="JHU_Employee" required>JHU Employee
<input type="radio" name="status" value="JHU_Student" required>JHU Student
<input type="radio" name="status" value="Speaker" required>Speaker
<input type="radio" name="status" value="Other" required>Other
</fieldset>
<fieldset id="fees" title="Prices are subject to change">
<legend>Additional Fees and Charges</legend>
<ul>
<li><input type="checkbox" name="hotel" value="true" <c:if test="${userRegistrationInfo.hotel}">checked=checked</c:if> >Hotel Accommodation (includes parking)</li>
<li><input type="checkbox" name="permit" value="true" <c:if test="${userRegistrationInfo.permit}">checked=checked</c:if> >Parking PermitParking Permit</li>
</ul>
</fieldset>
<button type="submit" class="btn btn-primary">Compute Seminar Costs</button></form>

</body>
</html>