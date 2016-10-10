package com.yaoyaohao.study.zookeeper.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.servlet.AbstractSessionManager;

/**
 * 自定义session管理器
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午3:00:46
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JettyDistributedSessionManager extends AbstractSessionManager {
	private Logger log = Logger.getLogger(getClass());
	private Configuration conf;
	private Map<String, HttpSession> sessions = new HashMap<>();

	public JettyDistributedSessionManager(Configuration conf) {
		this.conf = conf;
		//ZKHelper.initialize(conf);
	}

	// 获取Session ID
	public String getRequestSessionId(HttpServletRequest request) {
		return CookieHelper.findSessionId(request);
	}

	public HttpSession getHttpSession(String id, HttpServletRequest request) {
		if (!(request instanceof Request)) {
			log.warn("不是jetty容器下的Request对象");
			return null;
		}
		Request req = (Request) request;
		boolean valid = ZKHelper.isValid(id);
		if (!valid) {
			sessions.remove(id);
			return null;
		} else {
			ZKHelper.updateSessionMetaData(id);
			HttpSession session = sessions.get(id);
			if (session != null)
				return session;
			session = new JettyDistributeSession((AbstractSessionManager) req.getSessionManager(),
					System.currentTimeMillis(), id);
			sessions.put(id, session);
			return session;
		}
	}

	public HttpSession newHttpSession(HttpServletRequest request) {
		if (!(request instanceof Request)) {
			log.warn("不是jetty容器下的Request对象");
			return null;
		}
		Request req = (Request) request;
		Session session = new JettyDistributeSession((AbstractSessionManager) req.getSessionManager(), request);
		addHttpSession(session, request);
		String id = session.getId();
		// 写cookie
		Cookie cookie = CookieHelper.writeSessionIdToCookie(id, req, req.getConnection().getResponse());
		if (cookie != null) {
			log.debug("Wrote sid to Cookie,name:[" + cookie.getName() + "],value:[" + cookie.getValue() + "]");
		}
		// 在zk服务器上创建Session节点，节点名称为SessionID
		SessionMetaData metadata = new SessionMetaData();
		metadata.setId(id);
		metadata.setMaxIdle(conf.getTimeout() * 60 * 1000);
		ZKHelper.createSessionNode(metadata);
		return session;
	}

	public void addHttpSession(Session session, HttpServletRequest request) {
		addSession(session);
	}

	@Override
	public Map getSessionMap() {
		return Collections.unmodifiableMap(sessions);
	}

	@Override
	public int getSessions() {
		return sessions.size();
	}

	@Override
	protected void addSession(Session session) {
		sessions.put(session.getId(), session);
	}

	@Override
	public Session getSession(String idInCluster) {
		return (Session) sessions.get(idInCluster);
	}

	@Override
	protected void invalidateSessions() {
		ArrayList ss = new ArrayList(sessions.values());
		for (Iterator i = ss.iterator(); i.hasNext();) {
			Session session = (Session) i.next();
			session.invalidate();
		}
		sessions.clear();
	}

	@Override
	protected Session newSession(HttpServletRequest request) {
		return (Session) newHttpSession(request);
	}

	@Override
	protected void removeSession(String idInCluster) {
		sessions.remove(idInCluster);
	}
}
