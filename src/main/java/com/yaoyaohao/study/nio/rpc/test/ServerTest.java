package com.yaoyaohao.study.nio.rpc.test;

import java.io.IOException;

import com.yaoyaohao.study.nio.rpc.RpcServer;

/**
 * 调用RpcService，启动服务
 * 
 * @author liujianzhu
 * @date 2016年6月21日 下午5:05:07
 *
 */
public class ServerTest {

	public static void main(String[] args) {
		RpcServer rpcServer = null;
		try {
			rpcServer = new RpcServer();
			rpcServer.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rpcServer != null)
					rpcServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
