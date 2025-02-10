package uk.lazycat.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import uk.lazycat.shop.exception.SecurityExceptionHandler;

@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityExceptionHandler handler) throws Exception {
		// 驗證所有請求
		http.authorizeHttpRequests((requests) -> {
			requests
					.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/h2-console/**", "/*.html", "/*.js").permitAll()
					.requestMatchers("/singup", "/login").permitAll()
					.anyRequest().authenticated();
		});

		// 使用stateless不建立session
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// csrf 停用
		http.csrf(csrf -> csrf.disable());

		// <frame>啟用
		http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

		// 設定oauth2.0
		http.oauth2ResourceServer(oauth2ResourceServer -> {
			oauth2ResourceServer.jwt(Customizer.withDefaults());
			oauth2ResourceServer.authenticationEntryPoint((request, response, e) -> handler.handleAuthenticationException(request, response, e));
			oauth2ResourceServer.accessDeniedHandler((request, response, e) -> handler.handleAccessDeniedException(request, response, e));
		});

		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 預設10
	}

}