package com.rt.shop.pay.yikatong.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;




import com.rt.shop.pay.yikatong.util.YiKatongMD5;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rt.shop.pay.yikatong.config.YiKatongConfig;


public class JsonUtil {

	public static String toJson(Object obj) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(obj);
	}
	
	public static Object toObj(String json,Class cls) throws Exception{
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, cls);
		
	}
	
	/**
	 * 创建md5摘要,规则�?:按参数名称a-z排序,遇到空�?�的参数不参加签名�??
	 */
	public static String  createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"signMsg".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		//补上key
		//sb.append("key=" + packageParams.get("key"));
//		sb.append("key=" +YiKatongConfig.key );
		sb.append("key=" +"admin888" );
		System.out.println("sign string=============" + sb);
		
		String md5 = new YiKatongMD5().calcMD5(sb.toString()).toUpperCase();
		
//		String sign = MD5Util.MD5Encode(sb.toString(), "UTF8")
//				.toUpperCase();

		return md5;

	}
	
	
public static String getPaySignValue(Map<String,String> paySignMap) throws Exception{
		
		String[] arr = new String[]{"merchantId","paymentOrderId","payDatetime","payAmount","payResult","orderNo","orderDatetime","orderAmount",
				"errorCode","ext1","ext2"
			};
				// { "appid", "timestamp", "noncestr", "package", "appkey"};
		Arrays.sort(arr); 
		StringBuilder content1 = new StringBuilder();
		for (int i = 0; i < arr.length; i++) { 
			String value = paySignMap.get(arr[i]);
			if (value!=null && !value.trim().equals("")){
				content1.append(arr[i]+"="+value+"&");
			}
		}
		String string1 = content1.toString().substring(0,content1.length()-1);
		string1 = string1+"&key="+paySignMap.get("keys");
		//string1 = string1.replace("keys", "key");
		
		System.out.println("sign string1==========:"+string1);
		String md5 = new YiKatongMD5().calcMD5(string1).toUpperCase();
		return md5;
//		MessageDigest md = MessageDigest.getInstance("SHA-1");
//		byte[] digest = md.digest(string1.getBytes());
//		
////		System.out.println("aaa0:"+GeneralUtil.byteToStr(md.digest("appid=wxf8b4f85f3a794e77&appkey=2Wozy2aksie1puXUBpWD8oZxiD1DfQuEaiC7KcRATv1Ino3mdopKaPGQQ7TtkNySuAmCaDCrw4xhPY5qKTBl7Fzm0RgR3c0WaVYIXZARsxzHV2x7iwPPzOz94dnwPWSn&noncestr=adssdasssd13d&package=bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f%2fwww.qq.com&out_trade_no=16642817866003386000&partner=1900000109&spbill_create_ip=127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9&timestamp=189026618".getBytes())));
////		System.out.println("aaa2:"+GeneralUtil.byteToStr(md.digest("appid=wxf8b4f85f3a794e77&appkey=2Wozy2aksie1puXUBpWD8oZxiD1DfQuEaiC7KcRATv1Ino3mdopKaPGQQ7TtkNySuAmCaDCrw4xhPY5qKTBl7Fzm0RgR3c0WaVYIXZARsxzHV2x7iwPPzOz94dnwPWSn&nonceStr=adssdasssd13d&package=bank_type=WX&body=XXX&fee_type=1&input_charset=GBK&notify_url=http%3a%2f%2fwww.qq.com&out_trade_no=16642817866003386000&partner=1900000109&spbill_create_ip=127.0.0.1&total_fee=1&sign=BEEF37AD19575D92E191C1E4B1474CA9&timestamp=189026618".getBytes())));
//		return GeneralUtil.byteToStr(digest).toUpperCase();
	}

    public static String cutTail(String in)
    {
    	//in = in.substring(0, in.indexOf("/"));
    	in = in.substring(0, in.indexOf(""));
    	return in;
    }
	
	public static void main(String[] args) throws Exception{
		
		String in ="admin888/";
		
		System.out.println(cutTail(in));
		
		Map<String,String> vo = new HashMap<String,String>();
		
		vo.put("1", "aaaaaaaaaa");
		vo.put("2", "bbb");
		vo.put("3", "cccc");
		
		String json = JsonUtil.toJson(vo);
		System.out.println("aa:"+json);
		
		Map<String,String> vo1 = (Map<String,String>)JsonUtil.toObj(json, Map.class);
		
		for (Map.Entry<String, String> entry : vo1.entrySet()) {
			   System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
	    }
	}
	
	public static double fenToYuan(long fen, int bit) {
		double yuan = ((double) fen) / 100;

		yuan = formatData(yuan, 2);
		return yuan;

	}
	

	/**
	 * 格式化浮点数
	 * 
	 * @magic.serviceMethod tx="true"
	 */
	public static double formatData(double pDouble, int bit) {

		BigDecimal bd = new BigDecimal(pDouble);
		BigDecimal bd1 = bd.setScale(bit, BigDecimal.ROUND_HALF_UP);
		pDouble = bd1.doubleValue();
		long ll = Double.doubleToLongBits(pDouble);
		return pDouble;

	}
}
