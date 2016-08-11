package com.yaoyaohao.study.spi.impl;

import com.yaoyaohao.study.spi.IHello;

public class ImageHello implements IHello {
	@Override
	public void sayHello() {
		System.out.println("Image Hello.");
	}
}
