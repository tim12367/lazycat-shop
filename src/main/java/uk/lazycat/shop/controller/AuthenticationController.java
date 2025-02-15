package uk.lazycat.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.lazycat.shop.exception.LaztcatException;
import uk.lazycat.shop.service.AuthenticationService;

/**
 * 登入(取refresh token)、註冊、取得access-token
 */
@RestController
@Validated
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/singup")
	public void singUp(
			@NotBlank(message = "使用者不得為空") @Schema(example = "user") @RequestParam String username,
			@NotBlank(message = "密碼不得為空") @Schema(example = "dummy") @RequestParam String password) throws LaztcatException {
		authenticationService.singUp(username, password);
	}

	@PostMapping("/login")
	public String login(
			@NotBlank(message = "使用者不得為空") @Schema(example = "test123") @RequestParam String username,
			@NotBlank(message = "密碼不得為空") @Schema(example = "dummy") @RequestParam String password) throws LaztcatException {
		return authenticationService.login(username, password); // 登入回傳jwt refresh token
	}

	@PostMapping("/get-access-token")
	public String getAccessToken(@NotNull(message = "找不到驗證資料!") Authentication authentication) throws LaztcatException {
		return authenticationService.getAccessToken(authentication); // 登入回傳jwt refresh token
	}
}
