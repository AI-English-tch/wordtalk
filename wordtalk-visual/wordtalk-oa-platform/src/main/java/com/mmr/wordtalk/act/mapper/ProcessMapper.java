package com.mmr.wordtalk.act.mapper;

import com.mmr.wordtalk.common.data.datascope.WordtalkBaseMapper;
import org.activiti.engine.repository.Model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 请假流程
 *
 * @author 冷冷
 * @date 2018-09-27 15:20:44
 */
@Mapper
public interface ProcessMapper extends WordtalkBaseMapper<Model> {

	void deleteByIds(@Param("ids") String[] ids);

}
