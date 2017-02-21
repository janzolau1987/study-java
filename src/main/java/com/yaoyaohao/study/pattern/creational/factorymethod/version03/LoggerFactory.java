package com.yaoyaohao.study.pattern.creational.factorymethod.version03;

import com.yaoyaohao.study.pattern.creational.factorymethod.version01.Logger;

public abstract class LoggerFactory {
	//在工厂类中直接调用日志记录器类的业务方法writeLog()
	public void writeLog() {
		Logger logger = this.createLogger();
		logger.writeLog();
	}
	
	protected abstract Logger createLogger();
}
