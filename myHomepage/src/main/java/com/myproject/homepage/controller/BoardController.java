package com.myproject.homepage.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.BoardVO;
import com.myproject.homepage.board.PageDTO;

@Controller
/*
 * Client가 상세화면을 요청하면 getBoard() 메소드는 검색 결과인 BoardVO 객체를 board 라는 이름으로 Model에 저장함
 * @SessionAttributes('board') 설정으로 Model 에 'board' 라는 이름으로 저장되는 데이터가 있다면
 * 그 데이터를 세션(HttpSession)에도 자동으로 저장하라는 설정.
 * --> updateBoard 가 호출될 때, 스프링 컨테이너는 @ModelAttribute 설정을 해석하여
 * 	   세션에 board라는 이름으로 저장된 데이터가 있는지 확인.
 * 	   있으면 해당 객체를 vo 변수에 할당.
 * */
@SessionAttributes("board")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	// 글 등록
	@RequestMapping(value="insertBoard.do", method=RequestMethod.GET)
	public String insertBoardView(HttpServletRequest request, Model model) {
		logger.info("=============== insertBoardView START ===============");
		
		String userName = request.getParameter("userName");
		
		logger.info("userName : " + userName);
		
		model.addAttribute("userName", userName);
		
		logger.info("=============== insertBoardView END ===============");
		return "insertBoard";
	}
	
	@RequestMapping(value="insertBoard.do", method=RequestMethod.POST)
	public String insertBoard(BoardVO vo, PageDTO pd, HttpServletRequest request, Model model) throws IOException {		// 커맨드객체 사용
		logger.info("=============== insertBoard.do START ===============");
		
		// 파일 업로드 처리
		MultipartFile uploadFile = vo.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/upload/tmp/" + fileName));
			
			logger.info("FileName : " + fileName);
			logger.info("FileSize : " + uploadFile.getSize());
		}
		
		boardService.insertBoard(vo);
		pd = setPage(pd, request);
		getBoardListData(vo, pd, model);

		logger.info("=============== insertBoard.do END ===============");
		return "redirect:index.do";
	}
	
	// 글 수정
	@RequestMapping(value="/updateBoard.do", method=RequestMethod.GET)
	public String updateBoardView(@ModelAttribute("board") BoardVO vo, Model model) {
		logger.info("=============== updateBoardView START ===============");

		model.addAttribute("board", vo);
		
		logger.info("=============== updateBoardView END ===============");
		return "updateBoard";
	}
	
	// 글 수정
	@RequestMapping(value="/updateBoard.do", method=RequestMethod.POST)
	public String updateBoard(@ModelAttribute("board") BoardVO vo, PageDTO pd, HttpServletRequest request, Model model) {
		logger.info("=============== updateBoard.do START ===============");
		
		boardService.updateBoard(vo);
		pd = setPage(pd, request);
		getBoardListData(vo, pd, model);

		logger.info("=============== updateBoard.do END ===============");
		return "redirect:index.do";
	}
	
	// 글 삭제
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardVO vo, PageDTO pd, HttpServletRequest request, Model model) {
		logger.info("=============== deleteBoard.do START ===============");
		
		boardService.deleteBoard(vo);
		pd = setPage(pd, request);
		getBoardListData(vo, pd, model);
		
		logger.info("=============== deleteBoard.do END ===============");
		return "redirect:index.do";
	}
	
	// 글 상세 조회
	@RequestMapping(value="/getBoard.do")
	public String getBoard(BoardVO vo, PageDTO pd, HttpServletRequest request, Model model) {
		logger.info("=============== getBoard.do START ===============");
		
		pd = setPage(pd, request);
		
		model.addAttribute("pageMaker", pd);
		model.addAttribute("board", boardService.getBoard(vo));

		logger.info("=============== getBoard.do END ===============");
		return "getBoard";
	}
	
	// 검색 조건 목록 설정
	/*
	 * @ModelAttribute
	 * 1. 매개변수로 선언된 Command 객체의 이름을 변경할 때 사용가능
	 * 2. @ModelAttribute가 설정된 메소드는 @RequestMapping 이 적용된 메소드보다 먼저 호출됨
	 * 	  그리고 @ModelAttribute 메소드 실행 결과로 리턴된 객체는 자동으로 Model에 저장됨. 따라서 View 에서 사용 가능.
	 * 
	 * */
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		
		return conditionMap;
	}
	// 글 목록 검색
	/*
	@RequestMapping(value="/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		
		//  DispatcherServlet은 Controller가 리턴한 mav 객체에서 Model 정보를 추출한 다음
		//  HttpServletRequest 객체에 검색 결과에 해당하는 Model 정보를 저장하여 JSP로 포워딩.
		 
		getBoardListData(vo, model);
		
		return "index";
	}
	*/
	
	public void getBoardListData(BoardVO vo, PageDTO pd, Model model) {
		/*
		if (vo.getSearchCondition() == null) {
			vo.setSearchCondition("TITLE");
		}
		
		if (vo.getSearchKeyword() == null || vo.getSearchKeyword().equals("")) {
			vo.setSearchKeyword("");
		}
		*/

		logger.info("pageNum : " + pd.getPageNum() + " | Amount : " + pd.getAmount());
		
		pd.setEndPage((int)Math.ceil(pd.getPageNum() / 10.0) * 10);
		pd.setStartPage(pd.getEndPage() - 9);
		
		logger.info("EndPage : " + pd.getEndPage() + " | StartPage : " + pd.getStartPage());
		
		int total = 123; // 임시설정
		int realEnd = (int)(Math.ceil((total * 1.0) / pd.getAmount()));
		
		if (realEnd < pd.getEndPage()) {
			pd.setEndPage(realEnd);
		}
		
		pd.setPrev(pd.getStartPage() > 1);
		pd.setNext(pd.getEndPage() < realEnd);
		
//		model.addAttribute("boardList", boardService.getBoardList(vo));
		model.addAttribute("boardList", boardService.getListWithPaging(pd));
		model.addAttribute("pageMaker", pd);
	}
	
	public PageDTO setPage(PageDTO pd, HttpServletRequest request) {
		pd.setPageNum(Integer.parseInt(request.getParameter("pageNum")));
		pd.setAmount(Integer.parseInt(request.getParameter("amount")));
	
		return pd;
	}
}
