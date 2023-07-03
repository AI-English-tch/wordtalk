package com.mmr.wordtalk.bridge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mmr.wordtalk.bridge.entity.GptRobot;
import com.mmr.wordtalk.bridge.mapper.GptRobotMapper;
import com.mmr.wordtalk.bridge.service.GptRobotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * GptRobotServiceImpl
 *
 * @author 张恩睿
 * @date 2023-07-04 00:51:52
 */
@Service
@AllArgsConstructor
public class GptRobotServiceImpl extends ServiceImpl<GptRobotMapper, GptRobot> implements GptRobotService {
}
