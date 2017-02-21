package com.yaoyaohao.study.pattern.creational.factorymethod.version01;

/**
 * 文件日志记录器：具体产品
 * 
 * @author liujianzhu
 * @date 2017年2月21日 上午11:18:33
 */
public class FileLogger implements Logger {

	@Override
	public void writeLog() {
		System.out.println("文件日志记录.");
	}

}
