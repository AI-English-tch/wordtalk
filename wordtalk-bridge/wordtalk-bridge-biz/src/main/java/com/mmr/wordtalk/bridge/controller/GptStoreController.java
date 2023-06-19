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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.bridge.entity.GptStore;
import com.mmr.wordtalk.bridge.service.GptStoreService;
import com.mmr.wordtalk.bridge.vo.GptStoreVo;
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
 * 官方词库
 *
 * @author 张恩睿
 * @date 2023-06-11 20:01:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
@Tag(description = "store", name = "官方词库管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GptStoreController {

    private final GptStoreService gptStoreService;

    /**
     * 分页查询
     *
     * @param page     分页对象
     * @param vo 官方词库
     * @return
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('bridge_store_view')")
    public R getStorePage(Page page, GptStoreVo vo) {
        return R.ok(gptStoreService.queryPage(page, vo));
    }

    /**
     * 列表查询
     *
     * @param page     分页对象
     * @param vo 官方词库
     * @return
     */
    @Operation(summary = "列表查询", description = "列表查询")
    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('bridge_store_view')")
    public R getStoreList(Page page, GptStoreVo vo) {
        return R.ok(gptStoreService.queryList(page, vo));
    }


    /**
     * 通过id查询官方词库
     *
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('bridge_store_view')")
    public R getById(@PathVariable("id") Long id,GptStoreVo vo) {
        return R.ok(gptStoreService.detail(id,vo));
    }

    /**
     * 新增官方词库
     *
     * @param gptStore 官方词库
     * @return R
     */
    @Operation(summary = "新增官方词库", description = "新增官方词库")
    @SysLog("新增官方词库")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('bridge_store_add')")
    public R save(@RequestBody GptStore gptStore) {
        return R.ok(gptStoreService.save(gptStore));
    }

    /**
     * 导入单词
     *
     * @param id
     * @param wordsList
     * @return
     */
    @Operation(summary = "导入单词", description = "导入单词")
    @SysLog("导入单词")
    @PutMapping("/import/{id}")
    @PreAuthorize("@pms.hasPermission('bridge_store_edit')")
    public R importWords(@PathVariable Long id, @RequestBody List<String> wordsList) {
        return gptStoreService.importWords(id, wordsList);
    }

    /**
     * 修改官方词库
     *
     * @param gptStore 官方词库
     * @return R
     */
    @Operation(summary = "修改官方词库", description = "修改官方词库")
    @SysLog("修改官方词库")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('bridge_store_edit')")
    public R updateById(@RequestBody GptStore gptStore) {
        return R.ok(gptStoreService.updateById(gptStore));
    }

    /**
     * 通过id删除官方词库
     *
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除官方词库", description = "通过id删除官方词库")
    @SysLog("通过id删除官方词库")
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('bridge_store_del')")
    public R removeById(@RequestBody Long[] ids) {
        return R.ok(gptStoreService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     *
     * @param gptStore 查询条件
     * @return excel 文件流
     */
    @ResponseExcel
    @GetMapping("/export")
    @PreAuthorize("@pms.hasPermission('bridge_store_export')")
    public List<GptStore> export(GptStore gptStore) {
        return gptStoreService.list(Wrappers.query(gptStore));
    }
}