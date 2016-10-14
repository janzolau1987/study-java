package com.yaoyaohao.study.zookeeper.usecase.session;

import java.io.Serializable;

/**
 * 基于zookeeper实现分布式session
 * 
 * SessionMetaData类表示一个Session实例的元数据，保存一些与session生命周期控制相关的数据
 * 
 * 属性说明->
 * 	id		：session实例ID
 *	maxIdle	：session的最大空闲时间，默认情况下是30分钟
 *	lastAccessTm：session的最后一次访问时间，每次调用Request.getSession方法时都会更新此值。用来计算当前session是否超时。
 *				如果lastAccessTm+maxIdle小于System.currentTimeMillis()，就表示当前session超时.
 *	validate：表示当前session是否可用，如果超时，则此属性为false
 *	version	：此属性是为了冗余Znode的version值，用来实现乐观锁，对session节点的元数据进行更新操作。
 * @author liujianzhu
 * @date 2016年10月10日 下午2:39:05
 */
public class SessionMetaData implements Serializable {
	private static final long serialVersionUID = 2807835364286292206L;

	private String id;
	private Long createTm; // session的创建时间
	private Long maxIdle; // session的最大空闲时间
	private Long lastAccessTm; // session的最后一次访问时间
	private boolean validate = false; // 是否可用
	private int version = 0;

	public SessionMetaData() {
		this.createTm = System.currentTimeMillis();
		this.lastAccessTm = createTm;
		this.validate = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Long createTm) {
		this.createTm = createTm;
	}

	public Long getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Long maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Long getLastAccessTm() {
		return lastAccessTm;
	}

	public void setLastAccessTm(Long lastAccessTm) {
		this.lastAccessTm = lastAccessTm;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
