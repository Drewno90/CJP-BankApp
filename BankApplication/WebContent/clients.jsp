<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="bank.css">
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
<style>
    #menu td {
        padding:30px;
        border: 1px solid #FFA800;
        color:white;
        font-weight:bold;
        text-align:center;
        cursor:hand;
    }
</style>
<script>

function hideOthers()
{
	var name = $("#name").val();
	var city = $("#city").val();


	$('tr:not(:contains(' + name + '))').hide();
	$('tr:not(:contains(' + city + '))').hide();
	
}
</script>
</head>
<body>

<table>
	<tr>
        <td>Name:</td>
        <td><input type="text" name="name" id="name"/><div id="nameError" class="error"></div></td>
    </tr>
    <tr>
        <td>City:</td>
        <td><input type="text" name="city" id="city"/><div id="cityError" class="error"></div></td>
    </tr>
    <tr>
    	  <td><button id="hide" onclick="hideOthers()" value="Search">Search</button></td>
    </tr>
    </table>

<form action="/clients" method="post">
<table id="menu">
	<tr>
		<th>Name</th>
		<th>City</th>
		<th>Balance</th>
	</tr>	
	<c:forEach var="client" items="${clients}">
		<tr>
  			<td><a href="/client?id=${client.id}"><c:out value="${client.name}"/></a></td>
  			<td><c:out value="${client.city}"/></td>
  			<td><c:out value="${clientBalance[client.id]}"/></td>
  		</tr>
	</c:forEach>
</table>
</form>
<a type="button" href="menu.html">Return</a>
</body>
</html>