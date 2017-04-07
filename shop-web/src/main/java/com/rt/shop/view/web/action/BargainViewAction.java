 package com.rt.shop.view.web.action;
 
 import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.DateUtils;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.query.BargainGoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class BargainViewAction
 {
 
	 @Autowired
   private IAccessoryService  accessoryService;
	   
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IBargainGoodsService bargainGoodsService;
 
   @RequestMapping({"/bargain.htm"})
   public ModelAndView bargain(HttpServletRequest request, HttpServletResponse response, String bg_time, String currentPage, String orderBy, String orderType)
   {
     ModelAndView mv = new JModelAndView("bargain.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
  
     BargainGoods ssBargainGoods=new BargainGoods();
//     if (CommUtil.null2String(bg_time).equals("")){
//    	 ssBargainGoods.setBg_time(CommUtil.formatDate(CommUtil.formatTime("yyyy-MM-dd",new Date())));
//     }else{
//    	 ssBargainGoods.setBg_time(CommUtil.formatDate(bg_time));
//     }
     ssBargainGoods.setBg_status(1);
     Page<BargainGoods> page = new Page<BargainGoods>(0, 20);
     List<Goods> pList = this.bargainGoodsService.selectBargainGoodsPage(page,ssBargainGoods);
     for(Goods g1 : pList){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
     Page page1=new Page<>(0, 12);
     page1.setRecords(pList);
     page1.setTotal(pList.size());
     CommWebUtil.saveIPageList2ModelAndView("", "", "", page1, mv);
     Calendar cal = Calendar.getInstance();
     if (CommUtil.null2String(bg_time).equals("")) {
       bg_time = CommUtil.formatShortDate(new Date());
     }
     cal.setTime(CommUtil.formatDate(bg_time));
     cal.add(6, 1);
    
     BargainGoods sBargainGoods=new BargainGoods();
   //  sBargainGoods.setBg_time(DateUtils.getDate(DateUtils.));
     sBargainGoods.setBg_status(Integer.valueOf(1));
     List<Goods> bgs = this.bargainGoodsService.selectBargainGoodsPage(new Page<BargainGoods>(0, 5), sBargainGoods);
     for(Goods g1 : bgs){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
     mv.addObject("bgs", bgs);
     int day_count = this.configService.getSysConfig().getBargain_validity();
     List dates = new ArrayList();
     for (int i = 0; i < day_count; i++) {
       cal = Calendar.getInstance();
       cal.add(6, i);
       dates.add(cal.getTime());
     }
     mv.addObject("dates", dates);
     mv.addObject("bg_time", bg_time);
     return mv;
   }
 }


 
 
 