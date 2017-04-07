 package com.rt.shop.view.admin.sellers.action;
 
 import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.Watermark;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.service.IWatermarkService;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class WaterMarkSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IWatermarkService watermarkService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IUserService userService;
   @Autowired
   private IStoreService storeService;
 
   @SecurityMapping(display = false, rsequence = 0, title="图片水印", value="/seller/watermark.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/watermark.htm"})
   public ModelAndView watermark(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/watermark.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Store store = storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (store != null) {
       
       Watermark sWaterMark=new Watermark();
       sWaterMark.setStore_id(store.getId());
       List wms = this.watermarkService.selectList(sWaterMark);
       //  .query("select obj from WaterMark obj where obj.store.id=:store_id", 
       if (wms.size() > 0) {
         mv.addObject("obj", wms.get(0));
       }
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="图片水印保存", value="/seller/watermark_save.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/watermark_save.htm"})
   public ModelAndView watermark_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String cmd)
   {
     ModelAndView mv = null;
     Store store = storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id());
     if (store != null) {
       WebForm wf = new WebForm();
       Watermark watermark = null;
       if (id.equals("")) {
         watermark = (Watermark)wf.toPo( Watermark.class);
         watermark.setAddTime(new Date());
       } else {
         Watermark obj = this.watermarkService.selectById(
           Long.valueOf(Long.parseLong(id)));
         watermark = (Watermark)wf.toPo(obj);
       }
       watermark.setStore_id(store.getId());
       String path = request.getSession().getServletContext()
         .getRealPath("/") + 
         "upload/wm";
       try {
         Map map = CommUtil.saveFileToServer(request, "wm_img", path, 
           null, null);
         if (!map.get("fileName").equals("")) {
           Accessory wm_image = new Accessory();
           wm_image.setAddTime(new Date());
           wm_image.setHeight(CommUtil.null2Int(map.get("height")));
           wm_image.setName(CommUtil.null2String(map.get("fileName")));
           wm_image.setPath("upload/wm");
           wm_image.setSize(CommUtil.null2Float(map.get("fileSize")));
           wm_image.setUser(SecurityUserHolder.getCurrentUser());
           wm_image.setWidth(CommUtil.null2Int("width"));
           this.accessoryService.insertSelective(wm_image);
           watermark.setWm_image_id(wm_image.getId());
         }
       }
       catch (IOException e) {
         e.printStackTrace();
       }
       if (id.equals(""))
         this.watermarkService.insertSelective(watermark);
       else
         this.watermarkService.updateSelectiveById(watermark);
       mv = new JModelAndView("success.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "水印设置成功");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您尚未开店");
     }
     mv.addObject("url", CommUtil.getURL(request) + "/seller/watermark.htm");
     return mv;
   }
 }


 
 
 