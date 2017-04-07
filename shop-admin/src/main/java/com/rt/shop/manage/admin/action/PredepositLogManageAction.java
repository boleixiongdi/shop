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
import com.rt.shop.entity.PredepositLog;
import com.rt.shop.entity.query.PredepositLogQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IPredepositLogService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class PredepositLogManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IPredepositLogService predepositlogService;
 
   @SecurityMapping(display = false, rsequence = 0, title="预存款明细列表", value="/admin/predepositlog_list.htm*", rtype="admin", rname="预存款明细", rcode="predeposit", rgroup="会员")
   @RequestMapping({"/admin/predepositlog_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String userName)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/predepositlog_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getDeposit()) {
       String url = this.configService.getSysConfig().getAddress();
       if ((url == null) || (url.equals(""))) {
         url = CommUtil.getURL(request);
       }
       String params = "";
       PredepositLogQueryObject qo = new PredepositLogQueryObject(
         currentPage, mv, orderBy, orderType);
       if (!CommUtil.null2String(userName).equals("")) {
         qo.addQuery("obj.pd_log_user.userName", 
           new SysMap("userName", 
           userName), "=");
       }
       Page pList = this.predepositlogService.selectPage(new Page<PredepositLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView(url + 
         "/admin/predepositlog_list.htm", "", params, pList, mv);
       mv.addObject("userName", userName);
     } else {
       mv = new JModelAndView("admin/blue/error.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_title", "系统未开启预存款");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/operation_base_set.htm");
     }
     return mv;
   }
 }


 
 
 