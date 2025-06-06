package uk.lazycat.shop.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import uk.lazycat.shop.Authentication.request.LoginRequest;
import uk.lazycat.shop.Authentication.request.SignupRequest;
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
	public void singUp(@RequestBody SignupRequest signupRequest) throws LaztcatException {
		authenticationService.singUp(signupRequest.getUsername(), signupRequest.getPassword());
	}

	@Operation(summary = "使用者登入", description = "登入並回傳refresh token")
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) throws LaztcatException {
		return authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword()); // 登入回傳jwt refresh token
	}

	@Operation(summary = "取得使用者access token", description = "查詢使用者並返回使用者access token", responses = {
			@ApiResponse(description = "取得token成功", responseCode = "200", content = { @Content(schema = @Schema(implementation = String.class, example = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJsYXp5Y2F0LnVrIiwic3ViIjoidGVzdDEyMyIsImV4cCI6MTc0NzYzODg1OSwiaWF0IjoxNzQ3NjM3MDU5LCJzY29wZSI6IlJPTEVfVVNFUiJ9.hYW5mpRgFMxQsaWbXIRE_318Jd5uE_MOshtQVLm-A_YthcpzgwCWaWjIiMrPTbssicI5SAQJJO8quv3UNRgGg9v1_wU7v-aX_Hw8pFk76oVJLve45EOZtcuUORxyfVbe88DYGCTCqUeSiPaxa6frcCF4fMtzig91tShd-v4HeAw9C_j1PT_LOp_52t3HRORWU1FVEvhF4_4cA6iTSb1wRhvJClCYPlfyf6k9yHQNrQGhhGhtUvoilG3LAzNy9xMgByjY0VGJrljw6VjisueadqbzcaeLaN8fIUe18PC-BRpNmXYBbWKNcwQKKIC8jipYRvxI8Ha2ifNwrTAr-w7JcQ")) }),
			@ApiResponse(description = "TOKEN錯誤", responseCode = "401", content = { @Content(schema = @Schema(implementation = String.class, example = "TOKEN驗證錯誤")) }),
			@ApiResponse(description = "TOKEN權限錯誤", responseCode = "403", content = { @Content(schema = @Schema(implementation = String.class, example = "TOKEN權限被拒")) }) })
	@PostMapping("/get-access-token")
	public String getAccessToken(@NotNull(message = "找不到驗證資料!") Authentication authentication) throws LaztcatException {
		return authenticationService.getAccessToken(authentication); // 登入回傳jwt refresh token
	}
}
