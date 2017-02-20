package com.yaoyaohao.study.pattern.creational.simplefactory.version01;

/**
 * 产品B 
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:36:56
 */
public class ConcreteProductB extends Product {

	@Override
	public void methodDiff() {
		//业务方法的实现
		System.out.println("ConcreteProductB.methodDiff实现");
	}

}
