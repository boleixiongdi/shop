 package com.rt.shop.view.web.action;
 
 import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Chatting;
import com.rt.shop.entity.ChattingFriend;
import com.rt.shop.entity.ChattingLog;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserFriend;
import com.rt.shop.entity.query.ChattingLogQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IChattingFriendService;
import com.rt.shop.service.IChattingLogService;
import com.rt.shop.service.IChattingService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserFriendService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.UserTools;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class ChattingViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserFriendService snsFriendService;
 
   @Autowired
   private UserTools userTools;
 
   @Autowired
   private IChattingFriendService chattingFriendService;
 
   @Autowired
   private IChattingLogService chattinglogService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IChattingService chattingService;
 
   @RequestMapping({"/chatting.htm"})
   public ModelAndView chatting(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("chatting.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
 

     UserFriend sUserFriend=new UserFriend();
     sUserFriend.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
     List<UserFriend> Friends = this.snsFriendService.selectList(sUserFriend);
     //  "select obj from SnsFriend obj where obj.fromUser.id=:uid ", 
     mv.addObject("Friends", Friends);
     mv.addObject("userTools", this.userTools);
 
     if (Friends.size() > 0) {
       int count = 0;
       for (UserFriend friend : Friends) {
         boolean flag = this.userTools.userOnLine(userService.selectById(friend.getToUser_id()).getUserName());
         if (flag) {
           count++;
         }
         mv.addObject("OnlineCount", Integer.valueOf(count));
       }
     }
 
    
     ChattingFriend sChattingFriend=new ChattingFriend();
     sChattingFriend.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List Contactings = this.chattingFriendService.selectPage(new Page<ChattingFriend>(0, 15),sChattingFriend, "addTime desc").getRecords();
     mv.addObject("Contactings", Contactings);
 
     ChattingLog sChattingLog=new ChattingLog();
     sChattingLog.setMark(Integer.valueOf(0));
     sChattingLog.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List<ChattingLog> unreads = this.chattinglogService.selectListOr1(sChattingLog,null);
       
     mv.addObject("unreads", unreads);
     Object list = new ArrayList();
     for (int i = 1; i <= 60; i++) {
       ((List)list).add(Integer.valueOf(i));
     }
     mv.addObject("emoticons", list);
     return (ModelAndView)mv;
   }
 
   @RequestMapping({"/chatting_refresh.htm"})
   public ModelAndView chatting_refresh(HttpServletRequest request, HttpServletResponse response, String user_id)
   {
     ModelAndView mv = new JModelAndView("chatting_logs.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     Chatting chatting = null;
     User user = this.userService.selectById(CommUtil.null2Long(user_id));
     if ((SecurityUserHolder.getCurrentUser() != null) && 
       (!SecurityUserHolder.getCurrentUser().equals(""))) {
      
       Chatting sChatting=new Chatting();
       sChatting.setUser1_id(SecurityUserHolder.getCurrentUser().getId());
       sChatting.setUser2_id(CommUtil.null2Long(user_id));
       String sql="where user1_id="+SecurityUserHolder.getCurrentUser().getId()+" and user2_id="+CommUtil.null2Long(user_id)+" or user1_id="+CommUtil.null2Long(user_id)+" and user2_id="+SecurityUserHolder.getCurrentUser().getId()+"";
       List<Chatting> chattings = this.chattingService.selectList(sql, null);
         
         
       if (chattings.size() > 0) {
         chatting = (Chatting)chattings.get(0);
 
         String sql1=" where chatting_id="+chatting.getId()+" and mark=0 and user_id="+SecurityUserHolder.getCurrentUser().getId()+" order by addTime asc";
         List<ChattingLog> logs = this.chattinglogService.selectList(sql1,null);
        //   "select obj from ChattingLog obj where obj.chatting.id=:chat_id and obj.mark=:mark and obj.user.id=:user_id order by addTime asc", 
         mv.addObject("logs", logs);
         for (ChattingLog log : logs) {
           if (log.getUser_id() == 
             SecurityUserHolder.getCurrentUser().getId()) continue;
           log.setMark(1);
           this.chattinglogService.updateSelectiveById(log);
         }
 
       }
 
     }
 
     return mv;
   }
 
   @RequestMapping({"/chatting_ShowHistory.htm"})
   public ModelAndView chatting_ShowHistory(HttpServletRequest request, HttpServletResponse response, String user_id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("chatting_logs.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     Chatting chatting = null;
     if ((SecurityUserHolder.getCurrentUser() != null) && 
       (!SecurityUserHolder.getCurrentUser().equals(""))) {
    	 Chatting sChatting=new Chatting();
         sChatting.setUser1_id(SecurityUserHolder.getCurrentUser().getId());
         sChatting.setUser2_id(CommUtil.null2Long(user_id));
         List<Chatting> chattings = this.chattingService.selectListOr(sChatting);
       if (chattings.size() > 0) {
         chatting = (Chatting)chattings.get(0);
 
         ChattingLogQueryObject qo = new ChattingLogQueryObject(
           currentPage, mv, null, null);
         qo.addQuery("obj.chatting.id", 
           new SysMap("chatting_id", 
           chatting.getId()), "=");
         qo.setOrderBy("addTime");
         qo.setOrderType("desc");
         qo.setPageSize(Integer.valueOf(10));
         Page pList = this.chattinglogService.selectPage(new Page<ChattingLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
 
         mv.addObject("historys", pList.getRecords());
         String Ajax_url = CommUtil.getURL(request) + 
           "/chatting_ShowHistory.htm";
         mv
           .addObject("gotoPageAjaxHTML", 
           CommUtil.showPageAjaxHtml(Ajax_url, "", pList
           .getCurrent(), pList.getPages()));
       }
     }
     return mv;
   }
 
   @RequestMapping({"/chatting_save.htm"})
   public ModelAndView chatting_save(HttpServletRequest request, HttpServletResponse response, String user_id, String content)
   {
     ModelAndView mv = new JModelAndView("chatting_logs.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     Chatting chatting = null;
     User user = this.userService.selectById(CommUtil.null2Long(user_id));
     Chatting sChatting=new Chatting();
     sChatting.setUser1_id(SecurityUserHolder.getCurrentUser().getId());
     sChatting.setUser2_id(CommUtil.null2Long(user_id));
     List<Chatting> chattings = this.chattingService.selectListOr(sChatting);
     if (chattings.size() > 0) {
       chatting = (Chatting)chattings.get(0);
     } else {
       chatting = new Chatting();
       chatting.setAddTime(new Date());
       chatting.setUser1_id(SecurityUserHolder.getCurrentUser().getId());
       chatting.setUser2_id(user.getId());
       this.chattingService.insertSelective(chatting);
     }
     ChattingLog log = new ChattingLog();
     log.setAddTime(new Date());
     log.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     log.setContent(content);
     log.setChatting_id(chatting.getId());
     this.chattinglogService.insertSelective(log);
 
    
     ChattingFriend sChattingFriend=new ChattingFriend();
     sChattingFriend.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sChattingFriend.setFriendUser_id(CommUtil.null2Long(user_id));
     List ChattingFriends = this.chattingFriendService.selectList(sChattingFriend);

   //    "select obj from ChattingFriend obj where obj.user.id=:uid and obj.friendUser.id=:user_id", 
  
     if (ChattingFriends.size() == 0) {
       ChattingFriend contact = new ChattingFriend();
       contact.setAddTime(new Date());
       contact.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       contact.setFriendUser_id(user.getId());
       this.chattingFriendService.insertSelective(contact);
     }
 
     ChattingFriend ssChattingFriend=new ChattingFriend();
     ssChattingFriend.setUser_id(CommUtil.null2Long(user_id));
     ssChattingFriend.setFriendUser_id(SecurityUserHolder.getCurrentUser().getId());
     List ChattingFriends2 = this.chattingFriendService.selectList(ssChattingFriend);
     
     
     if (ChattingFriends2.size() == 0) {
       ChattingFriend contact = new ChattingFriend();
       contact.setAddTime(new Date());
       contact.setUser_id(user.getId());
       contact.setFriendUser_id(SecurityUserHolder.getCurrentUser().getId());
       this.chattingFriendService.insertSelective(contact);
     }
 
     
     ChattingLog sChattingLog=new ChattingLog();
     sChattingLog.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sChattingLog.setChatting_id(chatting.getId());
     List logs = this.chattinglogService.selectList(sChattingLog, "addTime desc");
      // "select obj from ChattingLog obj where obj.chatting.id=:chat_id  and obj.user.id=:uid order by addTime desc", 
     mv.addObject("logs", logs);
     return mv;
   }
 }


 
 
 