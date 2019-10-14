package com.myproject.homepage.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myproject.homepage.board.BoardService;
import com.myproject.homepage.domain.MemberVO;
import com.myproject.homepage.security.domain.CustomUser;

import lombok.Setter;

public class CustomUserDetailsService implements UserDetailsService {
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Setter(onMethod_ = {@Autowired})
	private BoardService boardServiceImpl;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		logger.warn("Load User By UserName : " + userName);
		
		// userName means userid
		MemberVO vo = boardServiceImpl.read(userName);
		
		logger.warn("Queried by member mapper : " + vo);
		
		return vo == null ? null : new CustomUser(vo);
	}

}
