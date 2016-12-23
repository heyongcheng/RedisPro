package com.he.redis.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.he.model.User;
import com.he.redis.base.BaseJunit4Test;

public class RedisTest extends BaseJunit4Test{
	
	@Resource
	RedisTemplate<String,User> redisTemplate;
	
	@Test
	public void testInit(){
		System.out.println("init...");
	}
	
	@Test
	public void testRedis(){
		try {
			ValueOperations<String,User> opsForValue = redisTemplate.opsForValue();
			
			User user = new User();
			user.setName("明月");
			user.setAge(26);
			user.setTel("18236102672");
			System.out.println(user);
			
			opsForValue.set("明月_", user);
			
			User user2 = opsForValue.get("明月");
			
			System.out.println(user2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
