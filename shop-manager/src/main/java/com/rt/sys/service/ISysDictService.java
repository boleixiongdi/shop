package com.rt.sys.service;

import javax.annotation.Resource;

import com.baomidou.framework.service.ISuperService;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.rt.sys.entity.SysDict;
import com.rt.sys.mapper.SysDictMapper;

/**
 *
 * SysDict 表数据服务层接口
 *
 */
public interface ISysDictService extends ISuperService<SysDict> {


	/**
	 * 保存或更新
	 * 
	 * @param sysDict
	 * @return
	 */
	public int saveSysdict(SysDict sysDict);

	/**
	 * 删除
	* @param sysDict
	* @return
	 */
	public int deleteSysDict(SysDict sysDict) ;

	public Table<String,String, SysDict> findAllDictTable();
	
	public Multimap<String, SysDict> findAllMultimap();
}