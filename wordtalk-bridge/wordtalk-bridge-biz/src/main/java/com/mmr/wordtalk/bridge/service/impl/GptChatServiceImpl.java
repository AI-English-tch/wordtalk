package com.mmr.wordtalk.bridge.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.mmr.wordtalk.ai.api.feign.RemoteAiModelService;
import com.mmr.wordtalk.ai.bo.ChatGptModelParams;
import com.mmr.wordtalk.ai.dto.Context;
import com.mmr.wordtalk.ai.dto.SendDto;
import com.mmr.wordtalk.bridge.dto.GptChatDto;
import com.mmr.wordtalk.bridge.entity.GptRobot;
import com.mmr.wordtalk.bridge.service.GptChatService;
import com.mmr.wordtalk.bridge.service.GptHistoryService;
import com.mmr.wordtalk.bridge.service.GptRobotService;
import com.mmr.wordtalk.common.core.util.RetOps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * GptChatServiceImpl
 *
 * @author 张恩睿
 * @date 2023-07-04 01:17:17
 */
@Service
@AllArgsConstructor
public class GptChatServiceImpl implements GptChatService {

    private final RemoteAiModelService remoteAiModelService;

    private final GptHistoryService gptHistoryService;

    private final GptRobotService gptRobotService;

    @Override
    public String send(Long robotId, GptChatDto chatDto) {
		Long bookId = chatDto.getBookId();
		String message = chatDto.getMessage();
		String inject = chatDto.getInject();
		// 获取助手的信息
        GptRobot robot = gptRobotService.getById(robotId);
        // 获取模型的ID
        Long modelId = robot.getModel_id();
        // 获取模型的微调参数
        ChatGptModelParams modelParams = robot.getModelParams();
        // 构建上下文对象
        List<Context> contextList = buildContext(robot, bookId, message, inject);
        SendDto sendDto = new SendDto();
		sendDto.setSystem(robot.getSystem());
        sendDto.setContextList(contextList);
        sendDto.setParams(new JSONObject(modelParams));
        // 接收AI模型的返回值
        String result = "";
        Optional<String> optional = RetOps.of(remoteAiModelService.sendOnOnce(modelId, sendDto))
                .getData();
        if (optional.isPresent()) {
            result = optional.get();
            // 将Ai的返回值保存到上下文中
            List<Context> needSave = new ArrayList<>(2);
            Context user = Context.builder()
                    .role(Context.Role.USER.getValue())
                    .content(message)
                    .build();
            Context assistant = Context.builder()
                    .role(Context.Role.ASSISTANT.getValue())
                    .content(result)
                    .build();
            needSave.add(user);
            needSave.add(assistant);

            gptHistoryService.saveContext(robotId, bookId, needSave);
        }
        return result;
    }

    @Override
    public String sendOnStream(Long robotId, GptChatDto chatDto) {
		Long bookId = chatDto.getBookId();
		String message = chatDto.getMessage();
		String inject = chatDto.getInject();
        // 获取助手的信息
        GptRobot robot = gptRobotService.getById(robotId);
        // 获取模型的ID
        Long modelId = robot.getModel_id();
        // 获取模型的微调参数
        ChatGptModelParams modelParams = robot.getModelParams();
        // 构建上下文对象
        List<Context> contextList = buildContext(robot, bookId, message, inject);
        SendDto sendDto = new SendDto();
		sendDto.setSystem(robot.getSystem());
        sendDto.setContextList(contextList);
        sendDto.setParams(new JSONObject(modelParams));
        // 接收AI模型的返回值
        String result = "";
        Optional<String> optional = RetOps.of(remoteAiModelService.sendOnStream(modelId, sendDto))
                .getData();
        if (optional.isPresent()) {
            result = optional.get();
            // 将Ai的返回值保存到上下文中
            List<Context> needSave = new ArrayList<>(2);
            Context user = Context.builder()
                    .role(Context.Role.USER.getValue())
                    .content(message)
                    .build();
            Context assistant = Context.builder()
                    .role(Context.Role.ASSISTANT.getValue())
                    .content(result)
                    .build();
            needSave.add(user);
            needSave.add(assistant);

            gptHistoryService.saveContext(robotId, bookId, needSave);
        }
        return result;
    }

    private List<Context> buildContext(GptRobot robot, Long bookId, String message, String inject) {
        // 根据上下文大小获取历史记录
        List<Context> context = gptHistoryService.getHistoryContext(robot.getId(), bookId, robot.getContextSize());
        // 构建prompt数据
        String text = robot.getPrompt();
        if (StrUtil.isNotBlank(inject)) {
            text = StrUtil.format(text, inject);
        }
        Context system = Context.builder()
                .role(Context.Role.SYSTEM.getValue())
                .content(text)
                .build();
        // 构建用户本次消息数据
        Context user = Context.builder()
                .role(Context.Role.USER.getValue())
                .content(message)
                .build();
        // 将系统设置加入头部,将用户消息加入尾部
        context.add(0, system);
        context.add(user);
        // 返回上下文
        return context;
    }
}
