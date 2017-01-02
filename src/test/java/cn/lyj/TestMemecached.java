package cn.lyj;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;

import cn.lyj.common.junit.SpringJuintTest;
import cn.lyj.core.bean.user.Buyer;

/**
 * 测试
 * @author Administrator
 *
 */
public class TestMemecached extends SpringJuintTest{
	
	@Autowired
	private MemCachedClient memCachedClient;
	
	@Test
	public void testAdd() throws Exception {
//	 ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application-context.xml");	
        Buyer buyer = new Buyer();
        buyer .setUsername("haha");
//		memCachedClient.set("ss", "yj");
		memCachedClient.set("hh", buyer);
		Object hh = memCachedClient.get("hh");
		
		System.out.println(hh);
	}

}
