package com.rt.common.spring.listener;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.rt.common.beetl.utils.BeetlUtils;
import com.rt.common.constant.Constant;
import com.rt.sys.entity.SysResource;
import com.rt.sys.service.ISysResourceService;

@Component
public class ApplicationContextInitListener implements
		ApplicationListener<ContextRefreshedEvent>, ServletContextAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ServletContext servletContext;

	@Resource
	private ISysResourceService sysResourceService;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ApplicationContext parentContext = ((ContextRefreshedEvent) event)
				.getApplicationContext().getParent();
		
		// 子容器初始化时(spring-mvc)
		if (null != parentContext) {
			
			/*RequestMappingHandlerMapping rmhp = event.getApplicationContext()
					.getBean(RequestMappingHandlerMapping.class);
			
			Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
			
			Iterator<RequestMappingInfo> iterator = map.keySet().iterator();
			
			while(iterator.hasNext()){
				RequestMappingInfo info = iterator.next();
				Set<String> set = info.getPatternsCondition().getPatterns();
			}*/
			
			String ctxPath = servletContext.getContextPath();
			
			//读取全部资源
			LinkedHashMap<String, SysResource> AllResourceMap = sysResourceService.getAllResourcesMap();
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_RESOURCE,AllResourceMap);
			
			//初始化任务调度
			//maintainTaskDefinitionService.initTask();
			
			logger.info("根路径:"+ctxPath);
			
			logger.info("初始化系统资源:(key:" + Constant.CACHE_ALL_RESOURCE
					+ ",value:Map<资源url, SysResource>)");
		}
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
