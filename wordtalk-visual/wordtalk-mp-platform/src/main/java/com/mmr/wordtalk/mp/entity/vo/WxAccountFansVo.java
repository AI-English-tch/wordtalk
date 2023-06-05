package com.mmr.wordtalk.mp.entity.vo;

import com.mmr.wordtalk.mp.entity.WxAccountFans;
import com.mmr.wordtalk.mp.entity.WxAccountTag;
import lombok.Data;

import java.util.List;

/**
 * 粉丝Vo 对象
 *
 * @author 张恩睿
 * @date 2022/1/5
 */
@Data
public class WxAccountFansVo extends WxAccountFans {

	/**
	 * 标签名称列表
	 */
	private List<WxAccountTag> tagList;

}
