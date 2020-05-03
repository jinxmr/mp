package com.jxm.interceptor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jxm.utils.CookieUtils;
import com.jxm.utils.TokenUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;


/**
 * @author jinxm
 * @date 2018-07-08 20:41
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

	//不需要校验的路径
	private static final String[] NOT_CHECK_URL = {"/login", "/registered"};

	private static final String SECURITY_USER = "SECURITY_USER";

	private boolean checkURI(String uri) {
		for (String nUri : NOT_CHECK_URL) {
			if (uri.contains(nUri)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String token = request.getHeader(TokenUtil.getHeader());// 从 http 请求头中取出 token
		if (checkURI(request.getRequestURI())) {
			return true;
		}
		if (StringUtils.isEmpty(token)) {
			return false;
		}

		Claims claims = null;
		try {
			claims = TokenUtil.parseJWT(token);
		} catch (Exception e) {
			//清空cookie
			CookieUtils.delCookie(response,TokenUtil.getHeader());
			setErr(response, "299", "认证失败");
			return false;
		}
		if (null != claims) {
			Date expiration = claims.getExpiration();
			if (TokenUtil.isExpired(expiration)) {

				Object userId = claims.get("userId");
				request.setAttribute(SECURITY_USER, userId);
				Cookie jwtCookie = new Cookie(TokenUtil.getHeader(), URLEncoder.encode(token, "UTF-8"));
				jwtCookie.setHttpOnly(true);
				jwtCookie.setPath("/");
				response.addCookie(jwtCookie);

				response.addHeader(TokenUtil.getHeader(), token);
				return true;
			} else {
				return false;
			}
		} else {
			log.info("{} 无效，请重新登录！", TokenUtil.getHeader());
			request.setAttribute("exceptionCode", "error");
			request.setAttribute("exceptionMessage", TokenUtil.getHeader() + " 无效，请重新登录！");
			request.getRequestDispatcher("/exception/authentication")
					.forward(request, response);
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}

	private void setErr(HttpServletResponse response, String code, String msg) throws IOException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("code", msg);
		jsonArray.add(jsonObject1);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("code", msg);
		jsonArray.add(jsonObject2);
		response.getWriter().write(jsonArray.toJSONString());
	}
}
