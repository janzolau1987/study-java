package com.yaoyaohao.study.rmi.demo02;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 定义远程接口
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午4:52:27
 */
public interface PersonService extends Remote {
	public List<Person> findAll() throws RemoteException;
}
