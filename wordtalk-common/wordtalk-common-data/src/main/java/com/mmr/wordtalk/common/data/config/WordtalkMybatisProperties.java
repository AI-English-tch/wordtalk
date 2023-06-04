package com.mmr.wordtalk.common.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Mybatis 配置
 *
 * @author wordtalk
 * @date 2021/6/3
 */
@Data
@RefreshScope
@ConfigurationProperties("wordtalk.mybatis")
public class WordtalkMybatisProperties {

	/**
	 * 是否打印可执行 sql
	 */
	private boolean showSql = true;

}
