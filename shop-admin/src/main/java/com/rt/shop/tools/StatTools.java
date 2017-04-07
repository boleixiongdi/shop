 package com.rt.shop.tools;
 
 import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Store;
import com.rt.shop.service.IComplaintService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IReportService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IUserService;
 
 @Component
 public class StatTools
 {
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IReportService reportService;
 
   @Autowired
   private IComplaintService complaintService;
 
   public int query_store(int count)
   {
     List stores = new ArrayList();
     Map params = new HashMap();
     Calendar cal = Calendar.getInstance();
     cal.add(6, count);
     params.put("time", cal.getTime());
     stores = this.storeService.selectList(null);
     //  "select obj from Store obj where obj.addTime>=:time", params, 
     return stores.size();
   }
 
   public int query_user(int count) {
     List users = new ArrayList();
     Map params = new HashMap();
     Calendar cal = Calendar.getInstance();
     cal.add(6, count);
     params.put("time", cal.getTime());
     users = this.userService.selectList(null);
      // "select obj from User obj where obj.addTime>=:time", params, 
     return users.size();
   }
 
   public int query_goods(int count) {
     List goods = new ArrayList();
     Map params = new HashMap();
     Calendar cal = Calendar.getInstance();
     cal.add(6, count);
     params.put("time", cal.getTime());
     goods = this.goodsService.selectList(null);
      // "select obj from Goods obj where obj.addTime>=:time", params, 
     return goods.size();
   }
 
   public int query_order(int count) {
     List orders = new ArrayList();
     Map params = new HashMap();
     Calendar cal = Calendar.getInstance();
     cal.add(6, count);
     params.put("time", cal.getTime());
     orders = this.orderFormService.selectList(null);
     //  "select obj from OrderForm obj where obj.addTime>=:time", 
     return orders.size();
   }
 
   public int query_all_user() {
     Map params = new HashMap();
     params.put("userRole", "ADMIN");
     List users = this.userService.selectList(null);
     //  "select obj from User obj where obj.userRole!=:userRole", 
     return users.size();
   }
 
   public int query_all_goods()
   {
     
     return goodsService.selectCount(null);
   }
 
   public int query_all_store() {
    
     return storeService.selectCount(null);
   }
 
   public int query_update_store() {
	   Store sStore=new Store();
	   sStore.setUpdate_grade_id(null);
     List stores = this.storeService.selectList(sStore);
       
     return stores.size();
   }
 
   public double query_all_amount() {
     double price = 0.0D;
     
     OrderForm sOrderForm=new OrderForm();
     sOrderForm.setOrder_status(Integer.valueOf(60));
     List<OrderForm> ofs = this.orderFormService.selectList(sOrderForm);
      
     for (OrderForm of : ofs) {
       price = CommUtil.null2Double(of.getTotalPrice()) + price;
     }
     return price;
   }
 
   public int query_complaint(int count) {
     List objs = new ArrayList();
     Map params = new HashMap();
     Calendar cal = Calendar.getInstance();
     cal.add(6, count);
     params.put("time", cal.getTime());
     params.put("status", Integer.valueOf(0));
     objs = this.complaintService.selectList(null);
      
     //  "select obj from Complaint obj where obj.addTime>=:time and obj.status=:status", 
     return objs.size();
   }
 
   public int query_report(int count) {
     List objs = new ArrayList();
     Map params = new HashMap();
     Calendar cal = Calendar.getInstance();
     cal.add(6, count);
     params.put("time", cal.getTime());
     params.put("status", Integer.valueOf(0));
     objs = this.reportService.selectList(null);
   
   //    "select obj from Report obj where obj.addTime>=:time and obj.status=:status", 
     return objs.size();
   }
 }


 
 
 