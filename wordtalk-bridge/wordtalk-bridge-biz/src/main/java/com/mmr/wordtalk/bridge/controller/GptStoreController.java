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
import com.mmr.wordtalk.bridge.entity.GptStoreEntity;
import com.mmr.wordtalk.bridge.service.GptStoreService;
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
 * 官方词库
 *
 * @author 张恩睿
 * @date 2023-06-11 20:01:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/store" )
@Tag(description = "store" , name = "官方词库管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptStoreController {

    private final  GptStoreService gptStoreService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param gptStore 官方词库
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('bridge_store_view')" )
    public R getgptStorePage(Page page, GptStoreEntity gptStore) {
        LambdaQueryWrapper<GptStoreEntity> wrapper = Wrappers.lambdaQuery();
		wrapper.like(StrUtil.isNotBlank(gptStore.getName()),GptStoreEntity::getName,gptStore.getName());
        return R.ok(gptStoreService.page(page, wrapper));
    }


    /**
     * 通过id查询官方词库
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('bridge_store_view')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(gptStoreService.getById(id));
    }

    /**
     * 新增官方词库
     * @param gptStore 官方词库
     * @return R
     */
    @Operation(summary = "新增官方词库" , description = "新增官方词库" )
    @SysLog("新增官方词库" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('bridge_store_add')" )
    public R save(@RequestBody GptStoreEntity gptStore) {
        return R.ok(gptStoreService.save(gptStore));
    }

    /**
     * 修改官方词库
     * @param gptStore 官方词库
     * @return R
     */
    @Operation(summary = "修改官方词库" , description = "修改官方词库" )
    @SysLog("修改官方词库" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('bridge_store_edit')" )
    public R updateById(@RequestBody GptStoreEntity gptStore) {
        return R.ok(gptStoreService.updateById(gptStore));
    }

    /**
     * 通过id删除官方词库
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除官方词库" , description = "通过id删除官方词库" )
    @SysLog("通过id删除官方词库" )
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('bridge_store_del')" )
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(gptStoreService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param gptStore 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('bridge_store_export')" )
    public List<GptStoreEntity> export(GptStoreEntity gptStore) {
        return gptStoreService.list(Wrappers.query(gptStore));
    }
}