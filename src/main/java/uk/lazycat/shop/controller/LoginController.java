package uk.lazycat.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotBlank;
import uk.lazycat.shop.exception.LaztcatException;
import uk.lazycat.shop.service.LoginService;

@RestController
@Validated
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/singup")
	public void singUp(
			@NotBlank(message = "使用者不得為空") @RequestParam String username,
			@NotBlank(message = "密碼不得為空") @RequestParam String password) throws LaztcatException {
		loginService.singUp(username, password);
	}
}
