package com.rt.shop.service;

import java.util.List;

import com.rt.shop.entity.Message;
import com.baomidou.framework.service.ISuperService;

/**
 *
 * Message 表数据服务层接口
 *
 */
public interface IMessageService extends ISuperService<Message> {

	/**
	 * "select obj from Message obj where obj.parent.id is null and (obj.status=:status and obj.toUser.id=:to_user_id) or (obj.reply_status=:reply_status and obj.fromUser.id=:from_user_id) ",
					
	 * @param sMessage
	 * @return
	 */

	List<Message> selectMessAgeOr(Message sMessage);


}