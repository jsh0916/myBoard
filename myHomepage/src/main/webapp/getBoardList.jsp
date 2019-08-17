<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title><spring:message code="message.board.list.mainTitle"/></title>
		
		<!-- Bootstrap core CSS -->
		<link href="/resources/myhomepage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Bootstrap core JavaScript -->
		<script src="/resources/myhomepage/vendor/jquery/jquery.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	</head>
	<script>
		function insertBoard() {
			var userName = ${userName};
			
			if (userName == "") {
				alert("로그인해주시기 바랍니다.");
			} else {
				location.url = "insertBoard.do?userName=" + userName;				
			}
		}
	</script>
	<body>
		<center>
			<h1><spring:message code="message.board.list.mainTitle"/></h1>
			<h3>${userName}<spring:message code="message.board.list.welcomeMsg"/>&nbsp;&nbsp;&nbsp;<a href="logout.do">Log-out</a></h3>
			
			<div class="container">
				<!-- 검색 시작 -->
				<form action="getBoardList.do" method="post">
					<table border="1" cellpadding="0" cellspacing="0" width="700">
						<tr>
							<td aligh="right">
								<select name="searchCondition">
									<c:forEach items="${conditionMap }" var="option">
										<option value="${option.value }">${option.key }								
									</c:forEach>
								</select>
								<input name="searchKeyword" type="text"/>
								<input type="submit" value="<spring:message code="message.board.list.search.condition.btn"/>"/>
							</td>
						</tr>
					</table>
				</form>
				<!-- 검색 종료 -->
				
				<table class="table table-striped" style="width: 700px;">
					<thead>
						<tr>
							<th><spring:message code="message.board.list.table.head.seq"/></th>
							<th><spring:message code="message.board.list.table.head.title"/></th>
							<th><spring:message code="message.board.list.table.head.writer"/></th>
							<th><spring:message code="message.board.list.table.head.regDate"/></th>
							<th><spring:message code="message.board.list.table.head.cnt"/></th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${boardList }" var="board">
						
						<tr>
							<td>${board.seq }</td>
							<td alig="left">
								<a href="getBoard.do?seq=${board.seq }">
									${board.title }
								</a>
							</td>
							<td>${board.writer }</td>
							<td>${board.regDate }</td>
							<td>${board.cnt }</td>
						</tr>
						
						</c:forEach>
					</tbody>
					
					<br>
				</table>
				</hr>
				
				<div class="text-center">
					<ul class="pagination">
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
					</ul>
				</div>
				<a class="btn btn-default pull-right" onclick="insertBoard()" href="#"><spring:message code="message.board.list.link.insertBoard"/></a>
			</div>
			
			
			
			
			<!-- Image board Page Content -->
			<div class="container">
		
				<div class="row">
		
					<!-- Board Entries Column -->
					<div class="col-md-8">
		
						<c:forEach items="${boardList}" var="board">
							<h1 class="my-4">
								${board.title } 
									<!-- 
										 <small>Secondary Text</small>								
									 -->
							</h1>
			
							<!-- Blog Post -->
							<div class="card mb-4">
								<img class="card-img-top" src="http://placehold.it/750x300"
									alt="Card image cap">
								<div class="card-body">
									<h2 class="card-title">${board.title }</h2>
									<p class="card-text">
										${board.content }
									</p>
									<a href="getBoard.do?seq=${board.seq }" class="btn btn-primary">Read More &rarr;</a>
								</div>
								<div class="card-footer text-muted">
									${board.regDate }
								</div>
							</div>
			
							<!-- Blog Post
							<div class="card mb-4">
								<img class="card-img-top" src="http://placehold.it/750x300"
									alt="Card image cap">
								<div class="card-body">
									<h2 class="card-title">Post Title</h2>
									<p class="card-text">Lorem ipsum dolor sit amet, consectetur
										adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex
										quis soluta, a laboriosam. Dicta expedita corporis animi vero
										voluptate voluptatibus possimus, veniam magni quis!</p>
									<a href="#" class="btn btn-primary">Read More &rarr;</a>
								</div>
								<div class="card-footer text-muted">
									Posted on January 1, 2017 by <a href="#">Start Bootstrap</a>
								</div>
							</div>
							-->
						
						</c:forEach>
		
						<!-- Pagination -->
						<ul class="pagination justify-content-center mb-4">
							<li class="page-item"><a class="page-link" href="#">&larr;
									Older</a></li>
							<li class="page-item disabled"><a class="page-link" href="#">Newer
									&rarr;</a></li>
						</ul>
		
					</div>
		
					<!-- Sidebar Widgets Column -->
					<div class="col-md-4">
		
						<!-- Search Widget -->
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
		
						<!-- Categories Widget
							<div class="card my-4">
								<h5 class="card-header">Categories</h5>
								<div class="card-body">
									<div class="row">
										<div class="col-lg-6">
											<ul class="list-unstyled mb-0">
												<li><a href="#">Web Design</a></li>
												<li><a href="#">HTML</a></li>
												<li><a href="#">Freebies</a></li>
											</ul>
										</div>
										<div class="col-lg-6">
											<ul class="list-unstyled mb-0">
												<li><a href="#">JavaScript</a></li>
												<li><a href="#">CSS</a></li>
												<li><a href="#">Tutorials</a></li>
											</ul>
										</div>
									</div>
								</div>
							</div>
						-->
		
						<!-- Side Widget
							<div class="card my-4">
								<h5 class="card-header">Side Widget</h5>
								<div class="card-body">You can put anything you want inside
									of these side widgets. They are easy to use, and feature the new
									Bootstrap 4 card containers!</div>
							</div>
						-->
		
					</div>
		
				</div>
				<!-- /.row -->
		
			</div>
			<!-- /.container -->
			
			
			
			
			
		</center>	
	</body>
</html>