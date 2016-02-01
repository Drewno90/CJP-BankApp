
<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="bank.css">
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>

<script>
function checkForm() {
	   var form = $("#main");
	   var checkResult = true;
	   var email = $("#email").val();
	      if (!email.match(".+@.+\.[a-zA-Z]{2,3}")) {
	         $("#emailError").html("invalid e-mail format");
	         checkResult=false;
	      }
	      
	      var name = $("#name").val();
	      var nameArr = name.split(" ");
	      for(var i=0;i<nameArr.length;i++) {
	    	  var n=nameArr[i];
	      if (!name.match("[a-zA-Z]{2,3}")) {
	         $("#nameError").html("invalid name format");
	         checkResult=false;
	      }
	      }
	      var city = $("#city").val();
	      if (!city.match("[a-zA-Z]{2,3}")) {
	         $("#cityError").html("invalid city format");
	         checkResult=false;
	      }
	      var ammount=$("#balance").val();
	        if (isNaN(ammount) || ammount.length==0) {
	            $("#balanceError").html("please, provide ammount");
	            checkResult=false;
	        }
	        if (checkResult) {
	            alert("All correct, sending...");
	            return true;
	        } else {
	            return false;
	        }
	}
</script>
</head>
<body>

<form id="form" action="saveClient" method="POST">
   <input type="hidden" name="id" value="${client.id}">
    Name: <input type="text" name="name" value="${client.name}"/><br>
    City: <input type="text" name="city" value="${client.city}"/><br>
    Gender:
        <input type="radio" name="gender" ${gender=="MALE"?"checked":""}/>Male
        <input type="radio" name="gender" ${gender=="FEMALE"?"checked":""}/>Female<br>
    Email: <input type="text" name="email" value="${email}"/><br>
    Balance: <input type="number" name="balance" value="${balance}"/><br>
    <input type="submit" onclick="return checkForm();" value="Add Client"/>
</form>
</body>
</html>