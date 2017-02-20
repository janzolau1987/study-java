package com.yaoyaohao.study.pattern.creational.simplefactory.version01;

/**
 * 抽象的产品类
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:34:05
 */
public abstract class Product {
	//所有产品类的公共业务方法
	public void methodSame() {
		System.out.println("Product.methodSame方法执行.");
		//公共方法的实现
	}
	
	//声明抽象业务方法
	public abstract void methodDiff();
}
