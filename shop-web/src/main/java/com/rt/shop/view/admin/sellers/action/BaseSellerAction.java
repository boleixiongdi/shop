 package com.rt.shop.view.admin.sellers.action;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Article;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IArticleService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.MenuTools;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.AreaViewTools;
import com.rt.shop.view.web.tools.OrderViewTools;
import com.rt.shop.view.web.tools.StoreViewTools;
 
 @Controller
 public class BaseSellerAction
 {
 
	 @Autowired
	   private IAccessoryService accessoryService; 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IMessageService messageService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IArticleService articleService;
 
   @Autowired
   private StoreViewTools storeViewTools;
 
   @Autowired
   private OrderViewTools orderViewTools;
 
   @Autowired
   private AreaViewTools areaViewTools;
 
   @Autowired
   private MenuTools menuTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家中心", value="/seller/index.htm*", rtype="buyer", rname="卖家中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/seller/index.htm"})
   public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
   {
	   User user1 =SecurityUserHolder.getSessionLoginUser();
	   ModelAndView mv = null;
	   if(user1!=null){
		    mv = new JModelAndView(
			       "user/default/usercenter/seller_index.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 0, request, response);
			     User user = this.userService.selectById(user1.getId());
			     List msgs = new ArrayList();
			     Message sMessage=new Message();
			     sMessage.setStatus(Integer.valueOf(0));
			     sMessage.setToUser_id(SecurityUserHolder.getCurrentUser().getId());
			     sMessage.setParent_id(null);
			     msgs = this.messageService.selectList("where status=0 and toUser_id='"+SecurityUserHolder.getCurrentUser().getId()+"' and parent_id is null",null);
			     
			     Article sArticle=new Article();
			     sArticle.setDisplay(Boolean.valueOf(true));
			     sArticle.setMark("notice");
			     List articles = this.articleService.selectPage(new Page<Article>(0, 5),sArticle, "addTime desc").getRecords();
			      Store store=storeService.selectById(user.getStore_id());
			      Accessory ac=accessoryService.selectById(store.getStore_logo_id());
			      store.setStore_logo(ac);
			     mv.addObject("articles", articles);
			     mv.addObject("user", user);
			     mv.addObject("store", store);
			     mv.addObject("msgs", msgs);
			     mv.addObject("storeViewTools", this.storeViewTools);
			     mv.addObject("orderViewTools", this.orderViewTools);
			     mv.addObject("areaViewTools", this.areaViewTools);
	   }else{
		   mv = new JModelAndView(
			       "login.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	   }
    
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家中心导航", value="/seller/nav.htm*", rtype="buyer", rname="卖家中心导航", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/seller/nav.htm"})
   public ModelAndView nav(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView("user/default/usercenter/seller_nav.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     int store_status = 0;
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (store != null) {
       store_status = store.getStore_status();
     }
     String op = CommUtil.null2String(request.getAttribute("op"));
     mv.addObject("op", op);
     mv.addObject("store_status", Integer.valueOf(store_status));
     mv.addObject("user", this.userService.selectById(SecurityUserHolder.getCurrentUser().getId()));
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家中心导航", value="/seller/head.htm*", rtype="buyer", rname="卖家中心导航", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/seller/nav_head.htm"})
   public ModelAndView nav_head(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/seller_head.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String type = CommUtil.null2String(request.getAttribute("type"));
     mv.addObject("type", type.equals("") ? "goods" : type);
     mv.addObject("menuTools", this.menuTools);
     mv.addObject("user", this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId()));
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家中心快捷功能设置", value="/seller/store_quick_menu.htm*", rtype="seller", rname="用户中心", rcode="user_center_seller", rgroup="用户中心")
   @RequestMapping({"/seller/store_quick_menu.htm"})
   public ModelAndView store_quick_menu(HttpServletRequest request, HttpServletResponse response) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_quick_menu.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家中心快捷功能设置保存", value="/seller/store_quick_menu_save.htm*", rtype="seller", rname="用户中心", rcode="user_center_seller", rgroup="用户中心")
   @RequestMapping({"/seller/store_quick_menu_save.htm"})
   public ModelAndView store_quick_menu_save(HttpServletRequest request, HttpServletResponse response, String menus) {
     String[] menu_navs = menus.split(",");
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     List list = new ArrayList();
     for (String menu_nav : menu_navs) {
       if (!menu_nav.equals("")) {
         String[] infos = menu_nav.split("\\|");
         Map map = new HashMap();
         map.put("menu_url", infos[0]);
         map.put("menu_name", infos[1]);
         list.add(map);
       }
     }
     user.setStore_quick_menu(Json.toJson(list, JsonFormat.compact()));
     this.userService.updateSelectiveById(user);
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_quick_menu_info.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("user", user);
     mv.addObject("menuTools", this.menuTools);
     return mv;
   }
 }


 
 
 