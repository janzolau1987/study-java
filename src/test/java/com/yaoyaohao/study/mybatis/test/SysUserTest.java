package com.yaoyaohao.study.mybatis.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yaoyaohao.study.mybatis.BaseTest;
import com.yaoyaohao.study.mybatis.entity.SysUser;
import com.yaoyaohao.study.mybatis.service.ISysUserService;

public class SysUserTest extends BaseTest{
	@Autowired
	ISysUserService userService;

	//@Test
	public void testInsert() {
		SysUser user = new SysUser();
		user.setName("张三");
		user.setAge(22);
		//
		boolean result = userService.insert(user);
		System.out.println("返回: " + result);
	}
	
	//@Test
	public void testUpdate() {
		SysUser user = userService.selectOne(new EntityWrapper<SysUser>().like("name", "张三", SqlLike.RIGHT));
		user.setAge(user.getAge() + 10);
		userService.updateById(user);
	}
	
	//@Test
	public void testQuery() {
		List<SysUser> userList = userService.selectList(new EntityWrapper<SysUser>().between("age", 20, 30));
		for(SysUser user : userList) {
			System.out.println(user);
		}
	}
	
	//@Test
	public void testPage() {
		Page<SysUser> page = new Page<>(1, 10);
		EntityWrapper<SysUser> wrapper = new EntityWrapper<>();
		wrapper.between("age", 10, 50);
		Page<SysUser> pageResult = userService.selectPage(page, wrapper);
		System.out.println("---------------------------------");
		System.out.println("总记录数: " + pageResult.getTotal());
		System.out.println("总页数: " + pageResult.getPages());
		System.out.println("记录:");
		for(SysUser user : pageResult.getRecords()) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testDelete() {
		userService.delete(new EntityWrapper<SysUser>().lt("age", 30));
	}

}
