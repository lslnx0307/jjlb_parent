package com.jjlb.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjlb.common.BaseResult;
import com.jjlb.common.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

public class AuthorizationTerceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		boolean result = false;
//		String code = "11111";
//		String payload = JwtUtil.getSubject(request);
//		if (!StringUtils.isEmpty(payload)) {
//			String auth = request.getHeader("authorization");
//			String jwt = auth.substring(7, auth.length());
////			MemberToken token = memberTokenDao.selectByUid(new Integer(payload));
////			if (token != null && token.getToken().equals(jwt)) {
//			if(true){
//				result = true;
//			}else{
//				code = "11110";
//			}
//		}
//		if (result == false) {
//			response.setCharacterEncoding("UTF-8");
//			response.setContentType("application/json; charset=utf-8");
//			response.setStatus(HttpServletResponse.SC_OK);
//			response.setHeader("code", code);
//			if("11110".equals(code)){
//				response.getWriter().write(JSON.toJSONString(new BaseResult<String>(false, "11110", "帐户在其他设备登录")));
//			}else{
//				response.getWriter().write(JSON.toJSONString(new BaseResult<String>(false, "11111", "登录超时")));
//			}
//		}
		return true;
	}

}
