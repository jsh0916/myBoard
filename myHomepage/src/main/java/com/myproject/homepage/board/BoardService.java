package com.myproject.homepage.board;

import java.util.List;

public interface BoardService {
	// CRUD ����� �޼ҵ� ����
	// �� ���
	void insertBoard(BoardVO vo);
	// �� ����
	void updateBoard(BoardVO vo);
	// �� ����
	void deleteBOard(BoardVO vo);
	// �� �� ��ȸ
	BoardVO getBoard(BoardVO vo);
	// �� ��� ��ȸ
	List<BoardVO> getBoardList(BoardVO vo);
}
