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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.GroupClass;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.entity.query.GroupClassQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IGroupClassService;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class GroupClassManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGroupClassService groupclassService;
 
   @Autowired
   private IGroupGoodsService groupgoodsService;
 
   @SecurityMapping(display = false, rsequence = 0, title="团购分类列表", value="/admin/group_class_list.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_class_list.htm"})
   public ModelAndView list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("admin/blue/group_class_list.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     GroupClassQueryObject qo = new GroupClassQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.parent.id is null", null);
     Page pList = this.groupclassService.selectPage(new Page<GroupClass>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(
       url + "/admin/group_class_list.htm", "", params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购分类增加", value="/admin/group_class_add.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_class_add.htm"})
   public ModelAndView add(HttpServletRequest request, HttpServletResponse response, String currentPage, String pid)
   {
     ModelAndView mv = new JModelAndView("admin/blue/group_class_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     GroupClass sGroupClass=new GroupClass();
     sGroupClass.setParent_id(null);
     List gcs = this.groupclassService.selectList(sGroupClass);
     GroupClass parent = this.groupclassService.selectById(
       CommUtil.null2Long(pid));
     GroupClass obj = new GroupClass();
     obj.setParent(parent);
     mv.addObject("obj", obj);
     mv.addObject("gcs", gcs);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购分类编辑", value="/admin/group_class_edit.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_class_edit.htm"})
   public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/group_class_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       GroupClass groupclass = this.groupclassService.selectById(
         Long.valueOf(Long.parseLong(id)));
       GroupClass sGroupClass=new GroupClass();
       sGroupClass.setParent_id(null);
       List gcs = this.groupclassService.selectList(sGroupClass);
       mv.addObject("gcs", gcs);
       mv.addObject("obj", groupclass);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购分类保存", value="/admin/group_class_save.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_class_save.htm"})
   public ModelAndView save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String pid)
   {
     WebForm wf = new WebForm();
     GroupClass groupclass = null;
     if (id.equals("")) {
       groupclass = (GroupClass)wf.toPo(request, GroupClass.class);
       groupclass.setAddTime(new Date());
     } else {
       GroupClass obj = this.groupclassService.selectById(
         Long.valueOf(Long.parseLong(id)));
       groupclass = (GroupClass)wf.toPo(request, obj);
     }
     GroupClass parent = this.groupclassService.selectById(
       CommUtil.null2Long(pid));
     if (parent != null) {
       groupclass.setParent(parent);
       groupclass.setGc_level(parent.getGc_level() + 1);
     }
     if (id.equals(""))
       this.groupclassService.insertSelective(groupclass);
     else
       this.groupclassService.updateSelectiveById(groupclass);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/group_class_list.htm");
     mv.addObject("op_title", "保存团购分类成功");
     mv.addObject("add_url", CommUtil.getURL(request) + 
       "/admin/group_class_add.htm" + "?currentPage=" + currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="团购分类删除", value="/admin/group_class_del.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_class_del.htm"})
   public String delete(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         GroupClass groupclass = this.groupclassService.selectById(
           Long.valueOf(Long.parseLong(id)));
         for (GroupGoods gg : groupclass.getGgs()) {
           if (gg != null) {
             gg.setGg_gc(null);
             this.groupgoodsService.updateSelectiveById(gg);
           }
         }
         this.groupclassService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:group_class_list.htm?currentPage=" + currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="团购分类Ajax更新", value="/admin/group_class_ajax.htm*", rtype="admin", rname="团购管理", rcode="group_admin", rgroup="运营")
   @RequestMapping({"/admin/group_class_ajax.htm"})
   public void ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     GroupClass obj = this.groupclassService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = GroupClass.class.getDeclaredFields();
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
     this.groupclassService.updateSelectiveById(obj);
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
 
   @SecurityMapping(display = false, rsequence = 0, title="团购分类下级加载", value="/admin/group_class_data.htm*", rtype="admin", rname="分类管理", rcode="goods_class", rgroup="商品")
   @RequestMapping({"/admin/group_class_data.htm"})
   public ModelAndView group_class_data(HttpServletRequest request, HttpServletResponse response, String pid, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/group_class_data.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
    
     GroupClass sGroupClass=new GroupClass();
     sGroupClass.setParent_id(CommUtil.null2Long(pid));
     List gcs = this.groupclassService.selectList(sGroupClass);
     mv.addObject("gcs", gcs);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @RequestMapping({"/admin/group_class_verify.htm"})
   public void group_class_verify(HttpServletRequest request, HttpServletResponse response, String gc_name, String id, String pid) {
     boolean ret = true;
     
     String sql="where gc_name='"+gc_name+"' and parent_id="+CommUtil.null2Long(pid)+" and id!="+CommUtil.null2Long(id);
     List gcs = this.groupclassService.selectList(sql,null);

     if ((gcs != null) && (gcs.size() > 0)) {
       ret = false;
     }
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
 }


 
 
 