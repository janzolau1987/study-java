package com.yaoyaohao.study.pattern.creational.factorymethod.version02;

import com.yaoyaohao.study.pattern.creational.factorymethod.version01.FileLogger;
import com.yaoyaohao.study.pattern.creational.factorymethod.version01.Logger;

public class FileLoggerFactory implements LoggerFactory {

	@Override
	public Logger createLogger() {
		//使用默认方式创建文件日志记录器
		Logger logger = new FileLogger();
		//初始化
		return logger;
	}

	@Override
	public Logger createLogger(String args) {
		//使用参数args创建文件日志记录器
		Logger logger = new FileLogger();
		//初始化
		return logger;
	}

	@Override
	public Logger createLogger(Object obj) {
		//使用参数obj对象创建文件日志记录器
		Logger logger = new FileLogger();
		//初始化
		return logger;
	}

}
