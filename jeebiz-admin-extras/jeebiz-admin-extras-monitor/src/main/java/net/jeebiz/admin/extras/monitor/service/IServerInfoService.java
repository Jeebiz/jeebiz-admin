/** 
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved. 
 */
package net.jeebiz.admin.extras.monitor.service;

import java.util.List;

import net.jeebiz.admin.extras.monitor.web.vo.*;
import net.jeebiz.boot.api.utils.ByteUnitFormat;

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
