package cn.lyj;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.lyj.common.junit.SpringJuintTest;
import cn.lyj.core.bean.TestTb;
import cn.lyj.core.bean.product.Brand;
import cn.lyj.core.query.product.BrandQuery;
import cn.lyj.core.service.TestTbService;
import cn.lyj.core.service.product.BrandService;

/**
 * 测试
 * @author Administrator
 *
 */
public class TestBrand extends SpringJuintTest{
	
	@Autowired
	private BrandService brandService;
	
	@Test
	public void testGet() throws Exception {
//	 ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application-context.xml");	

		BrandQuery brandQuery = new BrandQuery();
		
//		brandQuery.setFields("id");
//		brandQuery.setNameLike(true);
//		brandQuery.setName("ha");
		brandQuery.orderById(false);
		
		brandQuery.setPageNo(2);
		brandQuery.setPageSize(2);
		List<Brand> brands = brandService.getBrandList(brandQuery);
		
		for (Brand brand : brands) {
			
			System.out.println(brand);
			
		}
	}

}
