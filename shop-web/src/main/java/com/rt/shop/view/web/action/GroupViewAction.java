 package com.rt.shop.view.web.action;
 
 import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.easyjf.web.tools.IPageList;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.Group;
import com.rt.shop.entity.GroupArea;
import com.rt.shop.entity.GroupClass;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.entity.GroupPriceRange;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.entity.query.GroupGoodsQueryObject;
import com.rt.shop.entity.query.GroupQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGroupAreaService;
import com.rt.shop.service.IGroupClassService;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.IGroupPriceRangeService;
import com.rt.shop.service.IGroupService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.GroupViewTools;
 
 @Controller
 public class GroupViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGroupService groupService;
 
   @Autowired
   private IGroupAreaService groupAreaService;
 
   @Autowired
   private IGroupPriceRangeService groupPriceRangeService;
 
   @Autowired
   private IGroupClassService groupClassService;
 
   @Autowired
   private IGroupGoodsService groupGoodsService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private GroupViewTools groupViewTools;
 
   @RequestMapping({"/group.htm"})
   public ModelAndView group(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String gc_id, String gpr_id, String ga_id)
   {
     ModelAndView mv = new JModelAndView("group.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
    
     String sql=" where beginTime<='"+new Date()+"' and endTime>='"+new Date()+"'";
     List<Group> groups = this.groupService.selectList(sql, null);
   
     if (groups.size() > 0) {
       GroupGoodsQueryObject ggqo = new GroupGoodsQueryObject(currentPage, 
         mv, orderBy, orderType);
       ggqo.addQuery("obj.group.id", 
         new SysMap("group_id", ((Group)groups.get(0))
         .getId()), "=");
       if ((gc_id != null) && (!gc_id.equals(""))) {
         ggqo.addQuery("obj.gg_gc.id", 
           new SysMap("gc_id", 
           CommWebUtil.null2Long(gc_id)), "=");
       }
       if ((ga_id != null) && (!ga_id.equals(""))) {
         ggqo.addQuery("obj.gg_ga.id", 
           new SysMap("ga_id", 
           CommWebUtil.null2Long(ga_id)), "=");
         mv.addObject("ga_id", ga_id);
       }
       GroupPriceRange gpr = this.groupPriceRangeService
         .selectById(CommWebUtil.null2Long(gpr_id));
       if (gpr != null) {
         ggqo.addQuery("obj.gg_price", 
           new SysMap("begin_price", 
           BigDecimal.valueOf(gpr.getGpr_begin())), ">=");
         ggqo.addQuery("obj.gg_price", 
           new SysMap("end_price", 
           BigDecimal.valueOf(gpr.getGpr_end())), "<=");
       }
       ggqo.addQuery("obj.gg_status", new SysMap("gg_status", Integer.valueOf(1)), "=");
       Page pList = this.groupGoodsService.selectPage(new Page<GroupGoods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       
       List<GroupClass> gcs = this.groupClassService.selectList("where parent_id is null", "gc_sequence asc");
        
        // "select obj from GroupClass obj where obj.parent.id is null order by obj.gc_sequence asc", 
       List<GroupPriceRange> gprs = this.groupPriceRangeService.selectList(new GroupPriceRange(),"gpr_begin asc");
       //  "select obj from GroupPriceRange obj order by obj.gpr_begin asc", 
       mv.addObject("gprs", gprs);
       mv.addObject("gcs", gcs);
       mv.addObject("group", groups.get(0));
       if ((orderBy == null) || (orderBy.equals(""))) {
         orderBy = "addTime";
       }
       if ((orderType == null) || (orderType.equals(""))) {
         orderType = "desc";
       }
       mv.addObject("order_type", CommWebUtil.null2String(orderBy) + "_" + 
         CommWebUtil.null2String(orderType));
       mv.addObject("gc_id", gc_id);
       mv.addObject("gpr_id", gpr_id);
     }
     return mv;
   }
 
   @RequestMapping({"/group_head.htm"})
   public ModelAndView group_head(HttpServletRequest request, HttpServletResponse response, String ga_id) {
     ModelAndView mv = new JModelAndView("group_head.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     
     List<GroupArea> gas = this.groupAreaService.selectList("where parent_id is null","ga_sequence asc");
      // "select obj from GroupArea obj where obj.parent.id is null order by obj.ga_sequence asc", 
     mv.addObject("gas", gas);
     if ((ga_id != null) && (!ga_id.equals("")))
       mv.addObject("ga", this.groupAreaService.selectById(
         CommWebUtil.null2Long(ga_id)).getGa_name());
     else {
       mv.addObject("ga", "全国");
     }
     return mv;
   }
 
   @RequestMapping({"/group_view.htm"})
   public ModelAndView group_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView("group_view.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     GroupGoods obj = this.groupGoodsService.selectById(
       CommWebUtil.null2Long(id));
     User user = SecurityUserHolder.getCurrentUser();
     boolean view = false;
     Group group=groupService.selectById(obj.getGroup_id());
     if ((group.getBeginTime().before(new Date())) && 
       (group.getEndTime().after(new Date()))) {
       view = true;
     }
     if ((user != null) && (user.getUserRole().indexOf("ADMIN") >= 0)) {
       view = true;
     }
     if (view) {
       mv.addObject("obj", obj);
       Map params = new HashMap();
       params.put("beginTime", new Date());
       params.put("endTime", new Date());
       params.put("status", Integer.valueOf(0));
       Group sGroup=new Group();
       sGroup.setStatus(Integer.valueOf(0));
       List<Group> groups = this.groupService.selectList(sGroup);
       //  "select obj from Group obj where obj.beginTime<=:beginTime and obj.endTime>=:endTime and obj.status=:status", 
       if (groups.size() > 0) {
         GroupGoodsQueryObject ggqo = new GroupGoodsQueryObject("1", mv, 
           "gg_recommend", "desc");
         ggqo.addQuery("obj.gg_status", new SysMap("gg_status", Integer.valueOf(1)), "=");
         ggqo.addQuery("obj.group.id", 
           new SysMap("group_id", obj.getGroup_id()), "=");
         ggqo.addQuery("obj.id", new SysMap("goods_id", obj.getId()), 
           "!=");
         ggqo.setPageSize(Integer.valueOf(4));
         Page pList = this.groupGoodsService.selectPage(new Page<GroupGoods>(Integer.valueOf(CommUtil.null2Int(0)), 12), null);
         mv.addObject("hot_ggs", pList.getRecords());
         mv.addObject("group", groups.get(0));
       }
       GoodsQueryObject gqo = new GoodsQueryObject("1", mv, "addTime", 
         "desc");
//       gqo.addQuery("obj.goods_store.id", 
//         new SysMap("store_id", obj.get
//         .getGg_goods().getGoods_store().getId()), "=");
       gqo.addQuery("obj.goods_recommend", 
         new SysMap("goods_recommend", 
         Boolean.valueOf(true)), "=");
       gqo
         .addQuery("obj.goods_status", 
         new SysMap("goods_status", Integer.valueOf(0)), "=");
       gqo.setPageSize(Integer.valueOf(2));
       mv.addObject("recommend_goods", this.goodsService.selectList(new Goods()));
       params.clear();
       params.put("gg", obj);
       List<GoodsCart> gc_list = this.goodsCartService.selectPage(new Page<GoodsCart>(0, 4), null).getRecords();
       
       List gcs = new ArrayList();
       for (GoodsCart gc : gc_list) {
         if (!gcs.contains(gc))
           gcs.add(gc);
       }
       mv.addObject("gcs", gcs);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "团购商品参数错误");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/group_list.htm"})
   public ModelAndView group_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String time) {
     ModelAndView mv = new JModelAndView("group_list.html", this.configService
       .getSysConfig(), this.userConfigService.getUserConfig(), 1, 
       request, response);
     GroupQueryObject gqo = new GroupQueryObject(currentPage, mv, "addTime", 
       "desc");
     if (time.equals("soon")) {
       gqo.addQuery("obj.beginTime", new SysMap("beginTime", new Date()), 
         ">");
     }
     if (time.equals("history")) {
       gqo.addQuery("obj.endTime", new SysMap("endTime", new Date()), "<");
     }
     Page pList = this.groupService.selectPage(new Page<Group>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("time", time);
     mv.addObject("groupViewTools", this.groupViewTools);
     return mv;
   }
 }


 
 
 