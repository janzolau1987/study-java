package com.yaoyaohao.study.nio.reactor.single;

import java.io.IOException;

/**
 * 测试server
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午4:44:35
 */
public class SingleReactorServer {
	public static void main(String[] args) {
		int port = 9999;
		try {
			new Thread(new Reactor(port)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
