 package com.rt.shop.view.admin.buyer.actions;
 
 import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
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
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.ExpressCompany;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.OrderLog;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.PredepositLog;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StorePoint;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.User;
import com.rt.shop.entity.query.OrderFormQueryObject;
import com.rt.shop.entity.virtual.TransInfo;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IExpressCompanyService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsReturnService;
import com.rt.shop.service.IGoodsReturnitemService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IPredepositLogService;
import com.rt.shop.service.IStorePointService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.MsgTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
 
 @Controller
 public class OrderBuyerAction
 {
 
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IOrderLogService orderFormLogService;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private IUserService userService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private ITemplateService templateService;
 
   @Autowired
   private IStorePointService storePointService;
 
   @Autowired
   private IPredepositLogService predepositLogService;
 
   @Autowired
   private IPaymentService paymentService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
 
   @Autowired
   private IGoodsReturnitemService goodsReturnItemService;
 
   @Autowired
   private IGoodsReturnService goodsReturnService;
 
   @Autowired
   private IExpressCompanyService expressCompayService;
 
   @Autowired
   private MsgTools msgTools;
 
   @SecurityMapping(display = false, rsequence = 0, title="买家订单列表", value="/buyer/order.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order.htm"})
   public ModelAndView order(HttpServletRequest request, HttpServletResponse response, String currentPage, String order_id, String beginTime, String endTime, String order_status)
   {
     ModelAndView mv = new JModelAndView("user/default/usercenter/buyer_order.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/buyer_order.html",
					this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
	 }
     OrderFormQueryObject ofqo = new OrderFormQueryObject(currentPage, mv, "addTime", "desc");
     ofqo.addQuery("obj.user.id", new SysMap("user_id", SecurityUserHolder.getCurrentUser().getId()), "=");
     if (!CommUtil.null2String(order_id).equals("")) {
    	 ofqo.addQuery("obj.order_id", new SysMap("order_id", "%" + order_id + "%"), "like");
    	 mv.addObject("order_id", order_id);
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
    	 ofqo.addQuery("obj.addTime", new SysMap("beginTime", CommUtil.formatDate(beginTime)), ">=");
    	 mv.addObject("beginTime", beginTime);
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
    	 ofqo.addQuery("obj.addTime", 
         new SysMap("endTime", CommUtil.formatDate(endTime)), "<=");
    	 mv.addObject("endTime", endTime);
     }
     if (!CommUtil.null2String(order_status).equals("")) {
       if (order_status.equals("order_submit")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(10)), "=");
       }
       if (order_status.equals("order_pay")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(20)), "=");
       }
       if (order_status.equals("order_shipping")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(30)), "=");
       }
       if (order_status.equals("order_receive")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(40)), "=");
       }
       if (order_status.equals("order_finish")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(60)), "=");
       }
       if (order_status.equals("order_cancel")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(0)), "=");
       }
     }
     mv.addObject("order_status", order_status);
     Page pList = this.orderFormService.selectPage(new Page<OrderForm>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
   
   @SecurityMapping(display = false, rsequence = 0, title="买家订单列表", value="/buyer/ajaxorder.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/ajaxorder.htm"})
   public void ajaxorder(HttpServletRequest request, HttpServletResponse response, String currentPage, String order_id, String beginTime, String endTime, String order_status)
   {
	   Map<String, Object> map = new HashMap<String, Object>();
	   
     OrderFormQueryObject ofqo = new OrderFormQueryObject(currentPage, map, "addTime", "desc");
     
     ofqo.addQuery("obj.user.id", new SysMap("user_id", SecurityUserHolder.getCurrentUser().getId()), "=");
     if (!CommUtil.null2String(order_id).equals("")) {
    	 ofqo.addQuery("obj.order_id", new SysMap("order_id", "%" + order_id + "%"), "like");
    	 map.put("order_id", order_id);
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
    	 ofqo.addQuery("obj.addTime", new SysMap("beginTime", CommUtil.formatDate(beginTime)), ">=");
    	 map.put("beginTime", beginTime);
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
    	 ofqo.addQuery("obj.addTime", 
         new SysMap("endTime", CommUtil.formatDate(endTime)), "<=");
    	 map.put("endTime", endTime);
     }
     if (!CommUtil.null2String(order_status).equals("")) {
       if (order_status.equals("order_submit")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(10)), "=");
       }
       if (order_status.equals("order_pay")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(20)), "=");
       }
       if (order_status.equals("order_shipping")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(30)), "=");
       }
       if (order_status.equals("order_receive")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(40)), "=");
       }
       if (order_status.equals("order_finish")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(60)), "=");
       }
       if (order_status.equals("order_cancel")) {
    	   ofqo.addQuery("obj.order_status", 
           new SysMap("order_status", Integer.valueOf(0)), "=");
       }
     }
     map.put("order_status", order_status);
     
     Page pList = this.orderFormService.selectPage(new Page<OrderForm>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil sCommWebUtil=new CommWebUtil();
     sCommWebUtil.saveWebPaths(map, this.configService.getSysConfig(), request);
     map.put("show", "orders");
     sCommWebUtil.saveIPageList2Map("", "", "", pList, map);
     
	 String ret = Json.toJson(map, JsonFormat.compact());
	 response.setContentType("text/plain");
	 response.setHeader("Cache-Control", "no-cache");
	 response.setCharacterEncoding("UTF-8");
	 try {
		PrintWriter writer = response.getWriter();
		writer.print(ret);
	 } catch (IOException e) {
		e.printStackTrace();
	 }
     
     //return mv;
   }
   
   /**
	 * 取消订单
	 * @param request
	 * @param response
	 * @param id
	 * @param currentPage
	 * @return
	 */
   @SecurityMapping(display = false, rsequence = 0, title="订单取消", value="/buyer/order_cancel.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_cancel.htm"})
   public ModelAndView order_cancel(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView("user/default/usercenter/buyer_order_cancel.html", 
       this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
     
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
     
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/buyer_order_cancel.html",
					this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
	 }
     OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
       mv.addObject("currentPage", currentPage);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
       mv.addObject("op_title", "您没有编号为" + id + "的订单！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     return mv;
   }
   
   /**
	 * 订单取消确认
	 * @param request
	 * @param response
	 * @param id
	 * @param currentPage
	 * @param state_info
	 * @param other_state_info
	 * @return
	 * @throws Exception
	 */
   @SecurityMapping(display = false, rsequence = 0, title="订单取消确认", value="/buyer/order_cancel_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_cancel_save.htm"})
   public String order_cancel_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String state_info, String other_state_info) throws Exception {
     
	 OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) {
       obj.setOrder_status(0);
       this.orderFormService.updateSelectiveById(obj);
       OrderLog ofl = new OrderLog();
       ofl.setAddTime(new Date());
       ofl.setLog_info("取消订单");
       ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
       ofl.setOf_id(obj.getId());
       if (state_info.equals("other"))
         ofl.setState_info(other_state_info);
       else {
         ofl.setState_info(state_info);
       }
       this.orderFormLogService.insertSelective(ofl);
       				User sUser=new User();
		sUser.setStore_id(storeService.selectById(obj.getStore_id()).getId());
		User storeUser=userService.selectOne(sUser);
       if (this.configService.getSysConfig().getEmailEnable()) {
         send_email(request, obj, "email_toseller_order_cancel_notify");
       }
       if (this.configService.getSysConfig().getSmsEnbale()) {
         send_sms(request, obj, storeUser.getMobile(), "sms_toseller_order_cancel_notify");
       }
     }
     return "redirect:order.htm?currentPage=" + currentPage;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="收货确认", value="/buyer/order_cofirm.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_cofirm.htm"})
   public ModelAndView order_cofirm(HttpServletRequest request, HttpServletResponse response, String id, String currentPage)
   {
     ModelAndView mv = new JModelAndView("user/default/usercenter/buyer_order_cofirm.html", 
       this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/buyer_order_cofirm.html", 
			this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
	 }
     OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
       mv.addObject("currentPage", currentPage);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, response);
       mv.addObject("op_title", "您没有编号为" + id + "的订单！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     return mv;
   }
 
   /**
	 * 买家确认收货
	 * @param request
	 * @param response
	 * @param id
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
   @SecurityMapping(display = false, rsequence = 0, title="收货确认保存", value="/buyer/order_cofirm_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_cofirm_save.htm"})
   public String order_cofirm_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) throws Exception
   {
     OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) {
       obj.setOrder_status(40);
       boolean ret = this.orderFormService.updateSelectiveById(obj);
       if (ret) {
         OrderLog ofl = new OrderLog();
         ofl.setAddTime(new Date());
         ofl.setLog_info("确认收货");
         ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
         ofl.setOf_id(obj.getId());
         this.orderFormLogService.insertSelective(ofl);
			User sUser=new User();
			sUser.setStore_id(obj.getStore_id());
			User storeUser=userService.selectOne(sUser);
         if (this.configService.getSysConfig().getEmailEnable()) {
           send_email(request, obj, "email_toseller_order_receive_ok_notify");
         }
         if (this.configService.getSysConfig().getSmsEnbale()) {
           send_sms(request, obj, storeUser.getMobile(), "sms_toseller_order_receive_ok_notify");
         }
         Payment payment=paymentService.selectById(obj.getPayment_id());
         if (payment.getMark().equals("balance")) {
           User seller = this.userService.selectById(storeUser.getId());
           if (this.configService.getSysConfig().getBalance_fenrun() == 1)
           {
             Map params = new HashMap();
             params.put("type", "admin");
             params.put("mark", "balance");
             Payment sPayment=new Payment();
             sPayment.setType("admin");
             sPayment.setMark("balance");
             List payments = this.paymentService.selectList(sPayment);
            		 //.query("select obj from Payment obj where obj.type=:type and obj.mark=:mark", params, -1, -1);
             Payment shop_payment = new Payment();
             if (payments.size() > 0) {
               shop_payment = (Payment)payments.get(0);
             }
 
             double shop_availableBalance = CommUtil.null2Double(obj.getTotalPrice()) * CommUtil.null2Double(shop_payment.getBalance_divide_rate());
             User ssUser=new User();
             ssUser.setUserName("admin");
             User admin = this.userService.selectOne(ssUser);
             admin.setAvailableBalance(BigDecimal.valueOf(CommUtil.add(admin.getAvailableBalance(), Double.valueOf(shop_availableBalance))));
             this.userService.updateSelectiveById(admin);
             PredepositLog log = new PredepositLog();
             log.setAddTime(new Date());
             log.setPd_log_user_id(seller.getId());
             log.setPd_op_type("分润");
             log.setPd_log_amount(BigDecimal.valueOf(shop_availableBalance));
             log.setPd_log_info("订单" + obj.getOrder_id() + "确认收货平台分润获得预存款");
             log.setPd_type("可用预存款");
             this.predepositLogService.insertSelective(log);
 
             double seller_availableBalance = CommUtil.null2Double(obj.getTotalPrice()) - shop_availableBalance;
             seller.setAvailableBalance(BigDecimal.valueOf(CommUtil.add(seller.getAvailableBalance(), Double.valueOf(seller_availableBalance))));
             this.userService.updateSelectiveById(seller);
             PredepositLog log1 = new PredepositLog();
             log1.setAddTime(new Date());
             log1.setPd_log_user_id(seller.getId());
             log1.setPd_op_type("增加");
             log1.setPd_log_amount(BigDecimal.valueOf(seller_availableBalance));
             log1.setPd_log_info("订单" + obj.getOrder_id() + "确认收货增加预存款");
             log1.setPd_type("可用预存款");
             this.predepositLogService.insertSelective(log1);
             
             User buyer = userService.selectById(obj.getUser_id());
             buyer.setFreezeBlance(BigDecimal.valueOf(CommUtil.subtract(buyer.getFreezeBlance(), obj.getTotalPrice())));
             this.userService.updateSelectiveById(buyer);
           }
           else {
             seller.setAvailableBalance(BigDecimal.valueOf(CommUtil.add(seller.getAvailableBalance(), obj.getTotalPrice())));
             this.userService.updateSelectiveById(seller);
             PredepositLog log = new PredepositLog();
             log.setAddTime(new Date());
             log.setPd_log_user_id(seller.getId());
             log.setPd_op_type("增加");
             log.setPd_log_amount(obj.getTotalPrice());
             log.setPd_log_info("订单" + obj.getOrder_id() + "确认收货增加预存款");
             log.setPd_type("可用预存款");
             this.predepositLogService.insertSelective(log);
 
             User buyer = userService.selectById(obj.getId());
             buyer.setFreezeBlance(BigDecimal.valueOf(CommUtil.subtract(buyer.getFreezeBlance(), obj.getTotalPrice())));
             this.userService.updateSelectiveById(buyer);
           }
         }
       }
     }
     String url = "redirect:order.htm?currentPage=" + currentPage;
     return url;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家评价", value="/buyer/order_evaluate.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_evaluate.htm"})
   public ModelAndView order_evaluate(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/buyer_order_evaluate.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/order_evaluate.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	 }
     
     if (obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
       if (obj.getOrder_status() >= 50) {
         mv = new JModelAndView("success.html", this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, response);
         mv.addObject("op_title", "订单已经评价！");
         mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, response);
       mv.addObject("op_title", "您没有编号为" + id + "的订单！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     
     
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家评价保存", value="/buyer/order_evaluate_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_evaluate_save.htm"})
   public ModelAndView order_evaluate_save(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {
     OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) {
       if (obj.getOrder_status() == 40) {
         obj.setOrder_status(50);
         this.orderFormService.updateSelectiveById(obj);
         OrderLog ofl = new OrderLog();
         ofl.setAddTime(new Date());
         ofl.setLog_info("评价订单");
         ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
         ofl.setOf_id(obj.getId());
         this.orderFormLogService.insertSelective(ofl);
         GoodsCart sGoodsCart=new GoodsCart();
         sGoodsCart.setOf_id(obj.getId());
         List<GoodsCart> gcList=goodsCartService.selectList(null);
         for (GoodsCart gc : gcList) {
           Evaluate eva = new Evaluate();
           eva.setAddTime(new Date());
           eva.setEvaluate_goods(gc.getGoods());
           eva.setEvaluate_info(request.getParameter("evaluate_info_" + gc.getId()));
           eva.setEvaluate_buyer_val(CommUtil.null2Int(request.getParameter("evaluate_buyer_val" + gc.getId())));
           eva.setDescription_evaluate(BigDecimal.valueOf(
             CommUtil.null2Double(request.getParameter("description_evaluate" + gc.getId()))));
           eva.setService_evaluate(BigDecimal.valueOf(CommUtil.null2Double(request.getParameter("service_evaluate" + gc.getId()))));
           eva.setShip_evaluate(BigDecimal.valueOf(CommUtil.null2Double(request.getParameter("ship_evaluate" + gc.getId()))));
           eva.setEvaluate_type("goods");
           eva.setEvaluate_user(SecurityUserHolder.getCurrentUser());
           eva.setOf_id(obj.getId());
           eva.setGoods_spec(gc.getSpec_info());
           this.evaluateService.insertSelective(eva);
           Map params = new HashMap();
           params.put("store_id", obj.getStore_id());
           Evaluate sEvaluate=new Evaluate();
          
           List<Evaluate> evas = this.evaluateService.selectList(sEvaluate);//TODO
        		   //.query("select obj from Evaluate obj where obj.of.store.id=:store_id", params, -1, -1);
           double store_evaluate1 = 0.0D;
           double store_evaluate1_total = 0.0D;
           double description_evaluate = 0.0D;
           double description_evaluate_total = 0.0D;
           double service_evaluate = 0.0D;
           double service_evaluate_total = 0.0D;
           double ship_evaluate = 0.0D;
           double ship_evaluate_total = 0.0D;
           DecimalFormat df = new DecimalFormat("0.0");
           for (Evaluate eva1 : evas)
           {
             store_evaluate1_total = store_evaluate1_total + eva1.getEvaluate_buyer_val();
 
             description_evaluate_total = description_evaluate_total + CommUtil.null2Double(eva1.getDescription_evaluate());
 
             service_evaluate_total = service_evaluate_total + CommUtil.null2Double(eva1.getService_evaluate());
 
             ship_evaluate_total = ship_evaluate_total + CommUtil.null2Double(eva1.getShip_evaluate());
           }
           store_evaluate1 = CommUtil.null2Double(df.format(store_evaluate1_total / evas.size()));
           description_evaluate = CommUtil.null2Double(df.format(description_evaluate_total / evas.size()));
           service_evaluate = CommUtil.null2Double(df.format(service_evaluate_total / evas.size()));
           ship_evaluate = CommUtil.null2Double(df.format(ship_evaluate_total / evas.size()));
           Store store = storeService.selectById(obj.getStore_id());
           store.setStore_credit(store.getStore_credit() + eva.getEvaluate_buyer_val());
           this.storeService.updateSelectiveById(store);
          
           StorePoint sStorePoint=new StorePoint();
           sStorePoint.setStore_id(store.getId());
           List sps = this.storePointService.selectList(sStorePoint);
        		   //.query("select obj from StorePoint obj where obj.store.id=:store_id", params, -1, -1);
           StorePoint point = null;
           if (sps.size() > 0)
             point = (StorePoint)sps.get(0);
           else {
             point = new StorePoint();
           }
           point.setAddTime(new Date());
           point.setStore_id(store.getId());
           point.setDescription_evaluate(BigDecimal.valueOf(description_evaluate));
           point.setService_evaluate(BigDecimal.valueOf(service_evaluate));
           point.setShip_evaluate(BigDecimal.valueOf(ship_evaluate));
           point.setStore_evaluate1(BigDecimal.valueOf(store_evaluate1));
           if (sps.size() > 0)
             this.storePointService.updateSelectiveById(point);
           else {
             this.storePointService.insertSelective(point);
           }
 
           User user =userService.selectById(obj.getId());
           user.setIntegral(user.getIntegral() + this.configService.getSysConfig().getIndentComment());
           this.userService.updateSelectiveById(user);
         }
       }
       if (this.configService.getSysConfig().getEmailEnable()) {
         send_email(request, obj, "email_toseller_evaluate_ok_notify");
       }
     }
     ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     mv.addObject("op_title", "订单评价成功！");
     mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="删除订单信息", value="/buyer/order_delete.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_delete.htm"})
   public String order_delete(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) throws Exception {
     OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(id));
 
     if ((obj.getUser_id().equals(SecurityUserHolder.getCurrentUser().getId())) && 
       (obj.getOrder_status() == 0)) {
    	 GoodsCart sGoodsCart=new GoodsCart();
    	 sGoodsCart.setOf_id(obj.getId());
    	 List<GoodsCart> gcList=goodsCartService.selectList(sGoodsCart);
       for (GoodsCart gc : gcList) {
         gc.getGsps().clear();
         this.goodsCartService.deleteById(gc.getId());
       }
       this.orderFormService.deleteById(obj.getId());
     }
 
     return "redirect:order.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家订单详情", value="/buyer/order_view.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_view.htm"})
   public ModelAndView order_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/order_view.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id()
       .equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
       TransInfo transInfo = query_ship_getData(
         CommUtil.null2String(obj.getId()));
       mv.addObject("transInfo", transInfo);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您没有编号为" + id + "的订单！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家物流详情", value="/buyer/ship_view.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/ship_view.htm"})
   public ModelAndView order_ship_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/order_ship_view.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
     if ((obj != null) && (!obj.equals("")))
     {
       if (obj.getUser_id()
         .equals(SecurityUserHolder.getCurrentUser().getId())) {
         mv.addObject("obj", obj);
         TransInfo transInfo = query_ship_getData(
           CommUtil.null2String(obj.getId()));
         mv.addObject("transInfo", transInfo);
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "您查询的物流不存在！");
         mv.addObject("url", CommUtil.getURL(request) + 
           "/buyer/order.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您查询的物流不存在！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="物流跟踪查询", value="/buyer/query_ship.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/query_ship.htm"})
   public ModelAndView query_ship(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/query_ship_data.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     TransInfo info = query_ship_getData(id);
     mv.addObject("transInfo", info);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="虚拟商品信息", value="/buyer/order_seller_intro.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_seller_intro.htm"})
   public ModelAndView order_seller_intro(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/order_seller_intro.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id()
       .equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
     }
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家退货申请", value="/buyer/order_return_apply.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_return_apply.htm"})
   public ModelAndView order_return_apply(HttpServletRequest request, HttpServletResponse response, String id, String view) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/order_return_apply.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id()
       .equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
       if ((view != null) && (!view.equals("")))
         mv.addObject("view", Boolean.valueOf(true));
     }
     else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您没有编号为" + id + "的订单！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买家退货申请保存", value="/buyer/order_return_apply_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_return_apply_save.htm"})
   public String order_return_apply_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String return_content) throws Exception {
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id()
       .equals(SecurityUserHolder.getCurrentUser().getId())) {
       obj.setOrder_status(45);
       obj.setReturn_content(return_content);
       this.orderFormService.updateSelectiveById(obj);
       if (this.configService.getSysConfig().getEmailEnable()) {
         send_email(request, obj, 
           "email_toseller_order_return_apply_notify");
       }
       				User sUser=new User();
		sUser.setStore_id(storeService.selectById(obj.getStore_id()).getId());
		User storeUser=userService.selectOne(sUser);
       if (this.configService.getSysConfig().getSmsEnbale()) {
         send_sms(request, obj, storeUser.getMobile(), 
           "sms_toseller_order_return_apply_notify");
       }
     }
     return "redirect:order.htm?currentPage=" + currentPage;
   }
   @SecurityMapping(display = false, rsequence = 0, title="买家退货物流信息", value="/buyer/order_return_ship.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_return_ship.htm"})
   public ModelAndView order_return_ship(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/order_return_ship.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
 
     if (obj.getUser_id()
       .equals(SecurityUserHolder.getCurrentUser().getId())) {
       mv.addObject("obj", obj);
       mv.addObject("currentPage", currentPage);
 
       
       GoodsCart sGoodsCart=new GoodsCart();
       sGoodsCart.setOf_id(CommUtil.null2Long(id));
       List<GoodsCart> goodsCarts = this.goodsCartService.selectList(sGoodsCart);
       List deliveryGoods = new ArrayList();
       boolean physicalGoods = false;
       for (GoodsCart gc : goodsCarts) {
         if (gc.getGoods().getGoods_choice_type() == 1)
           deliveryGoods.add(gc);
         else {
           physicalGoods = true;
         }
       }
      
       ExpressCompany sExpressCompany=new ExpressCompany();
       sExpressCompany.setCompany_status(Integer.valueOf(0));
       List expressCompanys = this.expressCompayService.selectList(sExpressCompany, "company_sequence asc");
      //   .query("select obj from ExpressCompany obj where obj.company_status=:status order by company_sequence asc", 
      
       mv.addObject("expressCompanys", expressCompanys);
       mv.addObject("physicalGoods", Boolean.valueOf(physicalGoods));
       mv.addObject("deliveryGoods", deliveryGoods);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "您没有编号为" + id + "的订单！");
       mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
     }
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="买家退货物流信息保存", value="/buyer/order_return_ship_save.htm*", rtype="buyer", rname="用户中心", rcode="user_center", rgroup="用户中心")
   @RequestMapping({"/buyer/order_return_ship_save.htm"})
   public String order_return_ship_save(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String ec_id, String return_shipCode) {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/order_return_apply_view.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
     ExpressCompany ec = this.expressCompayService.selectById(
       CommUtil.null2Long(ec_id));
     obj.setReturn_ec_id(ec.getId());
     obj.setReturn_shipCode(return_shipCode);
     this.orderFormService.updateSelectiveById(obj);
     return "redirect:order.htm?currentPage=" + currentPage;
   }
 
   private TransInfo query_ship_getData(String id) {
     TransInfo info = new TransInfo();
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
     try {
    	 ExpressCompany ec=expressCompayService.selectById(obj.getEc_id());
       String query_url = "http://api.kuaidi100.com/api?id=" + 
         this.configService.getSysConfig().getKuaidi_id() + 
         "&com=" + (
         obj.getEc_id() != null ? ec.getCompany_mark() : "") + 
         "&nu=" + obj.getShipCode() + "&show=0&muti=1&order=asc";
       URL url = new URL(query_url);
       URLConnection con = url.openConnection();
       con.setAllowUserInteraction(false);
       InputStream urlStream = url.openStream();
       String type = URLConnection.guessContentTypeFromStream(urlStream);
       String charSet = null;
       if (type == null)
         type = con.getContentType();
       if ((type == null) || (type.trim().length() == 0) || 
         (type.trim().indexOf("text/html") < 0))
         return info;
       if (type.indexOf("charset=") > 0)
         charSet = type.substring(type.indexOf("charset=") + 8);
       byte[] b = new byte[10000];
       int numRead = urlStream.read(b);
       String content = new String(b, 0, numRead, charSet);
       while (numRead != -1) {
         numRead = urlStream.read(b);
         if (numRead == -1)
           continue;
         String newContent = new String(b, 0, numRead, charSet);
         content = content + newContent;
       }
 
       info = (TransInfo)Json.fromJson(TransInfo.class, content);
       urlStream.close();
     } catch (MalformedURLException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }
     return info;
   }
 
   private void send_email(HttpServletRequest request, OrderForm order, String mark) throws Exception
   {
	   Template sTemplate=new Template();
	   sTemplate.setMark(mark);
     Template template = this.templateService.selectOne(sTemplate);
     if (template.getOpen()) {
    	 				User sUser=new User();
			sUser.setStore_id(storeService.selectById(order.getStore_id()).getId());
			User storeUser=userService.selectOne(sUser);
       String email = storeUser.getEmail();
       String subject = template.getTitle();
       String path = request.getSession().getServletContext()
         .getRealPath("/") + 
         "/vm/";
       PrintWriter pwrite = new PrintWriter(
         new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
       pwrite.print(template.getContent());
       pwrite.flush();
       pwrite.close();
 
       Properties p = new Properties();
       p.setProperty("file.resource.loader.path", 
         request.getRealPath("/") + "vm" + File.separator);
       p.setProperty("input.encoding", "UTF-8");
       p.setProperty("output.encoding", "UTF-8");
       Velocity.init(p);
       org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", 
         "UTF-8");
       VelocityContext context = new VelocityContext();
       context.put("buyer", userService.selectById(order.getUser_id()));
       context.put("seller", storeUser);
       context.put("config", this.configService.getSysConfig());
       context.put("send_time", CommUtil.formatLongDate(new Date()));
       context.put("webPath", CommUtil.getURL(request));
       context.put("order", order);
       StringWriter writer = new StringWriter();
       blank.merge(context, writer);
 
       String content = writer.toString();
       this.msgTools.sendEmail(email, subject, content);
     }
   }
 
   private void send_sms(HttpServletRequest request, OrderForm order, String mobile, String mark) throws Exception
   {
		User sUser=new User();
		sUser.setStore_id(storeService.selectById(order.getStore_id()).getId());
		User storeUser=userService.selectOne(sUser);
	   Template sTemplate=new Template();
	   sTemplate.setMark(mark);
     Template template = this.templateService.selectOne(sTemplate);
     if (template.getOpen()) {
       String path = request.getSession().getServletContext()
         .getRealPath("/") + 
         "/vm/";
       PrintWriter pwrite = new PrintWriter(
         new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
       pwrite.print(template.getContent());
       pwrite.flush();
       pwrite.close();
 
       Properties p = new Properties();
       p.setProperty("file.resource.loader.path", 
         request.getRealPath("/") + "vm" + File.separator);
       p.setProperty("input.encoding", "UTF-8");
       p.setProperty("output.encoding", "UTF-8");
       Velocity.init(p);
       org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", 
         "UTF-8");
       VelocityContext context = new VelocityContext();
       context.put("buyer", userService.selectById(order.getUser_id()));
       context.put("seller", storeUser);
       context.put("config", this.configService.getSysConfig());
       context.put("send_time", CommUtil.formatLongDate(new Date()));
       context.put("webPath", CommUtil.getURL(request));
       context.put("order", order);
       StringWriter writer = new StringWriter();
       blank.merge(context, writer);
 
       String content = writer.toString();
       this.msgTools.sendSMS(mobile, content);
     }
   }
   

	/**
	 * wap服务中心
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityMapping(display = false, rsequence = 0, title = "服务中心", value = "/buyer/service_center.htm*", rtype = "buyer", rname = "服务中心", rcode = "user_center", rgroup = "服务中心")
	@RequestMapping({ "/buyer/service_center.htm" })
	public ModelAndView service_center(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("wap/service_center.html",
				this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
		
		return mv;
	}
	
 }


 
 
 