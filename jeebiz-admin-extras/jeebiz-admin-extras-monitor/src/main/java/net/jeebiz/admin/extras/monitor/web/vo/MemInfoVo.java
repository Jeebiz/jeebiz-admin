package net.jeebiz.admin.extras.monitor.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 內存相关信息
 */
@ApiModel(value = "MemInfoVo", description = "內存相关信息")
@Data
public class MemInfoVo {

	/**
	 * 内存总量
	 */
	@ApiModelProperty(name = "total", value = "内存总量")
	private double total;

	/**
	 * 已用内存
	 */
	@ApiModelProperty(name = "used", value = "已用内存")
	private double used;

	/**
	 * 剩余内存
	 */
	@ApiModelProperty(name = "free", value = "剩余内存")
	private double free;

	/**
	 * 内存使用率
	 */
	@ApiModelProperty(name = "usage", value = "内存使用率")
	private double usage;

}
