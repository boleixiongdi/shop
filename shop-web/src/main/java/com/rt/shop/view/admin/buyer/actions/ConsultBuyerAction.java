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
import com.rt.shop.entity.Consult;
import com.rt.shop.entity.query.ConsultQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IConsultService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class ConsultBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IConsultService consultService;
 
   @SecurityMapping(display = false, rsequence = 0, title="买家咨询列表", value="/buyer/consult.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/consult.htm"})
   public ModelAndView consult(HttpServletRequest request, HttpServletResponse response, String reply, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_consult.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     ConsultQueryObject qo = new ConsultQueryObject(currentPage, mv, 
       "addTime", "desc");
     if (!CommUtil.null2String(reply).equals("")) {
       qo.addQuery("obj.reply", 
         new SysMap("reply", 
         Boolean.valueOf(CommUtil.null2Boolean(reply))), "=");
     }
     qo.addQuery("obj.consult_user.id", 
       new SysMap("consult_user", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     Page pList = this.consultService.selectPage(new Page<Consult>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("reply", CommUtil.null2String(reply));
     return mv;
   }
 }


 
 
 