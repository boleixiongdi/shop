 package com.rt.shop.view.web.tools;
 
 import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.ISysConfigService;
 
 @Component
 public class ImageViewTools
 {
 
   @Autowired
   private ISysConfigService configService;
   @Autowired
	private IAccessoryService accessoryService;
 
   public String random_login_img()
   {
     String img = "";
     SysConfig config = this.configService.getSysConfig();
     	Accessory sAccessory=new Accessory();
		sAccessory.setConfig_id(config.getId());
		List<Accessory> loginImgs=accessoryService.selectList(sAccessory);
     if (loginImgs.size() > 0) {
       Random random = new Random();
       Accessory acc = loginImgs.get(
         random.nextInt(loginImgs.size()));
       img = acc.getPath() + "/" + acc.getName();
     } else {
       img = "resources/style/common/images/login_img.jpg";
     }
     return img;
   }
 }


 
 
 