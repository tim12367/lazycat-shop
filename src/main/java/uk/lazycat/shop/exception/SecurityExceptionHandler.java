package uk.lazycat.shop.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityExceptionHandler {

	public void handleAuthenticationException(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
		log.error("TOKEN驗證錯誤 401", e);
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write("TOKEN驗證錯誤");
		response.getWriter().flush();
	}

	public void handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
		log.error("TOKEN權限被拒 403", e);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write("TOKEN權限被拒");
		response.getWriter().flush();
	}

}