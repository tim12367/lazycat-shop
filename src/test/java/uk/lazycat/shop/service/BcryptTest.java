package uk.lazycat.shop.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BcryptTest {

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 測試密碼雜湊及驗證速度 100~250ms
	 * 若速度太快或太慢請調整設定檔，
	 * 設定檔位置: 
	 * {@link uk.lazycat.shop.config.SecurityConfig#passwordEncoder()}
	 */
	@Test
	public void testPasswordEncodeAndValidateSpeed() {
		String pwd = "gygvjkhsfdufhsi"; // 模擬用戶密碼

		Instant start1 = Instant.now();
		String hashPwd = passwordEncoder.encode(pwd); // 雜湊
		Instant end1 = Instant.now();
		long duration1 = Duration.between(start1, end1).toMillis();
		log.info("雜湊速度: {}ms", duration1);
		assertTrue(duration1 > 100 && duration1 < 250, "雜湊速度需介於100ms~250ms");

		Instant start2 = Instant.now();
		boolean matches = passwordEncoder.matches(pwd, hashPwd);
		Instant end2 = Instant.now();
		long duration2 = Duration.between(start2, end2).toMillis();
		log.info("驗證速度: {}ms", duration2);
		assertTrue(duration2 > 100 && duration2 < 250, "驗證速度需介於100ms~250ms");
		assertTrue(matches, "加解密驗證結果需一致"); // 驗證
	}

}
