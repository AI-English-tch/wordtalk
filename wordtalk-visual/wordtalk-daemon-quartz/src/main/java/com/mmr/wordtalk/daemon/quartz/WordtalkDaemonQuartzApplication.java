package com.mmr.wordtalk.daemon.quartz;

import com.mmr.wordtalk.common.feign.annotation.EnableWordtalkFeignClients;
import com.mmr.wordtalk.common.security.annotation.EnableWordtalkResourceServer;
import com.mmr.wordtalk.common.swagger.annotation.EnableOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author frwcloud
 * @date 2019/01/23 定时任务模块
 */
@EnableOpenApi("job")
@EnableWordtalkFeignClients
@EnableWordtalkResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class WordtalkDaemonQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordtalkDaemonQuartzApplication.class, args);
	}

}
