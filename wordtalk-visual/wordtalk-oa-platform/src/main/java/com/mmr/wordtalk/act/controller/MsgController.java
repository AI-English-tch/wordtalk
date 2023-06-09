package com.mmr.wordtalk.act.controller;

import cn.hutool.core.collection.CollUtil;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.core.util.SpringContextHolder;
import com.mmr.wordtalk.common.security.annotation.Inner;
import com.mmr.wordtalk.common.websocket.distribute.MessageDO;
import com.mmr.wordtalk.common.websocket.distribute.RedisMessageDistributor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张恩睿
 * @date 2021/12/16
 */
@RequestMapping("/msg")
@RestController
public class MsgController {

	@Inner(value = false)
	@GetMapping("/send")
	public R send() {
		RedisMessageDistributor messageDistributor = SpringContextHolder.getBean(RedisMessageDistributor.class);

		// websocket 发送消息
		MessageDO messageDO = new MessageDO();
		messageDO.setNeedBroadcast(Boolean.FALSE);
		// 给目标用户ID
		messageDO.setSessionKeys(CollUtil.newArrayList(1));
		messageDO.setMessageText("消息内容");
		messageDistributor.distribute(messageDO);

		return R.ok();
	}

}
