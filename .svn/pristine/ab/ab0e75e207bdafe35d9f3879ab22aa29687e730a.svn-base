package cn.lyj.core.service.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.lyj.common.web.session.SessionProvider;
import cn.lyj.core.bean.user.Buyer;
import cn.lyj.core.web.Constants;

public class LoginInterceptor implements HandlerInterceptor {

	//注入sessionProvider
	@Autowired
	private SessionProvider sessionProvider;
	
	private Integer adminId;
	
	private static final String INTERCEPTOR_URL = "/buyer/";
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(null != adminId){
			Buyer buyer = new Buyer();
			buyer.setUsername("fbb2014");
			sessionProvider.setAttribute(request,response, Constants.BUYER_SESSION, buyer);
		}else{
			Buyer buyer = (Buyer) sessionProvider.getAttribute(request,response, Constants.BUYER_SESSION);
			boolean isLogin = false;
			if(null != buyer){
				isLogin = true;
			}
			request.setAttribute("isLogin", isLogin);
			String requestURI = request.getRequestURI();
			if(requestURI.startsWith(INTERCEPTOR_URL)){
				if(null == buyer){
					String returnUrl = request.getParameter("returnUrl");
					response.sendRedirect("/shopping/login.shtml?returnUrl="+(returnUrl != null ? returnUrl : "/product/display/list.shtml"));
					return false;
				}
			}
		}
				return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

}
