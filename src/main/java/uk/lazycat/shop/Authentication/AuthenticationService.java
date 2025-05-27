package uk.lazycat.shop.Authentication;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import uk.lazycat.shop.entity.shop.Authorities;
import uk.lazycat.shop.entity.shop.Users;
import uk.lazycat.shop.exception.LaztcatException;
import uk.lazycat.shop.mapper.shop.AuthoritiesMapper;
import uk.lazycat.shop.mapper.shop.UsersMapper;
import uk.lazycat.shop.util.jwt.JwtUtil;

@Service
@Slf4j
public class AuthenticationService {

	@Autowired
	private UsersMapper uesrMapper;

	@Autowired
	private AuthoritiesMapper authoritiesMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 註冊
	 * 
	 * @param username 帳號
	 * @param password 密碼
	 * @throws LaztcatException
	 */
	@Transactional
	public void singUp(String username, String password) throws LaztcatException {
		if (StringUtils.isAnyBlank(username, password)) {
			throw new LaztcatException("註冊時使用者及密碼不得為空");
		}

		if (null != uesrMapper.selectByUserName(username)) {
			throw new LaztcatException("帳號重複註冊!");
		}

		// 查出最大UID
		BigInteger maxUserId = uesrMapper.selectMaxUserId();
		BigInteger userId = maxUserId.add(BigInteger.ONE);

		// 插入用戶資料
		uesrMapper.insert(Users.builder()
				.userId(userId)
				.username(username)
				.password(passwordEncoder.encode(password)) // Hash存入密碼
				.enabled(true) // 預設帳號啟用狀態
				.build());

		// 插入用戶預設角色
		authoritiesMapper.insert(Authorities.builder()
				.userId(userId)
				.authority("USER") // 預設角色
				.build());
	}

	/**
	 * 登入
	 * 
	 * @param username 帳號
	 * @param password 密碼
	 * @return jwt refresh token
	 * @throws LaztcatException
	 */
	public String login(String username, String password) throws LaztcatException {
		if (StringUtils.isAnyBlank(username, password)) {
			throw new LaztcatException("登入時使用者及密碼不得為空");
		}

		Users user = uesrMapper.selectByUserName(username); // 查出使用者

		// 查無使用者
		if (null == user) {
			log.debug("使用者尚未註冊!" + username);
			throw new LaztcatException("帳號或密碼錯誤!");
		}

		// 密碼錯誤
		if (!passwordEncoder.matches(password, user.getPassword())) {
			log.debug(passwordEncoder.encode(password));
			log.debug(user.getPassword());
			log.debug("使用者密碼錯誤!" + username);
			throw new LaztcatException("帳號或密碼錯誤!");
		}

		// 簽發JWT refresh token
		return jwtUtil.getJwtToken(user.getUserId().toString(), List.of("ROLE_REFRESH"), 365L * 24L * 60L * 60L); // jwt refresh token
	}

	/**
	 * 使用 refresh token 取得 access token
	 * 
	 * @param authentication
	 * @return jwt access token
	 * @throws LaztcatException
	 */
	public String getAccessToken(Authentication authentication) throws LaztcatException {
		return jwtUtil.getJwtAccessToken(authentication);
	}
}
