package com.myproject.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.myproject.domain.MemberVO;
import com.myproject.homepage.SampleController;

import lombok.Setter;

@RunWith(SpringRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MemberMapperTests {
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;
	
	@Test
	public void testRead() {
		MemberVO vo = mapper.read("admin90");
		
		logger.info("vo : " + vo);
		
		vo.getAuthList().forEach(authVO -> logger.info("authVO : " + authVO));
	}
}
