package uk.lazycat.shop.exception;

public class LaztcatException extends Exception {

	private String errorCode;
	private String errorMessage;

	public LaztcatException() {
		super();
	}

	public LaztcatException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}

	public LaztcatException(LazycatStatusCode lazyCatStatusCode) {
		super(lazyCatStatusCode.getInfo());
		this.setErrorCode(lazyCatStatusCode.getCode());
		this.setErrorMessage(lazyCatStatusCode.getInfo());
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	private static final long serialVersionUID = -4261474705575972037L;

}
