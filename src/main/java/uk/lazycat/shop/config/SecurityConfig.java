package uk.lazycat.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import uk.lazycat.shop.exception.SecurityExceptionHandler;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityExceptionHandler handler) throws Exception {
		// @formatter:off
		// 驗證所有請求
		http.authorizeHttpRequests((requests) -> {
			requests
					.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/h2-console/**").permitAll() // test
					.requestMatchers("/*.html", "/*.js", "/*.ico").permitAll() // resource
					.requestMatchers("/singup", "/login").permitAll() // 使用者驗證相關
					.requestMatchers("/get-access-token").hasRole("REFRESH") // 取access token
					.requestMatchers("/user/**").hasRole("USER") // user可以使用的區域
					.requestMatchers("/admin/**").hasRole("ADMIN") // admin可以使用的區域
					.anyRequest().authenticated();
		});
		// @formatter:on

		// 使用stateless不建立session
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// csrf 停用
		http.csrf(csrf -> csrf.disable());

		// <frame>啟用
		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

		// @formatter:off
		// 設定oauth2.0
		http.oauth2ResourceServer(oauth2ResourceServer -> {
			oauth2ResourceServer.jwt(Customizer.withDefaults());
			oauth2ResourceServer.authenticationEntryPoint((request, response, e) -> handler.handleAuthenticationException(request, response, e));
			oauth2ResourceServer.accessDeniedHandler((request, response, e) -> handler.handleAccessDeniedException(request, response, e));
		});
		// @formatter:on

		return http.build();
	}

	/**
	 * 密碼加解密bean
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11); // 預設10，一般情況下依照雜湊or驗證效能100-250ms之間
	}

}