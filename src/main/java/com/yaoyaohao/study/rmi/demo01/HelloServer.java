package com.yaoyaohao.study.rmi.demo01;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 创建RMI注册表，启动RMI服务，并将远程对象注册到RMI注册表中
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午3:26:49
 */
public class HelloServer {
	public static void main(String[] args) {
		try{
			//创建一个远程对象
			IHello rhello = new HelloImpl();
			//本地主机上的远程对象注册表LodateRegistry的实现，并指定端口为8888
			LocateRegistry.createRegistry(8888);
			
			//把远程对象注册到RMI注册服务器上，并命名为RHello
			//绑定URL标准格式为: rmi://host:port/name （其中协议名可以省略）
			Naming.bind("rmi://localhost:8888/RHello", rhello);
			//Naming.bind("localhost:8888/RHello", rhello);
			
			System.out.println(">>>> INFO : 远程IHello对象绑定成功.");
		} catch (RemoteException e) {
			System.out.println("创建远程对象发生异常!");
			e.printStackTrace();
		} catch(AlreadyBoundException e) {
			System.out.println("发生重复绑定对象异常!");
			e.printStackTrace();
		} catch(MalformedURLException e) {
			System.out.println("发生URL畸形异常!");
			e.printStackTrace();
		}
	}
}
