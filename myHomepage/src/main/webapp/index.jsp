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
		
<!-- 		<script src="/resources/myhomepage/js/board.js"></script> -->
	</head>
	<script>
		$(document).ready(function() {
			$(".overlay, .login-modal").css("display", "none");
			
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
	
		<!-- Page Content -->
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