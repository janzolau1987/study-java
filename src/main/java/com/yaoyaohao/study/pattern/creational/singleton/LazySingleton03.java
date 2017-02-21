package com.yaoyaohao.study.pattern.creational.singleton;

/**
 * 懒汉模式 -- 线程安全
 * 
 * 基于Initialization Demand Holder技术
 * 
 * @author liujianzhu
 * @date 2017年2月21日 下午6:07:20
 */
public class LazySingleton03 {
	
	private LazySingleton03() {}
	
	private static class HolderClass {
		private final static LazySingleton03 instance = new LazySingleton03();
	}
	
	public static LazySingleton03 getInstance() {
		return HolderClass.instance;
	}

}
