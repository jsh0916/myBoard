package com.myproject.homepage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.vo.BoardVO;
import com.myproject.homepage.board.vo.PageVO;
import com.myproject.homepage.user.UserService;
import com.myproject.homepage.user.UserVO;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String home(BoardVO boardVO, PageVO pd, @RequestParam Map<String, String> param, HttpServletRequest request, Model model) {
		logger.info("Welcome home!");

		if (request.getParameter("type") != null) {
			String type = request.getParameter("type");
			String keyword = request.getParameter("keyword");
			
			pd.setType(type);
			pd.setKeyword(keyword);
		} else {
			pd.setType("");
		}
		
		getBoardListData(boardVO, pd, model);
		
		logger.info("PageNum : " + pd.getPageNum() + " | Amount : " + pd.getAmount());

		return "index";
	}

	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginView(UserVO vo) {
		System.out.println("로그인 화면으로 이동");
		
		vo.setId("test");
		vo.setPassword("test123");

		return "login";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, BoardVO boardVO, PageVO pd, HttpSession session, Model model) {
		System.out.println("로그인 인증처리");
		
		UserVO user = userService.getUser(vo);
		
		if (user != null) {
			session.setAttribute("userName", user.getName());
			
			getBoardListData(boardVO, pd, model);
		}

		return "index";
	}
	
	@RequestMapping("/logout.do")
	public String logout(BoardVO vo, Model model, PageVO pd, HttpSession session) {
		session.invalidate();
		
		getBoardListData(vo, pd, model);
		
		return "index";
	}
	
	public void getBoardListData(BoardVO vo, PageVO pd, Model model) {
		logger.info("=============== getBoardListData START ===============");
		logger.info("pageNum : " + pd.getPageNum() + " | Amount : " + pd.getAmount());
		
		if (pd.getPageNum() <= 0 && pd.getAmount() <= 0) {
			pd.setPageNum(1);
			pd.setAmount(10);
		}
		
		pd.setEndPage((int)Math.ceil(pd.getPageNum() / 10.0) * 10);
		pd.setStartPage(pd.getEndPage() - 9);
		
		logger.info("EndPage : " + pd.getEndPage() + " | StartPage : " + pd.getStartPage());
		
		int total = boardService.getTotalCount();
		int realEnd = (int)(Math.ceil((total * 1.0) / pd.getAmount()));
		
		if (realEnd < pd.getEndPage()) {
			pd.setEndPage(realEnd);
		}
		
		pd.setPrev(pd.getStartPage() > 1);
		pd.setNext(pd.getEndPage() < realEnd);
		
		model.addAttribute("boardList", boardService.getListWithPaging(pd));
		model.addAttribute("pageMaker", pd);
		
		logger.info("=============== getBoardListData END ===============");
	}
}
