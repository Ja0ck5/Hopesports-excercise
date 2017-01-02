package cn.lyj.core.dao.order;

import java.util.List;

import cn.lyj.core.bean.order.Order;
import cn.lyj.core.query.order.OrderQuery;

public interface OrderDao {

	/**
	 * 添加
	 * @param order
	 */
	public Integer addOrder(Order order);

	/**
	 * 根据主键查找
	 * @param orderQuery
	 */
	public Order getOrderByKey(Integer id);

	/**
	 * 根据主键批量查找
	 * @param orderQuery
	 */
	public List<Order> getOrdersByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * @param orderQuery
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * @param orderQuery
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * @param orderQuery
	 */
	public Integer updateOrderByKey(Order order);

	/**
	 * 分页查询
	 * @param orderQuery
	 */
	public List<Order> getOrderListWithPage(OrderQuery orderQuery);

	/**
	 * 集合查询
	 * @param orderQuery
	 */
	public List<Order> getOrderList(OrderQuery orderQuery);
	
	/**
	 * 总条数
	 * @param orderQuery
	 */
	public int getOrderListCount(OrderQuery orderQuery);
}
