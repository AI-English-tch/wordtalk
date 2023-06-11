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
import com.mmr.wordtalk.bridge.entity.GptTopicEntity;
import com.mmr.wordtalk.bridge.service.GptTopicService;
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
 * 聊天话题表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:07:26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/topic" )
@Tag(description = "topic" , name = "聊天话题表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptTopicController {

    private final  GptTopicService gptTopicService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param gptTopic 聊天话题表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('bridge_topic_view')" )
    public R getgptTopicPage(Page page, GptTopicEntity gptTopic) {
        LambdaQueryWrapper<GptTopicEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.like(StrUtil.isNotBlank(gptTopic.getTitle()),GptTopicEntity::getTitle,gptTopic.getTitle());
		wrapper.like(StrUtil.isNotBlank(gptTopic.getCreateBy()),GptTopicEntity::getCreateBy,gptTopic.getCreateBy());
        return R.ok(gptTopicService.page(page, wrapper));
    }


    /**
     * 通过id查询聊天话题表
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('bridge_topic_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(gptTopicService.getById(id));
    }

    /**
     * 新增聊天话题表
     * @param gptTopic 聊天话题表
     * @return R
     */
    @Operation(summary = "新增聊天话题表" , description = "新增聊天话题表" )
    @SysLog("新增聊天话题表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('bridge_topic_add')" )
    public R save(@RequestBody GptTopicEntity gptTopic) {
        return R.ok(gptTopicService.save(gptTopic));
    }

    /**
     * 修改聊天话题表
     * @param gptTopic 聊天话题表
     * @return R
     */
    @Operation(summary = "修改聊天话题表" , description = "修改聊天话题表" )
    @SysLog("修改聊天话题表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('bridge_topic_edit')" )
    public R updateById(@RequestBody GptTopicEntity gptTopic) {
        return R.ok(gptTopicService.updateById(gptTopic));
    }

    /**
     * 通过id删除聊天话题表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除聊天话题表" , description = "通过id删除聊天话题表" )
    @SysLog("通过id删除聊天话题表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('bridge_topic_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(gptTopicService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param gptTopic 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('bridge_topic_export')" )
    public List<GptTopicEntity> export(GptTopicEntity gptTopic) {
        return gptTopicService.list(Wrappers.query(gptTopic));
    }
}