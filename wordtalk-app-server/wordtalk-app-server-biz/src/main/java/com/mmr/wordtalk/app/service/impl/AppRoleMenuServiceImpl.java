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
package com.mmr.wordtalk.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.app.api.entity.AppRoleMenu;
import com.mmr.wordtalk.app.mapper.AppRoleMenuMapper;
import com.mmr.wordtalk.app.service.AppRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色菜单表
 *
 * @author 张恩睿
 * @date 2022-12-07 09:52:03
 */
@Service
public class AppRoleMenuServiceImpl extends ServiceImpl<AppRoleMenuMapper, AppRoleMenu> implements AppRoleMenuService {

	@Override
	public Boolean saveRoleMenus(Long roleId, String menuIds) {
		this.remove(Wrappers.<AppRoleMenu>query().lambda().eq(AppRoleMenu::getRoleId, roleId));

		if (StrUtil.isBlank(menuIds)) {
			return Boolean.TRUE;
		}
		List<AppRoleMenu> roleMenuList = Arrays.stream(menuIds.split(StrUtil.COMMA)).map(menuId -> {
			AppRoleMenu roleMenu = new AppRoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(Long.valueOf(menuId));
			return roleMenu;
		}).collect(Collectors.toList());
		this.saveBatch(roleMenuList);
		return Boolean.TRUE;
	}

}
