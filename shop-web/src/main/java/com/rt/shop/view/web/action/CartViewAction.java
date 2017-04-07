package com.rt.shop.view.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jdom.JDOMException;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.easyjf.web.WebForm;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.WxAdvancedUtil;
import com.rt.shop.common.tools.WxCommonUtil;
import com.rt.shop.common.tools.bean.WxOauth2Token;
import com.rt.shop.common.tools.bean.WxToken;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Address;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.CartGsp;
import com.rt.shop.entity.Coupon;
import com.rt.shop.entity.CouponInfo;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.GoodsSpecProperty;
import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.OrderLog;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.PredepositLog;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreCart;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.User;
import com.rt.shop.entity.YiKaTongPay;
import com.rt.shop.entity.query.AddressQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.pay.alipay.config.AlipayConfig;
import com.rt.shop.pay.alipay.util.AlipaySubmit;
import com.rt.shop.pay.tools.PayTools;
import com.rt.shop.pay.yikatong.config.YiKatongConfig;
import com.rt.shop.pay.yikatong.services.YiKatongService;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAddressService;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.ICouponInfoService;
import com.rt.shop.service.ICouponService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGoodsSpecPropertyService;
import com.rt.shop.service.IGoodsSpecificationService;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IPredepositLogService;
import com.rt.shop.service.IStoreCartService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.MsgTools;
import com.rt.shop.tools.PaymentTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.QRCodeEncoderHandler;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.GoodsViewTools;
import com.rt.shop.view.web.tools.TransportTools;

@Controller
public class CartViewAction {

	@Autowired
	private ISysConfigService configService;
	@Autowired
	private ICouponService couponService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodsSpecificationService goodsSpecificationService;

	@Autowired
	private IGoodsSpecPropertyService goodsSpecPropertyService;

	@Autowired
	private IAddressService addressService;

	@Autowired
	private IAreaService areaService;

	@Autowired
	private IPaymentService paymentService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IGoodsCartService goodsCartService;

	@Autowired
	private IStoreService storeService;

	@Autowired
	private IOrderLogService orderFormLogService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ITemplateService templateService;

	@Autowired
	private IPredepositLogService predepositLogService;

	@Autowired
	private IGroupGoodsService groupGoodsService;

	@Autowired
	private ICouponInfoService couponInfoService;

	@Autowired
	private IStoreCartService storeCartService;

	@Autowired
	private IAccessoryService accessoryService;
	@Autowired
	private MsgTools msgTools;

	@Autowired
	private PaymentTools paymentTools;

	@Autowired
	private PayTools payTools;

	@Autowired
	private TransportTools transportTools;

	@Autowired
	private GoodsViewTools goodsViewTools;

	private static Logger logger = LoggerFactory.getLogger(CartViewAction.class);

	private List<StoreCart> cart_calc(HttpServletRequest request) {
		List<StoreCart> cart = new ArrayList<StoreCart>();
		List<StoreCart> user_cart = new ArrayList<StoreCart>();
		List<StoreCart> cookie_cart = new ArrayList<StoreCart>();
		User user = null;
		if (SecurityUserHolder.getCurrentUser() != null) {
			user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		}
		String cart_session_id = "";
		Map params = new HashMap();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cart_session_id")) {
					cart_session_id = CommUtil.null2String(cookie.getValue());
				}
			}
		}
		if (user != null) {
			if (!cart_session_id.equals("")) {
				Store store=storeService.selectById(user.getStore_id());
				if (store != null) {
					
					StoreCart sStoreCart=new StoreCart();
					String sql=" where (cart_session_id='"+cart_session_id+"' or user_id='"+user.getId()+"') and sc_status='"+Integer.valueOf(0)+"' and store_id='"+store.getId()+"'";
					List<StoreCart> store_cookie_cart = this.storeCartService.selectList(sql, null);
							
					for (StoreCart sc : store_cookie_cart) {
						List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
						for (GoodsCart gc : gcList1) {
							this.goodsCartService.deleteById(gc.getId());
						}
						this.storeCartService.deleteById(((StoreCart) sc).getId());
					}
				}

				StoreCart sStoreCart=new StoreCart();
				sStoreCart.setCart_session_id(cart_session_id);
				sStoreCart.setSc_status(Integer.valueOf(0));
				cookie_cart = this.storeCartService.selectList(sStoreCart);

				StoreCart ssStoreCart=new StoreCart();
				ssStoreCart.setUser_id(user.getId());
				ssStoreCart.setSc_status(Integer.valueOf(0));
				user_cart = this.storeCartService.selectList(ssStoreCart);
			} else {
				StoreCart ssStoreCart=new StoreCart();
				ssStoreCart.setUser_id(user.getId());
				ssStoreCart.setSc_status(Integer.valueOf(0));
				user_cart = this.storeCartService.selectList(ssStoreCart);
			}

		} else if (!cart_session_id.equals("")) {
			StoreCart sStoreCart=new StoreCart();
			sStoreCart.setCart_session_id(cart_session_id);
			sStoreCart.setSc_status(Integer.valueOf(0));
			cookie_cart = this.storeCartService.selectList(sStoreCart);
		}

		for (StoreCart sc : user_cart) {
			boolean sc_add = true;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(sc.getStore_id())) {
					sc_add = false;
				}
			}
			if (sc_add) {
				cart.add(sc);
			}
		}
		for (StoreCart sc : cookie_cart) {
			boolean sc_add = true;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(sc.getStore_id())) {
					sc_add = false;
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
					for (GoodsCart gc : gcList1) {
						gc.setSc_id(sc1.getId());
						this.goodsCartService.updateSelectiveById(gc);
					}
					this.storeCartService.deleteById(sc.getId());
				}
			}
			if (sc_add) {
				cart.add(sc);
			}
		}
		for(StoreCart sc: cart){
			if(sc.getId()!=null){
				List<GoodsCart> gcList=goodsCartService.selectByStoreCartId(sc.getId());
				for(GoodsCart gc : gcList){
					Goods goods=goodsService.selectById(gc.getGoods_id());
					gc.setGoods(goods);
					Accessory ac=accessoryService.selectById(goods.getGoods_main_photo_id());
					goods.setGoods_main_photo(ac);
				}
				sc.setGcs(gcList);sc.setStore(storeService.selectById(sc.getStore_id()));
			}
			
		}
		return (List<StoreCart>) cart;
	}

	@RequestMapping({ "/cart_menu_detail.htm" })
	public ModelAndView cart_menu_detail(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("cart_menu_detail.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		List<StoreCart> cart = cart_calc(request);
		List<GoodsCart> list = new ArrayList<GoodsCart>();
		if (cart != null) {
			for (StoreCart sc : cart) {
				if (sc != null){
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
					for(GoodsCart gc : gcList1){
						Goods goods=goodsService.selectById(gc.getGoods_id());
						gc.setGoods(goods);
						Accessory ac=accessoryService.selectById(goods.getGoods_main_photo_id());
						goods.setGoods_main_photo(ac);
					}
					list.addAll(gcList1);
				}
					
			}
		}
		float total_price = 0.0F;
		for (GoodsCart gc : list) {
			Goods goods = this.goodsService.selectById(gc.getGoods_id());
			if (CommUtil.null2String(gc.getCart_type()).equals("combin"))
				total_price = CommUtil.null2Float(goods.getCombin_price());
			else {
				total_price = CommUtil.null2Float(
						Double.valueOf(CommUtil.mul(Integer.valueOf(gc.getCount()), gc.getPrice()))) + total_price;
			}
		}
		mv.addObject("total_price", Float.valueOf(total_price));
		mv.addObject("cart", list);
		return mv;
	}

	/**
	 * 添加购物车
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param count
	 * @param price
	 * @param gsp
	 * @param buy_type
	 */
	@RequestMapping({ "/add_goods_cart.htm" })
	public void add_goods_cart(HttpServletRequest request, HttpServletResponse response, String id, String count,
			String price, String gsp, String buy_type) {
		String cart_session_id = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cart_session_id")) {
					cart_session_id = CommUtil.null2String(cookie.getValue());
				}
			}
		}

		if (cart_session_id.equals("")) {
			cart_session_id = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("cart_session_id", cart_session_id);
			cookie.setDomain(CommUtil.generic_domain(request));
			response.addCookie(cookie);
		}
		List<StoreCart> cart = new ArrayList<StoreCart>();
		List<StoreCart> user_cart = new ArrayList<StoreCart>();
		List<StoreCart> cookie_cart = new ArrayList<StoreCart>();
		User user = null;
		if (SecurityUserHolder.getCurrentUser() != null) {
			user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		}
		StoreCart sc;
		if (user != null) {
			if (!cart_session_id.equals("")) {
				Store store=storeService.selectById(user.getStore_id());
				if (store != null) {
					StoreCart sStoreCart=new StoreCart();
					String sql="where (cart_session_id='"+cart_session_id+"' or user_id='"+user.getId()+"') and sc_status=0 and store_id='"+store.getId()+"'";
					List store_cookie_cart = this.storeCartService.selectList(sql,null);
					for (Iterator localIterator1 = store_cookie_cart.iterator(); localIterator1.hasNext();) {
						sc = (StoreCart) localIterator1.next();
						List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
						for (GoodsCart gc : gcList1) {
							this.goodsCartService.deleteById(gc.getId());
						}
						this.storeCartService.deleteById(sc.getId());
					}
				}

				StoreCart sStoreCart=new StoreCart();
				sStoreCart.setCart_session_id(cart_session_id);
				sStoreCart.setSc_status(Integer.valueOf(0));
				cookie_cart = this.storeCartService.selectList(sStoreCart);

				StoreCart ssStoreCart=new StoreCart();
				ssStoreCart.setUser_id(user.getId());
				ssStoreCart.setSc_status(Integer.valueOf(0));
				user_cart = this.storeCartService.selectList(ssStoreCart);
			} else {
				StoreCart ssStoreCart=new StoreCart();
				ssStoreCart.setUser_id(user.getId());
				ssStoreCart.setSc_status(Integer.valueOf(0));
				user_cart = this.storeCartService.selectList(ssStoreCart);
			}

		} else if (!cart_session_id.equals("")) {
			StoreCart sStoreCart=new StoreCart();
			sStoreCart.setCart_session_id(cart_session_id);
			sStoreCart.setSc_status(Integer.valueOf(0));
			cookie_cart = this.storeCartService.selectList(sStoreCart);
		}

		for (StoreCart sc12 : user_cart) {
			boolean sc_add = true;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(sc12.getStore_id())) {
					sc_add = false;
				}
			}
			if (sc_add) {
				cart.add(sc12);
			}
		}
		for (StoreCart sc11 : cookie_cart) {
			boolean sc_add = true;
			for (StoreCart sc1 : cart) {
				if (sc11.getStore_id().equals(sc1.getStore_id())) {
					sc_add = false;
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc1.getId());
					for (GoodsCart gc : gcList1) {
					//	gc.setSc(sc1);
						gc.setSc_id(sc1.getId());
						this.goodsCartService.updateSelectiveById(gc);
					}
					this.storeCartService.deleteById(sc1.getId());
				}
			}
			if (sc_add) {
				cart.add(sc11);
			}
		}
		
		String[] gsp_ids = gsp.split(",");
		Arrays.sort(gsp_ids);
		boolean add = true;
		double total_price = 0.0D;
		int total_count = 0;
		String[] gsp_ids1;
		for (StoreCart sc1 : cart){
			GoodsCart gc1=new GoodsCart();
			gc1.setSc_id(sc1.getId());
			List<GoodsCart> goodsCarts=goodsCartService.selectList(gc1);
			for (GoodsCart gc : goodsCarts){
				CartGsp cg=new CartGsp();
				cg.setCart_id(gc.getId());
				List<GoodsSpecProperty> gsps=goodsSpecPropertyService.selectGspByGcId(cg);
				if ((gsp_ids != null) && (gsp_ids.length > 0) && (gsps != null) && (gsps.size() > 0)) {
					gsp_ids1 = new String[gsps.size()];
					for (int i = 0; i < gsps.size(); i++) {
						gsp_ids1[i] = (gsps.get(i) != null
								? ((GoodsSpecProperty) gsps.get(i)).getId().toString() : "");
					}
					Arrays.sort(gsp_ids1);
					if ((!gc.getGoods_id().toString().equals(id)) || (!Arrays.equals(gsp_ids, gsp_ids1)))
						continue;
					add = false;
				} else if (gc.getGoods_id().toString().equals(id)) {
					add = false;
				}
			}
		}

		Object obj;
		if (add) {
			Goods goods = this.goodsService.selectById(CommUtil.null2Long(id));
			String type = "save";
			StoreCart sc33 = new StoreCart();
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(goods.getGoods_store_id())) {
					sc33 = sc1;
					type = "update";
					break;
				}
			}
			sc33.setStore_id(goods.getGoods_store_id());
			if (((String) type).equals("save")) {
				sc33.setAddTime(new Date());
				//TODO
				this.storeCartService.insertSelective(sc33);
			} else {
				this.storeCartService.updateSelectiveById(sc33);
			}

			obj = new GoodsCart();
			((GoodsCart) obj).setAddTime(new Date());
			if (CommUtil.null2String(buy_type).equals("")) {
				((GoodsCart) obj).setCount(CommUtil.null2Int(count));
				((GoodsCart) obj).setPrice(BigDecimal.valueOf(CommUtil.null2Double(price)));
			}
			if (CommUtil.null2String(buy_type).equals("combin")) {
				((GoodsCart) obj).setCount(1);
				((GoodsCart) obj).setCart_type("combin");
				((GoodsCart) obj).setPrice(goods.getCombin_price());
			}
			((GoodsCart) obj).setGoods_id(goods.getId());
			String spec_info = "";
			GoodsSpecProperty spec_property;
			CartGsp cg=new CartGsp();
			cg.setCart_id(((GoodsCart)obj).getId());
			List<GoodsSpecProperty> gsps=goodsSpecPropertyService.selectGspByGcId(cg);
			if ((gsp_ids != null) && (gsp_ids.length > 0) && (gsps != null) && (gsps.size() > 0)) {
			for (String gsp_id : gsp_ids) {
				spec_property = this.goodsSpecPropertyService.selectById(CommUtil.null2Long(gsp_id));
				GoodsSpecification gsf=goodsSpecificationService.selectById(spec_property.getSpec_id1());
				gsps.add(spec_property);
				if (spec_property != null) {
					spec_info = gsf.getName() + ":" + spec_property.getValue() + " " + spec_info;
				}
			}
			}
			((GoodsCart) obj).setSc_id(sc33.getId());
			((GoodsCart) obj).setSpec_info(spec_info);
			this.goodsCartService.insertSelective((GoodsCart) obj);
			GoodsCart sGoodsCart=new GoodsCart();
			sGoodsCart.setSc_id(sc33.getId());
			//?
//			List<GoodsCart> gcList=goodsCartService.selectList(sGoodsCart);
//			gcList.add((GoodsCart) obj);
//			sc33.setGcs(gcList);

			double cart_total_price = 0.0D;
			List<GoodsCart> gcList33=goodsCartService.selectByStoreCartId(sc33.getId());
			for (GoodsCart gc1 : gcList33) {
				Goods goods1=goodsService.selectById(gc1.getGoods_id());
				if (CommUtil.null2String(gc1.getCart_type()).equals("")) {
					
					/*
					 * cart_total_price = cart_total_price +
					 * CommUtil.null2Double(gc1.getGoods().
					 * getGoods_current_price()) * gc1.getCount();
					 */
					cart_total_price = cart_total_price + CommUtil.null2Double(gc1.getPrice()) * gc1.getCount();
					gc1.setStatus(1);
					goodsCartService.updateSelectiveById(gc1);
				}
				if (!CommUtil.null2String(gc1.getCart_type()).equals("combin"))
					continue;
				cart_total_price = cart_total_price
						+ CommUtil.null2Double(goods1.getCombin_price()) * gc1.getCount();
			}

			sc33.setTotal_price(BigDecimal.valueOf(CommUtil.formatMoney(Double.valueOf(cart_total_price))));
			if (user == null)
				sc33.setCart_session_id(cart_session_id);
			else {
				//sc33.setUser(user);
				sc33.setUser_id(user.getId());
			}
			//StoreCart sc44=new StoreCart();
			if (((String) type).equals("save")) {
				sc33.setAddTime(new Date());
//				 sc44=sc33;
//				 sc44.setId(IdWorker.getId());
				 this.storeCartService.updateSelectiveById(sc33);
				//this.storeCartService.insertSelective(sc44);
			} else {
				//sc44=sc33;
				this.storeCartService.updateSelectiveById(sc33);
			}
			boolean cart_add = true;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(sc33.getStore_id())) {
					cart_add = false;
				}
			}
			if (cart_add) {
				cart.add(sc33);
			}
		}else {
			StoreCart sStoreCart=new StoreCart();
			sStoreCart.setUser_id(user.getId());
			sStoreCart.setStore_id(0L);
			sStoreCart.setStore_id(goodsService.selectById(Long.parseLong(id)).getGoods_store_id());
			List<StoreCart> scs = storeCartService.selectList(sStoreCart);
			StoreCart storeCart = scs.get(0);
			storeCart.setTotal_price(BigDecimal.valueOf(Double.valueOf(storeCart.getTotal_price().toString())+Double.valueOf(price)));
			storeCartService.updateSelectiveById(storeCart);
			Goods goods = goodsService.selectById(Long.parseLong(id));
			
			GoodsCart ssGoodsCart=new GoodsCart();
			ssGoodsCart.setGoods_id(goods.getId());
			ssGoodsCart.setSc_id(storeCart.getId());
			List<GoodsCart> gcs = goodsCartService.selectList(ssGoodsCart);
			GoodsCart goodsCart = gcs.get(0);
			goodsCart.setCount(goodsCart.getCount()+1);
			goodsCartService.updateSelectiveById(goodsCart);
		}
		for (Object type = cart.iterator(); ((Iterator) type).hasNext();) {
			StoreCart sc1 = (StoreCart) ((Iterator) type).next();
			List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc1.getId());
			for (GoodsCart gc : gcList1) {
				total_count += gc.getCount();
			}
			for (obj = gcList1.iterator(); ((Iterator) obj).hasNext();) {
				GoodsCart gc1 = (GoodsCart) ((Iterator) obj).next();
				total_price = total_price + CommUtil.mul(gc1.getPrice(), Integer.valueOf(gc1.getCount()));
			}
		}
		Map map = new HashMap();
		map.put("count", Integer.valueOf(total_count));
		map.put("total_price", Double.valueOf(total_price));
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
	}

	/**
	 * 从购物车删除商品
	 * 
	 * @param request
	 * @param response
	 * @param id
	 * @param store_id
	 */
	@RequestMapping({ "/remove_goods_cart.htm" })
	public void remove_goods_cart(HttpServletRequest request, HttpServletResponse response, String id,
			String store_id) {
		GoodsCart gc = this.goodsCartService.selectById(CommUtil.null2Long(id));
		StoreCart the_sc = storeCartService.selectById(gc.getSc_id());
		
		List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(the_sc.getId());
		this.goodsCartService.deleteById(CommUtil.null2Long(id));
		if (gcList1.size() == 0) {
			this.storeCartService.deleteById(the_sc.getId());
		}
		List<StoreCart> cart = cart_calc(request);
		double total_price = 0.0D;
		double sc_total_price = 0.0D;
		double count = 0.0D;
		for (StoreCart sc2 : cart) {
			List<GoodsCart> gcList11=goodsCartService.selectByStoreCartId(sc2.getId());
			for (GoodsCart gc1 : gcList11) {
				total_price = CommUtil.null2Double(gc1.getPrice()) * gc1.getCount() + total_price;
				count += 1.0D;
				if ((store_id == null) || (store_id.equals(""))
						|| (!sc2.getStore_id().toString().equals(store_id)))
					continue;
				sc_total_price = sc_total_price + CommUtil.null2Double(gc1.getPrice()) * gc1.getCount();
				sc2.setTotal_price(BigDecimal.valueOf(sc_total_price));
			}

			this.storeCartService.updateSelectiveById(sc2);
		}
		request.getSession(false).setAttribute("cart", cart);
		Map map = new HashMap();
		map.put("count", Double.valueOf(count));
		map.put("total_price", Double.valueOf(total_price));
		map.put("sc_total_price", Double.valueOf(sc_total_price));
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/goods_count_adjust.htm" })
	public void goods_count_adjust(HttpServletRequest request, HttpServletResponse response, String cart_id,
			String store_id, String count) {
		List<StoreCart> cart = cart_calc(request);

		double goods_total_price = 0.0D;
		String error = "100";
		Goods goods = null;
		String cart_type = "";
		GoodsCart gc;
		for (StoreCart sc : cart){
			List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
			for (Iterator localIterator2 = gcList1.iterator(); localIterator2.hasNext();) {
				gc = (GoodsCart) localIterator2.next();
				if (gc.getId().toString().equals(cart_id)) {
					
					goods=goodsService.selectById(gc.getGoods_id());
					cart_type = CommUtil.null2String(gc.getCart_type());
				}
			}
		}
		Object sc;
		if (cart_type.equals("")) {
			if (goods.getGroup_buy() == 2) {
				GroupGoods gg = new GroupGoods();
				GroupGoods sGroupGoods=new GroupGoods();
				sGroupGoods.setGg_goods_id(goods.getId());
				List<GroupGoods> childs=groupGoodsService.selectList(sGroupGoods);
				for (GroupGoods gg1 : childs) {
					if (gg1.getGg_goods_id().equals(goods.getId())) {
						gg = gg1;
					}
				}
				if (gg.getGg_count() >= CommUtil.null2Int(count))
					for (StoreCart sc1 : cart) { 
						List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc1.getId());
						for (int i = 0; i < gcList1.size(); i++) {
							GoodsCart art = gcList1.get(i);
							GoodsCart gc1 = art;
							if (art.getId().toString().equals(cart_id)) {
								((StoreCart) sc1).setTotal_price(
										BigDecimal.valueOf(CommUtil.add(((StoreCart) sc1).getTotal_price(),
												Double.valueOf((CommUtil.null2Int(count) - art.getCount())
														* CommUtil.null2Double(art.getPrice())))));
								art.setCount(CommUtil.null2Int(count));
								gc1 = art;
								gcList1.remove(art);
								gcList1.add(gc1);
								goods_total_price = CommUtil.null2Double(gc1.getPrice()) * gc1.getCount();
								this.storeCartService.updateSelectiveById((StoreCart) sc1);
							}
						}
					}
				else {
					error = "300";
				}
			} else if (goods.getGoods_inventory() >= CommUtil.null2Int(count)) {
				for (StoreCart scart : cart) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(scart.getId());
					for (int i = 0; i < gcList1.size(); i++) {
						GoodsCart gcart = gcList1.get(i);
						GoodsCart gc1 = gcart;
						if (gcart.getId().toString().equals(cart_id)) {
							scart.setTotal_price(BigDecimal.valueOf(CommUtil.add(scart.getTotal_price(),
									Double.valueOf((CommUtil.null2Int(count) - gcart.getCount())
											* Double.parseDouble(gcart.getPrice().toString())))));
							gcart.setCount(CommUtil.null2Int(count));
							gc1 = gcart;
							gcList1.remove(gcart);
							gcList1.add(gc1);
							goods_total_price = Double.parseDouble(gc1.getPrice().toString()) * gc1.getCount();
							this.storeCartService.updateSelectiveById(scart);
						}
					}
				}
			} else {
				error = "200";
			}
		}

		if (cart_type.equals("combin")) {
			if (goods.getGoods_inventory() >= CommUtil.null2Int(count))
				for (StoreCart sscart : cart) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sscart.getId());
					for (int i = 0; i < gcList1.size(); i++) {
						gc = gcList1.get(i);
					Goods	good2=goodsService.selectById(gc.getGoods_id());
						GoodsCart gc1 = (GoodsCart) gc;
						if (((GoodsCart) gc).getId().toString().equals(cart_id)) {
							sscart.setTotal_price(BigDecimal.valueOf(CommUtil.add(sscart.getTotal_price(),
									Float.valueOf((CommUtil.null2Int(count) - ((GoodsCart) gc).getCount())
											* CommUtil.null2Float(good2.getCombin_price())))));
							((GoodsCart) gc).setCount(CommUtil.null2Int(count));
							gc1 = (GoodsCart) gc;
							gcList1.remove(gc);
							gcList1.add(gc1);
							goods_total_price = Double.parseDouble(gc1.getPrice().toString()) * gc1.getCount();
							this.storeCartService.updateSelectiveById(sscart);
						}
					}
				}
			else {
				error = "200";
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		Object map = new HashMap();
		((Map) map).put("count", count);
		for (StoreCart ssscart : cart) {

			if (ssscart.getStore_id().equals(CommUtil.null2Long(store_id))) {
				((Map) map).put("sc_total_price", Float.valueOf(CommUtil.null2Float(ssscart.getTotal_price())));
			}
		}
		((Map) map).put("goods_total_price", Double.valueOf(df.format(goods_total_price)));
		((Map) map).put("error", error);
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查看购物车
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SecurityMapping(display = false, rsequence = 0, title = "查看购物车", value = "/goods_cart1.htm*", rtype = "buyer", rname = "购物流程1", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/goods_cart1.htm" })
	public ModelAndView goods_cart1(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("goods_cart1.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/goods_cart1.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		List<StoreCart> cart = cart_calc(request);
		if (cart != null) {
			Store store = SecurityUserHolder.getCurrentUser().getStore_id() != null
					? storeService.selectById(SecurityUserHolder.getCurrentUser().getStore_id()) : null;
			if (store != null) {
				for (StoreCart sc : cart) {
					if (sc.getStore_id().equals(store.getId())) {
						List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
						for (GoodsCart gc : gcList1) {
							this.goodsCartService.deleteById(gc.getId());
						}
						this.storeCartService.deleteById(sc.getId());
					}
				}
			}
			request.getSession(false).setAttribute("cart", cart);
			mv.addObject("cart", cart);
			mv.addObject("goodsViewTools", this.goodsViewTools);
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			if ((shop_view_type != null) && (!shop_view_type.equals(""))
					&& (shop_view_type.equals("wap"))) {
				mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
			}
			mv.addObject("op_title", "购物车信息为空");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}

		if (this.configService.getSysConfig().getZtc_status()) {
			List ztc_goods = null;
		       Page<Goods> page = new Page<Goods>(0, 8);
	    	   String sql="where ztc_status =3 and ztc_begin_time <='"+new Date()+"' and ztc_gold>0  order by ztc_dredge_price desc";
		     List goods = this.goodsService.selectPage(page, sql, null).getRecords();

			ztc_goods = randomZtcGoods(goods);
			mv.addObject("ztc_goods", ztc_goods);
			
		}
		return mv;
	}

	private List<Goods> randomZtcGoods(List<Goods> goods) {
		Random random = new Random();
		int random_num = 0;
		int num = 0;
		if (goods.size() - 8 > 0) {
			num = goods.size() - 8;
			random_num = random.nextInt(num);
		}
		Page<Goods> page = new Page<Goods>(0, 8);
 	    String sql="where ztc_status =3 and ztc_begin_time <="+CommUtil.formatTime("yyyyMMdd", new Date())+" and ztc_gold>0  order by ztc_dredge_price desc";
 	    Page p=this.goodsService.selectPage(page, sql, null);
	    if(this.goodsService.selectPage(page, sql, null)!=null && p.getRecords()!=null && p.getRecords().size()>0 ){
	    	List ztc_goods = p.getRecords();
			Collections.shuffle(ztc_goods);
			return ztc_goods;
	    }
		return goods; 
 	    
	}

	@SecurityMapping(display = false, rsequence = 0, title = "确认购物车填写地址", value = "/goods_cart2.htm*", rtype = "buyer", rname = "购物流程2", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/goods_cart2.htm" })
	public ModelAndView goods_cart2(HttpServletRequest request, HttpServletResponse response, String store_id) {
		ModelAndView mv = new JModelAndView("goods_cart2.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/goods_cart2.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		List<StoreCart> cart = cart_calc(request);
		StoreCart sc = null;
		if (cart != null) {
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(CommUtil.null2Long(store_id))) {
					sc = sc1;
					break;
				}
			}
		}
		if (sc != null) {
			
			Address sAddress=new Address();
			sAddress.setUser_id(SecurityUserHolder.getCurrentUser().getId());
			List<Address> addrs = this.addressService.selectList(sAddress,"addTime desc");
					//"select obj from Address obj where obj.user.id=:user_id order by obj.addTime desc", params, -1, -1);
			mv.addObject("addrs", addrs);
			if ((store_id == null) || (store_id.equals(""))) {
				store_id = sc.getStore_id().toString();
			}
			String cart_session = CommUtil.randomString(32);
			request.getSession(false).setAttribute("cart_session", cart_session);
//			params.clear();
//			params.put("coupon_order_amount", sc.getTotal_price());
//			params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
//			params.put("coupon_begin_time", new Date());
//			params.put("coupon_end_time", new Date());
//			params.put("status", Integer.valueOf(0));
			CouponInfo sCouponInfo=new CouponInfo();
			
			List couponinfos = this.couponInfoService.selectList(sCouponInfo);
			//		"select obj from CouponInfo obj where obj.coupon.coupon_order_amount<=:coupon_order_amount and obj.status=:status and obj.user.id=:user_id and obj.coupon.coupon_begin_time<=:coupon_begin_time and obj.coupon.coupon_end_time>=:coupon_end_time",
			
			mv.addObject("couponinfos", couponinfos);
			mv.addObject("sc", sc);
			mv.addObject("cart_session", cart_session);
			mv.addObject("store_id", store_id);
			mv.addObject("transportTools", this.transportTools);
			mv.addObject("goodsViewTools", this.goodsViewTools);

			boolean goods_delivery = false;
			List<GoodsCart> goodCarts=goodsCartService.selectByStoreCartId(sc.getId());
			for (GoodsCart gc : goodCarts) {
				Goods goods=goodsService.selectById(gc.getGoods_id());
				if (goods.getGoods_choice_type() == 0) {
					goods_delivery = true;
					break;
				}
			}
			mv.addObject("goods_delivery", Boolean.valueOf(goods_delivery));
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			if ((shop_view_type != null) && (!shop_view_type.equals(""))
					&& (shop_view_type.equals("wap"))) {
				mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
			}
			mv.addObject("op_title", "购物车信息为空");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "完成订单提交进入支付", value = "/goods_cart3.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/goods_cart3.htm" })
	public ModelAndView goods_cart3(OrderForm of,HttpServletRequest request, HttpServletResponse response, String cart_session,
			String store_id, String addr_id, String coupon_id) throws Exception {
		ModelAndView mv = new JModelAndView("goods_cart3.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/goods_cart3.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		String cart_session1 = (String) request.getSession(false).getAttribute("cart_session");
		List<StoreCart> cart = cart_calc(request);
		
		if (cart != null) {
			if (CommUtil.null2String(cart_session1).equals(cart_session)) {
				int randNum=(int)(Math.random()*10000);
				request.getSession(false).removeAttribute("cart_session");
				of.setAddTime(new Date());
				of.setOrder_id(randNum+CommUtil.formatTime("yyyyMMddHHmmss", new Date()));
				of.setId(Long.parseLong(randNum+CommUtil.formatTime("yyyyMMddHHmmss", new Date())));
				Address addr = this.addressService.selectById(CommUtil.null2Long(addr_id));
				of.setAddr_id(addr.getId());
				of.setOrder_status(10);
				of.setUser_id(SecurityUserHolder.getCurrentUser().getId());
				of.setStore_id(CommUtil.null2Long(store_id));
				of.setTotalPrice(BigDecimal.valueOf(CommUtil.add(of.getGoods_amount(), of.getShip_price())));
				if (!CommUtil.null2String(coupon_id).equals("")) {
					CouponInfo ci = this.couponInfoService.selectById(CommUtil.null2Long(coupon_id));
					ci.setStatus(1);
					this.couponInfoService.updateSelectiveById(ci);
					Coupon coupon=couponService.selectById(ci.getCoupon_id());
					//of.setCi(ci);
					of.setCi_id(ci.getId());
					of.setTotalPrice(BigDecimal
							.valueOf(CommUtil.subtract(of.getTotalPrice(), coupon.getCoupon_amount())));
				}
				of.setOrder_type("web");
				this.orderFormService.insertSelective(of);
				GoodsCart gc;
				for (StoreCart sc : cart) {
					if (sc.getStore_id().toString().equals(store_id)) {
						List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
						for (Iterator localIterator2 = gcList1.iterator(); localIterator2.hasNext();) {
							gc = (GoodsCart) localIterator2.next();
							//gc.setOf(of);
							gc.setOf_id(of.getId());
							this.goodsCartService.updateSelectiveById(gc);
						}
						sc.setCart_session_id(null);
						sc.setUser_id(SecurityUserHolder.getCurrentUser().getId());
						sc.setSc_status(1);
						this.storeCartService.updateSelectiveById(sc);
						break;
					}
				}
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {

					for (int i = 0; i < cookies.length; i++) {
						Cookie cookie = cookies[i];
						if (cookie.getName().equals("cart_session_id")) {
							cookie.setDomain(CommUtil.generic_domain(request));
							cookie.setValue("");
							cookie.setMaxAge(0);
							response.addCookie(cookie);
						}
					}
				}
				OrderLog ofl = new OrderLog();
				ofl.setAddTime(new Date());
				ofl.setOf_id(of.getId());
				ofl.setLog_info("提交订单");
				ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
				this.orderFormLogService.insertSelective(ofl);
				mv.addObject("of", of);
				mv.addObject("paymentTools", this.paymentTools);
				
//				User sUser=new User();
//				sUser.setStore_id(storeService.selectById(order.getStore_id()).getId());
//				User storeUser=userService.selectOne(sUser);
				
				User orderUser=userService.selectById(of.getUser_id());
				
				if (this.configService.getSysConfig().getEmailEnable()) {
					send_email(request, of, orderUser.getEmail(), "email_tobuyer_order_submit_ok_notify");
				}
				if (this.configService.getSysConfig().getSmsEnbale())
					send_sms(request, of, orderUser.getMobile(), "sms_tobuyer_order_submit_ok_notify");
			} else {
				mv = new JModelAndView("error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
				if ((shop_view_type != null) && (shop_view_type.equals(""))
						&& (shop_view_type.equals("wap"))) {
					mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request, response);
				}
				mv.addObject("op_title", "订单已经失效");
				mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
			}
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			if ((shop_view_type != null) && (!shop_view_type.equals(""))
					&& (shop_view_type.equals("wap"))) {
				mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
			}
			mv.addObject("op_title", "订单信息错误");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "订单支付详情", value = "/order_pay_view.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/order_pay_view.htm" })
	public ModelAndView order_pay_view(HttpServletRequest request, HttpServletResponse response, String id) {

		ModelAndView mv = new JModelAndView("order_pay.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);

		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/order_pay.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(id));
		if (of.getOrder_status() == 10) {
			mv.addObject("of", of);
			mv.addObject("paymentTools", this.paymentTools);
			mv.addObject("url", CommUtil.getURL(request));
		} else if (of.getOrder_status() < 10) {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "该订单已经取消！");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			if ((shop_view_type != null) && (!shop_view_type.equals(""))
					&& (shop_view_type.equals("wap"))) {
				mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
			}
			mv.addObject("op_title", "该订单已经付款！");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "订单支付", value = "/order_pay.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping("/order_pay.htm")
	public ModelAndView order_pay(HttpServletRequest request, HttpServletResponse response, String payType,
			String order_id) {
		ModelAndView mv = null;
		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));
		if (of.getOrder_status() == 10) {
			if (CommUtil.null2String(payType).equals("")) {
				mv = new JModelAndView("error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
				mv.addObject("op_title", "支付方式错误！");
				mv.addObject("url", CommUtil.getURL(request) + "/index.htm");

			} else {
				List<Payment> payments = new ArrayList();
				
				if (this.configService.getSysConfig().getConfig_payment_type() == 1) {
					
					Payment sPayment=new Payment();
					sPayment.setMark(payType);
					sPayment.setType("admin");
					payments = this.paymentService.selectList(sPayment);
							//"select obj from Payment obj where obj.mark=:mark and obj.type=:type", params, -1, -1);
				} else {
					
					Payment sPayment=new Payment();
					sPayment.setMark(payType);
					sPayment.setStore_id(of.getStore_id());
					payments = this.paymentService.selectList(sPayment);
						//	"select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", params, -1,
				}
				of.setPayment_id(payments.get(0).getId());
				//of.setPayment((Payment) payments.get(0));
				this.orderFormService.updateSelectiveById(of);
				if (payType.equals("balance")) {
					mv = new JModelAndView("balance_pay.html", this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request, response);
				} else if (payType.equals("outline")) {
					mv = new JModelAndView("outline_pay.html", this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request, response);
					String pay_session = CommUtil.randomString(32);
					request.getSession(false).setAttribute("pay_session", pay_session);
					mv.addObject("paymentTools", this.paymentTools);
					mv.addObject("store_id",
							this.orderFormService.selectById(CommUtil.null2Long(order_id)).getStore_id());
					mv.addObject("pay_session", pay_session);
				} else if (payType.equals("payafter")) {
					mv = new JModelAndView("payafter_pay.html", this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request, response);
					String pay_session = CommUtil.randomString(32);
					request.getSession(false).setAttribute("pay_session", pay_session);
					mv.addObject("paymentTools", this.paymentTools);
					mv.addObject("store_id",
							this.orderFormService.selectById(CommUtil.null2Long(order_id)).getStore_id());
					mv.addObject("pay_session", pay_session);
				} else {
					mv = new JModelAndView("line_pay.html", this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request, response);
					mv.addObject("payType", payType);
					mv.addObject("url", CommUtil.getURL(request));
					mv.addObject("payTools", this.payTools);
					mv.addObject("type", "goods");
					mv.addObject("payment_id", of.getPayment_id());
				}
				mv.addObject("order_id", of.getOrder_id());
			}
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "该订单不能进行付款！");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	/**
	 * wap支付提交
	 */
	@SecurityMapping(display = false, rsequence = 0, title = "wap订单支付", value = "/wxwap_submit.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/pay_submit.htm" })
	public String paymentSubmit(HttpServletRequest request, HttpServletResponse response, String payType,
			String order_id) {

		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));

		if (of != null && of.getOrder_status() == 10) {

			List<Payment> payments = new ArrayList();
			Map params = new HashMap();
			// 1为平台支付:
			if (this.configService.getSysConfig().getConfig_payment_type() == 1) {
				Payment sPayment=new Payment();
				sPayment.setMark(payType);
				sPayment.setType("admin");
				payments = this.paymentService.selectList(sPayment);
			} else {
				Payment sPayment=new Payment();
				sPayment.setMark(payType);
				sPayment.setStore_id(of.getStore_id());
				payments = this.paymentService.selectList(sPayment);
			}
			// 支付方式已经配置:wap支持支付宝wap支付以及微信公众号支付
			if (payments.size() > 0) {

				of.setPayment_id(payments.get(0).getId());

				this.orderFormService.updateSelectiveById(of);
				// 微信公众号支付
				if (payType.equals("weixin_wap")) {
					
					String APPID = payments.get(0).getWeixin_appId();
					String siteURL = CommUtil.getURL(request);
					String out_trade_no = of.getId().toString();

					return "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + APPID
							+ "&redirect_uri=" + siteURL + "/wechat/oauthCode.htm?sn=" + out_trade_no
							+ "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";

				} else if (payType.equals("alipay_wap")) {

					// ////////////////////////////////////////////////////////////////////////////////
					String siteURL = CommUtil.getURL(request);
					AlipayConfig config = new AlipayConfig();

					config.setSeller_email(payments.get(0).getSeller_email());
					config.setKey(payments.get(0).getSafeKey());
					config.setPartner(payments.get(0).getPartner());
					config.setSign_type("RSA");
					// 把请求参数打包成数组
					Map<String, String> sParaTemp = new HashMap<String, String>();
					// 调用的接口名，无需修改
					sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
					// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
					sParaTemp.put("partner", payments.get(0).getPartner());
					// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
					sParaTemp.put("seller_id", payments.get(0).getPartner());
					sParaTemp.put("_input_charset", config.getInput_charset());
					// 支付类型 ，无需修改
					sParaTemp.put("payment_type", "1");

					sParaTemp.put("notify_url", siteURL + "/alipay/alipay_notify.htm");
					sParaTemp.put("return_url", siteURL + "/alipay/alipay_retrun.htm");
					sParaTemp.put("out_trade_no", of.getId().toString());
					//sParaTemp.put("subject", "订单号为" + of.getOrder_id());
					sParaTemp.put("subject", "甜园云家-");
					// 价格测试改为1分钱
					// sParaTemp.put("total_fee", "0.01");
					sParaTemp.put("total_fee", of.getTotalPrice().toPlainString());
					sParaTemp.put("show_url", "/index.htm");
					sParaTemp.put("body", "支付宝wap支付");
					// 其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1

					String sHtmlText = AlipaySubmit.buildRequestWap(config, sParaTemp, "get", "确定");

					try {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("text/html");
						response.getWriter().print(sHtmlText);
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else if (payType.equals("yikatong") || payType.equals("ka3800ka")) {
					String shop_view_type = CommUtil
							.null2String(request.getSession().getAttribute("rt.shop_view_type"));

					String siteURL = CommUtil.getURL(request);
					YiKatongConfig config = new YiKatongConfig();

					config.setSeller_email(payments.get(0).getSeller_email());
					config.setKey(payments.get(0).getSafeKey());
					config.setPartner(payments.get(0).getPartner());

					config.setSign_type("MD5");
					// 把请求参数打包成数组
					SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
					YiKaTongPay yktp = new YiKaTongPay();
					yktp.setID(null);
					yktp.setShopIds(String.valueOf(of.getStore_id()));// 商铺号
					// yktp.setMerchantId(of.getPayment().getSeller_email());//
					// 商户号
					yktp.setMerchantId(payments.get(0).getPartner());// 商户号
//					yktp.setOrderNos(String.valueOf(of.getId()));// 订单号
					yktp.setOrderNos(String.valueOf(of.getOrder_id()));// 订单号

					yktp.setWIDsubject(of.getOrder_id()+"的订单");// 订单名
					yktp.setOrderAmounts((long) ((of.getTotalPrice().doubleValue()) * 100));// 付款金额
					yktp.setOrderAmount((long) ((of.getTotalPrice().doubleValue()) * 100));// 付款合计金额
					String date1 = new SimpleDateFormat("yyyyMMddhhmmss").format(of.getAddTime());
					yktp.setOrderDatetime(date1);// 订单日期

//					String telephone = of.getAddr().getTelephone();
//					String mobile = of.getAddr().getMobile();
//					telephone = (telephone == null || telephone.isEmpty()) ? "" : telephone;
//					mobile = (mobile == null || mobile.isEmpty()) ? telephone : mobile;
//					yktp.setPayerTelephone(mobile);// 客户电话

					yktp.setOrderCurrency("156");
					// sParaTemp.put("service", "create_direct_pay_by_user");
					try {
						// 1
						// sParaTemp1.put("shopIds", yktp.getShopIds());
						sParaTemp.put("shopIds", new String(yktp.getMerchantId().getBytes("ISO-8859-1"), "UTF-8"));
						// 2
						// sParaTemp1.put("receiveUrl", url);
						String returnUrl = payType.equals("ka3800ka") ? "/ka3800ka_return.htm" : "/yikatong_return.htm";
						sParaTemp.put("receiveUrl", new String((siteURL + returnUrl).getBytes("ISO-8859-1"), "UTF-8"));
						// 3
						// sParaTemp1.put("orderNos", yktp.getOrderNos());
						sParaTemp.put("orderNos", new String(yktp.getOrderNos().getBytes("ISO-8859-1"), "UTF-8"));
						// 4
						// sParaTemp1.put("orderAmounts",
						// String.valueOf(yktp.getOrderAmounts()));
						sParaTemp.put("orderAmounts",
								new String(String.valueOf(yktp.getOrderAmounts()).getBytes("ISO-8859-1"), "UTF-8"));
						// 5
						// sParaTemp1.put("payerTelephone",
						// yktp.getPayerTelephone());
						sParaTemp.put("payerTelephone",
								new String(yktp.getPayerTelephone().getBytes("ISO-8859-1"), "UTF-8"));
						// 6
						// sParaTemp1.put("orderCurrency",
						// yktp.getOrderCurrency());
						sParaTemp.put("orderCurrency",
								new String(yktp.getOrderCurrency().getBytes("ISO-8859-1"), "UTF-8"));

						// 7
						// sParaTemp1.put("orderDatetime",
						// yktp.getOrderDatetime());
						sParaTemp.put("orderDatetime",
								new String(yktp.getOrderDatetime().getBytes("ISO-8859-1"), "UTF-8"));

						// 8
						// sParaTemp1.put("orderAmount",
						// String.valueOf(yktp.getOrderAmount()));
						sParaTemp.put("orderAmount",
								new String(String.valueOf(yktp.getOrderAmount()).getBytes("ISO-8859-1"), "UTF-8"));
						sParaTemp.put("merchantId", new String(yktp.getMerchantId().getBytes("ISO-8859-1"), "UTF-8"));

					} catch (Exception e) {
						
						e.printStackTrace();
					}
					String url3800Url = payType.equals("ka3800ka") ? "/ka3800ka" : "/yikatong";
					if ((shop_view_type != null) && (!shop_view_type.equals(""))
							&& (shop_view_type.equals("wap"))) {

					} else {

					}

					config.setNotify_url(siteURL + url3800Url + "_notify.htm");
					config.setReturn_url(siteURL + url3800Url + "_return.htm");
					String result = payType.equals("ka3800ka")
							? YiKatongService.create_direct_pay_by_user_3800(config, sParaTemp)
							: YiKatongService.create_direct_pay_by_user(config, sParaTemp);
					try {
						response.setCharacterEncoding("UTF-8");
						response.setContentType("text/html");
						response.getWriter().print(result);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					// 支付方式错误
					return "redirect:" + CommUtil.getURL(request) + "/index.htm?payMethodError";
				}

			} else {
				// 支付方式未配置
				return "redirect:" + CommUtil.getURL(request) + "/index.htm?noPayMethod";
			}

		} else {
			// 该订单状态不正确，不能进行付款！
			return "redirect:" + CommUtil.getURL(request) + "/index.htm?orderError";
		}
		return null;
	}

	/**
	 * 微信CODE回调JSP并进行微信授权接口认证获取用户openid
	 */
	@RequestMapping({ "/wechat/oauthCode.htm" })
	public ModelAndView oauthCode(HttpServletRequest request, HttpServletResponse response) {
		logger.info("支付收到微信code回调请求");
		ModelAndView mv = new JModelAndView("wap/wxpay.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String sn = request.getParameter("sn");
		HttpSession session = request.getSession();
		String scode = (String) session.getAttribute("wxcode");

		if (code != null && code.equalsIgnoreCase(scode)) {

		} else {
			session.setAttribute("wxcode", code);
		}
		String openId = null;
		// 用户同意授权
		if (null != code && !"".equals(code) && !"authdeny".equals(code)) {

			OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(sn));
			Payment payment=paymentService.selectById(of.getPayment_id());
			
			// 获取网页授权access_token
			WxOauth2Token wxOauth2Token = WxAdvancedUtil.getOauth2AccessToken(payment.getWeixin_appId(),
					payment.getWeixin_appSecret(), code);
			// 用户标识
			if (null != wxOauth2Token) {
				openId = wxOauth2Token.getOpenId();
			}
			logger.info("微信code回调请求:openId={},sn={}", openId, sn);

			String prodName = "网上购物";
			String amount = of.getTotalPrice().toString();

			mv.addObject("openId", openId);
			mv.addObject("sn", sn);
			mv.addObject("amount", amount);
			mv.addObject("siteName", this.configService.getSysConfig().getWebsiteName());
			mv.addObject("productName", prodName);

		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "用户未授权！");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm?authdeny");
		}
		return mv;
	}

	/**
	 * 生成微信订单数据以及微信支付需要的签名等信息，传输到前端，发起调用JSAPI支付 作者: YUKE 日期：2016年1月14日
	 * 上午10:39:49
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/wechat/wxpay.htm" })
	public void wxpay(HttpServletRequest request, HttpServletResponse response, String openId, String sn,
			String productName, String totalPrice, String clientUrl) throws Exception {

		String APPID = null;
		String APP_SECRET = null;
		String MCH_ID = null;
		String API_KEY = null;
		String siteURL = CommUtil.getURL(request);
		String UNI_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

		logger.info("微信确认支付获取openId={},sn={}" + openId, sn);

		OrderForm of = null;
		String amount = null;
		try {
			of = this.orderFormService.selectById(CommUtil.null2Long(sn));
		} catch (Exception e) {
			logger.error("微信确认支付查询paymentLog异常=" + e.getMessage());
			e.printStackTrace();
		}
		if (of == null) {
			amount = "";
			logger.info("微信确认支付查询orderForm=null");
		} else {
			Payment payment=paymentService.selectById(of.getPayment_id());
			APPID = payment.getWeixin_appId();
			APP_SECRET = payment.getWeixin_appSecret();
			MCH_ID = payment.getWeixin_partnerId();
			API_KEY = payment.getWeixin_paySignKey();

			/** 将元转换为分 */
			amount = of.getTotalPrice().multiply(new BigDecimal(100)).setScale(0).toString();
			logger.info("微信确认支付元转分成功amount={}", amount);
		}

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", APPID);
		parameters.put("mch_id", MCH_ID);
		parameters.put("nonce_str", WxCommonUtil.createNoncestr());
		parameters.put("body", productName);// 商品名称

		/** 订单号 */
		parameters.put("out_trade_no", sn);
		/** 订单金额以分为单位，只能为整数 */
		// parameters.put("total_fee", "1");//测试用的金额1分钱
		parameters.put("total_fee", amount);
		/** 客户端本地ip */
		parameters.put("spbill_create_ip", request.getRemoteAddr());
		/** 支付回调地址 */
		parameters.put("notify_url", siteURL + "/wechat/paynotify.htm");
		/** 支付方式为JSAPI支付 */
		parameters.put("trade_type", "JSAPI");
		/** 用户微信的openid，当trade_type为JSAPI的时候，该属性字段必须设置 */
		parameters.put("openid", openId);

		/** 使用MD5进行签名，编码必须为UTF-8 */
		String sign = WxCommonUtil.createSignMD5("UTF-8", parameters, API_KEY);

		/** 将签名结果加入到map中，用于生成xml格式的字符串 */
		parameters.put("sign", sign);

		/** 生成xml结构的数据，用于统一下单请求的xml请求数据 */
		String requestXML = WxCommonUtil.getRequestXml(parameters);
		logger.info("请求统一支付requestXML：" + requestXML);

		try {
			/** 1、使用POST请求统一下单接口，获取预支付单号prepay_id */
			String result = WxCommonUtil.httpsRequestString(UNI_URL, "POST", requestXML);
			logger.info("请求统一支付result:" + result);
			// 解析微信返回的信息，以Map形式存储便于取值
			Map<String, String> map = WxCommonUtil.doXMLParse(result);
			logger.info("预支付单号prepay_id为:" + map.get("prepay_id"));
			// 全局map，该map存放前端ajax请求的返回值信息，包括wx.config中的配置参数值，也包括wx.chooseWXPay中的配置参数值
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("appId", APPID);
			params.put("timeStamp", new Date().getTime() + ""); // 时间戳
			params.put("nonceStr", WxCommonUtil.createNoncestr()); // 随机字符串
			params.put("package", "prepay_id=" + map.get("prepay_id")); // 格式必须为
																		// prepay_id=***
			params.put("signType", "MD5"); // 签名的方式必须是MD5
			/**
			 * 获取预支付prepay_id后，需要再次签名，此次签名是用于前端js中的wx.chooseWXPay中的paySign。
			 * 参与签名的参数有5个，分别是：appId、timeStamp、nonceStr、package、signType
			 * 注意参数名称的大小写
			 */
			String paySign = WxCommonUtil.createSignMD5("UTF-8", params, API_KEY);
			// 预支付单号
			params.put("packageValue", "prepay_id=" + map.get("prepay_id"));
			params.put("paySign", paySign); // 支付签名
			// 付款成功后同步请求的URL，请求我们自定义的支付成功的页面，展示给用户
			params.put("sendUrl", siteURL + "/wechat/paysuccess.htm?totalPrice=" + totalPrice);

			// 获取用户的微信客户端版本号，用于前端支付之前进行版本判断，微信版本低于5.0无法使用微信支付
			String userAgent = request.getHeader("user-agent");
			char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
			params.put("agent", new String(new char[] { agent }));

			/**
			 * 2、获取access_token作为参数传递,由于access_token有有效期限制，和调用次数限制，
			 * 可以缓存到session或者数据库中.有效期设置为小于7200秒
			 */
			WxToken wxtoken = WxCommonUtil.getToken(APPID, APP_SECRET);
			String token = wxtoken.getAccessToken();
			logger.info("获取的token值为:" + token);

			/** 3、获取凭证ticket发起GET请求 */
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + token;
			logger.info("接口调用凭证ticket的requestUrl：" + requestUrl);

			String ticktresult = WxCommonUtil.httpsRequestString(requestUrl, "GET", null);
			JSONObject jsonresult = JSONObject.fromObject(ticktresult);
			// JSONObject jsonObject = WxCommonUtil.httpsRequest(requestUrl,
			// "GET", null);
			// 使用JSSDK支付，需要另一个凭证，也就是jsapi_ticket。这个是JSSDK中使用到的。
			String jsapi_ticket = jsonresult.getString("ticket");
			logger.info("jsapi_ticket：" + jsapi_ticket);
			// 获取到ticket凭证之后，需要进行一次签名
			String config_nonceStr = WxCommonUtil.createNoncestr();// 获取随机字符串
			long config_timestamp = new Date().getTime();// 时间戳
			// 加入签名的参数有4个，分别是： noncestr、jsapi_ticket、timestamp、url，注意字母全部为小写
			SortedMap<Object, Object> configMap = new TreeMap<Object, Object>();
			configMap.put("noncestr", config_nonceStr);
			configMap.put("jsapi_ticket", jsapi_ticket);
			configMap.put("timestamp", config_timestamp + "");
			configMap.put("url", clientUrl);
			// 该签名是用于前端js中wx.config配置中的signature值。
			String config_sign = WxCommonUtil.createSignSHA1("UTF-8", configMap);
			// 将config_nonceStr、jsapi_ticket
			// 、config_timestamp、config_sign一同传递到前端
			// 这几个参数名称和上面获取预支付prepay_id使用的参数名称是不一样的，不要混淆了。
			// 这几个参数是提供给前端js代码在调用wx.config中进行配置的参数，wx.config里面的signature值就是这个config_sign的值，以此类推
			params.put("config_nonceStr", config_nonceStr);
			params.put("config_timestamp", config_timestamp + "");
			params.put("config_sign", config_sign);
			// 将map转换为json字符串，传递给前端ajax回调
			String json = JSONArray.fromObject(params).toString();
			logger.info("用于wx.config配置的json：" + json);

			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");

			try {
				PrintWriter writer = response.getWriter();
				writer.print(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 微信扫码支付
	 * 
	 * @param request
	 * @param response
	 * @param order_id
	 * @return
	 */
	@RequestMapping({ "/wechat/wxcodepay.htm" })
	public void wxcodepay(HttpServletRequest request, HttpServletResponse response, String order_id) {

		String UNI_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));
		String returnhtml = null;
		if (of.getOrder_status() == 10) {

			List payments = new ArrayList();
			
			// 判断是否平台支付
			if (this.configService.getSysConfig().getConfig_payment_type() == 1) {
				
				Payment sPayment=new Payment();
				sPayment.setMark("wxcodepay");
				sPayment.setType("admin");
				payments = this.paymentService.selectList(sPayment);
						//.query("select obj from Payment obj where obj.mark=:mark and obj.type=:type", params, -1, -1);
			} else {
				
				Payment sPayment=new Payment();
				sPayment.setMark("wxcodepay");
				sPayment.setStore_id(of.getStore_id());
				payments = this.paymentService.selectList(sPayment);
			}
			Payment payment = (Payment) payments.get(0);
			of.setPayment_id(payment.getId());
			this.orderFormService.updateSelectiveById(of);

			String codeUrl = "";// 微信返回的二维码地址信息

			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", payment.getWeixin_appId());// 公众账号id
			parameters.put("mch_id", payment.getWeixin_partnerId());// 商户号
			parameters.put("nonce_str", WxCommonUtil.createNoncestr());// 随机字符串
			parameters.put("body", "在线购物");// 商品描述
			parameters.put("out_trade_no", order_id);// 商户订单号
			parameters.put("total_fee", of.getTotalPrice().multiply(new BigDecimal(100)).setScale(0).toString());// 总金额
			// parameters.put("total_fee", "1");
			parameters.put("spbill_create_ip", WxCommonUtil.localIp());// 终端IP.Native支付填调用微信支付API的机器IP。
			// 支付成功后回调的action，与JSAPI相同
			// parameters.put("notify_url", basePath + NOTIFY_URL);//
			// 支付成功后回调的action
			parameters.put("notify_url", CommUtil.getURL(request) + "/wechat/paynotify.htm");// 支付成功后回调的action，与JSAPI相同
			parameters.put("trade_type", "NATIVE");// 交易类型
			parameters.put("product_id", order_id);// 商品ID。商品号要唯一,trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义
			// String sign = WxPayUtil.createSign2("UTF-8", parameters,
			// API_KEY);
			String sign = WxCommonUtil.createSignMD5("UTF-8", parameters, payment.getWeixin_paySignKey());
			parameters.put("sign", sign);// 签名
			String requestXML = WxCommonUtil.getRequestXml(parameters);
			logger.info("requestXML" + requestXML);
			String result = WxCommonUtil.httpsRequestString(UNI_URL, "POST", requestXML);// WxCommonUtil.httpsRequest(WxConstants.UNIFIED_ORDER_URL,
																							// "POST",
																							// requestXML);
			// System.out.println(" 微信支付二维码生成" + result);
			Map<String, String> map = new HashMap<String, String>();
			try {
				map = WxCommonUtil.doXMLParse(result);
				logger.info("------------------code_url=" + map.get("code_url") + ";      result_code="
						+ map.get("code_url") + "------------------------------");
			} catch (Exception e) {
				logger.error("doXMLParse()--error", e);
			}
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");

			if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
				codeUrl = map.get("code_url");
				// 拿到codeUrl，生成二维码图片
				byte[] imgs = QRCodeEncoderHandler.createQRCode(codeUrl);

				String urls = request.getRealPath("/") + this.configService.getSysConfig().getUploadFilePath()
						+ java.io.File.separator + "weixin_qr" + java.io.File.separator + "wxpay"
						+ java.io.File.separator;
				// 图片的实际路径
				String imgfile = urls + order_id + ".png";

				QRCodeEncoderHandler.saveImage(imgs, imgfile, "png");

				// 图片的网路路径
				String imgUrl = CommUtil.getURL(request) + "/" + this.configService.getSysConfig().getUploadFilePath()
						+ "/weixin_qr/wxpay/" + order_id + ".png";

				logger.info("图片的网路路径imgurl={}", imgUrl);

				returnhtml = "<img src='" + imgUrl + "' style='width:200px;height:200px;'/>";

			} else {
				returnhtml = "支付状态不正确";
			}
		}
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(returnhtml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return returnhtml;
	}

	// 订单线下一卡通支付
	@SecurityMapping(display = false, rsequence = 0, title = "甜园云卡线下支付", value = "/order_pay_payafter1.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping("/order_pay_payafter.htm1")
	public ModelAndView order_pay_payafter(HttpServletRequest request, HttpServletResponse response, String order_id,
			String pay_msg, String pay_session) throws Exception {
		ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String pay_session1 = CommUtil.null2String(request.getSession(false).getAttribute("pay_session"));
		if (pay_session1.equals(pay_session)) {
			OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));

		}
		return null;

	}

	@SecurityMapping(display = false, rsequence = 0, title = "订单线下支付", value = "/order_pay_outline.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/order_pay_outline.htm" })
	public ModelAndView order_pay_outline(HttpServletRequest request, HttpServletResponse response, String payType,
			String order_id, String pay_msg, String pay_session) throws Exception {
		ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String pay_session1 = CommUtil.null2String(request.getSession(false).getAttribute("pay_session"));
		if (pay_session1.equals(pay_session)) {
			OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));
			of.setPay_msg(pay_msg);
			
			
			Payment sPayment=new Payment();
			sPayment.setMark("outline");
			sPayment.setStore_id(of.getStore_id());
			List<Payment>	payments = this.paymentService.selectList(sPayment);
//			List payments = this.paymentService.query(
//					"select obj from Payment obj where obj.mark=:mark and obj.store.id=:store_id", params, -1, -1);
			if (payments.size() > 0) {
				of.setPayment_id( payments.get(0).getId());
				of.setPayTime(new Date());
			}
			of.setOrder_status(15);
			User orderUser=userService.selectById(of.getUser_id());
			this.orderFormService.updateSelectiveById(of);
			if (this.configService.getSysConfig().getSmsEnbale()) {
				send_sms(request, of, orderUser.getMobile(), "sms_toseller_outline_pay_ok_notify");
			}
			if (this.configService.getSysConfig().getEmailEnable()) {
				send_email(request, of, orderUser.getEmail(), "email_toseller_outline_pay_ok_notify");
			}

			OrderLog ofl = new OrderLog();
			ofl.setAddTime(new Date());
			ofl.setLog_info("提交线下支付申请");
			ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
			ofl.setOf_id(of.getId());
			this.orderFormLogService.insertSelective(ofl);
			request.getSession(false).removeAttribute("pay_session");
			mv.addObject("op_title", "线下支付提交成功，等待卖家审核！");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "订单已经支付，禁止重复支付！");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "订单货到付款", value = "/order_pay_payafter.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/order_pay_payafter.htm" })
	public ModelAndView order_pay_payafter(HttpServletRequest request, HttpServletResponse response, String payType,
			String order_id, String pay_msg, String pay_session) throws Exception {
		ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String pay_session1 = CommUtil.null2String(request.getSession(false).getAttribute("pay_session"));
		if (pay_session1.equals(pay_session)) {
			OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));
			of.setPay_msg(pay_msg);
			
			Payment sPayment=new Payment();
			sPayment.setMark("payafter");
			sPayment.setStore_id(of.getStore_id());
			List<Payment>	payments = this.paymentService.selectList(sPayment);
			
			if (payments.size() > 0) {
				of.setPayment_id(payments.get(0).getId());
				of.setPayTime(new Date());
			}
			of.setOrder_status(16);
			User orderUser=userService.selectById(of.getUser_id());
			this.orderFormService.updateSelectiveById(of);
			if (this.configService.getSysConfig().getSmsEnbale()) {
				send_sms(request, of, orderUser.getMobile(), "sms_toseller_payafter_pay_ok_notify");
			}
			if (this.configService.getSysConfig().getEmailEnable()) {
				send_email(request, of, orderUser.getEmail(), "email_toseller_payafter_pay_ok_notify");
			}

			OrderLog ofl = new OrderLog();
			ofl.setAddTime(new Date());
			ofl.setLog_info("提交货到付款申请");
			ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
			ofl.setOf_id(of.getId());
			this.orderFormLogService.insertSelective(ofl);
			request.getSession(false).removeAttribute("pay_session");
			mv.addObject("op_title", "货到付款提交成功，等待卖家发货！");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "订单已经支付，禁止重复支付！");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "订单预付款支付", value = "/order_pay_balance.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/order_pay_balance.htm" })
	public ModelAndView order_pay_balance(HttpServletRequest request, HttpServletResponse response, String payType,
			String order_id, String pay_msg) throws Exception {
		ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(order_id));
		User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());

		if (CommUtil.null2Double(user.getAvailableBalance()) > CommUtil.null2Double(of.getTotalPrice())) {
			of.setPay_msg(pay_msg);
			of.setOrder_status(20);
			
			Payment sPayment=new Payment();
			sPayment.setMark("balance");
			sPayment.setStore_id(of.getStore_id());
			List<Payment>	payments = this.paymentService.selectList(sPayment);
			
			if (payments.size() > 0) {
				of.setPayment_id(payments.get(0).getId());
				of.setPayTime(new Date());
			}
			User orderUser=userService.selectById(of.getUser_id());
			boolean ret = this.orderFormService.updateSelectiveById(of);
			if (this.configService.getSysConfig().getEmailEnable()) {
				send_email(request, of, orderUser.getEmail(), "email_toseller_balance_pay_ok_notify");
				send_email(request, of, orderUser.getEmail(), "email_tobuyer_balance_pay_ok_notify");
			}
			if (this.configService.getSysConfig().getSmsEnbale()) {
				send_sms(request, of, orderUser.getMobile(), "sms_toseller_balance_pay_ok_notify");
				send_sms(request, of, orderUser.getMobile(), "sms_tobuyer_balance_pay_ok_notify");
			}
			if (ret) {
				user.setAvailableBalance(
						BigDecimal.valueOf(CommUtil.subtract(user.getAvailableBalance(), of.getTotalPrice())));
				user.setFreezeBlance(BigDecimal.valueOf(CommUtil.add(user.getFreezeBlance(), of.getTotalPrice())));
				this.userService.updateSelectiveById(user);
				PredepositLog log = new PredepositLog();
				log.setAddTime(new Date());
				log.setPd_log_user_id(user.getId());
				log.setPd_op_type("消费");
				log.setPd_log_amount(BigDecimal.valueOf(-CommUtil.null2Double(of.getTotalPrice())));
				log.setPd_log_info("订单" + of.getOrder_id() + "购物减少可用预存款");
				log.setPd_type("可用预存款");
				this.predepositLogService.insertSelective(log);
				GoodsCart sGoodsCart=new GoodsCart();
				sGoodsCart.setOf_id(of.getId());
				List<GoodsCart> gcsList=goodsCartService.selectList(sGoodsCart);
				for (GoodsCart gc : gcsList) {
				
					Goods goods = goodsService.selectById(gc.getGoods_id());
					
					if ((goods.getGroup_id() != null) && (goods.getGroup_buy() == 2)) {
						GroupGoods sGroup=new GroupGoods();
						sGroup.setGg_goods_id(goods.getId());
						List<GroupGoods> groupList=groupGoodsService.selectList(sGroup);
						for (GroupGoods gg : groupList) {
							
							if (gg.getGroup_id().equals(goods.getGroup_id())) {
								gg.setGg_count(gg.getGg_count() - gc.getCount());
								gg.setGg_def_count(gg.getGg_def_count() + gc.getCount());
								this.groupGoodsService.updateSelectiveById(gg);
							}
						}
					}
					List gsps = new ArrayList();
					CartGsp cg=new CartGsp();
					cg.setCart_id((gc).getId());
					List<GoodsSpecProperty> gspList=goodsSpecPropertyService.selectGspByGcId(cg);
					for (GoodsSpecProperty gsp : gspList) {
						gsps.add(gsp.getId().toString());
					}
					String[] gsp_list = new String[gsps.size()];
					gsps.toArray(gsp_list);
					goods.setGoods_salenum(goods.getGoods_salenum() + gc.getCount());
					Map temp;
					if (goods.getInventory_type().equals("all")) {
						goods.setGoods_inventory(goods.getGoods_inventory() - gc.getCount());
					} else {
						List list = (List) Json.fromJson(ArrayList.class, goods.getGoods_inventory_detail());
						for (Iterator localIterator4 = list.iterator(); localIterator4.hasNext();) {
							temp = (Map) localIterator4.next();
							String[] temp_ids = CommUtil.null2String(temp.get("id")).split("_");
							Arrays.sort(temp_ids);
							Arrays.sort(gsp_list);
							if (Arrays.equals(temp_ids, gsp_list)) {
								temp.put("count",
										Integer.valueOf(CommUtil.null2Int(temp.get("count")) - gc.getCount()));
							}
						}
						goods.setGoods_inventory_detail(Json.toJson(list, JsonFormat.compact()));
					}
					GroupGoods sGroup=new GroupGoods();
					sGroup.setGg_goods_id(goods.getId());
					List<GroupGoods> groupList=groupGoodsService.selectList(sGroup);
					
					for (GroupGoods gg : groupList) {
						if ((!gg.getGroup_id().equals(goods.getGroup_id())) || (gg.getGg_count() != 0))
							continue;
						goods.setGroup_buy(3);
					}

					this.goodsService.updateSelectiveById(goods);
				}

			}

			OrderLog ofl = new OrderLog();
			ofl.setAddTime(new Date());
			ofl.setLog_info("预付款支付");
			ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
			ofl.setOf_id(of.getId());
			this.orderFormLogService.insertSelective(ofl);
			mv.addObject("op_title", "预付款支付成功！");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "可用余额不足，支付失败！");
			mv.addObject("url", CommUtil.getURL(request) + "/buyer/order.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "订单支付结果", value = "/order_finish.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/order_finish.htm" })
	public ModelAndView order_finish(HttpServletRequest request, HttpServletResponse response, String order_id) {
		ModelAndView mv = new JModelAndView("order_finish.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/order_finish.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		OrderForm obj = this.orderFormService.selectById(CommUtil.null2Long(order_id));
		mv.addObject("obj", obj);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "地址新增", value = "/cart_address.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/cart_address.htm" })
	public ModelAndView cart_address(HttpServletRequest request, HttpServletResponse response, String id,
			String store_id) {

		ModelAndView mv = new JModelAndView("cart_address.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/cart_address.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		
		List<Area> areas = this.areaService.selectList("where parent_id is null",null);
			//	.query("select obj from Area obj where obj.parent.id is null", null, -1, -1);
		mv.addObject("areas", areas);
		mv.addObject("store_id", store_id);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "购物车中收货地址保存", value = "/cart_address_save.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/cart_address_save.htm" })
	public String cart_address_save(Address address,HttpServletRequest request, HttpServletResponse response, String id, String area_id,
			String store_id) {
		if (id.equals("")) {
			address.setAddTime(new Date());
		} else {
			Address obj = this.addressService.selectById(Long.valueOf(Long.parseLong(id)));
		}
		address.setUser_id(SecurityUserHolder.getCurrentUser().getId());
		Area area = this.areaService.selectById(CommUtil.null2Long(area_id));
		address.setArea_id(area.getId());
		if (id.equals(""))
			this.addressService.insertSelective(address);
		else
			this.addressService.updateSelectiveById(address);
		return "redirect:goods_cart2.htm?store_id=" + store_id;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "地址切换", value = "/order_address.htm*", rtype = "buyer", rname = "购物流程3", rcode = "goods_cart", rgroup = "在线购物")
	@RequestMapping({ "/order_address.htm" })
	public void order_address(HttpServletRequest request, HttpServletResponse response, String addr_id,
			String store_id) {
		List<StoreCart> cart = (List) request.getSession(false).getAttribute("cart");
		StoreCart sc = null;
		if (cart != null) {
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(CommUtil.null2Long(store_id))) {
					sc = sc1;
					break;
				}
			}
		}
		Address addr = this.addressService.selectById(CommUtil.null2Long(addr_id));
		List sms = this.transportTools.query_cart_trans(sc, CommUtil.null2String(addr.getArea_id()));

		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(sms, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SecurityMapping(display = false, rsequence = 0, title = "收货地址列表", value = "/address.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping({ "/address.htm" })
	public ModelAndView address(HttpServletRequest request, HttpServletResponse response, String currentPage,
			String orderBy, String orderType, String store_id) {
		ModelAndView mv = new JModelAndView("address.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/address.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		AddressQueryObject qo = new AddressQueryObject(currentPage, mv, orderBy, orderType);
		qo.addQuery("obj.user.id", new SysMap("user_id", SecurityUserHolder.getCurrentUser().getId()), "=");
		 Page pList = this.addressService.selectPage(new Page<Address>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
		 
		CommWebUtil.saveIPageList2ModelAndView(url + "/address.htm", "", params, pList, mv);
	
		List areas = this.areaService.selectList("where parent_id is null",null);
			//	.query("select obj from Area obj where obj.parent.id is null", null, -1, -1);
		mv.addObject("areas", areas);
		mv.addObject("store_id", store_id);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "修改收货地址", value = "/address_edit.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping({ "/address_edit.htm" })
	public ModelAndView address_edit(HttpServletRequest request, HttpServletResponse response, String id,
			String currentPage, String store_id) {

		ModelAndView mv = new JModelAndView("cart_address.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shop_view_type = CommUtil.null2String(request.getSession().getAttribute("rt.shop_view_type"));
		if ((shop_view_type != null) && (!shop_view_type.equals("")) && (shop_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/cart_address.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		
		List areas = this.areaService.selectList("where parent_id is null",null);
		Address obj = this.addressService.selectById(CommUtil.null2Long(id));
		mv.addObject("obj", obj);
		mv.addObject("areas", areas);
		mv.addObject("store_id", store_id);
		mv.addObject("currentPage", currentPage);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "收货地址删除", value = "/address_del.htm*", rtype = "buyer", rname = "用户中心", rcode = "user_center", rgroup = "用户中心")
	@RequestMapping({ "/address_del.htm" })
	public String address_del(HttpServletRequest request, HttpServletResponse response, String mulitId,
			String currentPage, String store_id) {
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Address address = this.addressService.selectById(Long.valueOf(Long.parseLong(id)));
				this.addressService.deleteById(Long.valueOf(Long.parseLong(id)));
			}
		}
		return "redirect:goods_cart2.htm?store_id=" + store_id;
	}

	private void send_email(HttpServletRequest request, OrderForm order, String email, String mark) throws Exception {
		Template sTemplate=new Template();
		sTemplate.setMark(mark);
		Template template = this.templateService.selectOne(sTemplate);
		if ((template != null) && (template.getOpen())) {
			String subject = template.getTitle();
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + "vm"
					+ File.separator;
			if (!CommUtil.fileExist(path)) {
				CommUtil.createFolder(path);
			}
			PrintWriter pwrite = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
			pwrite.print(template.getContent());
			pwrite.flush();
			pwrite.close();
			User sUser=new User();
			sUser.setStore_id(storeService.selectById(order.getStore_id()).getId());
			User storeUser=userService.selectOne(sUser);
			
			User orderUser=userService.selectById(order.getUser_id());
			Properties p = new Properties();
			p.setProperty("file.resource.loader.path", request.getRealPath("/") + "vm" + File.separator);
			p.setProperty("input.encoding", "UTF-8");
			p.setProperty("output.encoding", "UTF-8");
			Velocity.init(p);
			org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("buyer", orderUser);
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

	private void send_sms(HttpServletRequest request, OrderForm order, String mobile, String mark) throws Exception {
		Template sTemplate=new Template();
		sTemplate.setMark(mark);
		Template template = this.templateService.selectOne(sTemplate);
		if ((template != null) && (template.getOpen())) {
			String path = request.getSession().getServletContext().getRealPath("") + File.separator + "vm"
					+ File.separator;
			if (!CommUtil.fileExist(path)) {
				CommUtil.createFolder(path);
			}
			PrintWriter pwrite = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
			pwrite.print(template.getContent());
			pwrite.flush();
			pwrite.close();

			Properties p = new Properties();
			p.setProperty("file.resource.loader.path", request.getRealPath("/") + "vm" + File.separator);
			p.setProperty("input.encoding", "UTF-8");
			p.setProperty("output.encoding", "UTF-8");
			Velocity.init(p);
			org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", "UTF-8");
			VelocityContext context = new VelocityContext();
			User sUser=new User();
			sUser.setStore_id(storeService.selectById(order.getStore_id()).getId());
			User storeUser=userService.selectOne(sUser);
			
			User orderUser=userService.selectById(order.getUser_id());
			context.put("buyer", orderUser);
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

	@RequestMapping("/send_status.htm")
	public void sendStatus(HttpServletRequest request, HttpServletResponse response, String store_id, String cart_id,
			String status) {

		Map map = new HashMap();

		GoodsCart gc = goodsCartService.selectById(Long.parseLong(cart_id));

		gc.setStatus(Integer.parseInt(status));

		goodsCartService.updateSelectiveById(gc);

		List<StoreCart> cart = cart_calc(request);

		for (StoreCart storeCart : cart) {

			if (storeCart.getStore_id().equals(Long.parseLong(store_id))) {
				double totalPrice = 0.0D;
				storeCart.setTotal_price(BigDecimal.valueOf(totalPrice));
				List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(storeCart.getId());
				for (GoodsCart goodsCart : gcList1) {
					if (goodsCart.getStatus() == 1) {
						totalPrice += Double.valueOf(goodsCart.getCount())
								* Double.valueOf(goodsCart.getPrice().toPlainString());

					}
				}
				storeCart.setTotal_price(BigDecimal.valueOf(totalPrice));
				storeCartService.updateSelectiveById(storeCart);
				DecimalFormat df = new DecimalFormat("0.00");
				map.put("sc_total_price", BigDecimal.valueOf(totalPrice));
			}
		}

		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping({ "/goods_count_adjust1.htm" })
	public void goods_count_adjust1(HttpServletRequest request, HttpServletResponse response, String cart_id,
			String store_id, String count) {
		List<StoreCart> cart = cart_calc(request);

		double goods_total_price = 0.0D;
		String error = "100";
		Goods goods = null;
		String cart_type = "";
		GoodsCart gc;
		for (StoreCart sc : cart){
			List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
			for (Iterator localIterator2 = gcList1.iterator(); localIterator2.hasNext();) {
				gc = (GoodsCart) localIterator2.next();
				if (gc.getId().toString().equals(cart_id)) {
					goods = goodsService.selectById(gc.getGoods_id());
					cart_type = CommUtil.null2String(gc.getCart_type());
				}
			}
		}
		Object sc;
		if (cart_type.equals("")) {
			if (goods.getGroup_buy() == 2) {
				GroupGoods gg = new GroupGoods();
				GroupGoods sGroupGoods=new GroupGoods();
				sGroupGoods.setGg_goods_id(goods.getId());
				List<GroupGoods> childs=groupGoodsService.selectList(sGroupGoods);
				for (GroupGoods gg1 : childs) {
					
					if (gg1.getGg_goods_id().equals(goods.getId())) {
						gg = gg1;
					}
				}
				if (gg.getGg_count() >= CommUtil.null2Int(count))
					for (StoreCart sc1 : cart) { // sc = (StoreCart)gc.next();
						List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc1.getId());
						for (int i = 0; i < gcList1.size(); i++) {
							GoodsCart art = gcList1.get(i);
							GoodsCart gc1 = art;
							if (art.getId().toString().equals(cart_id)) {
								((StoreCart) sc1).setTotal_price(
										BigDecimal.valueOf(CommUtil.add(((StoreCart) sc1).getTotal_price(),
												Double.valueOf((CommUtil.null2Int(count) - art.getCount())
														* CommUtil.null2Double(art.getPrice())))));
								art.setCount(CommUtil.null2Int(count));
								gc1 = art;
								gcList1.remove(art);
								gcList1.add(gc1);
								goods_total_price = CommUtil.null2Double(gc1.getPrice()) * gc1.getCount();
								this.storeCartService.updateSelectiveById((StoreCart) sc1);
							}
						}
					}
				else {
					error = "300";
				}
			} else if (goods.getGoods_inventory() >= CommUtil.null2Int(count)) {
				for (StoreCart scart : cart) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(scart.getId());
					for (int i = 0; i < gcList1.size(); i++) {
						GoodsCart gcart = gcList1.get(i);
						GoodsCart gc1 = gcart;
						if (gcart.getId().toString().equals(cart_id)) {
							if (gcart.getStatus() == 1) {
								scart.setTotal_price(BigDecimal.valueOf(CommUtil.add(scart.getTotal_price(),
										Double.valueOf((CommUtil.null2Int(count) - gcart.getCount())
												* Double.parseDouble(gcart.getPrice().toPlainString())))));

							} 
							/*else if (gcart.getStatus() == 0) {

								gcart.setStatus(1);

								scart.setTotal_price(BigDecimal.valueOf(
										CommUtil.add(scart.getTotal_price(), Double.valueOf((CommUtil.null2Int(count))
												* Double.parseDouble(gcart.getPrice().toPlainString())))));
							}*/

							gcart.setCount(CommUtil.null2Int(count));
							gc1 = gcart;
							gcList1.remove(gcart);
							gcList1.add(gc1);
							goods_total_price = Double.parseDouble(gc1.getPrice().toString()) * gc1.getCount();
							this.storeCartService.updateSelectiveById(scart);
						}
					}
				}
			} else {
				error = "200";
			}
		}

		if (cart_type.equals("combin")) {
			if (goods.getGoods_inventory() >= CommUtil.null2Int(count))
				for (StoreCart sscart : cart) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sscart.getId());
					for (int i = 0; i <gcList1.size(); i++) {
						gc = gcList1.get(i);
						GoodsCart gc1 = (GoodsCart) gc;
						if (((GoodsCart) gc).getId().toString().equals(cart_id)) {
							Goods goods1 = this.goodsService.selectById(gc.getGoods_id());
							sscart.setTotal_price(BigDecimal.valueOf(CommUtil.add(sscart.getTotal_price(),
									Float.valueOf((CommUtil.null2Int(count) - ((GoodsCart) gc).getCount())
											* CommUtil.null2Float(goods1.getCombin_price())))));
							((GoodsCart) gc).setCount(CommUtil.null2Int(count));
							gc1 = (GoodsCart) gc;
							gcList1.remove(gc);
							gcList1.add(gc1);
							goods_total_price = Double.parseDouble(gc1.getPrice().toString()) * gc1.getCount();
							this.storeCartService.updateSelectiveById(sscart);
						}
					}
				}
			else {
				error = "200";
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		Object map = new HashMap();
		((Map) map).put("count", count);
		for (StoreCart ssscart : cart) {

			if (ssscart.getStore_id().equals(CommUtil.null2Long(store_id))) {
				((Map) map).put("sc_total_price", Float.valueOf(CommUtil.null2Float(ssscart.getTotal_price())));
			}
		}
		((Map) map).put("goods_total_price", Double.valueOf(df.format(goods_total_price)));
		((Map) map).put("error", error);
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/totalPrice.htm")
	public void totalPirce(HttpServletRequest request, HttpServletResponse response, String store_id, int status) {

		

		List<StoreCart> cart = cart_calc(request);
		Map map=new HashMap();
		for (StoreCart storeCart : cart) {

			if (storeCart.getStore_id() == Integer.parseInt(store_id)) {

				// 总价
				double totalPrice = 0.0D;

				// 计数器，用来计算状态为1的goodscart的数量，
				int count = 0;

				// 如果status=1，将该storecart的所有goodscart的status都设为1
				if (status == 1) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(storeCart.getId());
					for (GoodsCart goodsCart : gcList1) {
						goodsCart.setStatus(1);
						goodsCartService.updateSelectiveById(goodsCart);
					}
				}

				// 如果status=0，将该storecart的所有goodscart的status都设为0
				if (status == 0) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(storeCart.getId());
					for (GoodsCart goodsCart : gcList1) {
						goodsCart.setStatus(0);
						goodsCartService.updateSelectiveById(goodsCart);
					}
				}
				List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(storeCart.getId());
				for (GoodsCart goodsCart : gcList1) {
					if (goodsCart.getStatus() == 1) {
						count++;
					}
				}
				//List<GoodsCart> gcList2=goodsCartService.selectByStoreCartId(storeCart.getId());
				// 如果status=1的goodscart的数量等于storecart.gcs.size()，则表示是全选状态，返回总价
				if (count == gcList1.size()) {
					for (GoodsCart goodsCart : gcList1) {
						totalPrice += Double.valueOf(goodsCart.getCount())
								* Double.valueOf(goodsCart.getPrice().toString());
						map.put("status", 1);
					}
				} else {

					totalPrice = 0.0D;
					map.put("status", 0);
				}

				DecimalFormat df = new DecimalFormat("0.00");
				storeCart.setTotal_price(BigDecimal.valueOf(totalPrice));
				storeCartService.updateSelectiveById(storeCart);

				map.put("goods_total_price", BigDecimal.valueOf(totalPrice));
			}
		}

		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();

			writer.print(Json.toJson(map, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
