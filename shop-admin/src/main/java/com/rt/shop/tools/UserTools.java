/* package com.rt.shop.tools;
 
 import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.User;
import com.rt.shop.service.IUserService;
 
 @Component
 public class UserTools
 {
 
   @Autowired(required=false)
   private SessionRegistry sessionRegistry;
 
   @Autowired
   private IUserService userSerivce;
 
   public List<User> query_user()
   {
     List users = new ArrayList();
     List objs = this.sessionRegistry.getAllPrincipals();
     for (int i = 0; i < objs.size(); i++) {
    	 User sUser=new User();
    	 sUser.setUserName(CommUtil.null2String(objs.get(i)));
       User user = this.userSerivce.selectOne(sUser);
       users.add(user);
     }
 
     return users;
   }
 
   public boolean userOnLine(String userName)
   {
     boolean ret = false;
     List<User> users = query_user();
     for (User user : users) {
       if ((user != null) && (user.getUserName().equals(userName.trim()))) {
         ret = true;
       }
     }
     return ret;
   }
 }


 
 
 */