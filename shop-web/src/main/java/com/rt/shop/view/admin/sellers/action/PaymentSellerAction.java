 package com.rt.shop.view.admin.sellers.action;
 
 import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.User;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.PaymentTools;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class PaymentSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IPaymentService paymentService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IUserService userService;
 
   
   @Autowired
   private PaymentTools paymentTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="支付方式列表", value="/seller/payment.htm*", rtype="seller", rname="支付方式", rcode="payment_seller", rgroup="交易管理")
   @RequestMapping({"/seller/payment.htm"})
   public ModelAndView payment(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = mv = new JModelAndView(
       "user/default/usercenter/payment.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String store_payment = this.configService.getSysConfig()
       .getStore_payment();
     if ((store_payment != null) && (!store_payment.equals(""))) {
       Map map = (Map)Json.fromJson(HashMap.class, store_payment);
       mv.addObject("map", map);
       mv.addObject("paymentTools", this.paymentTools);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="支付方式安装", value="/seller/payment_install.htm*", rtype="seller", rname="支付方式", rcode="payment_seller", rgroup="交易管理")
   @RequestMapping({"/seller/payment_install.htm"})
   public ModelAndView payment_install(HttpServletRequest request, HttpServletResponse response, String mark) {
     ModelAndView mv = mv = new JModelAndView(
       "user/default/usercenter/payment/" + mark + ".html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
    
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     if ((mark != null) && (!mark.equals(""))) {
       
       Payment sPayment=new Payment();
       sPayment.setStore_id(user.getStore_id());
       sPayment.setMark(mark);
       List objs = this.paymentService.selectList(sPayment);
       if (objs.size() > 0) {
         mv.addObject("obj", objs.get(0));
       }
     }
 
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="支付方式卸载", value="/seller/payment_uninstall.htm*", rtype="seller", rname="支付方式", rcode="payment_seller", rgroup="交易管理")
   @RequestMapping({"/seller/payment_uninstall.htm"})
   public ModelAndView payment_uninstall(HttpServletRequest request, HttpServletResponse response, String mark) {
     ModelAndView mv = mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Payment sPayment=new Payment();
     sPayment.setStore_id(user.getStore_id());
     sPayment.setMark(mark);
     List<Payment> objs = this.paymentService.selectList(sPayment);
     if (objs.size() > 0) {
    	 OrderForm sOrderForm=new OrderForm();
    	 sOrderForm.setPayment_id(objs.get(0).getId());
    	 List<OrderForm> orderList=orderFormService.selectList(sOrderForm);
       for (OrderForm of : orderList) {
         of.setPayment(null);
         this.orderFormService.updateSelectiveById(of);
       }
       this.paymentService.deleteById(((Payment)objs.get(0)).getId());
     }
     mv.addObject("op_title", "支付方式卸载成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/payment.htm");
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="支付方式编辑", value="/seller/payment_edit.htm*", rtype="seller", rname="支付方式", rcode="payment_seller", rgroup="交易管理")
   @RequestMapping({"/seller/payment_edit.htm"})
   public ModelAndView payment_edit(HttpServletRequest request, HttpServletResponse response, String mark) {
     ModelAndView mv = mv = new JModelAndView(
       "user/default/usercenter/payment/" + mark + ".html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Map params = new HashMap();
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Payment sPayment=new Payment();
     sPayment.setStore_id(user.getStore_id());
     sPayment.setMark(mark);
     List objs = this.paymentService.selectList(sPayment);
     if (objs.size() > 0)
       mv.addObject("obj", objs.get(0));
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="支付方式保存", value="/seller/payment_save.htm*", rtype="seller", rname="支付方式", rcode="payment_seller", rgroup="交易管理")
   @RequestMapping({"/seller/payment_save.htm"})
   public ModelAndView payment_save(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     WebForm wf = new WebForm();
     if (!id.equals("")) {
       Payment obj = this.paymentService
         .selectById(CommUtil.null2Long(id));
       Payment payment = (Payment)wf.toPo( obj);
       this.paymentService.updateSelectiveById(payment);
     } else {
       Payment payment = (Payment)wf.toPo(Payment.class);
       payment.setAddTime(new Date());
       payment.setType("user");
       User user = this.userService.selectById(
         SecurityUserHolder.getCurrentUser().getId());
       payment.setStore_id(user.getStore_id());
       this.paymentService.insertSelective(payment);
     }
     mv.addObject("op_title", "支付方式保存成功");
     mv.addObject("url", CommUtil.getURL(request) + "/seller/payment.htm");
     return mv;
   }
 }


 
 
 