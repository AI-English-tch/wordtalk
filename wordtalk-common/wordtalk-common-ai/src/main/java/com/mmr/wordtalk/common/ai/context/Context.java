package com.mmr.wordtalk.common.ai.context;

import com.mmr.wordtalk.common.ai.core.Content;

import java.util.List;

/**
 * 对话式AI的上下文对象
 *
 * @author 张恩睿
 * @date 2023-06-07 14:19:00
 */
public interface Context {

	/**
	 * 向上下文中添加新的内容
	 *
	 * @param key
	 * @param content
	 */
	void push(String key,Content content);

	/**
	 * 获取上下文的方法
	 *
	 * @return
	 */
	List<Content> getContext(String key);

	/**
	 * 清理上下文的对象的方法
	 */
	void clear(String key);
}
