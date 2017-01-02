package cn.lyj.core.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.lyj.core.bean.product.Brand;
import cn.lyj.core.bean.product.Color;
import cn.lyj.core.bean.product.Feature;
import cn.lyj.core.bean.product.Img;
import cn.lyj.core.bean.product.Product;
import cn.lyj.core.bean.product.Sku;
import cn.lyj.core.bean.product.Type;
import cn.lyj.core.query.product.BrandQuery;
import cn.lyj.core.query.product.ColorQuery;
import cn.lyj.core.query.product.FeatureQuery;
import cn.lyj.core.query.product.ProductQuery;
import cn.lyj.core.query.product.TypeQuery;
import cn.lyj.core.service.product.BrandService;
import cn.lyj.core.service.product.ColorService;
import cn.lyj.core.service.product.FeatureService;
import cn.lyj.core.service.product.ProductService;
import cn.lyj.core.service.product.SkuService;
import cn.lyj.core.service.product.TypeService;
import cn.lyj.core.service.staticpage.StaticPageService;

/**
 * 后台商品管理
 * @author Administrator
 *
 */
@Controller
public class ProductController {
	
	//品牌service
	@Autowired
	private BrandService brandService;
	//商品service
	@Autowired
	private ProductService productService;
	//商品类型
	@Autowired
	private TypeService typeService;
	//属性
	@Autowired
	private FeatureService featureService;
	//颜色
	@Autowired
	private ColorService colorService;
	//颜色
	@Autowired
	private SkuService skuService;
	//静态化
	@Autowired
	private StaticPageService staticPageService;
	
	//商品列表
	@RequestMapping(value="/product/list.do")
	public String list(Integer pageNo,String name,Integer brandId,Integer isShow,ModelMap model,HttpServletRequest request){
	
		BrandQuery brandQuery = new BrandQuery();
		
		brandQuery.setFields("id,name");
		brandQuery.setIsDisplay(1);
		//加载品牌
		List<Brand> brands = brandService.getBrandList(brandQuery);
		model.addAttribute("brands", brands);
		
		//分页参数
		StringBuilder params = new StringBuilder();
		
		//商品条件对象
		ProductQuery productQuery = new ProductQuery();
		//1:判断条件是为Null
		if(StringUtils.isNotBlank(name)){
			productQuery.setName(name);
			//要求模糊查询
			productQuery.setNameLike(true);
			params.append("&name=").append(name);
			//回显查询条件
			model.addAttribute("name", name);
		}
		//判断品牌ID
		if(null != brandId){
			productQuery.setBrandId(brandId);
			params.append("&").append("brandId=").append(brandId);
			//回显查询条件
			model.addAttribute("brandId", brandId);
		}
		//判断上下架状态
		if(null != isShow){
			productQuery.setIsShow(isShow);
			params.append("&").append("isShow=").append(isShow);
			//回显查询条件
			model.addAttribute("isShow", isShow);
		}else{
			productQuery.setIsShow(0);
			params.append("&").append("isShow=").append(0);
			//回显查询条件
			model.addAttribute("isShow", 0);
		}
		//设置页号
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//设置每页数
		productQuery.setPageSize(5);
		//倒排
		productQuery.orderbyId(false);
		//加载带有分页的商品
		Pagination pagination = productService.getProductListWithPage(productQuery);
		//分页页面展示
		String url = request.getServletPath();
		pagination.pageView(url, params.toString());
		model.addAttribute("pagination", pagination);
		
		return "product/list";
		
	}
	
	
	//跳转到添加页面
	@RequestMapping(value="/product/toAdd.do")
	public String toAdd(ModelMap model){
		TypeQuery typeQuery = new TypeQuery();
		//指定查询字段
		typeQuery.setFields("id,name");
		typeQuery.setIsDisplay(1);
		typeQuery.setParentId(0);
		//加载商品类型
		List<Type> types = typeService.getTypeList(typeQuery);
		model.addAttribute("types", types);

		//加载商品品牌
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setFields("id,name");
		brandQuery.setIsDisplay(1);
		//加载品牌
		List<Brand> brands = brandService.getBrandList(brandQuery);
		model.addAttribute("brands", brands);
		
		//加载商品属性
		FeatureQuery featureQuery = new FeatureQuery();
		List<Feature> features = featureService.getFeatureList(featureQuery);
		model.addAttribute("features", features);
		
		//加载颜色
		ColorQuery colorQuery = new ColorQuery();
		colorQuery.setParentId(0);
		List<Color> colors = colorService.getColorList(colorQuery);
		model.addAttribute("colors", colors);
		
		
		return "product/add";
	}
	
	//商品添加
	@RequestMapping(value="/product/add.do")
	public String add(Product product,Img img){
		
		product.setImg(img);
		//商品表   图片表  sku 表 需要控制事务
		
		productService.addProduct(product);
		
		return "redirect:/product/list.do";
	}
	
	//商品上架
	@RequestMapping(value="/product/isShow.do")
	public String isShow(Integer[] ids,Integer pageNo,String name,Integer brandId,Integer isShow,ModelMap model){
		Product product = new Product();
		product.setIsShow(isShow);
		for (Integer id : ids) {
			product.setId(id);
			productService.updateProductByKey(product);
			Map<String,Object> root = new HashMap<String, Object>();
			//商品
			Product pdt = productService.getProductByKey(id);
			root.put("product", pdt);
			
			//sku
			List<Sku> skus = skuService.getStock(id);
			root.put("skus", skus);
			//去重
			List<Color> colors = new ArrayList<Color>();
			for (Sku sku : skus) {
				//判断集合当中是否有当前对象,但是 contains 底层比较的是 euquals 实现，所以需要在比较对象(Color)中重写
				if(!colors.contains(sku.getColor())){
					colors.add(sku.getColor()); 
				}
			}
			root.put("colors", colors);
			// 生成静态页面
			staticPageService.productStatic(root,id);
		}
		
		if(null != pageNo){
			model.addAttribute("pageNo", pageNo);
		}
		if(null != name){
			model.addAttribute("name", name);
		}
		if(null != brandId){
			model.addAttribute("brandId", brandId);
		}
		if(null != isShow){
			model.addAttribute("isShow", isShow);
		}
		
		
		return "redirect:/product/list.do";
	}
	

}
