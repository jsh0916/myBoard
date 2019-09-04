package com.myproject.homepage.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.homepage.board.BoardVO;
import com.myproject.homepage.board.PageVO;
import com.myproject.homepage.board.ReplyVO;


// DAO (Date Access Object)
@Repository("boardDAO")
public class BoardDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertBoard(BoardVO vo) {
		logger.info("===> insertBoard() 기능처리");
		mybatis.insert("BoardDAO.insertBoard", vo);
	}

	public void updateBoard(BoardVO vo) {
		logger.info("===> updateBoard() 기능처리");
		mybatis.update("BoardDAO.updateBoard", vo);
	}

	public void deleteBoard(BoardVO vo) {
		logger.info("===> deleteBoard() 기능처리");
		mybatis.delete("BoardDAO.deleteBoard", vo);
	}

	public BoardVO getBoard(BoardVO vo) {
		logger.info("===> getBoard() 기능처리");
		return (BoardVO) mybatis.selectOne("BoardDAO.getBoard", vo);
	}
	
	public List<BoardVO> getListWithPaging(PageVO pd) {
		logger.info("===> getListWithPaging() 기능처리");
		return mybatis.selectList("BoardDAO.getListWithPaging", pd);
	}

	public int getTotalCount() {
		logger.info("===> getTotalCount() 기능처리");
		return mybatis.selectOne("BoardDAO.getTotalCount");
	}

	public List<ReplyVO> getReplyListData(Map<String, String> param) {
		logger.info("===> getReplyListData() 기능처리");
		return mybatis.selectList("BoardDAO.getReplyListData", param);
	}
}
