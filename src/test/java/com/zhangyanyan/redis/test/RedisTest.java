package com.zhangyanyan.redis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhangyanyan.redis.domain.User;
import com.zyy.common.utils.RandomUtil;
import com.zyy.common.utils.StringUtil;
import com.zyy.common.utils.UserUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-redis.xml")
public class RedisTest {

	@Resource
	private RedisTemplate redisTemplate;

	@Test
	public void testJDK() {

		long t1 = System.currentTimeMillis();

		List<User> user_list = new ArrayList();

		for (int i = 0; i < 50000; i++) {
//			随机生成姓名
			String name = StringUtil.generateChineseName();
//			随机生成性别
			String gender = UserUtils.getSex();
//			随机生成13开头的号码
			String phone = "13" + RandomUtil.randomNumber(9);
//			随机生成邮箱
			String email = UserUtils.getMail();
//			随机生成生日
			String birthday = UserUtils.getBirthday();
			
			User user = new User(i, name, gender, phone, email, birthday);
//			将数据放入list集合
			user_list.add(user);
		}
		
		ListOperations opsForList = redisTemplate.opsForList();

		opsForList.leftPushAll("user_jdk", user_list);

		long t2 = System.currentTimeMillis();

		System.out.println("总时间" + (t2 - t1));

	}

	@Test
	public void testJSON() {

		long t1 = System.currentTimeMillis();

		List<User> user_list = new ArrayList();

		for (int i = 0; i < 50000; i++) {

//			随机生成姓名
			String name = StringUtil.generateChineseName();
//			随机生成性别
			String gender = UserUtils.getSex();
//			随机生成13开头的号码
			String phone = "13" + RandomUtil.randomNumber(9);
//			随机生成邮箱
			String email = UserUtils.getMail();
//			随机生成生日
			String birthday = UserUtils.getBirthday();
			
			User user = new User(i, name, gender, phone, email, birthday);
//			将数据放入list集合
			user_list.add(user);
		}

		ListOperations opsForList = redisTemplate.opsForList();

		opsForList.leftPushAll("user_json", user_list);

		long t2 = System.currentTimeMillis();

		System.out.println("总时间" + (t2 - t1));

	}

	@Test
	public void testHash() {

		long t1 = System.currentTimeMillis();

		HashMap<String, User> map = new HashMap<String, User>();

		for (int i = 0; i < 50000; i++) {

//			随机生成姓名
			String name = StringUtil.generateChineseName();
//			随机生成性别
			String gender = UserUtils.getSex();
//			随机生成13开头的号码
			String phone = "13" + RandomUtil.randomNumber(9);
//			随机生成邮箱
			String email = UserUtils.getMail();
//			随机生成生日
			String birthday = UserUtils.getBirthday();
			
			User user = new User(i, name, gender, phone, email, birthday);
//			将数据放入map
			map.put(i+"", user);
		}

		HashOperations opsForHash = redisTemplate.opsForHash();
		
		opsForHash.putAll("user_hash_json", map);

		long t2 = System.currentTimeMillis();

		System.out.println("总时间" + (t2 - t1));

	}

}
