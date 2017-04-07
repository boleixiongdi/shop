package com.rt.shop.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rt.shop.common.tools.IPUtils;
import com.rt.shop.common.tools.spring.SpringContextUtil;
import com.rt.shop.entity.ShoppingPv;
import com.rt.shop.entity.User;
import com.rt.shop.service.IShoppingPvService;
import com.rt.shop.util.LogUtils;
import com.rt.shop.util.SecurityUserHolder;

/**
 * 记录访问日志
 */
public class AccessLogFilter extends BaseFilter {

	 @Autowired
	 private IShoppingPvService shoppingPvService=SpringContextUtil.getBean(IShoppingPvService.class);
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String username = getUsername();
        String jsessionId = request.getRequestedSessionId();
        String ip = IPUtils.getClientAddress(request);
        String accept = request.getHeader("accept");
        String userAgent = request.getHeader("User-Agent");
        String url = request.getRequestURI();
        String params = getParams(request);
        String headers = getHeaders(request);
        ShoppingPv pv=new ShoppingPv();
        pv.setAddTime(new Date());
        pv.setAccept("acc");
        pv.setHeaders("he");
        pv.setIp(ip);pv.setJsessionId(jsessionId);
        pv.setParams(params);
        pv.setUrl(url);pv.setUserAgent("ug");
        pv.setUsername(username);
        shoppingPvService.insertSelective(pv);
	//	LogUtils.logAccess(request);
		chain.doFilter(request, response);
	}
//	public static String getUserAgent(HttpServletRequest request) {
//		String uabrow = request.getHeader("User-Agent");
//		UserAgent userAgent = UserAgent.parseUserAgentString(uabrow);
//		Browser browser = userAgent.getBrowser();
//		OperatingSystem os = userAgent.getOperatingSystem();
//		return browser.getName().toLowerCase() + ";"
//				+ os.getName().toLowerCase();
//	}
	protected static String getParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        return JSON.toJSONString(params);
    }


	private static String getHeaders(HttpServletRequest request) {
        Map<String, List<String>> headers = Maps.newHashMap();
        Enumeration<String> namesEnumeration = request.getHeaderNames();
        while(namesEnumeration.hasMoreElements()) {
            String name = namesEnumeration.nextElement();
            Enumeration<String> valueEnumeration = request.getHeaders(name);
            List<String> values = Lists.newArrayList();
            while(valueEnumeration.hasMoreElements()) {
                values.add(valueEnumeration.nextElement());
            }
            headers.put(name, values);
        }
        return JSON.toJSONString(headers);
    }


    protected static String getUsername() {
    	User sysUser = SecurityUserHolder.getSessionLoginUser();
    	if(sysUser == null) return "未登录";
        return sysUser.getUserName();
    }
}
