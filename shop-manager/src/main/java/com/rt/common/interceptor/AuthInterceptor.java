package com.rt.common.interceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rt.common.beetl.utils.BeetlUtils;
import com.rt.common.constant.Constant;
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysUser;
import com.rt.sys.util.SysUserUtils;

public class AuthInterceptor implements HandlerInterceptor {

    private Set<String> ignorePath = new HashSet<String>
    (Arrays.asList("/gw", "/cms", "/jl","/front", "/notlogin", "/ErrorHandler"));

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI(); //请求路径
        String ctx = request.getContextPath();
        String path = uri.replace(ctx,"");
        if(path.startsWith("/front") || path.startsWith("/gw") || path.startsWith("/cms")|| path.startsWith("/jl")){
       	 return true;
       }
       
        if(ignorePath.contains(path)){
            //获得session中的登陆用户
            SysUser sessionUser = SysUserUtils.getSessionLoginUser();

            //获得缓存中的登陆用户
            SysUser cacheUser = SysUserUtils.getCacheLoginUser();

            if (sessionUser == null || cacheUser == null) { // 转到登陆页面
                response.sendRedirect(ctx + "/notlogin");
                return false;
            } else {
            	if(sessionUser.isAdmin()){
            		return true;
            	}
                Map<String, SysResource> allRes = BeetlUtils
                        .getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
                String perPath = path.substring(1);
                SysResource sysResource = allRes.get(perPath);
                //判断如果url不在数据库中，则默认都有权限访问
                if (sysResource == null
                        || Constant.RESOURCE_COMMON.equals(sysResource.getCommon())) {
                    return true;
                }
                
                //实时的权限验证,检测用户认证是否改变，如果认证改变则重置，否则不进行任何操作
                SysUserUtils.setUserAuth();
                
                //从缓存中的用户权限 map<url:resource>
                Map<Long,LinkedHashMap<String, SysResource>> userRes = SysUserUtils.getUserResources();
                for(Map<String, SysResource> res1 :userRes.values()){
                	if (res1.containsKey(perPath)) { //有权限则过
                        return true;
                    } else { //没有权限跳到未有权限
                        response.sendRedirect(ctx + "/notauth");
                        return false;
                    }
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
