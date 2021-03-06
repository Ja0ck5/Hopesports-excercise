package cn.lyj.common.web.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;


/**
 * 远程session
 * 存放到 Memcached 服务器里的 session
 * @author Administrator
 *
 */
public class CacheSessionProvider implements SessionProvider {


	@Autowired
	private MemCachedClient memCachedClient;
	
	private  int expiry = 30; 
	
	private static final String JSESSIONID = "JSESSIONID";




	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}

	
	

	public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name,
			Serializable value) {


		Map<String,Serializable> session = new HashMap<String,Serializable>();
		session.put(name, value);
		
		memCachedClient.set(getSessionId(request,response), session, expiry*60);
		
			
	}

	@SuppressWarnings("unchecked")
	public Serializable getAttribute(HttpServletRequest request, HttpServletResponse response, String name) {
		Map<String,Serializable> session = (Map<String, Serializable>) memCachedClient.get(getSessionId(request, response));
		if(null != session){
			return session.get(name);
		}
		return null;
	}

	public void logOut(HttpServletRequest request, HttpServletResponse response) {

		String sessionId = getSessionId(request, response);
		if(memCachedClient.keyExists(sessionId)){
			memCachedClient.delete(sessionId);
		}
		
		//需要清理 Cookie
		
			
	}

	public String getSessionId(HttpServletRequest request, HttpServletResponse response) {

		//从 Cookie 中获取
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length>0){
			for (Cookie cookie : cookies) {
				if(JSESSIONID.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		//不存在 则生成一个，并存放到Cookie 中
		String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
		Cookie ck = new Cookie(JSESSIONID, sessionId);
		ck.setMaxAge(-1);
		ck.setPath("/");
		response.addCookie(ck);
		
		return sessionId;
	
	}}
