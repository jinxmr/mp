package com.jxm.jxmsecurity.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxm.jxmsecurity.vo.LoginParamVO;
import com.jxm.jxmsecurity.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
 * 登录认证
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
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

    /**
     * 验证成功调用的方法
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        LoginParamVO jwtUser = (LoginParamVO) authResult.getPrincipal();
        log.info("jwtUser:" + jwtUser.toString());

        String token = null;
        try {
            token = TokenUtil.createJWT(jwtUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","登录成功");
        jsonObject.put(TokenUtil.getHeader(),token);
        jsonObject.put("code", 200);
        response.getWriter().write(jsonObject.toJSONString());
    }

    /**
     * 认证失败调用的方法
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error(failed.toString());

        JSONObject jsonObject = new JSONObject();
        if (failed instanceof BadCredentialsException) {
            //密码错误
            jsonObject.put("msg","密码错误");
            jsonObject.put("code", 299);
        } else if (failed instanceof LockedException) {
            //账号锁定
            jsonObject.put("msg","账号锁定");
            jsonObject.put("code", 299);
        } else if (failed instanceof InternalAuthenticationServiceException) {
            //用户不存在
            jsonObject.put("msg","用户不存在");
            jsonObject.put("code", 299);

        } else {
            //其他错误
            jsonObject.put("msg","其他错误");
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
    }
}
