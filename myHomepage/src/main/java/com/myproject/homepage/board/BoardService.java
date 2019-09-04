package com.myproject.homepage.board;

import java.util.List;
import java.util.Map;

public interface BoardService {
	public void insertBoard(BoardVO vo);
	
	public void updateBoard(BoardVO vo);
	
	public void deleteBoard(BoardVO vo);
	
	public BoardVO getBoard(BoardVO vo);
	
	public List<BoardVO> getListWithPaging(PageVO pd);

	public int getTotalCount();

	public List<ReplyVO> getReplyListData(Map<String, String> param);
}
