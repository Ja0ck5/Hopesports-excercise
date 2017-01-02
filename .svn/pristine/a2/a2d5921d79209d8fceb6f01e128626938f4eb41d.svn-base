package cn.lyj.core.service.order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.lyj.core.bean.BuyCart;
import cn.lyj.core.bean.BuyItem;
import cn.lyj.core.bean.order.Detail;
import cn.lyj.core.bean.order.Order;
import cn.lyj.core.dao.order.DetailDao;
import cn.lyj.core.dao.order.OrderDao;
import cn.lyj.core.query.order.OrderQuery;
/**
 * 订单主
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Resource
	OrderDao orderDao;
	@Resource
	DetailDao detailDao;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addOrder(Order order,BuyCart buyCart) {
		//生成订单号
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String oId = dateFormat.format(new Date());
		order.setOid(oId);
		//运费
		order.setDeliverFee(buyCart.getDeliverFee());
		//应付金额
		order.setPayableFee(buyCart.getTotalPrice());
		//订单金额
		order.setTotalPrice(buyCart.getProductPrice()); 
		//支付状态
		if(order.getPaymentWay() == (0)){
			order.setIsPaiy(0);
		}else{
			order.setIsPaiy(1);
		}
		//订单状态
		order.setState(0);
		//订单生成时间
		order.setCreateDate(new Date());
		//保存订单
		Integer o = orderDao.addOrder(order);
		
		//获取购物车详情
		List<BuyItem> items = buyCart.getItems();
		
		for (BuyItem buyItem : items) {
			//保存订单详情 List 集合
			Detail detail = new Detail(); 
			detail.setOrderId(order.getId());
			//设置productName
			detail.setProductName(buyItem.getSku().getProduct().getName());
			//商品编号
			detail.setProductNo(buyItem.getSku().getProduct().getNo());
			//设置颜色名称
			detail.setColor(buyItem.getSku().getColor().getName());
			//size
			detail.setSize(buyItem.getSku().getSize());
			//price
			detail.setSkuPrice(buyItem.getSku().getSkuPrice());
			//购物数量
			detail.setAmount(buyItem.getAmount());
			//add
			detailDao.addDetail(detail);
			
		}
		
		return o;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Order getOrderByKey(Integer id) {
		return orderDao.getOrderByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrdersByKeys(List<Integer> idList) {
		return orderDao.getOrdersByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return orderDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return orderDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateOrderByKey(Order order) {
		return orderDao.updateOrderByKey(order);
	}
	
	@Transactional(readOnly = true)
	public Pagination getOrderListWithPage(OrderQuery orderQuery) {
		Pagination p = new Pagination(orderQuery.getPageNo(),orderQuery.getPageSize(),orderDao.getOrderListCount(orderQuery));
		p.setList(orderDao.getOrderListWithPage(orderQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrderList(OrderQuery orderQuery) {
		return orderDao.getOrderList(orderQuery);
	}
}
