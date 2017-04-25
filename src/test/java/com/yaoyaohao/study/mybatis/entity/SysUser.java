package com.yaoyaohao.study.mybatis.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author liujianzhu
 * @since 2017-04-25
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
	private Long id;
    /**
     * 用户名
     */
	private String name;
    /**
     * 用户年龄
     */
	private Integer age;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "SysUser [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
}
