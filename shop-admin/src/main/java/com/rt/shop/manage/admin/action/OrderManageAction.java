 package com.rt.shop.manage.admin.action;
 
 import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.DateUtils;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.ExpressCompany;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.query.OrderFormQueryObject;
import com.rt.shop.entity.virtual.TransInfo;
import com.rt.shop.manage.util.WebForm;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IExpressCompanyService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
 
 @Controller
 public class OrderManageAction
 {
 
   @Autowired
   private ISysConfigService configService;
   @Autowired
   private IExpressCompanyService expressCompanyService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @SecurityMapping(display = false, rsequence = 0, title="订单设置", value="/admin/set_order_confirm.htm*", rtype="admin", rname="订单设置", rcode="set_order_confirm", rgroup="交易")
   @RequestMapping({"/admin/set_order_confirm.htm"})
   public ModelAndView set_order_confirm(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView(
       "admin/blue/set_order_confirm.html", this.configService
       .getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     return mv;
   }
 
   @SecurityMapping(display = false, rsequence = 0, title="订单设置保存", value="/admin/set_order_confirm_save.htm*", rtype="admin", rname="订单设置", rcode="set_order_confirm", rgroup="交易")
   @RequestMapping({"/admin/set_order_confirm_save.htm"})
   public ModelAndView set_order_confirm_save(HttpServletRequest request, HttpServletResponse response, String id, String auto_order_confirm, String auto_order_notice, String auto_order_return, String auto_order_evaluate)
   {
     SysConfig obj = this.configService.getSysConfig();
     WebForm wf = new WebForm();
     SysConfig config = null;
     if (id.equals("")) {
       config = (SysConfig)wf.toPo(request, SysConfig.class);
       config.setAddTime(new Date());
     } else {
       config = (SysConfig)wf.toPo(request, obj);
     }
     config.setAuto_order_confirm(CommUtil.null2Int(auto_order_confirm));
     config.setAuto_order_notice(CommUtil.null2Int(auto_order_notice));
     config.setAuto_order_return(CommUtil.null2Int(auto_order_return));
     config.setAuto_order_evaluate(CommUtil.null2Int(auto_order_evaluate));
     if (id.equals(""))
       this.configService.insertSelective(config);
     else {
       this.configService.updateSelectiveById(config);
     }
     ModelAndView mv = new JModelAndView("admin/blue/success.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     mv.addObject("op_title", "订单设置成功");
     mv.addObject("list_url", CommUtil.getURL(request) + 
       "/admin/set_order_confirm.htm");
     return mv;
   }
 
   /**
	 * 导出execl (示例)
	 */
	@RequestMapping(value = "/admin/order_export.htm",method = RequestMethod.POST)
	public void exportFile(HttpServletRequest request, HttpServletResponse response, String order_status, String type, String type_data, String payment, String beginTime, String endTime, String begin_price, String end_price){
		Map params = new HashMap();
		ModelAndView mv = new JModelAndView("admin/blue/order_list.html", 
			       this.configService.getSysConfig(), this.userConfigService
			       .getUserConfig(), 0, request, response);
		  OrderFormQueryObject ofqo = new OrderFormQueryObject(null, mv, 
			       "addTime", "desc");
			     if (!CommUtil.null2String(order_status).equals("")) {
			       ofqo.addQuery("obj.order_status", 
			         new SysMap("order_status", 
			         Integer.valueOf(CommUtil.null2Int(order_status))), "=");
			     }
			     if (!CommUtil.null2String(type_data).equals("")) {
			       if (type.equals("store")) {
			         ofqo.addQuery("obj.store.store_name", 
			           new SysMap("store_name", 
			           type_data), "=");
			       }
			       if (type.equals("buyer")) {
			         ofqo.addQuery("obj.user.userName", 
			           new SysMap("userName", 
			           type_data), "=");
			       }
			       if (type.equals("order")) {
			         ofqo.addQuery("obj.order_id", 
			           new SysMap("order_id", type_data), "=");
			       }
			     }
			     if (!CommUtil.null2String(payment).equals("")) {
			       ofqo.addQuery("obj.payment.mark", new SysMap("mark", payment), "=");
			     }
			     if (!CommUtil.null2String(beginTime).equals("")) {
			       ofqo.addQuery("obj.addTime", 
			         new SysMap("beginTime", 
			        		 DateUtils.getDateStart(CommUtil.formatDate(beginTime))), ">=");
			     }
			     if (!CommUtil.null2String(endTime).equals("")) {
			       ofqo.addQuery("obj.addTime", 
			         new SysMap("endTime", 
			         DateUtils.getDateEnd(CommUtil.formatDate(endTime))), "<=");
			     }
			     if (!CommUtil.null2String(begin_price).equals("")) {
			       ofqo.addQuery("obj.totalPrice", 
			         new SysMap("begin_price", 
			         BigDecimal.valueOf(CommUtil.null2Double(begin_price))), 
			         ">=");
			     }
			     if (!CommUtil.null2String(end_price).equals("")) {
			       ofqo.addQuery("obj.totalPrice", 
			         new SysMap("end_price", 
			         BigDecimal.valueOf(CommUtil.null2Double(end_price))), "<=");
			     }
			     ofqo.setPageSize(99999);
			    // orderFormService.
			     Page pList = this.orderFormService.selectPage(new Page<OrderForm>(Integer.valueOf(CommUtil.null2Int(0)), 12), null);

		            
		Map<String, String> titleMap = new LinkedHashMap();
		titleMap.put("店铺名称","obj.store.store_name");
		titleMap.put("订单号","order_id");
		titleMap.put("买家名称","store_name");
		titleMap.put("下单号","parentId");
		titleMap.put("订单总额","parentIds");
		titleMap.put("支付方式","type");
		titleMap.put("订单状态","icon");
		
		
		try {
			//流的方式直接下载
			//ExcelUtils.exportOrderExcel(response, "订单.xls", pList.getRecords(), titleMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
   @SecurityMapping(display = false, rsequence = 0, title="订单列表", value="/admin/order_list.htm*", rtype="admin", rname="订单管理", rcode="order_admin", rgroup="交易")
   @RequestMapping({"/admin/order_list.htm"})
   public ModelAndView order_list(HttpServletRequest request, HttpServletResponse response, String order_status, String type, String type_data, String payment, String beginTime, String endTime, String begin_price, String end_price, String currentPage)
   {
     ModelAndView mv = new JModelAndView("admin/blue/order_list.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     OrderFormQueryObject ofqo = new OrderFormQueryObject(currentPage, mv, 
       "addTime", "desc");
     if (!CommUtil.null2String(order_status).equals("")) {
       ofqo.addQuery("obj.order_status", 
         new SysMap("order_status", 
         Integer.valueOf(CommUtil.null2Int(order_status))), "=");
     }
     if (!CommUtil.null2String(type_data).equals("")) {
       if (type.equals("store")) {
         ofqo.addQuery("obj.store.store_name", 
           new SysMap("store_name", 
           type_data), "=");
       }
       if (type.equals("buyer")) {
         ofqo.addQuery("obj.user.userName", 
           new SysMap("userName", 
           type_data), "=");
       }
       if (type.equals("order")) {
         ofqo.addQuery("obj.order_id", 
           new SysMap("order_id", type_data), "=");
       }
     }
     if (!CommUtil.null2String(payment).equals("")) {
       ofqo.addQuery("obj.payment.mark", new SysMap("mark", payment), "=");
     }
     if (!CommUtil.null2String(beginTime).equals("")) {
	       ofqo.addQuery("obj.addTime", 
	         new SysMap("beginTime", 
	        		 DateUtils.getDateStart(CommUtil.formatDate(beginTime))), ">=");
	     }
	     if (!CommUtil.null2String(endTime).equals("")) {
	       ofqo.addQuery("obj.addTime", 
	         new SysMap("endTime", 
	         DateUtils.getDateEnd(CommUtil.formatDate(endTime))), "<=");
	     }
     if (!CommUtil.null2String(begin_price).equals("")) {
       ofqo.addQuery("obj.totalPrice", 
         new SysMap("begin_price", 
         BigDecimal.valueOf(CommUtil.null2Double(begin_price))), 
         ">=");
     }
     if (!CommUtil.null2String(end_price).equals("")) {
       ofqo.addQuery("obj.totalPrice", 
         new SysMap("end_price", 
         BigDecimal.valueOf(CommUtil.null2Double(end_price))), "<=");
     }
     Page pList = this.orderFormService.selectPage(new Page<OrderForm>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("order_status", order_status);
     mv.addObject("type", type);
     mv.addObject("type_data", type_data);
     mv.addObject("payment", payment);
     if(!CommUtil.null2String(beginTime).equals("")){
    	 mv.addObject("beginTime", beginTime+" 00:00:00");
     }
     if(!CommUtil.null2String(endTime).equals("")){
    	 mv.addObject("endTime", endTime+" 23:59:59");
     }
    
     mv.addObject("begin_price", begin_price);
     mv.addObject("end_price", end_price);
     return mv;
   }
   @SecurityMapping(display = false, rsequence = 0, title="订单详情", value="/admin/order_view.htm*", rtype="admin", rname="订单管理", rcode="order_admin", rgroup="交易")
   @RequestMapping({"/admin/order_view.htm"})
   public ModelAndView order_view(HttpServletRequest request, HttpServletResponse response, String id) {
     ModelAndView mv = new JModelAndView("admin/blue/order_view.html", 
       this.configService.getSysConfig(), this.userConfigService
       .getUserConfig(), 0, request, response);
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
     TransInfo transInfo = query_ship_getData(id);
     mv.addObject("transInfo", transInfo);
     mv.addObject("obj", obj);
     return mv;
   }
 
   public TransInfo query_ship_getData(String id) {
     TransInfo info = new TransInfo();
     OrderForm obj = this.orderFormService
       .selectById(CommUtil.null2Long(id));
     try {
    	 ExpressCompany ec=expressCompanyService.selectById(obj.getEc_id());
       URL url = new URL(
         "http://api.kuaidi100.com/api?id=" + 
         this.configService.getSysConfig().getKuaidi_id() + 
         "&com=" + (
         ec != null ? ec
         .getCompany_mark() : "") + "&nu=" + 
         obj.getShipCode() + "&show=0&muti=1&order=asc");
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
 }


 
 
 