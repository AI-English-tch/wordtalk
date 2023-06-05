package com.mmr.wordtalk.common.sequence.range.impl.name;

import com.mmr.wordtalk.common.sequence.range.BizName;
import lombok.AllArgsConstructor;

/**
 * @author 张恩睿
 * @date 2019-05-26 根据传入返回bizname
 */
@AllArgsConstructor
public class DefaultBizName implements BizName {

	private String bizName;

	/**
	 * 生成空间名称
	 */
	@Override
	public String create() {
		return bizName;
	}

}
