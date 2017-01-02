package cn.lyj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.lyj.common.junit.SpringJuintTest;
import cn.lyj.core.bean.TestTb;
import cn.lyj.core.service.TestTbService;

/**
 * 测试
 * @author Administrator
 *
 */
public class TestTestTb extends SpringJuintTest{
	
	@Autowired
	private TestTbService testTbservice;
	
	@Test
	public void testAdd() throws Exception {
//	 ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("application-context.xml");	
	 
		TestTb testTb = new TestTb();
		testTb.setName("haha");
		testTbservice.addTestTb(testTb);
	}

}
