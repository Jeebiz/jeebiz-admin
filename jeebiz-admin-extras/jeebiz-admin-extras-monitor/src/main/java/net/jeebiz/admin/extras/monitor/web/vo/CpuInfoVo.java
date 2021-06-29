package net.jeebiz.admin.extras.monitor.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * CPU相关信息
 */
@ApiModel(value = "CpuInfoVo", description = "CPU相关信息")
@Data
public class CpuInfoVo {
	
	/**
	 * CPU核心数
	 */
	@ApiModelProperty(name = "cpuNum", value = "核心数")
	private int cpuNum;

	/**
	 * CPU总的使用率
	 */
	@ApiModelProperty(name = "total", value = "CPU总的使用率")
	private double total;

	/**
	 * CPU系统使用率
	 */
	@ApiModelProperty(name = "sys", value = "CPU系统使用率")
	private double sys;

	/**
	 * CPU用户使用率
	 */
	@ApiModelProperty(name = "user", value = "CPU用户使用率")
	private double user;

	/**
	 * CPU当前等待率
	 */
	@ApiModelProperty(name = "wait", value = "CPU当前等待率")
	private double wait;

	/**
	 * CPU当前空闲率
	 */
	@ApiModelProperty(name = "free", value = "CPU当前空闲率")
	private double free;
}
