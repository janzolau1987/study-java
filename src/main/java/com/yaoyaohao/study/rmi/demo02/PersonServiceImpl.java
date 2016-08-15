package com.yaoyaohao.study.rmi.demo02;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 远程接口实现类
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午4:53:49
 */
public class PersonServiceImpl extends UnicastRemoteObject implements PersonService {

	private static final long serialVersionUID = -5757438369783466559L;

	protected PersonServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public List<Person> findAll() throws RemoteException {
		System.out.println("find all person start ...");
		List<Person> persons = new ArrayList<>();
		for (int i = 1; i < 10; i++) {
			Person p = new Person();
			p.setId(i);
			p.setName("用户" + i);
			p.setAge((i % 2) == 0 ? i + 20 : i + 18);
			persons.add(p);
		}
		return persons;
	}

}
