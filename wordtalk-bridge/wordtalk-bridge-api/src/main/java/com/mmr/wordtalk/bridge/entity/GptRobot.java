package com.mmr.wordtalk.bridge.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.mmr.wordtalk.ai.bo.ChatGptModelParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 对话助手
 *
 * @author 张恩睿
 * @date 2023-07-04 00:44:59
 */
@Data
@TableName(value = "gpt_robot", autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "对话助手")
public class GptRobot extends Model<GptRobot> {

	/**
	 * 唯一标识
	 */
	@TableId(type = IdType.AUTO)
	@Schema(description = "唯一标识")
	private Long id;

	/**
	 * 助手名称
	 */
	@Schema(description = "助手名称")
	private String name;

	/**
	 * master | servant
	 */
	@Schema(description = "使用的系统")
	private String system;

	/**
	 * 提词内容
	 */
	@Schema(description = "提词内容")
	private String prompt;

	/**
	 * 关联的模型ID
	 */
	@Schema(description = "关联的模型ID")
	private Long model_id;

	/**
	 * 模型的微调参数
	 */
	@Schema(description = "模型的微调参数")
	@TableField(typeHandler = FastjsonTypeHandler.class)
	private ChatGptModelParams modelParams;

	/**
	 * 上下文大小
	 */
	@Schema(description = "上下文大小")
	private Integer contextSize;

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
