package com.myproject.homepage.board.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.myproject.homepage.board.BoardVO;

public class BoardDAOSpring extends JdbcDaoSupport {
	private final String BOARD_INSERT 	= "insert into board(title, writer, content) values (?, ?, ?)";
	private final String BOARD_UPDATE 	= "update board set title = ?, content = ? where seq = ?";
	private final String BOARD_DELETE 	= "delete board where seq = ?";
	private final String BOARD_GET 		= "select * from board where seq = ?";
	private final String BOARD_LIST 	= "select * from board order by seq desc";
	
	@Autowired
	public void setSuperDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
	
	// CRUD
	// �� ���
	public void insertBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC�� insertBoard() ��� ó��");
		getJdbcTemplate().update(BOARD_INSERT, vo.getTitle(), vo.getWriter(), vo.getContent());
	}
	
	// �� ����
	public void updateBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC�� updateBoard() ��� ó��");
		getJdbcTemplate().update(BOARD_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());
	}
	
	// �� ����
	public void deleteBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC�� deleteBoard() ��� ó��");
		getJdbcTemplate().update(BOARD_DELETE, vo.getSeq());
	}
	
	// �� �� ��ȸ
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===> Spring JDBC�� getBoard() ��� ó��");
		
		Object[] args = {vo.getSeq()};
		return getJdbcTemplate().queryForObject(BOARD_GET, args, new BoardRowMapper());
	}
	
	// �� ��� ��ȸ
	public List<BoardVO> getBoardList(BoardVO vo) {
		System.out.println("===> Spring JDBC�� getBoardList() ��� ó��");
		return getJdbcTemplate().query(BOARD_GET, new BoardRowMapper());
	}
}