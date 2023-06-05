package com.mmr.wordtalk.common.websocket.custom;

import com.mmr.wordtalk.common.security.service.WordtalkUser;
import com.mmr.wordtalk.common.websocket.holder.SessionKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

/**
 * @author 张恩睿
 * @date 2021/10/4 websocket session 标识生成规则
 */
@Configuration
@RequiredArgsConstructor
public class WordtalkSessionKeyGenerator implements SessionKeyGenerator {

	/**
	 * 获取当前session的唯一标识
	 * @param webSocketSession 当前session
	 * @return session唯一标识
	 */
	@Override
	public Object sessionKey(WebSocketSession webSocketSession) {

		Object obj = webSocketSession.getAttributes().get("USER_KEY_ATTR_NAME");

		if (obj instanceof WordtalkUser) {
			WordtalkUser user = (WordtalkUser) obj;
			// userId 作为唯一区分
			return String.valueOf(user.getId());
		}

		return null;
	}

}
