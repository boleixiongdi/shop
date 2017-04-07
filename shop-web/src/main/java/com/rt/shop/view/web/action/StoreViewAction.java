 package com.rt.shop.view.web.action;
 
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
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Favorite;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreClass;
import com.rt.shop.entity.StoreNav;
import com.rt.shop.entity.StorePartner;
import com.rt.shop.entity.StorePoint;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserGoodsClass;
import com.rt.shop.entity.query.EvaluateQueryObject;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.entity.query.StoreQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IFavoriteService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IStoreClassService;
import com.rt.shop.service.IStoreNavService;
import com.rt.shop.service.IStorePartnerService;
import com.rt.shop.service.IStorePointService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserGoodsClassService;
import com.rt.shop.service.IUserService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.AreaViewTools;
import com.rt.shop.view.web.tools.GoodsViewTools;
import com.rt.shop.view.web.tools.StoreViewTools;
 
 @Controller
 public class StoreViewAction
 {
 
	 @Autowired
	   private IAccessoryService accessoryService;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IStoreService storeService;
 
   @Autowired
   private IStoreClassService storeClassService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IUserGoodsClassService userGoodsClassService;
 
   @Autowired
   private IStoreNavService storenavigationService;
 
   @Autowired
   private IStorePartnerService storepartnerService;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private AreaViewTools areaViewTools;
 
   @Autowired
   private GoodsViewTools goodsViewTools;
 
   @Autowired
   private StoreViewTools storeViewTools;
   
   @Autowired
   private IUserService userService;
   
   @Autowired
   private IFavoriteService favoriteService;
   @Autowired
   private IStorePointService storePointService;
 
   @RequestMapping({"/store.htm"})
   public ModelAndView store(HttpServletRequest request, HttpServletResponse response, String id , String parameter)
   {
     String serverName = request.getServerName().toLowerCase();
     Store store = null;
     if ((id == null) && (serverName.indexOf(".") >= 0) && 
       (serverName.indexOf(".") != serverName.lastIndexOf(".")) && 
       (this.configService.getSysConfig().getSecond_domain_open())) {
       String secondDomain = serverName.substring(0, 
         serverName.indexOf("."));
       Store sStore=new Store();
       sStore.setStore_second_domain(secondDomain);
       store = this.storeService.selectOne(sStore);
     } else {
       store = this.storeService.selectById(CommWebUtil.null2Long(id));
     }
     if (store == null) {
       ModelAndView mv = new JModelAndView("error.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "不存在该店铺信息");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
       return mv;
     }
    
     String template = "default";
     if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_index.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, 
       response);
     
   
	   String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
     
     if ((shopping_view_type != null) && (!shopping_view_type.equals("")) && (shopping_view_type.equals("wap"))) {
    	 
    	 if("recommend".equals(parameter)){
    		 mv = new JModelAndView("wap/store_index_recommend.html", this.configService.getSysConfig(),
 					this.userConfigService.getUserConfig(), 1, request, response);
    	 }
    	 if("new".equals(parameter)){
    		 mv = new JModelAndView("wap/store_index_new.html", this.configService.getSysConfig(),
 					this.userConfigService.getUserConfig(), 1, request, response);
    	 }
    	 if("all".equals(parameter)){
    		 mv = new JModelAndView("wap/store_index_all.html", this.configService.getSysConfig(),
 					this.userConfigService.getUserConfig(), 1, request, response);
    	 }
    /* mv = new JModelAndView("wap/store_index.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);*/
     }
     
     if (store.getStore_status() == 2) {
       add_store_common_info(mv, store);
       mv.addObject("store", store);
       mv.addObject("nav_id", "store_index");
     } else {
       mv = new JModelAndView("error.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "店铺已经关闭或者未开通店铺");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     generic_evaluate(store, mv);
     
    
     Goods sGoods=new Goods();
     sGoods.setGoods_status(0);
     sGoods.setGoods_store_id(store.getId());
     List<Goods> gs = goodsService.selectList(sGoods);
    		// .query("Select obj from Goods obj where obj.goods_store=:store and goods_status=0", param, -1, -1);
     mv.addObject("gs", gs);
     
     generic_evaluate(store, mv);
     
     int fsstatus = 0;
     
     if(SecurityUserHolder.getCurrentUser()==null){
    	 
    	 fsstatus =0;
     }else{
    	 
    	User user = userService.selectById(SecurityUserHolder.getCurrentUser().getId());
    	
    	
    	Favorite sFavorite=new Favorite();
    	sFavorite.setUser_id(user.getId());
    	sFavorite.setStore_id(store.getId());
    	List<Favorite> fss = favoriteService.selectList(sFavorite);
    //.query("select obj from Favorite obj where obj.user=:user and obj.store=:store", param, -1, -1);
    	
    	if(fss.size()==0){
    		
    		fsstatus = 0;
    	}else {
    		
    		fsstatus = 1;
    	}
     }
     
     mv.addObject("fsstatus" , fsstatus);
     
     return mv;
   }
   
   @RequestMapping("/store_introduction.htm")
   public ModelAndView store_introduction(HttpServletRequest request, HttpServletResponse response, String storeId){
	   
	   ModelAndView mv = new JModelAndView("store_goods_list.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
	   String shopping_view_type = CommWebUtil.null2String( request.getSession().getAttribute( "shopping_view_type" ) );
	   if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/store_introduction.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	   }
	   
	   Store store = storeService.selectById(CommWebUtil.null2Long(storeId));
	   
	   mv.addObject("areaViewTools", this.areaViewTools);
	   mv.addObject("store", store);
	   generic_evaluate(store, mv);
	  
	   StorePartner sStorePartner=new StorePartner();
	   sStorePartner.setStore_id(store.getId());
	   List partners = this.storepartnerService.selectList(sStorePartner,"sequence asc");
	//.query("select obj from StorePartner obj where obj.store.id=:store_id order by obj.sequence asc", params, -1, -1);
	   mv.addObject("partners", partners);
	   mv.addObject("goodsViewTools", this.goodsViewTools);
	   
	 
	   int fsstatus = 0;
	   
	   if(SecurityUserHolder.getCurrentUser()==null){
	    	 
	    	 fsstatus =0;
	     }else{
	    	 
	    	User user = userService.selectById(SecurityUserHolder.getCurrentUser().getId());
	    	
	    	Favorite sFavorite=new Favorite();
	    	sFavorite.setUser_id(user.getId());
	    	sFavorite.setStore_id(store.getId());
	    	List<Favorite> fss = favoriteService.selectList(sFavorite);
	    	
	    	if(fss.size()==0){
	    		
	    		fsstatus = 0;
	    	}else {
	    		
	    		fsstatus = 1;
	    	}
	     }
	     
	     mv.addObject("fsstatus" , fsstatus);
		     
	   return mv;
   }
   
   @RequestMapping({"/store_left.htm"})
   public ModelAndView store_left(HttpServletRequest request, HttpServletResponse response)
   {
     Store store = this.storeService.selectById(CommWebUtil.null2Long(request
       .getAttribute("id")));
     
     String template = "default";
     if ((store != null) && (store.getTemplate() != null) && 
       (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_left.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     mv.addObject("store", store);
     add_store_common_info(mv, store);
     generic_evaluate(store, mv);
   
     StorePartner sStorePartner=new StorePartner();
	   sStorePartner.setStore_id(store.getId());
	   List partners = this.storepartnerService.selectList(sStorePartner,"sequence asc");
    //   .query("select obj from StorePartner obj where obj.store.id=:store_id order by obj.sequence asc", 
     mv.addObject("partners", partners);
     mv.addObject("goodsViewTools", this.goodsViewTools);
     return mv;
   }
 
   @RequestMapping({"/store_left1.htm"})
   public ModelAndView store_left1(HttpServletRequest request, HttpServletResponse response) {
     Store store = this.storeService.selectById(CommWebUtil.null2Long(request
       .getAttribute("id")));
     String template = "default";
     if ((store != null) && (store.getTemplate() != null) && 
       (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_left1.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     mv.addObject("store", store);
     add_store_common_info(mv, store);
     StorePartner sStorePartner=new StorePartner();
	   sStorePartner.setStore_id(store.getId());
	   List partners = this.storepartnerService.selectList(sStorePartner,"sequence asc");
     //  .query("select obj from StorePartner obj where obj.store.id=:store_id order by obj.sequence asc", 
     mv.addObject("partners", partners);
     return mv;
   }
 
   @RequestMapping({"/store_left2.htm"})
   public ModelAndView store_left2(HttpServletRequest request, HttpServletResponse response) {
     Store store = this.storeService.selectById(CommWebUtil.null2Long(request
       .getAttribute("id")));
     String template = "default";
     if ((store != null) && (store.getTemplate() != null) && 
       (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_left2.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     mv.addObject("store", store);
     add_store_common_info(mv, store);
     return mv;
   }
 
   @RequestMapping({"/store_nav.htm"})
   public ModelAndView store_nav(HttpServletRequest request, HttpServletResponse response) {
     Long id = CommWebUtil.null2Long(request.getAttribute("id"));
     Store store = this.storeService.selectById(id);
     String template = "default";
     if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_nav.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (store.getStore_status() == 2) {
      
       StoreNav sStoreNav=new StoreNav();
       sStoreNav.setStore_id(store.getId());
       sStoreNav.setDisplay(Boolean.valueOf(true));
       List<StoreNav> navs = this.storenavigationService.selectList(sStoreNav, "sequence asc");
     //    .query("select obj from StoreNavigation obj where obj.store.id=:store_id and obj.display=:display order by obj.sequence asc", 
       mv.addObject("navs", navs);
       mv.addObject("store", store);
       String goods_view = CommWebUtil.null2String(request
         .getAttribute("goods_view"));
       mv.addObject("goods_view", Boolean.valueOf(CommWebUtil.null2Boolean(goods_view)));
       mv.addObject("goods_id", 
         CommWebUtil.null2String(request.getAttribute("goods_id")));
       mv.addObject("goods_list", 
         Boolean.valueOf(CommWebUtil.null2Boolean(request.getAttribute("goods_list"))));
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "店铺信息错误");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/store_credit.htm"})
   public ModelAndView store_credit(HttpServletRequest request, HttpServletResponse response, String id) {
     Store store = this.storeService.selectById(CommWebUtil.null2Long(id));
     String template = "default";
     if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_credit.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (store.getStore_status() == 2) {
       EvaluateQueryObject qo = new EvaluateQueryObject("1", mv, 
         "addTime", "desc");
       qo.addQuery("obj.of.store.id", 
         new SysMap("store_id", store.getId()), "=");
       Page pList = this.evaluateService.selectPage(new Page<Evaluate>(Integer.valueOf(CommUtil.null2Int(1)), 12), null);
       
       CommWebUtil.saveIPageList2ModelAndView(CommWebUtil.getURL(request) + 
         "/store_eva.htm", "", "", pList, mv);
       mv.addObject("store", store);
       mv.addObject("nav_id", "store_credit");
       mv.addObject("storeViewTools", this.storeViewTools);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "店铺信息错误");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/store_eva.htm"})
   public ModelAndView store_eva(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String eva_val)
   {
     Store store = this.storeService.selectById(Long.valueOf(Long.parseLong(id)));
     String template = "default";
     if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_eva.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (store.getStore_status() == 2) {
       EvaluateQueryObject qo = new EvaluateQueryObject(currentPage, mv, 
         "addTime", "desc");
       qo.addQuery("obj.evaluate_goods.goods_store.id", 
         new SysMap("store_id", store.getId()), "=");
       if (!CommWebUtil.null2String(eva_val).equals("")) {
         qo.addQuery("obj.evaluate_buyer_val", 
           new SysMap("evaluate_buyer_val", Integer.valueOf(CommWebUtil.null2Int(eva_val))), "=");
       }
       Page pList = this.evaluateService.selectPage(new Page<Evaluate>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       
       CommWebUtil.saveIPageList2ModelAndView(CommWebUtil.getURL(request) + 
         "/store_eva.htm", "", 
         "&eva_val=" + CommWebUtil.null2String(eva_val), pList, mv);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "店铺信息错误");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/store_info.htm"})
   public ModelAndView store_info(HttpServletRequest request, HttpServletResponse response, String id) {
     Store store = this.storeService.selectById(Long.valueOf(Long.parseLong(id)));
     String template = "default";
     if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     Accessory stBan=accessoryService.selectById(store.getStore_banner_id());
     store.setStore_banner(stBan);
     
     Accessory logo=accessoryService.selectById(store.getStore_logo_id());
     store.setStore_logo(logo);
     ModelAndView mv = new JModelAndView(template + "/store_info.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     if (store.getStore_status() == 2) {
       mv.addObject("store", store);
       mv.addObject("nav_id", "store_info");
       mv.addObject("areaViewTools", this.areaViewTools);
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "店铺信息错误");
       mv.addObject("url", CommWebUtil.getURL(request) + "/index.htm");
     }
     return mv;
   }
 
   @RequestMapping({"/store_url.htm"})
   public ModelAndView store_url(HttpServletRequest request, HttpServletResponse response, String id) {
     StoreNav nav = this.storenavigationService.selectById(
       CommWebUtil.null2Long(id));
     String template = "default";
     Store store=storeService.selectById(nav.getStore_id());
     if ((store.getTemplate() != null) && 
       (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/store_url.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     mv.addObject("store", store);
     mv.addObject("nav", nav);
     mv.addObject("nav_id", nav.getId());
     return mv;
   }
 
   private void add_store_common_info(ModelAndView mv, Store store) {
    User sUser=new User();
    sUser.setStore_id(store.getId());
     User user=userService.selectOne(sUser);
    
   
     String sql11="where user_id='"+user.getId()+"' and display=1 and parent_id is null order by sequence asc";
     List ugcs = this.userGoodsClassService.selectList(sql11,null);
     
      // .query("select obj from UserGoodsClass obj where obj.user.id=:user_id and obj.display=:display and obj.parent.id is null order by obj.sequence asc", 
       
     mv.addObject("ugcs", ugcs);
  
    
     Page<Goods> page = new Page<Goods>(0, 8);
	 String sql="where goods_recommend="+Boolean.valueOf(true)+" and goods_store_id="+store.getId()+" and goods_status=0 order by addTime desc";
     List<Goods> goods_recommend = this.goodsService.selectPage(page, sql, null).getRecords();
     for(Goods g : goods_recommend){
    	 Accessory ac=accessoryService.selectById(g.getGoods_main_photo_id());
    	 g.setGoods_main_photo(ac);
     }
   
     Page<Goods> page1 = new Page<Goods>(0, 12);
	 String sql1="where goods_store_id="+store.getId()+" and goods_status=0 order by addTime desc ";
     List<Goods> goods_new = this.goodsService.selectPage(page1, sql1, null).getRecords();
     for(Goods g : goods_new){
    	 Accessory ac=accessoryService.selectById(g.getGoods_main_photo_id());
    	 g.setGoods_main_photo(ac);
     }
     mv.addObject("goods_recommend", goods_recommend);
     mv.addObject("goods_new", goods_new);
     mv.addObject("goodsViewTools", this.goodsViewTools);
     mv.addObject("storeViewTools", this.storeViewTools);
     mv.addObject("areaViewTools", this.areaViewTools);
   }
 
   @RequestMapping({"/store_list.htm"})
   public ModelAndView store_list(HttpServletRequest request, HttpServletResponse response, String id, String sc_id, String currentPage, String orderType, String store_name, String store_ower, String type)
   {
     ModelAndView mv = new JModelAndView("store_list.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     List scs = this.storeClassService.selectList("where parent_id is null", "sequence asc");
     //  .query("select obj from StoreClass obj where obj.parent.id is null order by obj.sequence asc", 
     mv.addObject("scs", scs);
     StoreQueryObject sqo = new StoreQueryObject(currentPage, mv, 
       "store_credit", orderType);
     if ((sc_id != null) && (!sc_id.equals(""))) {
       sqo.addQuery("obj.sc.id", 
         new SysMap("sc_id", CommWebUtil.null2Long(sc_id)), "=");
     }
     if ((store_name != null) && (!store_name.equals(""))) {
       sqo.addQuery("obj.store_name", 
         new SysMap("store_name", "%" + 
         store_name + "%"), "like");
       mv.addObject("store_name", store_name);
     }
     if ((store_ower != null) && (!store_ower.equals(""))) {
       sqo.addQuery("obj.store_ower", 
         new SysMap("store_ower", "%" + 
         store_ower + "%"), "like");
       mv.addObject("store_ower", store_ower);
     }
     sqo.addQuery("obj.store_status", new SysMap("store_status", Integer.valueOf(2)), "=");
     Page pList = this.storeService.selectPage(new Page<Store>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("storeViewTools", this.storeViewTools);
     mv.addObject("type", type);
     return mv;
   }
 
   @RequestMapping({"/store_goods_search.htm"})
   public ModelAndView store_goods_search(HttpServletRequest request, HttpServletResponse response, String keyword, String store_id, String currentPage)
   {
     Store store = this.storeService.selectById(Long.valueOf(Long.parseLong(store_id)));
     String template = "default";
     if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + 
       "/store_goods_search.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     GoodsQueryObject gqo = new GoodsQueryObject(currentPage, mv, null, null);
     gqo.addQuery("obj.goods_store.id", 
       new SysMap("store_id", CommWebUtil.null2Long(store_id)), "=");
     gqo.addQuery("obj.goods_name", 
       new SysMap("goods_name", "%" + keyword + 
       "%"), "like");
     gqo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
     gqo.setPageSize(Integer.valueOf(20));
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("keyword", keyword);
     mv.addObject("store", store);
     return mv;
   }
 
   @RequestMapping({"/store_head.htm"})
   public ModelAndView store_head(HttpServletRequest request, HttpServletResponse response)
   {
     ModelAndView mv = new JModelAndView("store_head.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     Store store = this.storeService.selectById(CommWebUtil.null2Long(request
       .getAttribute("store_id")));
     generic_evaluate(store, mv);
     mv.addObject("store", store);
     mv.addObject("storeViewTools", this.storeViewTools);
     return mv;
   }
 
   private void generic_evaluate(Store store, ModelAndView mv) {
     double description_result = 0.0D;
     double service_result = 0.0D;
     double ship_result = 0.0D;
     StorePoint sStorePoint=new StorePoint();
     sStorePoint.setStore_id(store.getId());
     List<StorePoint> spList=storePointService.selectList(sStorePoint);
     if ((store != null) && (store.getSc_id()!= null) && (spList != null)) {
       StoreClass sc = this.storeClassService.selectById(store.getSc_id());
       float description_evaluate = CommWebUtil.null2Float(sc
         .getDescription_evaluate());
       float service_evaluate = CommWebUtil.null2Float(sc
         .getService_evaluate());
       float ship_evaluate = CommWebUtil.null2Float(sc.getShip_evaluate());
       float store_description_evaluate = CommWebUtil.null2Float(spList.get(0).getDescription_evaluate());
       float store_service_evaluate = CommWebUtil.null2Float(spList.get(0)
         .getService_evaluate());
       float store_ship_evaluate = CommWebUtil.null2Float(spList.get(0)
         .getShip_evaluate());
 
       description_result = CommWebUtil.div(Float.valueOf(store_description_evaluate - 
         description_evaluate), Float.valueOf(description_evaluate));
       service_result = CommWebUtil.div(Float.valueOf(store_service_evaluate - 
         service_evaluate), Float.valueOf(service_evaluate));
       ship_result = CommWebUtil.div(Float.valueOf(store_ship_evaluate - ship_evaluate), 
         Float.valueOf(ship_evaluate));
     }
     if (description_result > 0.0D) {
       mv.addObject("description_css", "better");
       mv.addObject("description_type", "高于");
       mv.addObject(
         "description_result", 
         CommWebUtil.null2String(Double.valueOf(CommWebUtil.mul(Double.valueOf(description_result), Integer.valueOf(100)) > 100.0D ? 100.0D : 
         CommWebUtil.mul(Double.valueOf(description_result), Integer.valueOf(100)))) + 
         "%");
     }
     if (description_result == 0.0D) {
       mv.addObject("description_css", "better");
       mv.addObject("description_type", "持平");
       mv.addObject("description_result", "-----");
     }
     if (description_result < 0.0D) {
       mv.addObject("description_css", "lower");
       mv.addObject("description_type", "低于");
       mv.addObject(
         "description_result", 
         CommWebUtil.null2String(Double.valueOf(CommWebUtil.mul(Double.valueOf(-description_result), Integer.valueOf(100)))) + 
         "%");
     }
     if (service_result > 0.0D) {
       mv.addObject("service_css", "better");
       mv.addObject("service_type", "高于");
       mv.addObject("service_result", 
         CommWebUtil.null2String(Double.valueOf(CommWebUtil.mul(Double.valueOf(service_result), Integer.valueOf(100)))) + 
         "%");
     }
     if (service_result == 0.0D) {
       mv.addObject("service_css", "better");
       mv.addObject("service_type", "持平");
       mv.addObject("service_result", "-----");
     }
     if (service_result < 0.0D) {
       mv.addObject("service_css", "lower");
       mv.addObject("service_type", "低于");
       mv.addObject("service_result", 
         CommWebUtil.null2String(Double.valueOf(CommWebUtil.mul(Double.valueOf(-service_result), Integer.valueOf(100)))) + 
         "%");
     }
     if (ship_result > 0.0D) {
       mv.addObject("ship_css", "better");
       mv.addObject("ship_type", "高于");
       mv.addObject("ship_result", 
         CommWebUtil.null2String(Double.valueOf(CommWebUtil.mul(Double.valueOf(ship_result), Integer.valueOf(100)))) + "%");
     }
     if (ship_result == 0.0D) {
       mv.addObject("ship_css", "better");
       mv.addObject("ship_type", "持平");
       mv.addObject("ship_result", "-----");
     }
     if (ship_result < 0.0D) {
       mv.addObject("ship_css", "lower");
       mv.addObject("ship_type", "低于");
       mv.addObject("ship_result", 
         CommWebUtil.null2String(Double.valueOf(CommWebUtil.mul(Double.valueOf(-ship_result), Integer.valueOf(100)))) + "%");
     }
   }
 }


 
 
 