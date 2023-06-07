package com.mmr.wordtalk.common.ai.context;

import com.mmr.wordtalk.common.ai.core.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.ListOperations;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-06-07 18:26:00
 */
@Data
@AllArgsConstructor
public class RedisContext implements Context {
	private Integer contextSize;

	private ListOperations<String, Content> ops;

	@Override
	public void push(String key, Content content) {
		ops.rightPush(key, content);
	}

	@Override
	public List<Content> getContext(String key) {
		//  优化算法，在取的时候限定大小
		List<Content> range = ops.range(key, 0, -1);
		if (range.size() > contextSize) {
			ops.trim(key,range.size() - contextSize,-1);
			// redis低于6.2版本不支持该语法
			// ops.leftPop(key,range.size() - contextSize);
			return range.subList(range.size() - contextSize, range.size());
		}
		return range;
	}

	@Override
	public void clear(String key) {
		ops.leftPop(key, -1);
	}
}
