package com.mmr.wordtalk.common.ai.proxy;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 张恩睿
 * @date 2023-06-07 13:35:00
 */
@Data
@ConfigurationProperties(prefix = "proxy")
public class ProxyProperties {

	/**
	 * 是否启用代理
	 */
	private Boolean enable;

	/**
	 * 代理的ip地址
	 */
	private String host;

	/**
	 * 代理端口
	 */
	private Integer port;
}
