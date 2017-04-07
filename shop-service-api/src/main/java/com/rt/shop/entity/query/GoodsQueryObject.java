 package com.rt.shop.entity.query;
 
 import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.query.QueryObject;
 
 public class GoodsQueryObject extends QueryObject
 {
   public GoodsQueryObject(String currentPage, ModelAndView mv, String orderBy, String orderType)
   {
     super(currentPage, mv, orderBy, orderType);
   }
 
   public GoodsQueryObject(String currentPage, Map<String, Object> map, String orderBy, String orderType)
   {
     super(currentPage, map, orderBy, orderType);
   }
   
   public GoodsQueryObject()
   {
   }
 }



 
 