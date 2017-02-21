package com.yaoyaohao.study.pattern.creational.factorymethod.version01;

public class DatabaseLogger implements Logger {

	@Override
	public void writeLog() {
		System.out.println("数据库日志记录.");
	}

}
