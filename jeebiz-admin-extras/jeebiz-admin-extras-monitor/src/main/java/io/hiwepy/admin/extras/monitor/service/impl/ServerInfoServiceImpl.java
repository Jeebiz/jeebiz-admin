/**
 * Copyright (C) 2018 Jeebiz (http://jeebiz.net).
 * All Rights Reserved.
 */
package io.hiwepy.admin.extras.monitor.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperationTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import hitool.core.collections.CollectionUtils;
import hitool.core.format.ByteUnitFormat;
import io.hiwepy.admin.extras.monitor.service.IServerInfoService;
import io.hiwepy.admin.extras.monitor.utils.OshiUtils;
import io.hiwepy.admin.extras.monitor.web.vo.CpuInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.JvmInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.MemInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.ServerInfoVo;
import io.hiwepy.admin.extras.monitor.web.vo.SysDiskInfoVo;
import io.hiwepy.admin.extras.redis.setup.BizRedisKey;
import oshi.hardware.HardwareAbstractionLayer;

@Service
public class ServerInfoServiceImpl implements IServerInfoService {

	@Autowired
    private RedisOperationTemplate redisOperationTemplate;

	@Override
	public ServerInfoVo getServerInfo(ByteUnitFormat unit) {
		return OshiUtils.getServerInfo(unit);
	}

	@Override
	public CpuInfoVo getCpuInfo(ByteUnitFormat unit) {
		HardwareAbstractionLayer hal = OshiUtils.si.getHardware();
		return OshiUtils.getCpuInfo(hal.getProcessor());
	}

	@Override
	public MemInfoVo getMemInfo(ByteUnitFormat unit) {
		HardwareAbstractionLayer hal = OshiUtils.si.getHardware();
		return OshiUtils.getMemInfo(hal.getMemory(), unit);
	}

	@Override
	public JvmInfoVo getJvmInfo(ByteUnitFormat unit) {
		return OshiUtils.getJvmInfo(unit);
	}

	@Override
	public List<SysDiskInfoVo> getDiskInfos(ByteUnitFormat unit) {
		HardwareAbstractionLayer hal = OshiUtils.si.getHardware();
		return OshiUtils.getSysDisks(OshiUtils.si.getOperatingSystem());
	}

	@Override
	public List<Double> getCpuHistory(Integer lastSequence, Integer maxLimited) {
		// 1、从缓存中查询我关注的用户缓存
		String cpuUsageHistoryKey = BizRedisKey.SERVER_USAGE_HISTORY.getKey("cpu");
		Integer curruentSequence = lastSequence + maxLimited;
		Set<ZSetOperations.TypedTuple<Object>> zRevrangeWithScores = getRedisOperationTemplate().zRevrangeWithScores(cpuUsageHistoryKey,
				Math.max(0, lastSequence), curruentSequence);
		// 2、如果缓存不存在则返回空集合
		if(CollectionUtils.isEmpty(zRevrangeWithScores)) {
			return Lists.newArrayList();
		}
		// 6、对结果进行解析
		return zRevrangeWithScores.stream().map(tuple ->{
			return Double.valueOf(tuple.getValue().toString());
		}).collect(Collectors.toList());
	}

	@Override
	public List<JvmInfoVo> getJvmHistory(Integer lastSequence, Integer maxLimited) {
		return null;
	}

	@Override
	public List<MemInfoVo> getMemHistory(Integer lastSequence, Integer maxLimited) {
		return null;
	}

	@Override
	public List<SysDiskInfoVo> getDiskHistory(Integer lastSequence, Integer maxLimited) {
		return null;
	}

	public RedisOperationTemplate getRedisOperationTemplate() {
		return redisOperationTemplate;
	}

}
