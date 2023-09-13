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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.app.api.dto.AppUserDTO;
import com.mmr.wordtalk.app.api.dto.AppUserInfo;
import com.mmr.wordtalk.app.api.dto.AppUserRegisterDto;
import com.mmr.wordtalk.app.api.entity.AppMenu;
import com.mmr.wordtalk.app.api.entity.AppRole;
import com.mmr.wordtalk.app.api.entity.AppUser;
import com.mmr.wordtalk.app.api.entity.AppUserRole;
import com.mmr.wordtalk.app.api.model.registration.EmailRegistration;
import com.mmr.wordtalk.app.api.vo.AppUserExcelVO;
import com.mmr.wordtalk.app.api.vo.AppUserVO;
import com.mmr.wordtalk.app.mapper.AppUserMapper;
import com.mmr.wordtalk.app.service.AppMenuService;
import com.mmr.wordtalk.app.service.AppRoleService;
import com.mmr.wordtalk.app.service.AppUserRoleService;
import com.mmr.wordtalk.app.service.AppUserService;
import com.mmr.wordtalk.app.trigger.EmailTrigger;
import com.mmr.wordtalk.common.core.constant.CacheConstants;
import com.mmr.wordtalk.common.core.constant.CommonConstants;
import com.mmr.wordtalk.common.core.exception.ErrorCodes;
import com.mmr.wordtalk.common.core.util.KeyStrResolver;
import com.mmr.wordtalk.common.core.util.MsgUtils;
import com.mmr.wordtalk.common.core.util.R;
import com.mmr.wordtalk.common.excel.vo.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * app用户表
 *
 * @author 张恩睿
 * @date 2022-12-07 09:52:03
 */
@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements AppUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private final AppUserRoleService appUserRoleService;

    private final AppRoleService appRoleService;

    private final AppMenuService appMenuService;

    private final CacheManager cacheManager;

    private final EmailTrigger emailTrigger;

    private final KeyStrResolver tenantKeyStrResolver;

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 更新用户
     *
     * @param userDTO
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS_MINI, key = "#userDTO.username")
    public Boolean updateUser(AppUserDTO userDTO) {

        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(userDTO, appUser);
        if (StrUtil.isNotBlank(userDTO.getPassword())) {
            appUser.setPassword(ENCODER.encode(userDTO.getPassword()));
        }
        this.updateById(appUser);

        appUserRoleService.remove(Wrappers.<AppUserRole>lambdaQuery().eq(AppUserRole::getUserId, appUser.getUserId()));
        userDTO.getRole().forEach(roleId -> {
            AppUserRole appUserRole = new AppUserRole();
            appUserRole.setRoleId(roleId);
            appUserRole.setUserId(userDTO.getUserId());
            appUserRole.insert();
        });
        return Boolean.TRUE;
    }

    /**
     * 新增用户
     *
     * @param userDTO
     * @return
     */
    @Override
    public Boolean saveUser(AppUserDTO userDTO) {
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(userDTO, appUser);
        appUser.setDelFlag(CommonConstants.STATUS_NORMAL);
        appUser.setPassword(ENCODER.encode(userDTO.getPassword()));
        baseMapper.insert(appUser);
        // 如果角色为空，赋默认角色
        if (CollUtil.isNotEmpty(userDTO.getRole())) {
            List<AppUserRole> userRoleList = userDTO.getRole().stream().map(roleId -> {
                AppUserRole userRole = new AppUserRole();
                userRole.setUserId(appUser.getUserId());
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
            appUserRoleService.saveBatch(userRoleList);
        }
        return Boolean.TRUE;
    }

    /**
     * 查询全部的用户
     *
     * @param appUser
     * @return
     */
    @Override
    public List<AppUserExcelVO> listUser(AppUserDTO appUser) {
        List<AppUser> appUsers = baseMapper.selectList(null);
        List<AppUserExcelVO> appUserExcelVOS = appUsers.stream().map(item -> {
            AppUserExcelVO appUserExcelVO = new AppUserExcelVO();
            BeanUtils.copyProperties(item, appUserExcelVO);
            return appUserExcelVO;
        }).collect(Collectors.toList());
        return appUserExcelVOS;
    }

    @Override
    public IPage getUsersWithRolePage(Page page, AppUserDTO appUserDTO) {
        return baseMapper.getUserVosPage(page, appUserDTO);
    }

    @Override
    public AppUserInfo findUserInfo(AppUser user) {
        AppUserInfo info = new AppUserInfo();
        info.setAppUser(user);
        // 设置角色列表 （ID）
        List<Long> roleIds = appRoleService.findRolesByUserId(user.getUserId()).stream().map(AppRole::getRoleId)
                .collect(Collectors.toList());
        info.setRoles(ArrayUtil.toArray(roleIds, Long.class));

        // 设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();
        roleIds.forEach(roleId -> {
            List<String> permissionList = appMenuService.findMenuByRoleId(roleId).stream()
                    .filter(menu -> StrUtil.isNotEmpty(menu.getPermission())).map(AppMenu::getPermission)
                    .collect(Collectors.toList());
            permissions.addAll(permissionList);
        });
        info.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return info;
    }

    @Override
    @CacheEvict(value = CacheConstants.USER_DETAILS_MINI, key = "#userDto.username")
    public R updateUserInfo(AppUserDTO userDto) {
        AppUser appUser = baseMapper.selectById(userDto.getUserId());
        BeanUtils.copyProperties(userDto, appUser);
        return R.ok(this.updateById(appUser));
    }

    @Override
    public AppUserVO selectUserVoById(Long userId) {
        return baseMapper.getUserVoById(userId);
    }

    /**
     * 删除user用户同时删除user-role关系表
     *
     * @param ids userIds
     */
    @Override
    public Boolean deleteAppUserByIds(Long[] ids) {
        Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS_MINI);
        for (AppUser appUser : baseMapper.selectBatchIds(CollUtil.toList(ids))) {
            cache.evict(appUser.getUsername());
        }
        // 删除用户关联表
        this.appUserRoleService
                .remove(Wrappers.<AppUserRole>lambdaQuery().in(AppUserRole::getUserId, CollUtil.toList(ids)));

        this.removeBatchByIds(CollUtil.toList(ids));

        return Boolean.TRUE;

    }

    /**
     * @param excelVOList
     * @param bindingResult
     * @return
     */
    @Override
    public R importUser(List<AppUserExcelVO> excelVOList, BindingResult bindingResult) {
        // 通用校验获取失败的数据
        List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();

        // 执行数据插入操作 组装 UserDto
        for (AppUserExcelVO excel : excelVOList) {
            // 个性化校验逻辑
            List<AppUser> userList = this.list();
            List<AppRole> roleList = appRoleService.list();

            Set<String> errorMsg = new HashSet<>();
            // 校验用户名是否存在
            boolean exsitUserName = userList.stream()
                    .anyMatch(sysUser -> excel.getUsername().equals(sysUser.getUsername()));

            if (exsitUserName) {
                errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, excel.getUsername()));
            }

            // 判断输入的角色名称列表是否合法
            List<String> roleNameList = StrUtil.split(excel.getRoleNameList(), StrUtil.COMMA);
            List<AppRole> roleCollList = roleList.stream()
                    .filter(role -> roleNameList.stream().anyMatch(name -> role.getRoleName().equals(name)))
                    .collect(Collectors.toList());

            if (roleCollList.size() != roleNameList.size()) {
                errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_ROLE_ROLENAME_INEXISTENCE, excel.getRoleNameList()));
            }
            // 数据合法情况
            if (CollUtil.isEmpty(errorMsg)) {
                insertExcelUser(excel, roleCollList);
            } else {
                // 数据不合法情况
                errorMessageList.add(new ErrorMessage(excel.getLineNum(), errorMsg));
            }

        }

        if (CollUtil.isNotEmpty(errorMessageList)) {
            return R.failed(errorMessageList);
        }
        return R.ok(null, MsgUtils.getMessage(ErrorCodes.SYS_USER_IMPORT_SUCCEED));

    }

    @Override
    public R getEmailCode(String email) {
        // 判断邮箱是否已被注册
        AppUser appUser = baseMapper.selectOne(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getEmail, email));
        if (ObjectUtil.isNotNull(appUser)) {
            return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_EMAIL_EXISTING, email));
        }
        String key = String.format("%s:%s:%s", tenantKeyStrResolver.key(), CacheConstants.REGISTER_VERIFICATION, email);
        redisTemplate.delete(key);
        // 3.重新生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        // 4.验证码保存到缓存
        redisTemplate.opsForValue().set(key, code, 10, TimeUnit.MINUTES);
        // 5.发送邮件
        emailTrigger.sendRegisterCode(email, code);
        return R.ok(null, MsgUtils.getMessage(ErrorCodes.VERIFY_CODE_SUCCESS));
    }

    @Override
    public R registerByEmail(AppUserRegisterDto registerDto) {
        // 1.转换注册参数
        EmailRegistration registration = BeanUtil.toBean(registerDto.getRegistration(), EmailRegistration.class);
        // 2.校验验证码是否正确
        String email = registration.getEmail();
        String code = registration.getCode();
        String key = String.format("%s:%s:%s", tenantKeyStrResolver.key(), CacheConstants.REGISTER_VERIFICATION, email);
        String verify = redisTemplate.opsForValue().get(key);
        if (code.equals(verify)) {
            // 验证通过可以注册
            AppUser appUser = baseMapper.selectOne(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getUsername, registerDto.getUsername()));
            if (ObjectUtil.isNotNull(appUser)) {
                return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, registerDto.getUsername()));
            }
            appUser = new AppUser();
            BeanUtils.copyProperties(registerDto, appUser);
            appUser.setDelFlag(CommonConstants.STATUS_NORMAL);
            appUser.setPassword(ENCODER.encode(registerDto.getPassword()));
            appUser.setEmail(email);
            this.save(appUser);
            // 给用户赋予默认角色
            AppUserRole userRole = new AppUserRole();
            userRole.setUserId(appUser.getUserId());
            userRole.setRoleId(1L);
            appUserRoleService.save(userRole);
            return R.ok(null, MsgUtils.getMessage(ErrorCodes.REGISTER_SUCCESS));
        }
        return R.failed(MsgUtils.getMessage(ErrorCodes.VERIFY_CODE_ERROR));
    }

    private void insertExcelUser(AppUserExcelVO excel, List<AppRole> roleCollList) {
        AppUserDTO userDTO = new AppUserDTO();
        userDTO.setUsername(excel.getUsername());
        userDTO.setPhone(excel.getPhone());
        userDTO.setNickname(excel.getNickname());
        userDTO.setName(excel.getName());
        userDTO.setEmail(excel.getEmail());
        // 批量导入初始密码为手机号
        userDTO.setPassword(userDTO.getPhone());
        // 根据角色名称查询角色ID
        List<Long> roleIdList = roleCollList.stream().map(AppRole::getRoleId).collect(Collectors.toList());
        userDTO.setRole(roleIdList);
        // 插入用户
        this.saveUser(userDTO);
    }

}
