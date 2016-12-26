package com.he.redis.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
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
	
	@Test
	public void testTransaction(){
		
		SessionCallback<User> callback = new SessionCallback<User>() {
			
			public <K, V> User execute(RedisOperations<K, V> operations) throws DataAccessException {
				
				ValueOperations<String, User> opsForValue = (ValueOperations<String, User>) operations.opsForValue();
				User user = null;
				do{
					operations.watch((K) "明月");
					user = opsForValue.get("明月");
					if(user == null){
						user = new User();
						user.setName("明月");
						user.setAge(12);
					}
					user.setAge(user.getAge() + 10);
					operations.multi();
					opsForValue.set("明月", user);
				}while(operations.exec() == null);
				
				return user;
			}
		};
		
		User user = redisTemplate.execute(callback);
		
		System.out.println(user);
	}
	
}
