 package com.rt.shop.tools;
 
 import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.entity.Bargain;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.IBargainService;
import com.rt.shop.service.ISysConfigService;
 
 @Component
 public class BargainManageTools
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
     sBargain.setBargain_time((Date)bargain_time);
     List bargain = this.bargainServicve.selectList(sBargain);
      // "select obj from Bargain obj where obj.bargain_time =:bt", 
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
	     sBargain.setBargain_time((Date)bargain_time);
	     List<Bargain> bargain = this.bargainServicve.selectList(sBargain);
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
	   BargainGoods sBargain=new BargainGoods();
	     sBargain.setBg_time((Date)bargain_time);
	     List<BargainGoods> bargain = this.bargainGoodsService.selectList(sBargain);
     int bd = 0;
     for (BargainGoods bg : bargain) {
       if (bg.getBg_status() == 1) {
         bd++;
       }
     }
     return bd;
   }
 
   public int query_bargain_apply(Object bargain_time)
   {
	   BargainGoods sBargain=new BargainGoods();
	     sBargain.setBg_time((Date)bargain_time);
	     List bargain = this.bargainGoodsService.selectList(sBargain);
     return bargain.size();
   }
 }


 
 
 