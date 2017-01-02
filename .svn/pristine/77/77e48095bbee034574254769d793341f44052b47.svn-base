package cn.lyj.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.lyj.common.web.session.SessionProvider;
import cn.lyj.core.bean.product.Brand;
import cn.lyj.core.bean.product.Color;
import cn.lyj.core.bean.product.Feature;
import cn.lyj.core.bean.product.Product;
import cn.lyj.core.bean.product.Sku;
import cn.lyj.core.bean.product.Type;
import cn.lyj.core.query.product.BrandQuery;
import cn.lyj.core.query.product.FeatureQuery;
import cn.lyj.core.query.product.ProductQuery;
import cn.lyj.core.query.product.SkuQuery;
import cn.lyj.core.query.product.TypeQuery;
import cn.lyj.core.service.product.BrandService;
import cn.lyj.core.service.product.ColorService;
import cn.lyj.core.service.product.FeatureService;
import cn.lyj.core.service.product.ProductService;
import cn.lyj.core.service.product.SkuService;
import cn.lyj.core.service.product.TypeService;

/**
 * 商品前台页面
 * 
 * @author Administrator
 *
 */
@Controller
public class FrontProductController {

	// 品牌service
	@Autowired
	private BrandService brandService;
	// 商品service
	@Autowired
	private ProductService productService;
	// 商品类型
	@Autowired
	private TypeService typeService;
	// 属性
	@Autowired
	private FeatureService featureService;
	// 颜色
	@Autowired
	private ColorService colorService;
	//sku
	@Autowired
	private SkuService skuService;
	
	//session 供应
	@Autowired
	private SessionProvider SessionProvider;
	
	@RequestMapping(value = "test/springmvc.do")
	public String test(String name, Date birthday) {
		System.out.println("name=" + name + ";birthday=" + birthday);
		return "";
	}

	// 局部日期转换
	/*
	 * @InitBinder public void initBinder(WebDataBinder binder, WebRequest
	 * request) {
	 * 
	 * DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
	 * true)); }
	 */

	@RequestMapping(value = "product/display/list.shtml")
	public String list(Integer pageNo,Integer brandId,String brandName,Integer typeId,String typeName,ModelMap model,HttpServletRequest request) {

		// 加载商品属性
		FeatureQuery featureQuery = new FeatureQuery();
		List<Feature> features = featureService.getFeatureList(featureQuery);
		model.addAttribute("features", features);

		//分页参数拼接对象
		StringBuilder params = new StringBuilder();
		ProductQuery productQuery = new ProductQuery();
		// 设置页号
		productQuery.setPageNo(Pagination.cpn(pageNo));
		// 设置每页数
		productQuery.setPageSize(Product.FRONT_PAGE_SIZE);
		productQuery.orderbyId(false);
		//TODO 条件
		Map<String,String> query = new LinkedHashMap<String, String>();
		boolean flag = false;
		
		if(null != brandId){
			productQuery.setBrandId(brandId);
			flag = true;
			model.addAttribute("brandId", brandId);
			model.addAttribute("brandName", brandName);
			query.put("品牌", brandName);
			params.append("&brandId=").append(brandId).append("&brandName=").append(brandName);
		}else{

			// 加载商品品牌
			BrandQuery brandQuery = new BrandQuery();
			brandQuery.setFields("id,name");
			brandQuery.setIsDisplay(1);
			// 加载品牌
			List<Brand> brands = brandService.getBrandList(brandQuery);
			model.addAttribute("brands", brands);
		}
		//类型
		if(null != typeId){
			productQuery.setBrandId(typeId);
			flag = true;
			model.addAttribute("typeId", typeId);
			model.addAttribute("typeName", typeName);
			query.put("类型", typeName);
			params.append("&typeId=").append(typeId).append("&typeName=").append(typeName);
		}else{

			TypeQuery typeQuery = new TypeQuery();
			// 指定查询字段
			typeQuery.setFields("id,name");
			typeQuery.setIsDisplay(1);
			typeQuery.setParentId(0);
			// 加载商品类型
			List<Type> types = typeService.getTypeList(typeQuery);
			model.addAttribute("types", types);
		}
		
		model.addAttribute("flag",flag);
		model.addAttribute("query", query);
		
		
		
		// 加载带有分页的商品
		Pagination pagination = productService.getProductListWithPage(productQuery);
		// 分页页面展示
		String url = request.getServletPath();
		pagination.pageView(url, params.toString());
		model.addAttribute("pagination", pagination);

		return "product/product";
	}
	
	//跳转到单品页
	@RequestMapping(value="/product/detail.shtml")
	public String detail(Integer id,ModelMap model){
		//商品
		Product product = productService.getProductByKey(id);
		model.addAttribute("product", product);
		
		//sku
		List<Sku> skus = skuService.getStock(id);
		model.addAttribute("skus", skus);
		//去重
		List<Color> colors = new ArrayList<Color>();
		for (Sku sku : skus) {
			//判断集合当中是否有当前对象,但是 contains 底层比较的是 euquals 实现，所以需要在比较对象(Color)中重写
			if(!colors.contains(sku.getColor())){
				colors.add(sku.getColor()); 
			}
		}
		model.addAttribute("colors", colors);
		
		return "product/productDetail";
	}
	
	
}
