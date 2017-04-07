package com.rt.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ErrorHandler")
public class ErrorHandlerController {

	@RequestMapping
	public String errorHandler(HttpServletRequest request){
	//LogUtils.logPageError(request);
		return "error/error";
	}
	
}
