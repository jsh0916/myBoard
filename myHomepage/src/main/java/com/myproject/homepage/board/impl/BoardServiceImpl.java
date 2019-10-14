package com.myproject.homepage.board.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.dao.AttachDAO;
import com.myproject.homepage.board.dao.BoardDAO;
import com.myproject.homepage.board.vo.AttachFileVO;
import com.myproject.homepage.board.vo.BoardVO;
import com.myproject.homepage.board.vo.PageVO;
import com.myproject.homepage.board.vo.ReplyVO;
import com.myproject.homepage.controller.BoardController;
import com.myproject.homepage.domain.MemberVO;

@Service
public class BoardServiceImpl implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private AttachDAO attachDAO;

	@Override
	public void insertBoard(BoardVO vo) {
		boardDAO.insertBoard(vo);
		
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		} else {
			vo.getAttachList().forEach(attach -> {
				attach.setSeq(vo.getSeq());
				attachDAO.insertAttach(attach);
			});			
		}
	}

	@Override
	public void updateBoard(BoardVO vo) {
		boardDAO.updateBoard(vo);
		
		attachDAO.deleteAttach(vo.getSeq());
		
		if (vo.getAttachList() != null && vo.getAttachList().size() > 0) {
			vo.getAttachList().forEach(attach -> {
				attach.setSeq(vo.getSeq());
				attachDAO.insertAttach(attach);
			});
		}
	}

	@Override
	public void deleteBoard(BoardVO vo) {
		boardDAO.deleteBoard(vo);
		
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		} else {
			attachDAO.deleteAttach(vo.getSeq());
		}
	}

	@Override
	public BoardVO getBoard(BoardVO vo) {
		return boardDAO.getBoard(vo);
	}

	@Override
	public List<BoardVO> getListWithPaging(PageVO pd) {
		return boardDAO.getListWithPaging(pd);
	}

	@Override
	public int getTotalCount() {
		return boardDAO.getTotalCount();
	}

	@Override
	public List<ReplyVO> getReplyListData(Map<String, String> param) {
		List<ReplyVO> replyList = boardDAO.getReplyListData(param);
		
		// 부모
		List<ReplyVO> replyListParent = new ArrayList<>();
		// 자식
		List<ReplyVO> replyListChild = new ArrayList<>();
		// 통합
		List<ReplyVO> newReplyList = new ArrayList<>();
		
		// 1. 부모와 자식 분리
		for (ReplyVO reply : replyList) {
			if (reply.getDepth() == 0) {
				replyListParent.add(reply);
			} else {
				replyListChild.add(reply);
			}
		}
		
		// 2. 부모를 돌린다.
		for (ReplyVO replyParent : replyListParent) {
			// 2-1. 부모는 무조건 넣는다.
			newReplyList.add(replyParent);
			// 3. 자식을 돌린다.
			for (ReplyVO replyChild : replyListChild) {
				// 3-1. 부모의 자식인 것들만 넣는다.
				if (replyParent.getRno() == replyChild.getParent_id()) {
					newReplyList.add(replyChild);
				}
			}
		}
		
		return newReplyList;
	}

	@Override
	public List<AttachFileVO> getAttachList(int seq) {
		logger.info("Attach List SEQ : " + seq);
		
		return attachDAO.findBySeq(seq);
	}

	@Override
	public List<AttachFileVO> getAllFiles() {
		
		return attachDAO.getAllFiles();
	}

	@Override
	public MemberVO read(String userid) {
		return boardDAO.read(userid);
	}
}
