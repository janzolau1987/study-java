package com.yaoyaohao.study.pattern.creational.abstractfactory.version01;

//Spring组合框类：具体产品
public class SpringComboBox implements ComboBox {

	@Override
	public void display() {
		System.out.println("显示绿色边框组合框。");
	}

}
