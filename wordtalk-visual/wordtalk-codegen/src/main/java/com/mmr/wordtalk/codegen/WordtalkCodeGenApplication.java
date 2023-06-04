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

package com.mmr.wordtalk.codegen;

import com.mmr.wordtalk.common.datasource.annotation.EnableDynamicDataSource;
import com.mmr.wordtalk.common.feign.annotation.EnableWordtalkFeignClients;
import com.mmr.wordtalk.common.security.annotation.EnableWordtalkResourceServer;
import com.mmr.wordtalk.common.swagger.annotation.EnableOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wordtalk
 * @date 2018/07/29 代码生成模块
 */
@EnableDynamicDataSource
@EnableOpenApi("gen")
@EnableWordtalkFeignClients
@EnableDiscoveryClient
@EnableWordtalkResourceServer
@SpringBootApplication
public class WordtalkCodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordtalkCodeGenApplication.class, args);
	}

}
