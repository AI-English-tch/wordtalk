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

package com.mmr.wordtalk.bridge.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 单库--单词关联表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:04:47
 */
@Data
@TableName("gpt_store_words")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "单库--单词关联表")
public class GptStoreWordsEntity extends Model<GptStoreWordsEntity> {

    private static final long serialVersionUID = 1L;


	/**
	* 词库id
	*/
    @Schema(description="词库id")
    private Long storeId;

	/**
	* 单词id
	*/
    @Schema(description="单词id")
    private Long wordId;

	/**
	* 创建人
	*/
	@TableField(fill = FieldFill.INSERT)
    @Schema(description="创建人")
    private String createBy;

	/**
	* 创建时间
	*/
	@TableField(fill = FieldFill.INSERT)
    @Schema(description="创建时间")
    private LocalDateTime createTime;

}