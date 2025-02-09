package uk.lazycat.shop.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import uk.lazycat.shop.entity.shop.Authorities;
import uk.lazycat.shop.entity.shop.Users;
import uk.lazycat.shop.exception.LaztcatException;
import uk.lazycat.shop.exception.LazycatStatusCode;
import uk.lazycat.shop.mapper.shop.AuthoritiesMapper;
import uk.lazycat.shop.mapper.shop.UsersMapper;

@Service
@Slf4j
public class LoginService {

	@Autowired
	private UsersMapper uesrMapper;

	@Autowired
	private AuthoritiesMapper authoritiesMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 註冊
	@Transactional
	public void singUp(String username, String password) throws LaztcatException {
		if (StringUtils.isAnyBlank(username, password)) {
			throw new LaztcatException(LazycatStatusCode.CUSTOM_ERROR.getCode(), "註冊時使用者及密碼不得為空");
		}

		if (null != uesrMapper.selectByPrimaryKey(username)) {
			throw new LaztcatException(LazycatStatusCode.CUSTOM_ERROR.getCode(), "帳號重複註冊!");
		}

		// 插入用戶資料
		uesrMapper.insert(Users.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.enabled(true) // 預設帳號啟用狀態
				.build());

		// 插入用戶預設角色
		authoritiesMapper.insert(Authorities.builder()
				.username(username)
				.authority("USER") // 預設角色
				.build());
	}
}
