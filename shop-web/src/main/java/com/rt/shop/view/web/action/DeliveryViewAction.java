 package com.rt.shop.view.web.action;
 
 import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.DeliveryGoods;
import com.rt.shop.entity.query.DeliveryGoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IDeliveryGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class DeliveryViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IDeliveryGoodsService deliveryGoodsService;
 
   @RequestMapping({"/delivery.htm"})
   public ModelAndView delivery(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("delivery.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     DeliveryGoodsQueryObject qo = new DeliveryGoodsQueryObject(currentPage, 
       mv, orderBy, orderType);
     qo.addQuery("obj.d_status", new SysMap("d_status", Integer.valueOf(1)), "=");
     qo.addQuery("obj.d_begin_time", new SysMap("d_begin_time", new Date()), 
       "<=");
     qo.addQuery("obj.d_end_time", new SysMap("d_end_time", new Date()), 
       ">=");
     qo.setPageSize(Integer.valueOf(20));
     Page pList = this.deliveryGoodsService.selectPage(new Page<DeliveryGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 }


 
 
 