package com.myproject.homepage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myproject.homepage.board.BoardVO;
import com.myproject.homepage.board.impl.BoardDAO;

public class GetBoardListController implements Controller {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("글 목록 검색 처리");
		
		// 1. 사용자 입력 정보 추출
		// 2. DB 연동 처리
		BoardVO vo = new BoardVO();
		BoardDAO boardDAO = new BoardDAO();
		List<BoardVO> boardList = boardDAO.getBoardList(vo);
		
		// 3. 검색 결과를 세션에 저장하고 목록 화면으로 이동
		// --> 추후 세션이 아닌 HttpServletRequest 객체에 저장하여 공유
		// HttpServletRequest 객체는 client 가 서버에 요청을 전송할 때마다 매번 새롭게 생성되며
		// 응답 메시지가 브라우저에 전송되면 바로 삭제되는 1회성 객체이므로
		// 공유할 데이터를 HttpServletRequest에 저장하면 서버에 부담이 없음.
		HttpSession session = request.getSession();
		session.setAttribute("boardList", boardList);
		
		return "getBoardList";
	}

}
