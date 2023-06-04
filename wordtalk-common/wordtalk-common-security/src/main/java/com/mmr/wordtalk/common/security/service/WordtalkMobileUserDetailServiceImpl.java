package com.mmr.wordtalk.common.security.service;

import com.mmr.wordtalk.admin.api.dto.UserInfo;
import com.mmr.wordtalk.admin.api.feign.RemoteUserService;
import com.mmr.wordtalk.common.core.constant.SecurityConstants;
import com.mmr.wordtalk.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author aeizzz
 */
@Slf4j
@RequiredArgsConstructor
public class WordtalkMobileUserDetailServiceImpl implements WordtalkUserDetailsService {

	private final UserDetailsService wordtalkDefaultUserDetailsServiceImpl;

	private final RemoteUserService remoteUserService;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		R<UserInfo> result = remoteUserService.social(phone, SecurityConstants.FROM_IN);
		return getUserDetails(result);
	}

	@Override
	public UserDetails loadUserByUser(WordtalkUser wordtalkUser) {
		return wordtalkDefaultUserDetailsServiceImpl.loadUserByUsername(wordtalkUser.getUsername());
	}

	/**
	 * 支持所有的 mobile 类型
	 * @param clientId 目标客户端
	 * @param grantType 授权类型
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.GRANT_MOBILE.equals(grantType);
	}

}
