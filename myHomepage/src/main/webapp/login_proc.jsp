<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="com.myproject.homepage.user.impl.UserDAO" %>
<%@ page import="com.myproject.homepage.user.UserVO" %>

<%
	// 1. ����� �Է� ���� ����
	String id = request.getParameter("id");
	String password = request.getParameter("password");
	
	// 2. DB ���� ó��
	UserVO vo = new UserVO();
	vo.setId(id);
	vo.setPassword(password);
	
	UserDAO userDAO = new UserDAO();
	UserVO user = userDAO.getUser(vo);
	
	// 3. ȭ�� �׺���̼�
	if (user != null) {
		response.sendRedirect("getBoardList.jsp");
	} else {
		response.sendRedirect("login.jsp");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<title>�α���</title>
	</head>
	<body>
		<center>
			<h1>�α���</h1>
			<hr>
			
			<form action="login_proc.jsp" method="post">
				<table border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td bgcolor="orange">���̵�</td>
						<td><input type="text" name="id"/></td>
					</tr>
					<tr>
						<td bgcolor="orange">��й�ȣ</td>
						<td><input type="password" name="password"/></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="�α���"/>
						</td>
					</tr>
				</table>
			</form>
		</center>	
	</body>
</html>