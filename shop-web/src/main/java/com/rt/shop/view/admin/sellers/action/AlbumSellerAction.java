 package com.rt.shop.view.admin.sellers.action;
 
 import java.awt.Font;
import java.io.File;
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

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.User;
import com.rt.shop.entity.Watermark;
import com.rt.shop.entity.query.AccessoryQueryObject;
import com.rt.shop.entity.query.AlbumQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.service.IWatermarkService;
import com.rt.shop.tools.database.DatabaseTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.AlbumViewTools;
 
 @Controller
 public class AlbumSellerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IAlbumService albumService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IWatermarkService waterMarkService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IGoodsService goodsSerivce;
 
   @Autowired
   private IStoreService storeService;
   @Autowired
   private AlbumViewTools albumViewTools;
   private DatabaseTools databaseTools=new DatabaseTools();
   
 
   @SecurityMapping(display = false, rsequence = 0, title="相册列表", value="/seller/album.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album.htm"})
   public ModelAndView album(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/album.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     AlbumQueryObject aqo = new AlbumQueryObject();
     aqo.addQuery("obj.user.id", 
       new SysMap("user_id", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     aqo.setCurrentPage(Integer.valueOf(CommUtil.null2Int(currentPage)));
     aqo.setOrderBy("album_sequence");
     aqo.setOrderType("asc");
     Page pList = this.albumService.selectPage(new Page<Album>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     CommWebUtil.saveIPageList2ModelAndView(url + "/seller/album.htm", "", "", 
       pList, mv);
     mv.addObject("albumViewTools", this.albumViewTools);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="新增相册", value="/seller/album_add.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_add.htm"})
   public ModelAndView album_add(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/album_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="新增相册", value="/seller/album_edit.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_edit.htm"})
   public ModelAndView album_edit(HttpServletRequest request, HttpServletResponse response, String currentPage, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/album_add.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Album obj = this.albumService.selectById(CommUtil.null2Long(id));
     mv.addObject("obj", obj);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="相册保存", value="/seller/album_save.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_save.htm"})
   public String album_save(HttpServletRequest request, HttpServletResponse response,Album album, String id, String currentPage) {
   
     if (id.equals("")) {
       album.setAddTime(new Date());
     } else {
       Album obj = this.albumService.selectById(Long.valueOf(Long.parseLong(id)));
     }
     album.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     boolean ret = true;
     if (id.equals(""))
       ret = this.albumService.insertSelective(album);
     else
       ret = this.albumService.updateSelectiveById(album);
     return "redirect:album.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="图片上传", value="/seller/album_upload.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_upload.htm"})
   public ModelAndView album_upload(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/album_upload.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
    
     Album sAlbum=new Album();
     sAlbum.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List objs = this.albumService.selectList(sAlbum, "album_sequence asc");
      // .query("select obj from Album obj where obj.user.id=:user_id order by obj.album_sequence asc", 
     mv.addObject("objs", objs);
     mv.addObject("currentPage", currentPage);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="相册删除", value="/seller/album_del.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_del.htm"})
   public String album_del(HttpServletRequest request, String mulitId) { String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
//         List<Accessory> accs = this.albumService.selectById(
//           Long.valueOf(Long.parseLong(id))).getPhotos();
         Accessory sAccessory=new Accessory();
         sAccessory.setAlbum_id(Long.parseLong(id));
         List<Accessory> accs =accessoryService.selectList(sAccessory);
         for (Accessory acc : accs) {
           CommWebUtil.del_acc(request, acc);
           this.databaseTools
             .execute("update shopping_album set album_cover_id=null where album_cover_id=" + 
             acc.getId());
         }
         this.albumService.deleteById(Long.valueOf(Long.parseLong(id)));
       }
     }
     return "redirect:album.htm"; }
 
   @SecurityMapping(display = false, rsequence = 0, title="相册封面设置", value="/seller/album_cover.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_cover.htm"})
   public String album_cover(HttpServletRequest request, String album_id, String id, String currentPage) {
     Accessory album_cover = this.accessoryService.selectById(
       Long.valueOf(Long.parseLong(id)));
     Album album = this.albumService.selectById(Long.valueOf(Long.parseLong(album_id)));
     album.setAlbum_cover_id(album_cover.getId());
     this.albumService.updateSelectiveById(album);
     return "redirect:album_image.htm?id=" + album_id + "&currentPage=" + 
       currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="相册转移", value="/seller/album_transfer.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_transfer.htm"})
   public ModelAndView album_transfer(HttpServletRequest request, HttpServletResponse response, String currentPage, String album_id, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/album_transfer.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Map params = new HashMap();
     params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
     Album sAlbum=new Album();
     sAlbum.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List objs = this.albumService.selectList(sAlbum, "album_sequence asc");
     //  .query("select obj from Album obj where obj.user.id=:user_id order by obj.album_sequence asc", 
     mv.addObject("objs", objs);
     mv.addObject("currentPage", currentPage);
     mv.addObject("album_id", album_id);
     mv.addObject("mulitId", id);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="图片转移相册", value="/seller/album_transfer_save.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_transfer_save.htm"})
   public String album_transfer_save(HttpServletRequest request, String mulitId, String album_id, String to_album_id, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Accessory acc = this.accessoryService.selectById(
           Long.valueOf(Long.parseLong(id)));
         Album to_album = this.albumService.selectById(
           Long.valueOf(Long.parseLong(to_album_id)));
         acc.setAlbum(to_album);
         this.accessoryService.updateSelectiveById(acc);
       }
     }
     return "redirect:album_image.htm?id=" + album_id + "&currentPage=" + 
       currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="图片列表", value="/seller/album_image.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_image.htm"})
   public ModelAndView album_image(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/album_image.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Album album = this.albumService.selectById(Long.valueOf(Long.parseLong(id)));
     AccessoryQueryObject aqo = new AccessoryQueryObject();
     if ((id != null) && (!id.equals("")))
       aqo.addQuery("obj.album.id", 
         new SysMap("album_id", CommUtil.null2Long(id)), "=");
     else {
       aqo.addQuery("obj.album.id is null", null);
     }
     aqo.addQuery("obj.album.user.id", 
       new SysMap("user_id", 
       SecurityUserHolder.getCurrentUser().getId()), "=");
     aqo.setCurrentPage(Integer.valueOf(CommUtil.null2Int(currentPage)));
     aqo.setPageSize(Integer.valueOf(15));
     Page pList = this.accessoryService.selectPage(new Page<Accessory>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     String url = this.configService.getSysConfig().getAddress();
     if ((url == null) || (url.equals(""))) {
       url = CommUtil.getURL(request);
     }
     CommWebUtil.saveIPageList2ModelAndView(url + "/seller/album_image.htm", 
       "", "&id=" + id, pList, mv);
     Map params = new HashMap();
     params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
     Album sAlbum=new Album();
     sAlbum.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     List albums = this.albumService.selectList(sAlbum, "album_sequence asc");
     //  .query("select obj from Album obj where obj.user.id=:user_id order by obj.album_sequence asc", 
     mv.addObject("albums", albums);
     mv.addObject("album", album);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="图片幻灯查看", value="/seller/image_slide.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/image_slide.htm"})
   public ModelAndView image_slide(HttpServletRequest request, HttpServletResponse response, String album_id, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/image_slide.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     Album album = this.albumService
       .selectById(CommUtil.null2Long(album_id));
     mv.addObject("album", album);
     Accessory current_img = this.accessoryService.selectById(
       CommUtil.null2Long(id));
     mv.addObject("current_img", current_img);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="相册内图片删除", value="/seller/album_img_del.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_img_del.htm"})
   public String album_img_del(HttpServletRequest request, String mulitId, String album_id, String currentPage) {
     String[] ids = mulitId.split(",");
     for (String id : ids) {
       if (!id.equals("")) {
         Accessory acc = this.accessoryService.selectById(
           Long.valueOf(Long.parseLong(id)));
         if (acc.getCover_album() != null) {
           acc.getCover_album().setAlbum_cover_id(null);
           this.albumService.updateSelectiveById(acc.getCover_album());
         }
         CommWebUtil.del_acc(request, acc);
         for (Goods goods : acc.getGoods_main_list()) {
           goods.setGoods_main_photo(null);
           this.goodsSerivce.updateSelectiveById(goods);
         }
         for (Goods goods : acc.getGoods_list()) {
           goods.getGoods_photos().remove(acc);
           this.goodsSerivce.updateSelectiveById(goods);
         }
         this.accessoryService.deleteById(acc.getId());
       }
     }
     return "redirect:album_image.htm?id=" + album_id + "&currentPage=" + 
       currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="图片转移相册", value="/seller/album_watermark.htm*", rtype="seller", rname="图片管理", rcode="album_seller", rgroup="其他设置")
   @RequestMapping({"/seller/album_watermark.htm"})
   public String album_watermark(HttpServletRequest request, String mulitId, String album_id, String to_album_id, String currentPage) {
     Long store_id = null;
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     Store store=storeService.selectById(user.getStore_id());
     if (store != null) {
       store_id = store.getId();
     }
     if (store_id != null) {
    	 Watermark sWatermark=new Watermark();
    	 sWatermark.setStore_id(store_id);
       Watermark waterMark = this.waterMarkService.selectOne(sWatermark);
       if (waterMark != null) {
         String[] ids = mulitId.split(",");
         for (String id : ids) {
           if (!id.equals("")) {
             Accessory acc = this.accessoryService.selectById(
               Long.valueOf(Long.parseLong(id)));
             String path = request.getSession().getServletContext()
               .getRealPath("/") + 
               acc.getPath() + 
               File.separator + 
               acc.getName();
             Accessory wm_image=accessoryService.selectById(waterMark.getWm_image_id());
             if (waterMark.getWm_image_open()) {
               String wm_path = request.getSession()
                 .getServletContext().getRealPath("/") + 
                 wm_image.getPath() + 
                 File.separator + 
                 wm_image.getName();
               CommUtil.waterMarkWithImage(wm_path, path, 
                 waterMark.getWm_image_pos(), 
                 waterMark.getWm_image_alpha());
             }
             if (waterMark.getWm_text_open()) {
               Font font = new Font(waterMark.getWm_text_font(), 
                 1, waterMark.getWm_text_font_size());
               CommUtil.waterMarkWithText(path, path, 
                 waterMark.getWm_text(), 
                 waterMark.getWm_text_color(), font, 
                 waterMark.getWm_text_pos(), 100.0F);
             }
           }
         }
       }
     }
     return "redirect:album_image.htm?id=" + album_id + "&currentPage=" + 
       currentPage;
   }
 }


 
 
 