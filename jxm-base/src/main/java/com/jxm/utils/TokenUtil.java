package com.jxm.utils;

import com.jxm.dto.LoginParam;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {

	private static final String KEY = "4ea0a7bce8717e27a1203158056c06f3acff0e9cb1932";

	private static final long OVER_TIME = 60 * 60 * 24 * 1000L;

	public static String JWT_MSG_KEY = "b3d4e546a7a94da59cb193203116c06f3acff0e258054";

	static byte[] KEY_BYTE = DatatypeConverter.parseBase64Binary(KEY);

	public static void main(String[] args) {
		LoginParam loginParam = new LoginParam("jinxm", "靳祥民", "M0001");
		try {
			String jwt = createJWT(loginParam);
			System.out.println(jwt);

			Claims claims = parseJWT(jwt);
			System.out.println(claims.get("userName"));
			System.out.println(claims.get("loginName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建jwt
	 */
	public static String createJWT(LoginParam loginParam) throws Exception {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Key signingKey = new SecretKeySpec(KEY_BYTE, signatureAlgorithm.getJcaName());
		String uuId = UUID.randomUUID().toString();

		//获取信息
		String loginName = loginParam.getLoginName();
		String userName = loginParam.getUserName();
		String workNumber = loginParam.getWorkNumber();
		String userId = loginParam.getUserId();

		Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
		claims.put("loginName", loginName);
		claims.put("userName", userName);
		claims.put("workNumber", workNumber);
		claims.put("userId", userId);

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		//下面就是在为payload添加各种标准声明和私有声明了
		JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
				.setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setId(uuId)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setIssuedAt(now)           //iat: jwt的签发时间
				.signWith(signingKey, signatureAlgorithm);//设置签名使用的签名算法和签名使用的秘钥
		//设置超时时间
		long expMillis = nowMillis + OVER_TIME;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);     //设置过期时间
		return builder.compact();           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
	}

	/**
	 * 解密jwt
	 */
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
		Claims claims = Jwts.parser()  //得到DefaultJwtParser
				.setSigningKey(KEY_BYTE)         //设置签名的秘钥
				.parseClaimsJws(jwt)
				.getBody();//设置需要解析的jwt
		return claims;
	}

	/**
	 * 由字符串生成加密key
	 */
	public static SecretKey generalKey() {
		byte[] encodedKey = Base64.decodeBase64(KEY);//本地的密码解码[B@152f6e2
		System.out.println(encodedKey);//[B@152f6e2
		System.out.println(Base64.encodeBase64URLSafeString(encodedKey));//7786df7fc3a34e26a61c034d5ec8245d
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
		return key;
	}

}
