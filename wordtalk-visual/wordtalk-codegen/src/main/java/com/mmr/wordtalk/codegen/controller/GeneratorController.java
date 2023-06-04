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

package com.mmr.wordtalk.codegen.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.mmr.wordtalk.codegen.service.GeneratorService;
import com.mmr.wordtalk.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author wordtalk
 * @date 2018-07-30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

	private final GeneratorService generatorService;

	/**
	 * ZIP 下载生成代码
	 * @param tableIds 数据表ID
	 * @param response 流输出对象
	 */
	@SneakyThrows
	@GetMapping("/download")
	public void download(String tableIds, HttpServletResponse response) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);

		// 生成代码
		for (String tableId : tableIds.split(StrUtil.COMMA)) {
			generatorService.downloadCode(Long.parseLong(tableId), zip);
		}

		IoUtil.close(zip);

		// zip压缩包数据
		byte[] data = outputStream.toByteArray();

		response.reset();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", tableIds));
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.write(response.getOutputStream(), false, data);
	}

	/**
	 * 目标目录生成代码
	 */
	@ResponseBody
	@GetMapping("/code")
	public R<String> code(String tableIds) throws Exception {
		// 生成代码
		for (String tableId : tableIds.split(StrUtil.COMMA)) {
			generatorService.generatorCode(Long.valueOf(tableId));
		}

		return R.ok();
	}

	/**
	 * 预览代码
	 * @param tableId 表ID
	 * @return
	 */
	@SneakyThrows
	@GetMapping("/preview")
	public List<Map<String, String>> preview(Long tableId) {
		return generatorService.preview(tableId);
	}

	/**
	 * 获取表单设计器初始化数据
	 * @param dsName 数据源名称
	 * @param tableName 表名称
	 * @return json string
	 */
	@SneakyThrows
	@GetMapping("/vform")
	public String vform(String dsName, String tableName) {
		return generatorService.vform(dsName, tableName);
	}

	/**
	 * 获取表单设计器初始化数据
	 * @param formId 表单ID
	 * @return json string
	 */
	@SneakyThrows
	@GetMapping("/vform/sfc")
	public void vformSfc(Long formId, HttpServletResponse response) {
		String result = generatorService.vformSfc(formId);

		byte[] data = result.getBytes(StandardCharsets.UTF_8);
		response.reset();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=form.vue");
		response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.write(response.getOutputStream(), false, data);
	}

}
