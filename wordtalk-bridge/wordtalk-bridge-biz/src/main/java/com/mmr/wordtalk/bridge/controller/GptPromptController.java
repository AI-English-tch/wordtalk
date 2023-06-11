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

package com.mmr.wordtalk.bridge.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.log.annotation.SysLog;
import com.mmr.wordtalk.bridge.entity.GptPromptEntity;
import com.mmr.wordtalk.bridge.service.GptPromptService;
import org.springframework.security.access.prepost.PreAuthorize;
import com.mmr.wordtalk.common.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * gpt的prompt信息表
 *
 * @author 张恩睿
 * @date 2023-06-11 19:56:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/prompt" )
@Tag(description = "prompt" , name = "gpt的prompt信息表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptPromptController {

    private final  GptPromptService gptPromptService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param gptPrompt gpt的prompt信息表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('bridge_prompt_view')" )
    public R getgptPromptPage(Page page, GptPromptEntity gptPrompt) {
        LambdaQueryWrapper<GptPromptEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.like(StrUtil.isNotBlank(gptPrompt.getName()),GptPromptEntity::getName,gptPrompt.getName());
        return R.ok(gptPromptService.page(page, wrapper));
    }


    /**
     * 通过id查询gpt的prompt信息表
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('bridge_prompt_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(gptPromptService.getById(id));
    }

    /**
     * 新增gpt的prompt信息表
     * @param gptPrompt gpt的prompt信息表
     * @return R
     */
    @Operation(summary = "新增gpt的prompt信息表" , description = "新增gpt的prompt信息表" )
    @SysLog("新增gpt的prompt信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('bridge_prompt_add')" )
    public R save(@RequestBody GptPromptEntity gptPrompt) {
        return R.ok(gptPromptService.save(gptPrompt));
    }

    /**
     * 修改gpt的prompt信息表
     * @param gptPrompt gpt的prompt信息表
     * @return R
     */
    @Operation(summary = "修改gpt的prompt信息表" , description = "修改gpt的prompt信息表" )
    @SysLog("修改gpt的prompt信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('bridge_prompt_edit')" )
    public R updateById(@RequestBody GptPromptEntity gptPrompt) {
        return R.ok(gptPromptService.updateById(gptPrompt));
    }

    /**
     * 通过id删除gpt的prompt信息表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除gpt的prompt信息表" , description = "通过id删除gpt的prompt信息表" )
    @SysLog("通过id删除gpt的prompt信息表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('bridge_prompt_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(gptPromptService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param gptPrompt 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('bridge_prompt_export')" )
    public List<GptPromptEntity> export(GptPromptEntity gptPrompt) {
        return gptPromptService.list(Wrappers.query(gptPrompt));
    }
}