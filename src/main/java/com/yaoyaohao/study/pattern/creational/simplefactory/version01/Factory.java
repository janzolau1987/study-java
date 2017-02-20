package com.yaoyaohao.study.pattern.creational.simplefactory.version01;

/**
 * 工厂类
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:37:36
 */
public class Factory {
	//静态工厂方法
	public static Product getProduct(String arg) {
		Product product = null;
		if(arg.equalsIgnoreCase("A")) {
			product = new ConcreteProductA();
			//初始化设置product
		}
		else if(arg.equalsIgnoreCase("B")) {
			product = new ConcreteProductB();
			//初始化设置product
		}
		return product;
	}
}
