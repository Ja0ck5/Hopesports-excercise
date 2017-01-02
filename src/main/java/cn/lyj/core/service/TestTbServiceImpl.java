package cn.lyj.core.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.lyj.core.bean.TestTb;
import cn.lyj.core.dao.TestTbDao;
/**
 * Test
 * @author Administrator
 *
 */
@Service
@Transactional
public class TestTbServiceImpl implements TestTbService {

	@Resource
	private TestTbDao testTbDao;
	
	public void addTestTb(TestTb testTb) {

		testTbDao.addTestTb(testTb);
		
//		throw new RuntimeException();
	}

}
