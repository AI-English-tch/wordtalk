package com.mmr.wordtalk.common.excel.handler;

import com.alibaba.excel.event.AnalysisEventListener;
import com.mmr.wordtalk.common.excel.vo.ErrorMessage;

import java.util.List;

/**
 * list analysis EventListener
 *
 * @author 张恩睿
 */
public abstract class ListAnalysisEventListener<T> extends AnalysisEventListener<T> {

	/**
	 * 获取 excel 解析的对象列表
	 * @return 集合
	 */
	public abstract List<T> getList();

	/**
	 * 获取异常校验结果
	 * @return 集合
	 */
	public abstract List<ErrorMessage> getErrors();

}
