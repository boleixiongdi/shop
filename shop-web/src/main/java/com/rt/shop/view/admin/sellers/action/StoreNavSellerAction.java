 package com.rt.shop.view.admin.sellers.action;
 
 import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Address;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreNav;
import com.rt.shop.entity.query.StoreNavigationQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IStoreNavService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class StoreNavSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IStoreNavService storenavigationService;
 
   @Autowired
   private IStoreService storeService;
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家导航管理", value="/seller/store_nav.htm*", rtype="seller", rname="导航管理", rcode="store_nav_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_nav.htm"})
   public ModelAndView store_nav(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_nav.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     StoreNavigationQueryObject qo = new StoreNavigationQueryObject(
       currentPage, mv, orderBy, orderType);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     qo.addQuery("obj.store.id", new SysMap("store_id", store.getId()), "=");
 
     Page pList = this.storenavigationService.selectPage(new Page<StoreNav>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/seller/store_nav.htm", "", 
       params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家导航添加", value="/seller/store_nav_add.htm*", rtype="seller", rname="导航管理", rcode="store_nav", rgroup="店铺设置")
   @RequestMapping({"/seller/store_nav_add.htm"})
   public ModelAndView store_nav_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_nav_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家导航编辑", value="/seller/store_nav_edit.htm*", rtype="seller", rname="导航管理", rcode="store_nav", rgroup="店铺设置")
   @RequestMapping({"/seller/store_nav_edit.htm"})
   public ModelAndView store_nav_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_nav_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       StoreNav storenavigation = this.storenavigationService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", storenavigation);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家导航保存", value="/seller/store_nav_save.htm*", rtype="seller", rname="导航管理", rcode="store_nav", rgroup="店铺设置")
   @RequestMapping({"/seller/store_nav_save.htm"})
   public ModelAndView store_nav_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd)
   {
     WebForm wf = new WebForm();
     StoreNav storenavigation = null;
     if (id.equals("")) {
       storenavigation = (StoreNav)wf.toPo(StoreNav.class);
       storenavigation.setAddTime(new Date());
     } else {
    	 StoreNav obj = this.storenavigationService.selectById(
         Long.valueOf(Long.parseLong(id)));
       storenavigation = (StoreNav)wf.toPo(obj);
     }
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     storenavigation.setStore_id(store.getId());
     if (id.equals(""))
       this.storenavigationService.insertSelective(storenavigation);
     else
       this.storenavigationService.updateSelectiveById(storenavigation);
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("url", CommUtil.getURL(request) + "/seller/store_nav.htm");
     mv.addObject("op_title", "保存导航成功");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家导航删除", value="/seller/store_nav_del.htm*", rtype="seller", rname="导航管理", rcode="store_nav", rgroup="店铺设置")
   @RequestMapping({"/seller/store_nav_del.htm"})
   public String store_nav_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         StoreNav storenavigation = this.storenavigationService
           .selectById(Long.valueOf(Long.parseLong(id)));
         this.storenavigationService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:store_nav.htm?currentPage=" + currentPage;
   }
 }


 
 
 