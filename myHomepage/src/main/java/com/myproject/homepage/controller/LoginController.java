package com.myproject.homepage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.BoardVO;
import com.myproject.homepage.user.UserService;
import com.myproject.homepage.user.UserVO;
import com.myproject.homepage.user.impl.UserDAO;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String home(BoardVO boardVO, Model model) {
		logger.info("Welcome home!");

		getBoardListData(boardVO, model);

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
	public String login(UserVO vo, BoardVO boardVO, HttpSession session, Model model) {
		System.out.println("로그인 인증처리");
		
		UserVO user = userService.getUser(vo);
		
		if (user != null) {
			session.setAttribute("userName", user.getName());
			
			getBoardListData(boardVO, model);
		}

		return "index";
	}
	
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		
		return conditionMap;
	}
	
	@RequestMapping("/logout.do")
	public String logout(BoardVO vo, Model model, HttpSession session) {
		session.invalidate();
		
		getBoardListData(vo, model);
		
		return "index";
	}
	
	public void getBoardListData(BoardVO vo, Model model) {
		if (vo.getSearchCondition() == null) {
			vo.setSearchCondition("TITLE");
		}
		
		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}
		
		model.addAttribute("boardList", boardService.getBoardList(vo));
	}
}
