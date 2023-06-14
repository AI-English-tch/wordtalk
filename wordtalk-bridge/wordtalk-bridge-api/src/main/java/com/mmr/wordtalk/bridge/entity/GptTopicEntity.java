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
 * 话题
 *
 * @author 张恩睿
 * @date 2023-06-14 11:11:15
 */
@Data
@TableName("gpt_topic")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "话题")
public class GptTopicEntity extends Model<GptTopicEntity> {

	private static final long serialVersionUID = 1L;


	/**
	 * 唯一标识
	 */
	@TableId(type = IdType.AUTO)
	@Schema(description = "唯一标识")
	private Long id;

	/**
	 * 标题
	 */
	@Schema(description = "标题")
	private String title;

	/**
	 * 关联的题词ID
	 */
	@Schema(description = "关联的题词ID")
	private Long promptId;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 对话发起人
	 */
	@TableField(fill = FieldFill.INSERT)
	@Schema(description = "对话发起人")
	private String createBy;

	/**
	 * 对话更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@Schema(description = "对话更新时间")
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