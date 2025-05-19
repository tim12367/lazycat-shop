package uk.lazycat.shop.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import uk.lazycat.shop.exception.LaztcatException;

/**
 * 登入(取refresh token)、註冊、取得access-token
 */
@RestController
@Validated
@Tag(name = "使用者權限")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@Operation(summary = "使用者註冊帳號", description = "使用者註冊帳號", responses = {
			@ApiResponse(description = "重複註冊", responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class, example = "帳號重複註冊!")) }) })
	@PostMapping("/singup")
	public void singUp(
			@NotBlank(message = "使用者不得為空") @Schema(example = "user") @RequestParam String username,
			@NotBlank(message = "密碼不得為空") @Schema(example = "dummy") @RequestParam String password) throws LaztcatException {
		authenticationService.singUp(username, password);
	}

	@Operation(summary = "使用者登入", description = "登入並回傳refresh token")
	@PostMapping("/login")
	public String login(
			@NotBlank(message = "使用者不得為空") @Schema(example = "test123") @RequestParam String username,
			@NotBlank(message = "密碼不得為空") @Schema(example = "dummy") @RequestParam String password) throws LaztcatException {
		return authenticationService.login(username, password); // 登入回傳jwt refresh token
	}

	@Operation(summary = "取得使用者access token", description = "查詢使用者並返回使用者access token", responses = {
			@ApiResponse(description = "TOKEN錯誤", responseCode = "400", content = { @Content(schema = @Schema(implementation = String.class, example = "TOKEN驗證錯誤")) }) })
	@PostMapping("/get-access-token")
	public String getAccessToken(@NotNull(message = "找不到驗證資料!") Authentication authentication) throws LaztcatException {
		return authenticationService.getAccessToken(authentication); // 登入回傳jwt refresh token
	}
}
