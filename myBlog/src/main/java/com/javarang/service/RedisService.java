package com.javarang.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface RedisService {

    public void inputRedis(HttpServletRequest request, HttpServletResponse response, String key, Map<String, Object> paramMap);

    public String chkExistTradeKey(HttpServletRequest request, HttpServletResponse response, String key);

    public void addRedisVal(HttpServletRequest request, HttpServletResponse response,String key, String nm, String val);

    public String getRedisVal(HttpServletRequest request, HttpServletResponse response,String key , String nm);

    public void chgRndTokkenRedisVal(HttpServletRequest request, HttpServletResponse response, String key);
}
