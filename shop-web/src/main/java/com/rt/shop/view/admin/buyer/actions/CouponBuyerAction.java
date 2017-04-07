 package com.rt.shop.view.admin.buyer.actions;
 
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
import com.rt.shop.entity.CouponInfo;
import com.rt.shop.entity.query.CouponInfoQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.ICouponInfoService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class CouponBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private ICouponInfoService couponInfoService;
 
   @SecurityMapping(display = false, rsequence = 0, title="买家优惠券列表", value="/buyer/coupon.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/coupon.htm"})
   public ModelAndView coupon(HttpServletRequest request, HttpServletResponse response, String reply, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_coupon.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     CouponInfoQueryObject qo = new CouponInfoQueryObject(currentPage, mv, 
       "addTime", "desc");
     qo.addQuery("obj.user.id", 
       new SysMap("user_id", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     Page pList = this.couponInfoService.selectPage(new Page<CouponInfo>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
 }


 
 
 