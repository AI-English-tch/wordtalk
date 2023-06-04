package com.mmr.wordtalk.mp.mapper;

import com.mmr.wordtalk.common.data.datascope.WordtalkBaseMapper;
import com.mmr.wordtalk.mp.entity.WxMsg;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信消息
 *
 * @author JL
 * @date 2019-05-28 16:12:10
 */
@Mapper
public interface WxMsgMapper extends WordtalkBaseMapper<WxMsg> {

}
