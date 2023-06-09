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

package com.mmr.wordtalk.app.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mmr.wordtalk.app.api.dto.AppUserDTO;
import com.mmr.wordtalk.app.api.entity.AppUser;
import com.mmr.wordtalk.app.api.vo.AppUserExcelVO;
import com.mmr.wordtalk.app.service.AppUserService;
import com.mmr.wordtalk.common.core.constant.CommonConstants;
import com.mmr.wordtalk.common.core.exception.ErrorCodes;
import com.mmr.wordtalk.common.core.util.MsgUtils;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.excel.annotation.RequestExcel;
import com.mmr.wordtalk.common.excel.annotation.ResponseExcel;
import com.mmr.wordtalk.common.log.annotation.SysLog;
import com.mmr.wordtalk.common.security.annotation.Inner;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * app用户表
 *
 * @author 张恩睿
 * @date 2022-12-07 09:52:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/appuser")
@Tag(description = "appuser", name = "app用户表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AppUserController {

	private final AppUserService appUserService;

	@Inner
	@GetMapping("/info/{username}")
	public R info(@PathVariable String username) {
		AppUser user = appUserService.getOne(Wrappers.<AppUser>query().lambda().eq(AppUser::getUsername, username));
		if (user == null) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.APP_USER_USERINFO_EMPTY, username));
		}
		return R.ok(appUserService.findUserInfo(user));
	}

	/**
	 * 获取当前用户全部信息
	 * @return 用户信息
	 */
	@GetMapping(value = { "/info" })
	public R info() {
		String username = SecurityUtils.getUser().getUsername();
		AppUser user = appUserService.getOne(Wrappers.<AppUser>query().lambda().eq(AppUser::getUsername, username));
		if (user == null) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR));
		}
		return R.ok(appUserService.findUserInfo(user));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param appUserDTO app用户表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	public R getAppUserPage(Page page, AppUserDTO appUserDTO) {
		return R.ok(appUserService.getUsersWithRolePage(page, appUserDTO));
	}

	/**
	 * 通过id查询app用户表
	 * @param userId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/details/{userId}")
	public R getById(@PathVariable("userId") Long userId) {
		return R.ok(appUserService.selectUserVoById(userId));
	}

	/**
	 * @return R
	 */
	@Operation(summary = "通过userName查询", description = "通过userName查询")
	@GetMapping("/details")
	public R getByUserName(AppUser user) {
		AppUser one = appUserService.getOne(Wrappers.query(user), false);
		return R.ok(one == null ? null : CommonConstants.SUCCESS);
	}

	/**
	 * 新增app用户表
	 * @param appUser app用户表
	 * @return R
	 */
	@Operation(summary = "新增app用户表", description = "新增app用户表")
	@SysLog("新增app用户表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('app_appuser_add')")
	public R save(@RequestBody AppUserDTO appUser) {
		appUserService.saveUser(appUser);
		return R.ok();
	}

	/**
	 * 修改app用户表
	 * @param appUser app用户表
	 * @return R
	 */
	@Operation(summary = "修改app用户表", description = "修改app用户表")
	@SysLog("修改app用户表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('app_appuser_edit')")
	public R updateById(@RequestBody AppUserDTO appUser) {
		return R.ok(appUserService.updateUser(appUser));
	}

	/**
	 * 通过id删除app用户表
	 * @param ids userIds
	 * @return R
	 */
	@Operation(summary = "通过ids删除app用户表", description = "通过ids删除app用户表")
	@SysLog("通过id删除app用户表")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('app_appuser_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(appUserService.deleteAppUserByIds(ids));
	}

	/**
	 * 导出excel 表格
	 * @param appUser 查询条件
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('app_appuser_export')")
	public List<AppUserExcelVO> export(AppUserDTO appUser) {
		return appUserService.listUser(appUser);
	}

	@SysLog("修改个人信息")
	@PutMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody AppUserDTO userDto) {
		return appUserService.updateUserInfo(userDto);
	}

	/**
	 * 导入用户
	 * @param excelVOList 用户列表
	 * @param bindingResult 错误信息列表
	 * @return R
	 */
	@PostMapping("/import")
	@PreAuthorize("@pms.hasPermission('app_appuser_export')")
	public R importUser(@RequestExcel List<AppUserExcelVO> excelVOList, BindingResult bindingResult) {
		return appUserService.importUser(excelVOList, bindingResult);
	}

}
