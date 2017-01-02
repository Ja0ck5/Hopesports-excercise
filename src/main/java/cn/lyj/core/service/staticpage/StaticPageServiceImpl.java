package cn.lyj.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 生成静态页实现类
 * @author Administrator
 *
 */
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{
	
	private Configuration conf;
	
/*	private FreeMarkerConfigurer freeMarkerConfigurer;
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
*/	
	//不使用注解注入，可以使用 setter(在配置文件当中注入)
	//不是用声明
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}

	//静态化
	public void productStatic(Map<String,Object> root,Integer id){
		//模板目录   既然要设置，就直接在注入的时候设置
//		conf.setDirectoryForTemplateLoading(new File(""));	
		//UTF-8 写 到磁盘
		Writer out = null;
		try {
			String templateName = "productDetail.html";
			//UTF-8 读 到内存
			Template template = conf.getTemplate(templateName);
			
			//获取路径
			String path = getPath("/html/product/" + id + ".html");//
			File f = new File(path);
			File parentFile = f.getParentFile();
			if(!parentFile.exists()) parentFile.mkdirs();
			out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
			
			//处理
			template.process(root, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		}
	
	}
	
	//获取路径
	public String getPath(String name){
		return servletContext.getRealPath(name);
	}
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext= servletContext;
		
		
	}


}
