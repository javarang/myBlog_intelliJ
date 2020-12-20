package com.javarang.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PreProcessService {
	
	public void changeSession(HttpServletRequest request, HttpServletResponse response);
	
	public void inputSession(HttpServletRequest request, HttpServletResponse response,  Map<String, Object> paramMap);
	
	public String chkExistSession(HttpServletRequest request, HttpServletResponse response);
	
	public void addSessionVal(HttpServletRequest request, HttpServletResponse response, String nm, String val);
	
	public String getSessionVal(HttpServletRequest request, HttpServletResponse response, String nm);
	
	public void removeSessionVal(HttpServletRequest request, HttpServletResponse response, String nm);
	
	public void chgRndTokkenSessionVal(HttpServletRequest request, HttpServletResponse response);
	
	public Object getSessionVal();
	
}
