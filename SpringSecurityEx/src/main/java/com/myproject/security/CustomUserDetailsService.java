package com.myproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myproject.domain.MemberVO;
import com.myproject.homepage.SampleController;
import com.myproject.mapper.MemberMapperImpl;
import com.myproject.security.domain.CustomUser;

import lombok.Setter;

public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Setter(onMethod_ = {@Autowired})
	private MemberMapperImpl MemberMapperImpl;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.warn("Load User By UserName : " + userName);
		
		// userName means userid
		MemberVO vo = MemberMapperImpl.read(userName);
		
		logger.warn("Queried by member mapper : " + vo);
		
		return vo == null ? null : new CustomUser(vo);
	}

}
