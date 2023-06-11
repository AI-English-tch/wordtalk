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
 * chart的对话记录表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:09:18
 */
@Data
@TableName("gpt_history")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "chart的对话记录表")
public class GptHistoryEntity extends Model<GptHistoryEntity> {

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
     * 关联的话题
     */
    @Schema(description = "关联的话题")
    private Long topicId;

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

}