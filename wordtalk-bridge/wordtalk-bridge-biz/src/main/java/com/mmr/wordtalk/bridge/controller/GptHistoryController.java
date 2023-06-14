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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.bridge.entity.GptHistoryEntity;
import com.mmr.wordtalk.bridge.service.GptHistoryService;
import com.mmr.wordtalk.common.core.util.R;
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
 * 对话历史
 *
 * @author 张恩睿
 * @date 2023-06-14 11:07:37
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@Tag(description = "history", name = "对话历史管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptHistoryController {

	private final GptHistoryService gptHistoryService;

	/**
	 * 分页查询
	 *
	 * @param page       分页对象
	 * @param gptHistory 对话历史
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	// @PreAuthorize("@pms.hasPermission('chatgpt_history_view')")
	public R getgptHistoryPage(Page page, GptHistoryEntity gptHistory) {
		LambdaQueryWrapper<GptHistoryEntity> wrapper = Wrappers.lambdaQuery();
		return R.ok(gptHistoryService.page(page, wrapper));
	}


	/**
	 * 通过id查询对话历史
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('chatgpt_history_view')")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(gptHistoryService.getById(id));
	}

	/**
	 * 新增对话历史
	 *
	 * @param gptHistory 对话历史
	 * @return R
	 */
	@Operation(summary = "新增对话历史", description = "新增对话历史")
	@SysLog("新增对话历史")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_history_add')")
	public R save(@RequestBody GptHistoryEntity gptHistory) {
		return R.ok(gptHistoryService.save(gptHistory));
	}

	/**
	 * 修改对话历史
	 *
	 * @param gptHistory 对话历史
	 * @return R
	 */
	@Operation(summary = "修改对话历史", description = "修改对话历史")
	@SysLog("修改对话历史")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_history_edit')")
	public R updateById(@RequestBody GptHistoryEntity gptHistory) {
		return R.ok(gptHistoryService.updateById(gptHistory));
	}

	/**
	 * 通过id删除对话历史
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除对话历史", description = "通过id删除对话历史")
	@SysLog("通过id删除对话历史")
	@DeleteMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_history_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(gptHistoryService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param gptHistory 查询条件
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	// @PreAuthorize("@pms.hasPermission('chatgpt_history_export')")
	public List<GptHistoryEntity> export(GptHistoryEntity gptHistory) {
		return gptHistoryService.list(Wrappers.query(gptHistory));
	}
}