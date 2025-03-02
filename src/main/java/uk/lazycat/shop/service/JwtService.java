package uk.lazycat.shop.service;

import java.time.Instant;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import uk.lazycat.shop.entity.shop.Authorities;
import uk.lazycat.shop.exception.LaztcatException;
import uk.lazycat.shop.mapper.shop.AuthoritiesMapper;

@Service
public class JwtService {

	@Autowired
	private JwtEncoder jwtEncoder;

	@Autowired
	private AuthoritiesMapper authoritiesMapper;

	/**
	 * 簽發Jwt token
	 * 
	 * @param username      使用者
	 * @param roles         存取角色(複數)
	 * @param expTimeSecond 過期時間(秒)
	 * @return jwt token
	 */
	public String getJwtToken(String username, List<String> roles, Long expTimeSecond) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("lazycat.uk")
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expTimeSecond))
				.subject(username)
				.claim("scope", String.join(",", roles))
				.build();

		JwtEncoderParameters parameter = JwtEncoderParameters.from(claims);
		return jwtEncoder.encode(parameter).getTokenValue();
	}

	/**
	 * 簽發 jwt access token
	 * 
	 * @param authentication
	 * @return jwt access token
	 * @throws LaztcatException
	 */
	public String getJwtAccessToken(Authentication authentication) throws LaztcatException {
		final String username = authentication.getName();
		if (StringUtils.isBlank(username)) {
			throw new LaztcatException("getJwtAccessToken發生錯誤! 驗證內容不含使用者名稱! ");
		}

		// 查出使用者所含角色
		List<Authorities> authorities = authoritiesMapper.selectByUsername(username);

		// 簽發access token
		return this.getJwtToken(username,
				authorities.stream().map(Authorities::getAuthority).map(authoritie -> "ROLE_" + authoritie).toList(),
				60L * 30L);
	}
}
