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
 * 官方词库
 *
 * @author 张恩睿
 * @date 2023-06-11 20:01:48
 */
@Data
@TableName("gpt_store")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "官方词库")
public class GptStore extends Model<GptStore> {

    private static final long serialVersionUID = 1L;


    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "唯一标识")
    private Long id;

    /**
     * 词库名称
     */
    @Schema(description = "词库名称")
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    /**
     * 词库简介
     */
    @Schema(description = "词库简介")
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 词库包含单词数
     */
    @Schema(description = "词库包含单词数")
    private Long wordNum;

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

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新人")
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
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @Schema(description = "逻辑删除")
    private String delFlag;

}