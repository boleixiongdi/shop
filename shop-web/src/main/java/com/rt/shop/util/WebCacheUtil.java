package com.rt.shop.util;

import com.rt.shop.common.ehcache.EHCacheUtil;
import com.rt.shop.entity.Store;

public class WebCacheUtil {

	
	public static Store getStore(Long userId){
		return (Store) EHCacheUtil.get("store"+userId);
	}
	
	public static void setStore(Long userId,Store store){
		 EHCacheUtil.set("store"+userId,store);
	}
}
