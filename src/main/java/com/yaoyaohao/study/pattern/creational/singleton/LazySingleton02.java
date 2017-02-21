package com.yaoyaohao.study.pattern.creational.singleton;

/**
 * 懒汉模式2 -- 线程安全
 * 
 * 	双重检查锁
 * 
 * 	该代码只能在JDK 1.5及以上版本中才能正确执行
 * 
 * @author liujianzhu
 * @date 2017年2月21日 下午6:04:00
 */
public class LazySingleton02 {
	private volatile static LazySingleton02 instance = null;
	
	private LazySingleton02(){}
	
	public static LazySingleton02 getInstance() {
		//第一重判断
		if(instance == null) {
			//锁定代码块
			synchronized (LazySingleton02.class) {
				//第二重判断
				if(instance == null) {
					instance = new LazySingleton02();
				}
			}
		}
		return instance;
	}
}
