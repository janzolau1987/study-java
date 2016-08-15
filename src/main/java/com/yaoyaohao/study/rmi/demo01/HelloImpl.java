package com.yaoyaohao.study.rmi.demo01;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 远程接口的实现
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午3:25:58
 */
public class HelloImpl extends UnicastRemoteObject implements IHello {
	private static final long serialVersionUID = -4212632357586163478L;

	protected HelloImpl() throws RemoteException {
	}

	@Override
	public String helloWorld() throws RemoteException {
		return "Hello World";
	}

	@Override
	public String sayHelloWorldToSomeBody(String someBodyName) throws RemoteException {
		return "你好," + someBodyName + "!";
	}

}
