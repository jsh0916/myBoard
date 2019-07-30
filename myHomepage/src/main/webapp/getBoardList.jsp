<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.List" %>
<%@ page import="com.myproject.homepage.board.impl.BoardDAO" %>
<%@ page import="com.myproject.homepage.board.BoardVO" %>

<%
	// 1. ����� �Է� ���� ����
	// 2. DB ���� ó��
	BoardVO vo = new BoardVO();
	BoardDAO boardDAO = new BoardDAO();
	List<BoardVO> boardList = boardDAO.getBoardList(vo);

	// 3. ���� ȭ�� ����
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="EUC-KR">
		<title>�� ���</title>
	</head>
	<body>
		<center>
			<h1>�� ���</h1>
			<h3>test�� ȯ���մϴ�<a href="logout_proc.jsp"></a></h3>
			
			<!-- �˻� ���� -->
			<form action="getBoardList.jsp" method="post">
				<table border="1" cellpadding="0" cellspacing="0" width="700">
					<tr>
						<td aligh="right">
							<select name="searchCondition">
								<option value="TITLE">����
								<option value="CONTENT">����
							</select>
							<input name="searchKeyword" type="text"/>
							<input type="submit" value="�˻�"/>
						</td>
					</tr>
				</table>
			</form>
			<!-- �˻� ���� -->
			
			<table border="1" cellpadding="0" cellspacing="0" width="700">
				<tr>
					<th bgcolor="orange" width="100">��ȣ</th>
					<th bgcolor="orange" width="200">����</th>
					<th bgcolor="orange" width="150">�ۼ���</th>
					<th bgcolor="orange" width="150">�����</th>
					<th bgcolor="orange" width="100">��ȸ��</th>
				</tr>
				
				<% for(BoardVO board : boardList) { %>
				
				<tr>
					<td><%= board.getSeq() %></td>
					<td alig="left">
						<a href="getBoard.jsp?seq=<%= board.getSeq() %>">
							<%= board.getTitle() %>
						</a>
					</td>
					<td><%= board.getWriter()%></td>
					<td><%= board.getRegDate()%></td>
					<td><%= board.getCnt()%></td>
				</tr>
				
				<% } %>
				<br>
				<a href="insertBoard.jsp">���� ���</a>
			</table>
		</center>	
	</body>
</html>