package com.rt.shop.view.admin.sellers.action;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.annotation.SecurityMapping;
import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Accessory;
import com.rt.shop.entity.Album;
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.GoodsBrand;
import com.rt.shop.entity.GoodsCart;
import com.rt.shop.entity.GoodsClass;
import com.rt.shop.entity.GoodsPhoto;
import com.rt.shop.entity.GoodsSpecProperty;
import com.rt.shop.entity.GoodsSpecification;
import com.rt.shop.entity.Goodsclassstaple;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.Report;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreGrade;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.Transport;
import com.rt.shop.entity.User;
import com.rt.shop.entity.UserGoodsClass;
import com.rt.shop.entity.Watermark;
import com.rt.shop.entity.query.ReportQueryObject;
import com.rt.shop.lucene.LuceneUtil;
import com.rt.shop.lucene.LuceneVo;
import com.rt.shop.mv.JModelAndView;
import com.rt.shop.service.IAccessoryService;
import com.rt.shop.service.IAlbumService;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IGoodsBrandService;
import com.rt.shop.service.IGoodsCartService;
import com.rt.shop.service.IGoodsClassService;
import com.rt.shop.service.IGoodsClassstapleService;
import com.rt.shop.service.IGoodsPhotoService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGoodsSpecPropertyService;
import com.rt.shop.service.IGoodsTypePropertyService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IReportService;
import com.rt.shop.service.IStoreGradeService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITransportService;
import com.rt.shop.service.IUserConfigService;
import com.rt.shop.service.IUserGoodsClassService;
import com.rt.shop.service.IUserService;
import com.rt.shop.service.IWatermarkService;
import com.rt.shop.tools.StoreTools;
import com.rt.shop.tools.database.DatabaseTools;
import com.rt.shop.util.CommWebUtil;
import com.rt.shop.util.SecurityUserHolder;
import com.rt.shop.view.web.tools.GoodsViewTools;
import com.rt.shop.view.web.tools.StoreViewTools;
import com.rt.shop.view.web.tools.TransportTools;

@Controller
public class GoodsSellerAction {

	@Autowired
	private ISysConfigService configService;
	@Autowired
	private IStoreGradeService storeGradeService;

	@Autowired
	private IUserConfigService userConfigService;

	@Autowired
	private IGoodsClassService goodsClassService;

	@Autowired
	private IGoodsClassstapleService goodsclassstapleService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IAccessoryService accessoryService;

	@Autowired
	private IUserGoodsClassService userGoodsClassService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IStoreService storeService;

	@Autowired
	private IGoodsBrandService goodsBrandService;

	@Autowired
	private IGoodsSpecPropertyService specPropertyService;

	@Autowired
	private IGoodsTypePropertyService goodsTypePropertyService;

	@Autowired
	private IGoodsPhotoService goodsPhotoService;
	@Autowired
	private IWatermarkService waterMarkService;

	@Autowired
	private IGoodsCartService goodsCartService;

	@Autowired
	private IAlbumService albumService;

	@Autowired
	private IReportService reportService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IOrderLogService orderFormLogService;

	@Autowired
	private IEvaluateService evaluateService;

	@Autowired
	private ITransportService transportService;

	@Autowired
	private IPaymentService paymentService;

	@Autowired
	private TransportTools transportTools;

	@Autowired
	private StoreTools storeTools;

	@Autowired
	private StoreViewTools storeViewTools;

	@Autowired
	private GoodsViewTools goodsViewTools;

	private DatabaseTools databaseTools=new DatabaseTools();

	

	@SecurityMapping(display = false, rsequence = 0, title = "商品运费模板分页显示", value = "/seller/goods_transport.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_transport.htm" })
	public ModelAndView goods_transport(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType, String ajax) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/goods_transport.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		if (CommUtil.null2Boolean(ajax)) {
			mv = new JModelAndView(
					"user/default/usercenter/goods_transport_list.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 0, request,
					response);
		}
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		
		Store store = store = this.userService.selectById(
				SecurityUserHolder.getCurrentUser().getId()).getStore();
		
		Transport transport=new Transport();
		transport.setStore_id(store.getId());
		Page pList = this.transportService.selectPage(new Page<Transport>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), transport,null);
		 CommWebUtil.saveIPageList2ModelAndView(
				url + "/seller/goods_transport.htm", "", params, pList, mv);
		mv.addObject("transportTools", this.transportTools);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "发布商品第一步", value = "/seller/add_goods_first.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/add_goods_first.htm" })
	public ModelAndView add_goods_first(HttpServletRequest request,
			HttpServletResponse response, String id) {
		ModelAndView mv = new JModelAndView("error.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 1, request, response);
		User user = this.userService.selectById(SecurityUserHolder
				.getCurrentUser().getId());
		List payments = new ArrayList();
		Map params = new HashMap();
		if (this.configService.getSysConfig().getConfig_payment_type() == 1) {
			params.put("type", "admin");
			params.put("install", Boolean.valueOf(true));
			Payment sPayment=new Payment();
			sPayment.setType("admin");
			sPayment.setInstall(Boolean.valueOf(true));
			payments = this.paymentService.selectList(sPayment);
		} else {
			params.put("store_id", user.getStore_id());
			params.put("install", Boolean.valueOf(true));
			Payment sPayment=new Payment();
			sPayment.setStore_id(user.getStore_id());
			sPayment.setInstall(Boolean.valueOf(true));
			payments = this.paymentService.selectList(sPayment);
		}
		if (payments.size() == 0) {
			mv.addObject("op_title", "请至少开通一种支付方式");
			mv.addObject("url", CommUtil.getURL(request)
					+ "/seller/payment.htm");
			return mv;
		}
		request.getSession(false).removeAttribute("goods_class_info");
		Store store=storeService.selectById(user.getStore_id());
		int store_status = user.getStore_id() == null ? 0 :store
				.getStore_status();
		if (store_status == 2) {
			StoreGrade grade =storeGradeService.selectById(store.getGrade_id());
			Goods sGoods=new Goods();
			sGoods.setGoods_store_id(store.getId());
			List<Goods> goodStoreList=goodsService.selectList(sGoods); 
			int user_goods_count = goodStoreList.size();
			if ((grade.getGoodsCount() == 0)
					|| (user_goods_count < grade.getGoodsCount())) {
				
				List gcs = this.goodsClassService.selectList("where parent_id is null","sequence asc");
				mv = new JModelAndView(
						"user/default/usercenter/add_goods_first.html",
						this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 0, request,
						response);
				
				Goodsclassstaple sGoodsClassStaple=new Goodsclassstaple();
				sGoodsClassStaple.setStore_id(user.getStore_id());
				List staples = this.goodsclassstapleService.selectList(sGoodsClassStaple,"addTime desc");
				mv.addObject("staples", staples);
				mv.addObject("gcs", gcs);
				mv.addObject("id", CommUtil.null2String(id));
			} else {
				mv.addObject("op_title", "您的店铺等级只允许上传" + grade.getGoodsCount()
						+ "件商品!");
				mv.addObject("url", CommUtil.getURL(request)
						+ "/seller/store_grade.htm");
			}
		}
		if (store_status == 0) {
			mv.addObject("op_title", "您尚未开通店铺，不能发布商品");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}
		if (store_status == 1) {
			mv.addObject("op_title", "您的店铺在审核中，不能发布商品");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}
		if (store_status == 3) {
			mv.addObject("op_title", "您的店铺已被关闭，不能发布商品");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}
		return mv;
	}
	@SecurityMapping(display = false, rsequence = 0, title = "发布商品第二步", value = "/seller/add_goods_second.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/add_goods_second.htm" })
	public ModelAndView add_goods_second(HttpServletRequest request, HttpServletResponse response) {
		SysConfig config=this.configService.getSysConfig();
		ModelAndView mv = new JModelAndView("error.html", config, this.userConfigService.getUserConfig(), 1, request, response);
		User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		int store_status = this.storeService.selectById(user.getStore_id()).getStore_status();
		if (store_status == 2) {
			mv = new JModelAndView(
					"user/default/usercenter/add_goods_second.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 0, request,
					response);
			if (request.getSession(false).getAttribute("goods_class_info") != null) {
				GoodsClass gc = (GoodsClass) request.getSession(false)
						.getAttribute("goods_class_info");
				gc = this.goodsClassService.selectById(gc.getId());
				String goods_class_info = generic_goods_class_info(gc);
				mv.addObject("goods_class",
						this.goodsClassService.selectById(gc.getId()));
				mv.addObject("goods_class_info", goods_class_info.substring(0,
						goods_class_info.length() - 1));
				request.getSession(false).removeAttribute("goods_class_info");
			}
			String path = request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator
					+ "upload"
					+ File.separator
					+ "store"
					+ File.separator + user.getStore_id();
			double img_remain_size = 0.0D;
			//Store store=WebCacheUtil.getStore(user.getId());
			//StoreGrade sg=storeGradeService.selectById(store.getGrade_id());
			/*if (sg.getSpaceSize() > 0.0F) {
				img_remain_size = user.getStore().getGrade().getSpaceSize()
						- CommUtil.div(Double.valueOf(CommUtil
								.fileSize(new File(path))), Integer
								.valueOf(1024));
			}*/
			
			List ugcs = this.userGoodsClassService.selectList("where user_id='"+user.getId()+"' and display=1 and  parent_id is null","sequence asc");
			List gbs = this.goodsBrandService.selectList(new GoodsBrand(), "sequence asc");
			Accessory goodsImg=accessoryService.selectById(config.getGoodsImage_id());
			config.setGoodsImage(goodsImg);
			mv.addObject("config", config);
			mv.addObject("gbs", gbs);
			mv.addObject("ugcs", ugcs);
			mv.addObject("img_remain_size", Double.valueOf(img_remain_size));
			mv.addObject("imageSuffix", this.storeViewTools
					.genericImageSuffix(this.configService.getSysConfig()
							.getImageSuffix()));
			String goods_session = CommUtil.randomString(32);
			mv.addObject("goods_session", goods_session);
			request.getSession(false).setAttribute("goods_session",
					goods_session);
		}
		if (store_status == 0) {
			mv.addObject("op_title", "您尚未开通店铺，不能发布商品");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}
		if (store_status == 1) {
			mv.addObject("op_title", "您的店铺在审核中，不能发布商品");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}
		if (store_status == 3) {
			mv.addObject("op_title", "您的店铺已被关闭，不能发布商品");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "发布商品第三步", value = "/seller/add_goods_finish.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/add_goods_finish.htm" })
	public ModelAndView add_goods_finish(HttpServletRequest request,Goods goods,
			HttpServletResponse response, String id, String goods_class_id,
			String image_ids, String goods_main_img_id, String user_class_ids,
			String goods_brand_id, String goods_spec_ids,
			String goods_properties, String intentory_details,
			String goods_session, String transport_type, String transport_id) {
		ModelAndView mv = null;
		String goods_session1 = CommUtil.null2String(request.getSession(false).getAttribute("goods_session"));
		if (goods_session1.equals("")) {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "禁止重复提交表单");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		} else if (goods_session1.equals(goods_session)) {
			if ((id == null) || (id.equals(""))) {
				mv = new JModelAndView("user/default/usercenter/add_goods_finish.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 0, request, response);
			} else {
				mv = new JModelAndView("success.html", this.configService.getSysConfig(),
						this.userConfigService.getUserConfig(), 1, request, response);
				mv.addObject("op_title", "商品编辑成功");
				mv.addObject("url", CommUtil.getURL(request) + "/goods_" + id + ".htm");
			}
			if (id.equals("")) {
				goods.setAddTime(new Date());
				User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
				goods.setGoods_store_id(user.getStore_id());
			} else {
				Goods obj = this.goodsService.selectById(Long.valueOf(Long.parseLong(id)));
			}
			if ((goods.getCombin_status()!=null && goods.getCombin_status() != 2)
					&& (goods.getDelivery_status()!=null && goods.getDelivery_status() != 2)
					&& (goods.getBargain_status() !=null && goods.getBargain_status() != 2)
					&& (goods.getActivity_status() !=null && goods.getActivity_status() != 2)) {
				goods.setGoods_current_price(goods.getStore_price());
			}
			goods.setGoods_name(clearContent(goods.getGoods_name()));
		//	GoodsClass gc = this.goodsClassService.selectById(Long.valueOf(Long.parseLong(goods_class_id)));
			goods.setGc_id(Long.parseLong(goods_class_id));
			Accessory main_img = null;
			if ((goods_main_img_id != null) && (!goods_main_img_id.equals(""))) {
				goods.setGoods_main_photo_id(Long.parseLong(goods_main_img_id));
			//	main_img = this.accessoryService.selectById(Long.valueOf(Long.parseLong(goods_main_img_id)));
			}
			
		
			if ((goods_brand_id != null) && (!goods_brand_id.equals(""))) {
				GoodsBrand goods_brand = this.goodsBrandService.selectById(Long.valueOf(Long.parseLong(goods_brand_id)));
				goods.setGoods_brand(goods_brand);
			}
			
			Object maps = new ArrayList();
			String[] properties = goods_properties.split(";");
			// String[] arrayOfString2;
			// GoodsSpecProperty gsp = (arrayOfString2 = properties).length;
			String[] list;
			for (int i = 0; i < properties.length; i++) {
				String property = properties[i];
				if (!property.equals("")) {
					list = property.split(",");
					Map map = new HashMap();
					map.put("id", list[0]);
					map.put("val", list[1]);
					map.put("name", this.goodsTypePropertyService.selectById(Long.valueOf(Long.parseLong(list[0]))).getName());
					((List) maps).add(map);
				}
			}
			goods.setGoods_property(Json.toJson(maps, JsonFormat.compact()));
			((List) maps).clear();
			String[] inventory_list = intentory_details.split(";");
			// GoodsSpecProperty localGoodsSpecProperty1 = (list =
			// inventory_list).length;
			for (int i = 0; i < inventory_list.length; i++) {
				String inventory = inventory_list[i];
				if (!inventory.equals("")) {
					String[] list1 = inventory.split(",");
					Map map = new HashMap();
					map.put("id", list1[0]);
					map.put("count", list1[1]);
					map.put("price", list1[2]);
					((List) maps).add(map);
				}
			}
			goods.setGoods_inventory_detail(Json.toJson(maps, JsonFormat.compact()));
			if (CommUtil.null2Int(transport_type) == 0) {
				Transport trans = this.transportService.selectById(CommUtil.null2Long(transport_id));
				goods.setTransport(trans);
			}
			if (CommUtil.null2Int(transport_type) == 1) {
				goods.setTransport(null);
			}
			if (id.equals("")) {
				this.goodsService.insertSelective(goods);
				String[] img_ids = image_ids.split(",");
				goods.getGoods_photos().clear();
				// UserGoodsClass localUserGoodsClass3 = (ugc = img_ids).length;
				for (int i = 0; i < img_ids.length; i++) {
					String img_id = img_ids[i];
					if (!img_id.equals("")) {
//						Accessory img = this.accessoryService.selectById(Long.valueOf(Long.parseLong(img_id)));
//						goods.getGoods_photos().add(img);
						GoodsPhoto gp=new GoodsPhoto();
						gp.setGoods_id(goods.getId());
						gp.setPhoto_id(CommUtil.null2Long(img_id));
						goodsPhotoService.insertSelective(gp);
					}
				}

				goods.getGoods_specs().clear();
				String[] spec_ids = goods_spec_ids.split(",");
				// UserGoodsClass ugc = (img = spec_ids).length;
				for (int i = 0; i < spec_ids.length; i++) {
					String spec_id = spec_ids[i];
					if (!spec_id.equals("")) {
						GoodsSpecProperty gsp = this.specPropertyService.selectById(Long.valueOf(Long.parseLong(spec_id)));
						goods.getGoods_specs().add(gsp);
//						GoodsSpecProperty gsp=new GoodsSpecProperty();
//						gsp.s
					}
				}
				
				String[] ugc_ids = user_class_ids.split(",");
				for (int i = 0; i < ugc_ids.length; i++) {
					String ugc_id = ugc_ids[i];
					if (!ugc_id.equals("")) {
						UserGoodsClass ugc = this.userGoodsClassService
								.selectById(Long.valueOf(Long.parseLong(ugc_id)));
						goods.getGoods_ugcs().add(ugc);
					}
				}
				String goods_lucene_path = System.getProperty("user.dir") + File.separator + "luence" + File.separator + "goods";
				File file = new File(goods_lucene_path);
				if (!file.exists()) {
					CommUtil.createFolder(goods_lucene_path);
				}
				LuceneVo vo = new LuceneVo();
				vo.setVo_id(goods.getId());
				vo.setVo_title(goods.getGoods_name());
				vo.setVo_content(goods.getGoods_details());
				vo.setVo_type("goods");
				vo.setVo_store_price(CommUtil.null2Double(goods.getStore_price()));
				vo.setVo_add_time(goods.getAddTime().getTime());
				//vo.setVo_goods_salenum(goods.getGoods_salenum());
				LuceneUtil lucene = LuceneUtil.instance();
				LuceneUtil.setIndex_path(goods_lucene_path);
				lucene.writeIndex(vo);
			} else {
				this.goodsService.updateSelectiveById(goods);

				String goods_lucene_path = System.getProperty("user.dir")
						+ File.separator + "luence" + File.separator + "goods";
				File file = new File(goods_lucene_path);
				if (!file.exists()) {
					CommUtil.createFolder(goods_lucene_path);
				}
				LuceneVo vo = new LuceneVo();
				vo.setVo_id(goods.getId());
				vo.setVo_title(goods.getGoods_name());
				vo.setVo_content(goods.getGoods_details());
				vo.setVo_type("goods");
				vo.setVo_store_price(CommUtil.null2Double(goods.getStore_price()));
				vo.setVo_add_time(goods.getAddTime().getTime());
			//	vo.setVo_goods_salenum(goods.getGoods_salenum());
				LuceneUtil lucene = LuceneUtil.instance();
				LuceneUtil.setIndex_path(goods_lucene_path);
				lucene.update(CommUtil.null2String(goods.getId()), vo);
			}
			mv.addObject("obj", goods);
			request.getSession(false).removeAttribute("goods_session");
		} else {
			mv = new JModelAndView("error.html", this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request, response);
			mv.addObject("op_title", "参数错误");
			mv.addObject("url", CommUtil.getURL(request) + "/seller/index.htm");
		}

		return (ModelAndView) mv;
	}
	@SecurityMapping(display = false, rsequence = 0, title = "产品规格显示", value = "/seller/goods_inventory.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_inventory.htm" })
	public ModelAndView goods_inventory(HttpServletRequest request,
			HttpServletResponse response, String goods_spec_ids) {
		ModelAndView mv = mv = new JModelAndView(
				"user/default/usercenter/goods_inventory.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String[] spec_ids = goods_spec_ids.split(",");
		List<GoodsSpecProperty> gsps = new ArrayList();
		// GoodsSpecProperty gsp;
		for (String spec_id : spec_ids) {
			if (!spec_id.equals("")) {
				GoodsSpecProperty gsp = this.specPropertyService
						.selectById(Long.valueOf(Long.parseLong(spec_id)));
				gsps.add(gsp);
			}
		}
		Set<GoodsSpecification> specs = new HashSet<GoodsSpecification>();
		for (GoodsSpecProperty gsp : gsps) {
			specs.add(gsp.getSpec());
		}
		for (GoodsSpecification spec : specs) {
			spec.getProperties().clear();
			for (GoodsSpecProperty gsp : gsps) {
				if (gsp.getSpec().getId().equals(spec.getId())) {
					spec.getProperties().add(gsp);
				}
			}
		}
		GoodsSpecification[] spec_list = (GoodsSpecification[]) specs
				.toArray(new GoodsSpecification[specs.size()]);
		Arrays.sort(spec_list, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				GoodsSpecification a = (GoodsSpecification) obj1;
				GoodsSpecification b = (GoodsSpecification) obj2;
				if (a.getSequence() == b.getSequence()) {
					return 0;
				}
				return a.getSequence() > b.getSequence() ? 1 : -1;
			}
		});
		List gsp_list = generic_spec_property(specs);
		mv.addObject("specs", Arrays.asList(spec_list));
		mv.addObject("gsps", gsp_list);
		return mv;
	}

	public static GoodsSpecProperty[][] list2group(
			List<List<GoodsSpecProperty>> list) {
		GoodsSpecProperty[][] gps = new GoodsSpecProperty[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			gps[i] = ((GoodsSpecProperty[]) ((List) list.get(i))
					.toArray(new GoodsSpecProperty[((List) list.get(i)).size()]));
		}
		return gps;
	}

	private List<List<GoodsSpecProperty>> generic_spec_property(
			Set<GoodsSpecification> specs) {
		List result_list = new ArrayList();
		List list = new ArrayList();
		int max = 1;
		for (GoodsSpecification spec : specs) {
			list.add(spec.getProperties());
		}

		GoodsSpecProperty[][] gsps = list2group(list);
		for (int i = 0; i < gsps.length; i++) {
			max *= gsps[i].length;
		}
		for (int i = 0; i < max; i++) {
			List temp_list = new ArrayList();
			int temp = 1;
			for (int j = 0; j < gsps.length; j++) {
				temp *= gsps[j].length;
				temp_list.add(j, gsps[j][(i / (max / temp) % gsps[j].length)]);
			}
			GoodsSpecProperty[] temp_gsps = (GoodsSpecProperty[]) temp_list
					.toArray(new GoodsSpecProperty[temp_list.size()]);
			Arrays.sort(temp_gsps, new Comparator() {
				public int compare(Object obj1, Object obj2) {
					GoodsSpecProperty a = (GoodsSpecProperty) obj1;
					GoodsSpecProperty b = (GoodsSpecProperty) obj2;
					if (a.getSpec().getSequence() == b.getSpec().getSequence()) {
						return 0;
					}
					return a.getSpec().getSequence() > b.getSpec()
							.getSequence() ? 1 : -1;
				}
			});
			result_list.add(Arrays.asList(temp_gsps));
		}
		return result_list;
	}

	@RequestMapping({ "/seller/swf_upload.htm" })
	public void swf_upload(HttpServletRequest request,
			HttpServletResponse response, String user_id, String album_id) {
		
		User user = this.userService.selectById(CommUtil.null2Long(user_id));
		String path = this.storeTools.createUserFolder(request, this.configService.getSysConfig(), user.getStore_id());
		String url = this.storeTools.createUserFolderURL(this.configService.getSysConfig(), user.getStore_id());
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("imgFile");
		double fileSize = Double.valueOf(file.getSize()).doubleValue();
		fileSize /= 1048576.0D;
		double csize = CommUtil.fileSize(new File(path));
		double remainSpace = 0.0D;
		Store store=storeService.selectById(user.getStore_id());
		StoreGrade sg=storeGradeService.selectById(store.getGrade_id());
		if (sg.getSpaceSize() != 0.0F)
			remainSpace = (user.getStore().getGrade().getSpaceSize() * 1024.0F - csize) * 1024.0D;
		else {
			remainSpace = 10000000.0D;
		}
		Map json_map = new HashMap();
		if (remainSpace > fileSize) {
			try {
				Map map = CommUtil.saveFileToServer(request, "imgFile", path, null, null);
				
				Watermark sWaterMark=new Watermark();
				sWaterMark.setStore_id(user.getStore_id());
				List wms = this.waterMarkService.selectList(sWaterMark);
				if (wms.size() > 0) {
					Watermark mark = (Watermark) wms.get(0);
					Accessory wm_img=accessoryService.selectById(mark.getWm_image_id());
					if (mark.getWm_image_open()) {
						String pressImg = request.getSession().getServletContext().getRealPath("")
								+ File.separator + wm_img.getPath()
								+ File.separator + wm_img.getName();
						String targetImg = path + File.separator + map.get("fileName");
						int pos = mark.getWm_image_pos();
						float alpha = mark.getWm_image_alpha();
						CommUtil.waterMarkWithImage(pressImg, targetImg, pos, alpha);
					}
					if (mark.getWm_text_open()) {
						String targetImg = path + File.separator + map.get("fileName");
						int pos = mark.getWm_text_pos();
						String text = mark.getWm_text();
						String markContentColor = mark.getWm_text_color();
						CommUtil.waterMarkWithText(
								targetImg, targetImg, text, markContentColor,
								new Font(mark.getWm_text_font(), 1, mark.getWm_text_font_size()), pos, 100.0F);
					}
				}
				Accessory image = new Accessory();
				image.setAddTime(new Date());
				image.setExt((String) map.get("mime"));
				image.setPath(url);
				image.setWidth(CommUtil.null2Int(map.get("width")));
				image.setHeight(CommUtil.null2Int(map.get("height")));
				image.setName(CommUtil.null2String(map.get("fileName")));
				image.setUser_id(user.getId());
				Album album = null;
				if ((album_id != null) && (!album_id.equals(""))) {
					album = this.albumService.selectById(CommUtil.null2Long(album_id));
				} else {
					album = this.albumService.getDefaultAlbum(CommUtil.null2Long(user_id));
					if (album == null) {
						album = new Album();
						album.setAddTime(new Date());
						album.setAlbum_name("默认相册");
						album.setAlbum_sequence(-10000);
						album.setAlbum_default(true);
						this.albumService.insertSelective(album);
					}
				}
				image.setAlbum_id(album.getId());
				this.accessoryService.insertSelective(image);
				String imageServer = this.configService.getSysConfig().getImageWebServer();
				String isUrl = (imageServer == ""||imageServer == null) ? CommUtil.getURL(request):imageServer;
				json_map.put("url", isUrl + "/" + url + "/" + image.getName());
				json_map.put("id", image.getId().toString());
				json_map.put("remainSpace", Double.valueOf(remainSpace == 10000.0D ? 0.0D : remainSpace));

				String ext = image.getExt().indexOf(".") < 0 ? "." + image.getExt() : image.getExt();
				String source = request.getSession().getServletContext().getRealPath("/")
						+ image.getPath() + File.separator + image.getName();
				String target = source + "_small" + ext;
				CommUtil.createSmall(source, target, this.configService
						.getSysConfig().getSmallWidth(), this.configService
						.getSysConfig().getSmallHeight());

				String midext = image.getExt().indexOf(".") < 0 ? "." + image.getExt() : image.getExt();
				String midtarget = source + "_middle" + ext;
				CommUtil.createSmall(source, midtarget, this.configService
						.getSysConfig().getMiddleWidth(), this.configService
						.getSysConfig().getMiddleHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			json_map.put("url", "");
			json_map.put("id", "");
			json_map.put("remainSpace", Integer.valueOf(0));
		}

		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(json_map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping({"/seller/upload.htm"})
    public void upload(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {
		
		/*String saveFilePathName = request.getSession().getServletContext().getRealPath("/") + 
				this.configService.getSysConfig().getUploadFilePath() + File.separator + "common";*/
		User user = this.userService.selectById(CommUtil.null2Long(SecurityUserHolder.getSessionLoginUser().getId()));
		
		String saveFilePathName = this.storeTools.createUserFolder(request, this.configService.getSysConfig(), user.getStore_id());
		
		String url = this.storeTools.createUserFolderURL(this.configService.getSysConfig(), user.getStore_id());
		
		String webPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
     
	    if ((this.configService.getSysConfig().getAddress() != null) && 
	       (!this.configService.getSysConfig().getAddress().equals(""))) {
	       webPath = this.configService.getSysConfig().getAddress() + webPath;
	    }
	    JSONObject obj = new JSONObject();
	    try {
	       Map map = CommUtil.saveFileToServer(request, "imgFile", saveFilePathName, null, null);
	       //String url = webPath + "/" + this.configService.getSysConfig().getUploadFilePath() + "/common/" + map.get("fileName");

			String imageServer = this.configService.getSysConfig().getImageWebServer();
			String isUrl = (imageServer == ""||imageServer == null) ? CommUtil.getURL(request):imageServer;
	       url = isUrl + "/" + url + "/" + map.get("fileName");
	       obj.put("error", Integer.valueOf(0));
	       obj.put("url", url);
	    } catch (IOException e) {
	       obj.put("error", Integer.valueOf(1));
	       obj.put("message", e.getMessage());
	       e.printStackTrace();
	    }
	    response.setContentType("text/html");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setCharacterEncoding("UTF-8");
	    try {
	       PrintWriter writer = response.getWriter();
	       writer.print(obj.toJSONString());
	    } catch (IOException e) {
	       e.printStackTrace();
	    }
   }

	@SecurityMapping(display = false, rsequence = 0, title = "商品图片删除", value = "/seller/goods_image_del.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_image_del.htm" })
	public void goods_image_del(HttpServletRequest request,
			HttpServletResponse response, String image_id) {
		User user = this.userService.selectById(SecurityUserHolder.getCurrentUser().getId());
		String path = this.storeTools.createUserFolder(request, this.configService.getSysConfig(), user.getStore_id());
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			Map map = new HashMap();
			Accessory img = this.accessoryService.selectById(CommUtil.null2Long(image_id));
			for (Goods goods : img.getGoods_main_list()) {
				goods.setGoods_main_photo(null);
				this.goodsService.updateSelectiveById(goods);
			}
			for (Goods goods1 : img.getGoods_list()) {
				goods1.getGoods_photos().remove(img);
				this.goodsService.updateSelectiveById(goods1);
			}
			boolean ret = this.accessoryService.deleteById(img.getId());
			if (ret) {
				CommWebUtil.del_acc(request, img);
			}
			double csize = CommUtil.fileSize(new File(path));
			double remainSpace = 10000.0D;
			if (user.getStore().getGrade().getSpaceSize() != 0.0F) {
				remainSpace = CommUtil.div(Double.valueOf(user.getStore().getGrade().getSpaceSize()
								* 1024.0F - csize), Integer.valueOf(1024));
			}
			map.put("result", Boolean.valueOf(ret));
			map.put("remainSpace", Double.valueOf(remainSpace));
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String clearContent(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>";
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>";
			String regEx_html = "<[^>]+>";
			String regEx_html1 = "<[^>]+";
			Pattern p_script = Pattern.compile(regEx_script, 2);
			Matcher m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll("");

			Pattern p_style = Pattern.compile(regEx_style, 2);
			Matcher m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll("");

			Pattern p_html = Pattern.compile(regEx_html, 2);
			Matcher m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll("");

			Pattern p_html1 = Pattern.compile(regEx_html1, 2);
			Matcher m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll("");

			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;
	}

	

	@SecurityMapping(display = false, rsequence = 0, title = "加载商品分类", value = "/seller/load_goods_class.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/load_goods_class.htm" })
	public void load_goods_class(HttpServletRequest request,
			HttpServletResponse response, String pid, String session) {
		GoodsClass obj = this.goodsClassService.selectById(CommUtil.null2Long(pid));
		List list = new ArrayList();
		if (obj != null) {
			GoodsClass sGoodsClass=new GoodsClass();
			sGoodsClass.setParent_id(obj.getId());
			List<GoodsClass> childs=goodsClassService.selectList(sGoodsClass);
			for (GoodsClass gc : childs) {
				Map map = new HashMap();
				map.put("id", gc.getId());
				map.put("className", gc.getClassName());
				list.add(map);
			}
			if (CommUtil.null2Boolean(session)) {
				request.getSession(false).setAttribute("goods_class_info", obj);
			}
		}
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SecurityMapping(display = false, rsequence = 0, title = "添加用户常用商品分类", value = "/seller/load_goods_class.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/add_goods_class_staple.htm" })
	public void add_goods_class_staple(HttpServletRequest request,
			HttpServletResponse response) {
		String ret = "error";
		if (request.getSession(false).getAttribute("goods_class_info") != null) {
			GoodsClass gc = (GoodsClass) request.getSession(false)
					.getAttribute("goods_class_info");
			Map params = new HashMap();
			User user = this.userService.selectById(SecurityUserHolder
					.getCurrentUser().getId());
			params.put("store_id", user.getStore().getId());
			params.put("gc_id", gc.getId());
			Goodsclassstaple sGoodsclassstaple=new Goodsclassstaple();
			sGoodsclassstaple.setStore_id(user.getStore_id());
			sGoodsclassstaple.setGc_id(gc.getId());
			List gcs = this.goodsclassstapleService.selectList(sGoodsclassstaple);
			if (gcs.size() == 0) {
				Goodsclassstaple staple = new Goodsclassstaple();
				staple.setAddTime(new Date());
				staple.setGc(gc);
				String name = generic_goods_class_info(gc);
				staple.setName(name.substring(0, name.length() - 1));
				staple.setStore(user.getStore());
				boolean flag = this.goodsclassstapleService.insertSelective(staple);
				if (flag) {
					Map map = new HashMap();
					map.put("name", staple.getName());
					map.put("id", staple.getId());
					ret = Json.toJson(map);
				}
			}
		}
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

	@SecurityMapping(display = false, rsequence = 0, title = "删除用户常用商品分类", value = "/seller/del_goods_class_staple.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/del_goods_class_staple.htm" })
	public void del_goods_class_staple(HttpServletRequest request,
			HttpServletResponse response, String id) {
		boolean ret = this.goodsclassstapleService.deleteById(Long.valueOf(Long
				.parseLong(id)));
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

	@SecurityMapping(display = false, rsequence = 0, title = "根据用户常用商品分类加载分类信息", value = "/seller/del_goods_class_staple.htm*", rtype = "seller", rname = "商品发布", rcode = "goods_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/load_goods_class_staple.htm" })
	public void load_goods_class_staple(HttpServletRequest request,
			HttpServletResponse response, String id, String name) {
		GoodsClass obj = null;
		if ((id != null) && (!id.equals(""))){
			obj = this.goodsclassstapleService.selectById(Long.valueOf(Long.parseLong(id))).getGc();
		}
		if ((name != null) && (!name.equals(""))){
			GoodsClass sGoodsClass=new GoodsClass();
			sGoodsClass.setClassName(name);
			obj = this.goodsClassService.selectOne(sGoodsClass);
		}
		List list = new ArrayList();
		if (obj != null) {
			request.getSession(false).setAttribute("goods_class_info", obj);
			Map params = new HashMap();
			List second_list = new ArrayList();
			List third_list = new ArrayList();
			List other_list = new ArrayList();
			Object gc;
			if (obj.getLevel() == 2) {
				GoodsClass pGoodsClass=goodsClassService.selectById(obj.getParent_id());
				GoodsClass sGoodsClass=new GoodsClass();
				sGoodsClass.setParent_id(pGoodsClass.getParent_id());
				List<GoodsClass> second_gcs = this.goodsClassService.selectList(sGoodsClass,"sequence asc");
				for (GoodsClass gc1 : second_gcs) {
					Map map = new HashMap();
					map.put("id", gc1.getId());
					map.put("className", gc1.getClassName());
					second_list.add(map);
				}
				
				GoodsClass ssGoodsClass=new GoodsClass();
				ssGoodsClass.setParent_id(obj.getParent_id());
				List<GoodsClass> third_gcs = this.goodsClassService.selectList(ssGoodsClass,"sequence asc");
				for (GoodsClass gc1 : third_gcs) { // gc = (GoodsClass)map.next();
					Map map = new HashMap();
					map.put("id", ((GoodsClass) gc1).getId());
					map.put("className", ((GoodsClass) gc1).getClassName());
					third_list.add(map);
				}
			}

			if (obj.getLevel() == 1) {
				GoodsClass ssGoodsClass=new GoodsClass();
				ssGoodsClass.setParent_id(obj.getParent_id());
				List<GoodsClass> third_gcs = this.goodsClassService.selectList(ssGoodsClass,"sequence asc");
				for (GoodsClass gc1 : third_gcs) {
					// GoodsClass gc = (GoodsClass)((Iterator)gc).next();
					Map map = new HashMap();
					map.put("id", gc1.getId());
					map.put("className", gc1.getClassName());
					second_list.add(map);
				}
			}

			Map map = new HashMap();
			String staple_info = generic_goods_class_info(obj);
			map.put("staple_info", staple_info.substring(0, staple_info.length() - 1));
			other_list.add(map);

			list.add(second_list);
			list.add(third_list);
			list.add(other_list);
		}
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(list, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String generic_goods_class_info(GoodsClass gc) {
		String goods_class_info = gc.getClassName() + ">";
		if (gc.getParent_id() != null) {
			String class_info = generic_goods_class_info(goodsClassService.selectById(gc.getParent_id()));
			goods_class_info = class_info + goods_class_info;
		}
		return goods_class_info;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "出售中的商品列表", value = "/seller/goods.htm*", rtype = "seller", rname = "出售中的商品", rcode = "goods_list_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods.htm" })
	public ModelAndView goods(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType, String goods_name, String user_class_id) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/goods.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			url = CommUtil.getURL(request);
		}
		User user = this.userService.selectById(SecurityUserHolder
				.getCurrentUser().getId());
		String params = "";
		StringBuffer sb=new StringBuffer(" where 1=1");
		sb.append(" and goods_status=0");
		sb.append(" and goods_store_id="+user.getStore_id());
		if ((goods_name != null) && (!goods_name.equals(""))) {
			sb.append(" and goods_name like"+"%"
					+ goods_name + "%");
		}
//		if ((user_class_id != null) && (!user_class_id.equals(""))) {
//			UserGoodsClass ugc = this.userGoodsClassService.selectById(Long
//					.valueOf(Long.parseLong(user_class_id)));
//			qo.addQuery("ugc", ugc, "obj.goods_ugcs", "member of");
//		}
		 Page<Goods> pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), sb.toString(),"addTime desc");
		for(Goods goods:pList.getRecords()){
			Accessory ac=accessoryService.selectById(goods.getGoods_main_photo_id());
			goods.setGoods_main_photo(ac);
		}
		 CommWebUtil.saveIPageList2ModelAndView(url + "/seller/goods.htm", "",
				params, pList, mv);
		mv.addObject("storeTools", this.storeTools);
		mv.addObject("goodsViewTools", this.goodsViewTools);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "仓库中的商品列表", value = "/seller/goods_storage.htm*", rtype = "seller", rname = "仓库中的商品", rcode = "goods_storage_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_storage.htm" })
	public ModelAndView goods_storage(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType, String goods_name, String user_class_id) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/goods_storage.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			url = CommUtil.getURL(request);
		}
		String params = "";
		User user = this.userService.selectById(SecurityUserHolder
				.getCurrentUser().getId());
		StringBuffer sb=new StringBuffer(" where 1=1");
		sb.append(" and goods_status=1");
		sb.append(" and goods_store_id="+user.getStore_id());
		if ((goods_name != null) && (!goods_name.equals(""))) {
			sb.append(" and goods_name like"+"%"
					+ goods_name + "%");
		}
		
		
//		if ((user_class_id != null) && (!user_class_id.equals(""))) {
//			UserGoodsClass ugc = this.userGoodsClassService.selectById(Long
//					.valueOf(Long.parseLong(user_class_id)));
//			qo.addQuery("ugc", ugc, "obj.goods_ugcs", "member of");
//		}
		 Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), sb.toString(),"goods_seller_time desc");
		CommWebUtil.saveIPageList2ModelAndView(url + "/seller/goods_storage.htm",
				"", params, pList, mv);
		mv.addObject("storeTools", this.storeTools);
		mv.addObject("goodsViewTools", this.goodsViewTools);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "违规下架商品", value = "/seller/goods_out.htm*", rtype = "seller", rname = "违规下架商品", rcode = "goods_out_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_out.htm" })
	public ModelAndView goods_out(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType, String goods_name, String user_class_id) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/goods_out.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		String url = this.configService.getSysConfig().getAddress();
		if ((url == null) || (url.equals(""))) {
			url = CommUtil.getURL(request);
		}
		User user = this.userService.selectById(SecurityUserHolder
				.getCurrentUser().getId());
		String params = "";StringBuffer sb=new StringBuffer(" where 1=1 ");
		sb.append(" and goods_status=-2");
		sb.append(" and goods_store_id="+user.getStore_id());
		if ((goods_name != null) && (!goods_name.equals(""))) {
			sb.append(" and goods_name like"+"%"
					+ goods_name + "%");
		}
		
//		if ((user_class_id != null) && (!user_class_id.equals(""))) {
//			UserGoodsClass ugc = this.userGoodsClassService.selectById(Long
//					.valueOf(Long.parseLong(user_class_id)));
//			qo.addQuery("ugc", ugc, "obj.goods_ugcs", "member of");
//		}
		 Page pList = this.goodsService.selectPage(new Page<Goods>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12),sb.toString(), "goods_seller_time desc");
		CommWebUtil.saveIPageList2ModelAndView(url + "/seller/goods_out.htm", "",
				params, pList, mv);
		mv.addObject("storeTools", this.storeTools);
		mv.addObject("goodsViewTools", this.goodsViewTools);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "商品编辑", value = "/seller/goods_edit.htm*", rtype = "seller", rname = "商品编辑", rcode = "goods_edit_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_edit.htm" })
	public ModelAndView goods_edit(HttpServletRequest request,
			HttpServletResponse response, String id) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/add_goods_second.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		Goods obj = this.goodsService.selectById(Long.valueOf(Long
				.parseLong(id)));

		if (obj.getGoods_store().getUser().getId()
				.equals(SecurityUserHolder.getCurrentUser().getId())) {
			User user = this.userService.selectById(SecurityUserHolder
					.getCurrentUser().getId());
			String path = request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator
					+ "upload"
					+ File.separator
					+ "store"
					+ File.separator + user.getStore().getId();
			double img_remain_size = user.getStore().getGrade().getSpaceSize()
					- CommUtil.div(
							Double.valueOf(CommUtil.fileSize(new File(path))),
							Integer.valueOf(1024));
			
			List ugcs = this.userGoodsClassService.selectList("where user_id='"+user.getId()+"' and display=1 and  parent_id is null","sequence asc");
		
			Accessory accessory=new Accessory();
			accessory.setUser_id(user.getId());
			Page pList = this.accessoryService.selectPage(new Page<Accessory>(Integer.valueOf(CommUtil.null2Int(0)), 12),accessory, "addTime desc");
			String photo_url = CommUtil.getURL(request)
					+ "/seller/load_photo.htm";
			List gbs = this.goodsBrandService.selectList(new GoodsBrand(),"sequence asc");
			mv.addObject("gbs", gbs);
			mv.addObject("photos", pList.getRecords());
			mv.addObject(
					"gotoPageAjaxHTML",
					CommUtil.showPageAjaxHtml(photo_url, "",
							pList.getCurrent(), pList.getPages()));
			mv.addObject("ugcs", ugcs);
			mv.addObject("img_remain_size", Double.valueOf(img_remain_size));
			mv.addObject("obj", obj);
			if (request.getSession(false).getAttribute("goods_class_info") != null) {
				GoodsClass session_gc = (GoodsClass) request.getSession(false)
						.getAttribute("goods_class_info");
				GoodsClass gc = this.goodsClassService.selectById(session_gc
						.getId());
				mv.addObject("goods_class_info",
						this.storeTools.generic_goods_class_info(gc));
				mv.addObject("goods_class", gc);
				request.getSession(false).removeAttribute("goods_class_info");
			} else if (obj.getGc() != null) {
				mv.addObject("goods_class_info",
						this.storeTools.generic_goods_class_info(obj.getGc()));
				mv.addObject("goods_class", obj.getGc());
			}

			String goods_session = CommUtil.randomString(32);
			mv.addObject("goods_session", goods_session);
			request.getSession(false).setAttribute("goods_session",
					goods_session);
			mv.addObject("imageSuffix", this.storeViewTools
					.genericImageSuffix(this.configService.getSysConfig()
							.getImageSuffix()));
		} else {
			mv = new JModelAndView("error.html",
					this.configService.getSysConfig(),
					this.userConfigService.getUserConfig(), 1, request,
					response);
			mv.addObject("op_title", "您没有该商品信息！");
			mv.addObject("url", CommUtil.getURL(request) + "/index.htm");
		}
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "商品上下架", value = "/seller/goods_sale.htm*", rtype = "seller", rname = "商品上下架", rcode = "goods_sale_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_sale.htm" })
	public String goods_sale(HttpServletRequest request,
			HttpServletResponse response, String mulitId) {
		String url = "/seller/goods.htm";
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Goods goods = this.goodsService.selectById(Long.valueOf(Long
						.parseLong(id)));

				if (goods.getGoods_store().getUser().getId()
						.equals(SecurityUserHolder.getCurrentUser().getId())) {
					int goods_status = goods.getGoods_status() == 0 ? 1 : 0;
					goods.setGoods_status(goods_status);
					this.goodsService.updateSelectiveById(goods);
					if (goods_status == 0) {
						url = "/seller/goods_storage.htm";

						String goods_lucene_path = System
								.getProperty("user.dir")
								+ File.separator
								+ "luence" + File.separator + "goods";
						File file = new File(goods_lucene_path);
						if (!file.exists()) {
							CommUtil.createFolder(goods_lucene_path);
						}
						LuceneVo vo = new LuceneVo();
						vo.setVo_id(goods.getId());
						vo.setVo_title(goods.getGoods_name());
						vo.setVo_content(goods.getGoods_details());
						vo.setVo_type("goods");
						vo.setVo_store_price(CommUtil.null2Double(goods
								.getStore_price()));
						vo.setVo_add_time(goods.getAddTime().getTime());
						vo.setVo_goods_salenum(goods.getGoods_salenum());
						LuceneUtil lucene = LuceneUtil.instance();
						LuceneUtil.setIndex_path(goods_lucene_path);
						lucene.update(CommUtil.null2String(goods.getId()), vo);
					} else {
						String goods_lucene_path = System
								.getProperty("user.dir")
								+ File.separator
								+ "luence" + File.separator + "goods";
						File file = new File(goods_lucene_path);
						if (!file.exists()) {
							CommUtil.createFolder(goods_lucene_path);
						}
						LuceneUtil lucene = LuceneUtil.instance();
						lucene.delete_index(CommUtil.null2String(goods.getId()));
					}
				}
			}
		}
		return "redirect:" + url;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "商品删除", value = "/seller/goods_del.htm*", rtype = "seller", rname = "商品删除", rcode = "goods_del_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_del.htm" })
	public String goods_del(HttpServletRequest request,
			HttpServletResponse response, String mulitId, String op) {
		String url = "/seller/goods.htm";
		if (CommUtil.null2String(op).equals("storage")) {
			url = "/seller/goods_storage.htm";
		}
		if (CommUtil.null2String(op).equals("out")) {
			url = "/seller/goods_out.htm";
		}
		String[] ids = mulitId.split(",");
		for (String id : ids) {
			if (!id.equals("")) {
				Goods goods = this.goodsService.selectById(CommUtil
						.null2Long(id));

				if (goods.getGoods_store().getUser().getId()
						.equals(SecurityUserHolder.getCurrentUser().getId())) {
					Map map = new HashMap();
					map.put("gid", goods.getId());
					GoodsCart sGoodsCart=new GoodsCart();
					sGoodsCart.setGoods_id(goods.getId());
					List<GoodsCart> goodCarts = this.goodsCartService.selectList(sGoodsCart);
					Long ofid = null;
					Long of_id;
					for (GoodsCart gc : goodCarts) {
						of_id = gc.getOf().getId();
						this.goodsCartService.deleteById(gc.getId());
						OrderForm of = this.orderFormService.selectById(of_id);
						if (of.getGcs().size() == 0) {
							this.orderFormService.deleteById(of_id);
						}
					}

					List<Evaluate> evaluates = goods.getEvaluates();
					for (Evaluate e : evaluates) {
						this.evaluateService.deleteById(e.getId());
					}
					goods.getGoods_ugcs().clear();
					goods.getGoods_ugcs().clear();
					goods.getGoods_photos().clear();
					goods.getGoods_ugcs().clear();
					goods.getGoods_specs().clear();
					this.goodsService.deleteById(goods.getId());

					String goods_lucene_path = System.getProperty("user.dir")
							+ File.separator + "luence" + File.separator
							+ "goods";
					File file = new File(goods_lucene_path);
					if (!file.exists()) {
						CommUtil.createFolder(goods_lucene_path);
					}
					LuceneUtil lucene = LuceneUtil.instance();
					LuceneUtil.setIndex_path(goods_lucene_path);
					lucene.delete_index(CommUtil.null2String(id));
				}
			}
		}
		return "redirect:" + url;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "被举报禁售商品", value = "/seller/goods_report.htm*", rtype = "seller", rname = "被举报禁售商品", rcode = "goods_report_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/goods_report.htm" })
	public ModelAndView goods_report(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String orderBy,
			String orderType) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/goods_report.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		ReportQueryObject qo = new ReportQueryObject(currentPage, mv, orderBy,
				orderType);
		qo.addQuery("obj.goods.goods_store.user.id", new SysMap("user_id",
				SecurityUserHolder.getCurrentUser().getId()), "=");
		qo.addQuery("obj.result", new SysMap("result", Integer.valueOf(1)), "=");
		 Page pList = this.reportService.selectPage(new Page<Report>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), null);
		CommWebUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
		return mv;
	}

	@SecurityMapping(display = false, rsequence = 0, title = "举报图片查看", value = "/seller/report_img.htm*", rtype = "seller", rname = "被举报禁售商品", rcode = "goods_report_seller", rgroup = "商品管理")
	@RequestMapping({ "/seller/report_img.htm" })
	public ModelAndView report_img(HttpServletRequest request,
			HttpServletResponse response, String id) {
		ModelAndView mv = new JModelAndView(
				"user/default/usercenter/report_img.html",
				this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		Report obj = this.reportService.selectById(CommUtil.null2Long(id));
		mv.addObject("obj", obj);
		return mv;
	}

	@RequestMapping({ "/seller/goods_img_album.htm" })
	public ModelAndView goods_img_album(HttpServletRequest request,
			HttpServletResponse response, String currentPage, String type) {
		ModelAndView mv = new JModelAndView("user/default/usercenter/" + type
				+ ".html", this.configService.getSysConfig(),
				this.userConfigService.getUserConfig(), 0, request, response);
		
		Accessory accessory=new Accessory();
		accessory.setUser_id(SecurityUserHolder
				.getCurrentUser().getId());
		 Page pList = this.accessoryService.selectPage(new Page<Accessory>(Integer.valueOf(CommUtil.null2Int(currentPage)), 12), accessory, "addTime desc");
		String photo_url = CommUtil.getURL(request)
				+ "/seller/goods_img_album.htm";
		mv.addObject("photos", pList.getRecords());
		mv.addObject("gotoPageAjaxHTML", CommUtil.showPageAjaxHtml(photo_url,
				"", pList.getCurrent(), pList.getPages()));

		return mv;
	}
}
