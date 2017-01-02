package cn.lyj.core.controller;


import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lyj.common.web.session.SessionProvider;
import cn.lyj.core.bean.BuyCart;
import cn.lyj.core.bean.BuyItem;
import cn.lyj.core.bean.product.Sku;
import cn.lyj.core.bean.user.Addr;
import cn.lyj.core.bean.user.Buyer;
import cn.lyj.core.query.user.AddrQuery;
import cn.lyj.core.service.product.SkuService;
import cn.lyj.core.service.user.AddrService;
import cn.lyj.core.web.Constants;

/**
 * 购物车
 * @author Administrator
 *
 */
@Controller
public class BuyCartController {
	
	@Autowired
	private SkuService skuService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private SessionProvider sessionProvider;
	//购买按钮
	@RequestMapping(value="/shopping/buyCart.shtml")
	public String buyCart(Integer skuId,Integer amount,Integer buyLimit,Integer productId,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
			//springMvc  要求 javaBean 是一个标准的
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
			
			BuyCart buyCart = null;
			//Cookie 不存在 则新建购物车
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
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
			}
			
			if(null == buyCart){
				buyCart = new BuyCart();
			}
			
			if(null != skuId){
				Sku sku = new Sku();
				if(null != buyLimit){
					sku.setSkuUpperLimit(buyLimit);
				}
				sku.setId(skuId);
				BuyItem buyItem = new BuyItem();
				buyItem.setSku(sku);
				buyItem.setAmount(amount);
	
				
				buyCart.addItem(buyItem);
				//最后一款产品的id
				if(null != productId)
				buyCart.setProductId(productId);
				
				//将购物车放进 Cookie  购物车对象转成json
				StringWriter stringWriter = new StringWriter();
				try {
					objectMapper.writeValue(stringWriter, buyCart);
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Cookie cookie = new Cookie(Constants.BUYCART_COOKIE, stringWriter.toString());
				//关闭浏览器也要此cookie
				//默认为 -1 关闭浏览器就销毁,销毁 为 0 
				cookie.setMaxAge(60*60*24);
				//cookie 的路径问题    默认为 当前路径 /shopping
				//不设置的话，就必须得以 /shopping 开头
				cookie.setPath("/");
				//发送到页面
				response.addCookie(cookie);
				
		}
			//装满购物车
		List<BuyItem> items = buyCart.getItems();
			
		for (BuyItem item : items) {
			Sku skuByKey = skuService.getSkuByKey(item.getSku().getId());
			item.setSku(skuByKey);
		}
		
		
		model.addAttribute("buyCart", buyCart);
		
		return "product/cart";
	}
	
	//清空购物车
	@RequestMapping(value="/shopping/clearCart.shtml")
	public String clearCart(HttpServletRequest request,HttpServletResponse response){
		//springMvc  要求 javaBean 是一个标准的
		/*ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		
		BuyCart buyCart = null;
		//Cookie 不存在 则新建购物车
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}*/
		
//		if(null != buyCart) buyCart.clearCart();
		
		Cookie cookie = new Cookie(Constants.BUYCART_COOKIE, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "redirect:/shopping/buyCart.shtml";
	}
	
	//删除单个购物项
	@RequestMapping(value="/shopping/delItem.shtml")
	public String delItem(HttpServletRequest request,HttpServletResponse response,Integer skuId){

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		
		BuyCart buyCart = null;
		//Cookie 不存在 则新建购物车
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		if(null != buyCart){
			Sku sku = new Sku();
			sku.setId(skuId);
			BuyItem buyItem = new BuyItem();
			buyItem.setSku(sku);

			buyCart.delItem(buyItem);
			
			//将购物车放进 Cookie  购物车对象转成json
			StringWriter stringWriter = new StringWriter();
			try {
				objectMapper.writeValue(stringWriter, buyCart);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Cookie cookie = new Cookie(Constants.BUYCART_COOKIE, stringWriter.toString());
			//关闭浏览器也要此cookie
			//默认为 -1 关闭浏览器就销毁,销毁 为 0 
			cookie.setMaxAge(60*60*24);
			//cookie 的路径问题    默认为 当前路径 /shopping
			//不设置的话，就必须得以 /shopping 开头
			cookie.setPath("/");
			//发送到页面
			response.addCookie(cookie);
		
		}
		return "redirect:/shopping/buyCart.shtml";
	}
	
	//结算
	@RequestMapping(value="/buyer/trueBuy.shtml")
	public String trueBuy(HttpServletRequest request,HttpServletResponse response,ModelMap model){

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
		
		if(null != buyCart){
			//判断购物车商品是否还有库存
			List<BuyItem> items = buyCart.getItems();
			int beforeSize = items.size();
			if(null != items && beforeSize>0){
				//判断购物项存在库存
				for (BuyItem it : items) {
					Sku sku = skuService.getSkuByKey(it.getSku().getId());
					if(sku.getStockInventory() < it.getAmount()){
						//清理当前项
						buyCart.delItem(it);
					}
				}
				
				int afterSize = items.size();
				//清理前后购物车项的数目
				if(beforeSize > afterSize){
					//修改Cookie 中的购物车
					//将购物车放进 Cookie  购物车对象转成json
					StringWriter stringWriter = new StringWriter();
					try {
						objectMapper.writeValue(stringWriter, buyCart);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Cookie cookie = new Cookie(Constants.BUYCART_COOKIE, stringWriter.toString());
					//关闭浏览器也要此cookie
					//默认为 -1 关闭浏览器就销毁,销毁 为 0 
					cookie.setMaxAge(60*60*24);
					//cookie 的路径问题    默认为 当前路径 /shopping
					//不设置的话，就必须得以 /shopping 开头
					cookie.setPath("/");
					//发送到页面
					response.addCookie(cookie);
					return "redirect:/shopping/buyCart.shtml";
				}else{
					Buyer buyer = (Buyer) sessionProvider.getAttribute(request,response, Constants.BUYER_SESSION);
					//加载收货地址
					AddrQuery addrQuery = new AddrQuery();
					addrQuery.setBuyerId(buyer.getUsername());
					addrQuery.setIsDef(1);
					List<Addr> addrs = addrService.getAddrList(addrQuery);
					
					model.addAttribute("addr", addrs.get(0));

					//装满购物车
					List<BuyItem> its = buyCart.getItems();
					for (BuyItem item : its) {
						Sku skuByKey = skuService.getSkuByKey(item.getSku().getId());
						item.setSku(skuByKey);
					}
					model.addAttribute("buyCart", buyCart);
					return "product/productOrder";
				}
			}else{
				return "redirect:/shopping/buyCart.shtml";
			}
			
		}else{
			return "redirect:/shopping/buyCart.shtml";
		}

	}
}
