package com.rt.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.rt.sys.entity.SysDict;
import com.rt.sys.entity.SysOffice;
import com.rt.sys.entity.SysRole;
import com.rt.sys.mapper.SysDictMapper;
import com.rt.sys.service.ISysDictService;
import com.rt.sys.service.impl.support.BaseServiceImpl;

/**
 *
 * SysDict 表数据服务层接口实现类
 *
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

	@Resource
	private SysDictMapper sysDictMapper;

	

	/**
	 * 保存或更新
	 * 
	 * @param sysDict
	 * @return
	 */
	public int saveSysdict(SysDict sysDict) {
		 this.insertSelective(sysDict);
		 return 1;
	}

	/**
	 * 删除
	* @param sysDict
	* @return
	 */
	public int deleteSysDict(SysDict sysDict) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("type", sysDict.getValue());
//		if(sysDict.getType().equals("sys_area_type")){
//			int areaCount = this.beforeDelete(SysArea.class,params);
//			if(areaCount<0) return -1;
//		}
//		if(sysDict.getType().equals("sys_office_type")){
//			int officeCount = this.beforeDelete(SysOffice.class,params);
//			if(officeCount<0) return -1;
//		}
//		if(sysDict.getType().equals("sys_data_scope")){
//			int roleCount = this.beforeDelete(SysRole.class, params);
//			if(roleCount<0) return -1;
//		}
//		return this.updateDelFlagToDelStatusById(SysDict.class, sysDict.getId());
		return 1;
	}

	public Table<String,String, SysDict> findAllDictTable(){
		List<SysDict> dictList = this.selectList(new SysDict());
		Table<String,String, SysDict> tableDicts = HashBasedTable.create();
		for(SysDict dict : dictList){
			tableDicts.put(dict.getType(), dict.getValue(), dict);
		}
		return tableDicts;
	}
	
	public Multimap<String, SysDict> findAllMultimap(){
		List<SysDict> dictList = this.selectList(new SysDict());
		Multimap<String, SysDict> multimap = ArrayListMultimap.create();
		for(SysDict dict : dictList){
			multimap.put(dict.getType(), dict);
		}
		return multimap;
	}
}