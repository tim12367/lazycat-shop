package uk.lazycat.shop.exception;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SecurityExceptionHandler {

	@Autowired
	private ObjectMapper objectMapper;

	public void handleAuthenticationException(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
		log.error("TOKEN驗證錯誤 401", e);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(getErrorMsg(LazycatStatusCode.AUTHENTICATION_EXCEPTION));
		response.getWriter().flush();
	}

	public void handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
		log.error("TOKEN權限被拒 403", e);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(getErrorMsg(LazycatStatusCode.ACCESS_DENIED));
		response.getWriter().flush();
	}

	/*
	 * 取得回應字串
	 */
	private String getErrorMsg(LazycatStatusCode lazycatStatusCode) {
		ErrorMsg errorMsg = new ErrorMsg();
		errorMsg.setCode(lazycatStatusCode.getCode());
		errorMsg.setInfo(lazycatStatusCode.getInfo());

		String message = "";
		try {
			return objectMapper.writeValueAsString(errorMsg);
		} catch (JsonProcessingException e) {
			log.error("錯誤訊息轉換發生錯誤!", e);
		}

		return message;
	}
}