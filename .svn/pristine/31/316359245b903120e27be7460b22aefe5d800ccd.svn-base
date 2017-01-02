package cn.lyj.core.service.product;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.lyj.core.bean.product.Brand;
import cn.lyj.core.query.product.BrandQuery;

/**
 * 品牌
 * @author Administrator
 *
 */
public interface BrandService {

	public Pagination getBrandListWithPage(Brand brand);

	//品牌List 集合 
	public List<Brand> getBrandList(BrandQuery brandQuery);
	
	//添加品牌
	public void addBrand(Brand brand);
	
	//根据主键删除品牌
	public void deleteBrandByKey(Integer id);
	
	//批量删除
	public void deleteBrandByKeys(Integer[] ids);
	
	//修改
	public void updateBrandByKey(Brand brand);
	
	//查询一个品牌
	public Brand getBrandByKey(Integer id);
}
