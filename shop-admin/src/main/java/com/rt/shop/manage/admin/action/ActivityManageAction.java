 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Activity;
import com.rt.shop.entity.ActivityGoods;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.query.ActivityGoodsQueryObject;
import com.rt.shop.entity.query.ActivityQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IActivityGoodsService;
import com.rt.shop.service.IActivityService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class ActivityManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IActivityService activityService;
 
   @Autowired
   private IActivityGoodsService activityGoodsService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IGoodsService goodService;
 
   @SecurityMapping(display = false, rsequence = 0, title="活动列表", value="/admin/activity_list.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_list.htm"})
   public ModelAndView activity_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String q_ac_title, String ac_status, String beginTime, String endTime)
   {
     ModelAndView mv = new JModelAndView("admin/blue/activity_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     ActivityQueryObject qo = new ActivityQueryObject(currentPage, mv, 
       orderBy, orderType);
     if (!CommUtil.null2String(q_ac_title).equals("")) {
       qo.addQuery("obj.ac_title", 
         new SysMap("q_ac_title", "%" + 
         q_ac_title.trim() + "%"), "like");
     }
     if (!CommUtil.null2String(ac_status).equals("")) {
       qo.addQuery("obj.ac_status", 
         new SysMap("ac_status", 
         Integer.valueOf(CommUtil.null2Int(ac_status))), "=");
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
       qo.addQuery("obj.ac_begin_time", 
         new SysMap("beginTime", 
         CommUtil.formatDate(beginTime)), ">=");
     }
     if (!CommUtil.null2String(endTime).equals("")) {
       qo.addQuery("obj.ac_end_time", 
         new SysMap("endTime", 
         CommUtil.formatDate(endTime)), "<=");
     }
     Page pList = this.activityService.selectPage(new Page<Activity>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("q_ac_title", q_ac_title);
     mv.addObject("ac_status", ac_status);
     mv.addObject("beginTime", beginTime);
     mv.addObject("endTime", endTime);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="活动添加", value="/admin/activity_add.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_add.htm"})
   public ModelAndView activity_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/activity_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="活动编辑", value="/admin/activity_edit.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_edit.htm"})
   public ModelAndView activity_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/activity_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Activity activity = this.activityService.selectById(
         Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", activity);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="活动保存", value="/admin/activity_save.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_save.htm"})
   public ModelAndView activity_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd)
   {
     WebForm wf = new WebForm();
     Activity activity = null;
     if (id.equals("")) {
       activity = (Activity)wf.toPo(request, Activity.class);
       activity.setAddTime(new Date());
     } else {
       Activity obj = this.activityService.selectById(Long.valueOf(Long.parseLong(id)));
       activity = (Activity)wf.toPo(request, obj);
     }
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "activity";
     Map map = new HashMap();
     try {
       String fileName = activity.getAc_acc() == null ? "" : activity
         .getAc_acc().getName();
       map = CommUtil.saveFileToServer(request, "acc", saveFilePathName, 
         fileName, null);
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           Accessory ac_acc = new Accessory();
           ac_acc.setName(CommUtil.null2String(map.get("fileName")));
           ac_acc.setExt(CommUtil.null2String(map.get("mime")));
           ac_acc.setSize(CommUtil.null2Float(map.get("fileSize")));
           ac_acc.setPath(uploadFilePath + "/activity");
           ac_acc.setWidth(CommUtil.null2Int(map.get("width")));
           ac_acc.setHeight(CommUtil.null2Int(map.get("height")));
           ac_acc.setAddTime(new Date());
           this.accessoryService.insertSelective(ac_acc);
           activity.setAc_acc(ac_acc);
         }
       }
       else if (map.get("fileName") != "") {
         Accessory ac_acc = activity.getAc_acc();
         ac_acc.setName(CommUtil.null2String(map.get("fileName")));
         ac_acc.setExt(CommUtil.null2String(map.get("mime")));
         ac_acc.setSize(CommUtil.null2Float(map.get("fileSize")));
         ac_acc.setPath(uploadFilePath + "/activity");
         ac_acc.setWidth(CommUtil.null2Int(map.get("width")));
         ac_acc.setHeight(CommUtil.null2Int(map.get("height")));
         ac_acc.setAddTime(new Date());
         this.accessoryService.updateSelectiveById(ac_acc);
       }
 
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     if (id.equals(""))
       this.activityService.insertSelective(activity);
     else
       this.activityService.updateSelectiveById(activity);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/activity_list.htm");
     mv.addObject("op_title", "保存商城活动成功");
     mv.addObject("add_url", CommUtil.getURL(request) + 
       "/admin/activity_add.htm" + "?currentPage=" + currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="活动删除", value="/admin/activity_del.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_del.htm"})
   public String activity_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Activity activity = this.activityService.selectById(
           Long.valueOf(Long.parseLong(id)));
         CommWebUtil.del_acc(request, activity.getAc_acc());
         this.activityService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:activity_list.htm?currentPage=" + currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="活动AJAX更新", value="/admin/activity_ajax.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_ajax.htm"})
   public void activity_ajax(HttpServletRequest request, HttpServletResponse response, String id, String fieldName, String value) throws ClassNotFoundException {
     Activity obj = this.activityService.selectById(Long.valueOf(Long.parseLong(id)));
     Field[] fields = Activity.class.getDeclaredFields();
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
     this.activityService.updateSelectiveById(obj);
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
 
   @SecurityMapping(display = false, rsequence = 0, title="活动商品列表", value="/admin/activity_goods_list.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_goods_list.htm"})
   public ModelAndView activity_goods_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String act_id, String goods_name, String ag_status)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/activity_goods_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     ActivityGoodsQueryObject qo = new ActivityGoodsQueryObject(currentPage, 
       mv, orderBy, orderType);
     qo.addQuery("obj.act.id", 
       new SysMap("act_id", 
       CommUtil.null2Long(act_id)), "=");
     if (!CommUtil.null2String(ag_status).equals("")) {
       qo.addQuery("obj.ag_status", 
         new SysMap("ag_status", 
         Integer.valueOf(CommUtil.null2Int(ag_status))), "=");
     }
     if (!CommUtil.null2String(goods_name).equals("")) {
       qo.addQuery("obj.ag_goods.goods_name", 
         new SysMap("goods_name", "%" + 
         goods_name.trim() + "goods_name"), "=");
     }
     Page pList = this.activityGoodsService.selectPage(new Page<ActivityGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("ag_status", ag_status);
     mv.addObject("goods_name", goods_name);
     mv.addObject("act_id", act_id);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="活动通过", value="/admin/activity_goods_audit.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_goods_audit.htm"})
   public String activity_goods_audit(HttpServletRequest request, HttpServletResponse response, String act_id, String mulitId, String currentPage) {
     Activity act = this.activityService.selectById(
       CommUtil.null2Long(act_id));
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         ActivityGoods ac = this.activityGoodsService.selectById(
           Long.valueOf(Long.parseLong(id)));
         ac.setAg_status(1);
         this.activityGoodsService.updateSelectiveById(ac);
 
         Goods goods = ac.getAg_goods();
         goods.setActivity_status(2);
 
         BigDecimal num = BigDecimal.valueOf(0.1D);
 
         BigDecimal acprice = num.multiply(act.getAc_rebate()).multiply(
           goods.getStore_price()).setScale(2, 
           4);
         goods.setGoods_current_price(acprice);
         this.goodService.updateSelectiveById(goods);
       }
     }
     return "redirect:activity_goods_list.htm?act_id=" + act_id + 
       "&currentPage=" + currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="活动拒绝", value="/admin/activity_goods_refuse.htm*", rtype="admin", rname="活动管理", rcode="activity_admin", rgroup="运营")
   @RequestMapping({"/admin/activity_goods_refuse.htm"})
   public String activity_goods_refuse(HttpServletRequest request, HttpServletResponse response, String act_id, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         ActivityGoods ac = this.activityGoodsService.selectById(
           Long.valueOf(Long.parseLong(id)));
         ac.setAg_status(-1);
         this.activityGoodsService.updateSelectiveById(ac);
 
         Goods goods = ac.getAg_goods();
         goods.setActivity_status(0);
 
         goods.setGoods_current_price(goods.getStore_price());
         this.goodService.updateSelectiveById(goods);
       }
     }
     return "redirect:activity_goods_list.htm?act_id=" + act_id + 
       "&currentPage=" + currentPage;
   }
 }


 
 
 