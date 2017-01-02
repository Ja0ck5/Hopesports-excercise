package cn.lyj.core.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Test
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/control")
public class CenterController {

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

	// 跳转入口页面
	@RequestMapping(value = "/index.do")
	public String index() {
		return "index";
	}

	// 跳转头页面
	@RequestMapping(value = "/top.do")
	public String top() {
		return "top";
	}

	// 跳转身体主页面
	@RequestMapping(value = "/main.do")
	public String main() {
		return "main";
	}

	// 跳转身体左边页面
	@RequestMapping(value = "/left.do")
	public String left() {
		return "left";
	}

	// 跳转身体you页面
	@RequestMapping(value = "/right.do")
	public String right() {
		return "right";
	}

}
