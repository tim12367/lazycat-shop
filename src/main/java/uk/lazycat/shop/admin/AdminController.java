package uk.lazycat.shop.admin;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import uk.lazycat.shop.entity.shop.UsersAndRoles;
import uk.lazycat.shop.exception.LaztcatException;

@RestController
@Validated
@Tag(name = "後台管理")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Operation(summary = "管理員查看所有用戶", description = "使用者註冊帳號")
	@GetMapping("/users")
	public List<UsersAndRoles> getUsers() throws LaztcatException {
		return adminService.getUsers();
	}

	@Operation(summary = "管理員查看用戶", description = "使用者註冊帳號")
	@GetMapping("/users/{id}")
	public UsersAndRoles getUser(@PathVariable @Min(1) BigInteger id) throws LaztcatException {
		return adminService.getUser(id);
	}
}
