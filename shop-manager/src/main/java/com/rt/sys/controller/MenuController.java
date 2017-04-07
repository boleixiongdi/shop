package com.rt.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.common.base.BaseController;
import com.rt.sys.entity.SysResource;
import com.rt.sys.service.ISysResourceService;

/**
 * 菜单管理
 * 
 * @ClassName: MenuController
 * @author
 * @date 2014年10月11日 上午11:38:28
 *
 */

@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {

	@Resource
	private ISysResourceService sysResourceService;

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @param model
	 * @return 菜单管理模块html
	 */
	@RequestMapping
	public String toMenu(Model model) {
		model.addAttribute("treeList", JSON.toJSONString(sysResourceService.getMenuTree()));
		return "sys/menu/menu";
	}

	/**
	 * 菜单树
	 * 
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<SysResource> tree() {
		return sysResourceService.getMenuTree();
	}

	/**
	 * 分页显示菜单table
	 * 
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model) {
		Page<SysResource> page = sysResourceService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/menu/menu-list";
	}

	/**
	 * 添加或更新菜单
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysResource sysResource) {
		return sysResourceService.saveSysResource(sysResource);
	}

	/**
	 * 删除菜单及其子菜单
	 * 
	 * @param resourceId
	 *            菜单id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Integer dels(Long id) {
		Integer count = 0;
		if (null != id) {
			count = sysResourceService.deleteResourceByRootId(id);
		}
		return count;
	}

	/**
	 * 弹窗
	 * 
	 * @param id
	 * @param parentId
	 *            父类id
	 * @param mode
	 *            模式(add,edit,detail)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		SysResource resource = null, pResource = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pResource = sysResourceService.selectById(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			resource = sysResourceService.selectById(id);
			pResource = sysResourceService.selectById(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			resource = sysResourceService.selectById(id);
			pResource = sysResourceService.selectById(resource
					.getParentId());
		}
		model.addAttribute("pResource", pResource).addAttribute("sysResource",
				resource);
		return mode.equals("detail") ? "sys/menu/menu-detail"
				: "sys/menu/menu-save";
	}

}
