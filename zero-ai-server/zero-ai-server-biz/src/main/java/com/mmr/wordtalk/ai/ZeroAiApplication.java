package com.mmr.wordtalk.ai;

import com.mmr.wordtalk.common.feign.annotation.EnableWordtalkFeignClients;
import com.mmr.wordtalk.common.security.annotation.EnableWordtalkResourceServer;
import com.mmr.wordtalk.common.swagger.annotation.EnableOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zero archetype
 * <p>
 * 项目启动类
 */
@EnableOpenApi("zero-ai-server")
@EnableWordtalkFeignClients
@EnableDiscoveryClient
@EnableWordtalkResourceServer
@SpringBootApplication
public class ZeroAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeroAiApplication.class, args);
    }
}
