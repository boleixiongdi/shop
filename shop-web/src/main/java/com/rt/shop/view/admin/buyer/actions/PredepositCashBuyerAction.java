 package com.rt.shop.view.admin.buyer.actions;
 
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
import com.rt.shop.entity.PredepositCash;
import com.rt.shop.entity.PredepositLog;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.PredepositCashQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IPredepositCashService;
import com.rt.shop.service.IPredepositLogService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class PredepositCashBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IPredepositCashService predepositCashService;
 
   @Autowired
   private IPredepositLogService predepositLogService;
 
   @Autowired
   private IUserService userService;
 
   @SecurityMapping(display = false, rsequence = 0, title="提现管理", value="/buyer/buyer_cash.htm*", rtype="buyer", rname="预存款管理", rcode="predeposit_set", rgroup="用户中心")
   @RequestMapping({"/buyer/buyer_cash.htm"})
   public ModelAndView buyer_cash(HttpServletRequest request, HttpServletResponse response, String id)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_cash.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getDeposit()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启预存款");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/index.htm");
     } else {
       mv.addObject("availableBalance", 
         Double.valueOf(CommUtil.null2Double(this.userService.selectById(
         SecurityUserHolder.getCurrentUser().getId())
         .getAvailableBalance())));
     }
 
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="提现管理保存", value="/buyer/buyer_cash_save.htm*", rtype="buyer", rname="预存款管理", rcode="predeposit_set", rgroup="用户中心")
   @RequestMapping({"/buyer/buyer_cash_save.htm"})
   public ModelAndView buyer_cash_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView("success.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     WebForm wf = new WebForm();
     PredepositCash obj = (PredepositCash)wf.toPo(PredepositCash.class);
     obj.setCash_sn("cash" + 
       CommUtil.formatTime("yyyyMMddHHmmss", new Date()) + 
       SecurityUserHolder.getCurrentUser().getId());
     obj.setAddTime(new Date());
     obj.setCash_user_id(SecurityUserHolder.getCurrentUser().getId());
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
 
     if (CommUtil.null2Double(obj.getCash_amount()) <= 
       CommUtil.null2Double(user.getAvailableBalance())) {
       this.predepositCashService.insertSelective(obj);
 
       PredepositLog log = new PredepositLog();
       log.setAddTime(new Date());
       log.setPd_log_amount(obj.getCash_amount());
       log.setPd_log_info("申请提现");
       log.setPd_log_user_id(obj.getCash_user_id());
       log.setPd_op_type("提现");
       log.setPd_type("可用预存款");
       this.predepositLogService.insertSelective(log);
       mv.addObject("op_title", "提现申请成功");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "提现金额大于用户余额，提现失败");
     }
 
     mv.addObject("url", CommUtil.getURL(request) + "/buyer/buyer_cash.htm");
 
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="提现管理", value="/buyer/buyer_cash_list.htm*", rtype="buyer", rname="预存款管理", rcode="predeposit_set", rgroup="用户中心")
   @RequestMapping({"/buyer/buyer_cash_list.htm"})
   public ModelAndView buyer_cash_list(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_cash_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getDeposit()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启预存款");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/index.htm");
     } else {
       PredepositCashQueryObject qo = new PredepositCashQueryObject(
         currentPage, mv, "addTime", "desc");
       qo.addQuery("obj.cash_user.id", 
         new SysMap("user_id", 
         SecurityUserHolder.getCurrentUser().getId()), "=");
       Page pList = this.predepositCashService.selectPage(new Page<PredepositCash>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="会员提现详情", value="/buyer/buyer_cash_view.htm*", rtype="buyer", rname="预存款管理", rcode="predeposit_set", rgroup="用户中心")
   @RequestMapping({"/buyer/buyer_cash_view.htm"})
   public ModelAndView buyer_cash_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_cash_view.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getDeposit()) {
       PredepositCash obj = this.predepositCashService.selectById(
         CommUtil.null2Long(id));
       mv.addObject("obj", obj);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启预存款");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/index.htm");
     }
     return mv;
   }
 }


 
 
 