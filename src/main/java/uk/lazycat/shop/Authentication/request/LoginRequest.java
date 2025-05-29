package uk.lazycat.shop.Authentication.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginRequest {
	
	@NotBlank(message = "使用者不得為空")
	@Schema(example = "test123")
	private String username;

	@NotBlank(message = "密碼不得為空")
	@Schema(example = "dummy")
	private String password;

}
