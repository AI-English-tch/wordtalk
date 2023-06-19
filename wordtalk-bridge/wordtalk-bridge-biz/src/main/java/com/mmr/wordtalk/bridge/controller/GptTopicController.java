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
import com.mmr.wordtalk.bridge.entity.GptTopic;
import com.mmr.wordtalk.bridge.service.GptTopicService;
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
 * 话题
 *
 * @author 张恩睿
 * @date 2023-06-14 11:11:15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/topic")
@Tag(description = "topic", name = "话题管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptTopicController {

	private final GptTopicService gptTopicService;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param gptTopic 话题
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_view')")
	public R getGptTopicPage(Page page, GptTopic gptTopic) {
		LambdaQueryWrapper<GptTopic> wrapper = Wrappers.lambdaQuery();
		return R.ok(gptTopicService.page(page, wrapper));
	}

	/**
	 * 列表查询
	 *
	 * @param gptTopic
	 * @return
	 */
	@Operation(summary = "列表查询", description = "列表查询")
	@GetMapping("/list")
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_view')")
	public R getGptTopicList(GptTopic gptTopic) {
		LambdaQueryWrapper<GptTopic> wrapper = Wrappers.lambdaQuery();
		return R.ok(gptTopicService.list(wrapper));
	}


	/**
	 * 通过id查询话题
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_view')")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(gptTopicService.getById(id));
	}

	/**
	 * 新增话题
	 *
	 * @param gptTopic 话题
	 * @return R
	 */
	@Operation(summary = "新增话题", description = "新增话题")
	@SysLog("新增话题")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_add')")
	public R save(@RequestBody GptTopic gptTopic) {
		return R.ok(gptTopicService.save(gptTopic));
	}

	/**
	 * 修改话题
	 *
	 * @param gptTopic 话题
	 * @return R
	 */
	@Operation(summary = "修改话题", description = "修改话题")
	@SysLog("修改话题")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_edit')")
	public R updateById(@RequestBody GptTopic gptTopic) {
		return R.ok(gptTopicService.updateById(gptTopic));
	}

	/**
	 * 通过id删除话题
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除话题", description = "通过id删除话题")
	@SysLog("通过id删除话题")
	@DeleteMapping
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(gptTopicService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param gptTopic 查询条件
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	// @PreAuthorize("@pms.hasPermission('chatgpt_topic_export')")
	public List<GptTopic> export(GptTopic gptTopic) {
		return gptTopicService.list(Wrappers.query(gptTopic));
	}
}