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

package com.mmr.wordtalk.bridge.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.ai.api.feign.RemoteAiModelService;
import com.mmr.wordtalk.ai.entity.AiModel;
import com.mmr.wordtalk.bridge.entity.GptRobot;
import com.mmr.wordtalk.bridge.service.GptRobotService;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.core.util.RetOps;
import com.mmr.wordtalk.common.excel.annotation.ResponseExcel;
import com.mmr.wordtalk.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 对话助手
 *
 * @author 张恩睿
 * @date 2023-06-14 11:07:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/robot")
@Tag(description = "robot", name = "对话助手管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptRobotController {

	private final GptRobotService gptRobotService;

	private final RemoteAiModelService remoteAiModelService;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param gptRobot 助手
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	// @PreAuthorize("@pms.hasPermission('chatgpt_robot_view')")
	public R getgptRobotPage(Page page, GptRobot gptRobot) {
		LambdaQueryWrapper<GptRobot> wrapper = Wrappers.lambdaQuery(gptRobot);
		return R.ok(gptRobotService.page(page, wrapper));
	}


	/**
	 * 通过id查询
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('chatgpt_robot_view')")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(gptRobotService.getById(id));
	}

	/**
	 * 新增助手
	 *
	 * @param gptRobot 助手
	 * @return R
	 */
	@Operation(summary = "新增助手", description = "新增助手")
	@SysLog("新增对话历史")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_robot_add')")
	public R save(@RequestBody GptRobot gptRobot) {
		return R.ok(gptRobotService.save(gptRobot));
	}

	/**
	 * 修改助手
	 *
	 * @param gptRobot 助手
	 * @return R
	 */
	@Operation(summary = "修改助手", description = "修改助手")
	@SysLog("修改对话历史")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_robot_edit')")
	public R updateById(@RequestBody GptRobot gptRobot) {
		return R.ok(gptRobotService.updateById(gptRobot));
	}

	/**
	 * 通过id删除
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除对话历史")
	@DeleteMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_robot_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(gptRobotService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param gptRobot 查询条件
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	// @PreAuthorize("@pms.hasPermission('chatgpt_robot_export')")
	public List<GptRobot> export(GptRobot gptRobot) {
		return gptRobotService.list(Wrappers.query(gptRobot));
	}

	/**
	 * 查询模型列表的接口
	 *
	 * @return
	 */
	@Operation(summary = "查询模型列表的接口", description = "查询模型列表的接口")
	@GetMapping("/list/model")
	public R listModel(AiModel aiModel) {
		List<AiModel> modelList = RetOps.of(remoteAiModelService.getAiModelList(BeanUtil.beanToMap(aiModel)))
				.getData()
				.get();
		return R.ok(modelList);
	}
}