package com.mmr.wordtalk.ai.config;

import com.plexpt.chatgpt.util.Proxys;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Proxy;

/**
 * @author 张恩睿
 * @date 2023-06-28 15:14:00
 */
@Configuration
@Data
public class ProxyConfiguration {

	/**
	 * 代理的ip地址
	 */
	@Value("${ai.proxy.host}")
	private String host = "127.0.0.1";

	/**
	 * 代理端口
	 */
	@Value("${ai.proxy.port}")
	private Integer port = 7890;

	@Bean
	@ConditionalOnMissingBean(Proxy.class)
	@ConditionalOnProperty(name = "ai.proxy.enable", havingValue = "true", matchIfMissing = true)
	public Proxy globalProxy() {
		return Proxys.http(host, port);
	}
}
