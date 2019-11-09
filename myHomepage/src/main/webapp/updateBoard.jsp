<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		
		<title>글 수정</title>
		
		<!-- Bootstrap core CSS -->
		<link href="/resources/myhomepage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Bootstrap core JavaScript -->
		<script src="/resources/myhomepage/vendor/jquery/jquery.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<script>
		$(document).ready(function() {
			var pageNum = "${pageMaker.pageNum}";
			var amount = "${pageMaker.amount}";
			var type = "${pageMaker.type}";
			var keyword = "${pageMaker.keyword}";
			
			$("#update").click(function(e) {
				console.log("게시글 수정");
				
				e.preventDefault();
				
				var str = "";
				$(".uploadResult ul li").each(function(i, obj) {
					var jobj = $(obj);
					console.dir(jobj);
					
					str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
					str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
				});
				
				var url =  "updateBoard.do?pageNum=" + pageNum + "&amount=" + amount + "&type=" + type + "&keyword=" + keyword;

				$("#updateForm").attr("action", url);
				$("#updateForm").append(str).submit();
			});
			
			$("#list").click(function(e) {
				e.preventDefault();
				location.href = "index.do?pageNum=" + pageNum + "&amount=" + amount + "&type=" + type + "&keyword=" + keyword;
			});
			
			// 글 불러올 때 첨부파일이 있으면 첨부파일도 함께 불러오기
			// 첨부파일 관련 START
			var seq = "${board.seq}";
			$.getJSON("/getAttachList.do", {seq: seq}, function(arr) {
				console.log(arr);
				
				var str = "";
				$(arr).each(function(i, attach) {
					// image type					
					if (attach.fileType) {
						var fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
						
						str += "<li data-path='" + attach.uploadPath + "'";
						str += " data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'>";
						str += "<div>";
						str += "<span> " + attach.fileName + "</span>";
						str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' ";
						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/showThumbnail.do?fileName=" + fileCallPath + "'>";
						str += "</div>";
						str += "</li>";
					} else {
						var fileCallPath = encodeURIComponent(attach.uploadPath + "/" + attach.uuid + "_" + attach.fileName);
// 						var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
						
						str += "<li data-path='" + attach.uploadPath + "'";
						str += " data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'>";
						str += "<div>";
						str += "<span> " + attach.fileName + "</span>";
						str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' "
						str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/resources/img/attach.png'>";
						str += "</div>";
						str += "</li>";
					}
					
					$(".uploadResult ul").html(str);
				});
			});
			
			// 사용자가 수정화면에서 첨부파일을 삭제하면 화면에서만 삭제하고 최종적으로 게시물을 수정했을 때 이를 반영하도록 구현.
			$(".uploadResult").on("click", "button", function(e) {
				console.log("delete file");
				
				if (confirm("파일을 삭제하시겠습니까?")) {
					var targetLi = $(this).closest("li");
					targetLi.remove();
				}
			});
			
			var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
			var maxSize = 5242880; // 5MB
			
			function checkExtension(fileName, fileSize) {
				if (fileSize >= maxSize) {
					alert("첨부할 수 있는 파일용량을 초과하였습니다.");
					
					return false;
				}
				
				if (regex.test(fileName)) {
					alert("해당 종류의 파일은 업로드할 수 없습니다.");
					
					return false;
				}
				
				return true;
			}
			
			// <input type='file'>의 내용이 변경되는 것을 확인 후 처리
			$("input[type='file']").change(function(e){
				var formData = new FormData();
				var inputFile = $("input[name='uploadFile']");
				var files = inputFile[0].files;
				
				for (var i = 0; i < files.length; i++) {
					if (!checkExtension(files[i].name, files[i].size)) {
						return false;
					}
					
					formData.append("uploadFile", files[i]);
				}
				
				$.ajax({
					url			:	'uploadAjaxAction.do',
					processData	:	false,
					contentType	:	false,
					data		:	formData,
					type		:	'post',
					dataType	:	'json',
					success		:	function(result) {
						console.log(result);
						
						showUploadResult(result);
					},
					error		:	function(request, status, error) {
			             alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n");
			        }
				});
			});
			
			function showUploadResult(uploadResultArr) {
				if (!uploadResultArr || uploadResultArr.length == 0) {
					return;
				}
				
				var uploadUL = $(".uploadResult ul");
				
				var str = "";
				
				$(uploadResultArr).each(function(i, obj) {
					if (obj.fileType) {
						// 브라우저에서 GET 방식으로 첨부파일의 이름을 사용할 때에는 파일 이름에 포함된 공백, 한글 이름을 주의
						// 이를 위해 encodeURIComponent()를 이용해 URI 호출에 적합한 문자열로 인코딩 처리
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
						
						str += "<li data-path='" + obj.uploadPath + "'";
						str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType + "'>";
						str += "<div>";
						str += "<span> " + obj.fileName + "</span>";
						str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<img src='/showThumbnail.do?fileName=" + fileCallPath + "'>";
						str += "</div>";
						str += "</li>";
					} else {
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						var fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
						
						str += "<li data-path='" + obj.uploadPath + "'";
						str += " data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType + "'>";
						str += "<div>";
						str += "<span> " + obj.fileName + "</span>";
						str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
						str += "<a href='downloadFile.do?fileName=" + fileCallPath + "'><img src='/resources/img/attach.png'></a>";
						str += "</div>";
						str += "</li>";
					}
				});
				
				uploadUL.append(str);
			}
		})
	</script>
	<style>
		.uploadResult {
			width: 100%;
			background-color: gray;
		}
		
		.uploadResult ul {
			display: flex;
			flex-flow: row;
			justify-content: center;
			align-items: center;
		}
		
		.uploadResult ul li {
			list-style: none;
			padding: 10px;
			aling-content: center;
			text-align: center;
		}
		
		.uploadResult ul li img {
			width: 100px;
		}
		
		.uploadResult ul li span {
			color: white;
		}
		
		.bigPictureWrapper {
			position: absolute;
			display: none;
			justify-content: center;
			align-items: center;
			top: 0%;
			width: 100%;
			height: 100%;
			background-color: gray;
			z-index: 100;
			background: rgba(255, 255, 255, 0.5);
		}
		
		.bigPicture {
			position: relative;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		
		.bigPicture img {
			width: 600px;
		}
	</style>
	<body>
		<!-- Navigation -->
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
			<div class="container">
				<a class="navbar-brand" href="#">기억보단 기록</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarResponsive" aria-controls="navbarResponsive"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<c:if test="${userName != null }">
					<div style="color: white;">
						${userName } <spring:message code="message.board.list.welcomeMsg"/>
					</div>
				</c:if>
				<div class="collapse navbar-collapse" id="navbarResponsive">
					<ul class="navbar-nav ml-auto">
						<li class="nav-item active">
							<a class="nav-link" href="#">Home
								<span class="sr-only">(current)</span>
							</a>
						</li>
						<li class="nav-item">
							<c:if test="${userName == null }">
								<a id="loginButton" class="nav-link" href="#">
									<spring:message code="message.user.login.title"/>
								</a>
							</c:if>
							<c:if test="${userName != null }">
								<a class="nav-link" href="logout.do">
									Log-out
								</a>
							</c:if>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		
		<div class="container">
			<h2>게시글 수정</h2>
			<form id="updateForm" method="post" enctype="multipart/form-data">
				<table class="table">
					<tbody>
						<tr>
							<td>
								<label for="title">제목</label>
							</td>
							<td>
								<input type="text" class="form-control" name="title" placeholder="제목을 입력해 주세요" value="${board.title }">
							</td>
						</tr>
						<tr>
							<td>
								<label for="reg_id">작성자</label>
							</td>
							<td>
								<input type="text" class="form-control" name="writer" readonly="readonly" placeholder="이름을 입력해 주세요" value="${board.writer }">
							</td>
						</tr>
						<tr>
							<td>
								<label for="content">내용</label>
							</td>
							<td>
								<textarea class="form-control" rows="5" name="content" placeholder="내용을 입력해 주세요" >${board.content }</textarea>
							</td>
						</tr>
						<tr>
							<td>
								<label for="tag">업로드</label>
							</td>
							<td>
								<div>
									<input type="file" class="form-control" name="uploadFile" multiple>								
								</div>
								<div class="uploadResult">
									<ul>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="button" id="update" class="btn btn-sm btn-primary" value="글 수정">	
								<input type="button" id="list" class="btn btn-sm btn-primary" value="목록">
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<form>
			
			</form>
		</div>
		
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