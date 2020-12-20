package com.javarang.service.impl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javarang.service.RedisService;
import com.javarang.utils.CommonUtil;
import com.javarang.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	StringRedisTemplate redisTemplate;
	 
	@Override
	public void inputRedis(HttpServletRequest request, HttpServletResponse response,String key,  Map<String, Object> paramMap) {

		paramMap.put("innerRndToken", RandomUtil.generateRamdomHexToken(16));
		//paramMap.put("accoNo", "111122222");
		//paramMap.put("ci", "123123");
		//paramMap.put("agreeYN", "Y");
		//paramMap.put("bnkCD", "22222222222223");
		
		key = "sabarada";
		
	 	for(Map.Entry<String, Object> s : paramMap.entrySet() ) {
	 		redisTemplate.opsForHash().put(key, s.getKey(), CommonUtil.convSTRtoNull(s.getValue()));
	 	}
	 	//Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
	 	//log.info("entries.id = " + entries.get("id"));
	 	log.info("size="+redisTemplate.opsForHash().size(key));
			   
	}

	@Override
	public String chkExistTradeKey(HttpServletRequest request, HttpServletResponse response, String key) {
		return redisTemplate.hasKey(key)?"Y":"N";
	}

	@Override
	public void addRedisVal(HttpServletRequest request, HttpServletResponse response,String key, String nm, String val) {
		
		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		stringObjectObjectHashOperations.put(key,nm, val);
		redisTemplate.expire(key, 5, TimeUnit.MINUTES);
	}

	@Override
	public String getRedisVal(HttpServletRequest request, HttpServletResponse response,String key , String nm) {
		
		return CommonUtil.convSTRtoNull(redisTemplate.opsForHash().get(key, nm));
	}

	@Override
	public void chgRndTokkenRedisVal(HttpServletRequest request, HttpServletResponse response, String key) {
		
		String orgToken = CommonUtil.convSTRtoNull(redisTemplate.opsForHash().get(key, "innerRndToken"));
		String newToken = "";
		while(orgToken.equals((newToken = RandomUtil.generateRamdomHexToken(16)))) {
			
		}
		addRedisVal(request, response, key, "innerRndToken", newToken);

	}

	

}
