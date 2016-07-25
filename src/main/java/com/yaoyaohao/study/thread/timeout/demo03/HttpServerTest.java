package com.yaoyaohao.study.thread.timeout.demo03;

/**
 * 测试
 * 
 * @author liujianzhu
 * @date 2016年7月25日 上午10:47:43
 */
public class HttpServerTest {

	public static void main(String[] args) throws Exception {
		String basePath = HttpServerTest.class.getResource("").getPath();
		//
		SimpleHttpServer.setBasePath(basePath);
		SimpleHttpServer.start();
	}

}
