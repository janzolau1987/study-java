package com.yaoyaohao.study.pattern.creational.simplefactory.version01;

/**
 * 测试使用
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:39:29
 */
public class Client {

	public static void main(String[] args) {
		Product product = Factory.getProduct("A");
		product.methodSame();
		product.methodDiff();
	}

}
