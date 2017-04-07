 package com.rt.shop.tools;
 
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.entity.Area;
import com.rt.shop.service.IAreaService;
 
 @Component
 public class AreaManageTools
 {
 
   @Autowired
   private IAreaService areaService;
 
   public String generic_area_info(Area area)
   {
     String area_info = "";
     if (area != null) {
       area_info = area.getAreaName() + " ";
       if (area.getParent_id()!= null) {
    	   Area p=areaService.selectById(area.getParent_id());
         String info = generic_area_info(p);
         area_info = info + area_info;
       }
     }
     return area_info;
   }
 }


 
 
 