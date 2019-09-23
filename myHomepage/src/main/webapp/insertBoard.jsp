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
		
		<title>글 등록</title>
		
		<!-- Bootstrap core CSS -->
		<link href="/resources/myhomepage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Bootstrap core JavaScript -->
		<script src="/resources/myhomepage/vendor/jquery/jquery.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<script>
		function boardList() {
			location.href = "index.do";
		}
	</script>
	<body>
		<!-- Navigation -->
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
			<div class="container">
				<a class="navbar-brand" href="#">Start Bootstrap</a>
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
			<h2>게시글 등록</h2>
			<form action="insertBoard.do" method="post" enctype="multipart/form-data">
				<table class="table">
					<tbody>
						<tr>
							<td>
								<label for="title">제목</label>
							</td>
							<td>
								<input type="text" class="form-control" name="title" placeholder="제목을 입력해 주세요">
							</td>
						</tr>
						<tr>
							<td>
								<label for="reg_id">작성자</label>
							</td>
							<td>
								<input type="text" class="form-control" name="writer" readonly="readonly" value="${board.writer }">
							</td>
						</tr>
						<tr>
							<td>
								<label for="content">내용</label>
							</td>
							<td>
								<textarea class="form-control" rows="5" name="content" placeholder="내용을 입력해 주세요" ></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<label for="tag">업로드</label>
							</td>
							<td>
								<input type="file" class="form-control" name="uploadFile" multiple>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="submit" class="btn btn-sm btn-primary" id="btnSave" value="글 등록"/>			
								<input type="button" onclick="boardList()" class="btn btn-sm btn-primary" id="btnList" value="목록"/>
							</td>
						</tr>
					</tbody>
				</table>
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