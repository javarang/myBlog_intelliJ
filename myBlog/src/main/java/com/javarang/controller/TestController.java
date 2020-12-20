package com.javarang.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.javarang.utils.CommonUtil;
import com.javarang.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@GetMapping(value = "/inter")
	public ModelAndView TestCall(Model model) {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/jsp/test");
		return mv;
	}
	 
	
	@GetMapping(value = "/testSession1")
	public String testSession1(HttpServletRequest request) {
		HttpSession session = request.getSession(true);	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accoNo", "111122222");
		paramMap.put("ci", "123123");
		paramMap.put("agreeYN", "Y");
		paramMap.put("bnkCD", "22222222222223");
		
		//�ӽ� �׽�Ʈ�� ��
		session.setAttribute("revTrans", paramMap);
		log.info(CommonUtil.convSTRtoNull(((Map<String, Object>)request.getSession().getAttribute("revTrans")).get("ci")));
		((Map<String, Object>)request.getSession().getAttribute("revTrans")).put("ci", "abcdef");
		log.info(CommonUtil.convSTRtoNull(((Map<String, Object>)request.getSession().getAttribute("revTrans")).get("ci")));
		
		return "asdfasdfasdf";
	}
	
	
	@GetMapping(value = "/testSession2")
	public String testSession2(HttpServletRequest request) {
		
	
		log.info(CommonUtil.convSTRtoNull(((Map<String, Object>)request.getSession().getAttribute("revTrans")).get("ci")));
		((Map<String, Object>)request.getSession().getAttribute("revTrans")).put("ci", "ZZZZZZZZZZ");
		log.info(CommonUtil.convSTRtoNull(((Map<String, Object>)request.getSession().getAttribute("revTrans")).get("ci")));
		
		return "asdfasdfasdf";
	}
	
	
	@GetMapping(value = "/testSession")
	public String testSession(HttpServletRequest request) {
		log.info("===========================session-Start===========================");
		log.info(request.getSession().getId());		
		log.info(request.getSession().getAttribute("revTrans").toString());
		log.info("===========================session-Enddd===========================");
		return "sessionID="+request.getSession().getId()+"::::::::::::::"+request.getSession().getAttribute("revTrans").toString();
	}

	@GetMapping(value = "/testInputValue")
	public String testInputValue(HttpServletRequest request) {
		String key = "sabarada";

	    HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();

	    stringObjectObjectHashOperations.put(key, "id", request.getSession().getId());	    
	    //Object hello = stringObjectObjectHashOperations.get(key, "Hello");	    
	    Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
	    //Long size = stringObjectObjectHashOperations.size(key);	   
		log.info("===========================testInputValue-Start===========================");
		//log.info("size="+stringObjectObjectHashOperations.size(key));
		log.info("entries.all = " + entries.toString());
		log.info("entries.id = " + entries.get("id"));
		log.info("===========================testInputValue-Enddd===========================");
		return entries.toString();
	}
	
	@GetMapping(value = "/testOutputValue")
	public String testOutputValue(HttpServletRequest request) {
		String key = "sabarada";
		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		Object hello = stringObjectObjectHashOperations.get(key, "id");
		log.info("===========================testOutputValue-Start===========================");
		log.info("id = " +hello.toString());
		log.info("===========================testOutputValue-Enddd===========================");
		return hello.toString();
	}

	@GetMapping(value = "/1")
	public String TestCall1() {
		String rr ="";
		int cnt = 0;
		while((rr= RandomUtil.generateRamdomHexToken(16)).length()==22) {
			cnt ++;
			log.info(rr+"========"+cnt);
		}
		return rr+"========"+rr.length();
//		 /"test";
	}
	
	@GetMapping("/test/redis")
	public Map redisTest(HttpSession session){
	    UUID uid = Optional.ofNullable(UUID.class.cast(session.getAttribute("uid")))
	            .orElse(UUID.randomUUID());
	    session.setAttribute("uid", uid);


		long currTime = System.currentTimeMillis();
	
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		
	    Map m = new HashMap();
	    //m.put("instance_ip", this.ip);
	    m.put("uuid", uid.toString());
	   
	    m.put("interval", session.getMaxInactiveInterval());

	    m.put("date1", sf.format(1601948520000L));
	    m.put("date2", sf.format(currTime));
	    
	    return m;
	}    
}
