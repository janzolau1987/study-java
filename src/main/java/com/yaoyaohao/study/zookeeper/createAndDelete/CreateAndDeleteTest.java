package com.yaoyaohao.study.zookeeper.createAndDelete;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试类
 * 
 * @author liujianzhu
 * @date 2016年9月27日 下午5:19:42
 */
public class CreateAndDeleteTest {
	private static final String HOSTS = "172.16.10.1:2181";
	private static final String groupName = "zoo";
	
	private CreateAndDeleteGroup cadGroup;
	
	@Before
	public void init() throws Exception {
		cadGroup = new CreateAndDeleteGroup();
		cadGroup.connect(HOSTS);
	}
	
	@Test
	public void testCreateAndDelete() throws Exception {
		cadGroup.create(groupName);
		cadGroup.delete(groupName);
		//
		cadGroup.exists(groupName);
	}
	
	@After
	public void destory() throws Exception {
		cadGroup.close();
	}
}
