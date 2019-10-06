package com.myproject.homepage.board;

import java.util.List;
import java.util.Map;

import com.myproject.homepage.board.vo.AttachFileVO;
import com.myproject.homepage.board.vo.BoardVO;
import com.myproject.homepage.board.vo.PageVO;
import com.myproject.homepage.board.vo.ReplyVO;

public interface BoardService {
	public void insertBoard(BoardVO vo);
	
	public void updateBoard(BoardVO vo);
	
	public void deleteBoard(BoardVO vo);
	
	public BoardVO getBoard(BoardVO vo);
	
	public List<BoardVO> getListWithPaging(PageVO pd);

	public int getTotalCount();

	public List<ReplyVO> getReplyListData(Map<String, String> param);
	
	public List<AttachFileVO> getAttachList(int seq); 
}
