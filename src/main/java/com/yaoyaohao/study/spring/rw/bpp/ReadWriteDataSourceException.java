package com.yaoyaohao.study.spring.rw.bpp;

import org.springframework.core.NestedRuntimeException;

/**
 * 读写分离数据源操作异常类
 * 
 */
@SuppressWarnings("serial")
public class ReadWriteDataSourceException extends NestedRuntimeException {
	public ReadWriteDataSourceException(String message, Throwable cause) {
		super(message, cause);
	}
}
