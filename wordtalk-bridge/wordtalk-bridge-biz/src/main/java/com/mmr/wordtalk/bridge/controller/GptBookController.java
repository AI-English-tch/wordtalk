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
import com.mmr.wordtalk.bridge.entity.GptBookEntity;
import com.mmr.wordtalk.bridge.service.GptBookService;
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
 * 用户自己的词书
 *
 * @author 张恩睿
 * @date 2023-06-11 20:00:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/book" )
@Tag(description = "book" , name = "用户自己的词书管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptBookController {

    private final  GptBookService gptBookService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param gptBook 用户自己的词书
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('bridge_book_view')" )
    public R getgptBookPage(Page page, GptBookEntity gptBook) {
        LambdaQueryWrapper<GptBookEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.like(StrUtil.isNotBlank(gptBook.getName()),GptBookEntity::getName,gptBook.getName());
		wrapper.like(StrUtil.isNotBlank(gptBook.getCreateBy()),GptBookEntity::getCreateBy,gptBook.getCreateBy());
        return R.ok(gptBookService.page(page, wrapper));
    }


    /**
     * 通过id查询用户自己的词书
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('bridge_book_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(gptBookService.getById(id));
    }

    /**
     * 新增用户自己的词书
     * @param gptBook 用户自己的词书
     * @return R
     */
    @Operation(summary = "新增用户自己的词书" , description = "新增用户自己的词书" )
    @SysLog("新增用户自己的词书" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('bridge_book_add')" )
    public R save(@RequestBody GptBookEntity gptBook) {
        return R.ok(gptBookService.save(gptBook));
    }

    /**
     * 修改用户自己的词书
     * @param gptBook 用户自己的词书
     * @return R
     */
    @Operation(summary = "修改用户自己的词书" , description = "修改用户自己的词书" )
    @SysLog("修改用户自己的词书" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('bridge_book_edit')" )
    public R updateById(@RequestBody GptBookEntity gptBook) {
        return R.ok(gptBookService.updateById(gptBook));
    }

    /**
     * 通过id删除用户自己的词书
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除用户自己的词书" , description = "通过id删除用户自己的词书" )
    @SysLog("通过id删除用户自己的词书" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('bridge_book_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(gptBookService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param gptBook 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('bridge_book_export')" )
    public List<GptBookEntity> export(GptBookEntity gptBook) {
        return gptBookService.list(Wrappers.query(gptBook));
    }
}