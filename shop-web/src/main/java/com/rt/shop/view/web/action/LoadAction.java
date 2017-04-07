 package com.rt.shop.view.web.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rt.shop.entity.Area;
import com.rt.shop.service.IAreaService;
 
 @Controller
 public class LoadAction
 {
 
   @Autowired
   private IAreaService areaService;
 
   @RequestMapping({"/load_area.htm"})
   public void load_area(HttpServletRequest request, HttpServletResponse response, String pid)
   {
     Map params = new HashMap();
     params.put("pid", Long.valueOf(Long.parseLong(pid)));
     Area sArea=new Area();
     sArea.setParent_id(Long.parseLong(pid));
     List<Area> areas = this.areaService.selectList(sArea);
       //"select obj from Area obj where obj.parent.id=:pid", params, 
     List list = new ArrayList();
     for (Area area : areas) {
       Map map = new HashMap();
       map.put("id", area.getId());
       map.put("areaName", area.getAreaName());
       list.add(map);
     }
     String temp = Json.toJson(list, JsonFormat.compact());
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(temp);
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 }


 
 
 