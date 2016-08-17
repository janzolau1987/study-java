package com.yaoyaohao.study.nio.reactor.multi;

import java.io.IOException;

/**
 * Multi 测试 -- server
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午6:45:01
 */
public class MReactorServer {
	public static void main(String[] args) {
		int port = 9999;
		try {
			new Thread(new MainReactor(port)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
