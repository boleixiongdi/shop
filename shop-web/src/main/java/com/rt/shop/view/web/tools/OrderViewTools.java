 package com.rt.shop.view.web.tools;
 
 import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.entity.OrderForm;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.util.SecurityUserHolder;
 
 @Component
 public class OrderViewTools
 {
 
   @Autowired
   private IOrderFormService orderFormService;
 
   public int query_user_order(String order_status)
   {
   
     int status = -1;
     if (order_status.equals("order_submit")) {
       status = 10;
     }
     if (order_status.equals("order_pay")) {
       status = 20;
     }
     if (order_status.equals("order_shipping")) {
       status = 30;
     }
     if (order_status.equals("order_receive")) {
       status = 40;
     }
     if (order_status.equals("order_finish")) {
       status = 60;
     }
     if (order_status.equals("order_cancel")) {
       status = 0;
     }
    
     OrderForm sOrderForm=new OrderForm();
     sOrderForm.setOrder_status(Integer.valueOf(status));
     sOrderForm.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List ofs = this.orderFormService.selectList(sOrderForm);
    //   "select obj from OrderForm obj where obj.order_status=:status and obj.user.id=:user_id", 
     return ofs.size();
   }
 
   public int query_store_order(String order_status) {
     if (SecurityUserHolder.getCurrentUser().getStore_id()!= null) {
       Map params = new HashMap();
       int status = -1;
       if (order_status.equals("order_submit")) {
         status = 10;
       }
       if (order_status.equals("order_pay")) {
         status = 20;
       }
       if (order_status.equals("order_shipping")) {
         status = 30;
       }
       if (order_status.equals("order_receive")) {
         status = 40;
       }
       if (order_status.equals("order_finish")) {
         status = 60;
       }
       if (order_status.equals("order_cancel")) {
         status = 0;
       }
       params.put("status", Integer.valueOf(status));
       params.put("store_id", SecurityUserHolder.getCurrentUser().getStore_id());
       
       OrderForm sOrderForm=new OrderForm();
       sOrderForm.setOrder_status(Integer.valueOf(status));
       sOrderForm.setStore_id(SecurityUserHolder.getCurrentUser().getStore_id());
       List ofs = this.orderFormService.selectList(sOrderForm);
//         .query(
//         "select obj from OrderForm obj where obj.order_status=:status and obj.store.id=:store_id", 
//         params, -1, -1);
       return ofs.size();
     }
     return 0;
   }
 }


 
 
 