 package com.rt.shop.util;
 
 import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rt.shop.common.constant.Constants;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.spring.SpringContextUtil;
import com.rt.shop.entity.User;
import com.rt.shop.service.IUserService;
 
 public class SecurityUserHolder
 {
	 private static IUserService userService = SpringContextUtil.getBean(IUserService.class);
  
	 public static User getCurrentUser() {
    
     Subject currentUser = SecurityUtils.getSubject();
     if(currentUser!=null && currentUser.getPrincipal() !=null){
    	// return (User)currentUser;
    	 return userService.selectUserByUsername((String)currentUser.getPrincipal());
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
 }

