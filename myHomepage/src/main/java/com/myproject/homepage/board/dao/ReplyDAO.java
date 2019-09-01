package com.myproject.homepage.board.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.homepage.board.BoardVO;


// DAO (Date Access Object)
@Repository("replyDAO")
public class ReplyDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertBoard(BoardVO vo) {
		logger.info("===> insertBoard() 기능처리");
		mybatis.insert("BoardDAO.insertBoard", vo);
	}

}
