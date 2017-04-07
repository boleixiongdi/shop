/* package com.rt.shop.common.service.impl;
 
 import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rt.shop.common.service.IQueryService;
 
 @Service
 @Transactional
 public class QueryService
   implements IQueryService
 {
 
   @Autowired
   @Qualifier("genericEntityDao")
   private GenericEntityDao geDao;
 
   public GenericEntityDao getGeDao()
   {
     return this.geDao;
   }
 
   public void setGeDao(GenericEntityDao geDao) {
     this.geDao = geDao;
   }
 
   public List query(String scope, Map params, int page, int pageSize)
   {
     return this.geDao.query(scope, params, page, pageSize);
   }
 }
*/