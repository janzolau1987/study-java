package com.yaoyaohao.study.pattern.creational.factorymethod.version01;

/**
 * 数据库日志记录器工厂类：具体工厂
 * 
 * @author liujianzhu
 * @date 2017年2月21日 上午11:20:01
 */
public class DatabaseLoggerFactory implements LoggerFactory {

	@Override
	public Logger createLogger() {
		// 连接数据库，代码省略
		// 创建数据库日志记录器对象
		Logger logger = new DatabaseLogger();
		//初始化数据库日志记录器，代码省略
		return logger;
	}

}
