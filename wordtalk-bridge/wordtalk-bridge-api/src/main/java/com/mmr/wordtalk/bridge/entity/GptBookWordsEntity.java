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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 词书--单词关联表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:06:27
 */
@Data
@TableName("gpt_book_words")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "词书--单词关联表")
public class GptBookWordsEntity extends Model<GptBookWordsEntity> {

    private static final long serialVersionUID = 1L;


    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "唯一标识")
    private Long id;

    /**
     * 词书id
     */
    @Schema(description = "词书id")
    private Long bookId;

    /**
     * 单词id
     */
    @Schema(description = "单词id")
    private Long wordId;

    /**
     * 熟练度
     */
    @Schema(description = "熟练度")
    private BigDecimal score;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}