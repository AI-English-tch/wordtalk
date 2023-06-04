package com.mmr.wordtalk.bridge.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.mmr.wordtalk.bridge.entity.PythonTalk;
import com.mmr.wordtalk.bridge.service.PythonService;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PythonServiceImpl implements PythonService {

//	private WebClient pythonClient = WebClient.builder()
//			.baseUrl("ip:端口")
//			.defaultHeader("token", SecurityUtils.getUser().getUsername()).build();
	private WebClient pythonClient = null;
	@Override
	public Boolean setWords(List<String> words) {
		if (CollectionUtil.isEmpty(words)) {
			// 如果指定的词组为空，则返回失败
			return Boolean.FALSE;
		}
		// 设置请求参数
		MultiValueMap<String, String> fromData = new LinkedMultiValueMap<>();
//        map.add("title", "abc");

		// 通过pythonClient访问python的后台
		Mono<JSONObject> responseMono = pythonClient.post()
				.uri("/接口名称")
				.body(BodyInserters.fromFormData(fromData))
				.retrieve()
				.bodyToMono(JSONObject.class);

		JSONObject response = responseMono.block();
		// 对response的结果做处理

		// 返回成功
		return Boolean.TRUE;
	}

	@Override
	public PythonTalk sendTalk(String talk) {
		//  将对话内容发送给python后台
		// 接收聊天回复给用户的聊天信息
		// 接收监督机器人返回给用户的批复信息
		// 封装到对象类型，并返回给客户端
		return null;
	}
}
