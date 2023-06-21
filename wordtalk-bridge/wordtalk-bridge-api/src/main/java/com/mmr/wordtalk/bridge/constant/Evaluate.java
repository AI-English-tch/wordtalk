package com.mmr.wordtalk.bridge.constant;

import cn.hutool.core.math.MathUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.MathUtils;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author 张恩睿
 * @date 2023-06-21 13:54:00
 */
@Getter
@AllArgsConstructor
public enum Evaluate {
	FAMILIAR("familiar", (scope) -> scope.multiply(new BigDecimal(0.5))),
	NORMAL("normal", (scope) -> scope.multiply(new BigDecimal(1))),
	UNFAMILIAR("unfamiliar", (scope) -> new BigDecimal(Math.log(scope.doubleValue() + 1.31375270747048)));
	private String name;
	private UnaryOperator<BigDecimal> operator;

	// 根据传入的name值返回对应的operator操作
	public static UnaryOperator<BigDecimal> getOperator(String name) {
		for (Evaluate evaluate : Evaluate.values()) {
            if (evaluate.name.equals(name)) {
                return evaluate.operator;
            }
        }
        return null;
	}
}
