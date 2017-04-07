package com.rt.shop.view.admin.buyer.actions;

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
import com.rt.shop.entity.Address;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.query.OrderFormQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;

/**
 * 用户中心的Controller,用户中心新增的：待评价，收藏店铺，收藏商品，浏览记录功能都在这里
 * 
 * @author : 孙宁 [sun.ning@rhxtune.com]
 * @version : 1.0
 * @created on  : 2016年5月31日,上午11:06:11
 * @copyright : Copyright(c) 2015 北京zsCat信息技术有限公司
 */
@Controller
public class WebUserCenterAction {
	
	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IUserConfigService userConfigService;
	
	@Autowired
	private IOrderFormService orderFormService;

	/**
	 * 待评价模块
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/wait_evaluation.htm")
	public ModelAndView buyerWaitEvaluation(HttpServletRequest request, HttpServletResponse response, String currentPage, String order_id, String beginTime, String endTime, String order_status) {
		
	 ModelAndView mv = new JModelAndView("login.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);

     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
     
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 mv = new JModelAndView("wap/wait_evaluation.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
     }

    
     OrderForm sOrderForm=new OrderForm();
     sOrderForm.setOrder_status(Integer.valueOf(40));
     List<OrderForm> orderForms = orderFormService.selectList(sOrderForm);
    		
     mv.addObject("orderForms",orderForms);
		
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
	
	/**
	 * 收藏商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/favorite_goods.htm")
	public ModelAndView favoriteGoods(HttpServletRequest request  , HttpServletResponse response){
		 
	 ModelAndView mv = new JModelAndView("login.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);

     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
     
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 mv = new JModelAndView("wap/favorite_goods.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
     }

     
     
     return mv;
	}
	
	
	@RequestMapping("/history.htm")
	public ModelAndView history(HttpServletRequest request  , HttpServletResponse response){
		 
	 ModelAndView mv = new JModelAndView("login.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);

     String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
     
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 mv = new JModelAndView("wap/history.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
     }

     
     
     return mv;
	}
}
