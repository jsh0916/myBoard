package com.myproject.homepage.board.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.board.BoardVO;
import com.myproject.homepage.board.PageVO;
import com.myproject.homepage.board.ReplyVO;
import com.myproject.homepage.board.dao.BoardDAO;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public void insertBoard(BoardVO vo) {
		boardDAO.insertBoard(vo);
	}

	@Override
	public void updateBoard(BoardVO vo) {
		boardDAO.updateBoard(vo);
	}

	@Override
	public void deleteBoard(BoardVO vo) {
		boardDAO.deleteBoard(vo);
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
				if (replyParent.getBno() == replyChild.getParent_id()) {
					newReplyList.add(replyChild);
				}
			}
		}
		
		return newReplyList;
	}

}
