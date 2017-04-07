 package com.rt.shop.tools;
 
 import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Bargain;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.IBargainService;
import com.rt.shop.service.ISysConfigService;
 
 @Component
 public class BargainSellerTools
 {
 
   @Autowired
   private IBargainGoodsService bargainGoodsService;
 
   @Autowired
   private IBargainService bargainServicve;
 
   @Autowired
   private ISysConfigService configService;
 
   public BigDecimal query_bargain_rebate(Object bargain_time)
   {
   
     Bargain sBargain=new Bargain();
     sBargain.setBargain_time(CommUtil.formatDate(
       CommUtil.null2String(bargain_time), "yyyy-MM-dd"));
     List bargain = this.bargainServicve.selectList(sBargain);
     BigDecimal bd = null;
     if (bargain.size() > 0)
       bd = ((Bargain)bargain.get(0)).getRebate();
     else {
       bd = this.configService.getSysConfig().getBargain_rebate();
     }
     return bd;
   }
 
   public int query_bargain_maximum(Object bargain_time)
   {
	   Bargain sBargain=new Bargain();
	     sBargain.setBargain_time(CommUtil.formatDate(
	       CommUtil.null2String(bargain_time), "yyyy-MM-dd"));
	     List bargain = this.bargainServicve.selectList(sBargain);
//     List bargain = this.bargainServicve.query(
//       "select obj from Bargain obj where obj.bargain_time =:bt", 
//       params, 0, 1);
     int bd = 0;
     if (bargain.size() > 0)
       bd = ((Bargain)bargain.get(0)).getMaximum();
     else {
       bd = this.configService.getSysConfig().getBargain_maximum();
     }
     return bd;
   }
 
   public int query_bargain_audit(Object bargain_time)
   {
     
     BargainGoods sBargainGoods=new BargainGoods();
     sBargainGoods.setBg_status(Integer.valueOf(1));
     sBargainGoods.setBg_time(CommUtil.formatDate(
       CommUtil.null2String(bargain_time), "yyyy-MM-dd"));
     List bargainGoods = this.bargainGoodsService.selectList(sBargainGoods);
       
     return bargainGoods.size();
   }
 }


 
 
 