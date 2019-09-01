package com.myproject.homepage.user.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.homepage.user.UserVO;

// DAO(Data Access Object)
@Repository("userDAO")
public class UserDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public UserVO getUser(UserVO vo) {
		return (UserVO) mybatis.selectOne("UserDAO.getUser", vo);
	}
}
