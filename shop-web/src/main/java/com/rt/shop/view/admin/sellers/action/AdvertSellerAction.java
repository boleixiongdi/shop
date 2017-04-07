 package com.rt.shop.view.admin.sellers.action;
 
 import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
 public class AdvertSellerAction
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
   private IUserService userService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IGoldLogService goldLogService;
 
   @SecurityMapping(display = false, rsequence = 0, title="广告列表", value="/seller/advert_list.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_list.htm"})
   public ModelAndView advert_list(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/advert_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     AdvertPositionQueryObject qo = new AdvertPositionQueryObject(
       currentPage, mv, "addTime", "desc");
     qo.addQuery("obj.ap_status", new SysMap("ap_status", Integer.valueOf(1)), "=");
     qo.addQuery("obj.ap_use_status", new SysMap("ap_use_status", Integer.valueOf(1)), "!=");
     qo.setPageSize(Integer.valueOf(30));
     Page pList = this.advertPositionService.selectPage(new Page<AdvPos>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告购买", value="/seller/advert_apply.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_apply.htm"})
   public ModelAndView advert_apply(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/advert_apply.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     AdvPos ap = this.advertPositionService.selectById(
       CommUtil.null2Long(id));
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     if (ap.getAp_price() > user.getGold()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "金币不足，不能申请");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/advert_list.htm");
     } else {
       String ap_session = CommUtil.randomString(32);
       request.getSession(false).setAttribute("ap_session", ap_session);
       mv.addObject("ap_session", ap_session);
       mv.addObject("ap", ap);
       mv.addObject("user", this.userService.selectById(
         SecurityUserHolder.getCurrentUser().getId()));
     }
     return mv;
   }
 
   @RequestMapping({"/seller/advert_vefity.htm"})
   public void advert_vefity(HttpServletRequest request, HttpServletResponse response, String month, String ap_id) {
     boolean ret = true;
     AdvPos ap = this.advertPositionService.selectById(
       CommUtil.null2Long(ap_id));
     int total_price = ap.getAp_price() * CommUtil.null2Int(month);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     if (total_price > user.getGold()) {
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
 
   @SecurityMapping(display = false, rsequence = 0, title="广告购买保存", value="/seller/advert_apply_save.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_apply_save.htm"})
   public ModelAndView advert_apply_save(HttpServletRequest request, HttpServletResponse response,Advert advert , String id, String ap_id, String ad_begin_time, String month, String ap_session) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String ap_session1 = CommUtil.null2String(request.getSession(false)
       .getAttribute("ap_session"));
     if (ap_session1.equals("")) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "禁止表单重复提交");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/advert_list.htm");
     } else {
       request.getSession(false).removeAttribute("ap_session");
     
     
       if (id.equals("")) {
         advert.setAddTime(new Date());
         AdvPos ap = this.advertPositionService
           .selectById(CommUtil.null2Long(ap_id));
         advert.setAd_ap_id(ap.getId());
         advert.setAd_begin_time(CommUtil.formatDate(ad_begin_time));
         Calendar cal = Calendar.getInstance();
         cal.add(2, CommUtil.null2Int(month));
         advert.setAd_end_time(cal.getTime());
         advert.setAd_user_id(SecurityUserHolder.getCurrentUser().getId());
         advert.setAd_gold(ap.getAp_price() * CommUtil.null2Int(month));
       } else {
         Advert obj = this.advertService.selectById(
           CommUtil.null2Long(id));
       }
       AdvPos advPos=advertPositionService.selectById(advert.getAd_ap_id());
       if (!advPos.getAp_type().equals("text")) {
         String uploadFilePath = this.configService.getSysConfig()
           .getUploadFilePath();
         String saveFilePathName = request.getSession()
           .getServletContext().getRealPath("/") + 
           uploadFilePath + File.separator + "advert";
         Map map = new HashMap();
         String fileName = "";
         Accessory accessory=accessoryService.selectById(advert.getAd_acc_id());
         if (accessory!= null)
           fileName = accessory.getName();
         try
         {
           map = CommUtil.saveFileToServer(request, "acc", 
             saveFilePathName, fileName, null);
           Accessory acc = null;
           if (fileName.equals("")) {
             if (map.get("fileName") != "") {
               acc = new Accessory();
               acc.setName(CommUtil.null2String(map
                 .get("fileName")));
               acc.setExt(CommUtil.null2String(map.get("mime")));
               acc.setSize(
                 CommUtil.null2Float(map.get("fileSize")));
               acc.setPath(uploadFilePath + "/advert");
               acc.setWidth(CommUtil.null2Int(map.get("width")));
               acc.setHeight(CommUtil.null2Int(map.get("height")));
               acc.setAddTime(new Date());
               this.accessoryService.insertSelective(acc);
               advert.setAd_acc_id(acc.getId());
             }
           }
           else if (map.get("fileName") != "") {
             acc = accessoryService.selectById(advert.getAd_acc_id());
             acc.setName(CommUtil.null2String(map
               .get("fileName")));
             acc.setExt(CommUtil.null2String(map.get("mime")));
             acc.setSize(
               CommUtil.null2Float(map.get("fileSize")));
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
       }
       if (id.equals("")) {
         this.advertService.insertSelective(advert);
 
         User user = this.userService.selectById(
           SecurityUserHolder.getCurrentUser().getId());
         user.setGold(user.getGold() - advert.getAd_gold());
         this.userService.updateSelectiveById(user);
         GoldLog log = new GoldLog();
         log.setAddTime(new Date());
         log.setGl_content("购买广告扣除金币");
         log.setGl_count(advert.getAd_gold());
         log.setGl_user(user);
         log.setGl_type(-1);
         this.goldLogService.insertSelective(log);
       } else {
         this.advertService.updateSelectiveById(advert);
       }
       mv.addObject("op_title", "广告申请成功");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/advert_my.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告编辑", value="/seller/advert_apply_edit.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_apply_edit.htm"})
   public ModelAndView advert_apply_edit(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/advert_apply.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Advert obj = this.advertService.selectById(CommUtil.null2Long(id));
     String ap_session = CommUtil.randomString(32);
     request.getSession(false).setAttribute("ap_session", ap_session);
     mv.addObject("ap_session", ap_session);
     mv.addObject("ap", advertPositionService.selectById(obj.getAd_ap_id()));
     mv.addObject("obj", obj);
     mv.addObject("user", this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId()));
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="我的广告", value="/seller/advert_my.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_my.htm"})
   public ModelAndView advert_my(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/advert_my.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     AdvertQueryObject qo = new AdvertQueryObject(currentPage, mv, 
       "addTime", "desc");
     qo.addQuery("obj.ad_user.id", 
       new SysMap("ad_user", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     Page pList = this.advertService.selectPage(new Page<Advert>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="广告延时", value="/seller/advert_delay.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_delay.htm"})
   public ModelAndView advert_delay(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/advert_delay.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Advert obj = this.advertService.selectById(CommUtil.null2Long(id));
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     AdvPos advPos=advertPositionService.selectById(obj.getAd_ap_id());
     if (advPos.getAp_price() > user.getGold()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "金币不足，不能申请");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/advert_list.htm");
     } else {
       String delay_session = CommUtil.randomString(32);
       request.getSession(false).setAttribute("delay_session", 
         delay_session);
       mv.addObject("delay_session", delay_session);
       mv.addObject("obj", obj);
       mv.addObject("ap", advertPositionService.selectById(obj.getAd_ap_id()));
       mv.addObject("user", this.userService.selectById(
         SecurityUserHolder.getCurrentUser().getId()));
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="广告购买保存", value="/seller/advert_delay_save.htm*", rtype="seller", rname="广告管理", rcode="advert_seller", rgroup="其他设置")
   @RequestMapping({"/seller/advert_delay_save.htm"})
   public ModelAndView advert_delay_save(HttpServletRequest request, HttpServletResponse response, String id, String month, String delay_session) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String delay_session1 = CommUtil.null2String(request.getSession(false)
       .getAttribute("delay_session"));
     if (delay_session1.equals("")) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "禁止表单重复提交");
       mv.addObject("url", CommUtil.getURL(request) + 
         "/seller/advert_list.htm");
     } else {
       request.getSession(false).removeAttribute("delay_session");
       Advert advert = this.advertService.selectById(
         CommUtil.null2Long(id));
       User user = this.userService.selectById(
         SecurityUserHolder.getCurrentUser().getId());
       int total_gold = advertPositionService.selectById(advert.getAd_ap_id()).getAp_price() * 
         CommUtil.null2Int(month);
       if (total_gold < user.getGold()) {
         Calendar cal = Calendar.getInstance();
         cal.setTime(advert.getAd_end_time());
         cal.add(2, CommUtil.null2Int(month));
         advert.setAd_end_time(cal.getTime());
         advert.setAd_gold(advert.getAd_gold() + total_gold);
         this.advertService.updateSelectiveById(advert);
 
         user.setGold(user.getGold() - total_gold);
         this.userService.updateSelectiveById(user);
         GoldLog log = new GoldLog();
         log.setAddTime(new Date());
         log.setGl_content("广告延时扣除金币");
         log.setGl_count(advert.getAd_gold());
         log.setGl_user(user);
         log.setGl_type(-1);
         this.goldLogService.insertSelective(log);
         mv.addObject("op_title", "广告延时成功");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/advert_my.htm");
       } else {
         mv = new JModelAndView("error.html", this.configService
           .getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "金币不足，不能延时");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/advert_delay.htm?id=" + id);
       }
     }
     return mv;
   }
 }


 
 
 