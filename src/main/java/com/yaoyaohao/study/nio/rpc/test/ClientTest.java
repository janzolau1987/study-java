package com.yaoyaohao.study.nio.rpc.test;

import com.yaoyaohao.study.nio.rpc.RpcClient;

public class ClientTest {
	public static void main(String[] args) throws Exception{
		HelloService helloService = RpcClient.refer(HelloService.class, "localhost", 10000);
		if(helloService != null) {
			String result =  helloService.hello("simple-RPC");
			System.out.println(result);
		}
	}
}
