package net.miraeit.cmm.authentication;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.property.EgovPropertyService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import net.miraeit.cmm.exception.jwt.JwtNotFoundException;
import net.miraeit.user.model.LoginVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;


@Component
@Slf4j
public class TokenProvider {

	private static final String AUTHORITIES_KEY = "auth";
	public static final String AUTHORIZATION_HEADER = "Authorization";

	private final EgovMessageSource msgSrc;
	private final EgovPropertyService propertyService;
	private final String secret;
	private final long tokenValidityInMilliseconds;

	private Key key;


	public TokenProvider(EgovMessageSource egovMessageSource, EgovPropertyService propertyService) {
		this.msgSrc = egovMessageSource;
		this.propertyService = propertyService;
		this.secret = propertyService.getString("jwt.secret");
		this.tokenValidityInMilliseconds = (1000 * 60 * 60) * 10; // 10시간: (1초 * 60 * 60) * 10
//		this.tokenValidityInMilliseconds = propertyService.getLong("jwt.token-validity-in-seconds") * 1000;

		byte[] keyBytes = Base64.getDecoder().decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Token을 생성한다.
	 * @param loginVO
	 * @return
	 */
	public String createToken(LoginVO loginVO) {

		// expiration time
		long now = (new Date()).getTime();
		long ValidityinSecond = now + this.tokenValidityInMilliseconds;
		Date validity = new Date(ValidityinSecond);

		// build and return token
		return Jwts.builder()
				.setSubject(loginVO.getId())
				.claim(AUTHORITIES_KEY, loginVO)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();
	}

	/**
	 * JSON Web Token을 추출하여 반환한다.
	 * @param request
	 * @return
	 */
	public String extractJwt(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}


	/**
	 * JSON Web Token 유효성 검사를 진행, 오류를 발생시킨다.
	 * @param jwt
	 * @return
	 */
	public boolean validateToken(String jwt) {
		if (!StringUtils.hasText(jwt)) throw new JwtNotFoundException(msgSrc.getMessage("T004"));
		Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt); // T000 ~ T003 에러 발생할 수 있음( 예외처리: CMMExceptionHandler )
		return true;
	}

	/**
	 * JSON Web Token에서 로그인 정보를 추출하여 반환하다.
	 * @param token
	 * @return
	 */
	public LoginVO getLoginVO(String token) {
		// decode
		Claims claims = Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();

		LinkedHashMap map = (LinkedHashMap) claims.get(AUTHORITIES_KEY);

		// 권한 검증에 필요한 값 build 후 반환
		return LoginVO.builder()
				.id((String) map.get("id"))
				.authGroupId((Integer) map.get("authGroupId"))
				.build();
	}
}
