package com.mmr.wordtalk.app;

import com.mmr.wordtalk.common.feign.annotation.EnableWordtalkFeignClients;
import com.mmr.wordtalk.common.security.annotation.EnableWordtalkResourceServer;
import com.mmr.wordtalk.common.swagger.annotation.EnableOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wordtalk archetype
 * <p>
 * 项目启动类
 */
@EnableOpenApi("app")
@EnableWordtalkFeignClients
@EnableDiscoveryClient
@EnableWordtalkResourceServer
@SpringBootApplication
public class WordtalkAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordtalkAppApplication.class, args);
	}

}
