package uk.lazycat.shop.exception;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
		LazycatStatusCode lazyCatStatusCode = LazycatStatusCode.PARAMETER_ERROR;

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

		return this.proccessErrorMsgResponseEntity(lazyCatStatusCode.getCode(), lazyCatStatusCode.getInfo().formatted(returnDesc), e, "客製化的錯誤");
	}

	// 異常處理 bean參數檢核錯誤(Validated)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 預設拋出500
	protected ResponseEntity<Object> handleMethodBeanNotValid(ConstraintViolationException e) {
		LazycatStatusCode lazyCatStatusCode = LazycatStatusCode.PARAMETER_ERROR;

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
		return this.proccessErrorMsgResponseEntity(lazyCatStatusCode.getCode(), lazyCatStatusCode.getInfo().formatted(returnDesc), e, "客製化的錯誤");
	}

	/**
	 * 處理非預期錯誤。
	 *
	 * @param e 非預期錯誤
	 * @return 錯誤回應
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception e) {
		LazycatStatusCode lazyCatStatusCode = LazycatStatusCode.UNKNOWN_ERROR;
		return this.proccessErrorMsgResponseEntity(lazyCatStatusCode, e, "非預期的錯誤");
	}

	/**
	 * 自定義錯誤拋出
	 */
	@ExceptionHandler(LaztcatException.class)
	public ResponseEntity<Object> handle(LaztcatException e) {
		String statusCode = e.getErrorCode();
		String errorMsg = e.getErrorMessage();
		return this.proccessErrorMsgResponseEntity(statusCode, errorMsg, e, "客製化的錯誤");
	}

	/** 
	 * 錯誤訊息處理 + LOG
	 */
	private ResponseEntity<Object> proccessErrorMsgResponseEntity(LazycatStatusCode statusCode, Exception e, String logMsgTitle) {
		return this.proccessErrorMsgResponseEntity(statusCode.getCode(), statusCode.getInfo(), e, logMsgTitle);
	}

	/** 
	 * 錯誤訊息處理 + LOG
	 */
	private ResponseEntity<Object> proccessErrorMsgResponseEntity(String code, String errorMsg, Exception e, String logMsgTitle) {
		if (StringUtils.isBlank(logMsgTitle)) {
			logMsgTitle = "發生錯誤";
		}
		log.error("%s: [%s] %s".formatted(logMsgTitle, code, errorMsg), e);
		return this.proccessErrorMsgResponseEntity(code, errorMsg);
	}

	/**
	 * 錯誤訊息處理
	 */
	private ResponseEntity<Object> proccessErrorMsgResponseEntity(String code, String info) {
		ErrorMsg errorMsg = new ErrorMsg();
		errorMsg.setCode(code);
		errorMsg.setInfo(info);
		return new ResponseEntity<Object>(errorMsg, HttpStatus.OK);
	}
}
