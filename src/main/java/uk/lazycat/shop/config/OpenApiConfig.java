package uk.lazycat.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	private static final String BEARER_AUTH_METHOD = "bearerAuth";

	@Bean
	OpenAPI customOpenAPI() {

		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_METHOD))
				.info(new Info().title("shop.lazycat.uk"))
				.components(new Components().addSecuritySchemes(BEARER_AUTH_METHOD,
						new SecurityScheme().name(BEARER_AUTH_METHOD)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")));

	}

}
