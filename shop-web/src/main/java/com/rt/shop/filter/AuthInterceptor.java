package com.rt.shop.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rt.shop.entity.User;
import com.rt.shop.util.SecurityUserHolder;

public class AuthInterceptor implements HandlerInterceptor {

	private Set<String> ignorePath = new HashSet<String>(Arrays.asList(
			"/seller/swf_upload.htm", "/seller/upload.htm", "/notlogin",
			"/ErrorHandler", "/user/login.htm", "/register.htm"));

	private Set<String> notignorePath = new HashSet<String>(
			Arrays.asList("/add_goods_cart.htm","/goods_cart1.htm"));

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String uri = request.getRequestURI(); // 请求路径
		String ctx = request.getContextPath();
		String path = uri.replace(ctx, "");

		if (!ignorePath.contains(path)) {
			if (path.contains("/seller") || path.contains("/buyer")
					|| notignorePath.contains(path)) {
				// 获得session中的登陆用户
				User sessionUser = SecurityUserHolder.getSessionLoginUser();
				if (sessionUser == null) { // 转到登陆页面
					request.getRequestDispatcher("/user/login.htm").forward(request, response);
					//response.sendRedirect(ctx + "/user/login.htm");
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
