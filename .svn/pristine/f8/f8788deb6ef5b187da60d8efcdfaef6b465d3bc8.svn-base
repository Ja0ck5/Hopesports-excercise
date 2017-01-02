package cn.lyj.common.web.session;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lyj.core.web.Constants;

/**
 * 本地session
 * 
 * @author Administrator
 *
 */
public class HttpSessionProvider implements SessionProvider {

	public void setAttribute(HttpServletRequest request,HttpServletResponse response, String name, Serializable value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);

	}

	public Serializable getAttribute(HttpServletRequest request,HttpServletResponse response, String name) {
		// 若存在获取session，不存在则不重新创建
		HttpSession session = request.getSession(false);
		if (null != session) {
			return (Serializable) session.getAttribute(name);
		}
		return null;
	}

	public void logOut(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (null != session) {
			session.invalidate();
		}
		// Cookie 有JSESSIONID

	}

	public String getSessionId(HttpServletRequest request,HttpServletResponse response) {
		// TODO Auto-generated method stub
		// http://localhost:8080/example.do?JSESSIONID=asdfe12313fhggg
		// request.getRequestedSessionId();//获取URL上的sessionId
		return request.getSession().getId();
	}

	// 退出登陆
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		Cookie c = new Cookie(Constants.SESSION_ID, null);
		c.setMaxAge(0);
		response.addCookie(c);
	}

}
