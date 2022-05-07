/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package io.hiwepy.admin.extras.monitor.service;

import java.util.List;

import hitool.core.format.ByteUnitFormat;
import io.hiwepy.admin.extras.monitor.web.vo.CpuInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.JvmInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.MemInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.ServerInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.SysDiskInfoVo;

public interface IServerInfoService {

	ServerInfoVo getServerInfo(ByteUnitFormat unit);

	CpuInfoVo getCpuInfo(ByteUnitFormat unit);

	MemInfoVo getMemInfo(ByteUnitFormat unit);

	JvmInfoVo getJvmInfo(ByteUnitFormat unit);

	List<SysDiskInfoVo> getDiskInfos(ByteUnitFormat unit);

	List<Double> getCpuHistory(Integer lastSequence, Integer maxLimited);

	List<JvmInfoVo> getJvmHistory(Integer lastSequence, Integer maxLimited);

	List<MemInfoVo> getMemHistory(Integer lastSequence, Integer maxLimited);

	List<SysDiskInfoVo> getDiskHistory(Integer lastSequence, Integer maxLimited);
			 
}
