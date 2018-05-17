package com.jjlb.common;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String code;

	public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    

    public BusinessException(String message,String code) {
		super(message);
		this.code = code;
	}

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
