package uk.lazycat.shop.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class BcryptTest {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
	public void testPasswordEncodeAndValidate() {
		String pwd = "gygvjkhsfdufhsi"; // 模擬用戶密碼
		String hashPwd = passwordEncoder.encode(pwd); // 雜湊
		assertTrue(passwordEncoder.matches(pwd, hashPwd)); // 驗證
	}

}
