 package com.rt.shop.entity.query;
 
 import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.query.QueryObject;
 
 public class GoodsReturnLogQueryObject extends QueryObject
 {
   public GoodsReturnLogQueryObject(String currentPage, ModelAndView mv, String orderBy, String orderType)
   {
     super(currentPage, mv, orderBy, orderType);
   }
 
   public GoodsReturnLogQueryObject()
   {
   }
 }



 
 