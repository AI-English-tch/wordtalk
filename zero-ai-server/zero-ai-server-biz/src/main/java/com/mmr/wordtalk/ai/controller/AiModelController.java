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

package com.mmr.wordtalk.ai.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.ai.dto.SendDto;
import com.mmr.wordtalk.ai.entity.AiModel;
import com.mmr.wordtalk.ai.service.AiModelService;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.excel.annotation.ResponseExcel;
import com.mmr.wordtalk.common.log.annotation.SysLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI模型表
 *
 * @author 张恩睿
 * @date 2023-06-29 11:56:29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai-model")
@Tag(description = "ai-model", name = "AI模型表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AiModelController {
	private final AiModelService aiModelService;

	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param aiModel AI模型表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	// @PreAuthorize("@pms.hasPermission('ai_aimodel_view')")
	public R getAiModelPage(Page page, AiModel aiModel) {
		return R.ok(aiModelService.page(page, Wrappers.query(aiModel)));
	}

	/**
	 * 列表查询
	 *
	 * @param aiModel AI模型表
	 * @return
	 */
	@Operation(summary = "列表查询", description = "列表查询")
	@GetMapping("/list")
	// @PreAuthorize("@pms.hasPermission('ai_aimodel_view')")
	public R getAiModelList(AiModel aiModel) {
		return R.ok(aiModelService.list(Wrappers.query(aiModel)));
	}


	/**
	 * 通过id查询AI模型表
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('ai_aimodel_view')")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(aiModelService.getById(id));
	}

	/**
	 * 新增AI模型表
	 *
	 * @param aiModel AI模型表
	 * @return R
	 */
	@Operation(summary = "新增AI模型表", description = "新增AI模型表")
	@SysLog("新增AI模型表")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('ai_aimodel_add')")
	public R save(@RequestBody AiModel aiModel) {
		return R.ok(aiModelService.save(aiModel));
	}

	/**
	 * 修改AI模型表
	 *
	 * @param aiModel AI模型表
	 * @return R
	 */
	@Operation(summary = "修改AI模型表", description = "修改AI模型表")
	@SysLog("修改AI模型表")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('ai_aimodel_edit')")
	public R updateById(@RequestBody AiModel aiModel) {
		return R.ok(aiModelService.updateById(aiModel));
	}

	/**
	 * 通过id删除AI模型表
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id删除AI模型表", description = "通过id删除AI模型表")
	@SysLog("通过id删除AI模型表")
	@DeleteMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('ai_aimodel_del')")
	public R removeById(@PathVariable Long id) {
		return R.ok(aiModelService.removeById(id));
	}

	/**
	 * 线性向AI发送消息
	 *
	 * @param id
	 * @param sendDto
	 * @return
	 */
	@Operation(summary = "线性向AI发送消息", description = "线性向AI发送消息")
	@PostMapping("/sendOnOnce/{id}")
	public R sendOnOnce(@PathVariable("id") Long id, @RequestBody SendDto sendDto) {
		return R.ok(aiModelService.sendOnOnce(id, sendDto));
	}

	/**
	 * 流式向AI发送消息
	 *
	 * @param id
	 * @param sendDto
	 * @return
	 */
	@Operation(summary = "流式向AI发送消息", description = "流式向AI发送消息")
	@PostMapping("/sendOnStream/{id}")
	public R sendOnStream(@PathVariable("id") Long id, @RequestBody SendDto sendDto) {
		return R.ok(aiModelService.sendOnStream(id, sendDto));
	}

	/**
	 * 导出excel 表格
	 *
	 * @param aiModel 查询条件
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('ai_aimodel_export')")
	public List<AiModel> export(AiModel aiModel) {
		return aiModelService.list(Wrappers.query(aiModel));
	}
}
