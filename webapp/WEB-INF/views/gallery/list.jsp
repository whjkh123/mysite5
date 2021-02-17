<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>

</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<!-- //header -->
		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/galleryAside.jsp"></c:import>
		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>갤러리</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>갤러리</li>
						<li class="last">갤러리</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="gallery">
				<div id="list">

					<c:if test="${!empty authUser }">
						<button id="btnImgUpload">이미지 올리기</button>
					</c:if>

					<div class="clear"></div>

					<ul id="viewArea">

						<!-- 이미지반복영역 -->
						<c:forEach items="${GalleryList }" var="gList">
							<li id="galleryList${gList.no }" data-no="${gList.no}" data-del="${gList.user_no == authUser.no}" data-user_no="${gList.user_no}">
								<div class="view">
									<img class="imgItem" src="${pageContext.request.contextPath}/upload/${gList.saveName}">
									<div class="imgWriter">
										작성자: <strong>${gList.name }</strong>
									</div>
								</div>
							</li>
						</c:forEach>
						<!-- 이미지반복영역 -->

					</ul>
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

	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="addModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지 등록</h4>
				</div>

				<form method="post" action="${pageContext.request.contextPath}/gallery/upload" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label class="form-text">글 작성</label> <input id="addModalContent" type="text" name="content" value="">
						</div>
						<div class="form-group">
							<label class="form-text">이미지 선택</label> <input id="file" type="file" name="file" value="">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn" id="btnUpload">등록</button>
					</div>
				</form>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">이미지 보기</h4>
				</div>
				<div class="modal-body">

					<div class="formgroup">
						<img id="viewModelImg" src="">
						<!-- ajax로 처리 : 이미지출력 위치-->
					</div>

					<div class="formgroup">
						<p id="viewModelContent"></p>
					</div>

					<input id="modalNo" type="text" name="galleryNo" value="">

				</div>

				<div class="modal-footer"></div>

			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

</body>

<script type="text/javascript">
	// 이미지 올리기
	$("#btnImgUpload").on("click", function() {
		console.log("이미지 올리기 클릭")

		// 모달창 출력
		$("#addModal").modal()
	})

	// 등록
	$("#btnUpload").on("click", function() {
		console.log("등록 클릭")

		$("#addModal").modal("hide")
	})

	// 이미지 조회
	$("#viewArea").on("click", "li", function() {
		console.log("이미지 클릭")
		var no = $(this).data("no")
		console.log(no)
		
		// 이미지 출력(ajax)		
		$.ajax({
			url : "${pageContext.request.contextPath}/gallery/read",
			type : "post",
			// contentType : "application/json",
			data : {
				no : no
				},
				dataType : "json",
				success : function(gVo) {
					console.log(gVo.name);

					// attr(attribute): 시작시 (html이 생성 될 때) 객체 값을 얻는 것, 여러 번 변경 될 수 val()있는 객체의 속성 값을 가져 오는 것 >> prop() 사용 권장(추가적인 설정이 필요..)
					$("#viewModelImg").attr("src", "${pageContext.request.contextPath}/upload/"	+ gVo.saveName);
					console.log(gVo.saveName);

					// 모달 창에 content 출력
					$("#viewModelContent").html(gVo.content);
					console.log(gVo.content);

					// 모달 창에 no 출력 >> hidden 처리
					$("#modalNo").attr("value", gVo.no);
					console.log(gVo.no);
					
					$("#viewModal").modal();
					},
					error : function(XHR, status, error) {
						console.error(status + " : " + error);
					}
				});
		
		var user_no = $(this).data("user_no");
		console.log(user_no);

		var authUser = "${authUser.no}";
		console.log(authUser);

		if (user_no == authUser) {
			$(".modal-footer").html("<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>"
									+ "<button type='button' class='btn btn-danger' id='btnDel'>삭제</button>");
		} else {
			$(".modal-footer").html("<button type='button' class='btn btn-default' data-dismiss='modal'>닫기</button>");
		}
	})

	// 이미지 삭제
	$(".modal-footer").on("click", "#btnDel", function(){
		console.log("삭제 클릭")

		var no = $("#modalNo").val()
		console.log(no)
		
		// 이미지 삭제(ajax)
		$.ajax({
			url : "${pageContext.request.contextPath}/gallery/remove",		
			type : "post",
			// contentType : "application/json",
			data : {no : no},
			dataType : "json",
			success : function(count){
				if(count == 1) {
					// #1 모달창이 사라지고..
					$("#viewModal").hide();
					
					// #2 리스트에서 해당 이미지가 삭제된 상태로 출력..
					$("#galleryList"+no).remove();
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	})
</script>

</html>