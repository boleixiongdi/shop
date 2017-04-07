package com.rt.shop.manage.timer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.entity.Activity;
import com.rt.shop.entity.ActivityGoods;
import com.rt.shop.entity.DeliveryGoods;
import com.rt.shop.entity.Evaluate;
import com.rt.shop.entity.Goods;
import com.rt.shop.entity.Group;
import com.rt.shop.entity.GroupGoods;
import com.rt.shop.entity.MobileVerifyCode;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.OrderLog;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.PredepositLog;
import com.rt.shop.entity.Store;
import com.rt.shop.entity.StoreClass;
import com.rt.shop.entity.StorePoint;
import com.rt.shop.entity.StoreStat;
import com.rt.shop.entity.Template;
import com.rt.shop.entity.User;
import com.rt.shop.service.IActivityGoodsService;
import com.rt.shop.service.IActivityService;
import com.rt.shop.service.IDeliveryGoodsService;
import com.rt.shop.service.IEvaluateService;
import com.rt.shop.service.IGoodsService;
import com.rt.shop.service.IGroupGoodsService;
import com.rt.shop.service.IGroupService;
import com.rt.shop.service.IMobileverifycodeService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IOrderLogService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IPredepositLogService;
import com.rt.shop.service.IStoreClassService;
import com.rt.shop.service.IStorePointService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.IStoreStatService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.service.ITemplateService;
import com.rt.shop.service.IUserService;
import com.rt.shop.tools.MsgTools;
import com.rt.shop.tools.StatTools;
import com.rt.shop.util.SecurityUserHolder;

@Component("shop_stat")
@Transactional
public class StatManageAction {

	@Autowired
	private IStoreStatService storeStatService;

	@Autowired
	private StatTools statTools;

	@Autowired
	private IMobileverifycodeService mobileverifycodeService;

	@Autowired
	private IStoreService storeService;

	@Autowired
	private IEvaluateService evaluateService;

	@Autowired
	private IStorePointService storePointService;

	@Autowired
	private IGroupService groupService;

	@Autowired
	private IOrderFormService orderFormService;

	@Autowired
	private IOrderLogService orderFormLogService;

	@Autowired
	private IPaymentService paymentService;

	@Autowired
	private IPredepositLogService predepositLogService;

	@Autowired
	private ISysConfigService configService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ITemplateService templateService;

	@Autowired
	private IActivityService activityService;

	@Autowired
	private IGoodsService goodsService;

	@Autowired
	private IDeliveryGoodsService deliveryGoodsService;

	@Autowired
	private IStoreClassService storeClassService;

	@Autowired
	private IActivityGoodsService activityGoodsService;

	@Autowired
	private IGroupGoodsService groupGoodsService;

	@Autowired
	private MsgTools msgTools;

	public void execute() throws Exception {
		List stats = this.storeStatService.selectList(null);
				
		StoreStat stat = null;
		if (stats.size() > 0)
			stat = (StoreStat) stats.get(0);
		else {
			stat = new StoreStat();
		}
		stat.setAddTime(new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(12, 30);
		stat.setNext_time(cal.getTime());
		stat.setWeek_complaint(this.statTools.query_complaint(-7));
		stat.setWeek_goods(this.statTools.query_goods(-7));
		stat.setWeek_order(this.statTools.query_order(-7));
		stat.setWeek_report(this.statTools.query_report(-7));
		stat.setWeek_store(this.statTools.query_store(-7));
		stat.setWeek_user(this.statTools.query_user(-7));
		stat.setAll_goods(this.statTools.query_all_goods());
		stat.setAll_store(this.statTools.query_all_store());
		stat.setAll_user(this.statTools.query_all_user());
		stat.setStore_update(this.statTools.query_update_store());
		stat.setOrder_amount(BigDecimal.valueOf(this.statTools.query_all_amount()));
		if (stats.size() > 0)
			this.storeStatService.updateSelectiveById(stat);
		else {
			this.storeStatService.insertSelective(stat);
		}
		cal.setTime(new Date());
		cal.add(12, -15);
		Map params = new HashMap();
		
		 String sql="where  addTime<='"+cal.getTime()+"'";
		List<MobileVerifyCode> mvcs = this.mobileverifycodeService.selectList(null);
		for (MobileVerifyCode mvc : mvcs) {
			this.mobileverifycodeService.deleteById(mvc.getId());
		}

		// 店铺描述、服务、物流信息评价
		List<Store> stores = this.storeService.selectList(null);
		List<Evaluate> evas;
		double service_evaluate;
		double ship_evaluate;
		DecimalFormat df;
		for (Store store : stores) {
			params.clear();
			params.put("store_id", store.getId());
			Evaluate sEvaluate=new Evaluate();
			
			evas = this.evaluateService.selectList(sEvaluate);//TODO
					//.query("select obj from Evaluate obj where obj.of.store.id=:store_id", params, -1, -1);
			double store_evaluate1 = 0.0D;
			double store_evaluate1_total = 0.0D;
			double description_evaluate = 0.0D;
			double description_evaluate_total = 0.0D;
			service_evaluate = 0.0D;
			double service_evaluate_total = 0.0D;
			ship_evaluate = 0.0D;
			double ship_evaluate_total = 0.0D;
			df = new DecimalFormat("0.0");
			for (Evaluate eva1 : evas) {
				store_evaluate1_total = store_evaluate1_total
						+ eva1.getEvaluate_buyer_val();

				description_evaluate_total = description_evaluate_total
						+ CommUtil.null2Double(eva1.getDescription_evaluate());

				service_evaluate_total = service_evaluate_total
						+ CommUtil.null2Double(eva1.getService_evaluate());

				ship_evaluate_total = ship_evaluate_total
						+ CommUtil.null2Double(eva1.getShip_evaluate());
			}
			store_evaluate1 = CommUtil.null2Double(df.format(store_evaluate1_total / evas.size()));
			description_evaluate = CommUtil.null2Double(df.format(description_evaluate_total / evas.size()));
			service_evaluate = CommUtil.null2Double(df.format(service_evaluate_total / evas.size()));
			ship_evaluate = CommUtil.null2Double(df.format(ship_evaluate_total / evas.size()));
			double description_evaluate_halfyear = 0.0D;
			double service_evaluate_halfyear = 0.0D;
			double ship_evaluate_halfyear = 0.0D;
			int description_evaluate_halfyear_count5 = 0;
			int description_evaluate_halfyear_count4 = 0;
			int description_evaluate_halfyear_count3 = 0;
			int description_evaluate_halfyear_count2 = 0;
			int description_evaluate_halfyear_count1 = 0;
			int service_evaluate_halfyear_count5 = 0;
			int service_evaluate_halfyear_count4 = 0;
			int service_evaluate_halfyear_count3 = 0;
			int service_evaluate_halfyear_count2 = 0;
			int service_evaluate_halfyear_count1 = 0;
			int ship_evaluate_halfyear_count5 = 0;
			int ship_evaluate_halfyear_count4 = 0;
			int ship_evaluate_halfyear_count3 = 0;
			int ship_evaluate_halfyear_count2 = 0;
			int ship_evaluate_halfyear_count1 = 0;
			Calendar cal1 = Calendar.getInstance();
			cal1.add(2, -6);
			params.clear();
			params.put("store_id", store.getId());
			params.put("addTime", cal1.getTime());
			evas = this.evaluateService.selectList(sEvaluate);//TODO
				//	.query("select obj from Evaluate obj where obj.of.store.id=:store_id and obj.addTime>=:addTime", params, -1, -1);
			for (Evaluate eva : evas) {
				description_evaluate_halfyear = description_evaluate_halfyear
						+ CommUtil.null2Double(eva.getDescription_evaluate());

				service_evaluate_halfyear = service_evaluate_halfyear
						+ CommUtil.null2Double(eva.getService_evaluate());

				ship_evaluate_halfyear = ship_evaluate_halfyear
						+ CommUtil.null2Double(eva.getService_evaluate());
				if (CommUtil.null2Double(eva.getDescription_evaluate()) >= 4.0D) {
					description_evaluate_halfyear_count5++;
				}
				if ((CommUtil.null2Double(eva.getDescription_evaluate()) >= 3.0D)
						&& (CommUtil.null2Double(eva.getDescription_evaluate()) < 4.0D)) {
					description_evaluate_halfyear_count4++;
				}
				if ((CommUtil.null2Double(eva.getDescription_evaluate()) >= 2.0D)
						&& (CommUtil.null2Double(eva.getDescription_evaluate()) < 3.0D)) {
					description_evaluate_halfyear_count3++;
				}
				if ((CommUtil.null2Double(eva.getDescription_evaluate()) >= 1.0D)
						&& (CommUtil.null2Double(eva.getDescription_evaluate()) < 2.0D)) {
					description_evaluate_halfyear_count2++;
				}
				if ((CommUtil.null2Double(eva.getDescription_evaluate()) >= 0.0D)
						&& (CommUtil.null2Double(eva.getDescription_evaluate()) < 1.0D)) {
					description_evaluate_halfyear_count1++;
				}
				if (CommUtil.null2Double(eva.getService_evaluate()) >= 4.0D) {
					service_evaluate_halfyear_count5++;
				}
				if ((CommUtil.null2Double(eva.getService_evaluate()) >= 3.0D)
						&& (CommUtil.null2Double(eva.getService_evaluate()) < 4.0D)) {
					service_evaluate_halfyear_count4++;
				}
				if ((CommUtil.null2Double(eva.getService_evaluate()) >= 2.0D)
						&& (CommUtil.null2Double(eva.getService_evaluate()) < 3.0D)) {
					service_evaluate_halfyear_count3++;
				}
				if ((CommUtil.null2Double(eva.getService_evaluate()) >= 1.0D)
						&& (CommUtil.null2Double(eva.getService_evaluate()) < 2.0D)) {
					service_evaluate_halfyear_count2++;
				}
				if ((CommUtil.null2Double(eva.getService_evaluate()) >= 0.0D)
						&& (CommUtil.null2Double(eva.getService_evaluate()) < 1.0D)) {
					service_evaluate_halfyear_count1++;
				}
				if (CommUtil.null2Double(eva.getShip_evaluate()) >= 4.0D) {
					ship_evaluate_halfyear_count5++;
				}
				if ((CommUtil.null2Double(eva.getShip_evaluate()) >= 3.0D)
						&& (CommUtil.null2Double(eva.getShip_evaluate()) < 4.0D)) {
					ship_evaluate_halfyear_count4++;
				}
				if ((CommUtil.null2Double(eva.getShip_evaluate()) >= 2.0D)
						&& (CommUtil.null2Double(eva.getShip_evaluate()) < 3.0D)) {
					ship_evaluate_halfyear_count3++;
				}
				if ((CommUtil.null2Double(eva.getShip_evaluate()) >= 1.0D)
						&& (CommUtil.null2Double(eva.getShip_evaluate()) < 2.0D)) {
					ship_evaluate_halfyear_count2++;
				}
				if ((CommUtil.null2Double(eva.getShip_evaluate()) < 0.0D)
						|| (CommUtil.null2Double(eva.getShip_evaluate()) >= 1.0D))
					continue;
				ship_evaluate_halfyear_count1++;
			}

			if (evas.size() > 0) {
				description_evaluate_halfyear = description_evaluate_halfyear / evas.size();

				service_evaluate_halfyear = service_evaluate_halfyear / evas.size();
				ship_evaluate_halfyear /= evas.size();
			}
			params.clear();
			params.put("store_id", store.getId());
			StorePoint sStorePoint=new StorePoint();
			
			List sps = this.storePointService.selectList(sStorePoint);//TODO
					//.query("select obj from StorePoint obj where obj.store.id=:store_id", params, -1, -1);
			StorePoint point = null;
			if (sps.size() > 0)
				point = (StorePoint) sps.get(0);
			else {
				point = new StorePoint();
			}
			point.setStatTime(new Date());
			point.setStore_id(store.getId());
			point.setDescription_evaluate(BigDecimal.valueOf(description_evaluate));
			point.setService_evaluate(BigDecimal.valueOf(service_evaluate));
			point.setShip_evaluate(BigDecimal.valueOf(ship_evaluate));
			point.setStore_evaluate1(BigDecimal.valueOf(store_evaluate1));
			point.setDescription_evaluate_halfyear(BigDecimal.valueOf(description_evaluate_halfyear));
			point.setDescription_evaluate_halfyear_count1(description_evaluate_halfyear_count1);
			point.setDescription_evaluate_halfyear_count2(description_evaluate_halfyear_count2);
			point.setDescription_evaluate_halfyear_count3(description_evaluate_halfyear_count3);
			point.setDescription_evaluate_halfyear_count4(description_evaluate_halfyear_count4);
			point.setDescription_evaluate_halfyear_count5(description_evaluate_halfyear_count5);
			point.setService_evaluate_halfyear(BigDecimal.valueOf(service_evaluate_halfyear));
			point.setService_evaluate_halfyear_count1(service_evaluate_halfyear_count1);
			point.setService_evaluate_halfyear_count2(service_evaluate_halfyear_count2);
			point.setService_evaluate_halfyear_count3(service_evaluate_halfyear_count3);
			point.setService_evaluate_halfyear_count4(service_evaluate_halfyear_count4);
			point.setService_evaluate_halfyear_count5(service_evaluate_halfyear_count5);
			point.setShip_evaluate_halfyear(BigDecimal.valueOf(ship_evaluate));
			point.setShip_evaluate_halfyear_count1(ship_evaluate_halfyear_count1);
			point.setShip_evaluate_halfyear_count2(ship_evaluate_halfyear_count2);
			point.setShip_evaluate_halfyear_count3(ship_evaluate_halfyear_count3);
			point.setShip_evaluate_halfyear_count4(ship_evaluate_halfyear_count4);
			point.setShip_evaluate_halfyear_count5(ship_evaluate_halfyear_count5);
			if (sps.size() > 0)
				this.storePointService.updateSelectiveById(point);
			else {
				this.storePointService.insertSelective(point);
			}
		}

		List<StoreClass> scs = this.storeClassService.selectList(null);
		double description_evaluate;
		// double service_evaluate;
		for (StoreClass sc : scs) {
			description_evaluate = 0.0D;
			service_evaluate = 0.0D;
			ship_evaluate = 0.0D;
			params.clear();
			params.put("sc_id", sc.getId());
			StorePoint sStorePoint=new StorePoint();
			
			List<StorePoint> sp_list = this.storePointService.selectList(null);//TODO
				//	.query("select obj from StorePoint obj where obj.store.sc.id=:sc_id", params, -1, -1);
			for (StorePoint sp : sp_list) {
				description_evaluate = CommUtil.add(Double.valueOf(description_evaluate),
						sp.getDescription_evaluate());
				service_evaluate = CommUtil.add(Double.valueOf(service_evaluate),
						sp.getService_evaluate());
				ship_evaluate = CommUtil.add(Double.valueOf(ship_evaluate),
						sp.getShip_evaluate());
			}
			sc.setDescription_evaluate(BigDecimal.valueOf(CommUtil.div(Double.valueOf(description_evaluate),
					Integer.valueOf(sp_list.size()))));
			sc.setService_evaluate(BigDecimal.valueOf(CommUtil.div(Double.valueOf(service_evaluate),
					Integer.valueOf(sp_list.size()))));
			sc.setShip_evaluate(BigDecimal.valueOf(CommUtil.div(Double.valueOf(ship_evaluate),
					Integer.valueOf(sp_list.size()))));
			this.storeClassService.updateSelectiveById(sc);
		}

		List<Group> groups = this.groupService.selectList(new Group(), "addTime");
				
		GroupGoods gg;
		Goods goods;
		for (Group group : groups) {
			if ((group.getBeginTime().before(new Date()))
					&& (group.getEndTime().after(new Date()))) {
				group.setStatus(0);
				this.groupService.updateSelectiveById(group);
			}
			if (group.getEndTime().before(new Date())) {
				group.setStatus(-2);
				this.groupService.updateSelectiveById(group);

				for (GroupGoods gg1 : group.getGg_list()) {
					// gg = (GroupGoods)service_evaluate.next();
					gg1.setGg_status(-2);
					this.groupGoodsService.updateSelectiveById(gg1);
					goods = gg1.getGg_goods();
					goods.setGroup_buy(0);
					goods.setGoods_current_price(goods.getStore_price());
					this.goodsService.updateSelectiveById(goods);
				}
			}
		}

		
		 String sql1="where  ac_end_time<='"+new Date()+"' and ac_status="+Integer.valueOf(1);
		List<Activity> acts = this.activityService.selectList(sql1,null);
			//	.query("select obj from Activity obj where obj.ac_end_time<=:ac_end_time and obj.ac_status=:ac_status", params, -1, -1);
		for (Activity act : acts) {
			act.setAc_status(0);
			this.activityService.updateSelectiveById(act);
			for (ActivityGoods ac : act.getAgs()) {
				ac.setAg_status(-2);
				this.activityGoodsService.updateSelectiveById(ac);
				Goods goods1 = ac.getAg_goods();
				goods1.setActivity_status(0);
				goods1.setGoods_current_price(goods1.getStore_price());
				this.goodsService.updateSelectiveById(goods1);
			}
		}
		
		//订单到期自动email或短信通知买家
		int auto_order_notice = this.configService.getSysConfig().getAuto_order_notice();
		cal = Calendar.getInstance();
		params.clear();
		cal.add(6, -auto_order_notice);
		
		String sql2="where shipTime<='"+cal.getTime()+"' and (auto_confirm_email="+Boolean.valueOf(true)+" or auto_confirm_sms="+Boolean.valueOf(true)+")";
		List<OrderForm> notice_ofs = this.orderFormService.selectList(sql2,null);
				//.query("select obj from OrderForm obj where obj.shipTime<=:shipTime and (obj.auto_confirm_email=:auto_confirm_email or obj.auto_confirm_sms=:auto_confirm_sms)",
						
		for (OrderForm of : notice_ofs) {
			if (!of.getAuto_confirm_email()) {
				boolean email = send_email(of,
						"email_tobuyer_order_will_confirm_notify");
				if (email) {
					of.setAuto_confirm_email(true);
					this.orderFormService.updateSelectiveById(of);
				}
			}
			if (!of.getAuto_confirm_sms()) {
				boolean sms = send_sms(of, userService.selectById(of.getUser_id()).getMobile(),
						"sms_tobuyer_order_will_confirm_notify");
				if (sms) {
					of.setAuto_confirm_sms(true);
					this.orderFormService.updateSelectiveById(of);
				}
			}
		}

		//自定义订单到期自动收货
		int auto_order_confirm = this.configService.getSysConfig().getAuto_order_confirm();
		cal = Calendar.getInstance();
		cal.add(6, -auto_order_confirm);
		
		String sql3="where shipTime<='"+cal.getTime()+"'";
		List<OrderForm> confirm_ofs = this.orderFormService.selectList(sql3,null);
				//"select obj from OrderForm obj where obj.shipTime<=:shipTime", params, -1, -1);
		OrderLog ofl;
		PredepositLog log;
		User buyer;
		for (OrderForm of : confirm_ofs) {
			of.setOrder_status(40);
			boolean ret = this.orderFormService.updateSelectiveById(of);
			if (ret) {
				ofl = new OrderLog();
				ofl.setAddTime(new Date());
				ofl.setLog_info("确认收货");
				ofl.setLog_user_id(SecurityUserHolder.getCurrentUser().getId());
				ofl.setOf_id(of.getId());
				this.orderFormLogService.insertSelective(ofl);
				if (this.configService.getSysConfig().getEmailEnable()) {
					send_email(of, "email_toseller_order_receive_ok_notify");
				}
				User sUser=new User();
				sUser.setStore_id(storeService.selectById(of.getStore_id()).getId());
				User storeUser=userService.selectOne(sUser);
				Payment payment=paymentService.selectById(of.getPayment_id());
				//User orderUser=userService.selectById(of.getUser_id());
				
				if (this.configService.getSysConfig().getSmsEnbale()) {
					send_sms(of, storeUser.getMobile(), "sms_toseller_order_receive_ok_notify");
				}
				if (payment.getMark().equals("balance")) {
					User seller = this.userService.selectById(storeUser.getId());
					if (this.configService.getSysConfig().getBalance_fenrun() == 1) {
						
						Payment sPayment=new Payment();
						sPayment.setType("admin");
						sPayment.setMark("alipay");
						List payments = this.paymentService.selectList(sPayment);
								
						Payment shop_payment = new Payment();
						if (payments.size() > 0) {
							shop_payment = (Payment) payments.get(0);
						}

						double shop_availableBalance = CommUtil.null2Double(of.getTotalPrice())
								* CommUtil.null2Double(shop_payment.getBalance_divide_rate());
						User admin = this.userService.selectUserByUsername("admin");
						admin.setAvailableBalance(BigDecimal.valueOf(CommUtil.add(admin.getAvailableBalance(),
										Double.valueOf(shop_availableBalance))));
						this.userService.updateSelectiveById(admin);
						PredepositLog log1 = new PredepositLog();
						log1.setAddTime(new Date());
						log1.setPd_log_user_id(seller.getId());
						log1.setPd_op_type("分润");
						log1.setPd_log_amount(BigDecimal.valueOf(shop_availableBalance));
						log1.setPd_log_info("自动确认收货平台分润获得预存款，订单" + of.getOrder_id());
						log1.setPd_type("可用预存款");
						this.predepositLogService.insertSelective(log1);

						double seller_availableBalance = CommUtil.null2Double(of.getTotalPrice()) - shop_availableBalance;
						seller.setAvailableBalance(BigDecimal.valueOf(CommUtil.add(seller.getAvailableBalance(),
										Double.valueOf(seller_availableBalance))));
						this.userService.updateSelectiveById(seller);
						PredepositLog log11 = new PredepositLog();
						log11.setAddTime(new Date());
						log11.setPd_log_user_id(seller.getId());
						log11.setPd_op_type("增加");
						log11.setPd_log_amount(BigDecimal.valueOf(seller_availableBalance));
						log11.setPd_log_info("自动确认收货增加预存款，订单号" + of.getOrder_id());
						log11.setPd_type("可用预存款");
						this.predepositLogService.insertSelective(log11);

						User buyer1 = userService.selectById(of.getUser_id());
						buyer1.setFreezeBlance(BigDecimal.valueOf(CommUtil.subtract(buyer1.getFreezeBlance(), of.getTotalPrice())));
						this.userService.updateSelectiveById(buyer1);
					} else {
						seller.setAvailableBalance(BigDecimal.valueOf(CommUtil.add(seller.getAvailableBalance(), of.getTotalPrice())));
						this.userService.updateSelectiveById(seller);
						log = new PredepositLog();
						log.setAddTime(new Date());
						log.setPd_log_user_id(seller.getId());
						log.setPd_op_type("增加");
						log.setPd_log_amount(of.getTotalPrice());
						log.setPd_log_info("自动确认收货增加预存款,订单号" + of.getOrder_id());

						log.setPd_type("可用预存款");
						this.predepositLogService.insertSelective(log);

						buyer = userService.selectById(of.getId());
						buyer.setFreezeBlance(BigDecimal.valueOf(CommUtil.subtract(buyer.getFreezeBlance(), of.getTotalPrice())));
						this.userService.updateSelectiveById(buyer);
					}
				}
			}
		}

		int auto_order_evaluate = this.configService.getSysConfig().getAuto_order_evaluate();
		cal = Calendar.getInstance();
		params.clear();
		cal.add(6, -auto_order_evaluate);
		/**2016年3月30日凌晨修改，定时器执行到此发现hql语句中无此参数报错*/
		// 缺少：return_shipTime参数，将查询语句中的return_shipTime修改为auto_order_evaluate
		params.put("auto_order_evaluate", cal.getTime());//自动订单评价
		params.put("order_status_40", Integer.valueOf(40));
		params.put("order_status_47", Integer.valueOf(47));
		params.put("order_status_48", Integer.valueOf(48));
		params.put("order_status_49", Integer.valueOf(49));
		params.put("order_status_50", Integer.valueOf(50));
		params.put("order_status_60", Integer.valueOf(60));
		String sql4="where return_shipTime<='"+cal.getTime()+"' and order_status>=40 and order_status!=47 and order_status!=48 and order_status!=49 and order_status!=50 and order_status!=60";
		List<OrderForm> confirm_evaluate_ofs = this.orderFormService.selectList(sql4,null);//TODO
				//.query("select obj from OrderForm obj where obj.return_shipTime<=:auto_order_evaluate and obj.order_status>=:order_status_40 and obj.order_status!=:order_status_47 and obj.order_status!=:order_status_48 and obj.order_status!=:order_status_49 and obj.order_status!=:order_status_50 and obj.order_status!=:order_status_60", params, -1, -1);
		for (OrderForm order : confirm_evaluate_ofs) {
			order.setOrder_status(65);
			this.orderFormService.updateSelectiveById(order);
		}

		int auto_order_return = this.configService.getSysConfig().getAuto_order_return();
		cal = Calendar.getInstance();
		cal.add(6, -auto_order_return);
		
		String sql5="where return_shipTime<='"+cal.getTime()+"' and order_status=46";
		List<OrderForm> confirm_return_ofs = this.orderFormService.selectList(sql5,null);
				//.query("select obj from OrderForm obj where obj.return_shipTime<=:return_shipTime and obj.order_status=:order_status", params, -1, -1);
		for (OrderForm order : confirm_return_ofs) {
			order.setOrder_status(49);
			this.orderFormService.updateSelectiveById(order);
		}

		params.clear();
		params.put("delivery_end_time", new Date());
		
		List<DeliveryGoods> dgs = this.deliveryGoodsService.selectList(null);//TODO
				//.query("select obj from DeliveryGoods obj where obj.d_goods.goods_store.delivery_end_time<:delivery_end_time", params, -1, -1);
		for (DeliveryGoods dg : dgs) {
			dg.setD_status(-2);
			this.deliveryGoodsService.updateSelectiveById(dg);
			Goods goods1 = dg.getD_goods();
			goods1.setDelivery_status(0);
			this.goodsService.updateSelectiveById(goods1);
		}

		
		String sql7="where combin_end_time<'"+new Date()+"'";
		stores = this.storeService.selectList(sql7,null);//
				//.query("select obj from Store obj where obj.combin_end_time<=:combin_end_time", params, -1, -1);
		
		for (Store store : stores) {
			List<Goods> goodsList=goodsService.selectGoodsByStore(store.getId());
			for (Goods goods1 : goodsList) {
				if (goods1.getCombin_status() != 0) {
					goods1.setCombin_begin_time(null);
					goods1.setCombin_end_time(null);
					goods1.setCombin_price(null);
					goods1.setCombin_status(0);
					goods1.getCombin_goods().clear();
					this.goodsService.updateSelectiveById(goods1);
				}
			}
		}

		List<Goods> goods_list=goodsService.selectList(null);//TODO
//		List<Goods> goods_list = this.evaluateService.query_goods(
//				"select distinct obj.evaluate_goods from Evaluate obj ", null, -1, -1);
		
		for (Goods goods1 : goods_list) {
			description_evaluate = 0.0D;
			
			Evaluate sEvaluate=new Evaluate();
			sEvaluate.setEvaluate_goods_id(goods1.getId());
			List<Evaluate> eva_list = this.evaluateService.selectList(sEvaluate);
				
			for (Evaluate eva : eva_list) {
				description_evaluate = CommUtil.add(eva.getDescription_evaluate(),
						Double.valueOf(description_evaluate));
			}

			description_evaluate = CommUtil.div(Double.valueOf(description_evaluate), Integer.valueOf(eva_list.size()));
			goods1.setDescription_evaluate(BigDecimal.valueOf(description_evaluate));
			this.goodsService.updateSelectiveById(goods1);
		}

		
		Store sStore=new Store();
		sStore.setWeixin_status(Integer.valueOf(1));
		List<Store> store_list = this.storeService.selectList(sStore);
			
		for (Store store : store_list)
			if (store.getWeixin_end_time().before(new Date())) {
				store.setWeixin_status(2);
				this.storeService.updateSelectiveById(store);
			}
	}

	private boolean send_email(OrderForm order, String mark) throws Exception {
		Template template = this.templateService.selectByMark(mark);
			
		if (template.getOpen()) {
			User sUser=new User();
			sUser.setStore_id(storeService.selectById(order.getStore_id()).getId());
			User storeUser=userService.selectOne(sUser);
			
			User orderUser=userService.selectById(order.getUser_id());
			String email = orderUser.getEmail();
			String subject = template.getTitle();
			String path = System.getProperty("shopping.root") + "vm" + File.separator;
			PrintWriter pwrite = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream(path + "msg.vm", false), "UTF-8"));
			pwrite.print(template.getContent());
			pwrite.flush();
			pwrite.close();

			Properties p = new Properties();
			p.setProperty("file.resource.loader.path", System.getProperty("shopping.root") + "vm" + File.separator);
			p.setProperty("input.encoding", "UTF-8");
			p.setProperty("output.encoding", "UTF-8");
			Velocity.init(p);
			org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("buyer", orderUser);
			context.put("seller", storeUser);
			context.put("config", this.configService.getSysConfig());
			context.put("send_time", CommUtil.formatLongDate(new Date()));
			context.put("webPath", this.configService.getSysConfig().getAddress());
			context.put("order", order);
			StringWriter writer = new StringWriter();
			blank.merge(context, writer);

			String content = writer.toString();
			boolean ret = this.msgTools.sendEmail(email, subject, content);
			return ret;
		}
		return false;
	}

	private boolean send_sms(OrderForm order, String mobile, String mark)
			throws Exception {
		Template template = this.templateService.selectByMark(mark);
		if (template.getOpen()) {
			String path = System.getProperty("shopping.root") + "vm" + File.separator;
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
			p.setProperty("file.resource.loader.path", System.getProperty("shopping.root") + "vm" + File.separator);
			p.setProperty("input.encoding", "UTF-8");
			p.setProperty("output.encoding", "UTF-8");
			Velocity.init(p);
			org.apache.velocity.Template blank = Velocity.getTemplate("msg.vm", "UTF-8");
			VelocityContext context = new VelocityContext();
			context.put("buyer", orderUser);
			context.put("seller",storeUser);
			context.put("config", this.configService.getSysConfig());
			context.put("send_time", CommUtil.formatLongDate(new Date()));
			context.put("webPath", this.configService.getSysConfig().getAddress());
			context.put("order", order);
			StringWriter writer = new StringWriter();
			blank.merge(context, writer);

			String content = writer.toString();
			boolean ret = this.msgTools.sendSMS(mobile, content);
			return ret;
		}
		return false;
	}
}
