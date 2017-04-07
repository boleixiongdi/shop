package com.rt.shop.pay.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.common.tools.CommUtil;
import com.rt.shop.common.tools.Md5Encrypt;
import com.rt.shop.domain.virtual.SysMap;
import com.rt.shop.entity.Address;
import com.rt.shop.entity.GoldRecord;
import com.rt.shop.entity.IntegralGoodsOrder;
import com.rt.shop.entity.OrderForm;
import com.rt.shop.entity.Payment;
import com.rt.shop.entity.Predeposit;
import com.rt.shop.entity.SysConfig;
import com.rt.shop.entity.YiKaTongPay;
import com.rt.shop.pay.alipay.config.AlipayConfig;
import com.rt.shop.pay.alipay.services.AlipayService;
import com.rt.shop.pay.alipay.util.AlipaySubmit;
import com.rt.shop.pay.alipay.util.UtilDate;
import com.rt.shop.pay.bill.config.BillConfig;
import com.rt.shop.pay.bill.services.BillService;
import com.rt.shop.pay.bill.util.BillCore;
import com.rt.shop.pay.bill.util.MD5Util;
import com.rt.shop.pay.chinabank.util.ChinaBankSubmit;
import com.rt.shop.pay.paypal.PaypalTools;
import com.rt.shop.pay.yikatong.config.YiKatongConfig;
import com.rt.shop.pay.yikatong.services.YiKatongService;
import com.rt.shop.service.IAddressService;
import com.rt.shop.service.IGoldRecordService;
import com.rt.shop.service.IIntegralGoodsOrderService;
import com.rt.shop.service.IOrderFormService;
import com.rt.shop.service.IPaymentService;
import com.rt.shop.service.IPredepositService;
import com.rt.shop.service.IStoreService;
import com.rt.shop.service.ISysConfigService;
import com.rt.shop.util.SecurityUserHolder;

@Component
public class PayTools {

	@Autowired(required=false)
	private IPaymentService paymentService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IStoreService storeService;

	@Autowired(required=false)
	private IOrderFormService orderFormService;

	@Autowired(required=false)
	private IPredepositService predepositService;

	@Autowired(required=false)
	private IGoldRecordService goldRecordService;

	@Autowired(required=false)
	private IIntegralGoodsOrderService integralGoodsOrderService;

	@Autowired(required=false)
	private ISysConfigService configService;

	// alipay------------------------
	@SuppressWarnings("unchecked")
	public String genericAlipay(String url, String payment_id, String type, String id) {
		
		String result = "";
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		int interfaceType = payment.getInterfaceType();
		AlipayConfig config = new AlipayConfig();
//		Map params = new HashMap();
//		params.put("type", "admin");
//		params.put("mark", "alipay");
//		List payments = this.paymentService.query("select obj from Payment obj where obj.type=:type and obj.mark=:mark",
//				params, -1, -1);
		Payment sPayment=new Payment();
		sPayment.setType("admin");
		sPayment.setMark("alipay");
		List<Payment> payments =this.paymentService.selectList(sPayment);
		Payment shop_payment = new Payment();
		if (payments.size() > 0) {
			shop_payment = (Payment) payments.get(0);
		}
		if ((!CommUtil.null2String(payment.getSafeKey()).equals(""))
				&& (!CommUtil.null2String(payment.getPartner()).equals(""))) {
			config.setKey(payment.getSafeKey());
			config.setPartner(payment.getPartner());
		} else {
			config.setKey(shop_payment.getSafeKey());
			config.setPartner(shop_payment.getPartner());
		}
		config.setSeller_email(payment.getSeller_email());
		config.setNotify_url(url + "/alipay_notify.htm");
		config.setReturn_url(url + "/aplipay_return.htm");

		SysConfig sys_config = this.configService.getSysConfig();
		if (sys_config.getAlipay_fenrun() == 1) {
			interfaceType = 0;
		}
		if (interfaceType == 0) {
			if (sys_config.getAlipay_fenrun() == 1) {
				config.setKey(shop_payment.getSafeKey());
				config.setPartner(shop_payment.getPartner());
				config.setSeller_email(shop_payment.getSeller_email());
			}
			String out_trade_no = "";
			if (type.equals("goods")) {
			//	out_trade_no = of.getId().toString();
				out_trade_no = of.getOrder_id().toString();
			}
			if (type.equals("cash")) {
				out_trade_no = obj.getId().toString();
			}
			if (type.equals("gold")) {
				out_trade_no = gold.getId().toString();
			}
			if (type.equals("integral")) {
				out_trade_no = ig_order.getId().toString();
			}

			String subject = "";
			if (type.equals("goods")) {
				subject = "甜园云家-"+this.storeService.selectById(of.getStore_id()).getStore_name();
				//subject=of.getGcs().get(0).getGoods().getGoods_name();
			}
			if (type.equals("cash")) {
				subject = obj.getPd_sn();
			}
			if (type.equals("gold")) {
				subject = gold.getGold_sn();
			}
			if (type.equals("integral")) {
				subject = ig_order.getIgo_order_sn();
			}
			String body = type;
			//订单描述 显示此订单所有的商品名称+goods ,goods做前台业务判断
//			StringBuffer bodys = new StringBuffer();
//			List<GoodsCart> goodsCart=this.orderFormService.getGoodCartByOrder(of);
//			for(GoodsCart gc : goodsCart){
//				bodys.append(gc.getGoods().getGoods_name()+",");
//			}
//			bodys.append(body);
			

			String total_fee = "";
			if (type.equals("goods")) {
				total_fee = CommUtil.null2String(of.getTotalPrice());
			}
			if (type.equals("cash")) {
				total_fee = CommUtil.null2String(obj.getPd_amount());
			}
			if (type.equals("gold")) {
				total_fee = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
			}
			if (type.equals("integral")) {
				total_fee = CommUtil.null2String(ig_order.getIgo_trans_fee());
			}

			String paymethod = "";

			String defaultbank = "";

			String anti_phishing_key = "";

			String exter_invoke_ip = "";

			String extra_common_param = type;

			String buyer_email = "";

			String show_url = "";

			String royalty_type = "10";

			String royalty_parameters = "";
			if ((type.equals("goods")) && (sys_config.getAlipay_fenrun() == 1)) {
				double fenrun_rate = CommUtil.null2Double(shop_payment.getAlipay_divide_rate());
				double apliay_rate = CommUtil.null2Double(shop_payment.getAlipay_rate()) / 100.0D;
				double shop_fee = CommUtil.null2Double(total_fee) * (1.0D - apliay_rate);
				shop_fee *= fenrun_rate;
				double seller_fee = CommUtil.null2Double(total_fee) * (1.0D - apliay_rate) - shop_fee;
				royalty_parameters = payment.getSeller_email() + "^"
						+ String.format("%.2f", new Object[] { Double.valueOf(seller_fee) }) + "^商家";
			}

			Map sParaTemp = new HashMap();
			sParaTemp.put("payment_type", "1");
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			//sParaTemp.put("body", bodys.substring(0, bodys.length()-1));
			sParaTemp.put("body", "pc支付");
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("paymethod", paymethod);
			sParaTemp.put("defaultbank", defaultbank);
			sParaTemp.put("anti_phishing_key", anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
			sParaTemp.put("extra_common_param", extra_common_param);
			sParaTemp.put("buyer_email", buyer_email);
			if ((type.equals("goods")) && (sys_config.getAlipay_fenrun() == 1)) {
				sParaTemp.put("royalty_type", royalty_type);
				sParaTemp.put("royalty_parameters", royalty_parameters);
			}

			result = AlipayService.create_direct_pay_by_user(config, sParaTemp);
		}
		if (interfaceType == 1) {
			String out_trade_no = "";
			if (type.equals("goods")) {
				out_trade_no = of.getId().toString();
			}
			if (type.equals("cash")) {
				out_trade_no = obj.getId().toString();
			}
			if (type.equals("gold")) {
				out_trade_no = gold.getId().toString();
			}
			if (type.equals("integral")) {
				out_trade_no = ig_order.getId().toString();
			}

			String subject = "";
			if (type.equals("goods")) {
				//subject = of.getOrder_id();
				//subject = "甜园云家-"+of.getStore().getStore_name();
				subject = "甜园云家-"+this.storeService.selectById(of.getStore_id()).getStore_name();
			}
			if (type.equals("cash")) {
				subject = obj.getPd_sn();
			}
			if (type.equals("gold")) {
				subject = gold.getGold_sn();
			}
			if (type.equals("integral")) {
				subject = ig_order.getIgo_order_sn();
			}

			String body = type;
			//订单描述 显示此订单所有的商品名称+goods ,goods做前台业务判断
//			StringBuffer bodys = new StringBuffer();
//			List<GoodsCart> goodsCart=of.getGcs();
//			for(GoodsCart gc : goodsCart){
//				bodys.append(gc.getGoods().getGoods_name()+",");
//			}
//			bodys.append(body);
			String total_fee = "";
			if (type.equals("goods")) {
				total_fee = CommUtil.null2String(of.getTotalPrice());
			}
			if (type.equals("cash")) {
				total_fee = CommUtil.null2String(obj.getPd_amount());
			}
			if (type.equals("gold")) {
				total_fee = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
			}
			if (type.equals("integral")) {
				total_fee = CommUtil.null2String(ig_order.getIgo_trans_fee());
			}

			String price = String.valueOf(total_fee);

			String logistics_fee = "0.00";

			String logistics_type = "EXPRESS";

			String logistics_payment = "SELLER_PAY";

			String quantity = "1";

			String extra_common_param = "";

			String receive_name = "";
			String receive_address = "";
			String receive_zip = "";
			String receive_phone = "";
			String receive_mobile = "";

			String show_url = "";

			Map sParaTemp = new HashMap();
			sParaTemp.put("payment_type", "1");
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			//sParaTemp.put("body", bodys.toString());
			
			sParaTemp.put("body", "支付宝pc支付");
			sParaTemp.put("price", price);
			sParaTemp.put("logistics_fee", logistics_fee);
			sParaTemp.put("logistics_type", logistics_type);
			sParaTemp.put("logistics_payment", logistics_payment);
			sParaTemp.put("quantity", quantity);
			sParaTemp.put("extra_common_param", extra_common_param);
			sParaTemp.put("receive_name", receive_name);
			sParaTemp.put("receive_address", receive_address);
			sParaTemp.put("receive_zip", receive_zip);
			sParaTemp.put("receive_phone", receive_phone);
			sParaTemp.put("receive_mobile", receive_mobile);

			result = AlipayService.create_partner_trade_by_buyer(config, sParaTemp);
		}
		if (interfaceType == 2) {
			String out_trade_no = "";
			if (type.equals("goods")) {
				out_trade_no = of.getId().toString();
			}
			if (type.equals("cash")) {
				out_trade_no = obj.getId().toString();
			}
			if (type.equals("gold")) {
				out_trade_no = gold.getId().toString();
			}
			if (type.equals("integral")) {
				out_trade_no = ig_order.getId().toString();
			}

			String subject = "";
			if (type.equals("goods")) {
				//subject = "甜园云家-"+of.getStore().getStore_name();
				subject = "甜园云家-"+this.storeService.selectById(of.getStore_id()).getStore_name();
			}
			if (type.equals("cash")) {
				subject = obj.getPd_sn();
			}
			if (type.equals("gold")) {
				subject = gold.getGold_sn();
			}
			if (type.equals("integral")) {
				subject = ig_order.getIgo_order_sn();
			}

			String body = type;
			//订单描述 显示此订单所有的商品名称+goods ,goods做前台业务判断
//			StringBuffer bodys = new StringBuffer();
//			List<GoodsCart> goodsCart=of.getGcs();
//			for(GoodsCart gc : goodsCart){
//				bodys.append(gc.getGoods().getGoods_name()+",");
//			}
//			bodys.append(body);
			String total_fee = "";
			if (type.equals("goods")) {
				total_fee = CommUtil.null2String(of.getTotalPrice());
			}
			if (type.equals("cash")) {
				total_fee = CommUtil.null2String(obj.getPd_amount());
			}
			if (type.equals("gold")) {
				total_fee = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
			}
			if (type.equals("integral")) {
				total_fee = CommUtil.null2String(ig_order.getIgo_trans_fee());
			}

			String price = String.valueOf(total_fee);

			String logistics_fee = "0.00";

			String logistics_type = "EXPRESS";

			String logistics_payment = "SELLER_PAY";

			String quantity = "1";

			String extra_common_param = "";

			String receive_name = "";
			String receive_address = "";
			String receive_zip = "";
			String receive_phone = "";
			String receive_mobile = "";

			String show_url = "";

			Map sParaTemp = new HashMap();
			sParaTemp.put("payment_type", "1");
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("subject", subject);
			//sParaTemp.put("body", bodys.toString());
			sParaTemp.put("body", "支付宝pc支付");
			sParaTemp.put("price", price);
			sParaTemp.put("logistics_fee", logistics_fee);
			sParaTemp.put("logistics_type", logistics_type);
			sParaTemp.put("logistics_payment", logistics_payment);
			sParaTemp.put("quantity", quantity);
			sParaTemp.put("extra_common_param", extra_common_param);
			sParaTemp.put("receive_name", receive_name);
			sParaTemp.put("receive_address", receive_address);
			sParaTemp.put("receive_zip", receive_zip);
			sParaTemp.put("receive_phone", receive_phone);
			sParaTemp.put("receive_mobile", receive_mobile);

			result = AlipayService.trade_create_by_buyer(config, sParaTemp);
		}
		return result;
	}

	// yikatong
	@SuppressWarnings("all")
	public String genericYikatong(String url, String payment_id, String type, String id) throws Exception {
		String result = "";
		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(id));
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		YiKatongConfig config = new YiKatongConfig();
//		Map params = new HashMap();
//		params.put("type", "admin");
//		params.put("mark", "yikatong");
//		List payments = this.paymentService.query("select obj from Payment obj where obj.type=:type and obj.mark=:mark",
//				params, -1, -1);
		Payment sPayment=new Payment();
		sPayment.setType("admin");
		sPayment.setMark("yikatong");
		List<Payment> payments =this.paymentService.selectList(sPayment);
		Payment shop_payment = new Payment();
		if (payments.size() > 0) {
			shop_payment = (Payment) payments.get(0);
		}
		config.setNotify_url(url + "/yikatong_notify.htm");
		config.setReturn_url(url + "/yikatong_return.htm");

		SysConfig sys_config = this.configService.getSysConfig();
		sys_config.setAlipay_fenrun(1);
		if (sys_config.getAlipay_fenrun() == 1) {

			config.setKey(shop_payment.getSafeKey());
			config.setPartner(shop_payment.getPartner());
			config.setSeller_email(shop_payment.getSeller_email()); // ~~~~~~~~~yikatong
			Address addres=addressService.selectById(of.getAddr_id());
			// 支付类型
			String payment_type = "1";
			SortedMap<String, String> sParaTemp1 = new TreeMap<String, String>();
			YiKaTongPay yktp = new YiKaTongPay();
			yktp.setID(null);
			yktp.setShopIds(String.valueOf(this.storeService.selectById(of.getStore_id()).getId()));// 商铺号
//			yktp.setMerchantId(of.getPayment().getSeller_email());// 商户号
			yktp.setMerchantId(this.paymentService.selectById(of.getPayment_id()).getPartner());// 商户号
//			yktp.setOrderNos(String.valueOf(of.getId()));// 订单号
			yktp.setOrderNos(String.valueOf(of.getOrder_id()));// 订单号
			
			yktp.setWIDsubject(addres.getTrueName() + "的订单");// 订单名
			yktp.setOrderAmounts((long) ((of.getTotalPrice().doubleValue()) * 100));// 付款金额
			yktp.setOrderAmount((long) ((of.getTotalPrice().doubleValue()) * 100));// 付款合计金额
			String date1 = new SimpleDateFormat("yyyyMMddhhmmss").format(of.getAddTime());
			yktp.setOrderDatetime(date1);// 订单日期
//			yktp.setPayerTelephone(of.getAddr().getTelephone());// 客户电话
			
			String telephone = addres.getTelephone();
			String mobile = addres.getMobile();
			telephone = (telephone == null || telephone.isEmpty()) ? "" : telephone;
			mobile = (mobile == null || mobile.isEmpty()) ? telephone : mobile;
			yktp.setPayerTelephone(mobile);// 客户电话
			
			yktp.setOrderCurrency("156");
			// sParaTemp.put("service", "create_direct_pay_by_user");
			// 1
			// sParaTemp1.put("shopIds", yktp.getShopIds());
			sParaTemp1.put("shopIds", new String(yktp.getMerchantId().getBytes("ISO-8859-1"), "UTF-8"));
			// 2
			// sParaTemp1.put("receiveUrl", url);
			sParaTemp1.put("receiveUrl", new String((url + "/yikatong_return.htm").getBytes("ISO-8859-1"), "UTF-8"));
			// 3
			// sParaTemp1.put("orderNos", yktp.getOrderNos());
			sParaTemp1.put("orderNos", new String(yktp.getOrderNos().getBytes("ISO-8859-1"), "UTF-8"));
			// 4
			// sParaTemp1.put("orderAmounts",
			// String.valueOf(yktp.getOrderAmounts()));
			sParaTemp1.put("orderAmounts",
					new String(String.valueOf(yktp.getOrderAmounts()).getBytes("ISO-8859-1"), "UTF-8"));
			// 5
			// sParaTemp1.put("payerTelephone", yktp.getPayerTelephone());
			sParaTemp1.put("payerTelephone", new String(yktp.getPayerTelephone().getBytes("ISO-8859-1"), "UTF-8"));
			// 6
			// sParaTemp1.put("orderCurrency", yktp.getOrderCurrency());
			sParaTemp1.put("orderCurrency", new String(yktp.getOrderCurrency().getBytes("ISO-8859-1"), "UTF-8"));

			// 7
			// sParaTemp1.put("orderDatetime", yktp.getOrderDatetime());
			sParaTemp1.put("orderDatetime", new String(yktp.getOrderDatetime().getBytes("ISO-8859-1"), "UTF-8"));

			// 8
			// sParaTemp1.put("orderAmount",
			// String.valueOf(yktp.getOrderAmount()));
			sParaTemp1.put("orderAmount",
					new String(String.valueOf(yktp.getOrderAmount()).getBytes("ISO-8859-1"), "UTF-8"));
			sParaTemp1.put("merchantId", new String(yktp.getMerchantId().getBytes("ISO-8859-1"), "UTF-8"));

			result = YiKatongService.create_direct_pay_by_user(config, sParaTemp1);
		}
		return result;
	}

	// 3800一卡通
	@SuppressWarnings("all")
	public String genericKa3800ka(String url, String payment_id, String type, String id) throws Exception {
		String result = "";
		OrderForm of = this.orderFormService.selectById(CommUtil.null2Long(id));
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		YiKatongConfig config = new YiKatongConfig();
//		Map params = new HashMap();
//		params.put("type", "admin");
//		params.put("mark", "ka3800ka");
//		List payments = this.paymentService.query("select obj from Payment obj where obj.type=:type and obj.mark=:mark",
//				params, -1, -1);
		Payment sPayment=new Payment();
		sPayment.setType("admin");
		sPayment.setMark("ka3800ka");
		List<Payment> payments =this.paymentService.selectList(sPayment);
		Payment shop_payment = new Payment();
		if (payments.size() > 0) {
			shop_payment = (Payment) payments.get(0);
		}
		config.setNotify_url(url + "/ka3800ka_notify.htm");
		config.setReturn_url(url + "/ka3800ka_return.htm");

		SysConfig sys_config = this.configService.getSysConfig();
		sys_config.setAlipay_fenrun(1);
		if (sys_config.getAlipay_fenrun() == 1) {

			config.setKey(shop_payment.getSafeKey());
			config.setPartner(shop_payment.getPartner());
			config.setSeller_email(shop_payment.getSeller_email()); // ~~~~~~~~~yikatong
			Address adress=addressService.selectById(of.getAddr_id());
			// 支付类型
			String payment_type = "1";
			SortedMap<String, String> sParaTemp1 = new TreeMap<String, String>();
			YiKaTongPay yktp = new YiKaTongPay();
			yktp.setID(null);
			yktp.setShopIds(String.valueOf(this.storeService.selectById(of.getStore_id()).getId()));// 商铺号
//			yktp.setMerchantId(of.getPayment().getSeller_email());// 商户号
			yktp.setMerchantId(this.paymentService.selectById(of.getPayment_id()).getPartner());// 商户号
//			yktp.setOrderNos(String.valueOf(of.getId()));// 订单号
			yktp.setOrderNos(String.valueOf(of.getOrder_id()));// 订单号
			
			yktp.setWIDsubject(adress.getTrueName() + "的订单");// 订单名
			yktp.setOrderAmounts((long) ((of.getTotalPrice().doubleValue()) * 100));// 付款金额
			yktp.setOrderAmount((long) ((of.getTotalPrice().doubleValue()) * 100));// 付款合计金额
			String date1 = new SimpleDateFormat("yyyyMMddhhmmss").format(of.getAddTime());
			yktp.setOrderDatetime(date1);// 订单日期
//			yktp.setPayerTelephone(of.getAddr().getTelephone());// 客户电话
			
			String telephone = adress.getTelephone();
			String mobile = adress.getMobile();
			telephone = (telephone == null || telephone.isEmpty()) ? "" : telephone;
			mobile = (mobile == null || mobile.isEmpty()) ? telephone : mobile;
			yktp.setPayerTelephone(mobile);// 客户电话
			
			yktp.setOrderCurrency("156");
			// sParaTemp.put("service", "create_direct_pay_by_user");
			// 1
			// sParaTemp1.put("shopIds", yktp.getShopIds());
			sParaTemp1.put("shopIds", new String(yktp.getMerchantId().getBytes("ISO-8859-1"), "UTF-8"));
			// 2
			// sParaTemp1.put("receiveUrl", url);
			sParaTemp1.put("receiveUrl", new String((url + "/ka3800ka_return.htm").getBytes("ISO-8859-1"), "UTF-8"));
			// 3
			// sParaTemp1.put("orderNos", yktp.getOrderNos());
			sParaTemp1.put("orderNos", new String(yktp.getOrderNos().getBytes("ISO-8859-1"), "UTF-8"));
			// 4
			// sParaTemp1.put("orderAmounts",
			// String.valueOf(yktp.getOrderAmounts()));
			sParaTemp1.put("orderAmounts",
					new String(String.valueOf(yktp.getOrderAmounts()).getBytes("ISO-8859-1"), "UTF-8"));
			// 5
			// sParaTemp1.put("payerTelephone", yktp.getPayerTelephone());
			sParaTemp1.put("payerTelephone", new String(yktp.getPayerTelephone().getBytes("ISO-8859-1"), "UTF-8"));
			// 6
			// sParaTemp1.put("orderCurrency", yktp.getOrderCurrency());
			sParaTemp1.put("orderCurrency", new String(yktp.getOrderCurrency().getBytes("ISO-8859-1"), "UTF-8"));

			// 7
			// sParaTemp1.put("orderDatetime", yktp.getOrderDatetime());
			sParaTemp1.put("orderDatetime", new String(yktp.getOrderDatetime().getBytes("ISO-8859-1"), "UTF-8"));

			// 8
			// sParaTemp1.put("orderAmount",
			// String.valueOf(yktp.getOrderAmount()));
			sParaTemp1.put("orderAmount",
					new String(String.valueOf(yktp.getOrderAmount()).getBytes("ISO-8859-1"), "UTF-8"));
			sParaTemp1.put("merchantId", new String(yktp.getMerchantId().getBytes("ISO-8859-1"), "UTF-8"));
			
			result = YiKatongService.create_direct_pay_by_user_3800(config, sParaTemp1);
		}
		return result;
	}

	public String generic99Bill(String url, String payment_id, String type, String id)
			throws UnsupportedEncodingException {
		String result = "";
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		BillConfig config = new BillConfig(payment.getMerchantAcctId(), payment.getRmbKey(), payment.getPid());

		String merchantAcctId = config.getMerchantAcctId();
		String key = config.getKey();
		String inputCharset = "1";
		String bgUrl = url + "/bill_return.htm";
		String version = "v2.0";
		String language = "1";
		String signType = "1";

		String payerName = SecurityUserHolder.getCurrentUser().getUserName();

		String payerContactType = "1";

		String payerContact = "";

		String orderId = "";
		if (type.equals("goods")) {
			orderId = of.getOrder_id();
		}
		if (type.equals("cash")) {
			orderId = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			orderId = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			orderId = ig_order.getIgo_order_sn();
		}

		String orderAmount = "";
		if (type.equals("goods")) {
			orderAmount = String.valueOf((int) Math.floor(CommUtil.null2Double(of.getTotalPrice()) * 100.0D));
		}
		if (type.equals("cash")) {
			orderAmount = String.valueOf((int) Math.floor(CommUtil.null2Double(obj.getPd_amount()) * 100.0D));
		}
		if (type.equals("gold")) {
			orderAmount = String
					.valueOf((int) Math.floor(CommUtil.null2Double(Integer.valueOf(gold.getGold_money())) * 100.0D));
		}
		if (type.equals("integral")) {
			orderAmount = String.valueOf((int) Math.floor(CommUtil.null2Double(ig_order.getIgo_trans_fee()) * 100.0D));
		}

		String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		String productName = "";
		if (type.equals("goods")) {
			productName = of.getOrder_id();
		}
		if (type.equals("cash")) {
			productName = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			productName = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			productName = ig_order.getIgo_order_sn();
		}

		String productNum = "1";

		String productId = "";

		String productDesc = "";

		String ext1 = "";
		if (type.equals("goods")) {
			ext1 = of.getId().toString();
		}
		if (type.equals("cash")) {
			ext1 = obj.getId().toString();
		}
		if (type.equals("gold")) {
			ext1 = gold.getId().toString();
		}
		if (type.equals("integral")) {
			ext1 = ig_order.getId().toString();
		}

		String ext2 = type;

		String payType = "00";

		String redoFlag = "0";

		String pid = "";
		if (config.getPid() != null) {
			pid = config.getPid();
		}

		String signMsgVal = "";
		signMsgVal = BillCore.appendParam(signMsgVal, "inputCharset", inputCharset);
		signMsgVal = BillCore.appendParam(signMsgVal, "bgUrl", bgUrl);
		signMsgVal = BillCore.appendParam(signMsgVal, "version", version);
		signMsgVal = BillCore.appendParam(signMsgVal, "language", language);
		signMsgVal = BillCore.appendParam(signMsgVal, "signType", signType);
		signMsgVal = BillCore.appendParam(signMsgVal, "merchantAcctId", merchantAcctId);
		signMsgVal = BillCore.appendParam(signMsgVal, "payerName", payerName);
		signMsgVal = BillCore.appendParam(signMsgVal, "payerContactType", payerContactType);
		signMsgVal = BillCore.appendParam(signMsgVal, "payerContact", payerContact);
		signMsgVal = BillCore.appendParam(signMsgVal, "orderId", orderId);
		signMsgVal = BillCore.appendParam(signMsgVal, "orderAmount", orderAmount);
		signMsgVal = BillCore.appendParam(signMsgVal, "orderTime", orderTime);
		signMsgVal = BillCore.appendParam(signMsgVal, "productName", productName);
		signMsgVal = BillCore.appendParam(signMsgVal, "productNum", productNum);
		signMsgVal = BillCore.appendParam(signMsgVal, "productId", productId);
		signMsgVal = BillCore.appendParam(signMsgVal, "productDesc", productDesc);
		signMsgVal = BillCore.appendParam(signMsgVal, "ext1", ext1);
		signMsgVal = BillCore.appendParam(signMsgVal, "ext2", ext2);
		signMsgVal = BillCore.appendParam(signMsgVal, "payType", payType);
		signMsgVal = BillCore.appendParam(signMsgVal, "redoFlag", redoFlag);
		signMsgVal = BillCore.appendParam(signMsgVal, "pid", pid);
		signMsgVal = BillCore.appendParam(signMsgVal, "key", key);

		String signMsg = MD5Util.md5Hex(signMsgVal.getBytes("UTF-8")).toUpperCase();

		Map sParaTemp = new HashMap();
		sParaTemp.put("inputCharset", inputCharset);
		sParaTemp.put("bgUrl", bgUrl);
		sParaTemp.put("version", version);
		sParaTemp.put("language", language);
		sParaTemp.put("signType", signType);
		sParaTemp.put("signMsg", signMsg);
		sParaTemp.put("merchantAcctId", merchantAcctId);
		sParaTemp.put("payerName", payerName);
		sParaTemp.put("payerContactType", payerContactType);
		sParaTemp.put("payerContact", payerContact);
		sParaTemp.put("orderId", orderId);
		sParaTemp.put("orderAmount", orderAmount);
		sParaTemp.put("orderTime", orderTime);
		sParaTemp.put("productName", productName);
		sParaTemp.put("productNum", productNum);
		sParaTemp.put("productId", productId);
		sParaTemp.put("productDesc", productDesc);
		sParaTemp.put("ext1", ext1);
		sParaTemp.put("ext2", ext2);
		sParaTemp.put("payType", payType);
		sParaTemp.put("redoFlag", redoFlag);
		sParaTemp.put("pid", pid);
		result = BillService.buildForm(config, sParaTemp, "post", "确定");
		return result;
	}

	public String genericChinaBank(String url, String payment_id, String type, String id) {
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		List list = new ArrayList();
		String v_mid = payment.getChinabank_account();
		list.add(new SysMap("v_mid", v_mid));
		String key = payment.getChinabank_key();
		list.add(new SysMap("key", key));
		String v_url = url + "/chinabank_return.htm";
		list.add(new SysMap("v_url", v_url));
		String v_oid = "";
		if (type.equals("goods")) {
			v_oid = of.getOrder_id();
		}
		if (type.equals("cash")) {
			v_oid = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			v_oid = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			v_oid = ig_order.getIgo_order_sn();
		}
		list.add(new SysMap("v_oid", v_oid));
		String v_amount = "";
		if (type.equals("goods")) {
			v_amount = CommUtil.null2String(of.getTotalPrice());
		}
		if (type.equals("cash")) {
			v_amount = CommUtil.null2String(obj.getPd_amount());
		}
		if (type.equals("gold")) {
			v_amount = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
		}
		if (type.equals("integral")) {
			v_amount = CommUtil.null2String(ig_order.getIgo_trans_fee());
		}
		list.add(new SysMap("v_amount", v_amount));
		String v_moneytype = "CNY";
		list.add(new SysMap("v_moneytype", v_moneytype));
		String temp = v_amount + v_moneytype + v_oid + v_mid + v_url + key;
		String v_md5info = Md5Encrypt.md5(temp).toUpperCase();
		list.add(new SysMap("v_md5info", v_md5info));

		String v_rcvname = "";
		String v_rcvaddr = "";
		String v_rcvtel = "";
		String v_rcvpost = "";
		String v_rcvemail = "";
		String v_rcvmobile = "";
		String remark1 = "";
		if (type.equals("goods")) {
			remark1 = of.getId().toString();
		}
		if (type.equals("cash")) {
			remark1 = obj.getId().toString();
		}
		if (type.equals("gold")) {
			remark1 = gold.getId().toString();
		}
		if (type.equals("integral")) {
			remark1 = ig_order.getId().toString();
		}
		list.add(new SysMap("remark1", remark1));
		String remark2 = type;
		list.add(new SysMap("remark2", remark2));
		String ret = ChinaBankSubmit.buildForm(list);
		return ret;
	}

	public String genericPaypal(String url, String payment_id, String type, String id) {
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		List sms = new ArrayList();
		String business = payment.getPaypal_userId();
		sms.add(new SysMap("business", business));
		String return_url = url + "/paypal_return.htm";
		String notify_url = url + "/paypal_return.htm";
		sms.add(new SysMap("return", return_url));
		String item_name = "";
		if (type.equals("goods")) {
			item_name = of.getOrder_id();
		}
		if (type.equals("cash")) {
			item_name = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			item_name = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			item_name = ig_order.getIgo_order_sn();
		}
		sms.add(new SysMap("item_name", item_name));
		String amount = "";
		String item_number = "";
		if (type.equals("goods")) {
			amount = CommUtil.null2String(of.getTotalPrice());
			item_number = of.getOrder_id();
		}
		if (type.equals("cash")) {
			amount = CommUtil.null2String(obj.getPd_amount());
			item_number = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			amount = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
			item_number = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			amount = CommUtil.null2String(ig_order.getIgo_trans_fee());
			item_number = ig_order.getIgo_order_sn();
		}
		sms.add(new SysMap("amount", amount));
		sms.add(new SysMap("notify_url", notify_url));
		sms.add(new SysMap("cmd", "_xclick"));
		sms.add(new SysMap("currency_code", payment.getCurrency_code()));
		sms.add(new SysMap("item_number", item_number));

		String custom = "";
		if (type.equals("goods")) {
			custom = of.getId().toString();
		}
		if (type.equals("cash")) {
			custom = obj.getId().toString();
		}
		if (type.equals("gold")) {
			custom = gold.getId().toString();
		}
		if (type.equals("integral")) {
			custom = ig_order.getId().toString();
		}
		custom = custom + "," + type;
		sms.add(new SysMap("custom", custom));
		String ret = PaypalTools.buildForm(sms);
		return ret;
	}

	public String genericAlipayWap(String url, String payment_id, String type, String id) throws Exception {
		String result = "";
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		int interfaceType = payment.getInterfaceType();
		AlipayConfig config = new AlipayConfig();
//		Map params = new HashMap();
//		params.put("type", "admin");
//		params.put("mark", "alipay_wap");
//		List payments = this.paymentService.query("select obj from Payment obj where obj.type=:type and obj.mark=:mark",
//				params, -1, -1);
		Payment sPayment=new Payment();
		sPayment.setType("admin");
		sPayment.setMark("alipay_wap");
		List<Payment> payments =this.paymentService.selectList(sPayment);
		Payment shop_payment = new Payment();
		if (payments.size() > 0) {
			shop_payment = (Payment) payments.get(0);
		}
		if ((!CommUtil.null2String(payment.getSafeKey()).equals(""))
				&& (!CommUtil.null2String(payment.getPartner()).equals(""))) {
			config.setKey(payment.getSafeKey());
			config.setPartner(payment.getPartner());
		} else {
			config.setKey(shop_payment.getSafeKey());
			config.setPartner(shop_payment.getPartner());
		}
		config.setSeller_email(payment.getSeller_email());

		String format = "xml";

		String v = "2.0";

		String req_id = UtilDate.getOrderNum();

		String notify_url = url + "/weixin/alipay_notify.htm";

		String call_back_url = url + "/weixin/alipay_return.htm";

		String merchant_url = url + "/weixin/index.htm";

		String seller_email = payment.getSeller_email();

		String out_trade_no = "";

		if (type.equals("goods")) {
			out_trade_no = of.getId().toString();
		}
		if (type.equals("cash")) {
			out_trade_no = obj.getId().toString();
		}
		if (type.equals("gold")) {
			out_trade_no = gold.getId().toString();
		}
		if (type.equals("integral")) {
			out_trade_no = ig_order.getId().toString();
		}

		String subject = "goods";
		if (type.equals("goods")) {
			subject = of.getOrder_id();
		}
		if (type.equals("cash")) {
			subject = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			subject = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			subject = ig_order.getIgo_order_sn();
		}

		String total_fee = "";
		if (type.equals("goods")) {
			total_fee = CommUtil.null2String(of.getTotalPrice());
		}
		if (type.equals("cash")) {
			total_fee = CommUtil.null2String(obj.getPd_amount());
		}
		if (type.equals("gold")) {
			total_fee = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
		}
		if (type.equals("integral")) {
			total_fee = CommUtil.null2String(ig_order.getIgo_trans_fee());
		}

		String req_dataToken = "<direct_trade_create_req><notify_url>" + notify_url + "</notify_url><call_back_url>"
				+ call_back_url + "</call_back_url><seller_account_name>" + seller_email
				+ "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>" + subject
				+ "</subject><total_fee>" + total_fee + "</total_fee><merchant_url>" + merchant_url
				+ "</merchant_url><pay_body>" + type + "</pay_body></direct_trade_create_req>";

		Map sParaTempToken = new HashMap();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", config.getPartner());
		sParaTempToken.put("_input_charset", config.getInput_charset());
		sParaTempToken.put("sec_id", config.getSign_type());
		sParaTempToken.put("format", format);
		sParaTempToken.put("v", v);
		sParaTempToken.put("req_id", req_id);
		sParaTempToken.put("req_data", req_dataToken);

		String sHtmlTextToken = AlipaySubmit.buildRequest(config, "wap", sParaTempToken, "", "");

		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, config.getInput_charset());

		String request_token = AlipaySubmit.getRequestToken(config, sHtmlTextToken);

		String req_data = "<auth_and_execute_req><request_token>" + request_token
				+ "</request_token></auth_and_execute_req>";

		Map sParaTemp = new HashMap();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", config.getPartner());
		sParaTemp.put("_input_charset", config.getInput_charset());
		sParaTemp.put("sec_id", config.getSign_type());
		sParaTemp.put("format", format);
		sParaTemp.put("v", v);
		sParaTemp.put("req_data", req_data);

		String WAP_ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";
		result = AlipaySubmit.buildForm(config, sParaTemp, WAP_ALIPAY_GATEWAY_NEW, "get", "确认");

		return result;
	}

	public String generic99BillWap(String url, String payment_id, String type, String id)
			throws UnsupportedEncodingException {
		String result = "";
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		BillConfig config = new BillConfig(payment.getMerchantAcctId(), payment.getRmbKey(), payment.getPid());

		String merchantAcctId = config.getMerchantAcctId();
		String key = config.getKey();
		String inputCharset = "1";
		String bgUrl = url + "/weixin/bill_return.htm";
		String version = "v2.0";
		String language = "1";
		String signType = "1";

		String payerName = SecurityUserHolder.getCurrentUser().getUserName();

		String payerContactType = "1";

		String payerContact = "";

		String orderId = "";
		if (type.equals("goods")) {
			orderId = of.getOrder_id();
		}
		if (type.equals("cash")) {
			orderId = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			orderId = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			orderId = ig_order.getIgo_order_sn();
		}

		String orderAmount = "";
		if (type.equals("goods")) {
			orderAmount = String.valueOf((int) Math.floor(CommUtil.null2Double(of.getTotalPrice()) * 100.0D));
		}
		if (type.equals("cash")) {
			orderAmount = String.valueOf((int) Math.floor(CommUtil.null2Double(obj.getPd_amount()) * 100.0D));
		}
		if (type.equals("gold")) {
			orderAmount = String
					.valueOf((int) Math.floor(CommUtil.null2Double(Integer.valueOf(gold.getGold_money())) * 100.0D));
		}
		if (type.equals("integral")) {
			orderAmount = String.valueOf((int) Math.floor(CommUtil.null2Double(ig_order.getIgo_trans_fee()) * 100.0D));
		}

		String orderTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		String productName = "";
		if (type.equals("goods")) {
			productName = of.getOrder_id();
		}
		if (type.equals("cash")) {
			productName = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			productName = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			productName = ig_order.getIgo_order_sn();
		}

		String productNum = "1";

		String productId = "";

		String productDesc = "";

		String ext1 = "";
		if (type.equals("goods")) {
			ext1 = of.getId().toString();
		}
		if (type.equals("cash")) {
			ext1 = obj.getId().toString();
		}
		if (type.equals("gold")) {
			ext1 = gold.getId().toString();
		}
		if (type.equals("integral")) {
			ext1 = ig_order.getId().toString();
		}

		String ext2 = type;

		String payType = "00";

		String redoFlag = "0";

		String pid = "";
		if (config.getPid() != null) {
			pid = config.getPid();
		}

		String signMsgVal = "";
		signMsgVal = BillCore.appendParam(signMsgVal, "inputCharset", inputCharset);
		signMsgVal = BillCore.appendParam(signMsgVal, "bgUrl", bgUrl);
		signMsgVal = BillCore.appendParam(signMsgVal, "version", version);
		signMsgVal = BillCore.appendParam(signMsgVal, "language", language);
		signMsgVal = BillCore.appendParam(signMsgVal, "signType", signType);
		signMsgVal = BillCore.appendParam(signMsgVal, "merchantAcctId", merchantAcctId);
		signMsgVal = BillCore.appendParam(signMsgVal, "payerName", payerName);
		signMsgVal = BillCore.appendParam(signMsgVal, "payerContactType", payerContactType);
		signMsgVal = BillCore.appendParam(signMsgVal, "payerContact", payerContact);
		signMsgVal = BillCore.appendParam(signMsgVal, "orderId", orderId);
		signMsgVal = BillCore.appendParam(signMsgVal, "orderAmount", orderAmount);
		signMsgVal = BillCore.appendParam(signMsgVal, "orderTime", orderTime);
		signMsgVal = BillCore.appendParam(signMsgVal, "productName", productName);
		signMsgVal = BillCore.appendParam(signMsgVal, "productNum", productNum);
		signMsgVal = BillCore.appendParam(signMsgVal, "productId", productId);
		signMsgVal = BillCore.appendParam(signMsgVal, "productDesc", productDesc);
		signMsgVal = BillCore.appendParam(signMsgVal, "ext1", ext1);
		signMsgVal = BillCore.appendParam(signMsgVal, "ext2", ext2);
		signMsgVal = BillCore.appendParam(signMsgVal, "payType", payType);
		signMsgVal = BillCore.appendParam(signMsgVal, "redoFlag", redoFlag);
		signMsgVal = BillCore.appendParam(signMsgVal, "pid", pid);
		signMsgVal = BillCore.appendParam(signMsgVal, "key", key);

		String signMsg = MD5Util.md5Hex(signMsgVal.getBytes("UTF-8")).toUpperCase();

		Map sParaTemp = new HashMap();
		sParaTemp.put("inputCharset", inputCharset);
		sParaTemp.put("bgUrl", bgUrl);
		sParaTemp.put("version", version);
		sParaTemp.put("language", language);
		sParaTemp.put("signType", signType);
		sParaTemp.put("signMsg", signMsg);
		sParaTemp.put("merchantAcctId", merchantAcctId);
		sParaTemp.put("payerName", payerName);
		sParaTemp.put("payerContactType", payerContactType);
		sParaTemp.put("payerContact", payerContact);
		sParaTemp.put("orderId", orderId);
		sParaTemp.put("orderAmount", orderAmount);
		sParaTemp.put("orderTime", orderTime);
		sParaTemp.put("productName", productName);
		sParaTemp.put("productNum", productNum);
		sParaTemp.put("productId", productId);
		sParaTemp.put("productDesc", productDesc);
		sParaTemp.put("ext1", ext1);
		sParaTemp.put("ext2", ext2);
		sParaTemp.put("payType", payType);
		sParaTemp.put("redoFlag", redoFlag);
		sParaTemp.put("pid", pid);
		result = BillService.buildForm(config, sParaTemp, "post", "确定");
		return result;
	}

	public String genericChinaBankWap(String url, String payment_id, String type, String id) {
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		List list = new ArrayList();
		String v_mid = payment.getChinabank_account();
		list.add(new SysMap("v_mid", v_mid));
		String key = payment.getChinabank_key();
		list.add(new SysMap("key", key));
		String v_url = url + "/weixin/chinabank_return.htm";
		list.add(new SysMap("v_url", v_url));
		String v_oid = "";
		if (type.equals("goods")) {
			v_oid = of.getOrder_id();
		}
		if (type.equals("cash")) {
			v_oid = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			v_oid = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			v_oid = ig_order.getIgo_order_sn();
		}
		list.add(new SysMap("v_oid", v_oid));
		String v_amount = "";
		if (type.equals("goods")) {
			v_amount = CommUtil.null2String(of.getTotalPrice());
		}
		if (type.equals("cash")) {
			v_amount = CommUtil.null2String(obj.getPd_amount());
		}
		if (type.equals("gold")) {
			v_amount = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
		}
		if (type.equals("integral")) {
			v_amount = CommUtil.null2String(ig_order.getIgo_trans_fee());
		}
		list.add(new SysMap("v_amount", v_amount));
		String v_moneytype = "CNY";
		list.add(new SysMap("v_moneytype", v_moneytype));
		String temp = v_amount + v_moneytype + v_oid + v_mid + v_url + key;
		String v_md5info = Md5Encrypt.md5(temp).toUpperCase();
		list.add(new SysMap("v_md5info", v_md5info));

		String v_rcvname = "";
		String v_rcvaddr = "";
		String v_rcvtel = "";
		String v_rcvpost = "";
		String v_rcvemail = "";
		String v_rcvmobile = "";
		String remark1 = "";
		if (type.equals("goods")) {
			remark1 = of.getId().toString();
		}
		if (type.equals("cash")) {
			remark1 = obj.getId().toString();
		}
		if (type.equals("gold")) {
			remark1 = gold.getId().toString();
		}
		if (type.equals("integral")) {
			remark1 = ig_order.getId().toString();
		}
		list.add(new SysMap("remark1", remark1));
		String remark2 = type;
		list.add(new SysMap("remark2", remark2));
		String ret = ChinaBankSubmit.buildForm(list);
		return ret;
	}

	public String genericPaypalWap(String url, String payment_id, String type, String id) {
		OrderForm of = null;
		Predeposit obj = null;
		GoldRecord gold = null;
		IntegralGoodsOrder ig_order = null;
		if (type.equals("goods")) {
			of = this.orderFormService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("cash")) {
			obj = this.predepositService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("gold")) {
			gold = this.goldRecordService.selectById(CommUtil.null2Long(id));
		}
		if (type.equals("integral")) {
			ig_order = this.integralGoodsOrderService.selectById(CommUtil.null2Long(id));
		}
		Payment payment = this.paymentService.selectById(CommUtil.null2Long(payment_id));
		if (payment == null)
			payment = new Payment();
		List sms = new ArrayList();
		String business = payment.getPaypal_userId();
		sms.add(new SysMap("business", business));
		String return_url = url + "/weixin/paypal_return.htm";
		String notify_url = url + "/weixin/paypal_return.htm";
		sms.add(new SysMap("return", return_url));
		String item_name = "";
		if (type.equals("goods")) {
			item_name = of.getOrder_id();
		}
		if (type.equals("cash")) {
			item_name = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			item_name = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			item_name = ig_order.getIgo_order_sn();
		}
		sms.add(new SysMap("item_name", item_name));
		String amount = "";
		String item_number = "";
		if (type.equals("goods")) {
			amount = CommUtil.null2String(of.getTotalPrice());
			item_number = of.getOrder_id();
		}
		if (type.equals("cash")) {
			amount = CommUtil.null2String(obj.getPd_amount());
			item_number = obj.getPd_sn();
		}
		if (type.equals("gold")) {
			amount = CommUtil.null2String(Integer.valueOf(gold.getGold_money()));
			item_number = gold.getGold_sn();
		}
		if (type.equals("integral")) {
			amount = CommUtil.null2String(ig_order.getIgo_trans_fee());
			item_number = ig_order.getIgo_order_sn();
		}
		sms.add(new SysMap("amount", amount));
		sms.add(new SysMap("notify_url", notify_url));
		sms.add(new SysMap("cmd", "_xclick"));
		sms.add(new SysMap("currency_code", payment.getCurrency_code()));
		sms.add(new SysMap("item_number", item_number));

		String custom = "";
		if (type.equals("goods")) {
			custom = of.getId().toString();
		}
		if (type.equals("cash")) {
			custom = obj.getId().toString();
		}
		if (type.equals("gold")) {
			custom = gold.getId().toString();
		}
		if (type.equals("integral")) {
			custom = ig_order.getId().toString();
		}
		custom = custom + "," + type;
		sms.add(new SysMap("custom", custom));
		String ret = PaypalTools.buildForm(sms);
		return ret;
	}
}
