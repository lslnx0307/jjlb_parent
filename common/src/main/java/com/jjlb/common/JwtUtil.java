package com.jjlb.common;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
	
	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public static SecretKey generalKey(){
		byte[] encodedKey = Base64.decodeBase64(Constants.JWT_SECRET);
	    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	    return key;
	}

	/**
	 * 创建jwt
	 * @param id
	 * @param subject
	 * @param nowMillis
	 * @param expMillis
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(String id, String subject,long nowMillis, long expMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
			.setId(id)
			.setIssuedAt(now)
			.setSubject(subject)
		    .signWith(signatureAlgorithm, key);
		if (expMillis >= nowMillis) {
		    Date exp = new Date(expMillis);
		    builder.setExpiration(exp);
		}
		return builder.compact();
	}
	
	/**
	 * 解密jwt
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();
		Claims claims = Jwts.parser()         
		   .setSigningKey(key)
		   .parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	/**
	 * 生成subject信息
	 * @param obj
	 * @return
	 */
	public static String generalSubject(Object obj){
     return JSON.toJSONString(obj);
	}
	
	/**
	 * 获取subject信息
	 * @param request
	 * @return
	 */
	public static String getSubject(HttpServletRequest request){
		String result = null;
		String auth = request.getHeader("authorization");
		if (!StringUtils.isEmpty(auth) && auth.length() > 7) {
			String HeadStr = auth.substring(0, 6).toLowerCase();
			if (HeadStr.compareTo("bearer") == 0) {
				String jwt = auth.substring(7, auth.length());
				try {
					String payload = JwtUtil.parseJWT(jwt).getSubject();
					if (!StringUtils.isEmpty(payload)) {
						result = payload;
					}
				} catch (Exception e) {
					logger.error("jwt解析失败：auth=" + auth, e);
				}
			}
		}
		return result;
	}
}
