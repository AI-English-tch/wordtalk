package com.mmr.wordtalk.bridge.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 张恩睿
 * @date 2023-06-14 11:45:00
 */
@Data
public class GptTalk {

	/**
	 * 关联的话题id
	 */
	private Long topicId;
	/**
	 * 使用的题词id
	 * (当topicId有值时promptId无效，当topicId无值时，根据promptId创建对应的话题，promptId也可以为null视为没挑选提词)
	 */
	private Long promptId;
	/**
	 * 用户发送的内容
	 */
	@NotBlank
	private String content;

}
