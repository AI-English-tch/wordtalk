package com.mmr.wordtalk.monitor.service;

import java.util.Map;

/**
 * @author wordtalk
 * @date 2019-05-08
 * <p>
 * redis 监控
 */
public interface RedisService {

	/**
	 * 获取内存信息
	 * @return
	 */
	Map<String, Object> getInfo();

}
