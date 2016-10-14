package com.yaoyaohao.study.zookeeper.usecase.session;


import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 主要负责完成初始化参数设置等
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午5:19:18
 */
public abstract class DistributeSessionFilter implements Filter {
	protected Logger log = Logger.getLogger(getClass());
	protected Configuration conf;
	protected JettyDistributedSessionManager sessionManager;
	
	public static final String SERVERS = "servers";
	public static final String TIMEOUT = "timeout";
	public static final String POOLSIZE = "poolSize";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		conf = new Configuration();
		String servers = filterConfig.getInitParameter(SERVERS);
		if(StringUtils.isNotBlank(servers)) {
			conf.setServers(servers);
		}
		String timeout = filterConfig.getInitParameter(TIMEOUT);
		if(StringUtils.isNotBlank(timeout)) {
			try{
				conf.setTimeout(Long.valueOf(timeout));
			} catch(NumberFormatException ex){
				log.error("timeout parse error [" + timeout + "]");
			}
		}
		String poolsize = filterConfig.getInitParameter(POOLSIZE);
		if(StringUtils.isNotBlank(poolsize)) {
			try{
				conf.setPoolSize(Integer.valueOf(poolsize));
			} catch(NumberFormatException ex) {
				log.error("poolsize parse error [" + poolsize + "]");
			}
		}
		//初始化ZooKeeper配置参数
		ZKHelper.initialize(conf);
	}

	@Override
	public void destroy() {
		if(sessionManager != null) {
			try{
				sessionManager.stop();
			} catch(Exception e) {
				log.error(e);
			}
		}
		ZKHelper.destory();
		log.debug("DistributeSessionFilter destory completed");
	}
}
