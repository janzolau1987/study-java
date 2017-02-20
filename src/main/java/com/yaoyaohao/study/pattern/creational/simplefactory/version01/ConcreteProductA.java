package com.yaoyaohao.study.pattern.creational.simplefactory.version01;

/**
 * 产品A
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:35:50
 */
public class ConcreteProductA extends Product {
	//实现业务方法

	@Override
	public void methodDiff() {
		//业务方法的实现
		System.out.println("ConcreteProductA.methodDiff实现");
	}

}
