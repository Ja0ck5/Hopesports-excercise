package cn.lyj.core.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.lyj.core.bean.product.Brand;
import cn.lyj.core.service.product.BrandService;

/**
 * 品牌
 * 
 * @author Administrator
 *
 */
@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;

	// 商品身体
	@RequestMapping(value = "/brand/list.do")
	public String list(String name, Integer isDisplay, Integer pageNo, ModelMap model) {

		StringBuilder params = new StringBuilder();
		Brand brand = new Brand();
		if (StringUtils.isNotBlank(name)) {
			brand.setName(name);
			params.append("name=").append(name);
		}

		isDisplay = isDisplay != null ? isDisplay : 1;

		brand.setIsDisplay(isDisplay);
		params.append("&").append("isDisplay=").append(isDisplay);
		// 如果页号为 null 或者小于 1，则重置 为1
		brand.setPageNo(Pagination.cpn(pageNo));
		// 设置每页记录数
		brand.setPageSize(5);
		// 分页对象
		Pagination pagination = brandService.getBrandListWithPage(brand);

		// 分页展示
		String url = "/brand/list.do";
		pagination.pageView(url, params.toString());

		model.addAttribute("pagination", pagination);// 底层原理依然是request.setAttribute("obj",obj);
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);

		return "brand/list";

	}

	// 跳转到品牌添加页面
	@RequestMapping(value = "/brand/toAdd.do")
	public String toAdd() {
		return "brand/add";
	}

	// 添加品牌
	@RequestMapping(value = "/brand/add.do")
	public String addBrand(Brand brand) {
		brandService.addBrand(brand);
		return "redirect:/brand/list.do";
	}

	// 删除品牌
	@RequestMapping(value = "/brand/delete.do")
	public String deleteBrand(Integer id, String name, Integer isDisplay, ModelMap model) {
		// 删除
		brandService.deleteBrandByKey(id);
		// 重定向传递参数可以使用ModelMap
		if (StringUtils.isNotBlank(name))
			model.addAttribute("name", name);
		if (null != isDisplay)
			model.addAttribute("isDisplay", isDisplay);
		return "redirect:/brand/list.do";
	}

	// 批量删除品牌
	@RequestMapping(value = "/brand/deletes.do")
	public String deleteBrands(Integer[] ids, String name, Integer isDisplay, ModelMap model) {
		// 删除
		brandService.deleteBrandByKeys(ids);
		// 重定向传递参数可以使用ModelMap
		if (StringUtils.isNotBlank(name))
			model.addAttribute("name", name);
		if (null != isDisplay)
			model.addAttribute("isDisplay", isDisplay);
		return "redirect:/brand/list.do";
	}
	
	//跳转到修改品牌
	@RequestMapping(value = "/brand/toEdit.do")
	public String toEdit(Integer id,ModelMap model){

		Brand brand = brandService.getBrandByKey(id);
		
		model.addAttribute("brand", brand);
		
		return "brand/edit";
	}

	//跳转到修改品牌
	@RequestMapping(value = "/brand/edit.do")
	public String edit(Brand brand,ModelMap model){

		brandService.updateBrandByKey(brand);
		
		return "redirect:/brand/list.do";
 	}

}
