package com.yaoyaohao.study.pattern.creational.abstractfactory.version01;

//Summer组合框类：具体产品
public class SummerComboBox implements ComboBox {

	@Override
	public void display() {
		System.out.println("显示蓝色边框组合框。");
	}

}
