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
		
		<title>myHomepage</title>
		
		<!-- Bootstrap core CSS -->
		<link href="/resources/myhomepage/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- Custom styles for this template -->
		<link href="/resources/myhomepage/css/blog-home.css" rel="stylesheet">
		<link href="/resources/myhomepage/css/style.css" rel="stylesheet">
		
		<!-- Bootstrap core JavaScript -->
		<script src="/resources/myhomepage/vendor/jquery/jquery.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
		<script src="/resources/myhomepage/vendor/bootstrap/js/bootstrap.min.js"></script>
		
<!-- 		<script src="/resources/myhomepage/js/board.js"></script> -->
	</head>
	<script>
		$(document).ready(function() {	
			$("#loginButton").click(function() {
				$(".login-modal, .overlay").css("display", "block");
				
				$('#guest, .close-modal').click(function() {
				    $('.login-modal, .overlay').fadeOut();
				  });
				  
				  $('#login').click(function() {
				    $('.modal-content').slideToggle();
				    $('.username').focus();
				  });
				  
				  $('.register-link').click(function() {
				    $('.register-slide').addClass('active-register');
				    $('.login-form').addClass('move-form');
				  });
				  
				  $('.close-register').click(function() {
				    $('.register-slide').removeClass('active-register');
				    $('.login-form').removeClass('move-form');
				    $('.username').focus();
				  });
			});
		})
		
		function insertBoard() {
			var userName = "${userName }";
			
			if (userName == "") {
				alert("로그인해주시기 바랍니다.");
			} else {
				location.href = "insertBoard.do?userName=" + userName;
			}
		}
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
						<!--
							<li class="nav-item"><a class="nav-link" href="#">About</a></li>
							<li class="nav-item"><a class="nav-link" href="#">Services</a>
							</li>
							<li class="nav-item"><a class="nav-link" href="#">Contact</a>
							</li>
						-->
					</ul>
				</div>
			</div>
		</nav>
	
		<!-- Image board Page Content -->
		<div class="container">
			<table class="table table-bordered table-hover">
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
			</table>
			<hr/>
			
			<a class="btn btn-default pull-right" onclick="insertBoard()" href="#">
				<spring:message code="message.board.list.link.insertBoard"/>
			</a>
			
			<div class="text-center">
				<ul class="pagination">
					<li class="page-item"><a href="#">1</a></li>
					<li class="page-item"><a href="#">2</a></li>
					<li class="page-item"><a href="#">3</a></li>
				</ul>
			</div>
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
	
	<!-- Layer Popup START -->
	
	<div class="login-modal">
		<div class="modal-inner">
			<div class="modal-top"><i class="material-icons">exit_to_app</i><br/>
				<h4>How would you like to checkout?</h4>
			</div>
			<div class="modal-content">
				<form class="login-form" action="login.do" method="post">
					<fieldset class="form-group">
						<input class="form-control username" type="text" name="id" placeholder="Username" required="required"/>
					</fieldset>
					<fieldset class="form-group">
						<input class="form-control" type="password" name="password" placeholder="Password" required="required"/>
					</fieldset>
					<button class="btn btn-primary" type="submit">Submit</button><span><a class="register-link" href="#0">Don't have an account?</a></span>
				</form>
				<div class="register-slide"><a class="close-register" href="#0"><i class="lnr lnr-cross"></i></a>
					<p>Click the "Create Account" checkbox to create an account during checkout.</p><a class="close-modal btn btn-primary" href="#0">Begin Checkout</a>
				</div>
			</div>
			<div class="modal-bottom"><a class="modal-btn" href="#0" id="login">Login</a><a class="modal-btn" href="#0" id="guest">Guest</a></div>
		</div>
	</div>
	<div class="overlay"></div>

	<!-- Layer Popup END -->
</html>