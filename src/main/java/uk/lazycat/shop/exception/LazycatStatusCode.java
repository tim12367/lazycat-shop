package uk.lazycat.shop.exception;

/**
 * 自定義狀態碼
 */
public enum LazycatStatusCode {
	SUCCESS("0000", "請求成功"),
	PARAMETER_ERROR("0001", "請求參數錯誤: %s"),
	CUSTOM_ERROR("0002", "自定義錯誤訊息"),
	AUTHENTICATION_EXCEPTION("0003", "TOKEN驗證錯誤"),
	ACCESS_DENIED("0004", "TOKEN權限被拒"),
	UNKNOWN_ERROR("9999", "發生非預期錯誤，請聯絡管理員");
	
	private String code;
	private String info;

	LazycatStatusCode(String code, String info) {
		this.code = code;
		this.info = info;
	}

	public String getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}
}
