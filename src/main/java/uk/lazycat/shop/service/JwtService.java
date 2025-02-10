package uk.lazycat.shop.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	@Autowired
	private JwtEncoder jwtEncoder;

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
}
