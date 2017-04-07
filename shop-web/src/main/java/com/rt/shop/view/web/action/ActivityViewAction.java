 package com.rt.shop.view.web.action;
 
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
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Activity;
import com.rt.shop.entity.ActivityGoods;
import com.rt.shop.entity.Article;
import com.rt.shop.entity.query.ActivityGoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IActivityGoodsService;
import com.rt.shop.service.IActivityService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class ActivityViewAction extends BaseController
 {
 
	 @Autowired
	   private IAccessoryService accessoryService;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IActivityService activityService;
 
   @Autowired
   private IActivityGoodsService activityGoodsService;
 
   @RequestMapping({"/activityList.htm"})
   public ModelAndView activityList(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("activityList.html",
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
     
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 mv = new JModelAndView("wap/activityList.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
     }
     Activity sActivity=new Activity();
     sActivity.setAc_status(1);
     List<Activity> acts = this.activityService.selectList(sActivity);
     for(Activity act : acts){
    	 Accessory ac=accessoryService.selectById(act.getAc_acc_id());
    	 act.setAc_acc(ac);
     }
     mv.addObject("acts", acts);
     return mv;
   }
   
   @RequestMapping({"/activity.htm"})
   public ModelAndView activity(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("activity.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("rt.shop_view_type"));
     
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 mv = new JModelAndView("wap/activity.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
     }
     
     Activity act = this.activityService.selectById(CommUtil.null2Long(id));
     ActivityGoodsQueryObject qo = new ActivityGoodsQueryObject(currentPage, 
       mv, "addTime", "desc");
     qo.setPageSize(Integer.valueOf(24));
     qo.addQuery("obj.ag_status", new SysMap("ag_status", Integer.valueOf(1)), "=");
     qo.addQuery("obj.act.id", new SysMap("act_id", act.getId()), "=");
     qo.addQuery("obj.act.ac_status", new SysMap("ac_status", Integer.valueOf(1)), "=");
     qo.addQuery("obj.act.ac_begin_time", 
       new SysMap("ac_begin_time", 
       new Date()), "<=");
     qo.addQuery("obj.act.ac_end_time", 
       new SysMap("ac_end_time", new Date()), ">=");
     qo.addQuery("obj.ag_goods.goods_status", new SysMap("goods_status", Integer.valueOf(0)), 
       "=");
     //时间查询没有加上
     StringBuffer sb=new StringBuffer(" where ag_status=1 and act_id='"+act.getId()+"' and ac_status=1");

     Page pList = this.activityGoodsService.selectPage(new Page<ActivityGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), sb.toString(),"addTime desc");
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("act", act);
     return mv;
   }
 }


 
 
 