package com.rt.shop.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.rt.shop.common.constant.Constants;
import com.rt.shop.entity.User;
import com.rt.shop.service.IUserService;

public class UserSetting extends AccessControlFilter {
	
	@Autowired
	private IUserService userService;
	
	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (subject == null) {
            return false;
        }
        HttpSession session = ((HttpServletRequest)request).getSession();
        User current_user = (User) session.getAttribute(Constants.CURRENT_USER);
		Object recs = session.getAttribute(Constants.USER_MENUS);
        if(current_user == null || recs == null){
        	String username = (String) subject.getPrincipal();
        	User user = userService.selectUserByUsername(username);
        	if(user == null || user.getStatus() == Constants.USER_STATUS_LOCK){
        		setLoginUrl(Constants.LOGIN_URL);
        		redirectToLogin(request, response);
        		return false;
        	}
        	session.setAttribute(Constants.CURRENT_USER, user);
        //	session.setAttribute(Constants.USER_MENUS, user.getMenus());
        }
        return true;
    }

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}

}
