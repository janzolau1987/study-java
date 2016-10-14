package com.yaoyaohao.study.zookeeper.usecase.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Response;

/**
 * Cookie工具类
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午3:02:46
 */
public class CookieHelper {
	private final static String DISTRIBUTED_COOKIE_NAME = "DISTRIBUTED_SESSION_ID";
	/**
	 * 从客户端cookies中查找自定义cookie值
	 * 
	 * @param request
	 * @return
	 */
	public static String findSessionId(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for(Cookie c : cookies) {
			if(c.getName().equals(DISTRIBUTED_COOKIE_NAME)) {
				return c.getValue();
			}
		}
		return null;
	}
	
	public static Cookie writeSessionIdToCookie(String id,Request request,Response response) {
		Cookie cookie = null;
		for(Cookie c : request.getCookies()) {
			if(c.getName().equals(DISTRIBUTED_COOKIE_NAME)) {
				cookie = c;
				break;
			}
		}
		if(cookie == null) {
			cookie = new Cookie(DISTRIBUTED_COOKIE_NAME,id);
		} else {
			cookie.setValue(id);
		}
		response.addCookie(cookie);
		return cookie;
	}
}
