package uk.lazycat.shop.exception;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * 錯誤處理
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

	@Autowired
	private ObjectMapper objectMapper;

	// 異常處理 object參數檢核錯誤 (Valid)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String returnDesc = "";
		// 將欄位錯誤訊息回傳
		try {
			returnDesc += objectMapper.writeValueAsString(e.getBindingResult()
					.getFieldErrors()
					.stream()
					.map(fieldError -> fieldError.getDefaultMessage())
					.collect(Collectors.toList()));
		} catch (Exception e2) {
			returnDesc += "欄位檢核訊息回傳失敗!";
		}

		return this.proccessErrorMsgResponseEntity("請求參數錯誤: %s".formatted(returnDesc), e, "請求參數錯誤");
	}

	// 異常處理 bean參數檢核錯誤(Validated)
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleMethodBeanNotValid(ConstraintViolationException e) {

		String returnDesc = "";
		// 將欄位錯誤訊息回傳
		try {
			returnDesc += objectMapper.writeValueAsString(e.getConstraintViolations()
					.stream()
					.map(fieldError -> fieldError.getMessage())
					.collect(Collectors.toList()));
		} catch (Exception e2) {
			returnDesc += "欄位檢核訊息回傳失敗!";
		}
		return this.proccessErrorMsgResponseEntity("請求參數錯誤: %s".formatted(returnDesc), e, "客製化的錯誤");
	}

	/**
	 * 處理非預期錯誤。
	 *
	 * @param e 非預期錯誤
	 * @return 錯誤回應
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception e) {
		return this.proccessErrorMsgResponseEntity("非預期的錯誤", e, "非預期的錯誤");
	}

	/**
	 * 自定義錯誤拋出
	 */
	@ExceptionHandler(LaztcatException.class)
	public ResponseEntity<Object> handle(LaztcatException e) {
		String errorMsg = e.getMessage();
		return this.proccessErrorMsgResponseEntity(errorMsg, e, "客製化的錯誤");
	}

	/** 
	 * 錯誤訊息處理 + LOG
	 */
	private ResponseEntity<Object> proccessErrorMsgResponseEntity(String errorMsg, Exception e, String logMsgTitle) {
		if (StringUtils.isBlank(logMsgTitle)) {
			logMsgTitle = "發生錯誤";
		}
		log.error("%s: [%s] %s".formatted(logMsgTitle, errorMsg, e.getMessage()), e);

		return ResponseEntity
				.badRequest()
				.header("Content-type", "text/plain;charset=UTF-8")
				.body(errorMsg);
	}

}
