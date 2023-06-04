package com.mmr.wordtalk.common.security.feign;

import cn.hutool.core.util.StrUtil;
import com.mmr.wordtalk.common.core.constant.SecurityConstants;
import com.mmr.wordtalk.common.core.util.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * TOC 客户标识传递
 *
 * @author wordtalk
 * @date 2023/3/17
 */
@Slf4j
public class WordtalkClientToCRequestInterceptor implements RequestInterceptor {

	/**
	 * Called for every request. Add data using methods on the supplied
	 * {@link RequestTemplate}.
	 * @param template
	 */
	public void apply(RequestTemplate template) {
		String reqVersion = WebUtils.getRequest() != null
				? WebUtils.getRequest().getHeader(SecurityConstants.HEADER_TOC) : null;

		if (StrUtil.isNotBlank(reqVersion)) {
			log.debug("feign  add header toc :{}", reqVersion);
			template.header(SecurityConstants.HEADER_TOC, reqVersion);
		}
	}

}
