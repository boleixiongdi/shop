package com.rt.shop.pay.yikatong.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.httpclient.NameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rt.shop.pay.yikatong.config.YiKatongConfig;
import com.rt.shop.pay.yikatong.util.httpClient.HttpProtocolHandler;
import com.rt.shop.pay.yikatong.util.httpClient.HttpRequest;
import com.rt.shop.pay.yikatong.util.httpClient.HttpResponse;
import com.rt.shop.pay.yikatong.util.httpClient.HttpResultType;
//import com.alipay.util.JsonUtil;

/* *
 *类名：AlipaySubmit
 *功能：宏顶高科各接口请求提交类
 *详细：构造宏顶高科各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究宏顶高科接口使用，只是提供一个参考。
 */

public class YiKatongSubmit {

	/**
	 * 宏顶高科提供给商户的服务接入网关URL(新)
	 */
	private static final String HDGKPAY_GATEWAY_NEW = "http://114.55.40.31:8088/payment/payment/onecardpay.do?";
	
	private static final String HDGKPAY_GATEWAY_NEW_3800 = "http://120.26.212.173:8089/payment/payment/onecardpay.do?";
	
	private static Logger logger = LoggerFactory.getLogger(YiKatongSubmit.class);
	/**
	 * 生成签名结果
	 * 
	 * @param sPara
	 *            要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(SortedMap<String, String> sPara) {

		String md5 = JsonUtil.createSign(sPara);

		return md5;
	}

	/**
	 * 生成要请求给宏顶高科的参数数组
	 * 
	 * @param sParaTemp
	 *            请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private static SortedMap<String, String> buildRequestPara(SortedMap<String, String> sParaTemp) {
		// 除去数组中的空值和签名参数
		// SortedMap<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
		// 生成签名结果
		String mysign = buildRequestMysign(sParaTemp);
		logger.info("生成签名结果,mysign=="+mysign);
		// 签名结果与签名方式加入请求提交参数组中
		sParaTemp.put("signMsg", mysign);
		// sParaTemp.put("sign_type", AlipayConfig.sign_type);

		return sParaTemp;
	}

	/**
	 * 建立请求，以表单HTML形式构造（默认）
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @return 提交表单HTML文本
	 */
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static String buildRequest(YiKatongConfig config,SortedMap<String, String> sParaTemp, String strMethod, String strButtonName) {

//		System.out.println("into buildRequest...");
		// 待请求参数数组
		// 只要最后按照顺序拼接成json即可
		SortedMap<String, String> sPara = buildRequestPara(sParaTemp);
		// 拼成json
		String json = "";
		try {
			json = JsonUtil.toJson(sParaTemp);
			System.out.println("json==" + json);
		} catch (Exception e) {
			System.out.println("转换json错误" + e.getMessage());
			e.printStackTrace();
		}

		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();
		// 生成确认页面----
		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + HDGKPAY_GATEWAY_NEW + "\" method=\""
				+ strMethod + "\">");
		// + "_input_charset=" + AlipayConfig.input_charset
		// 把json的值放入隐藏变量里
		sbHtml.append("<input type=\"hidden\" name=\"" + "json" + "\" value=" + json + "/>");

		/**
		 * for (int i = 0; i < keys.size(); i++) { String name = (String)
		 * keys.get(i); String value = (String) sPara.get(name);
		 * 
		 * sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\""
		 * + value + "\"/>"); }
		 */

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

		logger.info("sbHtml==" + sbHtml.toString());

		return sbHtml.toString();
	}
	public static String buildRequest_3800(YiKatongConfig config,SortedMap<String, String> sParaTemp, String strMethod, String strButtonName) {

//		System.out.println("into buildRequest...");
		// 待请求参数数组
		// 只要最后按照顺序拼接成json即可
		SortedMap<String, String> sPara = buildRequestPara(sParaTemp);
		// 拼成json
		String json = "";
		try {
			json = JsonUtil.toJson(sParaTemp);
			System.out.println("json==" + json);
		} catch (Exception e) {
			System.out.println("转换json错误" + e.getMessage());
			e.printStackTrace();
		}

		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();
		// 生成确认页面----
		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + HDGKPAY_GATEWAY_NEW_3800 + "\" method=\""
				+ strMethod + "\">");
		// + "_input_charset=" + AlipayConfig.input_charset
		// 把json的值放入隐藏变量里
		sbHtml.append("<input type=\"hidden\" name=\"" + "json" + "\" value=" + json + "/>");

		/**
		 * for (int i = 0; i < keys.size(); i++) { String name = (String)
		 * keys.get(i); String value = (String) sPara.get(name);
		 * 
		 * sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\""
		 * + value + "\"/>"); }
		 */

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

		logger.info("3800_sbHtml==" + sbHtml.toString());

		return sbHtml.toString();
	}

	/**
	 * 建立请求，以表单HTML形式构造，带文件上传功能
	 * 
	 * @param sParaTemp
	 *            请求参数数组
	 * @param strMethod
	 *            提交方式。两个值可选：post、get
	 * @param strButtonName
	 *            确认按钮显示文字
	 * @param strParaFileName
	 *            文件上传的参数名
	 * @return 提交表单HTML文本
	 */
	public static String buildRequest(SortedMap<String, String> sParaTemp, String strMethod, String strButtonName,
			String strParaFileName) {
		// 待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\"  enctype=\"multipart/form-data\" action=\""
				+ HDGKPAY_GATEWAY_NEW + "_input_charset=" + YiKatongConfig.input_charset + "\" method=\"" + strMethod
				+ "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sPara.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		sbHtml.append("<input type=\"file\" name=\"" + strParaFileName + "\" />");

		// submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");

		return sbHtml.toString();
	}

	/**
	 * 建立请求，以模拟远程HTTP的POST请求方式构造并获取宏顶高科的处理结果
	 * 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
	 * "",sParaTemp)
	 * 
	 * @param strParaFileName
	 *            文件类型的参数名
	 * @param strFilePath
	 *            文件路径
	 * @param sParaTemp
	 *            请求参数数组
	 * @return 宏顶高科处理结果
	 * @throws Exception
	 */
	public static String buildRequest(String strParaFileName, String strFilePath, SortedMap<String, String> sParaTemp)
			throws Exception {
		// 待请求参数数组
		SortedMap<String, String> sPara = buildRequestPara(sParaTemp);

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset(YiKatongConfig.input_charset);

		request.setParameters(generatNameValuePair(sPara));
		request.setUrl(HDGKPAY_GATEWAY_NEW + "_input_charset=" + YiKatongConfig.input_charset);

		HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
		if (response == null) {
			return null;
		}

		String strResult = response.getStringResult();

		return strResult;
	}

	/**
	 * MAP类型数组转换成NameValuePair类型
	 * 
	 * @param properties
	 *            MAP类型数组
	 * @return NameValuePair类型数组
	 */
	private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
		NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}

		return nameValuePair;
	}

	/**
	 * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
	 * 
	 * @return 时间戳字符串
	 * @throws IOException
	 * @throws DocumentException
	 * @throws MalformedURLException
	 */
	public static String query_timestamp() throws MalformedURLException, DocumentException, IOException {

		// 构造访问query_timestamp接口的URL串
		String strUrl = HDGKPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + new YiKatongConfig().getPartner()
				+ "&_input_charset" + YiKatongConfig.input_charset;
		StringBuffer result = new StringBuffer();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());

		List<Node> nodeList = doc.selectNodes("//alipay/*");

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
	}

	public static String buildForm(YiKatongConfig config, SortedMap<String, String> sParaTemp, String gateway,
			String strMethod, String strButtonName) {
		Map sPara = buildRequestPara(config, sParaTemp);
		List keys = new ArrayList(sPara.keySet());
		StringBuilder sbHtml = new StringBuilder();
		sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" enctype=\"multipart/form-data\" action=\""
				+ gateway + "_input_charset=" + config.getInput_charset() + "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			String value = (String) sPara.get(name);

			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

		return sbHtml.toString();
	}

	private static Map buildRequestPara(YiKatongConfig config, SortedMap<String, String> sParaTemp) {
		Map<String, String> sPara = YiKatongCore.paraFilter(sParaTemp);

		String mysign = JsonUtil.createSign(sParaTemp);
//		System.out.println(mysign);
		logger.info("mysign==="+mysign);
		sPara.put("sign", mysign);
		if (!((String) sPara.get("service")).equals("alipay.wap.trade.create.direct")) {
			if (!((String) sPara.get("service")).equals("alipay.wap.auth.authAndExecute"))
				sPara.put("sign_type", config.getSign_type());
		}
		return sPara;
	}

}
