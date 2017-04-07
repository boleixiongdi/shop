package com.rt.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.rt.common.beetl.utils.BeetlUtils;
import com.rt.common.constant.Constant;
import com.rt.common.utils.TreeUtils;
import com.rt.shop.common.tools.StringUtils;
import com.rt.sys.entity.SysResource;
import com.rt.sys.entity.SysUser;
import com.rt.sys.mapper.SysResourceMapper;
import com.rt.sys.service.ISysResourceService;
import com.rt.sys.service.impl.support.BaseServiceImpl;
import com.rt.sys.util.SysUserUtils;

/**
 *
 * SysResource 表数据服务层接口实现类
 *
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

	@Resource
	private SysResourceMapper sysResourceMapper;
	/**
	 * 新增or更新SysResource
	 */
	public int saveSysResource(SysResource sysResource) {
		boolean count = false;
		// 新的parentIds
		sysResource.setParentIds(sysResource.getParentIds()
				+ sysResource.getParentId() + ",");
		if (null == sysResource.getId()) {
			count = this.insertSelective(sysResource);
		} else {
			// getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			// 先更新parentId，此节点的parentIds以更新
			count = this.updateSelectiveById(sysResource);
			// 不移动节点不更新子节点的pids
			if (!sysResource.getOldParentIds().equals(sysResource.getParentIds())) {
				sysResourceMapper.updateParentIds(sysResource); // 批量更新子节点的parentIds
			}
		}
		if (count) {
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_RESOURCE,
					this.getAllResourcesMap());
			SysUserUtils.clearCacheResource();
		}
		return 1;
	}

	/**
	 * 根据父id删除自身已经所有子节点
	 * 
	 * @param id
	 * @return
	 */
	public int deleteResourceByRootId(Long id) {
		int count = sysResourceMapper.beforeDeleteResource(id);
//		if (count > 0)
//			return -1;
		int delCount = sysResourceMapper.deleteIdsByRootId(id);
		if (delCount > 0) {
			// 重新查找全部资源放入缓存(为了开发时候用)
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_RESOURCE,
					this.getAllResourcesMap());
			SysUserUtils.clearCacheResource();
		}

		return delCount;
	}

	/**
	 * 根据用户id得到用户持有的资源
	 * @param userId
	 * @return
	 */
	public List<SysResource> findUserResourceByUserId(SysUser sysUser) {
		return sysResourceMapper.findUserResourceByUserId(sysUser.getId());
	}
	public List<SysResource> findUserResourceByUserIdAndPid(Long userId,Long parentId) {
		return sysResourceMapper.findUserResourceByUserIdAndPid(userId,parentId);
	}

	/**
	 * 菜单管理分页显示筛选查询
	 * @param params {"name":"菜单名字","id":"菜单id"}
	 * @return
	 */
	public Page<SysResource> findPageInfo(Map<String, Object> params) {
		List<SysResource> list = sysResourceMapper.findPageInfo(params);
		Page p=null;
		p.setRecords(list);
		return p;
	}

	/**
	 * 获取全部资源map形式
	 * @return
	 */
	public LinkedHashMap<String, SysResource> getAllResourcesMap() {
		// 读取全部资源
		List<SysResource> resList = sysResourceMapper.selectList(new EntityWrapper(new SysResource(),"sort"));
		LinkedHashMap<String, SysResource> AllResourceMap = new LinkedHashMap<String, SysResource>();
		for (SysResource res : resList) {
			if (!StringUtils.hasLength(res.getUrl())) {
				System.out.println(res);
				System.out.println(res.getId());
				AllResourceMap.put(res.getId().toString(), res);
			} else {
				AllResourceMap.put(res.getUrl(), res);
			}
		}
		return AllResourceMap;
	}

	/**
	 * 获取全部资源list形式
	 * @return
	 */
	public List<SysResource> getAllResourcesList() {
		LinkedHashMap<String, SysResource> allRes = BeetlUtils
				.getBeetlSharedVars(Constant.CACHE_ALL_RESOURCE);
		List<SysResource> resList = new ArrayList<SysResource>(allRes.values());
		return resList;
	}
	
	/**
	 * 获取菜单树
	 */
	public List<SysResource> getMenuTree(){
		return TreeUtils.toTreeNodeList(getAllResourcesList(),SysResource.class);
	}

	public List<SysResource> selectTop(long parentId) {
		return sysResourceMapper.selectTop(parentId);
	}

	public List<SysResource> findUserResourceByPid(Long id) {
		// TODO Auto-generated method stub
		return sysResourceMapper.findUserResourceByPid(id);
	}
}