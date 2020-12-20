package com.javarang.interceptor;

import com.javarang.service.PreProcessService;
import com.javarang.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JavarangInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private PreProcessService preService;
 
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {


//    	if (handler instanceof HandlerMethod) {
//             
//             HandlerMethod method = (HandlerMethod) handler;
//             logger.info("handler method name : " + method.getMethod().getName());
//         }

        log.info("uri="+request.getRequestURI());
        // -> /test/redis
        String inputUri = CommonUtil.convSTRtoNull(request.getRequestURI());
        if(inputUri.equals("/issueToken")) {
            log.info("issueTokenHandler");
        }else {
            log.info("elseHandler");
            if(preService.chkExistSession(request, response).equals("N")) {
                //String str = CommonUtil.convSTRtoNull(request.getParameter("tradeKey"));
            
                String str = "234234234";
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("tradeKey", str);
             
                preService.inputSession(request, response, paramMap);

            }else {
                preService.chgRndTokkenSessionVal(request, response);
            }

        }


        //  log.info("================ Before Method");


        return true;
    }

    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView) {
        //	log.info("================ Method Executed");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        //	log.info("================ Method Completed");
    }
}
