package cn.lyj.core.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.lyj.core.bean.product.Brand;
import cn.lyj.core.dao.product.BrandDao;
import cn.lyj.core.query.product.BrandQuery;

/**
 * 品牌业务层
 * @author Administrator
 *
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService{
	
	@Resource
	private BrandDao brandDao;
	
	
	@Transactional(readOnly=true)
	public Pagination getBrandListWithPage(Brand brand){
		//起始页号  startRow = (pageNo - 1) * pageSize
		//每页记录
		//总记录数
		Pagination pagination = new Pagination(brand.getPageNo(), brand.getPageSize(), brandDao.getBrandCount(brand)); 
		//Brand 集合
		pagination.setList(brandDao.getBrandListWithPage(brand));
		
		return  pagination;
	}

	//添加品牌
	public void addBrand(Brand brand) {
		brandDao.addBrand(brand);
	}

	//根据主键删除
	public void deleteBrandByKey(Integer id) {
		brandDao.deleteBrandByKey(id);		
	}
	
	//批量删除
	public void deleteBrandByKeys(Integer[] ids) {
		brandDao.deleteBrandByKeys(ids);		
	}
	
	//修改品牌
	public void updateBrandByKey(Brand brand) {
		brandDao.updateBrandByKey(brand);		
	}

	//得到一個品牌
	public Brand getBrandByKey(Integer id) {
		return brandDao.getBrandByKey(id);
	}

	public List<Brand> getBrandList(BrandQuery brandQuery) {
		return brandDao.getBrandList(brandQuery);
	}

}
