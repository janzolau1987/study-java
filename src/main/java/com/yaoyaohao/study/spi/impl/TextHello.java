package com.yaoyaohao.study.spi.impl;

import com.yaoyaohao.study.spi.IHello;

public class TextHello implements IHello {

	@Override
	public void sayHello() {
		System.out.println("Text Hello.");
	}

}
