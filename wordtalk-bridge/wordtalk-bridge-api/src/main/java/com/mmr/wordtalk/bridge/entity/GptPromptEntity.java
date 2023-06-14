/*
 *    Copyright (c) 2018-2025, zero All rights reserved.
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
 * Author: zero
 */

package com.mmr.wordtalk.bridge.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 题词表
 *
 * @author 张恩睿
 * @date 2023-06-14 11:10:09
 */
@Data
@TableName("gpt_prompt")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "题词表")
public class GptPromptEntity extends Model<GptPromptEntity> {

	private static final long serialVersionUID = 1L;


	/**
	 * 唯一标识
	 */
	@TableId(type = IdType.AUTO)
	@Schema(description = "唯一标识")
	private Long id;

	/**
	 * 题词名称
	 */
	@Schema(description = "题词名称")
	private String name;

	/**
	 * 题词内容
	 */
	@Schema(description = "题词内容")
	private String content;

	/**
	 * 创建者
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建者")
	private String createBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description = "更新者")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 逻辑删除
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "逻辑删除")
	private String delFlag;

	/**
	 * 租户id
	 */
	@Schema(description = "租户id")
	private Long tenantId;

}