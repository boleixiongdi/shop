package com.rt.shop.pay.yikatong.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.rt.shop.pay.yikatong.config.YiKatongConfig;
import com.rt.shop.pay.yikatong.util.YiKatongSubmit;

public class YiKatongService {
	private static final String HDGKPAY_GATEWAY_NEW = "http://114.55.40.31:8088/payment/payment/onecardpay.do?";

	public static String create_direct_pay_by_user(YiKatongConfig config, SortedMap<String, String> sParaTemp) {
		// sParaTemp.put("service", "create_direct_pay_by_user");
		// sParaTemp.put("partner", config.getPartner());
		// sParaTemp.put("return_url", config.getReturn_url());
		// sParaTemp.put("notify_url", config.getNotify_url());
		// sParaTemp.put("seller_email", config.getSeller_email1());
		// sParaTemp.put("_input_charset", config.getInput_charset());

		String strButtonName = "确认";
		// return AlipaySubmit.buildForm(config, sParaTemp,
		// "http://123.56.179.117:8088/payment/payment/onecardpay.do?",
		// "post", strButtonName);
		return YiKatongSubmit.buildRequest(config,sParaTemp, "post", strButtonName);

	}
	
	public static String create_direct_pay_by_user_3800(YiKatongConfig config, SortedMap<String, String> sParaTemp) {
		// sParaTemp.put("service", "create_direct_pay_by_user");
		// sParaTemp.put("partner", config.getPartner());
		// sParaTemp.put("return_url", config.getReturn_url());
		// sParaTemp.put("notify_url", config.getNotify_url());
		// sParaTemp.put("seller_email", config.getSeller_email1());
		// sParaTemp.put("_input_charset", config.getInput_charset());

		String strButtonName = "确认";
		// return AlipaySubmit.buildForm(config, sParaTemp,
		// "http://123.56.179.117:8088/payment/payment/onecardpay.do?",
		// "post", strButtonName);
		return YiKatongSubmit.buildRequest_3800(config,sParaTemp, "post", strButtonName);

	}

	public static String query_timestamp(YiKatongConfig config)
			throws MalformedURLException, DocumentException, IOException {
		
		//构造访问query_timestamp接口的URL串
        String strUrl = HDGKPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + new YiKatongConfig().getPartner() + "&_input_charset" +YiKatongConfig.input_charset;
        StringBuffer result = new StringBuffer();

        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());

        List<Node> nodeList = doc.selectNodes("//yikatong/*");

        for (Node node : nodeList) {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }

        return result.toString();
//		String strUrl = "http://114.55.40.31:8088/payment/payment/onecardpay.do??service=query_timestamp&partner="
//				+ config.getPartner();
//		StringBuffer result = new StringBuffer();
//
//		SAXReader reader = new SAXReader();
//		Document doc = reader.read(new URL(strUrl).openStream());
//
//		List<Node> nodeList = doc.selectNodes("//yikatong/*");
//
//		for (Node node : nodeList) {
//			if ((!node.getName().equals("is_success")) || (!node.getText().equals("T")))
//				continue;
//			List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
//			for (Node node1 : nodeList1) {
//				result.append(node1.getText());
//			}
//
//		}
//
//		return result.toString();
	}

	public static String create_partner_trade_by_buyer(YiKatongConfig config, SortedMap<String, String> sParaTemp) {
		sParaTemp.put("service", "create_partner_trade_by_buyer");
		sParaTemp.put("partner", config.getPartner());
		sParaTemp.put("return_url", config.getReturn_url());
		sParaTemp.put("notify_url", config.getNotify_url());
		sParaTemp.put("seller_email", config.getSeller_email());
		sParaTemp.put("_input_charset", config.getInput_charset());

		String strButtonName = "确认";

		return YiKatongSubmit.buildForm(config, sParaTemp, HDGKPAY_GATEWAY_NEW,
				"get", strButtonName);
	}

	public static String trade_create_by_buyer(YiKatongConfig config, SortedMap<String, String> sParaTemp) {
		sParaTemp.put("service", "trade_create_by_buyer");
		sParaTemp.put("partner", config.getPartner());
		sParaTemp.put("return_url", config.getReturn_url());
		sParaTemp.put("notify_url", config.getNotify_url());
		sParaTemp.put("seller_email", config.getSeller_email());
		sParaTemp.put("_input_charset", config.getInput_charset());

		String strButtonName = "确认";

		return YiKatongSubmit.buildForm(config, sParaTemp, HDGKPAY_GATEWAY_NEW,
				"get", strButtonName);
	}
}
