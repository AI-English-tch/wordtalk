package com.mmr.wordtalk.common.ai.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 上下文对象
 *
 * @author 张恩睿
 * @date 2023-06-07 14:30:00
 */
@Data
@AllArgsConstructor
public class Context implements Serializable {
    private String role;

    private String text;
}
