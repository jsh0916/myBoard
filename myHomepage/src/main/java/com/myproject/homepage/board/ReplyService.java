package com.myproject.homepage.board;

import java.util.List;
import java.util.Map;

public interface ReplyService {
	public int insertReply(Map<String, String> param);
	
	public ReplyVO readReply(ReplyVO vo);
	
	public int deleteReply(Map<String, String> param);
	
	public boolean updateReply(Map<String, String> param);
	
	public List<ReplyVO> getListWithPaging(ReplyVO vo);

	public boolean checkReply(Map<String, String> param);
}
