 package com.rt.shop.tools;
 
 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Payment;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.SecurityUserHolder;
 
 @Component
 public class PaymentTools
 {
 
   @Autowired
   private IPaymentService paymentService;
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IUserService userService;
 
   public boolean queryPayment(String mark, String type)
   {
   
     Payment sPayment=new Payment();
     sPayment.setMark(mark);
     sPayment.setType(type);
     List<Payment> objs = this.paymentService.selectList(sPayment);
       
     if (objs.size() > 0) {
       return ((Payment)objs.get(0)).getInstall();
     }
     return false;
   }
 
   public Map queryPayment(String mark) {
    
     Long store_id = null;
     store_id = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId()).getStore_id();
     Payment sPayment=new Payment();
     sPayment.setStore_id(store_id);
     sPayment.setType("user");
     sPayment.setMark(mark);
     List<Payment> objs = this.paymentService.selectList(sPayment);
     //  "select obj from Payment obj where obj.mark=:mark and obj.type=:type and obj.store.id=:store_id", 
     Map ret = new HashMap();
     if (objs.size() == 1) {
       ret.put("install", Boolean.valueOf(((Payment)objs.get(0)).getInstall()));
       ret.put("already", Boolean.valueOf(true));
     } else {
       ret.put("install", Boolean.valueOf(false));
       ret.put("already", Boolean.valueOf(false));
     }
     return ret;
   }
 
   public Map queryStorePayment(String mark, String store_id) {
     Map ret = new HashMap();
     
     Payment sPayment=new Payment();
     sPayment.setMark(mark);
     sPayment.setStore_id(CommUtil.null2Long(store_id));
     List<Payment> objs = this.paymentService.selectList(sPayment);
     
    //   .query("select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", 
     if (objs.size() == 1) {
       ret.put("install", Boolean.valueOf(((Payment)objs.get(0)).getInstall()));
       ret.put("content", ((Payment)objs.get(0)).getContent());
     } else {
       ret.put("install", Boolean.valueOf(false));
       ret.put("content", "");
     }
     return ret;
   }
 
   public Map queryShopPayment(String mark) {
     Map ret = new HashMap();
     
     Payment sPayment=new Payment();
     sPayment.setMark(mark);
     sPayment.setType("admin");
     List<Payment> objs = this.paymentService.selectList(sPayment);
     
     
     if (objs.size() == 1) {
       ret.put("install", Boolean.valueOf(((Payment)objs.get(0)).getInstall()));
       ret.put("content", ((Payment)objs.get(0)).getContent());
     } else {
       ret.put("install", Boolean.valueOf(false));
       ret.put("content", "");
     }
     return ret;
   }
 }


 
 
 