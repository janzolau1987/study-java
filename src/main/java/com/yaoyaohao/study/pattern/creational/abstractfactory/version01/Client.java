package com.yaoyaohao.study.pattern.creational.abstractfactory.version01;

public class Client {
	public static void main(String[] args) {
		SkinFactory factory = new SpringSkinFactory();
		//
		Button bt = factory.createButton();
		TextField tf = factory.createTextField();
		ComboBox cb = factory.createComboBox();
		//
		bt.display();
		tf.display();
		cb.display();
	}
}
