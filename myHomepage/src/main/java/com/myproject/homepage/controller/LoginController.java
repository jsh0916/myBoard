package com.myproject.homepage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.BoardVO;
import com.myproject.homepage.user.UserService;
import com.myproject.homepage.user.UserVO;
import com.myproject.homepage.user.impl.UserDAO;

@Controller
public class LoginController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginView(UserVO vo) {
		System.out.println("로그인 화면으로 이동");
		
		vo.setId("test");
		vo.setPassword("test123");

		return "login";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, HttpSession session, Model model, BoardVO boardVO) {
		System.out.println("로그인 인증처리");
		
		UserVO user = userService.getUser(vo);
		
		if (user != null) {
			session.setAttribute("userName", user.getName());
			
			if (boardVO.getSearchCondition() == null) {
				boardVO.setSearchCondition("TITLE");
			}
			
			if (boardVO.getSearchKeyword() == null) {
				boardVO.setSearchKeyword("");
			}
			
			model.addAttribute("boardList", boardService.getBoardList(boardVO));
			
			return "getBoardList";
		} else {
			return "login";
		}
	}

}
