/*
 *    Copyright (c) 2018-2025, mall All rights reserved.
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
 * Author: mall
 */

package com.mmr.wordtalk.ai.entity;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mybatis.mate.annotation.FieldSensitive;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AI模型表
 *
 * @author 张恩睿
 * @date 2023-06-29 11:56:29
 */
@Data
@TableName(value = "ai_model", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "AI模型表")
public class AiModel extends Model<AiModel> {

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@Schema(description = "唯一标识")
	private Long id;

	/**
	 * 模型名称 -- 展示给用户的
	 */
	@Schema(description = "模型名称 -- 展示给用户的")
	@TableField(condition = SqlCondition.LIKE)
	private String name;

	/**
	 * 模型版本号 -- 给API看的
	 */
	@Schema(description = "模型版本号 -- 给API看的")
	private String version;

	/**
	 * 主机地址
	 */
	@Schema(description = "主机地址")
	private String host;

	/**
	 * 是否开启代理
	 */
	@Schema(description = "是否开启代理")
	private Boolean enable_proxy;

	/**
	 * 模型分类(会采用不同的调用策略)
	 */
	@Schema(description = "模型分类")
	private String type;

	/**
	 * API Key List
	 */
	@Schema(description = "API Key List")
	@TableField(typeHandler = FastjsonTypeHandler.class)
	@FieldSensitive("keyList")
	private List<String> keyList;

	/**
	 * 模型参数
	 */
	@Schema(description = "模型参数")
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private JSONObject param;

	/**
	 * 创建者
	 */
	@Schema(description = "创建者")
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

}
