package com.yaoyaohao.study.zookeeper.session;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mortbay.jetty.servlet.AbstractSessionManager;
import org.mortbay.jetty.servlet.AbstractSessionManager.Session;

/**
 * 自定义基于jetty的分布式session
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午4:49:23
 */
public class JettyDistributeSession extends Session {
	private static final long serialVersionUID = 676551569090570785L;

	public JettyDistributeSession(AbstractSessionManager abstractSessionManager, Long nowTm, String id) {
		abstractSessionManager.super(nowTm, id);
	}

	protected JettyDistributeSession(AbstractSessionManager abstractSessionManager, HttpServletRequest request) {
		abstractSessionManager.super(request);
	}

	@Override
	public synchronized Object getAttribute(String name) {
		String id = getId();
		if (id != null && id.length() > 0) {
			return ZKHelper.getSessionData(id, name);
		}
		return null;
	}
	
	@Override
	public synchronized void setAttribute(String name, Object value) {
		String id = getId();
		if(id != null && id.length() > 0) {
			ZKHelper.setSessionData(id, name, value);
		}
	}

	@Override
	public synchronized void removeAttribute(String name) {
		String id = getId();
		if (id != null && id.length() > 0) {
			ZKHelper.removeSessionData(id, name);
		}
	}
	
	@Override
	public void invalidate() throws IllegalStateException {
		String id = getId();
		if(id != null && id.length() > 0) {
			ZKHelper.deleteSessionNode(id);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Map newAttributeMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
