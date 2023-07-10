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
 * 对话历史
 *
 * @author 张恩睿
 * @date 2023-06-14 11:07:37
 */
@Data
@TableName("gpt_history")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对话历史")
public class GptHistory extends Model<GptHistory> {

    private static final long serialVersionUID = 1L;


    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "唯一标识")
    private Long id;

    /**
     * 对话角色
     */
    @Schema(description = "对话角色")
    private String role;

    /**
     * 对话内容
     */
    @Schema(description = "对话内容")
    private String content;

    /**
     * 关联的词书ID
     */
    @Schema(description = "关联的词书ID")
    private Long bookId;

    /**
     * 关联的助手ID
     */
    @Schema(description = "关联的助手系统区")
    @TableField("`system`")
    private String system;

    /**
     * 关联的单词信息
     */
    @Schema(description = "关联的单词信息")
    private String word;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建者")
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新者")
    private String updateBy;


}