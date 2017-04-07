package com.rt.shop.view.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.DateUtils;
import com.rt.shop.common.tools.Md5Encrypt;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Activity;
import com.rt.shop.entity.Article;
import com.rt.shop.entity.ArticleClass;
import com.rt.shop.entity.BargainGoods;
import com.rt.shop.entity.DeliveryGoods;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.entity.GoodsFloor;
import com.rt.shop.entity.Group;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.entity.Message;
import com.rt.shop.entity.Partner;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreCart;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.User;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IActivityService;
import com.rt.shop.service.IArticleClassService;
import com.rt.shop.service.IArticleService;
import com.rt.shop.service.IBargainGoodsService;
import com.rt.shop.service.IDeliveryGoodsService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsFloorService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.IGroupService;
import com.rt.shop.service.IMessageService;
import com.rt.shop.service.INavigationService;
import com.rt.shop.service.IPartnerService;
import com.rt.shop.service.IRoleService;
import com.rt.shop.service.IStoreCartService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.MsgTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.GoodsFloorViewTools;
import com.rt.shop.view.web.tools.GoodsViewTools;
import com.rt.shop.view.web.tools.NavViewTools;
import com.rt.shop.view.web.tools.StoreViewTools;

@Controller
public class IndexViewAction {

	CommWebUtil CommWebUtil=new CommWebUtil();
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IGoodsClassService goodsClassService;

	@Autowired
	private IGoodsBrandService goodsBrandService;

	@Autowired
	private IPartnerService partnerService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IArticleClassService articleClassService;

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IAccessoryService accessoryService;

	@Autowired
	private IMessageService messageService;

	@Autowired
	private IStoreService storeService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private INavigationService navigationService;

	@Autowired
	private IGroupGoodsService groupGoodsService;

	@Autowired
	private IGroupService groupService;

	@Autowired
	private IGoodsFloorService goodsFloorService;

	@Autowired
	private IBargainGoodsService bargainGoodsService;

	@Autowired
	private IDeliveryGoodsService deliveryGoodsService;

	@Autowired
	private IStoreCartService storeCartService;

	@Autowired
	private IGoodsCartService goodsCartService;

	@Autowired
	private NavViewTools navTools;

	@Autowired
	private GoodsViewTools goodsViewTools;

	@Autowired
	private StoreViewTools storeViewTools;

	@Autowired
	private MsgTools msgTools;

	@Autowired
	private GoodsFloorViewTools gf_tools;

	@Autowired
	private IActivityService activityService;

	/**
	 * 页面最上面部分
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "/top.htm" })
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("top.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		List msgs = new ArrayList();
		if (SecurityUserHolder.getCurrentUser() != null) {
			
			Message sMessage=new Message();
			sMessage.setStatus(Integer.valueOf(0));
			sMessage.setReply_status(Integer.valueOf(1));
			sMessage.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
			sMessage.setToUser_id(SecurityUserHolder.getCurrentUser().getId());
			msgs = this.messageService.selectMessAgeOr(sMessage);
					
					
		}
		
		List<GoodsCart> list = new ArrayList<GoodsCart>();
		List<StoreCart> cart = new ArrayList<StoreCart>();
		List<StoreCart> user_cart = new ArrayList<StoreCart>();
		List<StoreCart> cookie_cart = new ArrayList<StoreCart>();
		User user = null;
		if (SecurityUserHolder.getCurrentUser() != null) {
			user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		}
		Store store = null;
		if (SecurityUserHolder.getCurrentUser() != null)
			store = this.storeService.selectById(user.getStore_id());
			//getObjByProperty("user.id", SecurityUserHolder.getCurrentUser().getId());
		mv.addObject("store", store);
		mv.addObject("navTools", this.navTools);
		mv.addObject("msgs", msgs);
		String cart_session_id = "";
		
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
				if (user.getStore_id() != null) {
					
					StoreCart sStoreCart=new StoreCart();
					sStoreCart.setCart_session_id(cart_session_id);
					sStoreCart.setUser_id(user.getId());
					sStoreCart.setSc_status(Integer.valueOf(0));
					sStoreCart.setStore_id(store.getId());
					String sql="where (cart_session_id='"+cart_session_id+"' or user_id='"+user.getId()+"') and sc_status='"+Integer.valueOf(0)+"' and store_id='"+store.getId()+"'";
					List<StoreCart> store_cookie_cart = this.storeCartService.selectList(sql, null);
					List<GoodsCart> sGoodsCartList=null;	
					for (StoreCart sc : store_cookie_cart) {
						  sGoodsCartList=goodsCartService.selectByStoreCartId(sc.getId());
						for (GoodsCart gc : sGoodsCartList) {
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
			if (sc_add)
				cart.add(sc);
		}
		boolean sc_add;
		for (StoreCart sc : cookie_cart) {
			sc_add = true;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(sc.getStore_id())) {
					sc_add = false;
					List<GoodsCart> sGoodsCartList=goodsCartService.selectByStoreCartId(sc.getId());
					for (GoodsCart gc : sGoodsCartList) {
					//	gc.setSc(sc1);
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
		if (cart != null) {
			for (StoreCart sc : cart) {
				if (sc != null) {
					GoodsCart gc1=new GoodsCart();
					gc1.setSc_id(sc.getId());
					List<GoodsCart> goodsCarts=goodsCartService.selectList(gc1);
					list.addAll(goodsCarts);
				}
			}
		}
		float total_price = 0.0F;
		for (GoodsCart gc : list) {
			Goods goods = this.goodsService.selectById(gc.getGoods_id());
			if (CommUtil.null2String(gc.getCart_type()).equals("combin"))
				total_price = CommUtil.null2Float(goods.getCombin_price());
			else {
				total_price = CommUtil
						.null2Float(Double
								.valueOf(CommUtil.mul(Integer.valueOf(gc.getCount()), goods.getGoods_current_price())))
						+ total_price;
			}
		}
		mv.addObject("total_price", Float.valueOf(total_price));
		mv.addObject("cart", list);
		return (ModelAndView) mv;
	}

	/**
	 * 横着的导航栏
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "/nav.htm" })
	public ModelAndView nav(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("nav.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		mv.addObject("navTools", this.navTools);
		
		GoodsClass sGoodsClass=new GoodsClass();
		sGoodsClass.setDisplay(Boolean.valueOf(true));
		
		
		//List<GoodsClass> gcs = this.goodsClassService.selectPage(new Page<GoodsClass>(1, 14), sGoodsClass,"sequence").getRecords();
		List<GoodsClass> gcs = this.goodsClassService.selectList("where display=1 and parent_id is null", null);
		for(GoodsClass gc : gcs){
			GoodsClass ggc=new GoodsClass();
			ggc.setParent_id(gc.getId());
			List<GoodsClass> childs=this.goodsClassService.selectList(ggc);
			gc.setChilds(childs);
			Accessory ac=accessoryService.selectById(gc.getIcon_acc_id());
			gc.setIcon_acc(ac);
			for(GoodsClass gc1 : childs){
				GoodsClass ggc1=new GoodsClass();
				ggc1.setParent_id(gc1.getId());
				List<GoodsClass> childs1=this.goodsClassService.selectList(ggc1);
				gc1.setChilds(childs1);
				Accessory ac1=accessoryService.selectById(gc1.getIcon_acc_id());
				gc1.setIcon_acc(ac1);
			}
			
		}
		mv.addObject("navTools", this.navTools);
		mv.addObject("gcs", gcs);
		return mv;
	}

	@RequestMapping({ "/nav1.htm" })
	public ModelAndView nav1(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("nav1.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		
		List<GoodsClass> gcs = this.goodsClassService.selectList("where display=1 and parent_id is null", null);;
		for(GoodsClass gc : gcs){
			GoodsClass ggc=new GoodsClass();
			ggc.setParent_id(gc.getId());
			List<GoodsClass> childs=this.goodsClassService.selectList(ggc);
			gc.setChilds(childs);
			Accessory ac=accessoryService.selectById(gc.getIcon_acc_id());
			gc.setIcon_acc(ac);
			for(GoodsClass gc1 : childs){
				GoodsClass ggc1=new GoodsClass();
				ggc1.setParent_id(gc1.getId());
				List<GoodsClass> childs1=this.goodsClassService.selectList(ggc1);
				gc1.setChilds(childs1);
				Accessory ac1=accessoryService.selectById(gc1.getIcon_acc_id());
				gc1.setIcon_acc(ac1);
			}
		}
		mv.addObject("gcs", gcs);
		mv.addObject("navTools", this.navTools);
		return mv;
	}

	@RequestMapping({ "/head.htm" })
	public ModelAndView head(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("head.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String type = CommUtil.null2String(request.getAttribute("type"));
		mv.addObject("type", type.equals("") ? "goods" : type);
		return mv;
	}

	@RequestMapping({ "/login_head.htm" })
	public ModelAndView login_head(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("login_head.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		return mv;
	}

	@RequestMapping({ "/floor.htm" })
	public ModelAndView floor(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("floor.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		
		
		List floors=this.goodsFloorService.selectList("where gf_display=1 and parent_id is null", null);
//		List floors = this.goodsFloorService.query(
//				"select obj from GoodsFloor obj where obj.gf_display=:gf_display and obj.parent.id is null order by obj.gf_sequence asc",
//				params, -1, -1);
		mv.addObject("floors", floors);
		mv.addObject("gf_tools", this.gf_tools);
		mv.addObject("url", CommUtil.getURL(request));
		return mv;
	}

	@RequestMapping({ "/floor_ajax.htm" })
	public void floorAjax(HttpServletRequest request, HttpServletResponse response, Long id, Integer count) {
		
		
		List floors=this.goodsFloorService.selectList("where gf_display=1 and id='"+id+"' and parent_id is null", "gf_sequence asc");

		Map<String, Object> map = new HashMap<String, Object>();
		CommWebUtil.saveWebPaths(map, this.configService.getSysConfig(), request);
		String ret = showLoadFloorAjaxHtml(floors, count, CommUtil.getURL(request), map);
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

	@RequestMapping({ "/footer.htm" })
	public ModelAndView footer(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("footer.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shopping_view_type = CommUtil.null2String(request.getSession().getAttribute("shopping_view_type"));
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/footer.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		mv.addObject("navTools", this.navTools);
		return mv;
	}

	@RequestMapping({ "/index.htm" })
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("index.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		// 设置为PC访问
		request.getSession().setAttribute("shopping_view_type", "pc");
		String requestHeader = request.getHeader("user-agent");
        
		
		/*
		 * params.put( "display", Boolean.valueOf( true ) ); List gcs =
		 * this.goodsClassService.query(
		 * "select obj from GoodsClass obj where obj.parent.id is null and obj.display=:display order by obj.sequence asc"
		 * , params, 0, 15 ); mv.addObject( "gcs", gcs ); params.clear();
		 */
		// 推荐品牌
		
		GoodsBrand sGoodsBrand=new GoodsBrand();
		sGoodsBrand.setAudit(Integer.valueOf(1));
		sGoodsBrand.setRecommend(Boolean.valueOf(true));
		List<GoodsBrand> gbs = this.goodsBrandService.selectPage(new Page<GoodsBrand>(1, 14),sGoodsBrand,"sequence").getRecords();
		for(GoodsBrand gb: gbs){
			Accessory ac=accessoryService.selectById(gb.getBrandLogo_id());
			gb.setBrandLogo(ac);
		}
		mv.addObject("gbs", gbs);
		// 底部显示的合作伙伴
		
		Partner ssPartner=new Partner();
		//查询没有图片的合作伙伴
		List img_partners = this.partnerService.selectParterNoImg(ssPartner);
			
		mv.addObject("img_partners", img_partners);
		Partner sPartner=new Partner();
		sPartner.setImage_id(null);
		List text_partners = this.partnerService.selectList(sPartner, "sequence");
		
		mv.addObject("text_partners", text_partners);
		// 底部新闻分类显示
	
		ArticleClass sArticleClass=new ArticleClass();
		
		sArticleClass.setMark("news");
		String sql="where parent_id is null and mark!='news' order by sequence asc";
		List<ArticleClass> acs = this.articleClassService.selectPage(new Page<ArticleClass>(1, 9),sql,null).getRecords();
		mv.addObject("acs", acs);
		// 商城新闻
		Article sArticle=new Article();
		sArticle.setMark("news");
		sArticle.setDisplay(Boolean.valueOf(true));
		String sql1="where mark='news' and display=1 order by addTime desc";
		List<Article> articles = this.articleService.selectPage(new Page<Article>(1, 5),sql1,null).getRecords();//TODO
		mv.addObject("articles", articles);
		// 查询推荐店铺商品
		Goods sGoods=new Goods();
		sGoods.setStore_recommend(Boolean.valueOf(true));
		sGoods.setGoods_status(Integer.valueOf(0));
		
		List<Goods> store_reommend_goods_list = this.goodsService.selectList(sGoods, "store_recommend_time");
			for(Goods g1 : store_reommend_goods_list){
				Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
				g1.setGoods_main_photo(ac);
			}
		List store_reommend_goods = new ArrayList();
		int max = store_reommend_goods_list.size() >= 5 ? 4 : store_reommend_goods_list.size() - 1;
		for (int i = 0; i <= max; i++) {
			store_reommend_goods.add((Goods) store_reommend_goods_list.get(i));
		}
		// 1、推荐商品可在后台编辑
		mv.addObject("store_reommend_goods", store_reommend_goods);
		mv.addObject("store_reommend_goods_count", Double.valueOf(
				Math.ceil(CommUtil.div(Integer.valueOf(store_reommend_goods_list.size()), Integer.valueOf(5)))));
		mv.addObject("goodsViewTools", this.goodsViewTools);
		mv.addObject("storeViewTools", this.storeViewTools);
		if (SecurityUserHolder.getCurrentUser() != null) {
			mv.addObject("user", this.userService.selectById(SecurityUserHolder.getCurrentUser().getId()));
		}
		Goods ssGoods=new Goods();
		ssGoods.setGoods_status(Integer.valueOf(0));
		List<Goods> list = this.goodsService.selectPage(new Page<Goods>(0, 5), ssGoods, "goods_salenum desc").getRecords();
		for(Goods g1 : list){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
		mv.addObject("fengKuangs", list);

		// 5、随机生成推荐商品 猜您喜欢
		List store_guess_goods = new ArrayList();
		Random rand = new Random();
		int recommend_goods_random = rand.nextInt(5);
		int begin = recommend_goods_random * 5;
		if (begin > store_reommend_goods_list.size() - 1) {
			begin = 0;
		}
		int maxsize = begin + 5;
		if (maxsize > store_reommend_goods_list.size()) {
			begin -= maxsize - store_reommend_goods_list.size();
			maxsize--;
		}
		for (int i = 0; i < store_reommend_goods_list.size(); i++) {
			if ((i >= begin) && (i < maxsize)) {
				store_guess_goods.add((Goods) store_reommend_goods_list.get(i));
			}
		}
		mv.addObject("cais", store_guess_goods);
		
		// 7、新品上架
		Goods sssGoods=new Goods();
		sssGoods.setGoods_status(Integer.valueOf(0));
		List<Goods> new_goods_list = this.goodsService.selectPage(new Page<Goods>(0, 5), sssGoods, "addTime desc").getRecords();
		for(Goods g1 : new_goods_list){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
		mv.addObject("xinShangs", new_goods_list);

		// 8、点击数最多:人气商品
		List<Goods> hot_goods_list = this.goodsService.selectPage(new Page<Goods>(0, 5), sssGoods, "goods_click desc").getRecords();
		for(Goods g1 : hot_goods_list){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
		mv.addObject("hots", hot_goods_list);
		
		// 团购查询
		 String sql3=" where beginTime<='"+DateUtils.getDateStart(new Date())+"' and endTime>='"+DateUtils.getDateEnd(new Date())+"'";
	     List<Group> groups = this.groupService.selectList(sql3, null);
		if (groups!=null && groups.size() > 0) {
			// 2、团购商品
			GroupGoods sGroupGoods=new GroupGoods();
			sGroupGoods.setGg_status(Integer.valueOf(1));
			sGroupGoods.setGg_recommend(Integer.valueOf(1));
			sGroupGoods.setGroup_id(((Group) groups.get(0)).getId());
			List ggs = this.groupGoodsService.selectPage(new Page<GroupGoods>(0, 5), sGroupGoods, "gg_recommend_time desc").getRecords();
			mv.addObject("ggs", ggs);
		}
		// 3、天天特价
		/*
		 * params.clear(); params.put( "bg_time", CommUtil.formatDate(
		 * CommUtil.formatShortDate( new Date() ) ) ); params.put( "bg_status",
		 * Integer.valueOf( 1 ) ); List bgs = this.bargainGoodsService.query(
		 * "select obj from BargainGoods obj where obj.bg_time=:bg_time and obj.bg_status=:bg_status"
		 * , params, 0, 5 ); mv.addObject( "bgs", bgs );
		 */
		// 4、热销商品倒序-疯狂抢购
		
		
		// 6、满送商品
	
		DeliveryGoods sDeliveryGoods=new DeliveryGoods();
		sDeliveryGoods.setD_status(Integer.valueOf(1));
		sDeliveryGoods.setD_begin_time(new Date());
		sDeliveryGoods.setD_end_time(new Date());
		List<DeliveryGoods> dgs = this.deliveryGoodsService.selectPage(new Page<DeliveryGoods>(0, 5), sDeliveryGoods, "d_audit_time desc").getRecords();
		for(DeliveryGoods g1 : dgs){
			Goods g=goodsService.selectById(g1.getD_delivery_goods_id());
			Goods g2=goodsService.selectById(g1.getD_goods_id());
			g1.setD_delivery_goods(g);
			g1.setD_delivery_goods(g2);
		}
		mv.addObject("dgs", dgs);

		

		return mv;
	}

	@RequestMapping({ "/close.htm" })
	public ModelAndView wapclose(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("close.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shopping_view_type = CommUtil.null2String(request.getSession().getAttribute("shopping_view_type"));
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/close.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		return mv;
	}

	@RequestMapping({ "/404.htm" })
	public ModelAndView error404(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("404.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shopping_view_type = CommUtil.null2String(request.getSession().getAttribute("shopping_view_type"));
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			// String store_id = CommUtil.null2String( request.getSession( false
			// ).getAttribute( "store_id" ) );
			mv = new JModelAndView("wap/404.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("url", CommUtil.getURL(request) + "/wap/index.htm");
		}

		return mv;
	}

	@RequestMapping({ "/500.htm" })
	public ModelAndView error500(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("500.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			String store_id = CommUtil.null2String(request.getSession(false).getAttribute("store_id"));
			mv = new JModelAndView("wap/500.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("url", CommUtil.getURL(request) + "/wap/index.htm?store_id=" + store_id);
		}

		return mv;
	}

	@RequestMapping({ "/goods_class.htm" })
	public ModelAndView goods_class(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("goods_class.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/goods_class.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		
		GoodsClass sGoodsClass=new GoodsClass();
		sGoodsClass.setDisplay(Boolean.valueOf(true));
		List gcs = this.goodsClassService.selectList(sGoodsClass, "sequence asc");
			//	"select obj from GoodsClass obj where obj.parent.id is null and obj.display=:display order by obj.sequence asc",
				
		mv.addObject("gcs", gcs);
		return mv;
	}

	@RequestMapping({ "/goodsclass.htm" })
	public ModelAndView goodsclass(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "gcId", required = false) Long gcId) {

		ModelAndView mv = new JModelAndView("goodsclass.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		String shopping_view_type = CommUtil.null2String(request.getSession(false).getAttribute("shopping_view_type"));
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/goodsclass.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
		}
		
		GoodsClass sGoodsClass=new GoodsClass();
	
		String sql="where parent_id is null  order by sequence asc";
		List<GoodsClass> gcs = this.goodsClassService.selectList(sql,null);
				//"select obj from GoodsClass obj where obj.parent.id is null  order by obj.sequence asc", params, -1,

		mv.addObject("gcs", gcs);

		if (gcId != null) {
			GoodsClass ssGoodsClass=new GoodsClass();
			ssGoodsClass.setParent_id(gcId);

			List<GoodsClass> gcs2 = this.goodsClassService.selectList(ssGoodsClass, "sequence asc");
					//"select obj from GoodsClass obj where obj.parent.id =:gcId  order by obj.sequence asc", params, -1,

			mv.addObject("gcId", gcId);
			mv.addObject("gcs2", gcs2);
		} else {

			Long id = gcs.get(0).getId();

			GoodsClass ssGoodsClass=new GoodsClass();
			ssGoodsClass.setParent_id(id);
			List<GoodsClass> gcs2 = this.goodsClassService.selectList(ssGoodsClass, "sequence asc");
				//	"select obj from GoodsClass obj where obj.parent.id =:gcId  order by obj.sequence asc", params, -1,

			mv.addObject("gcId", id);
			mv.addObject("gcs2", gcs2);
		}

		return mv;
	}

	@RequestMapping({ "/forget.htm" })
	public ModelAndView forget(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("forget.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		SysConfig config = this.configService.getSysConfig();
		if (!config.getEmailEnable()) {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "系统关闭邮件功能，不能找回密码");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	@RequestMapping({ "/find_pws.htm" })
	public ModelAndView find_pws(HttpServletRequest request, HttpServletResponse response, String userName,
			String email, String code) {
		ModelAndView mv = new JModelAndView("success.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		HttpSession session = request.getSession(false);
		String verify_code = (String) session.getAttribute("verify_code");
		if (code.toUpperCase().equals(verify_code)) {
			User sUser=new User();
			sUser.setUserName(userName);
			User user = this.userService.selectOne(sUser);
					//getObjByProperty("userName", userName);
			if (user.getEmail().equals(email.trim())) {
				String pws = CommUtil.randomString(6).toLowerCase();
				String subject = this.configService.getSysConfig().getTitle() + "密码找回邮件";
				String content = user.getUserName() + ",您好！您通过密码找回功能重置密码，新密码为：" + pws;
				boolean ret = this.msgTools.sendEmail(email, subject, content);
				if (ret) {
					user.setPassword(Md5Encrypt.md5(pws));
					this.userService.updateSelectiveById(user);
					mv.addObject("op_title", "新密码已经发送到邮箱:<font color=red>" + email + "</font>，请查收后重新登录");
					mv.addObject("url", CommUtil.getURL(request) + "/user/login.htm");
				} else {
					mv = new JModelAndView("error.html", this.configService.getSysConfig(),
							this.userConfigService.getUserConfig(), 1, request, response);
					mv.addObject("op_title", "邮件发送失败，密码暂未执行重置");
					mv.addObject("url", CommUtil.getURL(request) + "/forget.htm");
				}
			} else {
				mv = new JModelAndView("error.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
				mv.addObject("op_title", "用户名、邮箱不匹配");
				mv.addObject("url", CommUtil.getURL(request) + "/forget.htm");
			}
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "验证码不正确");
			mv.addObject("url", CommUtil.getURL(request) + "/forget.htm");
		}
		return mv;
	}

	@RequestMapping({ "/switch_recommend_goods.htm" })
	public ModelAndView switch_recommend_goods(HttpServletRequest request, HttpServletResponse response,
			int recommend_goods_random) {
		ModelAndView mv = new JModelAndView("switch_recommend_goods.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		Map params = new HashMap();
		params.put("store_recommend", Boolean.valueOf(true));
		params.put("goods_status", Integer.valueOf(0));
		Goods sGoods=new Goods();
		sGoods.setStore_recommend(Boolean.valueOf(true));
		sGoods.setGoods_status(Integer.valueOf(0));
		List<Goods> store_reommend_goods_list = this.goodsService.selectList(sGoods,"store_recommend_time desc");
		for(Goods g1 : store_reommend_goods_list){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}	
		List store_reommend_goods = new ArrayList();
		int begin = recommend_goods_random * 5;
		if (begin > store_reommend_goods_list.size() - 1) {
			begin = 0;
		}
		int max = begin + 5;
		if (max > store_reommend_goods_list.size()) {
			begin -= max - store_reommend_goods_list.size();
			max--;
		}
		for (int i = 0; i < store_reommend_goods_list.size(); i++) {
			if ((i >= begin) && (i < max)) {
				store_reommend_goods.add((Goods) store_reommend_goods_list.get(i));
			}
		}
		mv.addObject("store_reommend_goods", store_reommend_goods);
		return mv;
	}

	@RequestMapping({ "/outline.htm" })
	public ModelAndView outline(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv=null;
		String shopping_view_type = CommUtil.null2String(request.getSession().getAttribute("shopping_view_type"));
		System.out.println("执行到了下线操作+++++shopping_view_type="+shopping_view_type);
		
		if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
			mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "该用户在其他地点登录，您被迫下线！");
			mv.addObject("url", CommUtil.getURL(request) + "/wap/index.htm");
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "该用户在其他地点登录，您被迫下线！");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	/** wap首页业务逻辑begin */

	
	/**
	 * wap首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping({ "/wap/index.htm" })
	public ModelAndView wapindex(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new JModelAndView("wap/index.html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
  
		// 设置为wap访问
	//	request.getSession().setAttribute("shopping_view_type", "wap");

		Store sStore=new Store();
		sStore.setStore_recommend(true);
		List<Store> sr = storeService.selectList(sStore);
		mv.addObject("sr", sr);
		// 活动
		Activity sActivity=new Activity();
		sActivity.setAc_status(1);
		List<Activity> activities = this.activityService.selectList(sActivity);
		mv.addObject("activities", activities);

		String sql="where parent_id is null and display=1 order by sequence asc";
		List gcs = this.goodsClassService.selectPage(new Page<GoodsClass>(0, 15), sql, null).getRecords();
		mv.addObject("gcs", gcs);
		
		GoodsBrand sGoodsBrand=new GoodsBrand();
		sGoodsBrand.setAudit(Integer.valueOf(1));
		sGoodsBrand.setRecommend(Boolean.valueOf(true));
		List gbs = this.goodsBrandService.selectList(sGoodsBrand, "sequence asc");
		mv.addObject("gbs", gbs);
		Partner sPartner=new Partner();
		List img_partners = this.partnerService.selectParterNoImg(sPartner);		
		mv.addObject("img_partners", img_partners);
		Partner ssPartner=new Partner();
		ssPartner.setImage_id(null);
		List text_partners = this.partnerService.selectList(ssPartner, "sequence asc");
		mv.addObject("text_partners", text_partners);
		
		
		String sql5="where parent_id is null and mark!='news' order by sequence asc";
		List acs = this.articleClassService.selectPage(new Page<ArticleClass>(0, 9), sql5, null).getRecords();
		mv.addObject("acs", acs);
	
		Goods sGoods=new Goods();
		sGoods.setStore_recommend(Boolean.valueOf(true));
		sGoods.setGoods_status(Integer.valueOf(0));
		List<Goods> store_reommend_goods_list = this.goodsService.selectPage(new Page<Goods>(0, 6), sGoods, "store_recommend_time desc").getRecords();
		for(Goods g1 : store_reommend_goods_list){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
		List store_reommend_goods = new ArrayList();
		int max = store_reommend_goods_list.size() >= 21 ? 20 : store_reommend_goods_list.size() - 1;
		for (int i = 0; i <= max; i++) {
			store_reommend_goods.add((Goods) store_reommend_goods_list.get(i));
		}
		mv.addObject("store_reommend_goods", store_reommend_goods);

		mv.addObject("store_reommend_goods_count", Double.valueOf(
				Math.ceil(CommUtil.div(Integer.valueOf(store_reommend_goods_list.size()), Integer.valueOf(5)))));
		mv.addObject("goodsViewTools", this.goodsViewTools);
		mv.addObject("storeViewTools", this.storeViewTools);
		if (SecurityUserHolder.getCurrentUser() != null) {
			mv.addObject("user", this.userService.selectById(SecurityUserHolder.getCurrentUser().getId()));
		}
		
		 String sql6=" where beginTime<='"+CommUtil.formatTime("yyyyMMdd", new Date())+"' and endTime>="+CommUtil.formatTime("yyyyMMdd", new Date())+"";
	     List<Group> groups = this.groupService.selectList(sql6, null);
		if (groups.size() > 0) {
			
			GroupGoods sGroupGoods=new GroupGoods();
			sGroupGoods.setGg_status(Integer.valueOf(1));
			sGroupGoods.setGg_recommend(Integer.valueOf(1));
			sGroupGoods.setGroup_id(((Group) groups.get(0)).getId());
			List ggs = this.groupGoodsService.selectPage(new Page<GroupGoods>(0, 1), sGroupGoods, "gg_recommend_time desc").getRecords();
			if (ggs.size() > 0)
				mv.addObject("group", ggs.get(0));
		}
		
		BargainGoods sBargainGoods=new BargainGoods();
		sBargainGoods.setBg_status(Integer.valueOf(1));
		sBargainGoods.setBg_time(CommUtil.formatDate(CommUtil.formatShortDate(new Date())));
		List bgs = this.bargainGoodsService.selectPage(new Page<BargainGoods>(0, 5), sBargainGoods).getRecords();
		mv.addObject("bgs", bgs);

		
		DeliveryGoods sDeliveryGoods=new DeliveryGoods();
		sDeliveryGoods.setD_status(Integer.valueOf(1));
		sDeliveryGoods.setD_begin_time(new Date());
		sDeliveryGoods.setD_end_time(new Date());
		List dgs = this.deliveryGoodsService.selectPage(new Page<DeliveryGoods>(0, 3), sDeliveryGoods, "d_audit_time desc").getRecords();
		mv.addObject("dgs", dgs);

		List msgs = new ArrayList();
		if (SecurityUserHolder.getCurrentUser() != null) {
			
			Message sMessage=new Message();
			sMessage.setStatus(Integer.valueOf(0));
			sMessage.setReply_status(Integer.valueOf(1));
			sMessage.setFromUser_id(SecurityUserHolder.getCurrentUser().getId());
			sMessage.setToUser_id(SecurityUserHolder.getCurrentUser().getId());
			msgs = this.messageService.selectMessAgeOr(sMessage);
		}
	
		mv.addObject("navTools", this.navTools);
		mv.addObject("msgs", msgs);
		List<GoodsCart> list = new ArrayList<GoodsCart>();
		List<StoreCart> cart = new ArrayList<StoreCart>();
		List<StoreCart> user_cart = new ArrayList<StoreCart>();
		List<StoreCart> cookie_cart = new ArrayList<StoreCart>();
		User user = null;
		if (SecurityUserHolder.getCurrentUser() != null) {
			user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		}
		String cart_session_id = "";
		Store store = null;
		if (SecurityUserHolder.getCurrentUser() != null)
			store = this.storeService.selectById(user.getStore_id());
		mv.addObject("store", store);
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
				if (user.getStore_id() != null) {
					String sqlx="where (cart_session_id='"+cart_session_id+"' or user_id='"+user.getId()+"') and sc_status='"+Integer.valueOf(0)+"' and store_id='"+store.getId()+"'";
					List<StoreCart> store_cookie_cart = this.storeCartService.selectList(sqlx, null);
					List<GoodsCart> sGoodsCartList=null;	
					for (StoreCart sc : store_cookie_cart) {
						  sGoodsCartList=goodsCartService.selectByStoreCartId(sc.getId());
						for (GoodsCart gc : sGoodsCartList) {
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
			if (sc_add)
				cart.add(sc);
		}
		boolean sc_add;
		for (StoreCart sc : cookie_cart) {
			sc_add = true;
			for (StoreCart sc1 : cart) {
				if (sc1.getStore_id().equals(sc.getStore_id())) {
					sc_add = false;
					List<GoodsCart> sGoodsCartList=goodsCartService.selectByStoreCartId(sc.getId());
					for (GoodsCart gc : sGoodsCartList) {
						//gc.setSc(sc1);
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
		if (cart != null) {
			for (StoreCart sc : cart) {
				if (sc != null) {
					List<GoodsCart> gcList1=goodsCartService.selectByStoreCartId(sc.getId());
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
				total_price = CommUtil
						.null2Float(Double
								.valueOf(CommUtil.mul(Integer.valueOf(gc.getCount()), goods.getGoods_current_price())))
						+ total_price;
			}
		}
		mv.addObject("total_price", Float.valueOf(total_price));
		mv.addObject("cart", list);

		return mv;
	}

	public String showLoadFloorAjaxHtml(List lists, int i, String url, Map<String, Object> map) {

		String img = null;
		String loadimg = map.get("imageWebServer") + "/resources/style/common/images/loader.gif";
		String errorimg = map.get("webPath") + "/" + map.get("goodsImagePath") + "/" + map.get("goodsImageName");
		String goods_url = null;
		String goods_class_url = null;
		String child_goods_class_url = null;

		GoodsFloor floor = (GoodsFloor) lists.get(0);

		img = null;

		StringBuffer sb = new StringBuffer(1000);
		sb.append("<div class='floor " + floor.getGf_css() + "'>")
				.append("<div class='floor_box' id='floor_" + i + "'>");
		sb.append("<div class='floor_menu'>").append("<div class='title'>").append("<div class='txt-type'>")
				.append("<span>").append(i).append("</span>");
		sb.append("<h2 title='").append(floor.getGf_name()).append("'>").append(floor.getGf_name())
				.append("</h2></div></div><div class='flr_m_details'><ul class='flr_m_du'>");
		List<GoodsClass> gcs = this.gf_tools.generic_gf_gc(floor.getGf_gc_list());
		for (GoodsClass gc : gcs) {
			goods_class_url = map.get("webPath") + "/store_goods_list_" + gc.getId() + ".htm";
			sb.append("<li><h4><a href='").append(goods_class_url).append("'>").append(gc.getClassName())
					.append("</a></h4><p>");
			GoodsClass sGoodsClass=new GoodsClass();
			sGoodsClass.setParent_id(gc.getId());
			List<GoodsClass> childs=goodsClassService.selectList(sGoodsClass);
			for (GoodsClass c_gc : childs) {
				child_goods_class_url = map.get("webPath") + "/store_goods_list_" + c_gc.getId() + ".htm";
				sb.append("<span><a href='").append(child_goods_class_url).append("' target='_blank'>")
						.append(c_gc.getClassName()).append("</a></span>");
			}
			sb.append("</p></li>");
		}
		sb.append("</ul><div class='flr_advertisment'>");
		// 拼接左侧广告
		sb.append(gf_tools.generic_adv(url, floor.getGf_left_adv()));

		sb.append("</div></div></div><div class='floorclass'><ul class='floorul'>");

		int num = 0;
		GoodsFloor sGoodsFloor=new GoodsFloor();
		sGoodsFloor.setParent_id(floor.getId());
		List<GoodsFloor> floorChilds=goodsFloorService.selectList(sGoodsFloor);
		for (GoodsFloor info : floorChilds) {
			num++;
			sb.append("<li ");
			if (num == 1) {
				sb.append("class='this'");
			}
			sb.append("style='cursor:pointer;' id='").append(info.getId()).append("' store_gc='").append(floor.getId())
					.append("' >");
			sb.append(info.getGf_name()).append("<s></s></li>");
		}
		sb.append("</ul>");

		int count = 0;

		for (GoodsFloor info : floorChilds) {

			count++;
			sb.append("<div id='").append(info.getId()).append("' store_gc='").append(floor.getId())
					.append("' class='ftab'");
			if (count > 1) {
				sb.append("style='display:none;'");
			}
			sb.append("><div class='ftabone'><div class='classpro'>");
			for (Goods goods : this.gf_tools.generic_goods(info.getGf_gc_goods())) {
				if (goods != null) {
					Accessory sAccessory=new Accessory();
					sAccessory.setId(goods.getGoods_main_photo_id());
					Accessory goods_main_photo=accessoryService.selectOne(sAccessory);
					
					Store sStore=new Store();
					sStore.setId(goods.getGoods_store_id());
					Store gStore=storeService.selectOne(sStore);
					
					if (goods.getGoods_main_photo_id() != null)
						img = map.get("imageWebServer") + "/" + goods_main_photo.getPath() + "/"
								+ goods_main_photo.getName() + "_small."
								+ goods_main_photo.getExt();
					else
						img = map.get("imageWebServer") + "/" + map.get("goodsImagePath") + "/"
								+ map.get("goodsImageName");

					goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";

					if ((Boolean) map.get("IsSecondDomainOpen")) {
						goods_url = "http://" + gStore.getStore_second_domain() + "."
								+ map.get("domainPath") + "/goods_" + goods.getId() + ".htm";
					}
					sb.append("<div class='productone'><ul class='this'><li><span class='center_span'>");
					// sb.append("<p><a href='").append(goods_url).append("'
					// target='_blank' ><img src='").append(loadimg).append("'
					// original='");
					// sb.append(img).append("'
					// onerror=\"this.src=").append(errorimg).append(";\"
					// width='28' height='28'/></a></p></span></li>");
					sb.append("<p><a href='").append(goods_url).append("' target='_blank' ><img src='").append(img)
							.append("' original='");
					sb.append(img).append("' onerror=\"this.src=").append(errorimg)
							.append(";\" /></a></p></span></li>");
					sb.append("<li class='pronames'><a href='").append(goods_url).append("' target='_blank'>")
							.append(goods.getGoods_name()).append("</a></li>");
					sb.append("<li><span class=\"hui2\">市场价：</span><span class=\"through hui\">¥")
							.append(goods.getGoods_price());
					sb.append("</span></li><li><span class=\"hui2\">商城价：</span><strong class=\"red\">¥")
							.append(goods.getGoods_current_price());
					sb.append("</strong></li></ul></div>");
				}
			}
			sb.append("</div></div></div>");
		}
		sb.append("</div><div class='ranking'>");
		Map<String, Object> mmap = gf_tools.generic_goods_list(floor.getGf_list_goods());
		sb.append("<h1>").append(mmap.get("list_title")).append("</h1>");

		if (mmap.get("goods1") != null) {
			Goods goods = (Goods) mmap.get("goods1");
			Accessory sAccessory=new Accessory();
			sAccessory.setId(goods.getGoods_main_photo_id());
			Accessory goods_main_photo=accessoryService.selectOne(sAccessory);
			
			
			if (goods.getGoods_main_photo_id() != null)
				img = map.get("imageWebServer") + "/" + goods_main_photo.getPath() + "/"
						+ goods_main_photo.getName() + "_small." + goods_main_photo.getExt();
			else
				img = map.get("imageWebServer") + "/" + map.get("goodsImagePath") + "/" + map.get("goodsImageName");

			goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";

			sb.append("<ul class=\"rankul\"><li class=\"rankimg\"><a href='").append(goods_url)
					.append("' target=\"_blank\">");
			// sb.append("<img src='").append(loadimg).append("'
			// original='").append(img).append("'
			// onerror=\"this.src='").append(errorimg).append("';\" width='28'
			// height='28'/>");
			sb.append("<img src='").append(img).append("' original='").append(img).append("' onerror=\"this.src='")
					.append(errorimg).append("';\"  width='73' height='73'/>");
			sb.append("</a><span class=\"rankno1\"></span></li><li class=\"rankhui\"><strong><a href='")
					.append(goods_url).append("' target=\"_blank\">");
			sb.append(CommUtil.substring(goods.getGoods_name(), 12))
					.append("</a></strong></li><li class=\"rankmoney\">¥").append(goods.getGoods_current_price());
			sb.append("</li></ul>");
		}

		if (mmap.get("goods2") != null) {
			Goods goods = (Goods) mmap.get("goods2");
			Accessory sAccessory=new Accessory();
			sAccessory.setId(goods.getGoods_main_photo_id());
			Accessory goods_main_photo=accessoryService.selectOne(sAccessory);
			if (goods.getGoods_main_photo_id() != null)
				img = map.get("imageWebServer") + "/" + goods_main_photo.getPath() + "/"
						+ goods_main_photo.getName() + "_small." + goods_main_photo.getExt();
			else
				img = map.get("imageWebServer") + "/" + map.get("goodsImagePath") + "/" + map.get("goodsImageName");

			goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";

			sb.append("<ul class=\"rankul\"><li class=\"rankimg\"><a href='").append(goods_url)
					.append("' target=\"_blank\">");
			// sb.append("<img src='").append(loadimg).append("'
			// original='").append(img).append("'
			// onerror=\"this.src='").append(errorimg).append("';\" width='28'
			// height='28'/>");
			sb.append("<img src='").append(img).append("' original='").append(img).append("' onerror=\"this.src='")
					.append(errorimg).append("';\"  width='73' height='73'/>");
			sb.append("</a><span class=\"rankno1\"></span></li><li class=\"rankhui\"><strong><a href='")
					.append(goods_url).append("' target=\"_blank\">");
			sb.append(CommUtil.substring(goods.getGoods_name(), 12))
					.append("</a></strong></li><li class=\"rankmoney\">¥").append(goods.getGoods_current_price());
			sb.append("</li></ul>");
		}

		if (mmap.get("goods3") != null) {
			Goods goods = (Goods) mmap.get("goods3");
			Accessory sAccessory=new Accessory();
			sAccessory.setId(goods.getGoods_main_photo_id());
			Accessory goods_main_photo=accessoryService.selectOne(sAccessory);
			if (goods.getGoods_main_photo_id() != null)
				img = map.get("imageWebServer") + "/" + goods_main_photo.getPath() + "/"
						+ goods_main_photo.getName() + "_small." + goods_main_photo.getExt();
			else
				img = map.get("imageWebServer") + "/" + map.get("goodsImagePath") + "/" + map.get("goodsImageName");

			goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";

			sb.append("<ul class=\"rankul\"><li class=\"rankimg\"><a href='").append(goods_url)
					.append("' target=\"_blank\">");
			// sb.append("<img src='").append(loadimg).append("'
			// original='").append(img).append("'
			// onerror=\"this.src='").append(errorimg).append("';\" width='28'
			// height='28'/>");
			sb.append("<img src='").append(img).append("' original='").append(img).append("' onerror=\"this.src='")
					.append(errorimg).append("';\"  width='73' height='73'/>");
			sb.append("</a><span class=\"rankno1\"></span></li><li class=\"rankhui\"><strong><a href='")
					.append(goods_url).append("' target=\"_blank\">");
			sb.append(CommUtil.substring(goods.getGoods_name(), 12))
					.append("</a></strong></li><li class=\"rankmoney\">¥").append(goods.getGoods_current_price());
			sb.append("</li></ul>");
		}

		sb.append("<ul class=\"rankul2\">");
		if (mmap.get("goods4") != null) {
			Goods goods = (Goods) mmap.get("goods4");
			goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";
			sb.append("<li><a href='").append(goods_url).append("' target='_blank'>")
					.append(CommUtil.substring(goods.getGoods_name(), 14)).append("</a></li>");
		}
		if (mmap.get("goods5") != null) {
			Goods goods = (Goods) mmap.get("goods5");
			goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";
			sb.append("<li><a href='").append(goods_url).append("' target='_blank'>")
					.append(CommUtil.substring(goods.getGoods_name(), 14)).append("</a></li>");
		}
		if (mmap.get("goods6") != null) {
			Goods goods = (Goods) mmap.get("goods6");
			goods_url = map.get("webPath") + "/goods_" + goods.getId() + ".htm";
			sb.append("<li><a href='").append(goods_url).append("' target='_blank'>")
					.append(CommUtil.substring(goods.getGoods_name(), 14)).append("</a></li>");
		}
		sb.append("</ul><div class=\"rank_advertisment\">");
		// 拼接右侧广告
		sb.append(this.gf_tools.generic_adv(url, floor.getGf_right_adv()));
		sb.append(
				"</div></div></div><div class=\"floor_brand\"><span class=\"fl_brand_sp\"></span><span class=\"flr_sp_brand\">");

		for (GoodsBrand brand : this.gf_tools.generic_brand(floor.getGf_brand_list())) {
			Accessory sAccessory=new Accessory();
			sAccessory.setId(brand.getBrandLogo_id());
			Accessory brandLogo=accessoryService.selectOne(sAccessory);
			String brand_url = map.get("webPath") + "/brand_goods_" + brand.getId() + ".htm";
			String brand_img = map.get("imageWebServer") + "/" + brandLogo.getPath() + "/"
					+ brandLogo.getName();
			sb.append("<a href='").append(brand_url).append("' target='_blank'><img src='").append(brand_img)
					.append("' width='98' height='35' /></a>");
		}
		sb.append("</span></div></div>");

		return sb.toString();
	}

}
