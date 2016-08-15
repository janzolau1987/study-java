package com.yaoyaohao.study.rmi.demo01;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 客户端测试，在客户端调用远程对象上的远程方法，并返回结果
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午3:43:18
 */
public class HelloClient {
	public static void main(String[] args) {
		try {
			//
			IHello rhello = (IHello) Naming.lookup("rmi://localhost:8888/RHello");
			System.out.println(rhello.helloWorld());
			System.out.println(rhello.sayHelloWorldToSomeBody("张三"));
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
