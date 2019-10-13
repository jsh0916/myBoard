package com.myproject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.domain.MemberVO;

@Service
public class MemberMapperImpl implements MemberMapper {

	@Autowired
	private MemberMapperDAO memberMapperDAO;
	
	@Override
	public MemberVO read(String userid) {
		return memberMapperDAO.read(userid);
	}

}
