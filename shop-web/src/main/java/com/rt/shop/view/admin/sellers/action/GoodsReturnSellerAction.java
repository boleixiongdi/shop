 package com.rt.shop.view.admin.sellers.action;
 
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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.GoodsReturn;
import com.rt.shop.entity.query.GoodsReturnQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IGoodsReturnService;
import com.rt.shop.service.IGoodsReturnitemService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class GoodsReturnSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsReturnService goodsReturnService;
 
   @Autowired
   private IGoodsReturnitemService goodsReturnItemService;
 
   @SecurityMapping(display = false, rsequence = 0, title="卖家退货列表", value="/seller/goods_return.htm*", rtype="seller", rname="退货管理", rcode="goods_return_seller", rgroup="客户服务")
   @RequestMapping({"/seller/goods_return.htm"})
   public ModelAndView refund(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String data_type, String data, String beginTime, String endTime)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/goods_return.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     GoodsReturnQueryObject qo = new GoodsReturnQueryObject(currentPage, mv, 
       "addTime", "desc");
     qo.setPageSize(Integer.valueOf(30));
     if (!CommUtil.null2String(data).equals("")) {
       if (CommUtil.null2String(data_type).equals("order_id")) {
         qo.addQuery("obj.of.order_id", new SysMap("order_id", data), 
           "=");
       }
       if (CommUtil.null2String(data_type).equals("buyer_name")) {
         qo.addQuery("obj.of.user.userName", 
           new SysMap("userName", data), "=");
       }
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
       qo.addQuery("obj.addTime", 
         new SysMap("beginTime", 
         CommUtil.formatDate(beginTime)), ">=");
     }
     if (!CommUtil.null2String(endTime).equals("")) {
       qo.addQuery("obj.addTime", 
         new SysMap("endTime", 
         CommUtil.formatDate(endTime)), "<=");
     }
     qo.addQuery("obj.user.id", 
       new SysMap("user_id", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     Page pList = this.goodsReturnService.selectPage(new Page<GoodsReturn>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("data_type", data_type);
     mv.addObject("data", data);
     mv.addObject("beginTime", beginTime);
     mv.addObject("endTime", endTime);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="卖家退款列表", value="/seller/return_view.htm*", rtype="seller", rname="退货管理", rcode="goods_return_seller", rgroup="客户服务")
   @RequestMapping({"/seller/return_view.htm"})
   public ModelAndView return_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/return_view.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     GoodsReturn obj = this.goodsReturnService.selectById(
       CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     return mv;
   }
 }


 
 
 