package com.mmr.wordtalk.common.api.encrypt.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.InputStream;

/**
 * <p>
 * 解密信息输入流
 * </p>
 *
 * @author licoy.cn
 * @author 张恩睿
 * @version 2018/9/7
 */
@Getter
@RequiredArgsConstructor
public class DecryptHttpInputMessage implements HttpInputMessage {

	private final InputStream body;

	private final HttpHeaders headers;

}
