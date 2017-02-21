package com.yaoyaohao.study.pattern.creational.singleton;

/**
 * 饿汉模式
 * 
 * @author liujianzhu
 * @date 2017年2月21日 下午6:00:19
 */
public class EagerSingleton {
	private static final EagerSingleton instance = new EagerSingleton();
	
	private EagerSingleton(){}
	
	public static EagerSingleton getInstance() {
		return instance;
	}
}
