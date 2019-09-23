package com.myproject.homepage.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.homepage.board.vo.ReplyVO;


// DAO (Date Access Object)
@Repository("replyDAO")
public class ReplyDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertReply (Map<String, String> param) {
		logger.info("===> insertReply() 기능처리");
		return mybatis.insert("ReplyDAO.insertReply", param);
	}
	
	public ReplyVO readReply (ReplyVO vo) {
		logger.info("===> readReply() 기능처리");
		return mybatis.selectOne("ReplyDAO.readReply", vo);
	}
	
	public int deleteReply (Map<String, String> param) {
		logger.info("===> deleteReply() 기능처리");
		return mybatis.delete("ReplyDAO.deleteReply", param);
	}

	public boolean updateReply (Map<String, String> param) {
		logger.info("===> updateReply() 기능처리");
		int result = mybatis.update("ReplyDAO.updateReply", param);
		
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<ReplyVO> getListWithPaging (ReplyVO vo) {
		logger.info("===> getListWithPaging() 기능처리");
		return mybatis.selectList("ReplyDAO.getListWithPaging", vo);
	}

	public boolean checkReply (Map<String, String> param) {
		logger.info("===> checkReply() 기능처리");
		int result = mybatis.selectOne("ReplyDAO.checkReply", param);
		
		if (result > 0) {
			return true;
		} else {
			return false;			
		}
	}
}
