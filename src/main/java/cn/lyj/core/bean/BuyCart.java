package cn.lyj.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class BuyCart {
	
	//购物项  应由购物车创建
	List<BuyItem> items = new ArrayList<BuyItem>();
	//再增加一个添加方法
	public void addItem(BuyItem item){
		if(items.contains(item)){
			for (BuyItem it : items) {
				if(it.equals(item)){
					int result = it.getAmount()+item.getAmount();
					if(it.getSku().getSkuUpperLimit() >= result){
						it.setAmount(result);
					}else{
						it.setAmount(it.getSku().getSkuUpperLimit());
					}
				}
			}
		}else{
			items.add(item);
		}
	}
	
	//继续购物 最后一款商品
	private Integer productId;
	
	public List<BuyItem> getItems() {
		return items;
	}

	public void setItems(List<BuyItem> items) {
		this.items = items;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	//因为有返回值，json 需要此属性有声明，整个Bean 是一个标准的 bean。
	//所以使用 此标签 忽略
	//小计
	//商品数量
	@JsonIgnore
	public int getProductAmount(){
		int result = 0;
		for (BuyItem item : items) {
			result+=item.getAmount();
		}
		return result;
	}
	//商品金额
	@JsonIgnore
	public Double getProductPrice(){
		Double result = 0.00;
		for (BuyItem item : items) {
			result+=item.getSku().getSkuPrice()*item.getAmount();
		}
		return result;
	}
	
	//运费
	@JsonIgnore
	public Double getDeliverFee(){
		Double result = 0.00;
			if(getProductPrice()<=39){
				result = 10.00;
			}
		return result;
	}
	
	//总额
	@JsonIgnore
	public Double getTotalPrice(){
		return getDeliverFee()+getTotalPrice();
	}
	
	//清空购物车，将item清空
	public void clearCart(){
		items.clear();
	}
	
	//删除单个item
	public void delItem(BuyItem item){
		items.remove(item);//重写了 equals 方法
	}
	
}
