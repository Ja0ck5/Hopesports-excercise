package cn.lyj.core.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.lyj.common.web.ResponseUtils;
import cn.lyj.core.bean.product.Sku;
import cn.lyj.core.query.product.SkuQuery;
import cn.lyj.core.service.product.SkuService;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class SkuController {
	
	@Autowired
	private SkuService skuService;

	//跳转到库存管理页面
	@RequestMapping(value="/sku/list.do")
	public String list(Integer productId,String pno,ModelMap model){
		
		model.addAttribute("pno", pno);
		//最小销售单元
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.setProductId(productId);
		List<Sku> skus = skuService.getSkuList(skuQuery);
		model.addAttribute("skus", skus);
		
		return "sku/list";
	}

	//跳转到库存管理页面
	@RequestMapping(value="/sku/add.do")
	public void add(Sku sku,ModelMap model,HttpServletResponse response){

		//修改
		skuService.updateSkuByKey(sku);
		//
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功");
		ResponseUtils.renderJson(response, jo.toString());
	}
}
