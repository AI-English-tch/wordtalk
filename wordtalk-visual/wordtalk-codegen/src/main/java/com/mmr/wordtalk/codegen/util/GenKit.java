package com.mmr.wordtalk.codegen.util;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmr.wordtalk.codegen.entity.GenDatasourceConf;
import com.mmr.wordtalk.codegen.mapper.GenDatasourceConfMapper;
import com.mmr.wordtalk.codegen.mapper.GeneratorMapper;
import com.mmr.wordtalk.common.core.util.SpringContextHolder;
import com.mmr.wordtalk.common.datasource.util.DsJdbcUrlEnum;
import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * 代码生成工具类
 *
 * @author 张恩睿
 * @date 2023/2/16
 */
@UtilityClass
public class GenKit {

	/**
	 * 获取功能名
	 * @param tableName 表名
	 * @return 功能名
	 */
	public String getFunctionName(String tableName) {
		String functionName = StrUtil.subAfter(tableName, "_", true);
		if (StrUtil.isBlank(functionName)) {
			functionName = tableName;
		}

		return functionName;
	}

	/**
	 * 获取模块名称
	 * @param packageName 包名
	 * @return 功能名
	 */
	public String getModuleName(String packageName) {
		return StrUtil.subAfter(packageName, ".", true);
	}

	/**
	 * 获取数据源对应方言的mapper
	 * @param dsName 数据源名称
	 * @return GeneratorMapper
	 */
	public GeneratorMapper getMapper(String dsName) {
		// 获取目标数据源数据库类型
		GenDatasourceConfMapper datasourceConfMapper = SpringContextHolder.getBean(GenDatasourceConfMapper.class);
		GenDatasourceConf datasourceConf = datasourceConfMapper
				.selectOne(Wrappers.<GenDatasourceConf>lambdaQuery().eq(GenDatasourceConf::getName, dsName));

		String dbConfType;
		// 默认MYSQL 数据源
		if (datasourceConf == null) {
			dbConfType = DsJdbcUrlEnum.MYSQL.getDbName();
		}
		else {
			dbConfType = datasourceConf.getDsType();
		}

		// 获取全部数据实现
		ApplicationContext context = SpringContextHolder.getApplicationContext();
		Map<String, GeneratorMapper> beansOfType = context.getBeansOfType(GeneratorMapper.class);

		// 根据数据类型选择mapper
		for (String key : beansOfType.keySet()) {
			if (StrUtil.containsIgnoreCase(key, dbConfType)) {
				return beansOfType.get(key);
			}
		}

		throw new IllegalArgumentException("dsName 不合法: " + dsName);
	}

}
