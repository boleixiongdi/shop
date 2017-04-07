 package com.rt.shop.util;
 
 import javax.security.auth.Subject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rt.shop.common.constant.Constants;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.spring.SpringContextUtil;
import com.rt.shop.entity.User;
import com.rt.shop.service.IUserService;
 
 public class SecurityUserHolder
 {
	
	 public static User getCurrentUser() {
    
	User sUser=	 getSessionLoginUser();
	if(sUser!=null){
		return sUser;
	}
     User user = null;
     if (RequestContextHolder.getRequestAttributes() != null) {
       HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
       HttpSession  session = request.getSession(false);
       if(session!=null)
    	   user = session.getAttribute(Constants.CURRENT_USER) != null ?(User)session.getAttribute(Constants.CURRENT_USER) : null;
 
       Cookie[] cookies = request.getCookies();
       String id = "";
       if (cookies != null) {
         for (Cookie cookie : cookies)
         {
           if (cookie.getName().equals("shopping_user_session")) {
             id = CommUtil.null2String(cookie.getValue());
           }
         }
       }
       if (id.equals("")) {
         user = null;
       }
     }
 
     return user;
   }
	 
	 /**
		 * 得到当前session
		 */
		public static HttpSession getSession() {
			HttpSession session = getCurRequest().getSession();
			return session;
		}
		
		/**
		 * session中的用户
		 */
		public static User getSessionLoginUser(){
			return (User) getSession().getAttribute("user");
		}
		
		/**
		 * @Title: getCurRequest
		 * @Description:(获得当前的request) 
		 * @param:@return 
		 * @return:HttpServletRequest
		 */
		public static HttpServletRequest getCurRequest(){
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			if(requestAttributes != null && requestAttributes instanceof ServletRequestAttributes){
				ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
				return servletRequestAttributes.getRequest();
			}
			return null;
		}
 }

