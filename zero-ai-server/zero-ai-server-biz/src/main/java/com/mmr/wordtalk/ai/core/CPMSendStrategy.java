package com.mmr.wordtalk.ai.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mmr.wordtalk.ai.dto.Context;
import com.mmr.wordtalk.ai.dto.SendDto;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张恩睿
 * @date 2023/09/15
 */
@Data
public class CPMSendStrategy implements SendStrategy {

    private static final String HOST = "http://saas-1222385307.us-west-2.elb.amazonaws.com/inference";
    private static final String ENDPOINT_NAME = "cpm-bee-230915045304JBSQ";
    private static final String AK = "72aa3973531411eeb4eb0242ac120004";
    private static final String SK = "I%0isNo+pKr=UfEuNEp2OIdtxmp0jKdE";

    private WebClient webClient;

    public CPMSendStrategy() {
        this.webClient = WebClient.builder()
                .baseUrl(HOST)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String getPayload(SendDto sendDto) {
        String timestamp = String.valueOf(DateUtil.current());
        // 加密sing参数
        String sign = SecureUtil.md5(timestamp + SK);
        String endpointName = ENDPOINT_NAME;
        String ak = AK;

        List<Context> contextList = sendDto.getContextList();

        // 排查是否有prompt--通常在第一个的位置，但是不一定有
        Context system = new Context();
        if (Context.Role.SYSTEM.getValue().equals(contextList.get(0).getRole())) {
            // 存在prompt，从数组中移除
            system = contextList.remove(0);
        }

        // 获取用户这次的问话内容
        Context current = contextList.remove(contextList.size() - 1);

        // 拼接历史消息记录
        String history = contextList.stream()
                .map(context -> {
                    String role = context.getRole();
                    if (role.equals(Context.Role.ASSISTANT)) {
                        role = "<AI>";
                    } else {
                        role = "<用户>";
                    }
                    return role + context.getContent();
                }).collect(Collectors.joining());

        // 封装input的参数
        JSONObject input = new JSONObject();
        input.putOnce("input", history);
        input.putOnce("prompt", system.getContent());
        input.putOnce("question", current.getContent());
        input.putOnce("<ans>", "");

        // 封装请求体参数
        JSONObject body = new JSONObject();
        body.putOnce("endpoint_name", ENDPOINT_NAME);
        body.putOnce("ak", AK);
        body.putOnce("timestamp", timestamp);
        body.putOnce("sign", sign);
        body.putOnce("input", input.toString());

        return body.toString();
    }

    @Override
    public String send(SendDto sendDto) {
        String payload = getPayload(sendDto);
        ParameterizedTypeReference<CPMResponse> responseType = new ParameterizedTypeReference<CPMResponse>() {
        };
        CPMResponse response = webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(responseType)
                .block();

        // 获取返回值，返回给用户
        JSONObject result = JSONUtil.parseObj(response.getData().get("data"));

        return result.getStr("<ans>");
    }

    @Override
    public String streamSend(SendDto sendDto) {
        return SendStrategy.super.streamSend(sendDto);
    }


}
