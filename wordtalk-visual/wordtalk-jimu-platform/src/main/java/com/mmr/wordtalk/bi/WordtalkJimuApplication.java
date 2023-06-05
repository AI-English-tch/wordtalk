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

package com.mmr.wordtalk.bi;

import com.mmr.wordtalk.common.feign.annotation.EnableWordtalkFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 张恩睿
 * @date 2022-04-06
 * <p>
 */
@EnableWordtalkFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { "org.jeecg.modules.jmreport", "com.mmr.wordtalk.bi" },
		exclude = MongoAutoConfiguration.class)
public class WordtalkJimuApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordtalkJimuApplication.class, args);
	}

}
