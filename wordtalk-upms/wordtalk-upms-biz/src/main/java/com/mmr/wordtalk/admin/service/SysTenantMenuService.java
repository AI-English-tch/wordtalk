package com.mmr.wordtalk.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mmr.wordtalk.admin.api.entity.SysTenantMenu;

public interface SysTenantMenuService extends IService<SysTenantMenu> {

	Boolean saveTenant(SysTenantMenu sysTenantMenu);

}
