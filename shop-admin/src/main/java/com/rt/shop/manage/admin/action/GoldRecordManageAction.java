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
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.GoldLog;
import com.rt.shop.entity.GoldRecord;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.GoldLogQueryObject;
import com.rt.shop.entity.query.GoldRecordQueryObject;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IGoldLogService;
import com.rt.shop.service.IGoldRecordService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class GoldRecordManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoldRecordService goldrecordService;
 
   @Autowired
   private IGoldLogService goldLogService;
 
   @Autowired
   private IUserService userService;
 
   @SecurityMapping(display = false, rsequence = 0, title="金币购买记录", value="/admin/gold_record.htm*", rtype="admin", rname="金币管理", rcode="gold_record_admin", rgroup="运营")
   @RequestMapping({"/admin/gold_record.htm"})
   public ModelAndView gold_record(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String beginTime, String endTime, String beginCount, String endCount)
   {
     ModelAndView mv = new JModelAndView("admin/blue/gold_record.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       GoldRecordQueryObject qo = new GoldRecordQueryObject(currentPage, 
         mv, orderBy, orderType);
       if (!CommUtil.null2String(beginTime).equals("")) {
         qo.addQuery("obj.addTime", 
           new SysMap("beginTime", 
           CommUtil.formatDate(beginTime)), ">=");
       }
       if (!CommUtil.null2String(endTime).equals("")) {
         qo.addQuery("obj.addTime", 
           new SysMap("endTime", 
           CommUtil.formatDate(endTime)), "<=");
       }
       if (!CommUtil.null2String(beginCount).equals("")) {
         qo.addQuery("obj.gold_count", 
           new SysMap("gold_count", 
           Integer.valueOf(CommUtil.null2Int(beginCount))), ">=");
       }
       if (!CommUtil.null2String(endCount).equals("")) {
         qo.addQuery("obj.gold_count", 
           new SysMap("endCount", 
           Integer.valueOf(CommUtil.null2Int(endCount))), "<=");
       }
       Page pList = this.goldrecordService.selectPage(new Page<GoldRecord>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       mv.addObject("beginTime", beginTime);
       mv.addObject("endTime", endTime);
       mv.addObject("beginCount", beginCount);
       mv.addObject("endCount", endCount);
     } else {
       mv = new JModelAndView("admin/blue/error.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/operation_base_set.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="金币日志列表", value="/admin/gold_log.htm*", rtype="admin", rname="金币管理", rcode="gold_record_admin", rgroup="运营")
   @RequestMapping({"/admin/gold_log.htm"})
   public ModelAndView gold_log(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String beginTime, String endTime) {
     ModelAndView mv = new JModelAndView("admin/blue/gold_log.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       GoldLogQueryObject qo = new GoldLogQueryObject(currentPage, mv, 
         orderBy, orderType);
       if (!CommUtil.null2String(beginTime).equals("")) {
         qo.addQuery("obj.addTime", 
           new SysMap("beginTime", 
           CommUtil.formatDate(beginTime)), ">=");
       }
       if (!CommUtil.null2String(endTime).equals("")) {
         qo.addQuery("obj.addTime", 
           new SysMap("endTime", 
           CommUtil.formatDate(endTime)), "<=");
       }
       Page pList = this.goldLogService.selectPage(new Page<GoldLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       mv.addObject("beginTime", beginTime);
       mv.addObject("endTime", endTime);
     } else {
       mv = new JModelAndView("admin/blue/error.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/operation_base_set.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="金币购买记录编辑", value="/admin/gold_record_edit.htm*", rtype="admin", rname="金币管理", rcode="gold_record_admin", rgroup="运营")
   @RequestMapping({"/admin/gold_record_edit.htm"})
   public ModelAndView gold_record_edit(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/gold_record_edit.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       if ((id != null) && (!id.equals(""))) {
         GoldRecord goldrecord = this.goldrecordService.selectById(
           Long.valueOf(Long.parseLong(id)));
         if (goldrecord.getGold_status() == 0) {
           mv.addObject("obj", goldrecord);
           mv.addObject("currentPage", currentPage);
         } else {
           mv = new JModelAndView("admin/blue/error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 0, request, 
             response);
           mv.addObject("op_title", "参数错误，编辑失败");
           mv.addObject("list_url", CommUtil.getURL(request) + 
             "/admin/gold_record.htm");
         }
       }
     } else {
       mv = new JModelAndView("admin/blue/error.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/operation_base_set.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="金币购买记录", value="/admin/gold_record_save.htm*", rtype="admin", rname="金币管理", rcode="gold_record_admin", rgroup="运营")
   @RequestMapping({"/admin/gold_record_save.htm"})
   public ModelAndView gold_record_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd, String list_url)
   {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       WebForm wf = new WebForm();
       GoldRecord goldrecord = null;
       if (id.equals("")) {
         goldrecord = (GoldRecord)wf.toPo(request, GoldRecord.class);
         goldrecord.setAddTime(new Date());
       } else {
         GoldRecord obj = this.goldrecordService.selectById(
           Long.valueOf(Long.parseLong(id)));
         goldrecord = (GoldRecord)wf.toPo(request, obj);
       }
       if (goldrecord.getGold_pay_status() == 2) {
         goldrecord.setGold_status(1);
       }
       goldrecord.setGold_admin(SecurityUserHolder.getCurrentUser());
       goldrecord.setGold_admin_info("管理员审核金币");
       goldrecord.setGold_admin_time(new Date());
       if (id.equals(""))
         this.goldrecordService.insertSelective(goldrecord);
       else
         this.goldrecordService.updateSelectiveById(goldrecord);
       if (goldrecord.getGold_pay_status() == 2) {
         User user = goldrecord.getGold_user();
         user.setGold(user.getGold() + goldrecord.getGold_count());
         this.userService.updateSelectiveById(user);
         GoldLog log = new GoldLog();
         log.setAddTime(new Date());
         log.setGl_admin(SecurityUserHolder.getCurrentUser());
         log.setGl_admin_content(goldrecord.getGold_admin_info());
         log.setGl_admin_time(new Date());
         log.setGl_content("管理员审核金币记录");
         log.setGl_count(goldrecord.getGold_count());
         log.setGl_money(goldrecord.getGold_money());
         log.setGl_payment(goldrecord.getGold_payment());
         log.setGl_type(0);
         log.setGl_user(goldrecord.getGold_user());
         this.goldLogService.insertSelective(log);
       }
       mv.addObject("list_url", list_url);
       mv.addObject("op_title", "编辑金币记录成功");
     } else {
       mv = new JModelAndView("admin/blue/error.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/operation_base_set.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="金币购买记录删除", value="/admin/gold_record_del.htm*", rtype="admin", rname="金币管理", rcode="gold_record_admin", rgroup="运营")
   @RequestMapping({"/admin/gold_record_del.htm"})
   public String gold_record_del(HttpServletRequest request, HttpServletResponse response, String mulitId, String currentPage) {
     if (this.configService.getSysConfig().getGold()) {
       String[] ids = mulitId.split(",");
       for (String id : ids) {
         if (!id.equals("")) {
           GoldRecord goldrecord = this.goldrecordService
             .selectById(Long.valueOf(Long.parseLong(id)));
           this.goldrecordService.deleteById(Long.valueOf(Long.parseLong(id)));
         }
       }
     }
     return "redirect:gold_record.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="金币购买记录", value="/admin/gold_record_view.htm*", rtype="admin", rname="金币管理", rcode="gold_record_admin", rgroup="运营")
   @RequestMapping({"/admin/gold_record_view.htm"})
   public ModelAndView gold_record_view(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/gold_record_view.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       if ((id != null) && (!id.equals(""))) {
         GoldRecord goldrecord = this.goldrecordService.selectById(
           Long.valueOf(Long.parseLong(id)));
         if (goldrecord.getGold_status() != 0) {
           mv.addObject("obj", goldrecord);
           mv.addObject("currentPage", currentPage);
         } else {
           mv = new JModelAndView("admin/blue/error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 0, request, 
             response);
           mv.addObject("op_title", "参数错误，编辑失败");
           mv.addObject("list_url", CommUtil.getURL(request) + 
             "/admin/gold_record.htm");
         }
       }
     } else {
       mv = new JModelAndView("admin/blue/error.html", this.configService
         .getSysConfig(), this.userConfigService.getUserConfig(), 0, 
         request, response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("list_url", CommUtil.getURL(request) + 
         "/admin/operation_base_set.htm");
     }
     return mv;
   }
 }


 
 
 