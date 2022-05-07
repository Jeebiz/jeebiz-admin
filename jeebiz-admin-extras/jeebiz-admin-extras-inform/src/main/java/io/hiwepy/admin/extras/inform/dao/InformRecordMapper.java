/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.inform.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.hiwepy.admin.extras.inform.dao.entities.InformRecordEntity;
import io.hiwepy.admin.extras.inform.web.dto.InformRecordStatsDTO;
import io.hiwepy.boot.api.dao.BaseMapper;

@Mapper
public interface InformRecordMapper extends BaseMapper<InformRecordEntity> {
	
	/**
	 * 消息通知统计信息
	 */
	List<InformRecordStatsDTO> getStats(@Param("uid") String uid);

	int deleteByTid(@Param("tid") String tid);
	
	/**
	 * 删除用户的通知信息
	 * @param userid
	 * @param ids
	 * @return
	 */
	int deleteByUid(@Param("uid") String uid, @Param("ids") List<String> ids);
	
}