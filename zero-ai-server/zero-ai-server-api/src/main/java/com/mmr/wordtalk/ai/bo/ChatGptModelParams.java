package com.mmr.wordtalk.ai.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 张恩睿
 * @date 2023-06-29 12:03:00
 */
@Data
public class ChatGptModelParams {

    /**
     * 温度采用系数：0~2,较高的值将使输出更加随机，而较低的值将使其更加集中和确定
     */
    private Double temperature = 0.9;

    /**
     * 核心采样系数：0~1,回答的准确性，0.1 意味着只考虑包含前 10% 概率质量的代币
     */
    @JsonProperty("top_p")
    private Double topP = 0.9;

    /**
     * 重复话题惩罚：-2.0~2.0,增加模型在回答问题时考虑是新主题的可能性。
     */
    @JsonProperty("presence_penalty")
    private Double presencePenalty = 0.0;

    /**
     * 重复回答惩罚：-2.0~2.0,增加模型对同一个问题产生不同回答的可能性。
     */
    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty = 0.0;

    /**
     * 传递的最大token值
     */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /**
     * 结果数
     */
    private Integer n = 1;


}
