 package com.rt.shop.view.web.tools;
 
 import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.AdvPos;
import com.rt.shop.entity.Advert;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAdvPosService;
import com.rt.shop.service.IAdvertService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsFloorService;
import com.rt.shop.service.IGoodsService;
 
 @Component
 public class GoodsFloorViewTools
 {
 
   @Autowired
   private IGoodsFloorService goodsFloorService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IAdvPosService advertPositionService;
 
   @Autowired
   private IAdvertService advertService;
 
   @Autowired
   private IGoodsBrandService goodsBrandService;
 
   public List<GoodsClass> generic_gf_gc(String json)
   {
     List gcs = new ArrayList();
     if ((json != null) && (!json.equals(""))) {
       List<Map> list = (List)Json.fromJson(List.class, json);
       for (Map map : list) {
         GoodsClass the_gc = this.goodsClassService.selectById(
           CommUtil.null2Long(map.get("pid")));
         if (the_gc != null) {
           int count = CommUtil.null2Int(map.get("gc_count"));
           GoodsClass gc = new GoodsClass();
           gc.setId(the_gc.getId());
           gc.setClassName(the_gc.getClassName());
           for (int i = 1; i <= count; i++) {
             GoodsClass child = this.goodsClassService.selectById(CommUtil.null2Long(map.get("gc_id" +i)));
             gc.getChilds().add(child);
           }
           gcs.add(gc);
         }
       }
     }
     return gcs;
   }
 
   public List<Goods> generic_goods(String json) {
     List goods_list = new ArrayList();
     if ((json != null) && (!json.equals(""))) {
       Map map = (Map)Json.fromJson(Map.class, json);
       for (int i = 1; i <= 10; i++) {
         String key = "goods_id" + i;
 
         Goods goods = this.goodsService.selectById(
           CommUtil.null2Long(map.get(key)));
         if (goods != null) {
           goods_list.add(goods);
         }
       }
     }
     return goods_list;
   }
 
   public Map generic_goods_list(String json) {
     Map map = new HashMap();
     map.put("list_title", "商品排行");
     if ((json != null) && (!json.equals(""))) {
       Map list = (Map)Json.fromJson(Map.class, json);
       map.put("list_title", CommUtil.null2String(list.get("list_title")));
       map.put("goods1", this.goodsService.selectById(
         CommUtil.null2Long(list.get("goods_id1"))));
       map.put("goods2", this.goodsService.selectById(
         CommUtil.null2Long(list.get("goods_id2"))));
       map.put("goods3", this.goodsService.selectById(
         CommUtil.null2Long(list.get("goods_id3"))));
       map.put("goods4", this.goodsService.selectById(
         CommUtil.null2Long(list.get("goods_id4"))));
       map.put("goods5", this.goodsService.selectById(
         CommUtil.null2Long(list.get("goods_id5"))));
       map.put("goods6", this.goodsService.selectById(
         CommUtil.null2Long(list.get("goods_id6"))));
     }
     return map;
   }
 
   public String generic_adv(String web_url, String json) {
     String template = "<div style='float:left;overflow:hidden;'>";
     if ((json != null) && (!json.equals(""))) {
       Map map = (Map)Json.fromJson(Map.class, json);
       if (CommUtil.null2String(map.get("adv_id")).equals("")) {
         Accessory img = this.accessoryService.selectById(
           CommUtil.null2Long(map.get("acc_id")));
         if (img != null) {
           String url = CommUtil.null2String(map.get("acc_url"));
           template = template + "<a href='" + url + 
             "' target='_blank'><img src='" + web_url + "/" + 
             img.getPath() + "/" + img.getName() + "' /></a>";
         }
       } else {
         AdvPos ap = this.advertPositionService
           .selectById(CommUtil.null2Long(map.get("adv_id")));
         AdvPos obj = new AdvPos();
         obj.setAp_type(ap.getAp_type());
         obj.setAp_status(ap.getAp_status());
         obj.setAp_show_type(ap.getAp_show_type());
         obj.setAp_width(ap.getAp_width());
         obj.setAp_height(ap.getAp_height());
         List<Advert> advs = new ArrayList();
         Advert sAdvert=new Advert();
         sAdvert.setAd_ap_id(ap.getId());
    	 List<Advert> advChilds= advertService.selectList(sAdvert);
    	 if(advChilds!=null && advChilds.size()>0){
    		 for (Advert temp_adv : advChilds) {
    	           if ((temp_adv.getAd_status() != 1) || 
    	             (!temp_adv.getAd_begin_time().before(new Date())) || 
    	             (!temp_adv.getAd_end_time().after(new Date()))) continue;
    	           advs.add(temp_adv);
    	         }
    	 }
        
 
         if (advs.size() > 0) {
           if (obj.getAp_type().equals("img")) {
             if (obj.getAp_show_type() == 0) {
//            	 Accessory sAccessory=new Accessory();
//					sAccessory.setId(advs.get(0).getAd_acc_id());
//					Accessory ap_acc=accessoryService.selectOne(sAccessory);
               obj.setAp_acc_id(advs.get(0).getAd_acc_id());	 
             //  obj.setAp_acc(((Advert)advs.get(0)).getAd_acc());
               obj.setAp_acc_url(((Advert)advs.get(0)).getAd_url());
               obj.setAdv_id(CommUtil.null2String(((Advert)advs.get(0))
                 .getId()));
               
             }
             if (obj.getAp_show_type() == 1) {
               Random random = new Random();
               int i = random.nextInt(advs.size());
            //   obj.setAp_acc(((Advert)advs.get(i)).getAd_acc());
               obj.setAp_acc_id(advs.get(i).getAd_acc_id());	
               obj.setAp_acc_url(((Advert)advs.get(i)).getAd_url());
               obj.setAdv_id(CommUtil.null2String(((Advert)advs.get(i))
                 .getId()));
             }
           }
         } else {
        	 obj.setAp_acc_id(ap.getAp_acc_id());	
         //  obj.setAp_acc(ap.getAp_acc());
           obj.setAp_text(ap.getAp_text());
           obj.setAp_acc_url(ap.getAp_acc_url());
           Advert adv = new Advert();
           adv.setAd_url(obj.getAp_acc_url());
           adv.setAd_acc_id(ap.getAp_acc_id());
        //   adv.setAd_acc(ap.getAp_acc());
           obj.getAdvs().add(adv);
         }
    	 Accessory sAccessory=new Accessory();
			sAccessory.setId(obj.getAp_acc_id());
			Accessory ap_acc=accessoryService.selectOne(sAccessory);
         template = template + "<a href='" + obj.getAp_acc_url() + 
           "' target='_blank'><img src='" + web_url + "/" + 
           ap_acc.getPath() + "/" + 
           ap_acc.getName() + "' /></a>";
       }
     }
     template = template + "</div>";
     return template;
   }
 
   public List<GoodsBrand> generic_brand(String json) {
     List brands = new ArrayList();
     if ((json != null) && (!json.equals(""))) {
       Map map = (Map)Json.fromJson(Map.class, json);
       for (int i = 1; i <= 11; i++) {
         String key = "brand_id" + i;
         GoodsBrand brand = this.goodsBrandService.selectById(
           CommUtil.null2Long(map.get(key)));
         if (brand != null) {
           brands.add(brand);
         }
       }
     }
     return brands;
   }
 }


 
 
 