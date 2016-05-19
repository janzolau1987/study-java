package com.yaoyaohao.study.spring;

import org.junit.Test;

public class BeanFactoryTest {

	@Test
	public void test() {
		BeanFactory beanFactory = new BeanFactory("beans.xml");
		JavaBean javaBean = beanFactory.getBean("javaBean", JavaBean.class);
		System.out.println("userName = " + javaBean.getUsername());
		System.out.println("password = " + javaBean.getPassword());
	}

}
