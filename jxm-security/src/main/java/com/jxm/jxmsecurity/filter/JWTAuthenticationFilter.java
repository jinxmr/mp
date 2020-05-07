package com.jxm.jxmsecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxm.jxmsecurity.vo.LoginParamVO;
import com.jxm.jxmsecurity.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;


/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		super.setFilterProcessesUrl("/malls/user/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {

		// 从输入流中获取到登录的信息
		try {
			LoginParamVO loginParam = new ObjectMapper().readValue(request.getInputStream(), LoginParamVO.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginParam.getLoginName(), loginParam.getPassword())
			);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 成功验证后调用的方法
	// 如果验证成功，就生成token并返回
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult) throws IOException, ServletException {

		LoginParamVO jwtUser = (LoginParamVO) authResult.getPrincipal();
		log.info("jwtUser:" + jwtUser.toString());

		String role = "";
		Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
		for (GrantedAuthority authority : authorities){
			role = authority.getAuthority();
		}

		String token = null;
		try {
			token = TokenUtil.createJWT(jwtUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
		// 返回创建成功的token
		// 但是这里创建的token只是单纯的token
		// 按照jwt的规定，最后请求的时候应该是 `Bearer token`
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("token",token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		log.error(failed.toString());
		response.getWriter().write("authentication failed, reason: " + failed.getMessage());
	}
}
