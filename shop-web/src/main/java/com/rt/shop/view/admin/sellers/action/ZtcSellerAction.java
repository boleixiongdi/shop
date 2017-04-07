 package com.rt.shop.view.admin.sellers.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class ZtcSellerAction
 {
	 @Autowired
	   private IAccessoryService  accessoryService;
	 @Autowired
	   private IStoreService storeService;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IUserService userService;
 
   @SecurityMapping(display = false, rsequence = 0, title="直通车申请", value="/seller/ztc_apply.htm*", rtype="seller", rname="竞价直通车", rcode="ztc_seller", rgroup="促销管理")
   @RequestMapping({"/seller/ztc_apply.htm"})
   public ModelAndView ztc_apply(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/ztc_apply.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getZtc_status()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启直通车");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     }
     String ztc_session = CommUtil.randomString(32);
     mv.addObject("ztc_session", ztc_session);
     request.getSession(false).setAttribute("ztc_session", ztc_session);
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
     mv.addObject("user", user);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="直通车商品加载", value="/seller/ztc_goods.htm*", rtype="seller", rname="竞价直通车", rcode="ztc_seller", rgroup="促销管理")
   @RequestMapping({"/seller/ztc_goods.htm"})
   public void ztc_goods(HttpServletRequest request, HttpServletResponse response, String goods_name) {
     Map params = new HashMap();
     params.put("goods_name", "%" + goods_name.trim() + "%");
     params.put("goods_status", Integer.valueOf(0));
     params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
     params.put("ztc_status", Integer.valueOf(0));
     List<Goods> goods_list = this.goodsService.selectList(null);//TODO
     //  "select obj from Goods obj where obj.goods_name like :goods_name and obj.goods_store.user.id=:user_id and obj.ztc_status=:ztc_status and obj.goods_status=:goods_status  order by obj.addTime desc", 
     List maps = new ArrayList();
     for (Goods goods : goods_list) {
       Map map = new HashMap();
       map.put("goods_name", goods.getGoods_name());
       map.put("goods_id", goods.getId());
       maps.add(map);
     }
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(Json.toJson(maps, JsonFormat.compact()));
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="直通车申请保存", value="/seller/ztc_apply_save.htm*", rtype="seller", rname="竞价直通车", rcode="ztc_seller", rgroup="促销管理")
   @RequestMapping({"/seller/ztc_apply_save.htm"})
   public ModelAndView ztc_apply_save(HttpServletRequest request, HttpServletResponse response, String goods_id, String ztc_price, String ztc_begin_time, String ztc_gold, String ztc_session) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getZtc_status()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启直通车");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
       String ztc_session1 = CommUtil.null2String(request
         .getSession(false).getAttribute("ztc_session"));
       if ((!ztc_session1.equals("")) && 
         (ztc_session1.equals(CommUtil.null2String(ztc_session)))) {
         request.getSession(false).removeAttribute("ztc_session");
         Goods goods = this.goodsService.selectById(
           CommUtil.null2Long(goods_id));
         goods.setZtc_status(1);
         goods.setZtc_pay_status(1);
         goods.setZtc_begin_time(CommUtil.formatDate(ztc_begin_time));
         goods.setZtc_gold(CommUtil.null2Int(ztc_gold));
         goods.setZtc_price(CommUtil.null2Int(ztc_price));
         goods.setZtc_apply_time(new Date());
         this.goodsService.updateSelectiveById(goods);
         mv.addObject("op_title", "直通车申请成功,等待审核");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/ztc_list.htm");
       } else {
         mv = new JModelAndView("error.html", this.configService
           .getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "不允许重复提交申请");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/ztc_apply.htm");
       }
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="直通车申请列表", value="/seller/ztc_apply_list.htm*", rtype="seller", rname="竞价直通车", rcode="ztc_seller", rgroup="促销管理")
   @RequestMapping({"/seller/ztc_apply_list.htm"})
   public ModelAndView ztc_apply_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String goods_name) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/ztc_apply_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getZtc_status()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启直通车");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
    	 String sql="where ztc_status =3 and ztc_begin_time <="+CommUtil.formatTime("yyyyMMdd", new Date())+" and ztc_gold>0  order by ztc_dredge_price desc";
       StringBuffer sb=new StringBuffer(" where 1=1  and goods_store_id="+SecurityUserHolder.getCurrentUser().getStore_id());
       sb.append(" and ztc_status="+Integer.valueOf(1));
       if (!CommUtil.null2String(goods_name).equals("")) {
    	   sb.append(" and goods_name like "+"%" + 
           goods_name.trim() + "%");
       }
       Page<Goods> pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12),sb.toString(), "ztc_begin_time desc");
       for(Goods goods:pList.getRecords()){
			Accessory ac=accessoryService.selectById(goods.getGoods_main_photo_id());
			goods.setGoods_main_photo(ac);
		}
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       mv.addObject("goods_name", goods_name);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="直通车商品列表", value="/seller/ztc_list.htm*", rtype="seller", rname="竞价直通车", rcode="ztc_seller", rgroup="促销管理")
   @RequestMapping({"/seller/ztc_list.htm"})
   public ModelAndView ztc_list(HttpServletRequest request, HttpServletResponse response, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/ztc_list.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getZtc_status()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启直通车");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
       GoodsQueryObject qo = new GoodsQueryObject(currentPage, mv, 
         "ztc_apply_time", "desc");
       qo.addQuery("obj.goods_store.user.id", 
         new SysMap("user_id", 
         SecurityUserHolder.getCurrentUser().getId()), "=");
       qo.addQuery("obj.ztc_status", new SysMap("ztc_status", Integer.valueOf(2)), ">=");
       Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="直通车申请查看", value="/seller/ztc_apply_view.htm*", rtype="seller", rname="竞价直通车", rcode="ztc_seller", rgroup="促销管理")
   @RequestMapping({"/seller/ztc_apply_view.htm"})
   public ModelAndView ztc_apply_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/ztc_apply_view.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     if (!this.configService.getSysConfig().getZtc_status()) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启直通车");
       mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
     } else {
       Goods obj = this.goodsService.selectById(CommUtil.null2Long(id));
    //   Store store=storeService.selectById(obj.getGoods_store_id());
       User sUser=new User();
       sUser.setStore_id(obj.getGoods_store_id());
       if (userService.selectOne(sUser).getId().equals(
         SecurityUserHolder.getCurrentUser().getId())) {
         mv.addObject("obj", obj);
       } else {
         mv = new JModelAndView("error.html", this.configService
           .getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "参数错误，不存在该直通车信息");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/seller/ztc_list.htm");
       }
     }
     return mv;
   }
 }


 
 
 