package com.yaoyaohao.study.zookeeper.session;

/**
 * 初始化配置参数
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午2:53:18
 */
public class Configuration {
	private String servers;
	private Long timeout;
	private int poolSize;

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
}
