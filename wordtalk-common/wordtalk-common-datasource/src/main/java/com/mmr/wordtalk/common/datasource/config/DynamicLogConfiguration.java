package com.mmr.wordtalk.common.datasource.config;

import com.mmr.wordtalk.common.core.factory.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 张恩睿
 * @date 2022/8/8
 *
 * 注入SQL 格式化的插件
 */
@ConditionalOnClass(name = "com.mmr.wordtalk.common.data.mybatis.DruidSqlLogFilter")
@PropertySource(value = "classpath:dynamic-ds-log.yaml", factory = YamlPropertySourceFactory.class)
public class DynamicLogConfiguration {

}
