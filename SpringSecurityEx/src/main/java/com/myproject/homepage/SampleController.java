package com.myproject.homepage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/sample/*")
@Controller
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping(value="/all")
	public void doAll() {
		logger.info("do all can access everybody");
	}
	
	@RequestMapping(value="/member")
	public void doMember() {
		logger.info("logined member");
	}
	
	@RequestMapping(value="/admin")
	public void doAdmin() {
		logger.info("admin only");
	}
	
	@RequestMapping(value="/accessError")
	public void accessDenied(Authentication auth, Model model) {
		logger.info("access Denied : " + auth);
		
		model.addAttribute("msg", "Access Denied");
	}
	
	@RequestMapping(value="/customLogin")
	public void loginInput(String error, String logout, Model model) {
		logger.info("error : " + error);
		logger.info("logout : " + logout);
		
		if (error != null) {
			model.addAttribute("error", "Login Error Check Your Account");
		}
		
		if (logout != null) {
			model.addAttribute("logout", "Logout!!");
		}
	}
}
