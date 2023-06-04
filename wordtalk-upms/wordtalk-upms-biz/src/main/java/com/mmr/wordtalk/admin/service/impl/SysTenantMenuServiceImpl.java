package com.mmr.wordtalk.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.admin.api.entity.SysTenantMenu;
import com.mmr.wordtalk.admin.mapper.SysTenantMenuMapper;
import com.mmr.wordtalk.admin.service.SysTenantMenuService;
import org.springframework.stereotype.Service;

/**
 * 租户菜单管理
 */
@Service
public class SysTenantMenuServiceImpl extends ServiceImpl<SysTenantMenuMapper, SysTenantMenu>
		implements SysTenantMenuService {

	@Override
	public Boolean saveTenant(SysTenantMenu sysTenantMenu) {
		return this.save(sysTenantMenu);
	}

}
