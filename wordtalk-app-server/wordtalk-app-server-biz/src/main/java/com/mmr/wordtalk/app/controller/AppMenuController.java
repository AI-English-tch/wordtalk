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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mmr.wordtalk.app.api.entity.AppMenu;
import com.mmr.wordtalk.app.service.AppMenuService;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.log.annotation.SysLog;
import com.mmr.wordtalk.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限表
 *
 * @author 张恩睿
 * @date 2022-12-07 09:52:03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/appmenu")
@Tag(description = "appmenu", name = "菜单权限表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AppMenuController {

	private final AppMenuService appMenuService;

	/**
	 * 返回当前用户的树形菜单集合
	 * @param type 类型
	 * @param parentId 父节点ID
	 * @return 当前用户的树形菜单
	 */
	@GetMapping
	public R getUserMenu(String type, Long parentId) {

		// 获取符合条件的菜单
		Set<AppMenu> all = new HashSet<>();
		SecurityUtils.getRoles().forEach(roleId -> all.addAll(appMenuService.findMenuByRoleId(roleId)));
		return R.ok(appMenuService.filterMenu(all, type, parentId));
	}

	/**
	 * 返回全部树形菜单集合
	 * @param parentId 父节点ID
	 * @param menuName 菜单名称
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public R getTree(Long parentId, String menuName) {
		return R.ok(appMenuService.treeMenu(parentId, menuName));
	}

	/**
	 * 返回角色的菜单集合授权
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/{roleId}")
	public R getRoleTree(@PathVariable Long roleId) {
		return R.ok(
				appMenuService.findMenuByRoleId(roleId).stream().map(AppMenu::getMenuId).collect(Collectors.toList()));
	}

	/**
	 * 通过ID查询菜单的详细信息
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable Long id) {
		return R.ok(appMenuService.getById(id));
	}

	/**
	 * 通过permission查询app菜单表
	 * @param permission permission
	 * @return R
	 */
	@Operation(summary = "通过permission查询", description = "通过permission查询")
	@GetMapping("/details/{permission}")
	public R getByMenuName(@PathVariable("permission") String permission) {
		return R.ok(appMenuService.getOne(Wrappers.<AppMenu>lambdaQuery().eq(AppMenu::getPermission, permission)));
	}

	/**
	 * 通过name查询app菜单表
	 * @param name name
	 * @return R
	 */
	@Operation(summary = "通过name查询", description = "通过name查询")
	@GetMapping("/detailsByName/{name}")
	public R getByMenuByName(@PathVariable("name") String name) {
		return R.ok(appMenuService.getOne(Wrappers.<AppMenu>lambdaQuery().eq(AppMenu::getName, name)));
	}

	/**
	 * 新增菜单
	 * @param appMenu 菜单信息
	 * @return success/false
	 */
	@SysLog("新增菜单")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('app_appmenu_add')")
	public R save(@Valid @RequestBody AppMenu appMenu) {
		appMenuService.save(appMenu);
		return R.ok(appMenu);
	}

	/**
	 * 删除菜单
	 * @param ids 菜单ID
	 * @return success/false
	 */
	@SysLog("删除菜单")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('app_appmenu_del')")
	public R removeById(@RequestBody Long[] ids) {
		appMenuService.removeBatchByIds(CollUtil.toList(ids));
		return R.ok();
	}

	/**
	 * 更新菜单
	 * @param appMenu
	 * @return
	 */
	@SysLog("更新菜单")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('app_appmenu_edit')")
	public R update(@Valid @RequestBody AppMenu appMenu) {
		return R.ok(appMenuService.updateMenuById(appMenu));
	}

}
