package com.pactera.jwt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * JSON WEB Token（JWT，读作 [/dʒɒt/]），是一种基于JSON的、
 * 用于在网络上声明某种主张的令牌（token）。
 * JWT通常由三部分组成: 
 * 头信息（header）, 消息体（payload）和签名（signature）。
 * @author Administrator
 *
 */
@Component
public class JWTUtil {
	private static final Logger LOG = LoggerFactory.getLogger(JWTUtil.class);
	@Value("${jwt.issuer}")
	public String issuer;
	@Value("${jwt.general.key}")
	public String generalKey;
	@Value("${jwt.effective.self.enable}")
	public boolean enableDateSetting;
	@Value("${jwt.effective.date}")
	public long effectiveDate;

	/**
	 * 创建加密jwt
	 *@param id  id标志
	 *@param subject 加密内容
	 *@param ttlMillis 有效时间毫秒
	 *@return
	 */
	public String createJWT(String id, String subject, long ttlMillis)
			throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder().setId(id) // JWT_ID 做唯一标识
				.setIssuedAt(now) // 生成的签发时间
				.setSubject(subject) // 用户对象
				.setIssuer(issuer)
				// .setNotBefore(now)
				.signWith(signatureAlgorithm, key);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	 /**
	  * 解析jwt
	  *@param jwt  jwt加密串
	  *@return
	  *@throws Exception
	  */
	public Claims parseJWT(String jwt) throws Exception {
		SecretKey key = generalKey();
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt)
				.getBody();
		return claims;
	}

	/**
	 * 生成TAI ticket有效时间20小时(默认是20个小时)
	 *
	 * @param empData 用户信息
	 * @return
	 */
	public String getTaiTicket(String empData) {
		String ticket = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			effectiveDate=enableDateSetting?effectiveDate:72000000;
			ticket = createJWT(df.format(new Date()), empData, effectiveDate);
			ticket=URLEncoder.encode(ticket);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return ticket;
	}
	/**
	 * 解析TAI的ticket
	 *@param ticket
	 *@return
	 */
	public String parseTaiTicket(String ticket) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			String date = df.format(new Date());
			Claims tmp = parseJWT(ticket);
			String jti = (String) tmp.get("jti");
			if (date.equalsIgnoreCase(jti)) {
				return tmp.getSubject();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 由字符串生成加密key
	 * 
	 * @return
	 */
	public SecretKey generalKey() {
		String stringKey = generalKey;
		byte[] encodedKey = stringKey.getBytes();
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length,"AES");
		return key;
	}
	
	public static void main(String[] args) {
		JWTUtil  jwt=new JWTUtil();
		try {
			System.out.println("simon:"+jwt.getTaiTicket("simonMeng")+"---");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		 System.out.println(jwt.parseTaiTicket("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyMDE5LTEwLTI5IiwiaWF0IjoxNTcyMzIwODcxLCJzdWIiOiJzaW1vbk1lbmciLCJpc3MiOiJzb2xhcnRlY2giLCJleHAiOjE1NzIzOTI4NzF9.yd2-9MO00yI8x4Pp0ClxN4UlyBpq5tSBF4IrqCzCB8s"));
	}

}
