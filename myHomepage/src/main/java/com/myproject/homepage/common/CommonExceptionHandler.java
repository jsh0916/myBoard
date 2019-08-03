package com.myproject.homepage.common;

import org.springframework.web.servlet.ModelAndView;

/*
 * com.myproject.homepage 패키지로 시작하는 Controller에서 Exception이 발생하면
 * @ExceptionHandler Annotation으로 지정한 메소드가 실행됨.
 * */
//@ControllerAdvice("com.myproject.homepage")
public class CommonExceptionHandler {

//	@ExceptionHandler(ArithmeticException.class)
	public ModelAndView handleArithmeticException(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.setViewName("/common/arithmeticError.jsp");
		
		return mav;
	}
	
//	@ExceptionHandler(NullPointerException.class)
	public ModelAndView handleNullPointerException(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.setViewName("/common/nullPointerError.jsp");
		
		return mav;
	}
	
//	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.setViewName("/common/error.jsp");
		
		return mav;
	}
}
