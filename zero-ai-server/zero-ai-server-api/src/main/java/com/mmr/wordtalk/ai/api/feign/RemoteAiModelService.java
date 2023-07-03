package com.mmr.wordtalk.ai.api.feign;

import com.mmr.wordtalk.ai.dto.SendDto;
import com.mmr.wordtalk.ai.entity.AiModel;
import com.mmr.wordtalk.common.core.constant.ServiceNameConstants;
import com.mmr.wordtalk.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 张恩睿
 * @date 2023-06-30 09:53:00
 */
@FeignClient(contextId = "remoteAiModelService", value = ServiceNameConstants.AI_SERVER)
public interface RemoteAiModelService {

    @GetMapping("/ai-model/list")
    R<List<AiModel>> getAiModelList(AiModel aiModel);

    @PostMapping("/ai-model/sendOnOnce/{id}")
    R<String> sendOnOnce(@PathVariable("id") Long id, @RequestBody SendDto sendDto);

    @PostMapping("/ai-model/sendOnStream/{id}")
    R<String> sendOnStream(@PathVariable("id") Long id, @RequestBody SendDto sendDto);
}
