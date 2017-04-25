package com.yaoyaohao.study.mybatis;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring/spring.xml",
		"classpath:spring/spring-mybatis.xml"})
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

}