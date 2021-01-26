<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My site</title>
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet"	type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<c:import url="/WEB-INF/views/include/aside.jsp"></c:import>

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="read">
					<form action="${pageContext.request.contextPath }/board" method="post">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${BoardVo.name }</span>
						</div>

						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${BoardVo.hit }</span>
						</div>

						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${BoardVo.reg_date }</span>
						</div>

						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">${BoardVo.title }</span>
						</div>

						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value">${BoardVo.content }</span>
						</div>

						<c:choose>
							<c:when test="${authUser.no == BoardVo.user_no }">
								<a id="btn_modify" href="${pageContext.request.contextPath }/board?action=list">목록</a>
								<a id="btn_modify" href="${pageContext.request.contextPath }/board?action=modifyForm&no=${BoardVo.no }">수정</a>
							</c:when>

							<c:otherwise>
								<a id="btn_modify" href="${pageContext.request.contextPath }/board?action=list">목록</a>
							</c:otherwise>
						</c:choose>

					</form>
					<!-- //form -->
				</div>
				<!-- //read -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>