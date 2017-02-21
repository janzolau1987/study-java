package com.yaoyaohao.study.pattern.creational.singleton;

/**
 * 懒汉模式 --线程安全
 * 
 * @author liujianzhu
 * @date 2017年2月21日 下午6:01:43
 */
public class LazySingleton01 {
	private static LazySingleton01 instance = null;
	
	private LazySingleton01(){}
	
	synchronized public static LazySingleton01 getInstance() {
		if(instance == null) {
			instance = new LazySingleton01();
		}
		return instance;
	}
}
