 package com.rt.shop.view.web.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rt.shop.entity.Favorite;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Store;
import com.rt.shop.service.IFavoriteService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class FavoriteViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IFavoriteService favoriteService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IStoreService storeService;
 
   @RequestMapping({"/add_goods_favorite.htm"})
   public void add_goods_favorite(HttpServletResponse response, String id)
   {
    
     Favorite sFavorite=new Favorite();
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sFavorite.setGoods_id(CommWebUtil.null2Long(id));
     List list = this.favoriteService.selectList(sFavorite);
     //  "select obj from Favorite obj where obj.user.id=:user_id and obj.goods.id=:goods_id", 
     int ret = 0;
     if (list.size() == 0) {
       Goods goods = this.goodsService.selectById(CommWebUtil.null2Long(id));
       Favorite obj = new Favorite();
       obj.setAddTime(new Date());
       obj.setType(0);
       obj.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       obj.setGoods_id(goods.getId());
       this.favoriteService.insertSelective(obj);
       goods.setGoods_collect(goods.getGoods_collect() + 1);
       this.goodsService.updateSelectiveById(goods);
     } else {
       ret = 1;
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     
     Map map = new HashMap();
     map.put("ret", ret);
     
     try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
 
   @RequestMapping({"/add_store_favorite.htm"})
   public void add_store_favorite(HttpServletResponse response, String id) {
     
     Favorite sFavorite=new Favorite();
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sFavorite.setStore_id(CommWebUtil.null2Long(id));
     List list = this.favoriteService.selectList(sFavorite);
    
     int ret = 0;
     if (list.size() == 0) {
       Favorite obj = new Favorite();
       obj.setAddTime(new Date());
       obj.setType(1);
       obj.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       obj.setStore_id(this.storeService.selectById(CommWebUtil.null2Long(id)).getId());
       this.favoriteService.insertSelective(obj);
       Store store = storeService.selectById(obj.getStore_id());
       store.setFavorite_count(store.getFavorite_count() + 1);
       this.storeService.updateSelectiveById(store);
     } else {
       ret = 1;
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(ret);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     
     Map map = new HashMap();
     
     map.put("ret", ret);
     try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
   @RequestMapping({"/wap/add_goods_favorite.htm"})
   public void add_goods_favorite1(HttpServletResponse response, String id)
   {
     
     Favorite sFavorite=new Favorite();
     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     sFavorite.setGoods_id(CommWebUtil.null2Long(id));
     List list = this.favoriteService.selectList(sFavorite);
    
     int ret = 0;
     if (list.size() == 0) {
       Goods goods = this.goodsService.selectById(CommWebUtil.null2Long(id));
       Favorite obj = new Favorite();
       obj.setAddTime(new Date());
       obj.setType(0);
       obj.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       obj.setGoods_id(goods.getId());
       this.favoriteService.insertSelective(obj);
       goods.setGoods_collect(goods.getGoods_collect() + 1);
       this.goodsService.updateSelectiveById(goods);
     } else {
       ret = 1;
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");

     Map map = new HashMap();
     map.put("ret", ret);
     
     try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
 
   @RequestMapping({"/wap/add_store_favorite.htm"})
   public void add_store_favorite1(HttpServletResponse response, String id) {
	   Favorite sFavorite=new Favorite();
	     sFavorite.setUser_id(SecurityUserHolder.getCurrentUser().getId());
	     sFavorite.setStore_id(CommWebUtil.null2Long(id));
	     List list = this.favoriteService.selectList(sFavorite);
     int ret = 0;
     if (list.size() == 0) {
       Favorite obj = new Favorite();
       obj.setAddTime(new Date());
       obj.setType(1);
       obj.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       obj.setStore_id(this.storeService.selectById(CommWebUtil.null2Long(id)).getId());
       this.favoriteService.insertSelective(obj);
       Store store = storeService.selectById(obj.getStore_id());
       store.setFavorite_count(store.getFavorite_count() + 1);
       this.storeService.updateSelectiveById(store);
     } else {
       ret = 1;
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     
     Map map = new HashMap();
     
     map.put("ret", ret);
     try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
 }


 
 
 