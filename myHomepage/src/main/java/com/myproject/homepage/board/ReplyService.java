package com.myproject.homepage.board;

import java.util.List;

public interface ReplyService {
	public void insertReply(ReplyVO vo);
	
	public ReplyVO readReply(ReplyVO vo);
	
	public void deleteReply(ReplyVO vo);
	
	public void updateReply(ReplyVO vo);
	
	public List<ReplyVO> getListWithPaging(ReplyVO vo);
}
