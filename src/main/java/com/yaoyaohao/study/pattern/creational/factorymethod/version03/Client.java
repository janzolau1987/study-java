package com.yaoyaohao.study.pattern.creational.factorymethod.version03;

public class Client {
	public static void main(String[] args) {
		LoggerFactory factory = new DatabaseLoggerFactory();
		factory.writeLog();
	}
}
