package com.yaoyaohao.study.nio.rpc.test;

public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello " + name;
	}
}
