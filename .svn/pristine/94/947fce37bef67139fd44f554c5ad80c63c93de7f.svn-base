package cn.lyj.core.query.product;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌模块查询专用
 * 
 * @author Administrator
 *
 */
public class BrandQuery {

	private Integer id;
	private String name;
	private String description;
	private String imgUrl;
	private Integer sort;
	private Integer isDisplay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}

	/******************************************** 指定查询字段 ****************************/

	private String fields;

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	/********************************************
	 * 指定字段是否模糊查询 like
	 ****************************/

	private boolean nameLike;

	public boolean isNameLike() {
		return nameLike;
	}

	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}

	/********************************************
	 * 指定字段是否模糊查询 like
	 ****************************/
	// 利用 内部类

	public class FieldOrder {
		private String filed;
		private String order;

		public FieldOrder(String filed, String order) {
			super();
			this.filed = filed;
			this.order = order;
		}

		public String getFiled() {
			return filed;
		}

		public void setFiled(String filed) {
			this.filed = filed;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

	}

	// 创建 FieldOrder (order by)集合
	private List<FieldOrder> filedOrders = new ArrayList<FieldOrder>();

	public void orderById(boolean isAsc) {
		filedOrders.add(new FieldOrder("id", isAsc == true ? "asc" : "desc"));
	}

	
	// 页号
	private Integer pageNo = 1;
	// 开始行
	private Integer startRow;
	// 每页记录数
	private Integer pageSize = 10;

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		// 计算开始行
		this.startRow = (pageNo - 1) * pageSize;
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		// 计算开始行
		this.startRow = (pageNo - 1) * pageSize;
		this.pageNo = pageNo;
	}

}
