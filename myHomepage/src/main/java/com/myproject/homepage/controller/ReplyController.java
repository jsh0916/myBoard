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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myproject.homepage.board.ReplyService;

@Controller
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private ReplyService replyService;
	
	
	@RequestMapping(value="insertReply.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> insertReply (@RequestParam Map<String, String> param) {
		logger.info("=============== insertReply START ===============");

		Map<String, String> retVal = new HashMap<>();
		
		// 패스워드 암호화
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(param.get("reply_password").toString(), null);
		param.put("reply_password", password);
		
		// 정보입력
		int result = replyService.insertReply(param);

		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("reply_rno", param.get("reply_rno"));
			retVal.put("parent_id", param.get("parent_id"));
			retVal.put("message", "등록에 성공 하였습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 하였습니다.");
		}
		
		logger.info("=============== insertReply END ===============");
		
		return retVal;
	}
	
	@RequestMapping(value="deleteReply.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteReply (@RequestParam Map<String, String> param) {
		logger.info("=============== deleteReply START ===============");

		Map<String, String> retVal = new HashMap<>();
		
		// 패스워드 암호화
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(param.get("reply_password").toString(), null);
		param.put("reply_password", password);
		
		// 정보입력
		int result = replyService.deleteReply(param);
		
		if (result > 0) {
			retVal.put("code", "OK");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "삭제에 실패했습니다. 패스워드를 확인해주세요");
		}
		
		logger.info("=============== deleteReply END ===============");
		
		return retVal;
	}
	
	@RequestMapping(value="checkReply.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> checkReply (@RequestParam Map<String, String> param) {
		logger.info("=============== checkReply START ===============");
		
		Map<String, String> retVal = new HashMap<>();
		
		// 패스워드 암호화
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(param.get("reply_password").toString(), null);
		param.put("reply_password", password);
		
		// 정보입력
		boolean check = replyService.checkReply(param);
		
		logger.info("CHECK: " + check);
		
		if (check) {
			retVal.put("code", "OK");
			retVal.put("reply_rno", param.get("reply_rno"));
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "패스워드를 확인해 주세요.");
		}
		
		logger.info("=============== checkReply END ===============");
	
		return retVal;
	}
	
	@RequestMapping(value="updateReply.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateReply (@RequestParam Map<String, String> param) {
		logger.info("=============== updateReply START ===============");
		
		Map<String, String> retVal = new HashMap<>();
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(param.get("reply_password").toString(), null);
		param.put("reply_password", password);
		
		boolean check = replyService.updateReply(param);
		
		if (check) {
			retVal.put("code", "OK");
			retVal.put("reply_rno", param.get("reply_rno"));
			retVal.put("message", "수정에 성공 하였습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "수정에 실패 하였습니다.");
		}
		
		logger.info("=============== updateReply END ===============");
		
		return retVal;
	}
	
	@RequestMapping(value="replyReplyInsert.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> replyReplyInsert (@RequestParam Map<String, String> param) {
		logger.info("=============== replyReplyInsert START ===============");
		
		Map<String, String> retVal = new HashMap<>();
		
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		String password = encoder.encodePassword(param.get("reply_password").toString(), null);
		param.put("reply_password", password);
		
		int result = replyService.insertReply(param);
		
		if (result > 0) {
			retVal.put("code", "OK");
			retVal.put("reply_rno", param.get("reply_rno"));
			retVal.put("parent_id", param.get("parent_id"));
			retVal.put("message", "등록에 성공 하였습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "등록에 실패 하였습니다.");
		}
		
		logger.info("=============== replyReplyInsert END ===============");
		
		return retVal;
	}
}
