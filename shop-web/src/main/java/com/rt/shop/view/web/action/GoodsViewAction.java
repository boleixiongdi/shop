 package com.rt.shop.view.web.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Area;
import com.rt.shop.entity.Consult;
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Favorite;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.entity.GoodsPhoto;
import com.rt.shop.entity.GoodsSpec;
import com.rt.shop.entity.GoodsSpecProperty;
import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.GoodsType;
import com.rt.shop.entity.GoodsTypeProperty;
import com.rt.shop.entity.GoodsTypeSpec;
import com.rt.shop.entity.Group;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreCart;
import com.rt.shop.entity.StoreClass;
import com.rt.shop.entity.StorePoint;
import com.rt.shop.entity.Transport;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserGoodsClass;
import com.rt.shop.entity.query.ConsultQueryObject;
import com.rt.shop.entity.query.EvaluateQueryObject;
import com.rt.shop.entity.query.GoodsCartQueryObject;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAreaService;
import com.rt.shop.service.IConsultService;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IFavoriteService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsPhotoService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGoodsSpecPropertyService;
import com.rt.shop.service.IGoodsSpecificationService;
import com.rt.shop.service.IGoodsTypePropertyService;
import com.rt.shop.service.IGoodsTypeService;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.IGroupService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IStoreCartService;
import com.rt.shop.service.IStoreClassService;
import com.rt.shop.service.IStorePointService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITransportService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserGoodsClassService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.UserTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.AreaViewTools;
import com.rt.shop.view.web.tools.GoodsViewTools;
import com.rt.shop.view.web.tools.IpAddress;
import com.rt.shop.view.web.tools.StoreViewTools;
import com.rt.shop.view.web.tools.TransportTools;
 
 @Controller
 public class GoodsViewAction
 {
 
	 CommWebUtil CommWebUtil=new CommWebUtil();
   @Autowired
   private ISysConfigService configService;
   @Autowired
   private IAccessoryService accessoryService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsService goodsService;
   
   @Autowired
   private IStorePointService storePointService;
 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   @Autowired
   private IUserGoodsClassService userGoodsClassService;
 
   @Autowired
   private IStoreService storeService;
   @Autowired
   private IGroupGoodsService groupGoodsService;
 
   @Autowired
   private IEvaluateService evaluateService;
 
   @Autowired
   private IOrderFormService orderFormService;
 
   @Autowired
   private IGoodsCartService goodsCartService;
   @Autowired
   private IGoodsTypeService goodsTypeService;
 
   @Autowired
   private IConsultService consultService;
 
   @Autowired
   private IGoodsPhotoService goodsPhotoService;
   
   @Autowired
   private IGoodsBrandService brandService;
 
   @Autowired
   private IGoodsSpecPropertyService goodsSpecPropertyService;
 
   @Autowired
   private IGoodsTypePropertyService goodsTypePropertyService;
 
   @Autowired
   private IAreaService areaService;
   @Autowired
   private IGroupService groupService;
 
   @Autowired
   private IStoreClassService storeClassService;
 
   @Autowired
   private AreaViewTools areaViewTools;
 
   @Autowired
   private GoodsViewTools goodsViewTools;
 
   @Autowired
   private StoreViewTools storeViewTools;
 
   @Autowired
   private UserTools userTools;
 
   @Autowired
   private TransportTools transportTools;
   
   @Autowired
   private IStoreCartService storeCartService;
   
   @Autowired
   private IUserService userService;
   
   @Autowired
   private IFavoriteService favoriteService;
   @Autowired
   private IGoodsSpecificationService  goodsSpecificationService;
 
   @RequestMapping({"/goods_list.htm"})
   public ModelAndView goods_list(HttpServletRequest request, HttpServletResponse response, String gc_id, String store_id, String recommend, String currentPage, String orderBy, String orderType, String begin_price, String end_price)
   {
     UserGoodsClass ugc = this.userGoodsClassService.selectById(
       CommUtil.null2Long(gc_id));
     String template = "default";
     Store store = this.storeService
       .selectById(CommUtil.null2Long(store_id));
     if (store != null) {
       if ((store.getTemplate() != null) && (!store.getTemplate().equals(""))) {
         template = store.getTemplate();
       }
       ModelAndView mv = new JModelAndView(template + "/goods_list.html", 
         this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       GoodsQueryObject gqo = new GoodsQueryObject(currentPage, mv, 
         orderBy, orderType);
       gqo.addQuery("obj.goods_store.id", 
         new SysMap("goods_store_id", 
         store.getId()), "=");
       if (ugc != null) {
         Set<Long> ids = genericUserGcIds(ugc);
         List ugc_list = new ArrayList();
         for (Long g_id : ids) {
           UserGoodsClass temp_ugc = this.userGoodsClassService
             .selectById(g_id);
           ugc_list.add(temp_ugc);
         }
         gqo.addQuery("ugc", ugc, "obj.goods_ugcs", "member of");
         for (int i = 0; i < ugc_list.size(); i++)
           gqo.addQuery("ugc" + i, ugc_list.get(i), "obj.goods_ugcs", 
             "member of", "or");
       }
       else {
         ugc = new UserGoodsClass();
         ugc.setClassName("全部商品");
         mv.addObject("ugc", ugc);
       }
       if ((recommend != null) && (!recommend.equals(""))) {
         gqo.addQuery("obj.goods_recommend", 
           new SysMap("goods_recommend", Boolean.valueOf(CommUtil.null2Boolean(recommend))), 
           "=");
       }
       gqo.setPageSize(Integer.valueOf(20));
       if ((begin_price != null) && (!begin_price.equals(""))) {
         gqo.addQuery("obj.store_price", 
           new SysMap("begin_price", 
           BigDecimal.valueOf(CommUtil.null2Double(begin_price))), 
           ">=");
       }
       if ((end_price != null) && (!end_price.equals(""))) {
         gqo.addQuery("obj.store_price", 
           new SysMap("end_price", 
           BigDecimal.valueOf(CommUtil.null2Double(end_price))), 
           "<=");
       }
       Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
       
       String url = this.configService.getSysConfig().getAddress();
       if ((url == null) || (url.equals(""))) {
         url = CommUtil.getURL(request);
       }
       CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
       mv.addObject("ugc", ugc);
       mv.addObject("store", store);
       mv.addObject("recommend", recommend);
       mv.addObject("begin_price", begin_price);
       mv.addObject("end_price", end_price);
       mv.addObject("goodsViewTools", this.goodsViewTools);
       mv.addObject("storeViewTools", this.storeViewTools);
       mv.addObject("areaViewTools", this.areaViewTools);
       return mv;
     }
     ModelAndView mv = new JModelAndView("error.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, 
       response);
     mv.addObject("op_title", "请求参数错误");
     mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
     return mv;
   }
 
   
   
   private Set<Long> genericUserGcIds(UserGoodsClass ugc){
	   if(ugc!=null){
		   Set ids = new HashSet();
		     ids.add(ugc.getId());
		     UserGoodsClass sUserGoodsClass=new UserGoodsClass();
		     sUserGoodsClass.setParent_id(ugc.getId());
		     List<UserGoodsClass> childs=userGoodsClassService.selectList(sUserGoodsClass);
		     if(childs!=null && childs.size()>0){
		     for (UserGoodsClass child : childs) {
		       Set<Long> cids = genericUserGcIds(child);
		       for (Long cid : cids) {
		         ids.add(cid);
		       }
		       ids.add(child.getId());
		     }
		     return ids;
	   }
	   }
	return null;
   }
 
   @RequestMapping({"/goods.htm"})
   public ModelAndView goods(HttpServletRequest request, HttpServletResponse response, String id)
   {
     ModelAndView mv = null;
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
     Goods obj = this.goodsService.selectById(Long.valueOf(Long.parseLong(id)));
     
     String sql1="s LEFT JOIN  shopping_goods_spec t on  s.id=t.spec_id where t.goods_id="+obj.getId();
     GoodsSpec sp=new GoodsSpec();
     sp.setGoods_id(obj.getId());
     List<GoodsSpecProperty> goods_specs =goodsSpecPropertyService.selectGSPByGoodsId(sp);
     obj.setGoods_specs(goods_specs);
     GoodsPhoto gp=new GoodsPhoto();
     gp.setGoods_id(obj.getId());
     List<GoodsPhoto> gpList=goodsPhotoService.selectList(gp);
    List<Long> acs=new ArrayList<Long>(gpList.size());
    if(gpList!=null && gpList.size()>0){
    	for(int i=0;i<gpList.size();i++){
       	 acs.add(gpList.get(i).getPhoto_id());
        }
        List<Accessory> goodsPhoto=accessoryService.selectBatchIds(acs);
        obj.setGoods_photos(goodsPhoto);
    }
     
     if (obj.getGoods_status() == 0) {
       String template = "default";
       Store store=storeService.selectById(obj.getGoods_store_id());
       if ((store.getTemplate() != null) && 
         (!store.getTemplate().equals(""))) {
         template = store.getTemplate();
       }
       mv = new JModelAndView(template+"/store_goods.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, response);
       User user1=SecurityUserHolder.getSessionLoginUser();
      
       if(user1!=null){
    	   if(user1.getStore_id()==obj.getGoods_store_id()){
  	      	 mv.addObject("selfGoods", "1");
  	       }else{
  	      	 mv.addObject("selfGoods", "0");
  	       } 
    	  
       }else{
    	   mv.addObject("userId", 0);
    	   mv.addObject("selfGoods", "0");
       }
      
      
       Long storeId = store.getId();
       mv.addObject("store",store);
       mv.addObject("storeId",storeId);
       
	   if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		   mv = new JModelAndView("wap/store_goods.html", this.configService.getSysConfig(), 
			         this.userConfigService.getUserConfig(), 1, request, response);
	   }
       obj.setGoods_click(obj.getGoods_click() + 1);
       if ((this.configService.getSysConfig().getZtc_status()) && (obj.getZtc_status() == 2)) {
         obj.setZtc_click_num(obj.getZtc_click_num() + 1);
       }
       if ((obj.getGroup_id() != null) && (obj.getGroup_buy() == 2)) {
         Group group = groupService.selectById(obj.getGroup_id());
         if (group.getEndTime().before(new Date())) {
           obj.setGroup_id(0L);
           obj.setGroup_buy(0);
           obj.setGoods_current_price(obj.getStore_price());
         }
       }
       this.goodsService.updateSelectiveById(obj);
       Accessory ac=accessoryService.selectById(obj.getGoods_main_photo_id());
       obj.setGoods_main_photo(ac);
       Goods sGoods=new Goods();
       sGoods.setGoods_store_id(store.getId());
       if (store.getStore_status() == 2) {
         mv.addObject("obj", obj);
         mv.addObject("store", store);
         mv.addObject("goods_count", goodsService.selectCount(sGoods));
        User sUser=new User();
        sUser.setStore_id(store.getId());
        
        
         String sql="where display=1 and user_id='"+userService.selectOne(sUser).getId()+"' and parent_id is null order by sequence asc";
         List<UserGoodsClass> ugcs = this.userGoodsClassService.selectList(sql, null);
         mv.addObject("ugcs", ugcs);

         StringBuffer rsb=new StringBuffer(" where goods_status=0 and goods_recommend=1 and goods_store_id="+obj.getGoods_store_id()+" and id!="+obj.getId()+"");
         List<Goods> rgoodsL=goodsService.selectList(new Goods(), rsb.toString(), "addTime desc");
        for(Goods g : rgoodsL){
        	Accessory ac1=accessoryService.selectById(g.getGoods_main_photo_id());
        	g.setGoods_main_photo(ac1);
        }
         mv.addObject("goods_recommend_list", rgoodsL);
        
         Evaluate sEvaluate=new Evaluate();
         sEvaluate.setEvaluate_goods_id(obj.getId());
         sEvaluate.setEvaluate_type("buyer");
         List<Evaluate> evas = this.evaluateService.selectList(sEvaluate);
         mv.addObject("eva_count", Integer.valueOf(evas.size()));
         mv.addObject("goodsViewTools", this.goodsViewTools);
         mv.addObject("storeViewTools", this.storeViewTools);
         mv.addObject("areaViewTools", this.areaViewTools);
         mv.addObject("transportTools", this.transportTools);
 
         List<Goods> user_viewed_goods = (List)request.getSession(false).getAttribute("user_viewed_goods");
         if (user_viewed_goods == null) {
        	 user_viewed_goods = new ArrayList();
         }else{
        	 for(Goods g : user_viewed_goods){
             	Accessory ac1=accessoryService.selectById(g.getGoods_main_photo_id());
             	g.setGoods_main_photo(ac1);
             }
         }
         boolean add = true;
         for (Goods goods : user_viewed_goods) {
           if (goods.getId().equals(obj.getId())) {
             add = false;
             break;
           }
         }
         if (add) {
           if (user_viewed_goods.size() >= 4)
             user_viewed_goods.set(1, obj);
           else
             user_viewed_goods.add(obj);
         }
         request.getSession(false).setAttribute("user_viewed_goods", user_viewed_goods);
 
         IpAddress ipAddr = IpAddress.getInstance();
         String current_ip = CommUtil.getIpAddr(request);
         String current_city = ipAddr.IpStringToAddress(current_ip);
         if ((current_city == null) || (current_city.equals(""))) {
           current_city = "全国";
         }
 
         mv.addObject("current_city", current_city);
        
         List areas = this.areaService.selectList("where parent_id is null", "sequence asc");
         mv.addObject("areas", areas);
         generic_evaluate(store, mv);
       } else {
    	   mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
    			   this.userConfigService.getUserConfig(), 1, request, response);
    	   if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
    		   mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(), 
    		           this.userConfigService.getUserConfig(), 1, request, response);
    	   }
    	   mv.addObject("op_title", "店铺够开通，拒绝访问");
    	   mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
       if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
    	   mv = new JModelAndView("wap/error.html", this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 1, request, response);
       }
       mv.addObject("op_title", "该商品未上架，不允许查看");
       mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
     }
     int tc = 0;
     int fgstatus = 0;
     if(SecurityUserHolder.getCurrentUser()==null){
    	 
    	 fgstatus =0;
    	 tc =0;
     }else{
    	 User user = userService.selectById(SecurityUserHolder.getCurrentUser().getId());
    	 Goods goods = goodsService.selectById(Long.parseLong(id));
         StoreCart sStoreCart=new StoreCart();
         sStoreCart.setUser_id(user.getId());
         sStoreCart.setSc_status(0);
         List<StoreCart> scs = storeCartService.selectList(sStoreCart);
     
         Favorite sFavorite=new Favorite();
         sFavorite.setUser_id(user.getId());
         sFavorite.setGoods_id(goods.getId());
         List<Favorite> fgs = favoriteService.selectList(sFavorite);
         
         if(fgs.size() == 0){
        	 fgstatus = 0;
         }else{
        	 fgstatus = 1;
         }
         
         for(StoreCart storecart : scs){
        	 GoodsCart gc1=new GoodsCart();
 			gc1.setSc_id(storecart.getStore_id());
 			List<GoodsCart> goodsCarts=goodsCartService.selectList(gc1);
			 for(GoodsCart gc : goodsCarts){
				 tc += gc.getCount();
			 }
         }
     }
     
     mv.addObject("tc", tc);
     mv.addObject("fgstatus",fgstatus);
     
     return mv;
   }
 
   @RequestMapping({"/store_goods_list.htm"})
   public ModelAndView store_goods_list(HttpServletRequest request, HttpServletResponse response, String gc_id, String currentPage, String orderBy, String orderType, String store_price_begin, String store_price_end, String brand_ids, String gs_ids, String properties, String op, String goods_name, String area_name, String area_id, String goods_view, String all_property_status, String detail_property_status)
   {
     ModelAndView mv = new JModelAndView("store_goods_list.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     String shopping_view_type = CommUtil.isMobileDeviceValue(request.getHeader("user-agent"));
	 if( (shopping_view_type != null) && (!shopping_view_type.equals( "" )) && (shopping_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/store_goods_list.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	 }
     GoodsClass gc = this.goodsClassService.selectById(CommUtil.null2Long(gc_id));
     if(gc.getGoodsType_id()!=null){
    	 GoodsType goodsType=goodsTypeService.selectById(gc.getGoodsType_id());
         gc.setGoodsType(goodsType);
         
         String sql1="s LEFT JOIN  shopping_goodstype_spec a on s.id=a.spec_id where a.type_id="+goodsType.getId();
         GoodsTypeSpec gts=new GoodsTypeSpec();
         gts.setType_id(goodsType.getId());
         List<GoodsSpecification> gssList=goodsSpecificationService.selectGSFByGoodsTypeId(gts);
         goodsType.setGss(gssList);
         GoodsTypeProperty sGoodsTypeProperty=new GoodsTypeProperty();
         sGoodsTypeProperty.setGoodsType_id(goodsType.getId());
         List<GoodsTypeProperty> propertiesList=goodsTypePropertyService.selectList(sGoodsTypeProperty);
         goodsType.setProperties(propertiesList);
     }
    
     mv.addObject("gc", gc);
     if ((orderBy == null) || (orderBy.equals(""))) {
       orderBy = "addTime";
     }
     if ((op != null) && (!op.equals(""))) {
       mv.addObject("op", op);
     }
     String orderBy1 = orderBy;
     if (this.configService.getSysConfig().getZtc_status()) {
       orderBy = "ztc_dredge_price ";
     }
     GoodsQueryObject gqo = new GoodsQueryObject(currentPage, mv, orderBy, orderType);
     Set ids = genericIds(gc);
     StringBuffer sb=new StringBuffer(" where goods_status=0 ");
     if(ids!=null && ids.size()>0){
    	 String x=CommUtil.exactSetToString(ids);
		 sb.append(" and gc_id in ("+x+") ");
     }
    
     if ((store_price_begin != null) && (!store_price_begin.equals(""))) {
      sb.append(" and store_price>="+BigDecimal.valueOf(CommUtil.null2Double(store_price_begin)));
       mv.addObject("store_price_begin", store_price_begin);
     }
     if ((store_price_end != null) && (!store_price_end.equals(""))) {
      sb.append(" and store_price<="+BigDecimal.valueOf(CommUtil.null2Double(store_price_end)));
       mv.addObject("store_price_end", store_price_end);
     }
     if ((goods_name != null) && (!goods_name.equals(""))) {
    	 sb.append(" and goods_name like "+"%" + goods_name.trim() + "%");
        mv.addObject("goods_name", goods_name);
     }
 //  按地区查询
//     if ((area_id != null) && (!area_id.equals(""))) {
//       Area area = this.areaService.selectById(CommUtil.null2Long(area_id));
//       mv.addObject("area", area);
//       Set area_ids = getAreaChildIds(area);
//       Map p_area = new HashMap();
//       p_area.put("area_ids", area_ids);
//       gqo.addQuery("obj.goods_store.area.id in (:area_ids)", p_area);
//     }
     if ((area_name != null) && (!area_name.equals(""))) {
       mv.addObject("area_name", area_name);
       Area sArea=new Area();
       String areaSql=" where areaName like "+"%" + area_name.trim() + "%";
       List likes_areas = this.areaService.selectList(sArea,areaSql);
    		//   .query("select obj from Area obj where obj.areaName like:area_name", like_area, -1, -1);
       Set like_area_ids = getArrayAreaChildIds(likes_areas);
//       like_area.clear();
//       like_area.put("like_area_ids", like_area_ids);
//       gqo.addQuery("obj.goods_store.area.id in (:like_area_ids)", like_area);
     }
 
     gqo.addQuery("obj.goods_store.store_status", new SysMap("store_status", Integer.valueOf(2)), "=");
    
     List goods_property = new ArrayList();
     if (!CommUtil.null2String(brand_ids).equals("")) {
       String[] brand_id_list = brand_ids.substring(1).split("\\|");
       if (brand_id_list.length == 1) {
         String brand_id = brand_id_list[0];
         String[] brand_info_list = brand_id.split(",");
         gqo.addQuery("obj.goods_brand.id", new SysMap("brand_id", CommUtil.null2Long(brand_info_list[0])), "=", "and");
         Map map = new HashMap();
         GoodsBrand brand = this.brandService.selectById(CommUtil.null2Long(brand_info_list[0]));
         map.put("name", "品牌");
         map.put("value", brand.getName());
         map.put("type", "brand");
         map.put("id", brand.getId());
         goods_property.add(map);
       } else {
         for (int i = 0; i < brand_id_list.length; i++) {
           String brand_id = brand_id_list[i];
           if (i == 0) {
             String[] brand_info_list = brand_id.split(",");
             gqo.addQuery("and (obj.goods_brand.id=" + CommUtil.null2Long(brand_info_list[0]), null);
             Map map = new HashMap();
             GoodsBrand brand = this.brandService.selectById(CommUtil.null2Long(brand_info_list[0]));
             map.put("name", "品牌");
             map.put("value", brand.getName());
             map.put("type", "brand");
             map.put("id", brand.getId());
             goods_property.add(map);
           } else if (i == brand_id_list.length - 1) {
             String[] brand_info_list = brand_id.split(",");
             gqo.addQuery("or obj.goods_brand.id=" + CommUtil.null2Long(brand_info_list[0]) + ")", null);
             Map map = new HashMap();
             GoodsBrand brand = this.brandService.selectById(CommUtil.null2Long(brand_info_list[0]));
             map.put("name", "品牌");
             map.put("value", brand.getName());
             map.put("type", "brand");
             map.put("id", brand.getId());
             goods_property.add(map);
           } else {
             String[] brand_info_list = brand_id.split(",");
             gqo.addQuery("or obj.goods_brand.id=" + CommUtil.null2Long(brand_info_list[0]), null);
             Map map = new HashMap();
             GoodsBrand brand = this.brandService.selectById(CommUtil.null2Long(brand_info_list[0]));
             map.put("name", "品牌");
             map.put("value", brand.getName());
             map.put("type", "brand");
             map.put("id", brand.getId());
             goods_property.add(map);
           }
         }
       }
       mv.addObject("brand_ids", brand_ids);
     }
     if (!CommUtil.null2String(gs_ids).equals("")) {
       List gsp_lists = generic_gsp(gs_ids);
 
       for (int j = 0; j < gsp_lists.size(); j++) {
         List gsp_list = (List)gsp_lists.get(j);
         if (gsp_list.size() == 1) {
           GoodsSpecProperty gsp = (GoodsSpecProperty)gsp_list.get(0);
           GoodsSpecification gsf=goodsSpecificationService.selectById(gsp.getSpec_id1());
           gqo.addQuery("gsp" + j, gsp, "obj.goods_specs", "member of", "and");
           Map map = new HashMap();
           map.put("name", gsf.getName());
           map.put("value", gsp.getValue());
           map.put("type", "gs");
           map.put("id", gsp.getId());
           goods_property.add(map);
         } else {
           for (int i = 0; i < gsp_list.size(); i++) {
             if (i == 0) {
               GoodsSpecProperty gsp = (GoodsSpecProperty)gsp_list.get(i);
               GoodsSpecification gsf=goodsSpecificationService.selectById(gsp.getSpec_id1());
               gqo.addQuery("gsp" + j + i, gsp, "obj.goods_specs", "member of", "and(");
               Map map = new HashMap();
               map.put("name", gsf.getName());
               map.put("value", gsp.getValue());
               map.put("type", "gs");
               map.put("id", gsp.getId());
               goods_property.add(map);
             } else if (i == gsp_list.size() - 1) {
               GoodsSpecProperty gsp = (GoodsSpecProperty)gsp_list.get(i);
               GoodsSpecification gsf=goodsSpecificationService.selectById(gsp.getSpec_id1());
               gqo.addQuery("gsp" + j + i, gsp, "obj.goods_specs)", "member of", "or");
               Map map = new HashMap();
               map.put("name", gsf.getName());
               map.put("value", gsp.getValue());
               map.put("type", "gs");
               map.put("id", gsp.getId());
               goods_property.add(map);
             } else {
               GoodsSpecProperty gsp = (GoodsSpecProperty)gsp_list.get(i);
               GoodsSpecification gsf=goodsSpecificationService.selectById(gsp.getSpec_id1());
               gqo.addQuery("gsp" + j + i, gsp, "obj.goods_specs", "member of", "or");
               Map map = new HashMap();
               map.put("name", gsf.getName());
               map.put("value", gsp.getValue());
               map.put("type", "gs");
               map.put("id", gsp.getId());
               goods_property.add(map);
             }
           }
         }
       }
       mv.addObject("gs_ids", gs_ids);
     }
     if (!CommUtil.null2String(properties).equals("")) {
       String[] properties_list = properties.substring(1).split("\\|");
       for (int i = 0; i < properties_list.length; i++) {
         String property_info = properties_list[i];
         String[] property_info_list = property_info.split(",");
         GoodsTypeProperty gtp = this.goodsTypePropertyService.selectById(CommUtil.null2Long(property_info_list[0]));
 
         Map p_map = new HashMap();
         p_map.put("gtp_name" + i, "%" + gtp.getName().trim() + "%");
         p_map.put("gtp_value" + i, "%" + property_info_list[1].trim() + "%");
         gqo.addQuery("and (obj.goods_property like :gtp_name" + i + " and obj.goods_property like :gtp_value" + i + ")", p_map);
         Map map = new HashMap();
         map.put("name", gtp.getName());
         map.put("value", property_info_list[1]);
         map.put("type", "properties");
         map.put("id", gtp.getId());
         goods_property.add(map);
       }
       mv.addObject("properties", properties);
     }
    
     Area sArea=new Area();
     sArea.setCommon(Boolean.valueOf(true));
     List areas = this.areaService.selectList(sArea,"sequence asc");
     mv.addObject("areas", areas);
 
     Page<Goods> pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12),sb.toString(), orderBy);
     for(Goods goods:pList.getRecords()){
    	 Accessory ac1=accessoryService.selectById(goods.getGoods_main_photo_id());
			goods.setGoods_main_photo(ac1);
			Store store=storeService.selectById(goods.getGoods_store_id());
			goods.setGoods_store(store);
     }
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("gc", gc);
     mv.addObject("orderBy", orderBy1);
     mv.addObject("user_viewed_goods", request.getSession(false).getAttribute("user_viewed_goods"));
     mv.addObject("goods_property", goods_property);
     if (CommUtil.null2String(goods_view).equals("list"))
       goods_view = "list";
     else {
       goods_view = "thumb";
     }
 
     if (this.configService.getSysConfig().getZtc_status()) {
       List ztc_goods = null;
       Map ztc_map = new HashMap();
       ztc_map.put("ztc_status", Integer.valueOf(3));
       ztc_map.put("now_date", new Date());
       ztc_map.put("ztc_gold", Integer.valueOf(0));
       Goods sGoods=new Goods();
       sGoods.setZtc_status(Integer.valueOf(3));
      
       if (this.configService.getSysConfig().getZtc_goods_view() == 0) {
    	   Page<Goods> page = new Page<Goods>(0, 5);
    	   String sql="where ztc_status =3 and ztc_begin_time <='"+CommUtil.formatTime("yyyyMMdd", new Date())+"' and ztc_gold>0 order by ztc_dredge_price desc";
         ztc_goods = this.goodsService.selectPage(page, sql, null).getRecords();
       }
       if (this.configService.getSysConfig().getZtc_goods_view() == 1) {
    	   Page<Goods> page = new Page<Goods>(0, 5);
    	   String x=CommUtil.exactSetToString(ids);
    	   String sql="where ztc_status =3 and ztc_begin_time <="+CommUtil.formatTime("yyyyMMdd", new Date())+" and ztc_gold>0  and gc_id in ("+x+") order by ztc_dredge_price desc";
       
         ztc_goods = this.goodsService.selectPage(page, sql, null).getRecords();
       }
       mv.addObject("ztc_goods", ztc_goods);
     }
     if ((detail_property_status != null) && 
       (!detail_property_status.equals(""))) {
       mv.addObject("detail_property_status", detail_property_status);
       String[] temp_str = detail_property_status.split(",");
       Map pro_map = new HashMap();
       List pro_list = new ArrayList();
       for (String property_status : temp_str) {
         if ((property_status != null) && (!property_status.equals(""))) {
           String[] mark = property_status.split("_");
           pro_map.put(mark[0], mark[1]);
           pro_list.add(mark[0]);
         }
       }
       mv.addObject("pro_list", pro_list);
       mv.addObject("pro_map", pro_map);
     }
     mv.addObject("goods_view", goods_view);
     mv.addObject("all_property_status", all_property_status);
     return mv;
   }
   
   @RequestMapping({"/store_goods_ajax.htm"})
   public void store_goods_ajax(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String orderBy, String orderType, String store_price_begin, String store_price_end, String op, String goods_name)
   {
	 Map<String, Object> map = new HashMap<String, Object>();
	 if ((op != null) && (!op.equals(""))) {
		 map.put("op", op);
	 }
     GoodsClass gc = this.goodsClassService.selectById(CommUtil.null2Long(id));
     StringBuffer sb=new StringBuffer(" where gc_id="+gc.getId()+" and goods_status=0");
     if ((store_price_begin != null) && (!store_price_begin.equals(""))) {
      sb.append(" and store_price>="+BigDecimal.valueOf(CommUtil.null2Float(store_price_begin)));
       map.put("store_price_begin", store_price_begin);
     }
     if ((store_price_end != null) && (!store_price_end.equals(""))) {
    	 sb.append(" and store_price<="+BigDecimal.valueOf(CommUtil.null2Float(store_price_end)));
       map.put("store_price_end", store_price_end);
     }
     if ((goods_name != null) && (!goods_name.equals(""))) {
    	 sb.append(" and goods_name like"+"%" + goods_name.trim() + "%");
       map.put("goods_name", goods_name);
     }
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), sb.toString(),orderBy+orderType);
     CommWebUtil.saveWebPaths(map, this.configService.getSysConfig(), request);
     map.put("show", "store_goods_list");
     CommWebUtil.saveIPageList2Map("", "", "", pList, map);
     
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
 
   private Set<Long> getArrayAreaChildIds(List<Area> areas) {
     Set ids = new HashSet();
     for (Area area : areas) {
       ids.add(area.getId());
       Area sArea=new Area();
       sArea.setParent_id(area.getId());
       List<Area> childs=areaService.selectList(sArea);
       for (Area are : childs) {
         Set<Long> cids = getAreaChildIds(are);
         for (Long cid : cids) {
           ids.add(cid);
         }
       }
     }
     return ids;
   }
 
   @RequestMapping({"/ztc_goods_list.htm"})
   public ModelAndView ztc_goods_list(HttpServletRequest request, HttpServletResponse response, String currentPage, String orderBy, String orderType, String goods_view)
   {
     ModelAndView mv = new JModelAndView("ztc_goods_list.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     StringBuffer sb=new StringBuffer(" where goods_status=0 and ztc_status=3 and ztc_begin_time<='"+CommUtil.formatTime("yyyyMMdd", new Date())+"' and ztc_gold>0");
     Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12),sb.toString(), "ztc_dredge_price desc");
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     mv.addObject("goods_view", goods_view);
     mv.addObject("user_viewed_goods", request.getSession(false)
       .getAttribute("user_viewed_goods"));
     return mv;
   }
 
   private Set<Long> getAreaChildIds(Area area) {
     Set ids = new HashSet();
     ids.add(area.getId());
     Area sArea=new Area();
     sArea.setParent_id(area.getId());
     List<Area> childs=areaService.selectList(sArea);
     for (Area are : childs) {
       Set<Long> cids = getAreaChildIds(are);
       for (Long cid : cids) {
         ids.add(cid);
       }
     }
     return ids;
   }
 
   private List<List<GoodsSpecProperty>> generic_gsp(String gs_ids) {
     List<List<GoodsSpecProperty>> list = new ArrayList<List<GoodsSpecProperty>>();
     String[] gs_id_list = gs_ids.substring(1).split("\\|");
     for (String gd_id_info : gs_id_list) {
       String[] gs_info_list = gd_id_info.split(",");
       GoodsSpecProperty gsp = this.goodsSpecPropertyService
         .selectById(CommUtil.null2Long(gs_info_list[0]));
       boolean create = true;
       for (List<GoodsSpecProperty> gsp_list : list) {
         for (GoodsSpecProperty gsp_temp : gsp_list)
         {
           if (gsp_temp.getSpec_id1()
             .equals(gsp.getSpec_id1())) {
             gsp_list.add(gsp);
             create = false;
             break;
           }
         }
       }
       if (create) {
         List gsps = new ArrayList();
         gsps.add(gsp);
         list.add(gsps);
       }
     }
     return list;
   }
 
   @RequestMapping({"/goods_evaluation.htm"})
   public ModelAndView goods_evaluation(HttpServletRequest request, HttpServletResponse response, String id, String goods_id, String currentPage)
   {
     String template = "default";
     Store store = this.storeService.selectById(CommUtil.null2Long(id));
     if (store != null) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(
       template + "/goods_evaluation.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     EvaluateQueryObject qo = new EvaluateQueryObject(currentPage, mv, 
       "addTime", "desc");
     qo.addQuery("obj.evaluate_goods.id", 
       new SysMap("goods_id", CommUtil.null2Long(goods_id)), "=");
     qo.addQuery("obj.evaluate_type", new SysMap("evaluate_type", "goods"), 
       "=");
     qo.addQuery("obj.evaluate_status", new SysMap("evaluate_status", Integer.valueOf(0)), 
       "=");
     qo.setPageSize(Integer.valueOf(8));
     Page pList = this.evaluateService.selectPage(new Page<Evaluate>(Integer.valueOf(CommUtil.null2Int(currentPage)), 8), null);
     
     CommWebUtil.saveIPageList2ModelAndView(CommUtil.getURL(request) + 
       "/goods_evaluation.htm", "", "", pList, mv);
     mv.addObject("storeViewTools", this.storeViewTools);
     mv.addObject("store", store);
     Goods goods = this.goodsService
       .selectById(CommUtil.null2Long(goods_id));
     mv.addObject("goods", goods);
     return mv;
   }
 
   @RequestMapping({"/goods_detail.htm"})
   public ModelAndView goods_detail(HttpServletRequest request, HttpServletResponse response, String id, String goods_id) {
     String template = "default";
     Store store = this.storeService.selectById(CommUtil.null2Long(id));
     if (store != null) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/goods_detail.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     Goods goods = this.goodsService
       .selectById(CommUtil.null2Long(goods_id));
     mv.addObject("obj", goods);
     generic_evaluate(storeService.selectById(goods.getGoods_store_id()), mv);
     this.userTools.query_user();
     return mv;
   }
 
   @RequestMapping({"/goods_order.htm"})
   public ModelAndView goods_order(HttpServletRequest request, HttpServletResponse response, String id, String goods_id, String currentPage)
   {
     String template = "default";
     Store store = this.storeService.selectById(CommUtil.null2Long(id));
     if (store != null) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/goods_order.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     GoodsCartQueryObject qo = new GoodsCartQueryObject(currentPage, mv, 
       "addTime", "desc");
     qo.addQuery("obj.goods.id", 
       new SysMap("goods_id", CommUtil.null2Long(goods_id)), "=");
     qo.addQuery("obj.of.order_status", new SysMap("order_status", Integer.valueOf(20)), ">=");
     qo.setPageSize(Integer.valueOf(8));
     StringBuffer sb=new StringBuffer(" where goods_id="+goods_id+"");
     Page pList = this.goodsCartService.selectPage(new Page<GoodsCart>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12),sb.toString(), "addTime desc");
     for(int i=0;i<pList.getRecords().size();i++){
    	 OrderForm of=orderFormService.selectById(((GoodsCart)pList.getRecords().get(i)).getOf_id());
    	 ((GoodsCart)pList.getRecords().get(i)).setOf(of);
    	 of.setUser(userService.selectById(of.getUser_id()));
     }
     CommWebUtil.saveIPageList2ModelAndView(CommUtil.getURL(request) + 
       "/goods_order.htm", "", "", pList, mv);
     mv.addObject("storeViewTools", this.storeViewTools);
     return mv;
   }
 
   @RequestMapping({"/goods_consult.htm"})
   public ModelAndView goods_consult(HttpServletRequest request, HttpServletResponse response, String id, String goods_id, String currentPage)
   {
     String template = "default";
     Store store = this.storeService.selectById(CommUtil.null2Long(id));
     if (store != null) {
       template = store.getTemplate();
     }
     ModelAndView mv = new JModelAndView(template + "/goods_consult.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     ConsultQueryObject qo = new ConsultQueryObject(currentPage, mv, 
       "addTime", "desc");
     qo.addQuery("obj.goods.id", 
       new SysMap("goods_id", CommUtil.null2Long(goods_id)), "=");
     Page pList = this.consultService.selectPage(new Page<Consult>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
     
     CommWebUtil.saveIPageList2ModelAndView(CommUtil.getURL(request) + 
       "/goods_consult.htm", "", "", pList, mv);
     mv.addObject("storeViewTools", this.storeViewTools);
     mv.addObject("goods_id", goods_id);
     return mv;
   }
 
   @RequestMapping({"/goods_consult_save.htm"})
   public ModelAndView goods_consult_save(HttpServletRequest request, HttpServletResponse response, String goods_id, String consult_content, String consult_email, String Anonymous, String consult_code)
   {
     ModelAndView mv = new JModelAndView(
       "user/default/usercenter/success.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 0, request, response);
     String verify_code = CommUtil.null2String(request.getSession(false)
       .getAttribute("consult_code"));
     boolean visit_consult = true;
     if (!this.configService.getSysConfig().getVisitorConsult()) {
       if (SecurityUserHolder.getCurrentUser() == null) {
         visit_consult = false;
       }
       if (CommUtil.null2Boolean(Anonymous)) {
         visit_consult = false;
       }
     }
     if (visit_consult) {
       if (CommUtil.null2String(consult_code).equals(verify_code)) {
         Consult obj = new Consult();
         obj.setAddTime(new Date());
         obj.setConsult_content(consult_content);
         obj.setConsult_email(consult_email);
         if (!CommUtil.null2Boolean(Anonymous)) {
           obj.setConsult_user_id(SecurityUserHolder.getCurrentUser().getId());
           mv.addObject("op_title", "咨询发布成功");
         }
         obj.setGoods_id(
           CommUtil.null2Long(goods_id));
         this.consultService.insertSelective(obj);
         request.getSession(false).removeAttribute("consult_code");
       } else {
         mv = new JModelAndView("error.html", 
           this.configService.getSysConfig(), 
           this.userConfigService.getUserConfig(), 1, request, 
           response);
         mv.addObject("op_title", "验证码错误，咨询发布失败");
       }
     } else {
       mv = new JModelAndView("error.html", this.configService.getSysConfig(), 
         this.userConfigService.getUserConfig(), 1, request, 
         response);
       mv.addObject("op_title", "不允许游客咨询");
     }
     mv.addObject("url", CommUtil.getURL(request) + "/goods_" + goods_id + 
       ".htm");
     return mv;
   }
 
   /**
    * 根据商品规格查询商品规格价格
	 * @param request
	 * @param response
	 * @param gsp 规格
	 * @param id
	 */
	@RequestMapping({"/load_goods_gsp.htm"})
   public void load_goods_gsp(HttpServletRequest request, HttpServletResponse response, String gsp, String id) {
     Goods goods = this.goodsService.selectById(CommUtil.null2Long(id));
     Map map = new HashMap();
     int count = 0;
     double price = 0.0D;
     if ((goods.getGroup_id() != null) && (goods.getGroup_buy() == 2)) {
    	  GroupGoods sGroup=new GroupGoods();
			sGroup.setGg_goods_id(goods.getId());
			List<GroupGoods> groupList=groupGoodsService.selectList(sGroup);
			for (GroupGoods gg : groupList) {
		         if (gg.getGroup_id().equals(goods.getGroup_id())) {
		           count = gg.getGg_group_count() - gg.getGg_def_count();
		           price = CommUtil.null2Double(gg.getGg_price());
		         }
			}
     }
     else {
       count = goods.getGoods_inventory();
       price = CommUtil.null2Double(goods.getStore_price());
       if (goods.getInventory_type().equals("spec")) {
         List<Map> list = (List)Json.fromJson(ArrayList.class, goods.getGoods_inventory_detail());
         String[] gsp_ids = gsp.split(",");
         for (Map temp : list) {
           String[] temp_ids = CommUtil.null2String(temp.get("id")).split("_");
           Arrays.sort(gsp_ids);
           Arrays.sort(temp_ids);
           if (Arrays.equals(gsp_ids, temp_ids)) {
             count = CommUtil.null2Int(temp.get("count"));
             price = CommUtil.null2Double(temp.get("price"));
           }
         }
       }
     }
     map.put("count", Integer.valueOf(count));
     map.put("price", Double.valueOf(price));
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
 @Autowired
 private ITransportService transportService;
   @RequestMapping({"/trans_fee.htm"})
   public void trans_fee(HttpServletRequest request, HttpServletResponse response, String city_name, String goods_id) {
	   Map map = new HashMap();
       Goods goods = this.goodsService.selectById(CommUtil.null2Long(goods_id));
       float mail_fee = 0.0F;
       float express_fee = 0.0F;
       float ems_fee = 0.0F;
       Transport transport=transportService.selectById(goods.getTransport_id());
       if (transport != null) {
           if(transport.getTrans_mail_info()!=null) {
               mail_fee = this.transportTools.cal_goods_trans_fee(
                       CommUtil.null2String(transport.getId()), "mail",
                       CommUtil.null2String(goods.getGoods_weight()),
                       CommUtil.null2String(goods.getGoods_volume()), city_name);
           }
           if(transport.getTrans_express_info()!=null) {
               express_fee = this.transportTools.cal_goods_trans_fee(
                       CommUtil.null2String(transport.getId()), "express",
                       CommUtil.null2String(goods.getGoods_weight()),
                       CommUtil.null2String(goods.getGoods_volume()), city_name);
           }
           if(transport.getTrans_ems_info()!=null) {
               ems_fee = this.transportTools.cal_goods_trans_fee(
                       CommUtil.null2String(transport.getId()), "ems",
                       CommUtil.null2String(goods.getGoods_weight()),
                       CommUtil.null2String(goods.getGoods_volume()), city_name);
           }
       }
       map.put("mail_fee", Float.valueOf(mail_fee));
       map.put("express_fee", Float.valueOf(express_fee));
       map.put("ems_fee", Float.valueOf(ems_fee));
       map.put("current_city_info", CommUtil.substring(city_name, 5));
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
 
   @RequestMapping({"/goods_share.htm"})
   public ModelAndView goods_share(HttpServletRequest request, HttpServletResponse response, String goods_id) {
     ModelAndView mv = new JModelAndView("goods_share.html", 
       this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     Goods goods = this.goodsService
       .selectById(CommUtil.null2Long(goods_id));
     mv.addObject("obj", goods);
     return mv;
   }
 
//   private Set<Long> genericIds(GoodsClass gc) {
//     Set ids = new HashSet();
//     ids.add(gc.getId());
//     for (GoodsClass child : gc.getChilds()) {
//       Set<Long> cids = genericIds(child);
//       for (Long cid : cids) {
//         ids.add(cid);
//       }
//       ids.add(child.getId());
//     }
//     return ids;
//   }
   private Set<Long> genericIds(GoodsClass gc) {
	   if(gc!=null){
		   Set ids = new HashSet();
		     ids.add(gc.getId());
		     GoodsClass sGoodsClass=new GoodsClass();
		     sGoodsClass.setParent_id(gc.getId());
		     List<GoodsClass> childs=goodsClassService.selectList(sGoodsClass);
		     if(childs!=null && childs.size()>0){
		    	 for (GoodsClass child : childs) {
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
   private void generic_evaluate(Store store, ModelAndView mv) {
     double description_result = 0.0D;
     double service_result = 0.0D;
     double ship_result = 0.0D;
     if (store.getSc_id() != null) {
       StoreClass sc = this.storeClassService.selectById(store.getSc_id());
       float description_evaluate = CommUtil.null2Float(sc
         .getDescription_evaluate());
       float service_evaluate = CommUtil.null2Float(sc
         .getService_evaluate());
       float ship_evaluate = CommUtil.null2Float(sc.getShip_evaluate());
       StorePoint sStorePoint=new StorePoint();
       sStorePoint.setStore_id(store.getId());
       StorePoint gStorePoint=storePointService.selectOne(sStorePoint);
       if (gStorePoint != null) {
         float store_description_evaluate = CommUtil.null2Float(gStorePoint.getDescription_evaluate());
         float store_service_evaluate = CommUtil.null2Float(gStorePoint.getService_evaluate());
         float store_ship_evaluate = CommUtil.null2Float(gStorePoint.getShip_evaluate());
 
         description_result = CommUtil.div(Float.valueOf(store_description_evaluate - 
           description_evaluate), Float.valueOf(description_evaluate));
         service_result = CommUtil.div(Float.valueOf(store_service_evaluate - 
           service_evaluate), Float.valueOf(service_evaluate));
         ship_result = CommUtil.div(Float.valueOf(store_ship_evaluate - ship_evaluate), 
           Float.valueOf(ship_evaluate));
       }
     }
     if (description_result > 0.0D) {
       mv.addObject("description_css", "better");
       mv.addObject("description_type", "高于");
       mv.addObject("description_result", 
         CommUtil.null2String(Double.valueOf(CommUtil.mul(Double.valueOf(description_result), Integer.valueOf(100)))) + 
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
         CommUtil.null2String(Double.valueOf(CommUtil.mul(Double.valueOf(-description_result), Integer.valueOf(100)))) + 
         "%");
     }
     if (service_result > 0.0D) {
       mv.addObject("service_css", "better");
       mv.addObject("service_type", "高于");
       mv.addObject("service_result", 
         CommUtil.null2String(Double.valueOf(CommUtil.mul(Double.valueOf(service_result), Integer.valueOf(100)))) + 
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
         CommUtil.null2String(Double.valueOf(CommUtil.mul(Double.valueOf(-service_result), Integer.valueOf(100)))) + 
         "%");
     }
     if (ship_result > 0.0D) {
       mv.addObject("ship_css", "better");
       mv.addObject("ship_type", "高于");
       mv.addObject("ship_result", 
         CommUtil.null2String(Double.valueOf(CommUtil.mul(Double.valueOf(ship_result), Integer.valueOf(100)))) + "%");
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
         CommUtil.null2String(Double.valueOf(CommUtil.mul(Double.valueOf(-ship_result), Integer.valueOf(100)))) + "%");
     }
   }
 }


 
 
 