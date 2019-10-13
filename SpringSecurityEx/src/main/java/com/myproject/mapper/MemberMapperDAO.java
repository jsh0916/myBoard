package com.myproject.mapper;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.domain.MemberVO;

@Repository
public class MemberMapperDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public MemberVO read(String userid) {
		return (MemberVO) mybatis.selectOne("MemberVO.read", userid);
	}
	
}
