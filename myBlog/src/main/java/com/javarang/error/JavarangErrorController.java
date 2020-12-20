	
package com.javarang.error;

import java.util.Date;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class JavarangErrorController implements ErrorController {
	
 
	@RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object exceptionObj = request.getAttribute("javax.servlet.error.exception");
		
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        log.info("httpStatus : "+httpStatus.toString());
       
        //Integer statusCode = Integer.valueOf(status.toString());        
        //if(Integer.parseInt(status.toString()) == HttpStatus.NOT_FOUND.value())
        //    return "/error/error404";
        //else if(Integer.parseInt(status.toString()) == HttpStatus.INTERNAL_SERVER_ERROR.value()) 
        //    return "/error/error500";
        if(RequestDispatcher.ERROR_STATUS_CODE==null) {
        	log.info("ERROR_STATUS_CODE");
        }else {
        	
        }
        log.info("ERROR_STATUS_CODE"+ RequestDispatcher.ERROR_STATUS_CODE);
        log.info("ERROR_STATUS_CODE1"+ request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        log.info("code"+ status.toString());
        log.info("msg"+ httpStatus.getReasonPhrase());
        log.info("timestamp"+ new Date()); 
        
        ModelAndView mv = new ModelAndView();
        if(exceptionObj!=null) {
			Throwable e = ((Exception) exceptionObj).getCause();
			mv.addObject("exception", e.getClass().getName());
			mv.addObject("message", e.getMessage());
		}
        mv.setViewName("/error/error");
        mv.addObject("code", status.toString());
        mv.addObject("msg", httpStatus.getReasonPhrase());
        mv.addObject("timestamp", new Date()); 
        return mv;
    }
	

	

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return  "/error";
	}


}
	