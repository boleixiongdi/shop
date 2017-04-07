 package com.rt.shop.view.web.action;
 
 import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Brandcategory;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.query.GoodsQueryObject;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IBrandcategoryService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.view.web.tools.StoreViewTools;
 
 @Controller
 public class BrandViewAction
 {
 
	 @Autowired
   private IAccessoryService accessoryService;
   @Autowired
   private ISysConfigService configService;
 
   @Autowired
   private IUserConfigService userConfigService;
 
   @Autowired
   private IGoodsClassService goodsClassService;
 
   @Autowired
   private IGoodsService goodsService;
 
   @Autowired
   private IGoodsBrandService goodsBrandService;
 
   @Autowired
   private IBrandcategoryService goodsBrandCategorySerivce;
 
   @Autowired
   private StoreViewTools storeViewTools;
   CommWebUtil CommWebUtil=new CommWebUtil();
 
   @RequestMapping({"/brand.htm"})
   public ModelAndView brand(HttpServletRequest request, HttpServletResponse response, String gbc_id)
   {
     ModelAndView mv = new JModelAndView("brand.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     String shop_view_type = CommUtil.null2String( request.getSession( false ).getAttribute( "shop_view_type" ) );
     
	 if( (shop_view_type != null) && (!shop_view_type.equals( "" )) && (shop_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/brand.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	 }
//     List<Brandcategory> gbcs = this.goodsBrandCategorySerivce.selectList(new Brandcategory(), "addTime asc");
//     mv.addObject("gbcs", gbcs);
     GoodsBrand sGoodsBrand=new GoodsBrand();
     sGoodsBrand.setRecommend(Boolean.valueOf(true));
     sGoodsBrand.setAudit(Integer.valueOf(1));
     List<GoodsBrand> gbs = this.goodsBrandService.selectPage(new Page<GoodsBrand>(0, 10), sGoodsBrand, "sequence asc").getRecords();
     for(GoodsBrand g1 : gbs){
			Accessory ac=accessoryService.selectById(g1.getBrandLogo_id());
			g1.setBrandLogo(ac);
		}
     mv.addObject("gbs", gbs);
    
     List<GoodsBrand> brands = new ArrayList();
     if ((gbc_id != null) && (!gbc_id.equals(""))) {
       mv.addObject("gbc_id", gbc_id);
       GoodsBrand ssGoodsBrand=new GoodsBrand();
       ssGoodsBrand.setCategory_id(CommUtil.null2Long(gbc_id));
       ssGoodsBrand.setAudit(Integer.valueOf(1));
       brands = this.goodsBrandService.selectList(ssGoodsBrand, "sequence asc");
     } else {
       GoodsBrand ssGoodsBrand=new GoodsBrand();
       ssGoodsBrand.setAudit(Integer.valueOf(1));
       brands = this.goodsBrandService.selectList(ssGoodsBrand, "sequence asc");
     }
     List all_list = new ArrayList();
     String list_word = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
     String[] words = list_word.split(",");
     for (String word : words) {
       Map brand_map = new HashMap();
       List brand_list = new ArrayList();
       for (GoodsBrand gb : brands) {
    	   Accessory ac=accessoryService.selectById(gb.getBrandLogo_id());
			gb.setBrandLogo(ac);
         if ((CommUtil.null2String(gb.getFirst_word()).equals("")) || 
           (!word.equals(gb.getFirst_word().toUpperCase()))) continue;
         brand_list.add(gb);
       }
       brand_map.put("brand_list", brand_list);
       brand_map.put("word", word);
       all_list.add(brand_map);
     }
     mv.addObject("all_list", all_list);
     return mv;
   }
 
   @RequestMapping({"/brand_goods.htm"})
   public ModelAndView brand_view(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String orderBy, String orderType, String store_price_begin, String store_price_end, String op, String goods_name)
   {
     ModelAndView mv = new JModelAndView("brand_goods.html", this.configService.getSysConfig(), 
       this.userConfigService.getUserConfig(), 1, request, response);
     
     String shop_view_type = CommUtil.null2String( request.getSession( false ).getAttribute( "rt.shop_view_type" ) );
     
	 if( (shop_view_type != null) && (!shop_view_type.equals( "" )) && (shop_view_type.equals( "wap" )) ) {
		 mv = new JModelAndView("wap/brand_goods.html", this.configService.getSysConfig(), 
			       this.userConfigService.getUserConfig(), 1, request, response);
	 }
     if ((op != null) && (!op.equals(""))) {
       mv.addObject("op", op);
     }
     GoodsBrand gb = this.goodsBrandService.selectById(CommUtil.null2Long(id));
     mv.addObject("gb", gb);
     
     GoodsBrand ssGoodsBrand=new GoodsBrand();
     ssGoodsBrand.setRecommend(Boolean.valueOf(true));
     ssGoodsBrand.setAudit(Integer.valueOf(1));
     
     List<GoodsBrand> gbs = this.goodsBrandService.selectPage(new Page<GoodsBrand>(0, 10), ssGoodsBrand, "sequence asc").getRecords();
     for(GoodsBrand g1 : gbs){
			Accessory ac=accessoryService.selectById(g1.getBrandLogo_id());
			g1.setBrandLogo(ac);
		}
     mv.addObject("gbs", gbs);
     mv.addObject("storeViewTools", this.storeViewTools);
    // GoodsQueryObject gqo = new GoodsQueryObject(currentPage, mv, orderBy, orderType);
     StringBuffer sb=new StringBuffer("where 1=1 ");
     if ((store_price_begin != null) && (!store_price_begin.equals(""))) {
    	 sb.append(" and store_price>="+BigDecimal.valueOf(CommUtil.null2Float(store_price_begin)));
       mv.addObject("store_price_begin", store_price_begin);
     }
     if ((store_price_end != null) && (!store_price_end.equals(""))) {
    	 sb.append(" and store_price<="+BigDecimal.valueOf(CommUtil.null2Float(store_price_end)));
   //    gqo.addQuery("obj.store_price", new SysMap("store_price_end", BigDecimal.valueOf(CommUtil.null2Float(store_price_end))), "<=");
       mv.addObject("store_price_end", store_price_end);
     }
     if ((goods_name != null) && (!goods_name.equals(""))) {
    	 sb.append(" and goods_name like"+"%" + goods_name.trim() + "%");
     //  gqo.addQuery("obj.goods_name", new SysMap("goods_name", "%" + goods_name.trim() + "%"), "like");
       mv.addObject("goods_name", goods_name);
     }
     sb.append(" and goods_status=0");
     sb.append(" and goods_brand_id="+gb.getId());
     Page<Goods> pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 20), sb.toString(),null);
     for(Goods g1 : pList.getRecords()){
			Accessory ac=accessoryService.selectById(g1.getGoods_main_photo_id());
			g1.setGoods_main_photo(ac);
		}
     CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
     return mv;
   }
   
    /**
     * wap根据品牌查询商品
	 * @param request
	 * @param response
	 * @param id
	 * @param currentPage
	 * @param orderBy
	 * @param orderType
	 * @param store_price_begin
	 * @param store_price_end
	 * @param op
	 * @param goods_name
	 * @return
	 */
	@RequestMapping({"/brand_goods_ajax.htm"})
   public void brand_view_ajax(HttpServletRequest request, HttpServletResponse response, String id, String currentPage, String orderBy, String orderType, String store_price_begin, String store_price_end, String op, String goods_name)
   {
	 Map<String, Object> map = new HashMap<String, Object>();
	 if ((op != null) && (!op.equals(""))) {
		 map.put("op", op);
	 }
     GoodsBrand gb = this.goodsBrandService.selectById(CommUtil.null2Long(id));
     
     GoodsQueryObject gqo = new GoodsQueryObject(currentPage, map, orderBy, orderType);
     
     if ((store_price_begin != null) && (!store_price_begin.equals(""))) {
       gqo.addQuery("obj.store_price", new SysMap("store_price_begin", BigDecimal.valueOf(CommUtil.null2Float(store_price_begin))), ">=");
       map.put("store_price_begin", store_price_begin);
     }
     
     if ((store_price_end != null) && (!store_price_end.equals(""))) {
       gqo.addQuery("obj.store_price", new SysMap("store_price_end", BigDecimal.valueOf(CommUtil.null2Float(store_price_end))), "<=");
       map.put("store_price_end", store_price_end);
     }
     
     if ((goods_name != null) && (!goods_name.equals(""))) {
       gqo.addQuery("obj.goods_name", new SysMap("goods_name", "%" + goods_name.trim() + "%"), "like");
       map.put("goods_name", goods_name);
     }
     
     gqo.addQuery("obj.goods_brand.id", new SysMap("goods_brand_id", gb.getId()), "=");
     gqo.addQuery("obj.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
     gqo.setPageSize(Integer.valueOf(12));
     Page pList = this.goodsService.selectPage(new Page<Goods>(0, 12), null);
     
     CommWebUtil.saveWebPaths(map, this.configService.getSysConfig(), request);
     map.put("show", "brand_goods");
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
 
 
 }


 
 
 