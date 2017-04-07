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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.ComplaintSubject;
import com.rt.shop.entity.query.ComplaintSubjectQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IComplaintSubjectService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class ComplaintSubjectManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IComplaintSubjectService complaintsubjectService;
 
   @SecurityMapping(display = false, rsequence = 0, title="投诉主题列表", value="/admin/complaintsubject_list.htm*", rtype="admin", rname="投诉管理", rcode="complaint_manage", rgroup="交易")
   @RequestMapping({"/admin/complaintsubject_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/complaintsubject_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     ComplaintSubjectQueryObject qo = new ComplaintSubjectQueryObject(
       currentPage, mv, orderBy, orderType);
     WebForm wf = new WebForm();
     wf.toQueryPo(request, qo, ComplaintSubject.class, mv);
     Page pList = this.complaintsubjectService.selectPage(new Page<ComplaintSubject>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + 
       "/admin/complaintsubject_list.htm", "", params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="投诉主题添加", value="/admin/complaintsubject_add.htm*", rtype="admin", rname="投诉管理", rcode="complaint_manage", rgroup="交易")
   @RequestMapping({"/admin/complaintsubject_add.htm"})
   public ModelAndView add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/complaintsubject_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="投诉主题编辑", value="/admin/complaintsubject_edit.htm*", rtype="admin", rname="投诉管理", rcode="complaint_manage", rgroup="交易")
   @RequestMapping({"/admin/complaintsubject_edit.htm"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/complaintsubject_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       ComplaintSubject complaintsubject = this.complaintsubjectService
         .selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", complaintsubject);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="投诉主题保存", value="/admin/complaintsubject_save.htm*", rtype="admin", rname="投诉管理", rcode="complaint_manage", rgroup="交易")
   @RequestMapping({"/admin/complaintsubject_save.htm"})
   public ModelAndView save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url, String add_url)
   {
     WebForm wf = new WebForm();
     ComplaintSubject complaintsubject = null;
     if (id.equals("")) {
       complaintsubject = (ComplaintSubject)wf.toPo(request, ComplaintSubject.class);
       complaintsubject.setAddTime(new Date());
     } else {
       ComplaintSubject obj = this.complaintsubjectService.selectById(
         Long.valueOf(Long.parseLong(id)));
       complaintsubject = (ComplaintSubject)wf.toPo(request, obj);
     }
 
     if (id.equals(""))
       this.complaintsubjectService.insertSelective(complaintsubject);
     else
       this.complaintsubjectService.updateSelectiveById(complaintsubject);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存投诉主题成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="投诉主题删除", value="/admin/complaintsubject_del.htm*", rtype="admin", rname="投诉管理", rcode="complaint_manage", rgroup="交易")
   @RequestMapping({"/admin/complaintsubject_del.htm"})
   public String delete(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         ComplaintSubject complaintsubject = this.complaintsubjectService
           .selectById(Long.valueOf(Long.parseLong(id)));
         this.complaintsubjectService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:complaintsubject_list.htm?currentPage=" + currentPage;
   }
 }


 
 
 