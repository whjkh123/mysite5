<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.js"></script>
<title>My site</title>
<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<c:import url="/WEB-INF/views/include/aside.jsp"></c:import>

		<div id="content">

			<div id="content-head">
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="guestbook">
				<%-- <form action="${pageContext.request.contextPath }/api/guestbook/write" method="get"> --%>
				<table id="guestAdd">
					<colgroup>
						<col style="width: 70px;">
						<col>
						<col style="width: 70px;">
						<col>
					</colgroup>
					<tbody>
						<tr>
							<td><label class="form-text" for="input-uname">이름</label></td>
							<td><input id="input-uname" type="text" name="name"></td>
							<td><label class="form-text" for="input-pass">패스워드</label></td>
							<td><input id="input-pass" type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
						</tr>
						<tr class="button-area">
							<td colspan="4"><button id="btnSubmit" type="submit">등록</button></td>
						</tr>
					</tbody>

				</table>

				<!-- </form> -->

				<div id="guestbook-list">
					<!-- 방문록 리스트 -->
				</div>

			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>

	</div>
	<!-- //wrap -->

	<!-- 모달창 영역 -->
	<div class="modal fade" id="delmodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">방명록 삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호 </label> <input id="modalPw" type="password" name="password" value="">
					<!-- 히든처리 -->
					<input id="modalNo" type="hidden" name="no" value="">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button id="modalBtnDel" type="button" class="btn btn-primary">삭제</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

</body>

<script type="text/javascript">
	//DOM 생성 >> list 출력
	$("document").ready(function() {
		console.log("ready")

		// 리스트 출력
		fetchList()
	})

	// 등록버튼 클릭 시
	$("#btnSubmit").on("click", function() {
		console.log("등록버튼 클릭")

		// 등록데이터 수집
		var guestbookVo = {
			name : $("[name='name']").val(),
			password : $("[name='password']").val(),
			content : $("[name='content']").val()
		}

		console.log(guestbookVo)

		// ajax >> 데이터만 수신 → 방문록 등록
		$.ajax({
			// url : "${pageContext.request.contextPath }/api/guestbook/write",

			url : "${pageContext.request.contextPath }/api/guestbook/write2",
			type : "post",

			/*
			// contentType : "application/json",
			data : guestbookVo,
			*/
			
			contentType : "application/json",
			data : JSON.stringify(guestbookVo),

			dataType : "json",
			success : function(guestbookVo) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookVo)
				render(guestbookVo, "up")

				// 등록폼 리셋
				$("[name='name']").val("")
				$("[name='password']").val("")
				$("[name='content']").val("")
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	})

	// #1 삭제버튼(a tag) 클릭 시 >> 비밀번호 입력창 호출
	$("#guestbook-list").on("click", "a", function() {
		event.preventDefault()
		console.log("모달 창 호출")

		// 입력한 비밀번호 리셋
		$("#modalPw").val("")

		var no = $(this).data("no")
		console.log(no)

		$("#modalNo").val(no)

		// 모델 창 호출
		$("#delmodal").modal()
	})

	// #2 삭제버튼(modal 클릭 시)
	$("#modalBtnDel").on("click", function() {
		console.log("모달삭제버튼 클릭")

		// 모달 창 비밀번호, no 수집
		var guestbookVo = {
			password : $("#modalPw").val(),
			no : $("#modalNo").val()
		}

		var no = $("#modalNo").val()

		console.log(guestbookVo)

		// 방문록 삭제
		$.ajax({
			url : "${pageContext.request.contextPath }/api/guestbook/remove",
			type : "post",
			// contentType : "application/json",
			data : guestbookVo,

			dataType : "json",
			success : function(result) {
				/*성공시 처리해야될 코드 작성*/
				console.log(result)

				// result(count) = 1 >> 화면에서 삭제
				if (result == 1) {
					// #1 모달 창 닫기
					$("#delmodal").modal("hide")

					// #2 no에 해당하는 테이블 삭제
					$("#t-" + no).remove()
				}
				// result(count) = 0 >> 삭제 실패
				else {
					// 모달 창 닫기
					alert("비밀번호가 틀렸습니다.")

					// 입력한 비밀번호 리셋
					$("#modalPw").val("")
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	})

	// 방문록 Vo + html 출력
	function render(guestbookVo, updown) {
		var Str = ""

		Str += '<table id="t-'+guestbookVo.no+'" class="guestRead">'

		Str += '<colgroup>'
		Str += '<col style="width: 10%;">'
		Str += '<col style="width: 40%;">'
		Str += '<col style="width: 40%;">'
		Str += '<col style="width: 10%;">'
		Str += '</colgroup>'

		Str += '<tr>'
		Str += '<td>' + guestbookVo.no + '</td>'
		Str += '<td>' + guestbookVo.name + '</td>'
		Str += '<td>' + guestbookVo.reg_date + '</td>'
		Str += '<td><a href="" data-no="'+guestbookVo.no+'">[삭제]</a></td>'
		Str += '</tr>'

		Str += '<tr>'
		Str += '<td colspan=4 class="text-left">' + guestbookVo.content
				+ '</td>'
		Str += '</tr>'

		Str += '</table>'

		if (updown == "down") {
			$("#guestbook-list").append(Str)
		} else if (updown == "up") {
			$("#guestbook-list").prepend(Str)
		} else {
			console.log("방향 미지정")
		}
	}

	// 리스트 출력
	function fetchList() {
		$.ajax({
			url : "${pageContext.request.contextPath }/api/guestbook/list",
			type : "post",
			// contentType : "application/json",
			/*data : {
				name : name,
				password : password,
				content : content
			},*/

			dataType : "json",
			success : function(guestbookList) {
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookList)

				for (var i = 0; i < guestbookList.length; i++) {
					render(guestbookList[i], "down")
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	}
</script>

</html>