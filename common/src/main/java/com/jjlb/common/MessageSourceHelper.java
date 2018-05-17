package com.jjlb.common;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageSourceHelper {
	
	private static ResourceBundleMessageSource messageSource;

	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		if(locale == null){
			locale = new Locale("zh");
		}
		String msg = messageSource.getMessage(code, args, defaultMessage,locale);
		
		return msg != null ? msg.trim() : msg;
	}
	public static String getMessage(String code) {
		String msg = getMessage(code,null,"",null);
		return msg != null ? msg.trim() : msg;
	}
	
	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		MessageSourceHelper.messageSource = messageSource;
	}
	
}
