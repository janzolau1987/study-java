package com.yaoyaohao.study.zookeeper.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Request;

/**
 * 包装器类，用来包装Jetty容器的Request对象，并覆盖其getSession方法
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午2:55:31
 */
public class JettyRequestWrapper extends Request {
	private Logger log = Logger.getLogger(getClass());
	private JettyDistributedSessionManager sessionManager;
	private HttpServletRequest request;
	private HttpSession session;

	public JettyRequestWrapper(JettyDistributedSessionManager sessionManager, HttpServletRequest request) {
		this.sessionManager = sessionManager;
		this.request = request;
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (sessionManager != null && create) {
			throw new IllegalStateException("No SessionHandler or SessionManager");
		}
		if (session != null && sessionManager != null) {
			return session;
		}
		session = null;
		//
		String id = sessionManager.getRequestSessionId(request);
		log.debug("获取客户端的Session ID : [" + id + "]");
		if (id != null && sessionManager != null) {
			session = sessionManager.getHttpSession(id, request);
			if (session == null && !create)
				return null;
		}
		if (session == null && sessionManager != null && create) {
			session = sessionManager.newHttpSession(request);
		}
		return session;
	}

}
