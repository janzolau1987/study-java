package com.yaoyaohao.study.zookeeper.usecase.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 实现分布式Session的第一步就是要定义一个filter，用来拦截HttpServletRequest对象
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午5:28:48
 */
public class JettyDistributedSessionFilter extends DistributeSessionFilter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		// 实例化Jetty容器下的Session管理器
		sessionManager = new JettyDistributedSessionManager(conf);
		try {
			sessionManager.start();
			// 创建组节点
			ZKHelper.createGroupNode();
			log.debug("DistributeSessionFilter.init method completed.");
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		JettyRequestWrapper req = new JettyRequestWrapper(sessionManager, (HttpServletRequest) request);
		chain.doFilter(req, response);
	}
}
