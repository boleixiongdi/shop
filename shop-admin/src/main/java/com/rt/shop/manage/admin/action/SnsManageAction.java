 package com.rt.shop.manage.admin.action;
 
 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Dynamic;
import com.rt.shop.entity.query.DynamicQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IDynamicService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.tools.StoreViewTools;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class SnsManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IDynamicService dynamicService;
 
   @Autowired
   private StoreViewTools storeViewTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="会员动态列表", value="/admin/sns_user.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/sns_user.htm"})
   public ModelAndView sns_user(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String condition, String userName)
   {
     ModelAndView mv = new JModelAndView("admin/blue/sns_user.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.dissParent.id is null", null);
     if ((userName != null) && (!userName.equals(""))) {
       qo.addQuery("obj.user.userName", 
         new SysMap("obj_userName", "%" + 
         userName.trim() + "%"), "like");
       mv.addObject("userName", userName);
     }
     qo.setPageSize(Integer.valueOf(10));
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="会员动态删除", value="/admin/sns_del.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/sns_del.htm"})
   public String sns_user_del(HttpServletRequest request, HttpServletResponse response, String currentPage, String mulitId) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       Dynamic obj = this.dynamicService
         .selectById(CommUtil.null2Long(id));
       this.dynamicService.deleteById(CommUtil.null2Long(id));
     }
     String url = "redirect:/admin/sns_user.htm?currentPage=" + currentPage;
     return url;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="店铺动态列表", value="/admin/sns_store.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/sns_store.htm"})
   public ModelAndView sns_store(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String condition, String store_name) {
     ModelAndView mv = new JModelAndView("admin/blue/sns_store.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     DynamicQueryObject qo = new DynamicQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.dissParent.id is null", null);
     if ((store_name != null) && (!store_name.equals(""))) {
       qo.addQuery("obj.store.store_name", 
         new SysMap("obj_store_name", 
         "%" + store_name.trim() + "%"), "like");
       mv.addObject("store_name", store_name);
     }
     qo.addQuery("obj.store.id is not null", null);
     qo.setPageSize(Integer.valueOf(10));
     qo.setOrderBy("addTime");
     qo.setOrderType("desc");
     Page pList = this.dynamicService.selectPage(new Page<Dynamic>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("storeViewTools", this.storeViewTools);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="店铺动态删除", value="/admin/sns_store_del.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/sns_store_del.htm"})
   public String sns_store_del(HttpServletRequest request, HttpServletResponse response, String currentPage, String mulitId) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       Dynamic obj = this.dynamicService
         .selectById(CommUtil.null2Long(id));
       this.dynamicService.deleteById(CommUtil.null2Long(id));
     }
     String url = "redirect:/admin/sns_store.htm?currentPage=" + currentPage;
     return url;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="sns动态设置可见度", value="/admin/sns_set_display.htm*", rtype="admin", rname="会员管理", rcode="user_manage", rgroup="会员")
   @RequestMapping({"/admin/sns_set_display.htm"})
   public String sns_set_display(HttpServletRequest request, HttpServletResponse response, String currentPage, String mulitId, String type, String mark) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       Dynamic obj = this.dynamicService
         .selectById(CommUtil.null2Long(id));
       if ((obj != null) && (!obj.equals(""))) {
         if (type.equals("show")) {
           if (!obj.getDisplay()) {
             obj.setDisplay(true);
           }
         }
         else if (obj.getDisplay()) {
           obj.setDisplay(false);
         }
 
         this.dynamicService.updateSelectiveById(obj);
       }
     }
     String url = "redirect:/admin/sns_" + mark + ".htm?currentPage=" + 
       currentPage;
     return url;
   }
 }


 
 
 