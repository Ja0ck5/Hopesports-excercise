package cn.lyj.core.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.image.ImageCaptchaService;

import cn.lyj.common.encode.Md5Pwd;
import cn.lyj.common.web.ResponseUtils;
import cn.lyj.common.web.session.SessionProvider;
import cn.lyj.core.bean.country.City;
import cn.lyj.core.bean.country.Province;
import cn.lyj.core.bean.country.Town;
import cn.lyj.core.bean.user.Buyer;
import cn.lyj.core.query.country.CityQuery;
import cn.lyj.core.query.country.TownQuery;
import cn.lyj.core.service.country.CityService;
import cn.lyj.core.service.country.ProvinceService;
import cn.lyj.core.service.country.TownService;
import cn.lyj.core.service.user.BuyerService;
import cn.lyj.core.web.Constants;

/**
 * 跳转登录页面 登录 个人中心
 * 
 * @author Administrator
 *
 */
@Controller
public class ProfileController {

	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private Md5Pwd md5Pwd;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ImageCaptchaService imageCaptchaService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private TownService townService;

	// GET 跳转到登录页面
	@RequestMapping(value = "/shopping/login.shtml", method = RequestMethod.GET)
	public String login() {
		return "buyer/login";
	}

	/**
	 * 
	 * @param buyer
	 * @param captcha
	 * @param returnUrl
	 * @return
	 */
	// POST 登录
	@RequestMapping(value = "/shopping/login.shtml", method = RequestMethod.POST)
	public String login(Buyer buyer, String captcha, String returnUrl, ModelMap model, HttpServletRequest request,HttpServletResponse response) {
		// 验证码是否为空
		if (StringUtils.isNotBlank(captcha)) {
			// 验证码是否正确 JSESSIONID 验证码
			if (imageCaptchaService.validateResponseForID(sessionProvider.getSessionId(request,response), captcha)) {
				if (null != buyer && StringUtils.isNotBlank(buyer.getUsername())) {
					if (StringUtils.isNotBlank(buyer.getPassword())) {
						Buyer buyerKey = buyerService.getBuyerByKey(buyer.getUsername());
						if (null != buyerKey) {
							if (buyerKey.getPassword().equals(md5Pwd.encode(buyer.getPassword()))) {
								sessionProvider.setAttribute(request,response, Constants.BUYER_SESSION, buyerKey);
								if (StringUtils.isNotBlank(returnUrl)) {
									return "redirect:" + returnUrl;
								} else {
									// 个人中心
									return "redirect:/buyer/index.shtml";
								}
							} else {
								model.addAttribute("error", "密码错误");
							}
						} else {
							model.addAttribute("error", "用户名错误");
						}
					} else {
						model.addAttribute("error", "密码不能为空");
					}
				} else {
					model.addAttribute("error", "用户名不能为空");
				}
			} else {
				model.addAttribute("error", "验证码错误");
			}

		} else {
			model.addAttribute("error", "请填写验证码");
		}

		return "buyer/login";
	}

	// 个人中心
	@RequestMapping(value = "/buyer/index.shtml")
	public String index() {
		return "buyer/index";
	}

	// 个人资料
	@RequestMapping(value = "/buyer/profile.shtml")
	public String profile(HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		Buyer buyer = (Buyer) sessionProvider.getAttribute(request,response, Constants.BUYER_SESSION);
		Buyer buyerByKey = buyerService.getBuyerByKey(buyer.getUsername());
		model.addAttribute("buyer", buyerByKey);

		// province
		List<Province> provinces = provinceService.getProvinceList(null);
		model.addAttribute("provinces", provinces);
		// city
		CityQuery cityQuery = new CityQuery();
		cityQuery.setProvince(buyerByKey.getProvince());
		List<City> cities = cityService.getCityList(cityQuery);
		model.addAttribute("cities", cities);
		// town
		TownQuery townQuery = new TownQuery();
		townQuery.setCity(buyerByKey.getCity());
		List<Town> towns = townService.getTownList(townQuery);
		model.addAttribute("towns", towns);

		return "buyer/profile";
	}

	// 加载city
	@RequestMapping(value = "/buyer/city.shtml")
	public void city(HttpServletResponse response, String code) {

		CityQuery cityQuery = new CityQuery();
		cityQuery.setProvince(code);
		List<City> cities = cityService.getCityList(cityQuery);

		JSONObject jo = new JSONObject();
		jo.put("cities", cities);
		ResponseUtils.renderJson(response, jo.toString());
	}

	// 加载县区
	@RequestMapping(value = "/buyer/town.shtml")
	public void town(HttpServletResponse response, String code) {

		TownQuery townQuery = new TownQuery();
		townQuery.setCity(code);
		List<Town> towns = townService.getTownList(townQuery);

		JSONObject jo = new JSONObject();
		jo.put("towns", towns);
		ResponseUtils.renderJson(response, jo.toString());
	}

	// 收货地址
	@RequestMapping(value = "/buyer/deliver_address.shtml")
	public String deliverAddress() {
		return "buyer/deliver_address";
	}
}
