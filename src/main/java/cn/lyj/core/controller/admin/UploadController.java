package cn.lyj.core.controller.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import cn.lyj.common.web.ResponseUtils;
import cn.lyj.core.web.Constants;
import net.fckeditor.response.UploadResponse;

/**
 * 上传图片
 * 商品
 * 品牌
 * 品牌介绍
 * @author Administrator
 *
 */
@Controller
public class UploadController {

	//上传图片
	@RequestMapping(value="/upload/uploadPic.do")
	public void uploadPic(@RequestParam(required=false)  MultipartFile pic,HttpServletResponse response){
		
		//图片名称生成策略(毫秒级时间 + 三位数随机数)
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		//图片名称的一部分
		String format = df.format(new Date());
		//随机三位数
		Random rd = new Random();

		for (int i = 0; i < 3; i++) {
			format += rd.nextInt(10);
		}
		
		//获取上传图片扩展名
		String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
		
		//实例化一个 Jersey
		Client client = new Client();
		
		//相对路径-----> 保存到数据库的图片路径
		String path = "upload/"+format+"."+extension;
		
		//另一台Tomcat 请求路径
		String url =  Constants.IMG_URL + path;
		//设置请求路径
		WebResource resource = client.resource(url);
		//发送开始 POST GET PUT 
		//它是基于 PUT 方式提交
		try {
			resource.put(String.class, pic.getBytes());
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		//返回两个路径
		JSONObject jo = new JSONObject();
		jo.put("url", url);
		jo.put("path", path);
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	//商品fck 上传图片
	@RequestMapping(value="/upload/uploadFck.do")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response){
		//强转 支持多个
		MultipartHttpServletRequest  mr = (MultipartHttpServletRequest)request;
		
		//获取
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
		
		for (Entry<String, MultipartFile> entry : entrySet) {
			//上传上来的文件
			MultipartFile pic = entry.getValue();

			//图片名称生成策略(毫秒级时间 + 三位数随机数)
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			//图片名称的一部分
			String format = df.format(new Date());
			//随机三位数
			Random rd = new Random();

			for (int i = 0; i < 3; i++) {
				format += rd.nextInt(10);
			}
			
			//获取上传图片扩展名
			String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
			
			//实例化一个 Jersey
			Client client = new Client();
			
			//相对路径-----> 保存到数据库的图片路径
			String path = "upload/"+format+"."+extension;
			
			//另一台Tomcat 请求路径
			String url =  Constants.IMG_URL + path;
			//设置请求路径
			WebResource resource = client.resource(url);
			//发送开始 POST GET PUT 
			//它是基于 PUT 方式提交
			try {
				resource.put(String.class, pic.getBytes());
			}catch (IOException e) {
				e.printStackTrace();
			}

			//返回全路径给 fck java-core....jar   	返回 url 即可
			UploadResponse ok = UploadResponse.getOK(url);
			//response write 字符流
			//response print 字节流 返回对象
			try {
				response.getWriter().print(ok);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}
