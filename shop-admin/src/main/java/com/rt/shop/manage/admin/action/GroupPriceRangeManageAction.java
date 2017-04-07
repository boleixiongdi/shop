 package com.rt.shop.manage.admin.action;
 
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
import com.rt.shop.entity.GroupPriceRange;
import com.rt.shop.entity.query.GroupPriceRangeQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IGroupPriceRangeService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class GroupPriceRangeManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGroupPriceRangeService grouppricerangeService;
 
   @SecurityMapping(display = false, rsequence = 0, title="团购价格区间列表", value="/admin/group_price_list.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_price_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("admin/blue/group_price_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     GroupPriceRangeQueryObject qo = new GroupPriceRangeQueryObject(
       currentPage, mv, orderBy, orderType);
 
     Page pList = this.grouppricerangeService.selectPage(new Page<GroupPriceRange>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(
       url + "/admin/group_range_list.htm", "", params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购价格区间列表", value="/admin/group_price_add.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_price_add.htm"})
   public ModelAndView add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/group_price_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购价格区间列表", value="/admin/group_price_edit.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_price_edit.htm"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/group_price_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       GroupPriceRange grouppricerange = this.grouppricerangeService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", grouppricerange);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购价格区间保存", value="/admin/group_price_save.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_price_save.htm"})
   public ModelAndView save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd)
   {
     WebForm wf = new WebForm();
     GroupPriceRange grouppricerange = null;
     if (id.equals("")) {
       grouppricerange = (GroupPriceRange)wf.toPo(request, GroupPriceRange.class);
       grouppricerange.setAddTime(new Date());
     } else {
       GroupPriceRange obj = this.grouppricerangeService.selectById(
         Long.valueOf(Long.parseLong(id)));
       grouppricerange = (GroupPriceRange)wf.toPo(request, obj);
     }
 
     if (id.equals(""))
       this.grouppricerangeService.insertSelective(grouppricerange);
     else
       this.grouppricerangeService.updateSelectiveById(grouppricerange);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/group_price_list.htm");
     mv.addObject("op_title", "保存价格区间成功");
     mv.addObject("add_url", CommUtil.getURL(request) + 
       "/admin/group_price_add.htm" + "?currentPage=" + currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="团购价格区间删除", value="/admin/group_price_del.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_price_del.htm"})
   public String delete(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         GroupPriceRange grouppricerange = this.grouppricerangeService
           .selectById(Long.valueOf(Long.parseLong(id)));
         this.grouppricerangeService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:group_price_list.htm?currentPage=" + currentPage;
   }
 }


 
 
 