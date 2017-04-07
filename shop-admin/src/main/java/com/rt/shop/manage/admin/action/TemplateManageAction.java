 package com.rt.shop.manage.admin.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Address;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.query.TemplateQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class TemplateManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private ITemplateService templateService;
 
   @SecurityMapping(display = false, rsequence = 0, title="模板列表", value="/admin/template_list.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_list.htm"})
   public ModelAndView template_list(HttpServletRequest request, HttpServletResponse response, String type, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("admin/blue/template_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     TemplateQueryObject qo = new TemplateQueryObject(currentPage, mv, 
       orderBy, orderType);
     if ((type == null) || (type.equals("")))
       type = "msg";
     qo.addQuery("obj.type", new SysMap("type", type), "=");
     Page pList = this.templateService.selectPage(new Page<Template>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/template_list.htm", 
       "", params, pList, mv);
     mv.addObject("type", type);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="模板添加", value="/admin/template_add.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_add.htm"})
   public ModelAndView template_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/template_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="模板编辑", value="/admin/template_edit.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_edit.htm"})
   public ModelAndView template_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/template_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Template template = this.templateService.selectById(
         Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", template);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="模板保存", value="/admin/template_save.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_save.htm"})
   public ModelAndView template_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url, String add_url)
   {
     WebForm wf = new WebForm();
     Template template = null;
     if (id.equals("")) {
       template = (Template)wf.toPo(request, Template.class);
       template.setAddTime(new Date());
     } else {
       Template obj = this.templateService.selectById(Long.valueOf(Long.parseLong(id)));
       template = (Template)wf.toPo(request, obj);
     }
 
     if (id.equals(""))
       this.templateService.insertSelective(template);
     else
       this.templateService.updateSelectiveById(template);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url + "?type=" + template.getType());
     mv.addObject("op_title", "保存模板成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="模板AJAX更新", value="/admin/template_ajax.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_ajax.htm"})
   public void template_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     Template obj = this.templateService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = Template.class.getDeclaredFields();
     BeanWrapper wrapper = new BeanWrapper(obj);
     Object val = null;
     for (Field field : fields)
     {
       if (field.getName().equals(fieldName)) {
         Class clz = Class.forName("java.lang.String");
         if (field.getType().getName().equals("int")) {
           clz = Class.forName("java.lang.Integer");
         }
         if (field.getType().getName().equals("boolean")) {
           clz = Class.forName("java.lang.Boolean");
         }
         if (!value.equals(""))
           val = BeanUtils.convertType(value, clz);
         else {
           val = Boolean.valueOf(
             !CommUtil.null2Boolean(wrapper
             .getPropertyValue(fieldName)));
         }
         wrapper.setPropertyValue(fieldName, val);
       }
     }
     this.templateService.updateSelectiveById(obj);
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(val.toString());
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="模板开启", value="/admin/template_open.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_open.htm"})
   public String template_open(HttpServletRequest request, String mulitId, String currentPage, String type) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Template obj = this.templateService.selectById(
           Long.valueOf(Long.parseLong(id)));
         obj.setOpen(true);
         this.templateService.updateSelectiveById(obj);
       }
     }
     return "redirect:template_list.htm?currentPage=" + currentPage + 
       "&type=" + type;
   }
 
   @RequestMapping({"/template/verify_mark.htm"})
   public void verify_mark(HttpServletRequest request, HttpServletResponse response, String mark, String id) {
     boolean ret = true;
    
     String sql="where mark='"+mark+"' and id!="+CommUtil.null2Long(id);
     List list = this.templateService.selectList(sql,null);

     if (list.size() > 0)
       ret = false;
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="模板编辑", value="/admin/template_copy.htm*", rtype="admin", rname="通知模板", rcode="template_set", rgroup="设置")
   @RequestMapping({"/admin/template_copy.htm"})
   public ModelAndView template_copy(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/template_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Template template = this.templateService.selectById(
         Long.valueOf(Long.parseLong(id)));
       template.setId(null);
       mv.addObject("obj", template);
       mv.addObject("currentPage", currentPage);
     }
     return mv;
   }
 }


 
 
 