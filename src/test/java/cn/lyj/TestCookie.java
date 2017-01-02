package cn.lyj;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Test;

import cn.lyj.core.bean.user.Buyer;

/**
 * 购物车对象 转成Json
 * @author Administrator
 *
 */
public class TestCookie {

	@Test
	public void testCookie() throws Exception {
		Buyer buyer = new Buyer();
		buyer.setUsername("fbb");
		//springMvc
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, buyer);
		
		System.out.println(stringWriter.toString());
	}
}
