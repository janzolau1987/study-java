package com.yaoyaohao.study.pattern.creational.factorymethod.version01;

/**
 * 文件日志记录器工厂类：具体工厂
 * 
 * @author liujianzhu
 * @date 2017年2月21日 上午11:57:09
 */
public class FileLoggerFactory implements LoggerFactory {

	@Override
	public Logger createLogger() {
		//创建文件日志记录器对象
		Logger logger = new FileLogger();
		//创建文件，代码省略
		return logger;
	}

}
