package com.mmr.wordtalk.dictionary;

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
@EnableOpenApi("wordtalk-dictionary")
@EnableWordtalkFeignClients
@EnableDiscoveryClient
@EnableWordtalkResourceServer
@SpringBootApplication
public class DictionaryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictionaryApplication.class, args);
    }
}
