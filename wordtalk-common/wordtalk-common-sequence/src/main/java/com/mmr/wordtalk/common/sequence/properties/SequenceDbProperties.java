package com.mmr.wordtalk.common.sequence.properties;

/**
 * @author 张恩睿
 * @date 2019-05-26
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 张恩睿
 * @date 2019/5/26
 * <p>
 * 发号器DB配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "wordtalk.xsequence.db")
public class SequenceDbProperties extends BaseSequenceProperties {

	/**
	 * 表名称
	 */
	private String tableName = "wordtalk_sequence";

	/**
	 * 重试次数
	 */
	private int retryTimes = 1;

}