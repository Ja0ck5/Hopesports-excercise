package cn.lyj.core.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lyj.core.bean.order.Detail;
import cn.lyj.core.bean.order.Order;
import cn.lyj.core.bean.user.Addr;
import cn.lyj.core.query.order.DetailQuery;
import cn.lyj.core.query.order.OrderQuery;
import cn.lyj.core.query.user.AddrQuery;
import cn.lyj.core.service.order.DetailService;
import cn.lyj.core.service.order.OrderService;
import cn.lyj.core.service.user.AddrService;

/**
 * 后台 订单管理
 * 订单列表
 * 订单查看
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private DetailService detailService;
	@Autowired
	private AddrService addrService;
	
	
	@RequestMapping(value="/order/list.do")
	public String list(Integer isPay,Integer state,ModelMap model){
		OrderQuery orderQuery = new OrderQuery();
		
		if(null != isPay){
			orderQuery.setIsPaiy(isPay);
		}
		
		if(null != state){
			orderQuery.setState(state);
		}
		
		List<Order> orders = orderService.getOrderList(orderQuery);
		model.addAttribute("orders", orders);
		return "order/list";
	}
	//订单查看
	@RequestMapping(value="/order/view.do")
	public String view(Integer id,ModelMap model){
		//查询订单主表
		Order order = orderService.getOrderByKey(id);
		
		//查询订单详情
		DetailQuery detailQuery = new DetailQuery();
		detailQuery.setOrderId(id);
		List<Detail> details = detailService.getDetailList(detailQuery);
		
		//查询收货地址
		AddrQuery addrQuery = new AddrQuery();
		addrQuery.setBuyerId(order.getBuyerId());
		addrQuery.setIsDef(1);
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		
		model.addAttribute("order", order);
		model.addAttribute("details", details);
		model.addAttribute("addr", addrs.get(0));
		return "order/view";
	}
	
	
	
}
