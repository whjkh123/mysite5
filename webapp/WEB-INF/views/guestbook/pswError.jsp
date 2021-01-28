<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>비밀번호 오류</p>

	<br>

	<a href="${pageContext.request.contextPath }/gbc/dForm?no=${param.no }">뒤로가기</a>
	
	<br>
	
	<a href="${pageContext.request.contextPath }/main">메인으로 돌악가기</a>

</body>
</html>