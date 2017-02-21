package com.yaoyaohao.study.pattern.creational.abstractfactory.version01;

//Spring文件框类：具体产品
public class SpringTextField implements TextField {

	@Override
	public void display() {
		System.out.println("显示绿色边框文本框。");
	}

}
