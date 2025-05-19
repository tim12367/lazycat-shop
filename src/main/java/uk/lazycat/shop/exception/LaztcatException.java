package uk.lazycat.shop.exception;

public class LaztcatException extends RuntimeException {

	public LaztcatException() {
		super();
	}

	public LaztcatException(String errorMessage) {
		super(errorMessage);
	}

	private static final long serialVersionUID = -4261474705575972037L;

}
