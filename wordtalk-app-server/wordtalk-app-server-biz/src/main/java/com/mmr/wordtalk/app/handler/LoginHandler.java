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

package com.mmr.wordtalk.app.handler;

import com.mmr.wordtalk.app.api.dto.AppUserInfo;
import com.mmr.wordtalk.app.api.entity.AppUser;

/**
 * @author 张恩睿
 * @date 2018/11/18
 * <p>
 * 登录处理器
 */
public interface LoginHandler {

	/***
	 * 数据合法性校验
	 * @param loginStr 通过用户传入获取唯一标识
	 * @return
	 */
	Boolean check(String loginStr);

	/**
	 * 通过用户传入获取唯一标识
	 * @param loginStr
	 * @return
	 */
	String identify(String loginStr);

	/**
	 * 通过openId 获取用户信息
	 * @param identify
	 * @return
	 */
	AppUserInfo info(String identify);

	/**
	 * 处理方法
	 * @param loginStr 登录参数
	 * @return
	 */
	AppUserInfo handle(String loginStr);

	/**
	 * 绑定逻辑
	 * @param user 用户实体
	 * @param identify 渠道返回唯一标识
	 * @return
	 */
	default Boolean bind(AppUser user, String identify) {
		return false;
	}

}
