package com.myproject.homepage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping(value="/test_all")
	public void doAll() {
		logger.info("do all can access everybody");
	}
	
	@RequestMapping(value="/test_member")
	public void doMember() {
		logger.info("logined member");
	}
	
	@RequestMapping(value="/test_admin")
	public void doAdmin() {
		logger.info("admin only");
	}
	
	@RequestMapping(value="/accessError")
	public void accessDenied(Authentication auth, Model model) {
		logger.info("access Denied : " + auth);
		
		model.addAttribute("msg", "Access Denied");
	}
}
