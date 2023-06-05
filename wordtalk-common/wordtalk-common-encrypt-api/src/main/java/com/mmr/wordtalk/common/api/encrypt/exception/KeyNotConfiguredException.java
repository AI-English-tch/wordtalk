package com.mmr.wordtalk.common.api.encrypt.exception;

/**
 * <p>
 * 未配置KEY运行时异常
 * </p>
 *
 * @author licoy.cn
 * @author 张恩睿
 * @version 2018/9/6
 */
public class KeyNotConfiguredException extends RuntimeException {

	public KeyNotConfiguredException(String message) {
		super(message);
	}

}
