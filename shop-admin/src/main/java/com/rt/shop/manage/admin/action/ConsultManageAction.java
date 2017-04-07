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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.Consult;
import com.rt.shop.entity.query.ConsultQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IConsultService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class ConsultManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IConsultService consultService;
 
   @SecurityMapping(display = false, rsequence = 0, title="咨询列表", value="/admin/consult_list.htm*", rtype="admin", rname="咨询管理", rcode="consult_admin", rgroup="交易")
   @RequestMapping({"/admin/consult_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String consult_user_userName, String consult_content)
   {
     ModelAndView mv = new JModelAndView("admin/blue/consult_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     ConsultQueryObject qo = new ConsultQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.setPageSize(Integer.valueOf(1));
     if ((consult_user_userName != null) && (!consult_user_userName.equals(""))) {
       qo.addQuery("obj.consult_user.userName", 
         new SysMap("userName", 
         CommUtil.null2String(consult_user_userName).trim()), "=");
     }
     if ((consult_content != null) && (!consult_content.equals(""))) {
       qo.addQuery("obj.consult_content", 
         new SysMap("consult_content", 
         "%" + consult_content + "%"), "like");
     }
     Page pList = this.consultService.selectPage(new Page<Consult>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/consult_list.htm", 
       "", params, pList, mv);
     mv.addObject("consult_user_userName", consult_user_userName);
     mv.addObject("consult_content", consult_content);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="咨询删除", value="/admin/consult_del.htm*", rtype="admin", rname="咨询管理", rcode="consult_admin", rgroup="交易")
   @RequestMapping({"/admin/consult_del.htm"})
   public String delete(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Consult consult = this.consultService.selectById(
           Long.valueOf(Long.parseLong(id)));
         this.consultService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:consult_list.htm?currentPage=" + currentPage;
   }
 }


 
 
 