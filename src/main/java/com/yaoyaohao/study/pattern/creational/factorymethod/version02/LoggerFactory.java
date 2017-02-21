package com.yaoyaohao.study.pattern.creational.factorymethod.version02;

import com.yaoyaohao.study.pattern.creational.factorymethod.version01.Logger;

public interface LoggerFactory {
	Logger createLogger();
	
	Logger createLogger(String args);
	
	Logger createLogger(Object obj);
}
