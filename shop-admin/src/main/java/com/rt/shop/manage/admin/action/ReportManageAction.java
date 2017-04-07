 package com.rt.shop.manage.admin.action;
 
 import java.io.File;
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
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Report;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.ReportQueryObject;
import com.rt.shop.lucene.LuceneUtil;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IReportService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class ReportManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IReportService reportService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IUserService userService;
 
   @SecurityMapping(display = false, rsequence = 0, title="未处理举报列表", value="/admin/report_list.htm*", rtype="admin", rname="举报管理", rcode="report_manage", rgroup="交易")
   @RequestMapping({"/admin/report_list.htm"})
   public ModelAndView report_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String goods_name, String userName)
   {
     ModelAndView mv = new JModelAndView("admin/blue/report_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     ReportQueryObject qo = new ReportQueryObject(currentPage, mv, orderBy, 
       orderType);
     if (!CommUtil.null2String(goods_name).equals("")) {
       qo.addQuery("obj.goods.goods_name", 
         new SysMap("goods_name", "%" + 
         goods_name + "%"), "like");
       mv.addObject("goods_name", goods_name);
     }
     if (!CommUtil.null2String(userName).equals("")) {
       qo.addQuery("obj.user.userName", new SysMap("userName", userName), 
         "=");
       mv.addObject("userName", userName);
     }
     qo.addQuery("obj.status", new SysMap("status", Integer.valueOf(0)), "=");
     Page pList = this.reportService.selectPage(new Page<Report>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/report_list.htm", "", 
       params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="已处理举报列表", value="/admin/report_handle_list.htm*", rtype="admin", rname="举报管理", rcode="report_manage", rgroup="交易")
   @RequestMapping({"/admin/report_handle_list.htm"})
   public ModelAndView report_handle_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String goods_name, String userName)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/report_handle_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     String params = "";
     ReportQueryObject qo = new ReportQueryObject(currentPage, mv, orderBy, 
       orderType);
     if (!CommUtil.null2String(goods_name).equals("")) {
       qo.addQuery("obj.goods.goods_name", 
         new SysMap("goods_name", "%" + 
         goods_name + "%"), "like");
       mv.addObject("goods_name", goods_name);
     }
     if (!CommUtil.null2String(userName).equals("")) {
       qo.addQuery("obj.user.userName", new SysMap("userName", userName), 
         "=");
       mv.addObject("userName", userName);
     }
     qo.addQuery("obj.status", new SysMap("status", Integer.valueOf(1)), "=");
     Page pList = this.reportService.selectPage(new Page<Report>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView(url + "/admin/report_list.htm", "", 
       params, pList, mv);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="举报处理", value="/admin/report_handle.htm*", rtype="admin", rname="举报管理", rcode="report_manage", rgroup="交易")
   @RequestMapping({"/admin/report_handle.htm"})
   public ModelAndView report_handle(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/report_handle.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     Report obj = this.reportService.selectById(CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="举报处理", value="/admin/report_handle_save.htm*", rtype="admin", rname="举报管理", rcode="report_manage", rgroup="交易")
   @RequestMapping({"/admin/report_handle_save.htm"})
   public ModelAndView report_handle_save(HttpServletRequest request, HttpServletResponse response, String id, int result, String handle_info, String currentPage) {
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     Report obj = this.reportService.selectById(CommUtil.null2Long(id));
     obj.setResult(result);
     obj.setStatus(1);
     obj.setHandle_info(handle_info);
     obj.setHandle_Time(new Date());
     this.reportService.updateSelectiveById(obj);
     if (obj.getResult() == 1) {
       Goods goods = goodsService.selectById(obj.getGoods_id());
       goods.setGoods_status(-3);
       this.goodsService.updateSelectiveById(goods);
 
       String goods_lucene_path = System.getProperty("user.dir") + 
         File.separator + "luence" + File.separator + "goods";
       File file = new File(goods_lucene_path);
       if (!file.exists()) {
         CommUtil.createFolder(goods_lucene_path);
       }
       LuceneUtil lucene = LuceneUtil.instance();
       LuceneUtil.setIndex_path(goods_lucene_path);
       lucene.delete_index(CommUtil.null2String(goods.getId()));
     }
     if (obj.getResult() == -2) {
       User user = userService.selectById(obj.getUser_id());
       user.setReport(-1);
       this.userService.updateSelectiveById(user);
     }
     mv.addObject("op_title", "处理举报成功");
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/report_list.htm?currentPage=" + currentPage);
     return mv;
   }
 }


 
 
 