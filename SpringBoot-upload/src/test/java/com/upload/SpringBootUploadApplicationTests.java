package com.upload;

import com.google.common.cache.*;
import com.upload.Entity.Apple;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest(classes = SpringBootUploadApplication.class)
class SpringBootUploadApplicationTests {

	@Autowired
	public Apple apple;
	@Test
	void contextLoads() {
		System.out.println("泰斗了");
	}

	private static final Logger log = LoggerFactory.getLogger(SpringBootUploadApplicationTests.class);

	@Transactional
	@org.junit.Test
	public void  shangke(){
		List<String> list = new ArrayList<>();
		list.add("xiaoming ");
	    TransactionAspectSupport.currentTransactionStatus().hasSavepoint();
		Object savepoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
		list.add("数据库");
		System.out.println(list.size());
		 TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savepoint);
		System.out.println("回滚之后  "+list.size());
	}


	public static void main(String[] args)  throws ExecutionException{


		try {
			SpringBootUploadApplicationTests.testLoadingCache();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static  void testLoadingCache() throws Exception {
		LoadingCache<String, String> cahceBuilder = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
			@Override
			public String load(String key) throws Exception {
				System.out.println("请求的key为" + key + "在缓存中不存在,通过load方法获取key.");
				String strProValue = "hello " + key + "!";
				return strProValue;
			}

		});

		// 第一次到缓存里面key为peida的数据，缓存不存在通过，load加载，并保存到缓存里面
		System.out.println("a value:" + cahceBuilder.get("a"));
		// 第二次获取key为peida的数据，缓存已经存在，直接在缓存里面返回
		System.out.println("a value:" + cahceBuilder.get("a"));

		// 往缓存里面存放数据
		cahceBuilder.put("b", "bbbb");
		// 缓存已经存在数据了,直接获取
		System.out.println("b value:" + cahceBuilder.get("b"));
	}



	private static Cache<String,Object> cache;
		static {
			cache = CacheBuilder.newBuilder().maximumSize(10000)
					.expireAfterWrite(24, TimeUnit.HOURS)
					.initialCapacity(10)
					.removalListener(new RemovalListener<String, Object>() {
						@Override
						public void onRemoval(RemovalNotification<String, Object> rn) {
							if(log.isInfoEnabled()){
								log.info("被移除缓存{}:{}",rn.getKey(),rn.getValue());
							}
						}
					}).build();
		}

	/**
	 * @desction: 获取缓存
	 */
	public  static Object get(String key){
		return !StringUtils.isEmpty(key)?cache.getIfPresent(key):null;
	}
	/**
	 * @desction: 放入缓存
	 */
	public static void put(String key,Object value){
		if(!StringUtils.isEmpty(key) && value !=null){
			cache.put(key,value);
		}
	}
	/**
	 * @desction: 移除缓存
	 */
	public static void remove(String key){
		if(!StringUtils.isEmpty(key)){
			cache.invalidate(key);
		}
	}
	/**
	 * @desction: 批量删除缓存
	 */
	public static void remove(List<String> keys){
		if(keys !=null && keys.size() >0){
			cache.invalidateAll(keys);
		}
	}

	@Test
	public void showInfo(){
		Apple apple = new Apple();
		apple.showName();
	}


}
