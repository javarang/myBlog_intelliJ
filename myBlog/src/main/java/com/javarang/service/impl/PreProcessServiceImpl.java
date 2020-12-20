package com.javarang.service.impl;

import com.javarang.service.PreProcessService;
import com.javarang.utils.CommonUtil;
import com.javarang.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Service
public class PreProcessServiceImpl implements PreProcessService {

    @Override
    public void changeSession(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    @Override
    public void inputSession(HttpServletRequest request, HttpServletResponse response, Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        HttpSession session = request.getSession(true);
        //임시 테스트용 시작
//		paramMap.put("innerRndToken", RandomUtil.generateRamdomHexToken(16));
//		paramMap.put("accoNo", "111122222");
//		paramMap.put("ci", "123123");
//		paramMap.put("agreeYN", "Y");
//		paramMap.put("bnkCD", "22222222222223");

        //임시 테스트용 끝
//		session.setAttribute("revTrans", paramMap);
        //session.setAttribute("tradeKey", paramMap.get("tradeKey"));
        //session.setAttribute("tradeKey", paramMap.get("rndToken"));
        //param.put("innerRndToken", RandomUtil.generateRamdomHexToken(16))
        //session.setAttribute("tradeKey", paramMap.get("accoNo"));
        //session.setAttribute("tradeKey", paramMap.get("ci"));
        //session.setAttribute("tradeKey", paramMap.get("agreeYN"));

    }

    @Override
    public String chkExistSession(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method
        if(request.getSession().getAttribute("revTrans")==null)
            return "N";
        else
            return "Y";
//		String trKey = CommonUtil.convSTRtoNull(((Map)request.getSession().getAttribute("revTrans")).get("tradeKey"));
//		if(!trKey.equals("")) {
//			return "Y";
//		}else {
//			return "N";
//
//		}

    }

    @Override
    public void addSessionVal(HttpServletRequest request, HttpServletResponse response, String nm, String val) {
        // TODO Auto-generated method stub
        ((Map<String, Object>)request.getSession().getAttribute("revTrans")).put(nm, val);
        //map.put("innerRndToken", RandomUtil.generateRamdomHexToken(16));

    }

    @Override
    public void chgRndTokkenSessionVal(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        String orgToken = CommonUtil.convSTRtoNull(((Map)request.getSession().getAttribute("revTrans")).get("innerRndToken"));
        String newToken = "";
        while(orgToken.equals((newToken = RandomUtil.generateRamdomHexToken(16)))) {

        }
        addSessionVal(request, response, "innerRndToken", newToken);


    }

    @Override
    public void removeSessionVal(HttpServletRequest request, HttpServletResponse response, String nm) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getSessionVal() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSessionVal(HttpServletRequest request, HttpServletResponse response, String nm) {
        // TODO Auto-generated method stub
        return CommonUtil.convSTRtoNull(((Map<String, Object>)request.getSession().getAttribute("revTrans")).get(nm));

    }
}
