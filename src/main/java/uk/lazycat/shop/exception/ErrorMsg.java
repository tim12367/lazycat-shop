package uk.lazycat.shop.exception;

import lombok.Data;

@Data
public class ErrorMsg {
	private String code;
	private String info;
}
