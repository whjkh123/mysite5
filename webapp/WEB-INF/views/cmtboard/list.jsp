<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My site</title>
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet"
	type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet"
	type="text/css">

</head>


<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<c:import url="/WEB-INF/views/include/aside.jsp"></c:import>

		<div id="content">

			<div id="content-head">
				<h3>댓글게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">댓글게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="list">
					<form action="${pageContext.request.contextPath }/cmtboard" method="get">
						<div class="form-group text-right">
							<input type="text" name="keyword">
							<button type="submit" id=btn_search>검색</button>

							<input type="hidden" name="action" value="search">
						</div>
					</form>
					<table>
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>group_no</th>
								<th>order_no</th>
								<th>depth</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${requestScope.CmtBoardList }" var="cbList">
								<tr>
									<td>${cbList.no }</td>
									<td class="text-left"><a
										href="${pageContext.request.contextPath }/cmtboard/read?no=${cbList.no }"> <c:forEach
												var="item" begin="1" end="${cbList.depth}" step="1">
												<!-- &nbsp; 공백표시를 해줌. -->
												&nbsp;&nbsp;&nbsp;
										</c:forEach> ${cbList.title }
									</a></td>
									<td>${cbList.name }</td>
									<td>${cbList.hit }</td>
									<td>${cbList.reg_date }</td>
									<td>${cbList.group_no }</td>
									<td>${cbList.order_no }</td>
									<td>${cbList.depth }</td>

									<c:if test="${authUser.no == cbList.user_no }">
										<td><a href="${pageContext.request.contextPath }/cmtboard/remove?no=${cbList.no }">[삭제]</a></td>
									</c:if>

									<td></td>
								</tr>
							</c:forEach>

						</tbody>
					</table>

					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>

						<div class="clear"></div>
					</div>
					<c:if test="${!empty authUser }">
						<a id="btn_write" href="${pageContext.request.contextPath }/cmtboard/wForm">글쓰기</a>
					</c:if>

				</div>
				<!-- //list -->
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