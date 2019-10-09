package com.myproject.homepage.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.homepage.board.vo.AttachFileVO;

//DAO (Date Access Object)
@Repository("attachDAO")
public class AttachDAO {
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertAttach (AttachFileVO vo) {
		logger.info("===> insertAttach() 기능처리");
		return mybatis.insert("AttachDAO.insertAttach", vo);
	}
	
	public void deleteAttach (int seq) {
		logger.info("===> deleteAttach() 기능처리");
		mybatis.delete("AttachDAO.deleteAttach", seq);
	}
	
	public List<AttachFileVO> findBySeq (int seq) {
		logger.info("===> FindBySeq() 기능처리");
		return mybatis.selectList("AttachDAO.findBySeq", seq);
	}

	public List<AttachFileVO> getAllFiles() {
		logger.info("===> getAllFiles() 기능처리");
		return mybatis.selectList("AttachDAO.getAllFiles");
	}
}
