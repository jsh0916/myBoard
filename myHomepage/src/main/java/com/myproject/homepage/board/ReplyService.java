package com.myproject.homepage.board;

import java.util.List;
import java.util.Map;

public interface ReplyService {
	public int insertReply(Map<String, String> param);
	
	public ReplyVO readReply(ReplyVO vo);
	
	public void deleteReply(ReplyVO vo);
	
	public void updateReply(ReplyVO vo);
	
	public List<ReplyVO> getListWithPaging(ReplyVO vo);
}
