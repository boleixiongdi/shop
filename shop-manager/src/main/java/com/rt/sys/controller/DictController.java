package com.rt.sys.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.rt.shop.common.tools.CommUtil;
import com.rt.sys.entity.SysDict;
import com.rt.sys.service.ISysDictService;

@Controller
@RequestMapping("dict")
public class DictController {

	@Resource
	private ISysDictService sysDictService;
	
	
	@RequestMapping
	public String toDict(Model model){
		return "sys/dict/dict";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody boolean save(@ModelAttribute SysDict sysDict) {
		return sysDictService.insertSelective(sysDict);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody boolean del(@ModelAttribute SysDict sysDict){
		return sysDictService.deleteSelective(sysDict);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute SysDict sysDict, Model model) {
		Page<SysDict> page = sysDictService.selectPage(new Page<SysDict>(Integer.valueOf(CommUtil.null2Int(pageNum)), 12), sysDict);
		model.addAttribute("page", page);
		return "sys/dict/dict-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		SysDict dict = sysDictService.selectById(id);
		model.addAttribute("dict", dict);
		return "sys/dict/dict-save";
	}
	
}
