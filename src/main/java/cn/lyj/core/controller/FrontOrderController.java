package cn.lyj.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lyj.common.web.session.SessionProvider;
import cn.lyj.core.bean.BuyCart;
import cn.lyj.core.bean.BuyItem;
import cn.lyj.core.bean.order.Order;
import cn.lyj.core.bean.product.Sku;
import cn.lyj.core.bean.user.Buyer;
import cn.lyj.core.service.order.OrderService;
import cn.lyj.core.service.product.SkuService;
import cn.lyj.core.web.Constants;

/**
 * 提交订单 前台
 * @author Administrator
 *
 */
@Controller
public class FrontOrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private SkuService  skuService;
	//提交订单
	@RequestMapping(value="/buyer/confirmOrder.shtml")
	public String confirmOrder(Order order,HttpServletRequest request,HttpServletResponse response){
		//接收参数
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		
		BuyCart buyCart = null;
		//JSESSIONID
		//buyCart cookie
		Cookie[] cookies = request.getCookies();
		if(null != cookies && cookies.length>0){
			for (Cookie cookie : cookies) {
				//存在购物车 cookie
				if(Constants.BUYCART_COOKIE.equals(cookie.getName())){
					String value = cookie.getValue();
					try {
						buyCart = objectMapper.readValue(value, BuyCart.class);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		//装满购物车
		List<BuyItem> its = buyCart.getItems();
		for (BuyItem item : its) {
			Sku skuByKey = skuService.getSkuByKey(item.getSku().getId());
			item.setSku(skuByKey);
		}
		
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request,response, Constants.BUYER_SESSION);	
		order.setBuyerId(buyer.getUsername());
		//保存订单 订单详情
		orderService.addOrder(order,buyCart);
		//提交完订单之后清空购物车
		Cookie cookie = new Cookie(Constants.BUYCART_COOKIE, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "product/confirmOrder";
	}
}
