 package com.rt.shop.tools;
 
 import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.entity.GoodsSpecProperty;
import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsSpecPropertyService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IUserService;
 
 @Component
 public class StoreTools
 {
 
	 @Autowired
  private IUserService userSerivce;
	 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   @Autowired
   private IStoreService storeService;
   @Autowired
   private IGoodsSpecPropertyService goodsSpecPropertyService;
 
   public String genericProperty(GoodsSpecification spec)
   {
     String val = "";
     GoodsSpecProperty sGoodsSpecProperty=new GoodsSpecProperty();
     sGoodsSpecProperty.setSpec_id1(spec.getId());
     List<GoodsSpecProperty> list=goodsSpecPropertyService.selectList(sGoodsSpecProperty);
     for (GoodsSpecProperty gsp : list) {
       val = val + "," + gsp.getValue();
     }
     if (!val.equals("")) {
       return val.substring(1);
     }
     return "";
   }
 
   public String createUserFolder(HttpServletRequest request, SysConfig config, Long storeId)
   {
     String path = "";
     String uploadFilePath = config.getUploadFilePath();
     if (config.getImageSaveType().equals("sidImg")) {
       path = request.getSession().getServletContext().getRealPath("/") + 
         uploadFilePath + File.separator + "store" + 
         File.separator + storeId;
     }
 
     if (config.getImageSaveType().equals("sidYearImg")) {
       path = request.getSession().getServletContext().getRealPath("/") + 
         uploadFilePath + File.separator + "store" + 
         File.separator + storeId + File.separator + 
         CommUtil.formatTime("yyyy", new Date());
     }
     if (config.getImageSaveType().equals("sidYearMonthImg")) {
       path = request.getSession().getServletContext().getRealPath("/") + 
         uploadFilePath + File.separator + "store" + 
         File.separator + storeId + File.separator + 
         CommUtil.formatTime("yyyy", new Date()) + File.separator + 
         CommUtil.formatTime("MM", new Date());
     }
     if (config.getImageSaveType().equals("sidYearMonthDayImg")) {
       path = request.getSession().getServletContext().getRealPath("/") + 
         uploadFilePath + File.separator + "store" + 
         File.separator + storeId + File.separator + 
         CommUtil.formatTime("yyyy", new Date()) + File.separator + 
         CommUtil.formatTime("MM", new Date()) + File.separator + 
         CommUtil.formatTime("dd", new Date());
     }
     CommUtil.createFolder(path);
     return path;
   }
 
   public String createUserFolderURL(SysConfig config, Long storeId) {
     String path = "";
     String uploadFilePath = config.getUploadFilePath();
     if (config.getImageSaveType().equals("sidImg")) {
       path = uploadFilePath + "/store/" + storeId;
     }
 
     if (config.getImageSaveType().equals("sidYearImg")) {
       path = uploadFilePath + "/store/" + storeId + "/" + 
         CommUtil.formatTime("yyyy", new Date());
     }
     if (config.getImageSaveType().equals("sidYearMonthImg")) {
       path = uploadFilePath + "/store/" + storeId + "/" + 
         CommUtil.formatTime("yyyy", new Date()) + "/" + 
         CommUtil.formatTime("MM", new Date());
     }
     if (config.getImageSaveType().equals("sidYearMonthDayImg")) {
       path = uploadFilePath + "/store/" + storeId + "/" + 
         CommUtil.formatTime("yyyy", new Date()) + "/" + 
         CommUtil.formatTime("MM", new Date()) + "/" + 
         CommUtil.formatTime("dd", new Date());
     }
     return path;
   }
 
   public String generic_goods_class_info(GoodsClass gc)
   {
     if (gc != null) {
       String goods_class_info = generic_the_goods_class_info(gc);
       return goods_class_info.substring(0, goods_class_info.length() - 1);
     }
     return "";
   }
 
   private String generic_the_goods_class_info(GoodsClass gc) {
     if (gc != null) {
       String goods_class_info = gc.getClassName() + ">";
       if (gc.getParent_id() != null) {
    	  
         String class_info = generic_the_goods_class_info(goodsClassService.selectById(gc.getParent_id()));
         goods_class_info = class_info + goods_class_info;
       }
       return goods_class_info;
     }
     return "";
   }
 
   public int query_store_with_user(String user_id) {
     int status = 0;
     Store store = this.storeService.selectById(userSerivce.selectById(CommUtil.null2Long(user_id)).getStore_id());
     if (store != null)
       status = 1;
     return status;
   }
 }


 
 
 