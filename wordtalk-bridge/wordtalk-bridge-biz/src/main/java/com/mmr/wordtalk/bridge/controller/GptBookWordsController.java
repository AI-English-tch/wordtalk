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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.log.annotation.SysLog;
import com.mmr.wordtalk.bridge.entity.GptBookWordsEntity;
import com.mmr.wordtalk.bridge.service.GptBookWordsService;
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
 * 词书--单词关联表
 *
 * @author 张恩睿
 * @date 2023-06-11 20:06:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/book-words" )
@Tag(description = "book-words" , name = "词书--单词关联表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptBookWordsController {

    private final  GptBookWordsService gptBookWordsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param gptBookWords 词书--单词关联表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('bridge_words_view')" )
    public R getgptBookWordsPage(Page page, GptBookWordsEntity gptBookWords) {
        LambdaQueryWrapper<GptBookWordsEntity> wrapper = Wrappers.lambdaQuery();
        return R.ok(gptBookWordsService.page(page, wrapper));
    }


    /**
     * 通过id查询词书--单词关联表
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('bridge_words_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(gptBookWordsService.getById(id));
    }

    /**
     * 新增词书--单词关联表
     * @param gptBookWords 词书--单词关联表
     * @return R
     */
    @Operation(summary = "新增词书--单词关联表" , description = "新增词书--单词关联表" )
    @SysLog("新增词书--单词关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('bridge_words_add')" )
    public R save(@RequestBody GptBookWordsEntity gptBookWords) {
        return R.ok(gptBookWordsService.save(gptBookWords));
    }

    /**
     * 修改词书--单词关联表
     * @param gptBookWords 词书--单词关联表
     * @return R
     */
    @Operation(summary = "修改词书--单词关联表" , description = "修改词书--单词关联表" )
    @SysLog("修改词书--单词关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('bridge_words_edit')" )
    public R updateById(@RequestBody GptBookWordsEntity gptBookWords) {
        return R.ok(gptBookWordsService.updateById(gptBookWords));
    }

    /**
     * 通过id删除词书--单词关联表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除词书--单词关联表" , description = "通过id删除词书--单词关联表" )
    @SysLog("通过id删除词书--单词关联表" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('bridge_words_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(gptBookWordsService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param gptBookWords 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('bridge_words_export')" )
    public List<GptBookWordsEntity> export(GptBookWordsEntity gptBookWords) {
        return gptBookWordsService.list(Wrappers.query(gptBookWords));
    }
}