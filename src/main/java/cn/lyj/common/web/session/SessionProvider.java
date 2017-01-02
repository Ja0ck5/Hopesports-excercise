package cn.lyj.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session 供应类接口
 * @author Administrator
 *
 */
public interface SessionProvider {

	/**
	 * 往session中设置值
	 * @param request
	 * @param name
	 * @param value
	 */
	public void setAttribute(HttpServletRequest request,HttpServletResponse response,String name,Serializable value);

	/**
	 * 取值
	 * @param request
	 * @param name
	 */
	public Serializable getAttribute(HttpServletRequest request,HttpServletResponse response,String name);

	/**
	 * 退出登录
	 * @param request
	 */
	public void logOut(HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 获取 sessionId
	 * @param request
	 * @return
	 */
	public String getSessionId(HttpServletRequest request,HttpServletResponse response);
}
