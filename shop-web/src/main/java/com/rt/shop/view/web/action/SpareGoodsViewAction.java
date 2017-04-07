 package com.rt.shop.view.web.action;
 
 import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.Navigation;
import com.rt.shop.entity.SpareGoods;
import com.rt.shop.entity.SpareGoodsClass;
import com.rt.shop.entity.SpareGoodsFloor;
import com.rt.shop.entity.query.SpareGoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.INavigationService;
import com.rt.shop.service.ISpareGoodsClassService;
import com.rt.shop.service.ISpareGoodsFloorService;
import com.rt.shop.service.ISpareGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.SpareGoodsViewTools;
 
 @Controller
 public class SpareGoodsViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private ISpareGoodsClassService sparegoodsclassService;
 
   @Autowired
   private ISpareGoodsFloorService sparegoodsfloorService;
 
   @Autowired
   private ISpareGoodsService sparegoodsService;
 
   @Autowired
   private IAreaService areaService;
 
   @Autowired
   private SpareGoodsViewTools SpareGoodsTools;
 
   @Autowired
   private INavigationService navService;
 
   @RequestMapping({"/sparegoods_head.htm"})
   public ModelAndView sparegoods_head(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("sparegoods_head.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 1, request, response);
     return mv;
   }
 
   @RequestMapping({"/sparegoods_nav.htm"})
   public ModelAndView sparegoods_nav(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("sparegoods_nav.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 1, request, response);
    
     List sgcs = this.sparegoodsclassService.selectList("where parent_id is null", "sequence asc");
      // "select obj from SpareGoodsClass obj where obj.parent.id is null order by sequence asc", 
     mv.addObject("sgcs", sgcs);
     if ((SecurityUserHolder.getCurrentUser() != null) && 
       (!SecurityUserHolder.getCurrentUser().equals("")))
     {
    	 SpareGoods sSpareGoods=new SpareGoods();
    	 sSpareGoods.setStatus(Integer.valueOf(0));
    	 sSpareGoods.setDown(Integer.valueOf(0));
    	 sSpareGoods.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       
       List<SpareGoods> sps = this.sparegoodsService.selectList(sSpareGoods);
         //"select obj from SpareGoods obj where obj.status=:status and obj.down=:down and obj.user.id=:uid", 
 
      
       SpareGoods ssSpareGoods=new SpareGoods();
  	 	ssSpareGoods.setStatus(Integer.valueOf(-1));
  	 	ssSpareGoods.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       List<SpareGoods> drops = this.sparegoodsService.selectList(ssSpareGoods);
       //  "select obj from SpareGoods obj where obj.status=:status and obj.user.id=:uid", 
 
       
       //  "select obj from SpareGoods obj where obj.down=:down and obj.user.id=:uid", 
       SpareGoods sssSpareGoods=new SpareGoods();
  	 sssSpareGoods.setDown(Integer.valueOf(-1));
  	 sssSpareGoods.setUser_id(SecurityUserHolder.getCurrentUser().getId());
     
     List<SpareGoods> down = this.sparegoodsService.selectList(sssSpareGoods);
       mv.addObject("selling", Integer.valueOf(sps.size()));
       mv.addObject("drops", Integer.valueOf(drops.size()));
       mv.addObject("down", Integer.valueOf(down.size()));
     }
     
     Navigation sNavigation=new Navigation();
     sNavigation.setType("sparegoods");
     sNavigation.setDisplay(Boolean.valueOf(true));
     List navs = this.navService.selectList(sNavigation, "sequence asc");
       //"select obj from Navigation obj where obj.type=:type and obj.display=:display order by sequence asc", 
     mv.addObject("navs", navs);
     return mv;
   }
 
   @RequestMapping({"/sparegoods_nav2.htm"})
   public ModelAndView sparegoods_nav2(HttpServletRequest request, HttpServletResponse response, String currentPage)
   {
     ModelAndView mv = new JModelAndView("sparegoods_nav2.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 1, request, response);
   
     
     List sgcs = this.sparegoodsclassService.selectList("where parent_id is null","sequence asc");
      // "select obj from SpareGoodsClass obj where obj.parent.id is null order by sequence asc", 
     mv.addObject("sgcs", sgcs);
     if ((SecurityUserHolder.getCurrentUser() != null) && 
       (!SecurityUserHolder.getCurrentUser().equals("")))
     {
       
       SpareGoods sSpareGoods=new SpareGoods();
  	 sSpareGoods.setStatus(Integer.valueOf(0));
  	 sSpareGoods.setDown(Integer.valueOf(0));
  	 sSpareGoods.setUser_id(SecurityUserHolder.getCurrentUser().getId());
       List<SpareGoods> sps = this.sparegoodsService.selectList(sSpareGoods);
        
       //  "select obj from SpareGoods obj where obj.status=:status and obj.down=:down and obj.user.id=:uid", 
       
       SpareGoods ssSpareGoods=new SpareGoods();
 	 	ssSpareGoods.setStatus(Integer.valueOf(-1));
 	 	ssSpareGoods.setUser_id(SecurityUserHolder.getCurrentUser().getId());
      List<SpareGoods> drops = this.sparegoodsService.selectList(ssSpareGoods);
      
      
      SpareGoods sssSpareGoods=new SpareGoods();
   	 sssSpareGoods.setDown(Integer.valueOf(-1));
   	 sssSpareGoods.setUser_id(SecurityUserHolder.getCurrentUser().getId());
      
      List<SpareGoods> down = this.sparegoodsService.selectList(sssSpareGoods);
      
       mv.addObject("selling", Integer.valueOf(sps.size()));
       mv.addObject("drops", Integer.valueOf(drops.size()));
       mv.addObject("down", Integer.valueOf(down.size()));
     }
    
     Navigation sNavigation=new Navigation();
     sNavigation.setType("sparegoods");
     sNavigation.setDisplay(Boolean.valueOf(true));
     List navs = this.navService.selectList(sNavigation, "sequence asc");
     mv.addObject("navs", navs);
     return mv;
   }
 
   @RequestMapping({"/sparegoods.htm"})
   public ModelAndView sparegoods(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView("sparegoods.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
   
     SpareGoodsFloor sSpareGoodsFloor=new SpareGoodsFloor();
     sSpareGoodsFloor.setDisplay(Boolean.valueOf(true));
     List<SpareGoodsFloor> floors = this.sparegoodsfloorService.selectList(sSpareGoodsFloor, "sequence asc");
 
     List sgcs = this.sparegoodsclassService.selectList("where parent_id is null", "sequence asc");

     mv.addObject("sgcs", sgcs);
     mv.addObject("floors", floors);
     mv.addObject("SpareGoodsTools", this.SpareGoodsTools);
     return mv;
   }
 
   @RequestMapping({"/sparegoods_detail.htm"})
   public ModelAndView sparegoods_detail(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView("sparegoods_detail.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 1, request, response);
     SpareGoods obj = this.sparegoodsService.selectById(
       CommWebUtil.null2Long(id));
     if (obj.getStatus() == 0) {
       mv.addObject("obj", obj);
     }
     if (obj.getStatus() == -1) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("url", CommWebUtil.getURL(request) + "/sparegoods.htm");
       mv.addObject("op_title", "该商品已下架!");
     }
     if (obj.getDown() == -1) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("url", CommWebUtil.getURL(request) + "/sparegoods.htm");
       mv.addObject("op_title", "该商品因违规已下架!");
     }
 
     return mv;
   }
 
   @RequestMapping({"/sparegoods_search.htm"})
   public ModelAndView sparegoods_search(HttpServletRequest request, HttpServletResponse response, String cid, String orderBy, String orderType, String currentPage, String price_begin, String price_end, String keyword, String area_id)
   {
     ModelAndView mv = new JModelAndView("sparegoods_search.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 1, request, response);
     if ((orderType != null) && (!orderType.equals(""))) {
       if (orderType.equals("asc"))
         orderType = "desc";
       else
         orderType = "asc";
     }
     else {
       orderType = "desc";
     }
     if ((orderBy != null) && (!orderBy.equals(""))) {
       if (orderBy.equals("addTime"))
         orderType = "desc";
     }
     else {
       orderBy = "addTime";
     }
     SpareGoodsQueryObject qo = new SpareGoodsQueryObject(currentPage, mv, 
       orderBy, orderType);
     qo.addQuery("obj.status", new SysMap("status", Integer.valueOf(0)), "=");
     qo.addQuery("obj.down", new SysMap("down", Integer.valueOf(0)), "=");
     if ((cid != null) && (!cid.equals(""))) {
       SpareGoodsClass sgc = this.sparegoodsclassService
         .selectById(CommWebUtil.null2Long(cid));
       Set ids = genericIds(sgc);
       Map map = new HashMap();
       map.put("ids", ids);
       qo.addQuery("obj.spareGoodsClass.id in (:ids)", map);
       mv.addObject("cid", cid);
       mv.addObject("sgc", sgc);
     }
     if ((orderBy != null) && (!orderBy.equals(""))) {
       if (orderBy.equals("recommend")) {
         qo.addQuery("obj.recommend", new SysMap("obj_recommend", Boolean.valueOf(true)), 
           "=");
       }
       if ((price_begin != null) && (!price_begin.equals(""))) {
         qo.addQuery("obj.goods_price", 
           new SysMap("goods_price", 
           Integer.valueOf(CommWebUtil.null2Int(price_begin))), ">=");
       }
       if ((price_end != null) && (!price_end.equals(""))) {
         qo.addQuery("obj.goods_price", 
           new SysMap("goods_end", 
           Integer.valueOf(CommWebUtil.null2Int(price_end))), "<=");
       }
     }
     if ((keyword != null) && (!keyword.equals(""))) {
       qo.addQuery("obj.title", 
         new SysMap("obj_title", "%" + 
         keyword.trim() + "%"), "like");
     }
     if ((area_id != null) && (!area_id.equals(""))) {
       qo.addQuery("obj.area.parent.id", 
         new SysMap("obj_area_id", 
         CommWebUtil.null2Long(area_id)), "=");
       Area area = this.areaService
         .selectById(CommWebUtil.null2Long(area_id));
       mv.addObject("area", area);
     }
     Page pList = this.sparegoodsService.selectPage(new Page<SpareGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
    
     List<Area> citys = this.areaService.selectList("where parent_id is null",null);
      // "select obj from Area obj where obj.parent.id is null", null, 
     mv.addObject("citys", citys);
     mv.addObject("area_id", area_id);
     mv.addObject("keyword", keyword);
     mv.addObject("price_begin", price_begin);
     mv.addObject("price_end", price_end);
     mv.addObject("allCount", Integer.valueOf(pList.getTotal()));
     mv.addObject("SpareGoodsTools", this.SpareGoodsTools);
     return mv;
   }
 
//   private Set<Long> genericIds(SpareGoodsClass gc) {
//     Set ids = new HashSet();
//     ids.add(gc.getId());
//     for (SpareGoodsClass child : gc.getChilds()) {
//       Set<Long> cids = genericIds(child);
//       for (Long cid : cids) {
//         ids.add(cid);
//       }
//       ids.add(child.getId());
//     }
//     return ids;
//   }
   private Set<Long> genericIds(SpareGoodsClass gc) {
	   if(gc!=null){
		   Set ids = new HashSet();
		     ids.add(gc.getId());
		     SpareGoodsClass sGoodsClass=new SpareGoodsClass();
		     sGoodsClass.setParent_id(gc.getId());
		     List<SpareGoodsClass> childs=sparegoodsclassService.selectList(sGoodsClass);
		     if(childs!=null && childs.size()>0){
		    	 for (SpareGoodsClass child : childs) {
				       Set<Long> cids = genericIds(child);
				       for (Long cid : cids) {
				         ids.add(cid);
				       }
				       ids.add(child.getId());
				     }
		     }
		     
		     return ids;
	   }
	return null;
	}
 }


 
 
 