/*
package com.jxm.base.interceptor;

import com.jxm.base.utils.TokenUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

public class BaseAuthInterceptor extends HandlerInterceptorAdapter {

	public BaseAuthInterceptor() {
		super();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		if(StringUtils.isNotBlank(token)) {
			//解析token
			Claims claims = TokenUtil.parseJWT(token);
			claims.get
		}
		return super.preHandle(request, response, handler);
	}
}
*/
