  package com.rt.shop.view.web.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.IntegralGoods;
import com.rt.shop.entity.IntegralGoodsCart;
import com.rt.shop.entity.IntegralGoodsOrder;
import com.rt.shop.entity.IntegralLog;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.IntegralGoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.pay.tools.PayTools;
import com.rt.shop.service.IAddressService;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IIntegralGoodsCartService;
import com.rt.shop.service.IIntegralGoodsOrderService;
import com.rt.shop.service.IIntegralGoodsService;
import com.rt.shop.service.IIntegralLogService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.PaymentTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class IntegralViewAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IIntegralGoodsService integralGoodsService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IAddressService addressService;
 
   @Autowired
   private IIntegralGoodsOrderService integralGoodsOrderService;
 
   @Autowired
   private IIntegralGoodsCartService integralGoodsCartService;
 
   @Autowired
   private IPaymentService paymentService;
 
   @Autowired
   private IIntegralLogService integralLogService;
 
   @Autowired
   private IAreaService areaService;
 
   @Autowired
   private PaymentTools paymentTools;
 
   @Autowired
   private PayTools payTools;
 
   @RequestMapping({"/integral.htm"})
   public ModelAndView integral(HttpServletRequest request, HttpServletResponse response, String begin, String end)
   {
     ModelAndView mv = new JModelAndView("integral.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (this.configService.getSysConfig().getIntegralStore()) {
       List recommend_igs = new ArrayList();
       if ((begin != null) && (!begin.equals("")) && (end != null) && 
         (!end.equals(""))) {
         if (end.equals("0")) {
           IntegralGoods sIntegralGoods=new IntegralGoods();
           String sql1="where ig_recommend=1 and ig_show=1 and  ig_goods_integral>='"+Integer.valueOf(CommWebUtil.null2Int(begin))+"' order by ig_sequence asc";
           recommend_igs = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,10), sql1, null).getRecords();
           
         } else {
           String sql1="where ig_recommend=1 and ig_show=1 and  ig_goods_integral>='"+Integer.valueOf(CommWebUtil.null2Int(begin))+"' and ig_goods_integral<'"+Integer.valueOf(CommWebUtil.null2Int(end))+"' order by ig_sequence asc";
           recommend_igs = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,10), sql1, null).getRecords();
         }
       }
       else{
    	   String sql1="where ig_recommend=1 and ig_show=1  order by ig_sequence asc";
           recommend_igs = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,10), sql1, null).getRecords();
       }
       mv.addObject("recommend_igs", recommend_igs);

       List new_igs = new ArrayList();
       if ((begin != null) && (!begin.equals("")) && (end != null) && 
         (!end.equals(""))) {
         if (end.equals("0")) {
           String sql1="where  ig_show=1 and  ig_goods_integral>='"+Integer.valueOf(CommWebUtil.null2Int(begin))+"' order by ig_sequence asc";
           new_igs = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,15), sql1, null).getRecords();
         } else {
        	  String sql1="where and ig_show=1 and  ig_goods_integral>='"+Integer.valueOf(CommWebUtil.null2Int(begin))+"' and ig_goods_integral<'"+Integer.valueOf(CommWebUtil.null2Int(end))+"' order by ig_sequence asc";
        	  new_igs = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,15), sql1, null).getRecords();
         }
       }
       else{ 
    	   String sql1="where  ig_show=1  order by ig_sequence asc";
    	   new_igs = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,15), sql1, null).getRecords();
       }
 
       mv.addObject("new_igs", new_igs);
       List exchange_igs = this.integralGoodsService.selectList(new IntegralGoods(), "ig_exchange_count desc");
       mv.addObject("exchange_igs", exchange_igs);
       if (SecurityUserHolder.getCurrentUser() != null) {
         mv.addObject("user", 
           this.userService.selectById(
           SecurityUserHolder.getCurrentUser().getId()));
       }
       mv.addObject("integral_cart", request.getSession(false)
         .getAttribute("integral_goods_cart"));
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启积分商城");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/integral_view.htm"})
   public ModelAndView integral_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView("integral_view.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (this.configService.getSysConfig().getIntegralStore()) {
       IntegralGoods obj = this.integralGoodsService.selectById(
         CommWebUtil.null2Long(id));
       obj.setIg_click_count(obj.getIg_click_count() + 1);
       this.integralGoodsService.updateSelectiveById(obj);
       List gcs = this.integralGoodsCartService.selectList(new IntegralGoodsCart(), "addTime desc");
       mv.addObject("gcs", gcs);
       mv.addObject("obj", obj);
       mv.addObject("view_url", CommWebUtil.getURL(request) + 
         "/integral_view" + id + ".htm");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启积分商城");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/integral_list.htm"})
   public ModelAndView integral_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String rang_begin, String rang_end)
   {
     ModelAndView mv = new JModelAndView("integral_list.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if ((orderType != null) && (!orderType.equals("")))
       orderBy = "ig_sequence";
     else {
       orderBy = "addTime";
     }
     if (this.configService.getSysConfig().getIntegralStore()) {
       IntegralGoodsQueryObject qo = new IntegralGoodsQueryObject(
         currentPage, mv, orderBy, orderType);
       StringBuffer sb=new StringBuffer("where 1=1");
       if ((rang_begin != null) && (!rang_begin.equals(""))) {
         mv.addObject("rang_begin", rang_begin);
         sb.append(" and ig_goods_integral>="+Integer.valueOf(CommWebUtil.null2Int(rang_begin)));
//         qo.addQuery("obj.ig_goods_integral", 
//           new SysMap("rang_begin", 
//           Integer.valueOf(CommWebUtil.null2Int(rang_begin))), ">=");
       }
       if ((rang_end != null) && (!rang_end.equals("")) && 
         (!rang_end.equals("0"))) {
         mv.addObject("rang_end", rang_end);
         sb.append(" and ig_goods_integral<"+Integer.valueOf(CommWebUtil.null2Int(rang_end)));
//         qo.addQuery("obj.ig_goods_integral", 
//           new SysMap("rang_end", 
//           Integer.valueOf(CommWebUtil.null2Int(rang_end))), "<");
       }
       qo.setPageSize(Integer.valueOf(20));
       Page pList = this.integralGoodsService.selectPage(new Page<IntegralGoods>(0,20), sb.toString(), null);
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       List exchange_igs = this.integralGoodsService.selectList(new IntegralGoods(), "ig_exchange_count desc");
       mv.addObject("exchange_igs", exchange_igs);
       if (SecurityUserHolder.getCurrentUser() != null) {
         mv.addObject("user", 
           this.userService.selectById(
           SecurityUserHolder.getCurrentUser().getId()));
       }
       mv.addObject("integral_cart", request.getSession(false)
         .getAttribute("integral_goods_cart"));
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启积分商城");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="积分兑换第一步", value="/integral_exchange1.htm*", rtype="buyer", rname="积分兑换", rcode="integral_exchange", rgroup="积分兑换")
   @RequestMapping({"/integral_exchange1.htm"})
   public ModelAndView integral_exchange1(HttpServletRequest request, HttpServletResponse response, String id, String exchange_count) {
     ModelAndView mv = new JModelAndView("integral_exchange1.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (this.configService.getSysConfig().getIntegralStore()) {
       IntegralGoods obj = this.integralGoodsService.selectById(
         CommWebUtil.null2Long(id));
       int exchange_status = 0;
       if (obj != null) {
         if ((exchange_count == null) || (exchange_count.equals(""))) {
           exchange_count = "1";
         }
         if (obj.getIg_goods_count() < CommWebUtil.null2Int(exchange_count)) {
           exchange_status = -1;
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "库存数量不足，重新选择兑换数量");
           mv.addObject("url", CommWebUtil.getURL(request) + 
             "/integral_view_" + id + ".htm");
         }
         if (obj.getIg_limit_type())
         {
           if (obj.getIg_limit_count() < 
             CommWebUtil.null2Int(exchange_count)) {
             exchange_status = -2;
             mv = new JModelAndView("error.html", 
               this.configService.getSysConfig(), 
               this.userConfigService.getUserConfig(), 1, request, 
               response);
             mv.addObject("op_title", "限制最多兑换" + obj.getIg_limit_count() + 
               "，重新选择兑换数量");
             mv.addObject("url", CommWebUtil.getURL(request) + 
               "/integral_view_" + id + ".htm");
           }
         }
         int cart_total_integral = obj.getIg_goods_integral() * 
           CommWebUtil.null2Int(exchange_count);
         User user = this.userService.selectById(
           SecurityUserHolder.getCurrentUser().getId());
         if (user.getIntegral() < cart_total_integral) {
           exchange_status = -3;
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("op_title", "您的积分不足");
           mv.addObject("url", CommWebUtil.getURL(request) + 
             "/integral_view_" + id + ".htm");
         }
         if ((obj.getIg_begin_time() != null) && 
           (obj.getIg_end_time() != null) && (
           (obj.getIg_begin_time().after(new Date())) || 
           (obj
           .getIg_end_time().before(new Date())))) {
           exchange_status = -4;
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, 
             request, response);
           mv.addObject("op_title", "兑换已经过期");
           mv.addObject("url", CommWebUtil.getURL(request) + 
             "/integral_view_" + id + ".htm");
         }
 
       }
 
       if (exchange_status == 0) {
         List<IntegralGoodsCart> integral_goods_cart = (List)request
           .getSession(false).getAttribute("integral_goods_cart");
         if (integral_goods_cart == null) {
           integral_goods_cart = new ArrayList();
         }
         boolean add = obj != null;
         for (IntegralGoodsCart igc : integral_goods_cart) {
           if (igc.getGoods().getId().toString().equals(id)) {
             add = false;
             break;
           }
         }
         if (add) {
           IntegralGoodsCart gc = new IntegralGoodsCart();
           gc.setAddTime(new Date());
           gc.setCount(CommWebUtil.null2Int(exchange_count));
           gc.setGoods(obj);
           gc.setTrans_fee(obj.getIg_transfee());
           gc.setIntegral(CommWebUtil.null2Int(exchange_count) * 
             obj.getIg_goods_integral());
           integral_goods_cart.add(gc);
         }
         request.getSession(false).setAttribute("integral_goods_cart", 
           integral_goods_cart);
         int total_integral = 0;
         for (IntegralGoodsCart igc : integral_goods_cart) {
           total_integral += igc.getIntegral();
         }
         mv.addObject("total_integral", Integer.valueOf(total_integral));
         mv.addObject("integral_cart", integral_goods_cart);
         mv.addObject("user", 
           this.userService.selectById(
           SecurityUserHolder.getCurrentUser().getId()));
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启积分商城");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/integral_cart_remove.htm"})
   public void integral_cart_remove(HttpServletRequest request, HttpServletResponse response, String id) {
     List<IntegralGoodsCart> igcs = (List)request
       .getSession(false).getAttribute("integral_goods_cart");
     for (IntegralGoodsCart igc : igcs) {
       if (igc.getGoods().getId().toString().equals(id)) {
         igcs.remove(igc);
         break;
       }
     }
     int total_integral = 0;
     for (IntegralGoodsCart igc : igcs) {
       total_integral += igc.getIntegral();
     }
     request.getSession(false).setAttribute("integral_goods_cart", igcs);
     Object map = new HashMap();
     ((Map)map).put("status", Integer.valueOf(100));
     ((Map)map).put("total_integral", Integer.valueOf(total_integral));
     ((Map)map).put("size", Integer.valueOf(igcs.size()));
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(Json.toJson(map, JsonFormat.compact()));
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @RequestMapping({"/integral_adjust_count.htm"})
   public void integral_adjust_count(HttpServletRequest request, HttpServletResponse response, String goods_id, String count) {
     List<IntegralGoodsCart> igcs = (List)request
       .getSession(false).getAttribute("integral_goods_cart");
     IntegralGoodsCart obj = null;
     int num = CommWebUtil.null2Int(count);
     IntegralGoods ig;
     for (IntegralGoodsCart igc : igcs) {
       if (igc.getGoods().getId().toString().equals(goods_id)) {
         ig = igc.getGoods();
         if (num > ig.getIg_goods_count()) {
           num = ig.getIg_goods_count();
         }
         if ((ig.getIg_limit_type()) && (ig.getIg_limit_count() < num)) {
           num = ig.getIg_limit_count();
         }
         igc.setCount(num);
         igc.setIntegral(igc.getGoods().getIg_goods_integral() * 
           CommWebUtil.null2Int(Integer.valueOf(num)));
         obj = igc;
         break;
       }
     }
 
     int total_integral = 0;
     for (IntegralGoodsCart igc : igcs) {
       total_integral += igc.getIntegral();
     }
     request.getSession(false).setAttribute("integral_goods_cart", igcs);
     Object map = new HashMap();
     ((Map)map).put("total_integral", Integer.valueOf(total_integral));
     ((Map)map).put("integral", Integer.valueOf(obj.getIntegral()));
     ((Map)map).put("count", Integer.valueOf(num));
     response.setContentType("text/plain");
     response.setHeader("Cache-Control", "no-cache");
     response.setCharacterEncoding("UTF-8");
     try
     {
       PrintWriter writer = response.getWriter();
       writer.print(Json.toJson(map, JsonFormat.compact()));
     }
     catch (IOException e) {
       e.printStackTrace();
     }
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="积分兑换第二步", value="/integral_exchange2.htm*", rtype="buyer", rname="积分兑换", rcode="integral_exchange", rgroup="积分兑换")
   @RequestMapping({"/integral_exchange2.htm"})
   public ModelAndView integral_exchange2(HttpServletRequest request, HttpServletResponse response, String id, String exchange_count) {
     ModelAndView mv = new JModelAndView("integral_exchange2.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (this.configService.getSysConfig().getIntegralStore()) {
       List<IntegralGoodsCart> igcs = (List)request
         .getSession(false).getAttribute("integral_goods_cart");
       if (igcs != null) {
         Address sAddress=new Address();
         sAddress.setUser_id(SecurityUserHolder.getCurrentUser()
           .getId());
         List addrs = this.addressService.selectList(sAddress);
         mv.addObject("addrs", addrs);
         mv.addObject("igcs", 
           igcs == null ? new ArrayList() : 
           igcs);
         int total_integral = 0;
         double trans_fee = 0.0D;
         for (IntegralGoodsCart igc : igcs) {
           total_integral += igc.getIntegral();
           trans_fee = CommWebUtil.null2Double(igc.getTrans_fee()) + 
             trans_fee;
         }
         mv.addObject("trans_fee", Double.valueOf(trans_fee));
         mv.addObject("total_integral", Integer.valueOf(total_integral));
         String integral_order_session = CommWebUtil.randomString(32);
         mv.addObject("integral_order_session", integral_order_session);
         request.getSession(false).setAttribute(
           "integral_order_session", integral_order_session);
         Area sArea=new Area();
         sArea.setParent_id(0L);
         List areas = this.areaService.selectList(sArea);
         mv.addObject("areas", areas);
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "兑换购物车为空");
         mv.addObject("url", CommWebUtil.getURL(request) + "/integral.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启积分商城");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="积分兑换第三步", value="/integral_exchange3.htm*", rtype="buyer", rname="积分兑换", rcode="integral_exchange", rgroup="积分兑换")
   @RequestMapping({"/integral_exchange3.htm"})
   public ModelAndView integral_exchange3(HttpServletRequest request, HttpServletResponse response, String addr_id, String igo_msg, String integral_order_session, String area_id, String trueName, String area_info, String zip, String telephone, String mobile)
   {
     ModelAndView mv = new JModelAndView("integral_exchange3.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (this.configService.getSysConfig().getIntegralStore()) {
       List<IntegralGoodsCart> igcs = (List)request
         .getSession(false).getAttribute("integral_goods_cart");
       String integral_order_session1 = CommWebUtil.null2String(request
         .getSession(false).getAttribute("integral_order_session"));
       if (integral_order_session1.equals("")) {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "表单已经过期");
         mv.addObject("url", CommWebUtil.getURL(request) + "/integral.htm");
       }
       else if (integral_order_session1.equals(integral_order_session
         .trim())) {
         if (igcs != null) {
           int total_integral = 0;
           double trans_fee = 0.0D;
           for (IntegralGoodsCart igc : igcs) {
             total_integral += igc.getIntegral();
             trans_fee = 
               CommWebUtil.null2Double(igc.getTrans_fee()) + 
               trans_fee;
           }
           IntegralGoodsOrder order = new IntegralGoodsOrder();
           Address addr = null;
           if (addr_id.equals("new")) {
             addr = new Address();
             addr.setAddTime(new Date());
             Area area = this.areaService.selectById(
               CommWebUtil.null2Long(area_id));
             addr.setArea_info(area_info);
             addr.setMobile(mobile);
             addr.setTelephone(telephone);
             addr.setTrueName(trueName);
             addr.setZip(zip);
             addr.setArea(area);
             addr.setAd_user(SecurityUserHolder.getCurrentUser());
             this.addressService.insertSelective(addr);
           } else {
             addr = this.addressService.selectById(
               CommWebUtil.null2Long(addr_id));
           }
           order.setAddTime(new Date());
           order.setIgo_addr(addr);
           order.setIgo_gcs(igcs);
           order.setIgo_msg(igo_msg);
           User user = this.userService
             .selectById(SecurityUserHolder.getCurrentUser()
             .getId());
           order.setIgo_order_sn("igo" + 
             CommUtil.formatTime("yyyyMMddHHmmss", 
             new Date()) + user.getId());
           order.setIgo_user(user);
           order.setIgo_trans_fee(BigDecimal.valueOf(trans_fee));
           order.setIgo_total_integral(total_integral);
           for (IntegralGoodsCart igc : igcs) {
             igc.setOrder(order);
           }
           if (trans_fee == 0.0D) {
             order.setIgo_status(20);
             order.setIgo_pay_time(new Date());
             order.setIgo_payment("no_fee");
             this.integralGoodsOrderService.insertSelective(order);
             for (IntegralGoodsCart igc : order.getIgo_gcs()) {
               IntegralGoods goods = igc.getGoods();
               goods.setIg_goods_count(goods
                 .getIg_goods_count() - igc.getCount());
               goods.setIg_exchange_count(goods
                 .getIg_exchange_count() + 
                 igc.getCount());
               this.integralGoodsService.updateSelectiveById(goods);
             }
             request.getSession(false).removeAttribute(
               "integral_goods_cart");
             mv.addObject("url", CommWebUtil.getURL(request) + 
               "/integral.htm");
             mv.addObject("order", order);
           } else {
             order.setIgo_status(0);
             this.integralGoodsOrderService.insertSelective(order);
             mv = new JModelAndView("integral_exchange4.html", 
               this.configService.getSysConfig(), 
               this.userConfigService.getUserConfig(), 1, 
               request, response);
             mv.addObject("obj", order);
             mv.addObject("paymentTools", this.paymentTools);
           }
 
           user.setIntegral(user.getIntegral() - 
             order.getIgo_total_integral());
           this.userService.updateSelectiveById(user);
 
           IntegralLog log = new IntegralLog();
           log.setAddTime(new Date());
           log.setContent("兑换商品消耗积分");
           log.setIntegral(-order.getIgo_total_integral());
           log.setIntegral_user(user);
           log.setType("integral_order");
           this.integralLogService.insertSelective(log);
           request.getSession(false).removeAttribute(
             "integral_goods_cart");
         } else {
           mv = new JModelAndView("error.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, 
             request, response);
           mv.addObject("op_title", "兑换购物车为空");
           mv.addObject("url", CommWebUtil.getURL(request) + 
             "/integral.htm");
         }
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "参数错误，订单提交失败");
         mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
       }
     }
     else
     {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "系统未开启积分商城");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="积分订单支付", value="/integral_order_pay.htm*", rtype="buyer", rname="积分兑换", rcode="integral_exchange", rgroup="积分兑换")
   @RequestMapping({"/integral_order_pay.htm"})
   public ModelAndView integral_order_pay(HttpServletRequest request, HttpServletResponse response, String payType, String integral_order_id) {
     ModelAndView mv = null;
     IntegralGoodsOrder order = this.integralGoodsOrderService
       .selectById(CommWebUtil.null2Long(integral_order_id));
     if (order.getIgo_status() == 0) {
       if (CommWebUtil.null2String(payType).equals("")) {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "支付方式错误！");
         mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
       }
       else {
         order.setIgo_payment(payType);
         this.integralGoodsOrderService.updateSelectiveById(order);
         if (payType.equals("balance")) {
           mv = new JModelAndView("integral_balance_pay.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
         } else if (payType.equals("outline")) {
           mv = new JModelAndView("integral_outline_pay.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           String integral_pay_session = CommWebUtil.randomString(32);
           request.getSession(false).setAttribute(
             "integral_pay_session", integral_pay_session);
           mv.addObject("paymentTools", this.paymentTools);
           mv.addObject("integral_pay_session", integral_pay_session);
         } else {
           mv = new JModelAndView("line_pay.html", 
             this.configService.getSysConfig(), 
             this.userConfigService.getUserConfig(), 1, request, 
             response);
           mv.addObject("payType", payType);
           mv.addObject("url", CommWebUtil.getURL(request));
           mv.addObject("payTools", this.payTools);
           mv.addObject("type", "integral");
           Map params = new HashMap();
           params.put("install", Boolean.valueOf(true));
           params.put("mark", payType);
           params.put("type", "admin");
           List payments = this.paymentService.selectByMap(params);
           mv.addObject("payment_id", payments.size() > 0 ? 
             ((Payment)payments
             .get(0)).getId() : new Payment());
         }
         mv.addObject("integral_order_id", integral_order_id);
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "该订单不能进行付款！");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="订单线下支付", value="/integral_order_pay_outline.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
   @RequestMapping({"/integral_order_pay_outline.htm"})
   public ModelAndView integral_order_pay_outline(HttpServletRequest request, HttpServletResponse response, String payType, String integral_order_id, String igo_pay_msg, String integral_pay_session)
   {
     ModelAndView mv = new JModelAndView("success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     String integral_pay_session1 = CommWebUtil.null2String(request.getSession(
       false).getAttribute("integral_pay_session"));
     if (integral_pay_session1.equals(integral_pay_session)) {
       IntegralGoodsOrder order = this.integralGoodsOrderService
         .selectById(CommWebUtil.null2Long(integral_order_id));
       order.setIgo_pay_msg(igo_pay_msg);
       order.setIgo_payment("outline");
       order.setIgo_pay_time(new Date());
       order.setIgo_status(10);
       this.integralGoodsOrderService.updateSelectiveById(order);
       request.getSession(false).removeAttribute("pay_session");
       mv.addObject("op_title", "线下支付提交成功，等待审核！");
       mv.addObject("url", CommWebUtil.getURL(request) + 
         "/buyer/integral_order.htm");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "订单已经支付，禁止重复支付！");
       mv.addObject("url", CommWebUtil.getURL(request) + 
         "/buyer/integral_order.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="订单预付款支付", value="/integral_order_pay_balance.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
   @RequestMapping({"/integral_order_pay_balance.htm"})
   public ModelAndView integral_order_pay_balance(HttpServletRequest request, HttpServletResponse response, String payType, String integral_order_id, String igo_pay_msg) {
     ModelAndView mv = new JModelAndView("success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     IntegralGoodsOrder order = this.integralGoodsOrderService
       .selectById(CommWebUtil.null2Long(integral_order_id));
     User user = this.userService.selectById(
       SecurityUserHolder.getCurrentUser().getId());
 
     if (CommWebUtil.null2Double(user.getAvailableBalance()) > 
       CommWebUtil.null2Double(order.getIgo_trans_fee())) {
       order.setIgo_pay_msg(igo_pay_msg);
       order.setIgo_status(20);
       order.setIgo_payment("balance");
       order.setIgo_pay_time(new Date());
       boolean ret = this.integralGoodsOrderService.updateSelectiveById(order);
       if (ret) {
         user.setAvailableBalance(BigDecimal.valueOf(CommWebUtil.subtract(
           user.getAvailableBalance(), order.getIgo_trans_fee())));
         this.userService.updateSelectiveById(user);
 
         for (IntegralGoodsCart igc : order.getIgo_gcs()) {
           IntegralGoods goods = igc.getGoods();
           goods.setIg_goods_count(goods.getIg_goods_count() - 
             igc.getCount());
           goods.setIg_exchange_count(goods.getIg_exchange_count() + 
             igc.getCount());
           this.integralGoodsService.updateSelectiveById(goods);
         }
       }
       mv.addObject("op_title", "预付款支付成功！");
       mv.addObject("url", CommWebUtil.getURL(request) + 
         "/buyer/integral_order_list.htm");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "可用余额不足，支付失败！");
       mv.addObject("url", CommWebUtil.getURL(request) + 
         "/buyer/integral_order.htm");
     }
 
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="积分订单支付结果", value="/integral_order_finish.htm*", rtype="buyer", rname="积分兑换", rcode="integral_exchange", rgroup="积分兑换")
   @RequestMapping({"/integral_order_finish.htm"})
   public ModelAndView integral_order_finish(HttpServletRequest request, HttpServletResponse response, String order_id) {
     ModelAndView mv = new JModelAndView("integral_order_finish.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     IntegralGoodsOrder obj = this.integralGoodsOrderService
       .selectById(CommWebUtil.null2Long(order_id));
     mv.addObject("obj", obj);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="订单支付详情", value="/integral_order_pay_view.htm*", rtype="buyer", rname="购物流程3", rcode="goods_cart", rgroup="在线购物")
   @RequestMapping({"/integral_order_pay_view.htm"})
   public ModelAndView integral_order_pay_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView("integral_exchange4.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     IntegralGoodsOrder obj = this.integralGoodsOrderService
       .selectById(CommWebUtil.null2Long(id));
     if (obj.getIgo_status() == 0) {
       mv.addObject("obj", obj);
       mv.addObject("paymentTools", this.paymentTools);
       mv.addObject("url", CommWebUtil.getURL(request));
     } else if (obj.getIgo_status() < 0) {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "该订单已经取消！");
       mv.addObject("url", CommWebUtil.getURL(request) + 
         "/buyer/integral_order_list.htm");
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "该订单已经付款！");
       mv.addObject("url", CommWebUtil.getURL(request) + 
         "/buyer/integral_order_list.htm");
     }
     return mv;
   }
 }


 
 
