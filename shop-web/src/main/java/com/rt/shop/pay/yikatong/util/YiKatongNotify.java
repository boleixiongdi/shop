package com.rt.shop.pay.yikatong.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rt.shop.pay.yikatong.config.YiKatongConfig;
//import com.pa.util.JsonUtil;

/* *
 *类名：AlipayNotify
 *功能：支付宝通知处理类
 *详细：处理支付宝各接口通知返回
 *版本：3.3
 *日期：2012-08-17
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考

 *************************注意*************************
 *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
 */
public class YiKatongNotify {

	/**
	 * 一卡通消息验证地址
	 */
	private static final String HTTPS_VERIFY_URL = "http://114.55.40.31:8088/payment/payment/verifyResponse.do?";

	private static final String HTTPS_VERIFY_URL_3800 = "http://120.26.212.173:8089/payment/payment/verifyResponse.do?";

	private static Logger logger = LoggerFactory.getLogger(YiKatongNotify.class);

	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * 
	 * @param params
	 *            通知返回来的参数数组
	 * @return 验证结果
	 */
	public static boolean verify(YiKatongConfig config, Map<String, String> params) {
		// responsetResult的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "";
		String responseResult = "true";
		YiKaTongJsonBean yktjb = null;
		String notify_id = params.get("notify_id");
		if (notify_id != null) {
			responseTxt = verifyResponse(config, notify_id, params);
			logger.info("一卡通通知返回来的数据responseTxt===" + responseTxt);
			try {
				yktjb = (YiKaTongJsonBean) JsonUtil.toObj(responseTxt, YiKaTongJsonBean.class);
				if (yktjb.getResultCode() != null && yktjb.getResultCode().equals("00")
						|| yktjb.getResultCode().equals("true")) {
					responseResult = "true";
					YiKatongCore.logResult("一卡通交易回调消息合法性: " + responseResult);
				}
			} catch (Exception e) {
				System.out.println("返回结果json转换出错");
				e.printStackTrace();
			}
		}
		boolean isSign = true;
		if (yktjb != null) {
			SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
			sParaTemp.put("resultCode", yktjb.getResultCode());
			sParaTemp.put("resultMsg", yktjb.getResultMsg());
			sParaTemp.put("signMsg", yktjb.getSignMsg());
			sParaTemp.put("key", "admin888");
			// 此处修订为与消费统一的验签函数2016-04-15
			// 取得返回，转换为map格式
			isSign = verifySign(sParaTemp);
		}

		// 写日志记录（若要调试，请取消下面两行注释）
		// String sWord = "sign="+"responseTxt=" + responseTxt + "\n isSign=" +
		// isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
		// AlipayCore.logResult(sWord);
		// if (isSign) {
		// System.out.println("notify_id校验业务返回报文验签通过");
		// }
		if (isSign && responseResult.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean verify_3800(YiKatongConfig config, Map<String, String> params) {
		// responsetResult的结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关
		// isSign不是true，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关
		String responseTxt = "";
		String responseResult = "true";
		YiKaTongJsonBean yktjb = null;
		String notify_id = params.get("notify_id");
		if (notify_id != null) {
			// String notify_id = params.get(string);
			responseTxt = verifyResponse_3800(config, notify_id, params);
			// responseTxt = verifyResponse(notify_id, orderNo);
			logger.info("3800_一卡通通知返回来的数据responseTxt===" + responseTxt);
			try {
				yktjb = (YiKaTongJsonBean) JsonUtil.toObj(responseTxt, YiKaTongJsonBean.class);
				if (yktjb.getResultCode() != null && yktjb.getResultCode().equals("00")
						|| yktjb.getResultCode().equals("true")) {
					responseResult = "true";
					YiKatongCore.logResult("3800_一卡通交易回调消息合法性: " + responseResult);
				}
			} catch (Exception e) {
				logger.info("3800_返回结果json转换出错");
				e.printStackTrace();
			}
		}
		boolean isSign = true;
		if (yktjb != null) {
			SortedMap<String, String> sParaTemp = new TreeMap<String, String>();
			sParaTemp.put("resultCode", yktjb.getResultCode());
			sParaTemp.put("resultMsg", yktjb.getResultMsg());
			sParaTemp.put("signMsg", yktjb.getSignMsg());
			sParaTemp.put("key", "admin888");
			// 此处修订为与消费统一的验签函数2016-04-15
			// 取得返回，转换为map格式
			isSign = verifySign(sParaTemp);
		}

		// 写日志记录（若要调试，请取消下面两行注释）
		// String sWord = "sign="+"responseTxt=" + responseTxt + "\n isSign=" +
		// isSign + "\n 返回回来的参数：" + AlipayCore.createLinkString(params);
		// AlipayCore.logResult(sWord);
		// if (isSign) {
		// System.out.println("notify_id校验业务返回报文验签通过");
		// }
		if (isSign && responseResult.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 校验签名
	 * 
	 * @param params
	 * @return
	 */
	public static boolean verifySign(SortedMap<String, String> params) {
		String md5_calc = JsonUtil.createSign(params);
		logger.info("md5_calc==" + md5_calc);
		String sign = "";
		if (params.get("signMsg") != null) {
			sign = params.get("signMsg");
		}

		logger.info("sign==" + sign);
		if (sign == null || sign.equals("")) {
			return (false);
		} else {
			YiKatongCore.logResult("一卡通交易签名信息:" + "MySign = " + md5_calc + "\n" + "Sign = " + params.get("signMsg")
					+ "MySign==sign ?" + md5_calc.equals(sign));
			return (md5_calc.equals(sign));
		}
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * 
	 * @param Params
	 *            通知返回来的参数数组
	 * @param sign
	 *            比对的签名结果
	 * @return 生成的签名结果
	 */
	@SuppressWarnings("all")
	private static boolean getSignVeryfy(YiKatongConfig config, Map<String, String> Params, String sign) {
		// 过滤空值、sign与sign_type参数
		Map<String, String> sParaNew = YiKatongCore.paraFilter(Params);
		/**
		 * 签名msgsign 去/ 待修改
		 */
		// 获取待签名字符串
		String preSignStr = YiKatongCore.createLinkString(sParaNew);
		// 获得签名验证结果
		boolean isSign = true;
		if (YiKatongConfig.sign_type.equals("MD5")) {
			// isSign = YiKatongMD5.verify(preSignStr, sign,new
			// YiKatongConfig.key, YiKatongConfig.input_charset);
			isSign = YiKatongMD5.verify(preSignStr, sign, "admin888",
					/* AlipayConfig.input_charset */"gbk");
		}
		return isSign;
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * 
	 * @param notify_id
	 *            通知校验ID
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String verifyResponse(YiKatongConfig config, String notify_id, Map<String, String> params) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		// String signMsg1 = params.get("signMsg");
		String orderNo = params.get("orderNos");
		String partner = config.getPartner();// 一卡通合作账号

		SortedMap<String, String> params1 = new TreeMap<String, String>();
		params1.put("merchantId", partner);
		params1.put("notify_id", notify_id);
		params1.put("orderNo", orderNo);
		params1.put("key", new YiKatongConfig().getKey());
		String signMsg = JsonUtil.createSign(params1);

		String veryfy_url = HTTPS_VERIFY_URL + "merchantId=" + partner + "&notify_id=" + notify_id + "&orderNo="
				+ orderNo + "&signMsg=" + signMsg;

		logger.info("veryfy_url====" + veryfy_url);
		return checkUrl(veryfy_url);
	}

	private static String verifyResponse_3800(YiKatongConfig config, String notify_id, Map<String, String> params) {
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		// String signMsg1 = params.get("signMsg");
		String orderNo = params.get("orderNos");
		String partner = config.getPartner();// 一卡通合作账号

		SortedMap<String, String> params1 = new TreeMap<String, String>();
		params1.put("merchantId", partner);
		params1.put("notify_id", notify_id);
		params1.put("orderNo", orderNo);
		params1.put("key", new YiKatongConfig().getKey());
		String signMsg = JsonUtil.createSign(params1);

		String veryfy_url = HTTPS_VERIFY_URL_3800 + "merchantId=" + partner + "&notify_id=" + notify_id + "&orderNo="
				+ orderNo + "&signMsg=" + signMsg;

		logger.info("3800_veryfy_url====" + veryfy_url);
		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * 
	 * @param urlvalue
	 *            指定URL路径地址
	 * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
	 *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String checkUrl(String urlvalue) {
		String inputLine = "";
		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			inputLine = in.readLine().toString();
			logger.info("获取远程服务器ATN结果,inputLine====" + inputLine);
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}
}
