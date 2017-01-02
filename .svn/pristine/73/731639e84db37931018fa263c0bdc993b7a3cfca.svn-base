package cn.lyj.common.web.aop;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;

import com.danga.MemCached.MemCachedClient;

import cn.itcast.common.web.aop.MemCachedUtil;

/**
 * 缓存 memcached 中数据的切面对象
 * around 环绕
 * after  
 * @author Administrator
 *
 */
public class MemCachedInterceptor {
	//memcached 缓存时间
	public static final int TIMEOUT = 360000;//单位 sec
	@Autowired
	private MemCachedClient memCachedClient;

	private int expiry = TIMEOUT;
	
	//配置环绕 around
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
		// 如果 Memcached 没连接上
		if(memCachedClient.stats().isEmpty()){
			System.err.println("找不到  memcached 服务器或者连接不上");
			//回service	
			return pjp.proceed();
		}
		
		//去 memecached 当中有没有数据
		String cacheKey = getCacheKey(pjp);
		System.out.println(cacheKey);
		//返回值
		if(null == memCachedClient.get(cacheKey)){
			//回 service 中取 ，再返回来
			Object proceed = pjp.proceed();
			//先放到 memcached 中
			memCachedClient.set(cacheKey, proceed, expiry);
		}
		//返回Controller中
		return memCachedClient.get(cacheKey);
	}
	
	//后置 执行更新，添加，删除之后，执行
	public void doAfter(JoinPoint jp){
		//包名+类名+方法名+参数（多个,并且转成json），那么根据规则，只需要 key 满足 包+类 开头，就清理
		String prefix = jp.getTarget().getClass().getName();
		Map<String, Object> keySet = MemCachedUtil.getKeySet(memCachedClient);
		
		for (Entry<String, Object> entry : keySet.entrySet()) {
			String key = entry.getKey();
			if(key.startsWith(prefix) )
				memCachedClient.delete(key);
		}
		
	}
	/**
	 * K 的生成规则
	 * 包名+类名+方法名+参数（多个,并且转成json）
	 */
	public String getCacheKey(ProceedingJoinPoint pjp){
		StringBuilder key = new StringBuilder();
		
		//package + class name
		String packageClazzName = pjp.getTarget().getClass().getName();
		key.append(packageClazzName);
		//method name
		String methodName = pjp.getSignature().getName();
		key.append(".").append(methodName);
		//args name
		Object[] args = pjp.getArgs();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
		//转成 josn
		for (Object arg : args) {
			StringWriter stringWriter = new StringWriter();
			
			try {
				objectMapper.writeValue(stringWriter, arg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			key.append(".").append(stringWriter);
		}
		
		return key.toString();
	}

	//支持扩展 可在配置文件中注入自定义时间
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
	
	
}
