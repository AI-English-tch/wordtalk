/*
 *    Copyright (c) 2018-2025, wordtalk All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: wordtalk
 */

package com.mmr.wordtalk.common.data.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.mmr.wordtalk.admin.api.feign.RemoteDataScopeService;
import com.mmr.wordtalk.common.data.config.WordtalkMybatisProperties;
import com.mmr.wordtalk.common.data.datascope.DataScopeInnerInterceptor;
import com.mmr.wordtalk.common.data.datascope.DataScopeInterceptor;
import com.mmr.wordtalk.common.data.datascope.DataScopeSqlInjector;
import com.mmr.wordtalk.common.data.datascope.WordtalkDefaultDatascopeHandle;
import com.mmr.wordtalk.common.data.resolver.SqlFilterArgumentResolver;
import com.mmr.wordtalk.common.data.tenant.WordtalkTenantConfigProperties;
import com.mmr.wordtalk.common.data.tenant.WordtalkTenantHandler;
import com.mmr.wordtalk.common.security.service.WordtalkUser;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

/**
 * @author wordtalk
 * @date 2020-02-08
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(WordtalkMybatisProperties.class)
public class MybatisPlusConfiguration implements WebMvcConfigurer {

	/**
	 * 增加请求参数解析器，对请求中的参数注入SQL 检查
	 * @param resolverList
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
		resolverList.add(new SqlFilterArgumentResolver());
	}

	/**
	 * mybatis plus 拦截器配置
	 * @return WordtalkDefaultDatascopeHandle
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(TenantLineInnerInterceptor tenantLineInnerInterceptor,
			DataScopeInterceptor dataScopeInterceptor) {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 注入多租户支持
		interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		// 数据权限
		interceptor.addInnerInterceptor(dataScopeInterceptor);
		// 分页支持
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		paginationInnerInterceptor.setMaxLimit(1000L);
		interceptor.addInnerInterceptor(paginationInnerInterceptor);
		return interceptor;
	}

	/**
	 * 创建租户维护处理器对象
	 * @return 处理后的租户维护处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public TenantLineInnerInterceptor tenantLineInnerInterceptor(WordtalkTenantConfigProperties tenantConfigProperties) {
		TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
		tenantLineInnerInterceptor.setTenantLineHandler(new WordtalkTenantHandler(tenantConfigProperties));
		return tenantLineInnerInterceptor;
	}

	/**
	 * 数据权限拦截器
	 * @return DataScopeInterceptor
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(WordtalkUser.class)
	public DataScopeInterceptor dataScopeInterceptor(RemoteDataScopeService dataScopeService) {
		DataScopeInnerInterceptor dataScopeInnerInterceptor = new DataScopeInnerInterceptor();
		dataScopeInnerInterceptor.setDataScopeHandle(new WordtalkDefaultDatascopeHandle(dataScopeService));
		return dataScopeInnerInterceptor;
	}

	/**
	 * 扩展 mybatis-plus baseMapper 支持数据权限
	 * @return
	 */
	@Bean
	@ConditionalOnBean(DataScopeInterceptor.class)
	public DataScopeSqlInjector dataScopeSqlInjector() {
		return new DataScopeSqlInjector();
	}

	/**
	 * SQL 日志格式化
	 * @return DruidSqlLogFilter
	 */
	@Bean
	public DruidSqlLogFilter sqlLogFilter(WordtalkMybatisProperties properties) {
		return new DruidSqlLogFilter(properties);
	}

	/**
	 * 审计字段自动填充
	 * @return {@link MetaObjectHandler}
	 */
	@Bean
	public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
		return new MybatisPlusMetaObjectHandler();
	}

	/**
	 * 数据库方言配置
	 * @return
	 */
	@Bean
	public DatabaseIdProvider databaseIdProvider() {
		VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
		Properties properties = new Properties();
		properties.setProperty("SQL Server", "mssql");
		databaseIdProvider.setProperties(properties);
		return databaseIdProvider;
	}

}
