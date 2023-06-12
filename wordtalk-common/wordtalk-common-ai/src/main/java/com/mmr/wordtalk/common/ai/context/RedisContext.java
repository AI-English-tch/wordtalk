package com.mmr.wordtalk.common.ai.context;

import com.mmr.wordtalk.common.ai.core.Content;
import lombok.Data;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-06-07 18:26:00
 */
@Data
public class RedisContext implements Context {
	private Integer contextSize;

	private RedisTemplate redisTemplate;

	private ListOperations<String, Content> opsForList;

	public RedisContext(Integer contextSize, RedisTemplate redisTemplate) {
		this.contextSize = contextSize;
		this.redisTemplate = redisTemplate;
		this.opsForList = redisTemplate.opsForList();
	}

	@Override
	public void init(String key, List<Content> contentList) {
		// 删除原有的列表
		redisTemplate.delete(key);
		opsForList.rightPushAll(key,contentList);
	}

	@Override
	public void push(String key, Content content) {
		opsForList.rightPush(key, content);
	}

	@Override
	public List<Content> getContext(String key) {
		//  优化算法，在取的时候限定大小
		List<Content> range = opsForList.range(key, 0, -1);
		if (range.size() > contextSize) {
			opsForList.trim(key, range.size() - contextSize, -1);
			// redis低于6.2版本不支持该语法
			// ops.leftPop(key,range.size() - contextSize);
			return range.subList(range.size() - contextSize, range.size());
		}
		return range;
	}

	@Override
	public void clear(String key) {
		opsForList.leftPop(key, -1);
	}
}
