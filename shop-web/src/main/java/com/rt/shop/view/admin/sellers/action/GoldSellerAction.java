 package com.rt.shop.view.admin.sellers.action;
 
 import java.math.BigDecimal;
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
import com.rt.shop.entity.GoldLog;
import com.rt.shop.entity.GoldRecord;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.PredepositLog;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.GoldLogQueryObject;
import com.rt.shop.entity.query.GoldRecordQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.pay.tools.PayTools;
import com.rt.shop.service.IGoldLogService;
import com.rt.shop.service.IGoldRecordService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IPredepositLogService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class GoldSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IPaymentService paymentService;
 
   @Autowired
   private IGoldRecordService goldRecordService;
 
   @Autowired
   private IGoldLogService goldLogService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IPredepositLogService predepositLogService;
 
   @Autowired
   private PayTools payTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="金币兑换", value="/seller/gold_record.htm*", rtype="seller", rname="金币管理", rcode="gold_seller", rgroup="其他设置")
   @RequestMapping({"/seller/gold_record.htm"})
   public ModelAndView gold_record(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/gold_record.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getGold()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "商城未开启金币功能");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
       
       String sql="where type='admin' and mark!='alipay_wap' and mark!='weixin' and install="+Boolean.valueOf(true);
       List payments = this.paymentService.selectList(sql,null);
        // .query("select obj from Payment obj where obj.type=:type and obj.mark!=:mark and obj.mark!=:mark2 and obj.install=:install", 
       String gold_session = CommUtil.randomString(32);
       request.getSession(false)
         .setAttribute("gold_session", gold_session);
       mv.addObject("gold_session", gold_session);
       mv.addObject("payments", payments);
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="金币兑换保存", value="/buyer/gold_record_save.htm*", rtype="seller", rname="金币管理", rcode="gold_seller", rgroup="其他设置")
   @RequestMapping({"/seller/gold_record_save.htm"})
   public ModelAndView gold_record_save(HttpServletRequest request, HttpServletResponse response,GoldRecord obj, String id, String gold_payment, String gold_exchange_info, String gold_session) {
     ModelAndView mv = new JModelAndView("line_pay.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (this.configService.getSysConfig().getGold()) {
       String gold_session1 = CommUtil.null2String(request.getSession(
         false).getAttribute("gold_session"));
       if ((!gold_session1.equals("")) && (gold_session1.equals(gold_session))) {
         request.getSession(false).removeAttribute("gold_session");
         if (CommUtil.null2String(id).equals("")) {
           obj.setAddTime(new Date());
           if (gold_payment.equals("outline"))
             obj.setGold_pay_status(1);
           else {
             obj.setGold_pay_status(0);
           }
           obj.setGold_sn("gold" + 
             CommUtil.formatTime("yyyyMMddHHmmss", new Date()) + 
             SecurityUserHolder.getCurrentUser().getId());
           obj.setGold_user(SecurityUserHolder.getCurrentUser());
           obj.setGold_count(obj.getGold_money() * 
             this.configService.getSysConfig()
             .getGoldMarketValue());
           this.goldRecordService.insertSelective(obj);
         } else {
           GoldRecord gr = this.goldRecordService.selectById(
             CommUtil.null2Long(id));
           this.goldRecordService.updateSelectiveById(obj);
         }
         if (gold_payment.equals("outline")) {
           GoldLog log = new GoldLog();
           log.setAddTime(new Date());
           log.setGl_payment(obj.getGold_payment());
           log.setGl_content("线下支付");
           log.setGl_money(obj.getGold_money());
           log.setGl_count(obj.getGold_count());
           log.setGl_type(0);
           log.setGl_user(obj.getGold_user());
           log.setGr(obj);
           this.goldLogService.insertSelective(log);
           mv = new JModelAndView("success.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "线下支付提交成功，等待审核");
           mv.addObject("url", CommUtil.getURL(request) + 
             "/seller/gold_record_list.htm");
         } else if (gold_payment.equals("balance")) {
           User user = this.userService.selectById(
             SecurityUserHolder.getCurrentUser().getId());
           double balance = CommUtil.null2Double(user
             .getAvailableBalance());
           if (balance > obj.getGold_money()) {
             user.setGold(user.getGold() + obj.getGold_count());
             user.setAvailableBalance(BigDecimal.valueOf(
               CommUtil.subtract(user.getAvailableBalance(), 
               Integer.valueOf(obj.getGold_money()))));
             this.userService.updateSelectiveById(user);
 
             obj.setGold_pay_status(2);
             obj.setGold_status(1);
             this.goldRecordService.updateSelectiveById(obj);
 
             PredepositLog pre_log = new PredepositLog();
             pre_log.setAddTime(new Date());
             pre_log.setPd_log_user_id(user.getId());
             pre_log.setPd_op_type("兑换金币");
             pre_log.setPd_log_amount(BigDecimal.valueOf(
               -obj
               .getGold_money()));
             pre_log.setPd_log_info("兑换金币物减少可用预存款");
             pre_log.setPd_type("可用预存款");
             this.predepositLogService.insertSelective(pre_log);
 
             GoldLog log = new GoldLog();
             log.setAddTime(new Date());
             log.setGl_payment(obj.getGold_payment());
             log.setGl_content("预存款支付");
             log.setGl_money(obj.getGold_money());
             log.setGl_count(obj.getGold_count());
             log.setGl_type(0);
             log.setGl_user(obj.getGold_user());
             log.setGr(obj);
             this.goldLogService.insertSelective(log);
             mv = new JModelAndView("success.html", 
               this.configService.getSysConfig(), 
               this.userConfigService.getUserConfig(), 1, 
               request, response);
             mv.addObject("op_title", "金币兑换成功");
             mv.addObject("url", CommUtil.getURL(request) + 
               "/seller/gold_record_list.htm");
           } else {
             mv = new JModelAndView("error.html", 
               this.configService.getSysConfig(), 
               this.userConfigService.getUserConfig(), 1, 
               request, response);
             mv.addObject("op_title", "预存款金额不足");
             mv.addObject("url", CommUtil.getURL(request) + 
               "/seller/gold_record.htm");
           }
         } else {
           mv.addObject("payType", gold_payment);
           mv.addObject("type", "gold");
           mv.addObject("url", CommUtil.getURL(request));
           mv.addObject("payTools", this.payTools);
           mv.addObject("gold_id", obj.getId());
           Map params = new HashMap();
           params.put("install", obj.getGold_payment());
           params.put("mark", obj.getGold_payment());
           params.put("type", "admin");
           Payment sPayment=new Payment();
           sPayment.setInstall(Boolean.valueOf(obj.getGold_payment()));
           sPayment.setMark(obj.getGold_payment());
           sPayment.setType("admin");
           List payments = this.paymentService.selectList(sPayment);
            // .query("select obj from Payment obj where obj.install=:install and obj.mark=:mark and obj.type=:type", 
           mv.addObject("payment_id", payments.size() > 0 ? 
             ((Payment)payments
             .get(0)).getId() : new Payment());
         }
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "您已经提交过该请求");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/gold_record_list.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="金币兑换", value="/seller/gold_record_list.htm*", rtype="seller", rname="金币管理", rcode="gold_seller", rgroup="其他设置")
   @RequestMapping({"/seller/gold_record_list.htm"})
   public ModelAndView gold_record_list(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/gold_record_list.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getGold()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
       GoldRecordQueryObject qo = new GoldRecordQueryObject(currentPage, 
         mv, "addTime", "desc");
       qo.addQuery("obj.gold_user.id", 
         new SysMap("user_id", 
         SecurityUserHolder.getCurrentUser().getId()), "=");
       Page pList = this.goldRecordService.selectPage(new Page<GoldRecord>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="金币兑换支付", value="/seller/gold_pay.htm*", rtype="seller", rname="金币管理", rcode="gold_seller", rgroup="其他设置")
   @RequestMapping({"/seller/gold_pay.htm"})
   public ModelAndView gold_pay(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/gold_pay.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       GoldRecord obj = this.goldRecordService.selectById(
         CommUtil.null2Long(id));
 
       if (obj.getGold_user().getId()
         .equals(SecurityUserHolder.getCurrentUser().getId())) {
         String gold_session = CommUtil.randomString(32);
         request.getSession(false).setAttribute("gold_session", 
           gold_session);
         mv.addObject("gold_session", gold_session);
         mv.addObject("obj", obj);
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "参数错误，您没有该兑换信息");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/gold_record_list.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="金币兑换详情", value="/seller/gold_view.htm*", rtype="seller", rname="金币管理", rcode="gold_seller", rgroup="其他设置")
   @RequestMapping({"/seller/gold_view.htm"})
   public ModelAndView gold_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/gold_view.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (this.configService.getSysConfig().getGold()) {
       GoldRecord obj = this.goldRecordService.selectById(
         CommUtil.null2Long(id));
 
       if (obj.getGold_user().getId()
         .equals(SecurityUserHolder.getCurrentUser().getId())) {
         mv.addObject("obj", obj);
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "参数错误，您没有该兑换信息");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/gold_record_list.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="兑换日志", value="/seller/gold_log.htm*", rtype="seller", rname="金币管理", rcode="gold_seller", rgroup="其他设置")
   @RequestMapping({"/seller/gold_log.htm"})
   public ModelAndView gold_log(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/gold_log.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getGold()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启金币");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
       GoldLogQueryObject qo = new GoldLogQueryObject(currentPage, mv, 
         "addTime", "desc");
       qo.addQuery("obj.gl_user.id", 
         new SysMap("user_id", 
         SecurityUserHolder.getCurrentUser().getId()), "=");
       Page pList = this.goldLogService.selectPage(new Page<GoldLog>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       mv.addObject("user", this.userService.selectById(
         SecurityUserHolder.getCurrentUser().getId()));
     }
     return mv;
   }
 }


 
 
 