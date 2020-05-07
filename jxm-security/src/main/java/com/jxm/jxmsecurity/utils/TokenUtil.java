package com.jxm.jxmsecurity.utils;

import java.security.Key;
import java.util.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.jxm.jxmsecurity.vo.LoginParamVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {

    private static final String KEY = "4ea0a7bce8717e27a1203158056c06f3acff0e9cb1932";

    private static final long OVER_TIME = 60 * 60 * 24 * 1000L;

    private static final String HEADER = "Authorization";

    static byte[] KEY_BYTE = DatatypeConverter.parseBase64Binary(KEY);


    public static void main(String[] args) throws Exception {
        String jwt = createJWT(new LoginParamVO(1L, "jinxmxx", "jxm", "M00001"));
        System.out.println(jwt);
	}

    /**
     * 随机生成5位的字符串
     */
    public static String randomStr() {
        int i = 1;
        StringBuffer sb = new StringBuffer();
        while (i <= 5) {
            char c = (char) ('a' + Math.random() * ('z' - 'a' + 1));
            sb.append(c);
            i++;
        }
        return sb.toString();
    }

    /**
     * 创建jwt
     */
    public static String createJWT(LoginParamVO loginParam) throws Exception {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(KEY_BYTE, signatureAlgorithm.getJcaName());
        String uuId = UUID.randomUUID()
                .toString();

        //获取信息
        String loginName = loginParam.getLoginName();
        String userName = loginParam.getUsername();
        String workNumber = loginParam.getWorkNumber();
        Long userId = loginParam.getUserId();

        Map<String, Object> claims = new HashMap<String, Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("loginName", loginName);
        claims.put("userName", userName);
        claims.put("workNumber", workNumber);
        claims.put("userId", userId);
        List<String> roles = new ArrayList<String>(){{
            add("user");
            add("p");
        }};
        claims.put("rol", roles);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(uuId)                  //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           //iat: jwt的签发时间
                .setExpiration(now)            //JWT签发时间
                .signWith(signingKey, signatureAlgorithm);//设置签名使用的签名算法和签名使用的秘钥
        //设置超时时间
        long expMillis = nowMillis + OVER_TIME;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);     //设置过期时间
		String token = builder.compact();
		return token;           //就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
    }

    /**
     * 解密jwt
     */
    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(KEY_BYTE)         //设置签名的秘钥
                .parseClaimsJws(jwt)
                .getBody();//设置需要解析的jwt
        return claims;
    }

    /**
     * 判断token是否过期
     */
    public static boolean isExpired(Date expiration) {
        return expiration.after(new Date());

    }

    /**
     * 获取request的头
     *
     * @return
     */
    public static String getHeader() {
        return HEADER;
    }

    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        Claims claims = Jwts.parser().setSigningKey(KEY_BYTE).parseClaimsJws(token).getBody();
        return claims.get("userName").toString();
    }

    /**
     * 获取用户角色
     *
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        Claims claims = Jwts.parser().setSigningKey(KEY_BYTE).parseClaimsJws(token).getBody();
        return claims.get("rol").toString();
    }

}
