package com.mmr.wordtalk.common.api.encrypt.core;

import com.mmr.wordtalk.common.api.encrypt.enums.EncryptType;

import javax.servlet.http.HttpServletRequest;

/**
 * 解析加密密钥
 *
 * @author 张恩睿
 */
public interface ISecretKeyResolver {

	/**
	 * 获取租户对应的加、解密密钥
	 * @param request HttpServletRequest
	 * @param encryptType 加解密类型
	 * @return 密钥
	 */
	String getSecretKey(HttpServletRequest request, EncryptType encryptType);

}
