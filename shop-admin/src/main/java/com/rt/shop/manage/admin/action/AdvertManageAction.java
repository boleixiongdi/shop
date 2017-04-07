 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
import java.io.IOException;
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
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.AdvPos;
import com.rt.shop.entity.Advert;
import com.rt.shop.entity.GoldLog;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.AdvertPositionQueryObject;
import com.rt.shop.entity.query.AdvertQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAdvPosService;
import com.rt.shop.service.IAdvertService;
import com.rt.shop.service.IGoldLogService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class AdvertManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IAdvertService advertService;
 
   @Autowired
   private IAdvPosService advertPositionService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IGoldLogService goldLogService;
 
   @SecurityMapping(display = false, rsequence = 0, title="广告列表", value="/admin/advert_list.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_list.htm"})
   public ModelAndView advert_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String ad_title)
   {
     ModelAndView mv = new JModelAndView("admin/blue/advert_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     AdvertQueryObject qo = new AdvertQueryObject(currentPage, mv, orderBy, 
       orderType);
     if (!CommUtil.null2String(ad_title).equals("")) {
       qo.addQuery("obj.ad_title", 
         new SysMap("ad_title", "%" + 
         ad_title.trim() + "%"), "like");
     }
     Page pList = this.advertService.selectPage(new Page<Advert>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("ad_title", ad_title);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="待审批广告列表", value="/admin/advert_list_audit.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_list_audit.htm"})
   public ModelAndView advert_list_audit(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String ad_title)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/advert_list_audit.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     AdvertQueryObject qo = new AdvertQueryObject(currentPage, mv, orderBy, 
       orderType);
     if (!CommUtil.null2String(ad_title).equals("")) {
       qo.addQuery("obj.ad_title", 
         new SysMap("ad_title", "%" + 
         ad_title.trim() + "%"), "like");
     }
     qo.addQuery("obj.ad_status", new SysMap("ad_status", Integer.valueOf(0)), "=");
     Page pList = this.advertService.selectPage(new Page<Advert>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("ad_title", ad_title);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告增加", value="/admin/advert_add.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_add.htm"})
   public ModelAndView advert_add(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/advert_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     List aps = this.advertPositionService.selectList(null);
     mv.addObject("aps", aps);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告编辑", value="/admin/advert_edit.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_edit.htm"})
   public ModelAndView advert_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/advert_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Advert advert = this.advertService.selectById(Long.valueOf(Long.parseLong(id)));
       List aps = this.advertPositionService.selectList(null);
       mv.addObject("aps", aps);
       mv.addObject("obj", advert);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告查看", value="/admin/advert_view.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_view.htm"})
   public ModelAndView advert_view(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/advert_view.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
       Advert advert = this.advertService.selectById(Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", advert);
       mv.addObject("currentPage", currentPage);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告审核", value="/admin/advert_audit.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_audit.htm"})
   public ModelAndView advert_audit(HttpServletRequest request, HttpServletResponse response, String id, String ad_status, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     Advert obj = this.advertService.selectById(CommUtil.null2Long(id));
     obj.setAd_status(CommUtil.null2Int(ad_status));
     this.advertService.updateSelectiveById(obj);
     AdvPos ap=advertPositionService.selectById(obj.getAd_ap_id());
     if ((obj.getAd_status() == 1) && (ap.getAp_show_type() == 0)) {
    	 
       ap.setAp_use_status(1);
       this.advertPositionService.updateSelectiveById(ap);
     }
     if (obj.getAd_status() == -1) {
       User user = userService.selectById(obj.getAd_user_id());
       user.setGold(user.getGold() + obj.getAd_gold());
       this.userService.updateSelectiveById(user);
       GoldLog log = new GoldLog();
       log.setAddTime(new Date());
       log.setGl_content("广告审核失败，恢复金币");
       log.setGl_count(obj.getAd_gold());
       log.setGl_user_id(obj.getAd_user_id());
       log.setGl_type(0);
       this.goldLogService.insertSelective(log);
     }
     mv.addObject("op_title", "广告审核成功");
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/advert_list_audit.htm?currentPage=" + currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告保存", value="/admin/advert_save.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_save.htm"})
   public ModelAndView advert_save(HttpServletRequest request, HttpServletResponse response, String id, String ad_ap_id, String currentPage, String ad_begin_time, String ad_end_time)
   {
     WebForm wf = new WebForm();
     Advert advert = null;
     if (id.equals("")) {
       advert = (Advert)wf.toPo(request, Advert.class);
       advert.setAddTime(new Date());
       advert.setAd_user_id(SecurityUserHolder.getCurrentUser().getId());
     } else {
       Advert obj = this.advertService.selectById(Long.valueOf(Long.parseLong(id)));
       advert = (Advert)wf.toPo(request, obj);
     }
     AdvPos ap = this.advertPositionService.selectById(
       CommUtil.null2Long(ad_ap_id));
     advert.setAd_ap_id(ap.getId());
     advert.setAd_begin_time(CommUtil.formatDate(ad_begin_time));
     advert.setAd_end_time(CommUtil.formatDate(ad_end_time));
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "advert";
     Map map = new HashMap();
     String fileName = "";
     Accessory ad_acc=accessoryService.selectById(advert.getAd_acc_id());
     if (ad_acc != null)
       fileName = ad_acc.getName();
     try
     {
       map = CommUtil.saveFileToServer(request, "acc", saveFilePathName, 
         fileName, null);
       Accessory acc = null;
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           acc = new Accessory();
           acc.setName(CommUtil.null2String(map.get("fileName")));
           acc.setExt(CommUtil.null2String(map.get("mime")));
           acc.setSize(CommUtil.null2Float(map.get("fileSize")));
           acc.setPath(uploadFilePath + "/advert");
           acc.setWidth(CommUtil.null2Int(map.get("width")));
           acc.setHeight(CommUtil.null2Int(map.get("height")));
           acc.setAddTime(new Date());
           this.accessoryService.insertSelective(acc);
           advert.setAd_acc_id(acc.getId());
         }
       }
       else if (map.get("fileName") != "") {
         acc = ad_acc;
         acc.setName(CommUtil.null2String(map.get("fileName")));
         acc.setExt(CommUtil.null2String(map.get("mime")));
         acc.setSize(CommUtil.null2Float(map.get("fileSize")));
         acc.setPath(uploadFilePath + "/advert");
         acc.setWidth(CommUtil.null2Int(map.get("width")));
         acc.setHeight(CommUtil.null2Int(map.get("height")));
         acc.setAddTime(new Date());
         this.accessoryService.updateSelectiveById(acc);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     if (id.equals(""))
       this.advertService.insertSelective(advert);
     else
       this.advertService.updateSelectiveById(advert);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/advert_list.htm?currentPage=" + currentPage);
     mv.addObject("op_title", "保存广告成功");
     mv.addObject("add_url", CommUtil.getURL(request) + 
       "/admin/advert_add.htm?currentPage=" + currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告删除", value="/admin/advert_del.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/advert_del.htm"})
   public String advert_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Advert advert = this.advertService.selectById(
           Long.valueOf(Long.parseLong(id)));
         if (advert.getAd_status() != 1) {
        	 Accessory ad_acc=accessoryService.selectById(advert.getAd_acc_id());
           CommWebUtil.del_acc(request, ad_acc);
           this.advertService.deleteById(Long.valueOf(Long.parseLong(id)));
         }
       }
     }
     return "redirect:advert_list.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告位添加", value="/admin/adv_pos_add.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/adv_pos_add.htm"})
   public ModelAndView adv_pos_add(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/adv_pos_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     List advs = this.advertService.selectList(null);
      
     mv.addObject("advs", advs);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告位保存", value="/admin/adv_pos_save.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/adv_pos_save.htm"})
   public ModelAndView adv_pos_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url, String add_url) {
     WebForm wf = new WebForm();
     AdvPos ap = null;
     if (id.equals("")) {
       ap = (AdvPos)wf.toPo(request, AdvPos.class);
       ap.setAddTime(new Date());
     } else {
    	 AdvPos obj = this.advertPositionService.selectById(
         Long.valueOf(Long.parseLong(id)));
       ap = (AdvPos)wf.toPo(request, obj);
     }
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "advert";
     Map map = new HashMap();
     String fileName = "";
     if (ap.getAp_acc() != null)
       fileName = ap.getAp_acc().getName();
     try
     {
       map = CommUtil.saveFileToServer(request, "acc", saveFilePathName, 
         fileName, null);
       Accessory acc = null;
       if (fileName.equals("")) {
         if (map.get("fileName") != "") {
           acc = new Accessory();
           acc.setName(CommUtil.null2String(map.get("fileName")));
           acc.setExt(CommUtil.null2String(map.get("mime")));
           acc.setSize(CommUtil.null2Float(map.get("fileSize")));
           acc.setPath(uploadFilePath + "/advert");
           acc.setWidth(CommUtil.null2Int(map.get("width")));
           acc.setHeight(CommUtil.null2Int(map.get("height")));
           acc.setAddTime(new Date());
           this.accessoryService.insertSelective(acc);
           ap.setAp_acc(acc);
         }
       }
       else if (map.get("fileName") != "") {
         acc = ap.getAp_acc();
         acc.setName(CommUtil.null2String(map.get("fileName")));
         acc.setExt(CommUtil.null2String(map.get("mime")));
         acc.setSize(CommUtil.null2Float(map.get("fileSize")));
         acc.setPath(uploadFilePath + "/advert");
         acc.setWidth(CommUtil.null2Int(map.get("width")));
         acc.setHeight(CommUtil.null2Int(map.get("height")));
         acc.setAddTime(new Date());
         this.accessoryService.updateSelectiveById(acc);
       }
     }
     catch (IOException e)
     {
       e.printStackTrace();
     }
     if (id.equals(""))
       this.advertPositionService.insertSelective(ap);
     else
       this.advertPositionService.updateSelectiveById(ap);
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("list_url", list_url);
     mv.addObject("op_title", "保存广告位成功");
     if (add_url != null) {
       mv.addObject("add_url", add_url + "?currentPage=" + currentPage);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告位列表", value="/admin/adv_pos_list.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/adv_pos_list.htm"})
   public ModelAndView adv_pos_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String ap_title) {
     ModelAndView mv = new JModelAndView("admin/blue/adv_pos_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     AdvertPositionQueryObject qo = new AdvertPositionQueryObject(
       currentPage, mv, orderBy, orderType);
     if (!CommUtil.null2String(ap_title).equals("")) {
       qo.addQuery("obj.ap_title", 
         new SysMap("ap_title", "%" + ap_title + 
         "%"), "like");
     }
     Page pList = this.advertPositionService.selectPage(new Page<AdvPos>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("ap_title", ap_title);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告位编辑", value="/admin/adv_pos_edit.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/adv_pos_edit.htm"})
   public ModelAndView adv_pos_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/adv_pos_add.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if ((id != null) && (!id.equals(""))) {
    	 AdvPos obj = this.advertPositionService.selectById(
         Long.valueOf(Long.parseLong(id)));
       mv.addObject("obj", obj);
       mv.addObject("currentPage", currentPage);
       mv.addObject("edit", Boolean.valueOf(true));
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告位删除", value="/admin/adv_pos_del.htm*", rtype="admin", rname="广告管理", rcode="advert_admin", rgroup="运营")
   @RequestMapping({"/admin/adv_pos_del.htm"})
   public String adv_pos_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
    	   AdvPos ap = this.advertPositionService.selectById(
           Long.valueOf(Long.parseLong(id)));
         if (ap.getAp_sys_type() != 0) {
           CommWebUtil.del_acc(request, ap.getAp_acc());
           this.advertPositionService.deleteById(Long.valueOf(Long.parseLong(id)));
         }
       }
     }
     return "redirect:adv_pos_list.htm?currentPage=" + currentPage;
   }
 }


 
 
 