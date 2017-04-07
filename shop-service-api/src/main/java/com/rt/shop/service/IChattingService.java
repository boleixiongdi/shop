package com.rt.shop.service;

import java.util.List;

import com.rt.shop.entity.Chatting;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Chatting 表数据服务层接口
 *
 */
public interface IChattingService extends ISuperService<Chatting> {

	/**
	 * "select obj from Chatting obj where obj.user1.id=:uid and obj.user2.id=:user_id or obj.user1.id=:user_id and obj.user2.id=:uid", 
        
	 * @param sChatting
	 * @return
	 */
	List selectListOr(Chatting sChatting);


}