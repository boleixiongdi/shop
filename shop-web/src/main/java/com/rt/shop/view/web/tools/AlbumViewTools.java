 package com.rt.shop.view.web.tools;
 
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Accessory;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAlbumService;
 
 @Component
 public class AlbumViewTools
 {
 
   @Autowired
   private IAlbumService albumService;
 
   @Autowired
   private IAccessoryService accessoryService;
 
   public List<Accessory> query_album(String id)
   {
     List list = new ArrayList();
     if ((id != null) && (!id.equals(""))) {
    	 Accessory sAccessory=new Accessory();
    	 sAccessory.setAlbum_id(CommUtil.null2Long(id));
         list = this.accessoryService.selectList(sAccessory);
       //  "select obj from Accessory obj where obj.album.id=:album_id", 
     } else {
    	 Accessory sAccessory=new Accessory();
    	 sAccessory.setAlbum_id(null);
         list = this.accessoryService.selectList(sAccessory);
       //  "select obj from Accessory obj where obj.album.id is null", 
     }
     return list;
   }
 }


 
 
 