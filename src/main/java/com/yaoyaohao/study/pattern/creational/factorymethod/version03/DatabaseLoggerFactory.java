package com.yaoyaohao.study.pattern.creational.factorymethod.version03;

import com.yaoyaohao.study.pattern.creational.factorymethod.version01.DatabaseLogger;
import com.yaoyaohao.study.pattern.creational.factorymethod.version01.Logger;

public class DatabaseLoggerFactory extends LoggerFactory {

	@Override
	protected Logger createLogger() {
		//使用默认方式创建数据库连接日志记录器
		Logger logger = new DatabaseLogger();
		//初始化
		return logger;
	}
	
}
