 package com.rt.shop.view.admin.buyer.actions;
 
 import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Report;
import com.rt.shop.entity.ReportSubject;
import com.rt.shop.entity.ReportType;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.ReportQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IReportService;
import com.rt.shop.service.IReportSubjectService;
import com.rt.shop.service.IReportTypeService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class ReportBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IReportTypeService reportTypeService;
 
   @Autowired
   private IReportSubjectService reportSubjectService;
 
   @Autowired
   private IReportService reportService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IUserService userService;
 
   @SecurityMapping(display = false, rsequence = 0, title="买家举报列表", value="/buyer/report.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/report.htm"})
   public ModelAndView report(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/report.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     ReportQueryObject rqo = new ReportQueryObject(currentPage, mv, null, 
       null);
     rqo.addQuery("obj.user.id", 
       new SysMap("user_id", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     Page pList = this.reportService.selectPage(new Page<Report>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家举报商品", value="/buyer/report_add.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/report_add.htm"})
   public ModelAndView report_add(HttpServletRequest request, HttpServletResponse response, String goods_id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/report_add.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     if (user.getReport() == -1) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您因为恶意举报已被禁止举报，请与商城管理员联系");
       mv.addObject("url", CommUtil.getURL(request) + "/goods_" + goods_id + 
         ".htm");
     } else {
      
       Report sReport=new Report();
       sReport.setGoods_id(CommUtil.null2Long(goods_id));
       sReport.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       sReport.setStatus(Integer.valueOf(0));
       List reports = this.reportService.selectList(sReport);
        
         //"select obj from Report obj where obj.goods.id=:goods_id and obj.user.id=:user_id and obj.status=:status", 
         
       if (reports.size() == 0) {
         Goods goods = this.goodsService.selectById(
           CommUtil.null2Long(goods_id));
         mv.addObject("goods", goods);
         ReportType sReportType=new ReportType();
         List types = this.reportTypeService.selectList(new ReportType(),"addTime desc");
           
         mv.addObject("types", types);
       } else {
         mv = new JModelAndView("error.html", this.configService
           .getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "您已经举报该商品，且尚未得到商城处理");
         mv.addObject("url", CommUtil.getURL(request) + "/goods_" + 
           goods_id + ".htm");
       }
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="保存买家举报商品", value="/buyer/report_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/report_save.htm"})
   public ModelAndView report_save(HttpServletRequest request, HttpServletResponse response, String goods_id, String subject_id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     WebForm wf = new WebForm();
     Report report = (Report)wf.toPo( Report.class);
     report.setAddTime(new Date());
     report.setUser_id(SecurityUserHolder.getCurrentUser().getId());
    
     report.setGoods_id(CommUtil.null2Long(goods_id));
     ReportSubject subject = this.reportSubjectService.selectById(
       CommUtil.null2Long(subject_id));
     report.setSubject_id(subject.getId());
 
     String uploadFilePath = this.configService.getSysConfig()
       .getUploadFilePath();
     String saveFilePathName = request.getSession().getServletContext()
       .getRealPath("/") + 
       uploadFilePath + File.separator + "report";
     Map map = new HashMap();
     try {
       map = CommUtil.saveFileToServer(request, "img1", saveFilePathName, 
         null, null);
       if (map.get("fileName") != "") {
         Accessory acc1 = new Accessory();
         acc1.setName(CommUtil.null2String(map.get("fileName")));
         acc1.setExt(CommUtil.null2String(map.get("mime")));
         acc1.setSize(CommUtil.null2Float(map.get("fileSize")));
         acc1.setPath(uploadFilePath + "/report");
         acc1.setWidth(CommUtil.null2Int(map.get("width")));
         acc1.setHeight(CommUtil.null2Int(map.get("height")));
         acc1.setAddTime(new Date());
         this.accessoryService.insertSelective(acc1);
         report.setAcc1_id(acc1.getId());
       }
       map.clear();
       map = CommUtil.saveFileToServer(request, "img2", saveFilePathName, 
         null, null);
       if (map.get("fileName") != "") {
         Accessory acc2 = new Accessory();
         acc2.setName(CommUtil.null2String(map.get("fileName")));
         acc2.setExt(CommUtil.null2String(map.get("mime")));
         acc2.setSize(CommUtil.null2Float(map.get("fileSize")));
         acc2.setPath(uploadFilePath + "/report");
         acc2.setWidth(CommUtil.null2Int(map.get("width")));
         acc2.setHeight(CommUtil.null2Int(map.get("height")));
         acc2.setAddTime(new Date());
         this.accessoryService.insertSelective(acc2);
         report.setAcc2_id(acc2.getId());
       }
       map.clear();
       map = CommUtil.saveFileToServer(request, "img3", saveFilePathName, 
         null, null);
       if (map.get("fileName") != "") {
         Accessory acc3 = new Accessory();
         acc3.setName(CommUtil.null2String(map.get("fileName")));
         acc3.setExt(CommUtil.null2String(map.get("mime")));
         acc3.setSize(CommUtil.null2Float(map.get("fileSize")));
         acc3.setPath(uploadFilePath + "/report");
         acc3.setWidth(CommUtil.null2Int(map.get("width")));
         acc3.setHeight(CommUtil.null2Int(map.get("height")));
         acc3.setAddTime(new Date());
         this.accessoryService.insertSelective(acc3);
         report.setAcc3_id(acc3.getId());
       }
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     this.reportService.insertSelective(report);
     mv.addObject("op_title", "举报商品成功");
     mv.addObject("url", CommUtil.getURL(request) + "/buyer/report.htm");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家举报详情", value="/buyer/report_view.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/report_view.htm"})
   public ModelAndView report_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/report_view.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Report obj = this.reportService.selectById(CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家取消举报", value="/buyer/report_cancel.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/report_cancel.htm"})
   public String report_cancel(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     Report obj = this.reportService.selectById(CommUtil.null2Long(id));
     obj.setStatus(-1);
     this.reportService.updateSelectiveById(obj);
     return "redirect:report.htm?currentPage=" + currentPage;
   }
 
   @RequestMapping({"/buyer/report_subject_load.htm"})
   public void report_subject_load(HttpServletRequest request, HttpServletResponse response, String type_id) {
   
     ReportSubject sReportSubject=new ReportSubject();
     sReportSubject.setType_id(CommUtil.null2Long(type_id));
     List<ReportSubject> rss = this.reportSubjectService.selectList(sReportSubject);
     List<Map> list = new ArrayList<Map>();
     for (ReportSubject rs : rss) {
       Map map = new HashMap();
       map.put("id", rs.getId());
       map.put("title", rs.getTitle());
       list.add(map);
     }
     String temp = Json.toJson(list, JsonFormat.compact());
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(temp);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 }


 
 
 