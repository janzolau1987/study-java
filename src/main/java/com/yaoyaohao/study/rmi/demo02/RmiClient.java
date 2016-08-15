package com.yaoyaohao.study.rmi.demo02;

import java.rmi.Naming;
import java.util.List;

public class RmiClient {

	public static void main(String[] args) {
		try {
			PersonService personService = (PersonService) Naming.lookup("rmi://127.0.0.1:6600/PersonService");
			List<Person> persons = personService.findAll();
			for(Person p : persons) {
				System.out.println(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
