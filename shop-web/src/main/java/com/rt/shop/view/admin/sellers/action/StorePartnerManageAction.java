 package com.rt.shop.view.admin.sellers.action;
 
 import java.util.Date;

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
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StorePartner;
import com.rt.shop.entity.query.StorePartnerQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IStorePartnerService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class StorePartnerManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IStorePartnerService storepartnerService;
 
   @Autowired
   private IStoreService storeService;
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家合作伙伴列表", value="/seller/store_partner.htm*", rtype="seller", rname="友情链接", rcode="store_partner_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_partner.htm"})
   public ModelAndView store_partner(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_partner.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     StorePartnerQueryObject qo = new StorePartnerQueryObject(currentPage, 
       mv, orderBy, orderType);
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     qo.addQuery("obj.store.id", new SysMap("store_id", store.getId()), "=");
 
     Page pList = this.storepartnerService.selectPage(new Page<StorePartner>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/seller/store_partner.htm", 
       "", params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家合作伙伴添加", value="/seller/store_partner_add.htm*", rtype="seller", rname="友情链接", rcode="store_partner_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_partner_add.htm"})
   public ModelAndView store_partner_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_partner_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家合作伙伴编辑", value="/seller/store_partner_edit.htm*", rtype="seller", rname="友情链接", rcode="store_partner_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_partner_edit.htm"})
   public ModelAndView store_partner_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/store_partner_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       StorePartner storepartner = this.storepartnerService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", storepartner);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家合作伙伴保存", value="/seller/store_partner_save.htm*", rtype="seller", rname="友情链接", rcode="store_partner_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_partner_save.htm"})
   public ModelAndView store_partner_save(HttpServletRequest request, HttpServletResponse response,StorePartner storepartner, String id, String currentPage, String cmd, String list_url, String add_url)
   {
   
     if (id.equals("")) {
       storepartner.setAddTime(new Date());
     } else {
       StorePartner obj = this.storepartnerService.selectById(
         Long.valueOf(Long.parseLong(id)));
     }
     Store store = this.storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     storepartner.setStore_id(store.getId());
     if (id.equals(""))
       this.storepartnerService.insertSelective(storepartner);
     else
       this.storepartnerService.updateSelectiveById(storepartner);
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("url", CommUtil.getURL(request) + 
       "/seller/store_partner.htm");
     mv.addObject("op_title", "保存友情链接成功");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家合作伙伴删除", value="/seller/store_partner_del.htm*", rtype="seller", rname="友情链接", rcode="store_partner_seller", rgroup="店铺设置")
   @RequestMapping({"/seller/store_partner_del.htm"})
   public String store_partner_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         StorePartner storepartner = this.storepartnerService
           .selectById(Long.valueOf(Long.parseLong(id)));
         this.storepartnerService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:store_partner.htm?currentPage=" + currentPage;
   }
 }


 
 
 