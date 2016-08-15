package com.yaoyaohao.study.rmi.demo01;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 定义一个远程接口，必须继承Remote接口，其中需要远程调用的方法必须抛出RemoteException异常
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午3:23:27
 */
public interface IHello extends Remote {
	public String helloWorld() throws RemoteException;

	public String sayHelloWorldToSomeBody(String someBodyName) throws RemoteException;
}
