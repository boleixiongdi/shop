package com.rt.shop.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.rt.shop.entity.ChattingLog;

/**
 *
 * Chattinglog 表数据服务层接口
 *
 */
public interface IChattingLogService extends ISuperService<ChattingLog> {

	

	/**
	 * //   "select obj from ChattingLog obj where obj.chatting.id=:chat_id and obj.mark=:mark and obj.user.id=:user_id order by addTime asc", 
	 * @param sChattingLog
	 * @param string
	 * @return
	 */
	List<ChattingLog> selectListOr1(ChattingLog sChattingLog, String string);

	


}