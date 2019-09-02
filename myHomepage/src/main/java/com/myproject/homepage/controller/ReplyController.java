package com.myproject.homepage.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.homepage.board.ReplyService;

@Controller
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private ReplyService replyService;
	
	
	@RequestMapping(value="insertReply.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> insertReply(Map<String, String> param) {
		Map<String, String> retVal = new HashMap<>();
		
		// 패스워드 암호화
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(param.get("reply_password").toString(), null);
		param.put("reply_password", password);
	
		// 정보입력
//		int result = replyService.insertReply(vo);
		return retVal;
	}
}
