package com.rt.common.beetl.function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rt.common.constant.Constant;
import com.rt.common.utils.CacheUtils;
import com.rt.common.utils.StringConvert;
import com.rt.sys.entity.SysDict;
import com.rt.sys.entity.SysOffice;
import com.rt.sys.service.ISysDictService;
import com.rt.sys.service.ISysOfficeService;
import com.rt.sys.util.SysUserUtils;

@Component
public class OfficeFunctions {
	
	@Resource
	private ISysOfficeService sysOfficeService;
	@Resource
	private ISysDictService sysDictService;
	
	/**
	 * 全部机构 key:机构id  value:机构对象
	* @return
	 */
	public Map<Long, SysOffice> getAllOfficeMap(){
		Map<Long, SysOffice> allOfficeMap = CacheUtils.get(Constant.CACHE_SYS_OFFICE, "allOffice");
		if(allOfficeMap == null){
			allOfficeMap = Maps.newHashMap();
			List<SysOffice> list = sysOfficeService.selectList(new SysOffice());
			if(list!=null && list.size()>0){
				for(SysOffice so : list){
					allOfficeMap.put(so.getId(), so);
				}
			}
			CacheUtils.put(Constant.CACHE_SYS_OFFICE, "allOffice", allOfficeMap);
		}
		return allOfficeMap;
	}
	
	/**
	 * 根据部门id拼接所属机构层级字符
	* @param officeId 部门id
	* @param offices 全部机构map
	* @return
	 */
	public String getOfficeStrByOfficeId(Long officeId){
		Map<Long, SysOffice> offices = getAllOfficeMap();
		String str = "";
		if(offices!=null){
			if(offices.get(officeId) != null){
				String[] pids = ((SysOffice)offices.get(officeId)).getParent_ids().split(",");
				for(String id : pids){
					SysOffice so = (SysOffice)offices.get(StringConvert.toLong(id));
					if(so!=null)str+=so.getName()+" - ";
				}
				SysOffice so = (SysOffice)offices.get(officeId);
				str+=so.getName();
			}
		}
		return str;
	}
	
	/**
	 * 得到用户机构
	* @return
	 */
	public List<SysOffice> getUserOfficeList(){
		return SysUserUtils.getUserOffice();
	}
	
	/**
	 * 用户持有数据范围
	 */
	public List<SysDict> getUserDataScope(){
		List<String> values = SysUserUtils.getUserDataScope();
		Collection<SysDict> dicts = sysDictService.findAllMultimap().get("sys_data_scope");
		List<SysDict> resultList = Lists.newArrayList();
		for(SysDict dict : dicts){
			if(values.contains(dict.getValue())){
				resultList.add(dict);
			}
		}
		return resultList;
	}
	
}
