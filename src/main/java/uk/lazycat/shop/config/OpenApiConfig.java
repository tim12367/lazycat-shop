package uk.lazycat.shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

//@formatter:off
@OpenAPIDefinition(
	info = @Info(
		contact = @Contact( // 聯絡方式
			name = "shop.lazycat.uk", // 公司名稱
			email = "shop.lazycat.uk", // 公司email
			url = "https://shop.lazycat.uk" // 公司網站
		),
		description = "OpenApi for lazycat shop", // 描述
		title = "lazycat shop", // 標題
		version = "1.0"//, // 版號
//		license = @License(), // 授權
//		termsOfService = "Terms of service" // 使用條款url
	),
	servers = { // 環境
		@Server(
			description = "Local env",
			url = "http://localhost:8080"
		),
		@Server(
			description = "Prod env",
			url = "https://shop.lazycat.uk"
		)
	},
	security = @SecurityRequirement(
		name = "bearerAuth" // 全域套用下方@SecurityScheme定義的安全性驗證
	)
)
@SecurityScheme( // 定義安全性驗證
	name = "bearerAuth", // 展開後大標
	scheme = "bearer", // Authorization header，參考RFC 7235
	type = SecuritySchemeType.HTTP, // 驗證方式，HTTP、OAUTH2...
	description = "Jwt auth", // 展開後詳情
	bearerFormat = "JWT", // token格式
	in = SecuritySchemeIn.HEADER // token夾帶位置
)
//@formatter:on
public class OpenApiConfig {
}
