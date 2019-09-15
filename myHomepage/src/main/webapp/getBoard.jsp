<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>글 상세</title>
		
		<!-- Bootstrap core CSS -->
		<link href="/resources/myhomepage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Custom styles for this template -->
		<link href="/resources/myhomepage/css/blog-post.css" rel="stylesheet">
		
		<!-- Bootstrap core JavaScript -->
		<script src="/resources/myhomepage/vendor/jquery/jquery-3.4.1.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	</head>
	<script>
		$(document).ready(function(){
			var getBoardForm = $("#getBoardForm");
			
			$(".button").click(function(e) {
				e.preventDefault();
				
				getBoardForm.attr("action", $(this).attr("href"));
				getBoardForm.submit();
			});
			
			
			var status = false; // 수정과 대댓글을 동시에 적용 못하도록
			
			// 댓글 저장
			$("#reply_save").click(function() {
				if ($.trim($("#reply_writer").val()) == "") {
					alert("이름을 입력하세요.");
					$("#reply_writer").focus();
					
					return false;
				}
				
				if ($.trim($("#reply_password").val()) == "") {
					alert("패스워드를 입력하세요.");
					$("#reply_password").focus();
					
					return false;
				}
				
				if ($.trim($("#reply").val()) == "") {
					alert("내용을 입력하세요.");
					$("#reply").focus();
					
					return false;
				}
				
				var reply = $("#reply").val().replace("\n", "<br>");
				var reply_rno;
				
				// 값 세팅
				var objParams = {
					seq				: ${board.seq },
					parent_id		: "0",
					depth			: "0",
					reply_writer	: $("#reply_writer").val(),
					reply_password	: $("#reply_password").val(),
					reply			: reply
				};
				
				$.ajax({
					url				: "insertReply.do",
					dataType		: "json",
					contentType		: "application/x-www-form-urlencoded; charset=UTF-8",
					type			: "post",
					async			: false,
					data			: objParams,
					success			: function (retVal) {
						if (retVal.code != "OK") {
							alert(retVal.message);
							
							return false;
						} else {
							reply_rno = retVal.reply_rno;
						}
					},
					error			: function() {
						console.log("AJAX_ERROR");
					}
				});
				
				var reply_area = $("#reply_area");
			
				var reply =
					'<tr reply_type="main">'		+
					'	<td style="width: 820px">'	+
							reply 			+
					'	</td>' 						+
					'	<td style="width: 100px">'	+
							$("#reply_writer").val()+
					'	</td>'						+
					'	<td style="width: 100px">'	+
					'	<input type="password" id="reply_password_' + reply_rno +'" style="width: 100px;" maxlength="10" placeholder="패스워드"/>' +
					'    </td>'+
                    '    <td align="center">'+
                    '       <button name="reply_button" reply_rno = "' + reply_rno + '">댓글</button>' +
                    '       <button name="reply_modify" r_type="main" parent_id="0" reply_rno="' + reply_rno + '">수정</button>' +
                    '       <button name="reply_del" r_type="main" reply_rno="' + reply_rno + '">삭제</button>      ' +
                    '    </td>' +
                    '</tr>';

				if ($("#reply_area").contents().length == 0) {
					$("#reply_area").append(reply);
				} else {
					$("#reply_area tr:last").after(reply);
				}
				
				// 댓글 초기화
				$("#reply_writer").val("");
				$("#reply_password").val("");
				$("#reply").val("");
			});
			
			// 댓글 삭제
			$(document).on("click", "button[name='reply_del']", function() {
				var check 				=	false;
				var reply_rno			=	$(this).attr("reply_rno");
				var r_type				=	$(this).attr("r_type");
				var reply_password		=	"reply_password_" + reply_rno;
				
				if ($("#" + reply_password).val().trim() == "") {
					alert("패스워드를 입력하세요.");
					$("#" + reply_password).focus();
					
					return false;
				}
				
				// 패스워드와 아이디를 넘겨 삭제 한다.
				// 값 셋팅
				var objParams = {
					reply_password		:	$("#" + reply_password).val(),
					reply_rno			:	reply_rno,
					r_type				:	r_type
				};
				
				$.ajax({
					url					:	"deleteReply.do",
					dataType			:	"json",
					contentType			:	"application/x-www-form-urlencoded; charset=UTF-8",
					type				:	"post",
					async				:	false,
					data				:	objParams,
					success				:	function (retVal) {
						if (retVal.code != "OK") {
							alert(retVal.message);
							
							return false;
						} else {
							check = true;
						}
					},
					error				:	function () {
						console.log("AJAX_ERROR");
					}
				});
				
				if (check) {
					// depth가 0이면 하위 댓글 다 지움
					if (r_type == "main") {
						// 삭제하면서 하위 댓글도 삭제
						var prevTr = $(this).parent().parent().next(); // 댓글의 다음
						
						// 댓글의 다음이 sub이면 계속 넘어감
						while (prevTr.attr("reply_type") == "sub") {
							prevTr.remove();
							prevTr = $(this).parent().parent().next();
						}
						
						$(this).parent().parent().remove();
					} else {	// main 이 아니면 자기만 지움
						$(this).parent().parent().remove();
					}
				}
			});
			
			// 댓글 수정 입력 START
			$(document).on("click", "button[name='reply_modify']", function() {
				var check 				=	false;
				var reply_rno 			=	$(this).attr("reply_rno");
				var parent_id 			=	$(this).attr("parent_id");
				var r_type				=	$(this).attr("r_type");
				var reply_password		=	"reply_password_" + reply_rno;
				
				if ($.trim($("#" + reply_password).val()) == "") {
					alert("패스워드를 입력하세요.");
					$("#" + reply_password).focus();
					
					return false;
				}
				
				// 패스워드와 아이디를 넘겨 패스워드 확인
				// 값 셋팅
				var objParams = {
					reply_password		:	$("#" + reply_password).val(),
					reply_rno			:	reply_rno
				};
				
				$.ajax({
					url					:	"checkReply.do",
					dataType			:	"json",
					contentType			:	"application/x-www-form-urlencoded; charset=UTF-8",
					type				:	"post",
					async				:	false,
					data				:	objParams,
					success				:	function (retVal) {
						if (retVal.code != "OK") {
							alert(retVal.message);
							
							return false;
						} else {
							check = true;
						}
					},
					error				:	function() {
						console.log("AJAX_ERROR");
					}
				});
				
				if (status) {
					alert("수정과 대댓글은 동시에 불가합니다.");
					
					return false;
				}
				
				if (check) {
					status = true;
					// 자기 위에 댓글 수정창 입력하고 기존값을 채우고 자기 자신 삭제
					var txt_reply_content = $.trim($(this).parent().prev().prev().prev().html()); // 댓글내용 가져오기
					if (r_type == "sub") {
						txt_reply_content = txt_reply_content.replace("-> ", ""); // 대댓글의 뎁스표시(화살표) 없애기
					}
					
					var txt_reply_writer = $.trim($(this).parent().prev().prev().html()); // 댓글작성자 가져오기
					
					// 입력받는 창 등록
					var replyEditor = 
						'<tr id="reply_add" class="reply_modify">'		+
                        '   <td width="820px">'							+
                        '       <textarea name="reply_modify_content_' + reply_rno + '" id="reply_modify_content_' + reply_rno + '" rows="3" cols="50">' + txt_reply_content + '</textarea>' + // 기존 내용 넣기
                        '   </td>'										+
                        '   <td width="100px">'							+
                        '       <input type="text" name="reply_modify_writer_' + reply_rno + '" id="reply_modify_writer_' + reply_rno + '" style="width:100%;" maxlength="10" placeholder="작성자" value="' + txt_reply_writer + '"/>' + //기존 작성자 넣기
                        '   </td>'										+
                        '   <td width="100px">'							+
                        '       <input type="password" name="reply_modify_password_' + reply_rno + '" id="reply_modify_password_' + reply_rno + '" style="width:100%;" maxlength="10" placeholder="패스워드"/>' +
                        '   </td>'										+
                        '   <td align="center">'						+
                        '       <button name="reply_modify_save" r_type = "' + r_type + '" parent_id="' + parent_id + '" reply_rno="' + reply_rno + '">등록</button>' +
                        '       <button name="reply_modify_cancel" r_type = "' + r_type + '" r_content = "' + txt_reply_content + '" r_writer = "' + txt_reply_writer + '" parent_id="' + parent_id + '"  reply_rno="' + reply_rno + '">취소</button>' +
                        '   </td>'										+
                        '</tr>';
					
					var prevTr = $(this).parent().parent();
					// 자기 위에 붙이기
					prevTr.after(replyEditor);
					// 자기 자신 삭제
					$(this).parent().parent().remove();
				}
			});
			// 댓글 수정 입력 END
			
			// 댓글 수정 취소
			$(document).on("click", "button[name='reply_modify_cancel']", function() {
				// 원래 데이터를 가져온다.
				var r_type 		= $(this).attr("r_type");
				var r_content 	= $(this).attr("r_content");
				var r_writer 	= $(this).attr("r_writer");
				var reply_rno	= $(this).attr("reply_rno");
				var parent_id	= $(this).attr("parent_id");
				
				var reply;
				
				// 자기 위에 기존 댓글 적고
				if(r_type == "main"){
                    reply = 
                        '<tr reply_type="main">'			+
                        '   <td style="width: 820px;">'		+
                        		r_content					+
                        '   </td>'							+
                        '   <td style="width: 100px;">'		+
                        		r_writer					+
                        '   </td>'							+
                        '   <td style="width: 100px;">'		+
                        '       <input type="password" id="reply_password_' + reply_rno + '" style="width:100px;" maxlength="10" placeholder="패스워드"/>' +
                        '   </td>'							+
                        '   <td align="center">'			+
                        '       <button name="reply_button" reply_rno="' + reply_rno + '">댓글</button>' +
                        '       <button name="reply_modify" r_type="main" parent_id="0" reply_rno="' + reply_rno + '">수정</button>' +
                        '       <button name="reply_del" reply_rno="' + reply_rno + '">삭제</button>' +
                        '   </td>'							+
                        '</tr>';
                } else {
                    reply = 
                        '<tr reply_type="sub">'				+
                        '   <td style="width: 820px;"> → '	+
                        		r_content					+
                        '   </td>'							+
                        '   <td style="width: 100px;">'		+
                       			r_writer					+
                        '   </td>'							+
                        '   <td style="width: 100px;">'		+
                        '       <input type="password" id="reply_password_' + reply_rno + '" style="width:100px;" maxlength="10" placeholder="패스워드"/>' +
                        '   </td>'							+
                        '   <td align="center">'			+
                        '       <button name="reply_modify" r_type = "sub" parent_id="' + parent_id + '" reply_rno = "' + reply_rno + '">수정</button>' +
                        '       <button name="reply_del" reply_rno = "' + reply_rno + '">삭제</button>' +
                        '   </td>'							+
                        '</tr>';
                }
				
				var prevTr = $(this).parent().parent();
				// 자기 위에 붙이기
				prevTr.after(reply);
				// 자기 자신 삭제
				$(this).parent().parent().remove();
				status = false;
			});
			
			// 댓글 수정 저장
			$(document).on("click", "button[name='reply_modify_save']", function() {
				var reply_rno = $(this).attr("reply_rno");
				
				if ($.trim($("#reply_modify_writer_" + reply_rno).val()) == "") {
					alert("이름을 입력하세요");
					$("#reply_modify_writer_" + reply_rno).focus();
					
					return false;
				}
				
				if ($.trim($("#reply_modify_password_" + reply_rno).val()) == "") {
					alert("패스워드를 입력하세요.");
					$("#reply_modify_password_" + reply_rno).focus();
					
					return false;
				}
				
				if ($.trim($("#reply_modify_content_" + reply_rno).val()) == "") {
					alert("내용을 입력하세요.");
					$("#reply_modify_content").focus();
					
					return false;
				}
				
				var reply_content = $("#reply_modify_content_" + reply_rno).val().replace("\n", "<br>");
				var r_type = $(this).attr("r_type");
				var parent_id;
				var depth;
				
				if (r_type == "main") {
					parent_id = "0";
					depth = "0";
				} else {
					parent_id = $(this).attr("parent_id");
					depth = "1";
				}
				
				var objParams = {
					seq				:	${board.seq },
					reply_id		:	reply_rno,
					parent_id		:	parent_id,
					depth			:	depth,
					reply_writer	:	$("#reply_modify_writer_" + reply_rno).val(),
					reply_password  :	$("#reply_modify_password_" + reply_rno).val(),
					reply_content	:	reply_content
				};
				
				$.ajax({
					url				:	"updateReply.do",
					dataType		:	"json",
					contentType		:	"application/x-www-form-urlencoded; charset=UTF-8",
					type			:	"post",
					async			:	false,
					data			:	objParams,
					success			:	function(retVal) {
						if (retVal.code != "OK") {
							alert(retVal.message);
							
							return false;
						} else {
							reply_rno = retVal.reply_rno;
							parent_id = retVal.parent_id;
						}
					},
					error			:	function() {
						console.log("AJAX_ERROR");
					}
				});
				
				if (r_type=="main"){
                    reply = 
                        '<tr reply_type="main">'						+
                        '   <td style="width: 820px;">'					+
                        $("#reply_modify_content_" + reply_rno).val()	+
                        '   </td>'										+
                        '   <td style="width: 100px;">'					+
                        $("#reply_modify_writer_" + reply_rno).val()	+
                        '   </td>'										+
                        '   <td style="width: 100px;">'					+
                        '       <input type="password" id="reply_password_' + reply_rno + '" style="width:100px;" maxlength="10" placeholder="패스워드"/>' +
                        '   </td>'										+
                        '   <td align="center">'						+
                        '       <button name="reply_reply" reply_rno = "' + reply_rno + '">댓글</button>' +
                        '       <button name="reply_modify" r_type="main" parent_id="0" reply_rno = "' + reply_rno + '">수정</button>' +
                        '       <button name="reply_del" r_type="main" reply_rno="' + reply_rno + '">삭제</button>' +
                        '   </td>'										+
                        '</tr>';
                } else {
                    reply = 
                        '<tr reply_type="sub">'							+
                        '   <td style="width: 820px;"> → '				+
                        $("#reply_modify_content_" + reply_rno).val()	+
                        '   </td>'										+
                        '   <td style="width: 100px;">'					+
                        $("#reply_modify_writer_" + reply_rno).val()	+
                        '   </td>'										+
                        '   <td style="width: 100px;">'					+
                        '       <input type="password" id="reply_password_' + reply_rno + '" style="width:100px;" maxlength="10" placeholder="패스워드"/>' +
                        '   </td>'										+
                        '   <td align="center">'						+
                        '       <button name="reply_modify" r_type="sub" parent_id="' + parent_id + '" reply_rno="' + reply_rno + '">수정</button>' +
                        '       <button name="reply_del" r_type = "sub" reply_rno="' + reply_rno + '">삭제</button>' +
                        '   </td>'										+
                        '</tr>';
                }
				
				var prevTr = $(this).parent().parent();
				// 자기 위에 붙이기
				prevTr.after(reply);
				
				// 자기 자신 삭제
				$(this).parent().parent().remove();
				status = false;
			});
			
			// 대댓글 입력창
			$(document).on("click", "button[name='reply_reply']", function() {
				
			});
			
			// 대댓글 등록
			$(document).on("click", "button[name='reply_reply_save']", function() {
				
			});
			
			// 대댓글 입력창 취소
			$(document).on("click", "button[name='reply_reply_cancel']", function() {
				$("#reply_add").remove();
				
				status = false;
			});
		})
	</script>
	<body>
		<!-- Navigation -->
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
			<div class="container">
				<a class="navbar-brand" href="#">Start Bootstrap</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarResponsive" aria-controls="navbarResponsive"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarResponsive">
					<ul class="navbar-nav ml-auto">
						<li class="nav-item active"><a class="nav-link" href="#">Home
								<span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#">About</a></li>
					</ul>
				</div>
			</div>
		</nav>
	
		<!-- Page Content -->
		<div class="container">
	
			<div class="row">
	
				<!-- Post Content Column -->
				<div class="col-lg-8">
	
					<!-- Title -->
					<h1 class="mt-4">${board.title }</h1>
	
					<!-- Author -->
					<p class="lead">
						by <a href="#">${board.writer }</a>
					</p>
	
					<hr>
	
					<!-- Date/Time -->
					<p>Posted on ${board.regDate }</p>
	
					<hr>
	
					<!-- Preview Image -->
					<img class="img-fluid rounded" src="http://placehold.it/900x300"
						alt="">
	
					<hr>
	
					<!-- Post Content -->					
					<p>
						${board.content }
					</p>

					<hr>
	
					<!-- Comments Form -->
					<div class="card my-4">
						<h5 class="card-header">Leave a Comment:</h5>
						<div class="card-body">
							<form>
								<div class="form-group">
									<textarea class="form-control" rows="3"></textarea>
								</div>
								<button type="submit" class="btn btn-primary">Submit</button>
							</form>
						</div>
					</div>
					
					<table border='1' id="reply_area" style="width: 1200px;">
						<tr reply_type="all" style="display: none;"> <!-- 뒤에 댓글 붙이기 쉽게 선언 -->
							<td colspan="4"></td>
						</tr>
						<!-- 댓글이 들어갈 공간 -->
						<c:forEach var="replyList" items="${replyList}" varStatus="status">
							<!-- 댓글의 depth 표시 -->
							<tr reply_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>"><!-- 댓글의 depth 표시 -->
								<td style="width: 820px;">
									<c:if test="${replyList.depth == '1' }">-></c:if> ${replyList.reply}
								</td>
								<td style="width: 100px">
									${replyList.replyer }
								</td>
								<td style="width: 100px;">
									<input type="password" id="reply_password_${replyList.rno }" style="width: 100px" maxlength="10" placeholder="패스워드">
								</td>
								<td align="center">
									<c:if test="${replyList.depth != '1' }">
										<!-- 첫 댓글에만 댓글이 추가 대댓글 불가 -->
										<button name="reply_button" parent_id="${replyList.rno }" reply_rno = "${replyList.rno }">댓글</button>
									</c:if>
									<button name="reply_modify" parent_id="${replyList.parent_id }" r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>" reply_rno="${replyList.rno }">수정</button>
									<button name="reply_del" r_type="<c:if test="${replyList.depth == '0'}">main</c:if><c:if test="${replyList.depth == '1'}">sub</c:if>" reply_rno="${replyList.rno }">삭제</button>
								</td>
							</tr>
						</c:forEach>
					</table>
					<table border="1" style="width: 1200px; border-color: #46AA46">
						<tr>
							<td width="500px">
								이름: <input type="text" id="reply_writer" name="reply_writer" style="width:170px;" maxlength="10" placeholder="작성자"/>
								패스워드: <input type="password" id="reply_password" name="reply_password" style="width:170px;" maxlength="10" placeholder="패스워드"/>
							<button id="reply_save" name="reply_save">댓글 등록</button>
							</td>
						</tr>
						<tr>
							<td>
								<textarea id="reply" name="reply" rows="4" cols="50" placeholder="댓글을 입력하세요."></textarea>
							</td>
						</tr>
					</table>
	
					<!-- Single Comment 
					<div class="media mb-4">
						<img class="d-flex mr-3 rounded-circle"
							src="http://placehold.it/50x50" alt="">
						<div class="media-body">
							<h5 class="mt-0">Commenter Name</h5>
							Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
							scelerisque ante sollicitudin. Cras purus odio, vestibulum in
							vulputate at, tempus viverra turpis. Fusce condimentum nunc ac
							nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
						</div>
					</div>
	
					Comment with nested comments
					<div class="media mb-4">
						<img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
						<div class="media-body">
							<h5 class="mt-0">Commenter Name</h5>
							Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
							scelerisque ante sollicitudin. Cras purus odio, vestibulum in
							vulputate at, tempus viverra turpis. Fusce condimentum nunc ac
							nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
	
							<div class="media mt-4">
								<img class="d-flex mr-3 rounded-circle"
									src="http://placehold.it/50x50" alt="">
								<div class="media-body">
									<h5 class="mt-0">Commenter Name</h5>
									Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
									scelerisque ante sollicitudin. Cras purus odio, vestibulum in
									vulputate at, tempus viverra turpis. Fusce condimentum nunc ac
									nisi vulputate fringilla. Donec lacinia congue felis in
									faucibus.
								</div>
							</div>
	
							<div class="media mt-4">
								<img class="d-flex mr-3 rounded-circle"
									src="http://placehold.it/50x50" alt="">
								<div class="media-body">
									<h5 class="mt-0">Commenter Name</h5>
									Cras sit amet nibh libero, in gravida nulla. Nulla vel metus
									scelerisque ante sollicitudin. Cras purus odio, vestibulum in
									vulputate at, tempus viverra turpis. Fusce condimentum nunc ac
									nisi vulputate fringilla. Donec lacinia congue felis in
									faucibus.
								</div>
							</div>
	
						</div>
					</div> -->
	
					<div>
						<a class="button" href="updateBoard.do">글수정</a>&nbsp;&nbsp;&nbsp;
						<c:if test="${pageMaker.keyword ne ''}">
							<a id="delete" href="deleteBoard.do?seq=${board.seq }&pageNum=${pageMaker.pageNum }&amount=${pageMaker.amount }&type=${pageMaker.type}&keyword=${pageMaker.keyword}">글삭제</a>&nbsp;&nbsp;&nbsp; 
						</c:if>
						<c:if test="${pageMaker.keyword eq ''}">
							<a id="delete" href="deleteBoard.do?seq=${board.seq }&pageNum=${pageMaker.pageNum }&amount=${pageMaker.amount }">글삭제</a>&nbsp;&nbsp;&nbsp; 							
						</c:if>
						<a class="button" href="index.do">글목록</a>
					</div>
					
					<form id="getBoardForm" method="get">
						<input type="hidden" name="pageNum" value="${pageMaker.pageNum }">
						<input type="hidden" name="amount" value="${pageMaker.amount }">
					</form>
				</div>
				
	
				<!-- Sidebar Widgets Column
				<div class="col-md-4">
	
					Search Widget
					<div class="card my-4">
						<h5 class="card-header">Search</h5>
						<div class="card-body">
							<div class="input-group">
								<input type="text" class="form-control"
									placeholder="Search for..."> <span
									class="input-group-btn">
									<button class="btn btn-secondary" type="button">Go!</button>
								</span>
							</div>
						</div>
					</div>
				</div>
	 			-->
			</div>
			<!-- /.row -->
	
		</div>
		<!-- /.container -->
	
		<!-- Footer -->
		<footer class="py-5 bg-dark">
			<div class="container">
				<p class="m-0 text-center text-white">Copyright &copy; Your
					Website 2019</p>
			</div>
			<!-- /.container -->
		</footer>
	</body>
</html>