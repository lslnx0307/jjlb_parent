package com.jjlb.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public BaseResult<String> handleException(HttpServletRequest req, Exception e) throws Exception {
    	logger.error(req.getRequestURI(), e);
		return new BaseResult<String>(false,null,"系统异常，请联系客服");
    }
    
    @ExceptionHandler(value =UnauthorizedException.class)
    @ResponseBody
    public BaseResult<String> handleUnauthorizedException(HttpServletRequest req, UnauthorizedException e) throws Exception {
		return new BaseResult<String>(false,null,"请确认您的系统权限");
    }
    
    @ExceptionHandler(value =BusinessException.class)
    @ResponseBody
    public BaseResult<String> handleBusinessException(HttpServletRequest req, BusinessException e) throws Exception {
		return new BaseResult<String>(false,e.getCode(),e.getMessage());
    }    
    
}
