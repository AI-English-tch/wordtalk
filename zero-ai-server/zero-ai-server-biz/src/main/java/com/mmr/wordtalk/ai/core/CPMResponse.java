package com.mmr.wordtalk.ai.core;

import lombok.Data;

import java.util.Map;

@Data
public class CPMResponse {
    private Integer code;
    private String msg;
    private Map<String, String> data;
}