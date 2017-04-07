 package com.rt.shop.view.web.tools;
 
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Area;
import com.rt.shop.service.IAreaService;
 
 @Component
 public class AreaViewTools
 {
 
   @Autowired
   private IAreaService areaService;
 
   public String generic_area_info(String area_id)
   {
     String area_info = "";
     Area area = this.areaService.selectById(CommUtil.null2Long(area_id));
     if (area != null) {
       area_info = area.getAreaName() + " ";
       if (area.getParent_id() != null) {
         String info = generic_area_info(area.getParent_id()
           .toString());
         area_info = info + area_info;
       }
     }
     return area_info;
   }
 }


 
 
 