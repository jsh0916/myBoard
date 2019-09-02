package com.myproject.homepage.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.homepage.board.ReplyService;
import com.myproject.homepage.board.ReplyVO;
import com.myproject.homepage.board.dao.ReplyDAO;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDAO replyDAO;

	@Override
	public void insertReply(ReplyVO vo) {
		replyDAO.insertReply(vo);
	}

	@Override
	public ReplyVO readReply(ReplyVO vo) {
		return replyDAO.readReply(vo);
	}

	@Override
	public void deleteReply(ReplyVO vo) {
		replyDAO.deleteReply(vo);
	}

	@Override
	public void updateReply(ReplyVO vo) {
		replyDAO.updateReply(vo);
	}

	@Override
	public List<ReplyVO> getListWithPaging(ReplyVO vo) {
		return replyDAO.getListWithPaging(vo);
	}

	

}
