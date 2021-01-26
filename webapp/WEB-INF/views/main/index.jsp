<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My site</title>
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/main.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<!-- aside없음 -->

		<div id="full-content">

			<!-- content-head 없음 -->

			<div id="index">

				<img id="profile-img" src="${pageContext.request.contextPath }/assets/image/profile.jpg">

				<div id="greetings">
					<p class="text-xlarge">
						Hello Spring 헬로 스프링
						<a class="" href="${pageContext.request.contextPath }/gbc?action=addlist">[방명록에 글 남기기]</a>
					</p>
				</div>
				<!-- //greetings -->

				<div class="clear"></div>

			</div>
			<!-- //index -->

		</div>
		<!-- //full-content -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->
</body>

</html>