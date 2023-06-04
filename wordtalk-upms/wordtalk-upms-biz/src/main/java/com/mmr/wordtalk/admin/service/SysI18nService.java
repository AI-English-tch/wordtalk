package com.mmr.wordtalk.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmr.wordtalk.admin.api.entity.SysI18nEntity;
import com.mmr.wordtalk.common.core.util.R;

import java.util.Map;

public interface SysI18nService extends IService<SysI18nEntity> {

	Map listMap();

	/**
	 * 同步数据
	 * @return R
	 */
	R syncI18nCache();

}
